CREATE TABLE refresh_token (
       refresh_token_id bigint not null GENERATED ALWAYS AS IDENTITY primary key,
       refresh_token VARCHAR(255) NOT NULL,
       account_id bigint NOT NULL,
       expires_at TIMESTAMP NOT NULL,
       FOREIGN KEY (account_id) REFERENCES Account(account_id)
);