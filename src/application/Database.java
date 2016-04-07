package application;

import javafx.scene.control.Label;
import javafx.scene.text.Text;
import old.Show;

import javax.xml.parsers.FactoryConfigurationError;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

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
        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
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
        } catch (SQLException e) {
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


    public ArrayList<String> getRecipes() {
        try {
            String sql = "select * from Recipes";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            ArrayList<String> allRecipes = new ArrayList<>();
            while (result.next()) {
                allRecipes.add(result.getString("recipeName"));
            }
            return allRecipes;
        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean blockPallets(ArrayList<String> pallet_ids){
        boolean failure = false;
        try {
            conn.setAutoCommit(false);

            for (String pallet_id : pallet_ids) {

                String sql = "select blockForDelivery from Pallets where palletID = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(pallet_id));
                ResultSet result = ps.executeQuery();
                if(result.next()){
                    if(!result.getBoolean("blockForDelivery")){
                        String updateSql = "update Pallets set blockForDelivery = ? where palletID = ?";
                        PreparedStatement up = conn.prepareStatement(updateSql); // updates remainingSeats

                        up.setBoolean(1, true);
                        up.setString(2, pallet_id);
                        if (up.executeUpdate() == 0){
                            failure = true;
                            System.out.println("Database.blockPallet");
                            conn.rollback();
                            break;
                        }
                    }
                }


            }



        } catch (SQLException e) {
            failure = true;
            e.printStackTrace();
        }finally {
            try {
                conn.commit();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                failure = true;
                e.printStackTrace();
            }
        }
        return failure;

    }

    public ArrayList<String> getPallets_filtered(String delivery_date, int customer_Id, String start_Date, String end_Date, String recipe, boolean blocked) {
        try {
            System.out.println("customer_Id = [" + customer_Id + "], start_Date = [" + start_Date + "], end_Date = [" + end_Date + "], recipe = [" + recipe + "]");

            int offset = 3;
            String sql;

            boolean haveDelivDate = delivery_date != null && !delivery_date.isEmpty();
            boolean haveRecipe = recipe != null && !recipe.isEmpty();
            boolean havecustomerId = customer_Id > 0 ;
            

            sql = "select palletID from Pallets where timestampBaking between ? and ?";
            if (haveDelivDate) {
                sql += " and timestampDelivery = ?";
            }

            if (haveRecipe) {
                sql += " and recipeName = ?";
            }

            if (havecustomerId) {
                sql += " and customerID = ?";
            }

            if (blocked) {
                sql += " and blockForDelivery = 1";
            }


            PreparedStatement ps = conn.prepareCall(sql);

            ps.setString(1, start_Date);
            ps.setString(2, end_Date);

            if (haveDelivDate) {
                ps.setString(offset, delivery_date);
                offset++;
            }

            if (haveRecipe) {
                ps.setString(offset, recipe);
                offset++;
            }

            if (havecustomerId) {
                ps.setInt(offset, customer_Id);
            }

            System.out.println("sql = " + sql);
            ResultSet result = ps.executeQuery();
            ArrayList<String> palletList = new ArrayList<>();
            System.out.println("hej");
            while (result.next()) {
                palletList.add(result.getString("palletID"));
            }
            return palletList;
        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    final static String DATE_FORMAT = "yyyy-MM-dd";

    public static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public void update_review_panel(String pallet_id, Label recipe, Text customer_id, Text location, Text blocked, Text bake_date, Text deliv_date){
        String sql = "select * from Pallets where palletID = ?";
        String sql_customer = "select name from Customers where customerID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pallet_id);
            ResultSet result = ps.executeQuery();

            if(result.next()){

                PreparedStatement ps1 = conn.prepareStatement(sql_customer);
                ps1.setInt(1,result.getInt("customerID"));
                ResultSet result1 = ps1.executeQuery();

                if(result1.next()){

                    recipe.setText(result.getString("recipeName"));
                    customer_id.setText(result1.getString("name"));
                    location.setText(result.getString("location"));
                    blocked.setText(result.getBoolean("blockForDelivery") ? "Yes" : "No");
                    bake_date.setText(result.getDate("timestampBaking").toString());
                    deliv_date.setText((result.getDate("timestampDelivery") != null) ? result.getDate("timestampDelivery").toString() : "---");

                }

            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deliverPallet(String deliv_date, int pallet_id) {
        //Integer mFreeSeats = 42;
        //String mVenue = "Kino 2";
        boolean delivered = false;
        try {
            String sql = "select * from Pallets where palletID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pallet_id);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                if(result.getDate("timestampDelivery") == null){
                    if((!result.getBoolean("blockForDelivery") && isDateValid(deliv_date.trim()) && result.getDate("timestampBaking").compareTo(Date.valueOf(deliv_date.trim())) < 0)){
                        String updateSql = "update Pallets set timestampDelivery = ? where palletID = ?";
                        PreparedStatement up = conn.prepareStatement(updateSql);

                        up.setString(1, deliv_date.trim());
                        up.setInt(2, pallet_id);
                        up.executeUpdate();
                        delivered = true;
                    }

                }
            }


        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return delivered;
    }

    public void createPallet(int customerID, String deliveryDate, String productionDate,Boolean blockedStatus,String currentLocation,String currentRecipe) {
        boolean failure = false;
        try {
            conn.setAutoCommit(false);

            String sql_ingredients = "select * from Ingredients where recipeName = ?";
            PreparedStatement ps_ingr = conn.prepareStatement(sql_ingredients);
            ps_ingr.setString(1,currentRecipe);
            ResultSet rs_ingr = ps_ingr.executeQuery();

            while(rs_ingr.next()){
                String sql = "select currentAmount from RawMaterials where materialName = ?";
                PreparedStatement ps_rawm = conn.prepareCall(sql);
                ps_rawm.setString(1,rs_ingr.getString("materialName"));
                ResultSet rs_rawm = ps_rawm.executeQuery();
                if (rs_rawm.next()){
                    if (rs_rawm.getDouble("currentAmount") >= rs_ingr.getDouble("amount")){

                        String updateSql = "update RawMaterials set currentAmount = currentAmount - ? where materialName = ?;";
                        PreparedStatement up = conn.prepareStatement(updateSql); // updates remainingSeats

                        up.setDouble(1, rs_ingr.getDouble("amount"));
                        up.setString(2, rs_ingr.getString("materialName"));
                        up.executeUpdate();

                    } else {
                        failure = true;
                        System.out.println("Database.createPallet");
                        conn.rollback();
                        break;
                    }
                }

            }

            if (!failure){
                String sql = "INSERT INTO Pallets (customerID,recipeName,location,timestampBaking,blockForDelivery,timestampDelivery) VALUES (?, ?, ?, ?, ?, ?);";
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setInt(1, customerID);
                ps.setString(2, currentRecipe);
                ps.setString(3, currentLocation);
                ps.setDate(4, Date.valueOf(productionDate));
                ps.setBoolean(5, blockedStatus);

                if (!blockedStatus && deliveryDate != null && !deliveryDate.isEmpty()){
                    ps.setDate(6, Date.valueOf(deliveryDate));
                } else {
                    ps.setDate(6, null);
                }

                ps.executeUpdate();
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
        }


    }




    public int bookTicket(String movie, String date, String uname) {
        int resultCode = GENERAL_FAILURE;
        try {
            conn.setAutoCommit(false);
            String check = "select remainingSeats from Performance where Performance.movieName = ? and Performance.performanceDate = ? for update;";
            PreparedStatement ch = conn.prepareStatement(check);
            ch.setString(1, movie);
            ch.setString(2, date);
            ResultSet seatCheck = ch.executeQuery();
            if (seatCheck.next() && seatCheck.getInt("remainingSeats") == 0) {
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

    public boolean customerNotRegistered(Integer customerID) {
        try {
            String sql = "select * from Customers where customerID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                return false;
            }

        } catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return true;
    }

    /* --- TODO: insert more own code here --- */
}
