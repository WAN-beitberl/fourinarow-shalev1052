USE studentsdb;

-- Create the HighSchool table --
CREATE TABLE HighSchool
(
    id INT  NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL ,
    gender VARCHAR(255)  NOT NULL,
	ip_address VARCHAR(255) UNIQUE NOT NULL ,
    cm_height INT NOT NULL,
    age INT NOT NULL CHECK (age >= 14 AND age <= 20),
    has_car BOOLEAN NOT NULL,
	car_color VARCHAR(255) ,
    grade INT NOT NULL,
	grade_avg decimal(3,1) NOT NULL,
    identification_card VARCHAR(255) UNIQUE NOT NULL ,
    CHECK (has_car = TRUE OR car_color IS NULL)
);
