package com.example.demo.service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.example.demo.domain.dto.UserActionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserAuditService {

  @Autowired
  private CqlSession session;

  enum Action {
    SELECT, UPDATE, INSERT, DELETE
  }

  public void insertUserAction(Long userId, Instant eventTime, Action eventType, String eventDetails) {
    PreparedStatement preparedStatement = session.prepare(
        "INSERT INTO my_keyspace.user_audit (user_id, event_time, event_type, event_details) " +
            "VALUES (?, ?, ?, ?)" +
            "WITH default_time_to_live = 31536000"
    );

    BoundStatement boundStatement = preparedStatement.bind(
        userId, eventTime, eventType.toString(), eventDetails
    );

    session.execute(boundStatement);
  }

  public Optional<UserActionDTO> selectUserAction(Long userId, Instant eventTime) {
    PreparedStatement preparedStatement = session.prepare(
        "SELECT user_id, event_time, event_type, event_details " +
            "FROM my_keyspace.user_audit " +
            "WHERE user_id = ? AND event_time = ?"
    );

    BoundStatement boundStatement = preparedStatement.bind(userId, eventTime);
    ResultSet resultSet = session.execute(boundStatement);
    Row row = resultSet.one();

    if (row != null) {
      return Optional.of(UserActionDTO.builder()
          .userId(row.getLong("user_id"))
          .eventTime(row.getInstant("event_time"))
          .eventType(row.getString("event_type"))
          .eventDetails(row.getString("event_details"))
          .build());
    }

    return Optional.empty();
  }
}