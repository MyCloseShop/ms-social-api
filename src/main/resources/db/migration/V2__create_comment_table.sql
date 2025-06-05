USE `db-ms-social`;

CREATE TABLE IF NOT EXISTS `comment`
(
    comment_id  BINARY(16) NOT NULL,
    content VARCHAR(255) NOT NULL,
    media VARCHAR(255),
    post_id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    created_at    datetime DEFAULT NOW() NULL,
    updated_at    datetime DEFAULT NOW() NULL,
)