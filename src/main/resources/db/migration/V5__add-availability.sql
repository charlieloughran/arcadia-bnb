ALTER TABLE spaces ADD availability_start TIMESTAMP;
ALTER TABLE spaces ADD availability_end TIMESTAMP;


UPDATE spaces SET availability_start = '2024-06-23 00:00:00' WHERE availability_start IS NULL;
UPDATE spaces SET availability_end = '2025-06-23 23:59:59' WHERE availability_end IS NULL;

