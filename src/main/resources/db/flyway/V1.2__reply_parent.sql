ALTER TABLE reply
    ADD COLUMN parent_id bigint,
ADD FOREIGN KEY (parent_id) REFERENCES reply(reply_id);