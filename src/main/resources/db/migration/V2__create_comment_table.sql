CREATE TABLE IF NOT EXISTS `comment`
(
    comment_id  BINARY(16) NOT NULL,
    content TEXT NOT NULL,
    post_id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    created_at    datetime DEFAULT NOW() NULL,
    updated_at datetime DEFAULT NOW() NULL
);