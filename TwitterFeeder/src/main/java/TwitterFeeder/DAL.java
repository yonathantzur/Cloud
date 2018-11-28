package TwitterFeeder;

import java.sql.*;
import java.util.Date;

public class DAL {

    private Connection connection = null;
    private Statement statement = null;

    public DAL() throws SQLException {
        this.InitDB();
    }

    public void InitDB() throws SQLException {
        OpenConnection();
        statement.executeUpdate("drop table if exists websites");
        statement.executeUpdate("create table websites (id INTEGER PRIMARY KEY AUTOINCREMENT, link STRING, tweetId LONG, title STRING, content STRING, tms DATE, screenshot STRING )");
        CloseConnection();
    }

    public void InsertWebsite(String link, Long tweetId, String title, String content, Date date, String screenshot) {
        try {
            OpenConnection();
            String sql = "insert into websites(link, tweetId, title, content, tms, screenshot) values(?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, link);
            pstmt.setLong(2, tweetId);
            pstmt.setString(3, title);
            pstmt.setString(4, content);
            pstmt.setDate(5, new java.sql.Date(date.getTime()));
            pstmt.setString(6, screenshot);
            pstmt.executeUpdate();
            CloseConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void OpenConnection() throws SQLException {
        // create a database connection
        connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/TwitterFeeder.db");
        statement = connection.createStatement();
        statement.setQueryTimeout(10);
    }

    public void CloseConnection() {
        try
        {
            if(connection != null)
                connection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void PrintDBToConsole() {
        try {
            OpenConnection();
            ResultSet rs = statement.executeQuery("select * from websites");

            System.out.println("\n\n\n----------------------Websites-----------------\n");
            while(rs.next())
            {
                System.out.println("*********************************************");
                // read the result set
                System.out.println("link = " + rs.getString("link"));
                System.out.println("tweet Id = " + rs.getLong("tweetId"));
                System.out.println("title = " + rs.getString("title"));
                System.out.println("content = " + rs.getString("content"));
                System.out.println("timestamp = " + rs.getDate("tms"));
                System.out.println("*********************************************");
            }
            System.out.println("\n----------------------Websites-----------------");

            CloseConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}