package Student_Management;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.*;

public class program {
    public static void main(String[] args) {
        
        InsertData data = new InsertData();
        data.applyData();

        System.out.println("");
        System.out.println("");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("");
            System.out.println("==========================================================================");
            System.out.println("          - Wellcome to Student Management by Shalev Mashiah -");
            System.out.println("==========================================================================");

            System.out.println("");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("                         Please select an option:");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            System.out.println("1. Average school grades");
            System.out.println("2. Average grade of the boys");
            System.out.println("3. Average grades of the girls'");
            System.out.println("4. Height of products two meters tall and above, with purple car");
            System.out.println("5. Friends of a student (first and second circle)");
            System.out.println("6. Percentage of popular, regular, and lonely students");
            System.out.println("7. Grade point average of a student by ID card");
            System.out.println("8. Exit");

            int option = scanner.nextInt();

            if (option == 1) {
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                avgSchoolGrades();
            } else if (option == 2) {
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                avgMansGrades();
            } else if (option == 3) {
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                avgWomenGrades();
            } else if (option == 4) {
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                heightQuery();
            } else if (option == 5) {
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                getFriends();
            } else if (option == 6) {
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                studentPopularity();

            } else if (option == 7) {
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                RetrieveGPA();

            } else if (option == 8) {
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                System.out.println("Exiting program...");
                
                break;
            } else {
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                System.out.print("\033[H\033[2J");  
                System.out.flush();
                System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void avgSchoolGrades(){

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdb", "root", "13032004Akhu")) 
        {
            // Create a statement object
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT AVG(grade_avg) FROM highschool");

             // Print the average grade
             while (rs.next()) {
                System.out.println("The average grade is: " + rs.getDouble(1));
            }
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void avgMansGrades(){

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdb", "root", "13032004Akhu")) 
        {
            // Create a statement object
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT AVG(grade_avg) FROM highschool where gender = 'male'");

             // Print the average grade
             while (rs.next()) {
                System.out.println("The average of all the boys' grades in school is: " + rs.getDouble(1));
            }
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void avgWomenGrades(){

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdb", "root", "13032004Akhu")) 
        {
            // Create a statement object
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT AVG(grade_avg) FROM highschool where gender = 'female'");

             // Print the average grade
             while (rs.next()) {
                System.out.println("The average of all the grades of the girls in school is: " + rs.getDouble(1));
            }
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void heightQuery ()
    {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdb", "root", "13032004Akhu"))
        { 
             // Create the SQL query
             String query = "SELECT AVG(cm_height) FROM highschool " +
             "WHERE cm_height >= 200 AND car_color = 'purple'";

            // Create the statement
                Statement stmt = conn.createStatement();

                // Execute the query
                ResultSet result = stmt.executeQuery(query);

                // Print the result
                while (result.next()) {
                System.out.println("Grade Average: " + result.getDouble("AVG(cm_height)"));
                }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public static void getFriends()
    {
        Scanner input = new Scanner(System.in);

        // Prompt user for student ID
        System.out.print("Enter student ID: ");
        int studentID = input.nextInt();

        // Connect to database
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdb", "root", "13032004Akhu");

            // Get friends of first circle
            Set<Integer> friends = new HashSet<>();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT friend_id FROM highschool_friendships WHERE student_id = " + studentID);
            while (rs.next()) {
                friends.add(rs.getInt("friend_id"));
            }

            // Get friends of second circle
            Set<Integer> friendsOfFriends = new HashSet<>();
            for (int friend : friends) {
                rs = stmt.executeQuery("SELECT friend_id FROM highschool_friendships WHERE student_id = " + friend);
                while (rs.next()) {
                    friendsOfFriends.add(rs.getInt("friend_id"));
                }
            }

            // Print results
            System.out.println("Friends of student ID " + studentID + ":");
            for (int friend : friends) {
                System.out.println(friend);
            }
            System.out.println("Friends of friends of student ID " + studentID + ":");
            for (int friend : friendsOfFriends) {
                System.out.println(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void studentPopularity()
    {
    Connection conn = null;
    Statement stmt = null;
    try {
        // Connect to the database
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdb", "root", "13032004Akhu");
        stmt = conn.createStatement();

        // Count the total number of students
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM highschool");
        int totalStudents = 0;
        if (rs.next()) {
            totalStudents = rs.getInt(1);
        }

        // Count the number of popular students
        rs = stmt.executeQuery("SELECT COUNT(*) FROM highschool WHERE (SELECT COUNT(*) FROM highschool_friendships WHERE student_id = id) >= 2");
        int popularStudents = 0;
        if (rs.next()) {
            popularStudents = rs.getInt(1);
        }

        // Count the number of regular students
        rs = stmt.executeQuery("SELECT COUNT(*) FROM highschool WHERE (SELECT COUNT(*) FROM highschool_friendships WHERE student_id = id) = 1");
        int regularStudents = 0;
        if (rs.next()) {
            regularStudents = rs.getInt(1);
        }

        // Count the number of single students
        rs = stmt.executeQuery("SELECT COUNT(*) FROM highschool WHERE (SELECT COUNT(*) FROM highschool_friendships WHERE student_id = id) = 0");
        int singleStudents = 0;
        if (rs.next()) {
            singleStudents = rs.getInt(1);
        }

        // Calculate the percentages
        double popularPercent = (double) popularStudents / totalStudents * 100;
        double regularPercent = (double) regularStudents / totalStudents * 100;
        double singlePercent = (double) singleStudents / totalStudents * 100;

        // Print the results
        System.out.println("Popular students: " + popularPercent + "%");
        System.out.println("Regular students: " + regularPercent + "%");
        System.out.println("Single students: " + singlePercent + "%");
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    }

    private static void RetrieveGPA ()
    {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdb", "root", "13032004Akhu");
            Statement statement = con.createStatement();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the student's ID card: ");
            int id = scanner.nextInt();

            String query = "SELECT grade_avg FROM new_view WHERE identification_card = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                System.out.println("The grade point average for student with ID card " + id + " is " + resultSet.getDouble("grade_avg"));
            } else {
                System.out.println("No records found for student with ID card " + id);
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    } 
}