CREATE TABLE bookings (
           id SERIAL PRIMARY KEY,
           bookedFrom TIMESTAMP,
           bookedUntil TIMESTAMP,
           user_id VARCHAR(255),
           space_id int,
            CONSTRAINT fk_space
            FOREIGN KEY (space_id)
            REFERENCES Spaces(id)
           );