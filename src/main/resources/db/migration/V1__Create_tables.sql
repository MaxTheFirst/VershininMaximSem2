CREATE SEQUENCE article_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE category_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE user_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE user_website_status_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE website_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE categories (
    category_id BIGINT PRIMARY KEY DEFAULT nextval('category_id_seq'),
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE websites (
    website_id BIGINT PRIMARY KEY DEFAULT nextval('website_id_seq'),
    name VARCHAR(255) NOT NULL UNIQUE,
    url VARCHAR(255) NOT NULL UNIQUE,
    owner_id BIGINT
);

CREATE TABLE users (
    user_id BIGINT PRIMARY KEY DEFAULT nextval('user_id_seq'),
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE user_categories (
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, category_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE user_websites (
    user_id BIGINT NOT NULL,
    website_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, website_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (website_id) REFERENCES websites(website_id)
);

CREATE TABLE user_website_status (
    user_website_status_id BIGINT PRIMARY KEY DEFAULT nextval('user_website_status_id_seq'),
    user_id BIGINT NOT NULL,
    website_id BIGINT NOT NULL,
    last_sent_article_date TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (website_id) REFERENCES websites(website_id)
);

CREATE TABLE articles (
    article_id BIGINT PRIMARY KEY DEFAULT nextval('article_id_seq'),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    date TIMESTAMP NOT NULL,
    url VARCHAR(255) NOT NULL UNIQUE,
    website_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (website_id) REFERENCES websites(website_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

INSERT INTO categories (category_id, name) VALUES (1, 'Technology');
INSERT INTO categories (category_id, name) VALUES (2, 'Health');
INSERT INTO categories (category_id, name) VALUES (3, 'Education');

INSERT INTO websites (website_id, name, url) VALUES (1, 'Google', 'https://www.google.com');
INSERT INTO websites (website_id, name, url) VALUES (2, 'Wikipedia', 'https://www.wikipedia.org');
INSERT INTO websites (website_id, name, url) VALUES (3, 'StackOverflow', 'https://stackoverflow.com');