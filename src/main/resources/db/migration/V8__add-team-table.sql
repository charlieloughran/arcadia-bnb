CREATE TABLE staff (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    role VARCHAR(255)
   );

INSERT INTO staff (name, role) VALUES
('Jon', 'Assistant Pot Wash'),
('Charlie', 'CEO');
