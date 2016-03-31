package application;

import old.Show;

import java.sql.*;
import java.util.ArrayList;

/**
 * Database is a class that specifies the interface to the 
 * movie database. Uses JDBC and the MySQL Connector/J driver.
 */
public class Database {
    public static final int SUCCESS = 0;
    public static final int GENERAL_FAILURE = -1;
    public static final int NO_SEATS_FAILURE = -2;


    /** 
     * The database connection.
     */
    private Connection conn;
        
    /**
     * Create the database interface object. Connection to the database
     * is performed later.
     */
    public Database() {
        conn = null;
    }
        
    /** 
     * Open a connection to the database, using the specified user name
     * and password.
     *
     * @param userName The user name.
     * @param password The user's password.
     * @return true if the connection succeeded, false if the supplied
     * user name and password were not recognized. Returns false also
     * if the JDBC driver isn't found.
     */
    public boolean openConnection(String userName, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection 
                ("jdbc:mysql://puccini.cs.lth.se/" + userName,
                 userName, password);
        }
        catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
        catch (ClassNotFoundException e) {
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
        return true;
    }
        
    /**
     * Close the connection to the database.
     */
    public void closeConnection() {
        try {
            if (conn != null)
                conn.close();
        }
        catch (SQLException e) {
        	e.printStackTrace();
        }
        conn = null;
        
        System.err.println("Database connection closed.");
    }
        
    /**
     * Check if the connection to the database has been established
     *
     * @return true if the connection has been established
     */
    public boolean isConnected() {
        return conn != null;
    }

    public boolean login(String uname) {
        String sql = "select * from Users WHERE Users.userName = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, uname);
            ResultSet user = ps.executeQuery();
            return user.next();
        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<String> movieDates(String movieName) {
        try {
            String sql = "select performanceDate from Performance where Performance.movieName = ? order by performanceDate";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,movieName);
            ResultSet result = ps.executeQuery();
            ArrayList<String> movieDates = new ArrayList<>();
            while(result.next()) {
                movieDates.add(result.getString("performanceDate"));
            }
            return movieDates;
        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<String> movieTitles(){
        try {
            String sql = "select * from Movies order by movieName";
            PreparedStatement ps1 = conn.prepareStatement(sql);
            ResultSet result = ps1.executeQuery();
            ArrayList<String> movieList = new ArrayList<>();
            while(result.next()) {
               movieList.add(result.getString("movieName"));
            }
            return movieList;
        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



  	public Show getShowData(String mTitle, String mDate) {
        //Integer mFreeSeats = 42;
		//String mVenue = "Kino 2";
        try {
            String sql = "select * from Performance where Performance.movieName = ? and Performance.performanceDate = ? order by performanceDate";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,mTitle);
            ps.setString(2,mDate);
            ResultSet result = ps.executeQuery();
            result.next();
            return new Show(result.getString("movieName"),result.getString("performanceDate"),result.getString("theaterName"),result.getInt("remainingSeats"));
        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
            return new Show();
        }

	}

    public int bookTicket(String movie, String date,String uname) {
        int resultCode = GENERAL_FAILURE;
    try {
        conn.setAutoCommit(false);
        String check = "select remainingSeats from Performance where Performance.movieName = ? and Performance.performanceDate = ? for update;";
        PreparedStatement ch = conn.prepareStatement(check);
        ch.setString(1,movie);
        ch.setString(2,date);
        ResultSet seatCheck = ch.executeQuery();
        if (seatCheck.next() && seatCheck.getInt("remainingSeats") == 0 ) {
            resultCode = NO_SEATS_FAILURE; // indicate no available seats
            conn.rollback();
        } else {


            String updateSql = "update Performance set remainingSeats = remainingSeats - 1 where Performance.movieName = ? and Performance.performanceDate = ?;";
            PreparedStatement up = conn.prepareStatement(updateSql); // updates remainingSeats

            up.setString(1, movie);
            up.setString(2, date);
            up.executeUpdate();

            String sql = "INSERT INTO Ticket (userName,performanceDate,movieName) VALUES (?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, uname);
            ps.setString(2, date);
            ps.setString(3, movie);
            ps.executeUpdate();

            // ResultSet id = conn.prepareStatement("SELECT SCOPE_IDENTITY();").executeQuery();
            resultCode = SUCCESS;
        }
    } catch (SQLException e) {
        System.err.println(e);
        e.printStackTrace();
    } finally {
        try {
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultCode;
    }





    }

    /* --- TODO: insert more own code here --- */
}
