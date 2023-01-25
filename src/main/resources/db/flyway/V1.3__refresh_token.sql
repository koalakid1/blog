CREATE TABLE refresh_token (
       rt_id SERIAL PRIMARY KEY,
       refresh_token VARCHAR(255) NOT NULL,
       account_id INT NOT NULL,
       expires_at TIMESTAMP NOT NULL,
       FOREIGN KEY (account_id) REFERENCES Account(account_id)
);