import java.sql.*;

public class Database {
    private static Connection con;
    //This method executes a query and returns the number of albums for the artist with name artistName
    public int getTitles(String artistName) {
        int titleNum = 0;
        //Implement this method
        //Prevent SQL injections!
        try {
            String sql = "SELECT COUNT(album.artistid) " +
                    "FROM artist, album WHERE artist.name = ? " +
                    "AND album.artistid = artist.artistid " +
                    "GROUP BY artist.artistid;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.clearParameters();
            pstmt.setString(1, artistName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                titleNum = rs.getInt("count");

            pstmt.close();
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return titleNum;
    }

    // This method establishes a DB connection & returns a boolean status
    public boolean establishDBConnection() {
        //Implement this method
        //5 sec timeout
        try{
            Class.forName("org.postgresql.Driver");
            DriverManager.setLoginTimeout(5);
            con = DriverManager.getConnection(Credentials.URL, Credentials.USERNAME, Credentials.PASSWORD);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}