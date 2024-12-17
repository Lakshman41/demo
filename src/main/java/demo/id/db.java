package demo.id;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class db {
    public String url = "jdbc:postgresql://localhost:5432/demo";
    public String usename = "postgres";
    public String password = "123456";
    public Connection con;
    public Statement st;
    public Map<String, Object> questionMap;

    public void get(int id) {
        try {
            con = DriverManager.getConnection(url, usename, password);
            st = con.createStatement();
            String sql = String.format("SELECT * FROM questions WHERE ques_id='%s';", id);
            ResultSet rs = st.executeQuery(sql);
            
            if (rs.next()) {
                // Create a HashMap to store question details
                questionMap = new HashMap<>();
                
                // Add question and options to the map
                questionMap.put("question", rs.getString("question"));
                questionMap.put("Option A", rs.getString("opt_a"));
                questionMap.put("Option B", rs.getString("opt_b"));
                questionMap.put("Option C", rs.getString("opt_c"));
                questionMap.put("Option D", rs.getString("opt_d"));
            }
            
            // Close resources
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }
        catch(Exception e) {
            System.out.println("Error in get method: " + e.getMessage());
            // Reset questionMap in case of error
            questionMap = new HashMap<>();
        }
    }

    public void ans(int id, String response) {
        try {
            con = DriverManager.getConnection(url, usename, password);
            st = con.createStatement();
            String sql = String.format("SELECT * FROM questions WHERE ques_id='%s';", id);
            ResultSet rs = st.executeQuery(sql);
            
            if (rs.next()) {
                // Create a HashMap to store result details
                questionMap = new HashMap<>();
                
                String correctAnswer = rs.getString("answer");
                String result;
                
                if (correctAnswer.equals(response)) {
                    result = "Correct";
                    // Create map for correct answer scenario
                    questionMap.put("question", rs.getString("question"));
                    questionMap.put("result", result);
                    questionMap.put("Your response", response);
                }
                else {
                    result = "Incorrect";
                    // Create map for incorrect answer scenario
                    questionMap.put("question", rs.getString("question"));
                    questionMap.put("result", result);
                    questionMap.put("Your response", response);
                    questionMap.put("correct answer", correctAnswer);
                }
            }
            
            // Close resources
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }
        catch(Exception e) {
            System.out.println("Error in ans method: " + e.getMessage());
            // Reset questionMap in case of error
            questionMap = new HashMap<>();
        }
    }
}