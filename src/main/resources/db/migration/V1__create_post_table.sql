CREATE TABLE IF NOT EXISTS `post`
(
    post_id  BINARY(16) NOT NULL,
    nom VARCHAR(50) NOT NULL,
    description TEXT NULL,
    media VARCHAR(255),
    shop_id BINARY(16) NOT NULL,
    created_at    datetime DEFAULT NOW() NULL,
    updated_at datetime DEFAULT NOW() NULL
);