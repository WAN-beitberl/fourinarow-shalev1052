package Student_Management;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.*;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class InsertData {

    static void applyData() {
        try{

        // Load the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Connect to the MySQL server
        String url = "jdbc:mysql://localhost:3306/studentsdb";
        String username = "root";
        String password = "13032004Akhu";

        String csvFilePath = "Student_Management\\highschool.csv";

        Connection conn = DriverManager.getConnection(url, username, password);

        // Insert data from highschool.csv into the students table
        String sql = "INSERT INTO highschool (first_name, last_name, email, gender, ip_address, cm_height, age, has_car, car_color, grade, grade_avg, identification_card) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
        String line;

            //skip the first line
            lineReader.readLine();
            // Iterate through the CSV records and insert the data into the database
            while ((line = lineReader.readLine()) != null) {
                String[] data = line.split(",");

                statement.setString(1, data[1]);
                statement.setString(2, data[2]);
                statement.setString(3, data[3]);
                statement.setString(4, data[4]);
                statement.setString(5, data[5]);
                statement.setInt(6, Integer.parseInt(data[6]));
                statement.setInt(7, Integer.parseInt(data[7]));
                statement.setBoolean(8, Boolean.parseBoolean(data[8]));
                statement.setInt(10, Integer.parseInt(data[10]));
                if(Boolean.parseBoolean(data[8]) == true) statement.setString(9, data[9]);
                else
                statement.setString(9,null);
                double gradeAvg = Double.parseDouble(data[11]);
                BigDecimal gradeAvgTruncated = new BigDecimal(gradeAvg).setScale(1, RoundingMode.HALF_UP);
                statement.setBigDecimal(11, gradeAvgTruncated);
                statement.setString(12, data[12]);
                statement.executeUpdate();       
            } 
   
            // Close the CSV parser and the database connection
                lineReader.close();
                statement.close();
                System.out.println("Data imported successfully!");  
                
            sql = "INSERT INTO HighSchool_FriendShips (friend_id, other_friend_id) VALUES (?, ?)";
            statement = conn.prepareStatement(sql);
            lineReader = new BufferedReader(new FileReader("Student_Management\\highschool_friendships.csv"));
            lineReader.readLine();
 
            while ((line = lineReader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length < 3) {
                    continue;
                }
                if (data[1] == "" || data[2] == "" ) {
                    continue;   
                }
                
                if (Integer.parseInt(data[1]) == Integer.parseInt(data[2]) ) {
                    continue;
                }

                statement.setInt(1, Integer.parseInt(data[1]));
                statement.setInt(2, Integer.parseInt(data[2]));
                statement.executeUpdate();
                
            }
            lineReader.close();
            statement.close();

             // Close the connection
             conn.close();
             System.out.println("Data imported successfully!");  

        }catch (Exception e) {    
            e.printStackTrace();
        }
    }
}
 




