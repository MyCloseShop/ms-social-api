USE `db-ms-login`;

CREATE TABLE IF NOT EXISTS `role`
(
    role_id    INTEGER NOT NULL AUTO_INCREMENT,
    role_name  VARCHAR(50) NOT NULL,
    created_at datetime DEFAULT NOW() NULL,
    updated_at datetime DEFAULT NOW() NULL,
    CONSTRAINT pk_role PRIMARY KEY (role_id)
);

CREATE TABLE IF NOT EXISTS user
(
    user_id       BINARY(16)             NOT NULL,
    username      VARCHAR(50)  NOT NULL,
    email         VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name    VARCHAR(50) NULL,
    last_name     VARCHAR(50) NULL,
    phone_number  VARCHAR(20)  NOT NULL,
    created_at    datetime DEFAULT NOW() NULL,
    updated_at    datetime DEFAULT NOW() NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id BINARY(16) NOT NULL,
    role_id INTEGER NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id)
);

ALTER TABLE user
    ADD CONSTRAINT email UNIQUE (email);

ALTER TABLE user
    ADD CONSTRAINT phone_number UNIQUE (phone_number);

ALTER TABLE `role`
    ADD CONSTRAINT role_name UNIQUE (role_name);

ALTER TABLE user
    ADD CONSTRAINT username UNIQUE (username);

ALTER TABLE user_roles
    ADD CONSTRAINT FK_USER_ROLES_ON_ROLE FOREIGN KEY (role_id) REFERENCES `role` (role_id);

CREATE INDEX role_id ON user_roles (role_id);

ALTER TABLE user_roles
    ADD CONSTRAINT FK_USER_ROLES_ON_USER FOREIGN KEY (user_id) REFERENCES user (user_id);