CREATE TABLE if not exists account (
    account_id  bigint not null GENERATED ALWAYS AS IDENTITY primary key,
    email    VARCHAR(40),
    password varchar,
    nickname VARCHAR(15) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    update_at   TIMESTAMP WITHOUT TIME ZONE,
    role    VARCHAR(10) NOT NULL,
    path     VARCHAR(200)
);

CREATE TABLE if not exists category (
    category_id bigint not null GENERATED ALWAYS AS IDENTITY primary key,
    name        VARCHAR(50) NOT NULL,
    priority    integer not null
);

CREATE TABLE if not exists board (
    board_id   bigint not null GENERATED ALWAYS AS IDENTITY primary key,
    title       VARCHAR(200) NOT NULL,
    content     TEXT         NOT NULL,
    hit_cnt    INTEGER DEFAULT 0,
    like_cnt      INTEGER DEFAULT 0,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    update_at   TIMESTAMP WITHOUT TIME ZONE,
    category_id BIGINT,
    account_id     BIGINT
);

ALTER TABLE board
    ADD CONSTRAINT FK_BOARD_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (category_id);

ALTER TABLE board
    ADD CONSTRAINT FK_BOARD_ON_USER FOREIGN KEY (account_id) REFERENCES account (account_id);

CREATE TABLE if not exists image (
    image_id bigint not null GENERATED ALWAYS AS IDENTITY primary key,
    name     VARCHAR(100) NOT NULL,
    path     VARCHAR(100) NOT NULL,
    board_id BIGINT
);

ALTER TABLE image
    ADD CONSTRAINT FK_IMAGE_ON_BOARD FOREIGN KEY (board_id) REFERENCES board (board_id);


CREATE TABLE if not exists reply (
    reply_id   bigint not null GENERATED ALWAYS AS IDENTITY primary key,
    content    VARCHAR(100) NOT NULL,
    create_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    board_id   BIGINT,
    account_id    BIGINT
);


ALTER TABLE reply
    ADD CONSTRAINT FK_REPLY_ON_BOARD FOREIGN KEY (board_id) REFERENCES board (board_id);

ALTER TABLE reply
    ADD CONSTRAINT FK_REPLY_ON_USER FOREIGN KEY (account_id) REFERENCES account (account_id);