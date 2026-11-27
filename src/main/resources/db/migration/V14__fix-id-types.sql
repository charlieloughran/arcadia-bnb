-- V14__fix-id-types.sql

ALTER TABLE users ALTER COLUMN id TYPE BIGINT USING id::bigint;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='spaces' and column_name='user_id') THEN
        ALTER TABLE spaces ADD COLUMN user_id BIGINT;
    ELSE
        ALTER TABLE spaces ALTER COLUMN user_id TYPE BIGINT USING NULL;
    END IF;
END $$;

ALTER TABLE spaces DROP CONSTRAINT IF EXISTS fk_space_user;
ALTER TABLE spaces DROP CONSTRAINT IF EXISTS fk_space_owner;

ALTER TABLE spaces
ADD CONSTRAINT fk_space_user
FOREIGN KEY (user_id)
REFERENCES users(id)
NOT VALID;