USE studentsdb;

-- Create the HighSchool_FriendShips table --
CREATE TABLE HighSchool_FriendShips  (
	-- friendship_id INT PRIMARY KEY AUTO_INCREMENT,
	student_id INT PRIMARY KEY AUTO_INCREMENT REFERENCES studentsdb.highschool(id),
    friend_id INT NOT NULL  REFERENCES studentsdb.highschool(id),
    other_friend_id INT NOT NULL REFERENCES studentsdb.highschool(id),
     CHECK (friend_id != other_friend_id)
);