ALTER TABLE spaces DROP CONSTRAINT IF EXISTS fk_space_owner;

-- 2. Drop the owner_id column
ALTER TABLE spaces DROP COLUMN IF EXISTS owner_id;

-- 3. Add back the user_id column as a VARCHAR(255) to match your Zitadel IDs
ALTER TABLE spaces ADD COLUMN IF NOT EXISTS user_id VARCHAR(255);