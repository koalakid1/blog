CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE if not exists "user"
(
    user_id  BIGINT      NOT NULL default nextval('user_id_seq'),
    email    VARCHAR(40),
    name VARCHAR(15) NOT NULL,
    role    VARCHAR(10) NOT NULL,
    path     VARCHAR(200),
    CONSTRAINT pk_user PRIMARY KEY (user_id)
);

alter user_id_seq owned by user.user_id;

CREATE SEQUENCE IF NOT EXISTS category_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS category_priority_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE if not exists category
(
    category_id BIGINT      NOT NULL default nextval('category_id_seq'),
    name        VARCHAR(50) NOT NULL,
    priority    INTEGER     NOT NULL default nextval('category_priority_seq'),
    CONSTRAINT pk_category PRIMARY KEY (category_id)
);

alter category_id_seq owned by category.category_id;
alter category_priority_seq owned by category.priority;


CREATE SEQUENCE IF NOT EXISTS board_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE if not exists board
(
    board_id          BIGINT       NOT NULL default nextval('board_id_seq'),
    title       VARCHAR(200) NOT NULL,
    content     TEXT         NOT NULL,
    hit         INTEGER,
    "like"      INTEGER,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    update_at   TIMESTAMP WITHOUT TIME ZONE,
    category_id BIGINT,
    user_id     BIGINT,
    CONSTRAINT pk_board PRIMARY KEY (board_id)
);

alter board_id_seq owned by board.board_id;

ALTER TABLE board
    ADD CONSTRAINT FK_BOARD_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (category_id);

ALTER TABLE board
    ADD CONSTRAINT FK_BOARD_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (user_id);

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE if not exists image
(
    image_id BIGINT       NOT NULL,
    name     VARCHAR(100) NOT NULL,
    path     VARCHAR(100) NOT NULL,
    board_id BIGINT,
    CONSTRAINT pk_image PRIMARY KEY (image_id)
);

ALTER TABLE image
    ADD CONSTRAINT FK_IMAGE_ON_BOARD FOREIGN KEY (board_id) REFERENCES board (board_id);

CREATE SEQUENCE IF NOT EXISTS reply_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE if not exists reply
(
    reply_id   BIGINT       NOT NULL default nextval('reply_id_seq'),
    content    VARCHAR(100) NOT NULL,
    create_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    board_id   BIGINT,
    user_id    BIGINT,
    CONSTRAINT pk_reply PRIMARY KEY (reply_id)
);

alter reply_id_seq owned by reply.reply_id;

ALTER TABLE reply
    ADD CONSTRAINT FK_REPLY_ON_BOARD FOREIGN KEY (board_id) REFERENCES board (board_id);

ALTER TABLE reply
    ADD CONSTRAINT FK_REPLY_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (user_id);