ALTER TABLE spaces ADD COLUMN owner_id BIGINT;
ALTER TABLE spaces ADD CONSTRAINT fk_space_owner FOREIGN KEY (owner_id) REFERENCES users(id);