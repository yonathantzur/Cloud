package il.ac.colman.cs;
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
        statement.executeUpdate("create table websites (id INTEGER PRIMARY KEY AUTOINCREMENT, link STRING, tweetId LONG, title STRING, content STRING, tms DATE )");
        CloseConnection();
    }

    public void InsertWebsite(String link, Long tweetId, String title, String content, Date date) throws SQLException {
        OpenConnection();
        String sql = "insert into websites(link, tweetId, title, content, tms) values(?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, link);
            pstmt.setLong(2, tweetId);
            pstmt.setString(3, title);
            pstmt.setString(4, content);
            pstmt.setDate(5, new java.sql.Date(date.getTime()));
            pstmt.executeUpdate();
        CloseConnection();
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
            // connection close failed.
            System.err.println(e);
        }
    }
}

//        ResultSet rs = statement.executeQuery("select * from websites");
//        while(rs.next())
//        {
//            // read the result set
//            System.out.println("name = " + rs.getString("name"));
//            System.out.println("id = " + rs.getInt("id"));
//        }