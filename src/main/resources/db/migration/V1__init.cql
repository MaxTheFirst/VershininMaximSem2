CREATE KEYSPACE IF NOT EXISTS my_keyspace
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE my_keyspace.user_audit (
    user_id BIGINT,               -- Идентификатор пользователя (данные будут распределены по юзерам)
    event_time TIMESTAMP,       -- Время события (используется для уникальности так и для сортировки)
    event_type TEXT,            -- Тип события (например, "INSERT", "DELETE")
    event_details TEXT,         -- Детали события
    PRIMARY KEY ((user_id), event_time)
) WITH CLUSTERING ORDER BY (event_time DESC)
   AND default_time_to_live = 2592000;
