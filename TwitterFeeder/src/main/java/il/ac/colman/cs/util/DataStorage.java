package il.ac.colman.cs.util;

import il.ac.colman.cs.ExtractedLink;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction layer for database access
 */
public class DataStorage {
    Connection conn;
    Statement statement;

    public DataStorage() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = System.getProperty("db_connection");
        conn = DriverManager.getConnection(url);
        statement = conn.createStatement();
        statement.setQueryTimeout(10);
    }

    /**
     * Add link to the database
     */
    public void addLink(ExtractedLink link, String track) throws SQLException {
        int linksAmount = 0;
        ResultSet countResult = conn.prepareStatement("SELECT COUNT(*) FROM websites").executeQuery();

        while (countResult.next()) {
            linksAmount = countResult.getInt(1);
        }

        // Delete the oldest link in case the DB is on max limit.
        if (linksAmount > Integer.parseInt(System.getProperty("max_links"))) {
            String deleteOldestQuery = "DELETE FROM websites ORDER BY tms ASC LIMIT 1";
            conn.prepareStatement(deleteOldestQuery).executeUpdate();
        }

        // Insert the link data to the DB.
        String sql = "INSERT INTO websites(url, title, content, description, tms, screenshotURL, track) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, link.getUrl());
        pstmt.setString(2, link.getTitle());
        pstmt.setString(3, link.getContent());
        pstmt.setString(4, link.getDescription());
        pstmt.setString(5, link.getTimestamp());
        pstmt.setString(6, link.getScreenshotURL());
        pstmt.setString(7, track);
        pstmt.executeUpdate();
    }

    /**
     * Search for a link
     *
     * @param query The query to search
     */
    public List<ExtractedLink> search(String query) throws SQLException {
        List<ExtractedLink> result = new ArrayList<ExtractedLink>();

        String searchQuery = "SELECT * FROM websites WHERE " +
                "url LIKE '%?%' OR " +
                "title LIKE '%?%' OR  " +
                "content LIKE '%?%' OR " +
                "description LIKE '%?%'";
        searchQuery = searchQuery.replace("?", query);

        ResultSet queryResult = conn.prepareStatement(searchQuery).executeQuery();

        while (queryResult.next()) {
            result.add(new ExtractedLink(
                    queryResult.getString(2), // url
                    queryResult.getString(3), // title
                    getShortContent(queryResult.getString(4)), // content
                    queryResult.getString(5), // description
                    queryResult.getString(6), // timestamp
                    queryResult.getString(7), // screenshotURL
                    queryResult.getString(8))); // track
        }

        return result;
    }

    public static String getShortContent(String content)
    {
        int maxLength = 5;

        if (content != null && content.length() > maxLength) {
            content = content.substring(0, maxLength + 1) + "...";
        }

        return content;
    }
}
