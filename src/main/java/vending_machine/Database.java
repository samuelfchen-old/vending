package vending_machine;

import java.sql.*;
import java.util.*;
// import vending_machine.snacks.*;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import vending_machine.snacks.*;
// import vending_machine.snacks.Snack;

public class Database {

    String filepath;

    public Database(String filepath) {

        this.filepath = filepath;
    }

    public void init() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Users (uid integer primary key, username string not null unique, password string not null, type string not null)";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Users values(0, 'admin', 'admin', 'owner');";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Users (username, password, type) values('anonymous', 'anonymous', 'customer');";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Users (username, password, type) values('seller', 'seller', 'seller');";
            statement.executeUpdate(sql);

            // Snacks
            sql = "CREATE TABLE IF NOT EXISTS Snacks (sid integer primary key, category string not null, name string not null unique, price float not null, quantity integer not null);";
            statement.executeUpdate(sql);

            // Transaction
            sql = "CREATE TABLE IF NOT EXISTS Snack_Transaction (tid integer primary key, uid integer, cost float not null, change_given float not null, payment_type string not null, time text not null, FOREIGN KEY(uid) REFERENCES Users(uid))";
            statement.executeUpdate(sql);

            // Purchase
            sql = "CREATE TABLE IF NOT EXISTS Purchase (pid integer primary key, uid integer, tid integer, sid integer, FOREIGN KEY(uid) REFERENCES Users(uid), FOREIGN KEY(tid) REFERENCES Snack_Transaction(tid), FOREIGN KEY(sid) REFERENCES Snacks(sid))";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Currency (value integer primary key, amount integer)";
            statement.executeUpdate(sql);
            addCurrency();

            // Cards
            sql = "CREATE TABLE IF NOT EXISTS Cards (cid integer primary key, name string not null, number integer not null);";
            statement.executeUpdate(sql);

            // Cringe
            sql = "insert or ignore into Cards (name, number) values('Charles', 40691);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Sergio', 42689);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Kasey', 60146);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Vincent', 59141);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Ruth', 55134);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Donald', 23858);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Christine', 35717);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Helene', 72500);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Brian', 44756);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Wanda', 97523);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Elaine', 48685);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Blake', 14138);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Debbie', 92090);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Felix', 31093);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('John', 90669);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Deena', 95953);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Joan', 77852);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Kenneth', 60632);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Audrey', 45925);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Francisco', 27402);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Christopher', 28376);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Manuel', 53477);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Mark', 66192);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('William', 67707);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Rebecca', 54981);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Arthur', 41696);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Robert', 85202);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Christopher', 87286);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Edwin', 23842);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Stacey', 26436);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Michael', 24531);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Janet', 69655);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Jeremy', 74061);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Patricia', 30690);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Julie', 56907);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Linda', 38409);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Evelyn', 64820);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Liana', 75183);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Simone', 89037);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Jeffrey', 98708);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Elizabeth', 96667);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Andy', 82050);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Chad', 34572);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('James', 33527);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Ruby', 78073);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Naomi', 43114);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('James', 20565);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Leonard', 72238);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Marguerite', 30831);";
            statement.executeUpdate(sql);
            sql = "insert or ignore into Cards (name, number) values('Maxine', 34402);";
            statement.executeUpdate(sql);


            // Saved Cards
            sql = "CREATE TABLE IF NOT EXISTS Saved_Cards (uid integer primary key, cid integer not null, FOREIGN KEY(uid) REFERENCES Users(uid), FOREIGN KEY(cid) REFERENCES Cards(cid))";
            statement.executeUpdate(sql);

            // Add basic snacks
            // if (!(
            //     addSnack("Snickers", "chocolate", 2.50f, 7) &&
            //     addSnack("Mars", "chocolate", 2.45f, 7) &&
            //     addSnack("M&M", "chocolate", 2.3f, 7) &&
            //     addSnack("Bounty", "chocolate", 2f, 7) &&

            //     addSnack("Mineral Water", "drink", 3f, 7) &&
            //     addSnack("Sprite", "drink", 4.5f, 7) &&
            //     addSnack("Coca Cola", "drink", 4f, 7) &&
            //     addSnack("Pepsi", "drink", 3.5f, 7) &&
            //     addSnack("Juice", "drink", 3.25f, 7) &&

            //     addSnack("Smiths", "chip", 3f, 7) &&
            //     addSnack("Pringles", "chip", 3.2f, 7) &&
            //     addSnack("Kettle", "chip", 4f, 7) &&
            //     addSnack("Thins", "chip", 2.75f, 7) &&

            //     addSnack("Mentos", "candy", 1.5f, 7) &&
            //     addSnack("Sour Patch", "candy", 2f, 7) &&
            //     addSnack("Skittles", "candy", 2.2f, 7)
            // )) {
            //     System.out.println("Error adding snack info");
            // }

            addSnack("Snickers", "chocolate", 2.50f, 7);
            addSnack("Mars", "chocolate", 2.45f, 7);
            addSnack("M&M", "chocolate", 2.3f, 7);
            addSnack("Bounty", "chocolate", 2f, 7);

            addSnack("Mineral Water", "drink", 3f, 7);
            addSnack("Sprite", "drink", 4.5f, 7);
            addSnack("Coca Cola", "drink", 4f, 7);
            addSnack("Pepsi", "drink", 3.5f, 7);
            addSnack("Juice", "drink", 3.25f, 7);

            addSnack("Smiths", "chip", 3f, 7);
            addSnack("Pringles", "chip", 3.2f, 7);
            addSnack("Kettle", "chip", 4f, 7);
            addSnack("Thins", "chip", 2.75f, 7);

            addSnack("Mentos", "candy", 1.5f, 7);
            addSnack("Sour Patch", "candy", 2f, 7);
            addSnack("Skittles", "candy", 2.2f, 7);


        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public User doLogin(String username, String password) {

        Connection connection = null;
        User toReturn = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "select * from Users where username = ? and password = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                toReturn = new User(rs.getInt("uid"), rs.getString("username"), rs.getString("type"));
            }

        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();

                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return toReturn;
    }

    public String checkUsername(String username) {
        Connection connection = null;
        String toReturn = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "select * from Users where username = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                toReturn = rs.getString("username");
            }

        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();

                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return toReturn;
    }

    public boolean removeUser(String username) {
        Connection connection = null;
        try {
            if (username.equals("admin") || username.equals("anonymous")) {
                return false;
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = String.format("delete from Users where username = ?;");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.executeUpdate();

            
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    public boolean createAccount(String username, String password, String accountType) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "insert into Users (username, password, type) values(?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, accountType);
            statement.executeUpdate();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();

                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    public List<User> getUsers() {
        Connection connection = null;
        List<User> users = new ArrayList<>();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "select * from Users;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // uid, username, password, type
                User u = new User(rs.getInt("uid"), rs.getString("username"), rs.getString("type"));
                users.add(u);
            }


        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();

                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return users;
    }

    public boolean addCurrency() {
        int[] values = {5000, 2000, 1000, 500, 200, 100, 50, 20, 10, 5};
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            for (int value : values) {
                String sql = String.format("insert or ignore into Currency values(%d, 5)", value);
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
            }

            
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    public boolean updateCurrency(Map<Integer, Integer> payment, boolean add) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            for (Map.Entry<Integer, Integer> input : payment.entrySet()) {
                int val = input.getValue();
                String sql = String.format("select amount from Currency where value = ?;");
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, input.getKey());
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    if (add) {
                        val += rs.getInt("amount");
                    } else {
                        val = rs.getInt("amount") - val;
                    }
                    
                }
                sql = String.format("update Currency set amount = ? where value = ?;");
                statement = connection.prepareStatement(sql);
                statement.setInt(1, val);
                statement.setInt(2, input.getKey());
                statement.executeUpdate();
            }

            
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    public TreeMap<Integer, Integer> getCurrency() {
        Connection connection = null;
        TreeMap<Integer, Integer> currencies = new TreeMap<>();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "select * from Currency;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                currencies.put(rs.getInt("value"), rs.getInt("amount"));
            }
            
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return currencies;
    }

    /**
     * Adds a snack to the databse
     * @param name
     * @param category
     * @param price
     * @param quantity
     * @return true on success, false on failure
     */
    public boolean addSnack(String name, String category, float price, double quantity) {
        // Null check
        if (name == null || category == null) {
            System.out.println("Error adding snack: null values");
            return false;
        }

        // Price validity check
        if (price <= 0) {
            System.out.println("Error adding snack: invalid price");
            return false;
        }

        // Quantity validity check
        if (quantity < 0) {
            System.out.println("Error adding snack: invalid quantity");
            return false;
        }

        // Category validity check
        switch (category) {
            case "candy":
                break;
            case "chip":
                break;
            case "chocolate":
                break;
            case "drink":
                break;
            default:
                System.out.println("Error adding snack: invalid category");
                return false;
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "insert or ignore into Snacks (name, category, price, quantity) values(?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, category);
            statement.setString(3, String.valueOf(price));
            statement.setString(4, String.valueOf(quantity));

            statement.executeUpdate();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    /**
     * Searches database for a snack of a particular name
     * @param name snack name
     * @return Snack object, null if not found
     */
    public Snack getSnack(String name) {
        Connection connection = null;
        Snack toReturn = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "select * from Snacks where name = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                switch (rs.getString("category")) {
                    case "candy":
                        toReturn = new Candies(name, rs.getFloat("price"), rs.getInt("quantity"));
                        break;
                    case "chip":
                        toReturn = new Chips(name, rs.getFloat("price"), rs.getInt("quantity"));
                        break;
                    case "chocolate":
                        toReturn = new Chocolates(name, rs.getFloat("price"), rs.getInt("quantity"));
                        break;
                    case "drink":
                        toReturn = new Drinks(name, rs.getFloat("price"), rs.getInt("quantity"));
                        break;
                }
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();

                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return toReturn;
    }

    public int getSnackId(String name) {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "SELECT sid FROM Snacks WHERE name = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return rs.getInt("sid");
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return -1;

    }

    public boolean changeSnackQuantity(int sid, int quantity) {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "update Snacks set quantity =  ? where sid = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setInt(2, sid);
            statement.executeUpdate();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    public boolean changeSnackPrice(int sid, double price) {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "update Snacks set price =  ? where sid = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, price);
            statement.setInt(2, sid);
            statement.executeUpdate();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    /*Purchase function decrement quantiity of Snack by amount, then create a purchase entry. All the different purchases at same time make up a transation
        here
    */
    public boolean addTransaction(int uid, double cost, double change, String paymentType) {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "insert into Snack_Transaction (uid, cost, change_given, payment_type, time) values(?, ?, ?, ?, datetime('now'));";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, uid);
            statement.setDouble(2, cost);
            statement.setDouble(3, change);
            statement.setString(4, paymentType);
            statement.executeUpdate();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    public int getCurrentTransactionID() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "SELECT MAX(tid) as tid FROM Snack_Transaction;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()) {
                return rs.getInt("tid");
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return -1;
    }

    public boolean addPurchase(int uid, int tid, int sid) {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "insert into Purchase (uid, tid, sid) values(?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, uid);
            statement.setInt(2, tid);
            statement.setInt(3, sid);
            statement.executeUpdate();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    return true;
    }

    public Map<String, List<Snack>> getAllSnacks() {
        Map<String, List<Snack>> toReturn = new HashMap<>();
        toReturn.put("chocolate", new ArrayList<>());
        toReturn.put("drink", new ArrayList<>());
        toReturn.put("candy", new ArrayList<>());
        toReturn.put("chip", new ArrayList<>());

        // Get a list of all the snack names
        Connection connection = null;
        List<String> snackNames = new ArrayList<>();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "select name from Snacks;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                snackNames.add(rs.getString("name"));
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        // Populate map
        for (String name : snackNames) {
            Snack snack = getSnack(name);

            toReturn.get(snack.getCategory()).add(snack);
        }
        return toReturn;
    }

    public List<Snack> getRecentSnacks(int uid) {
        List<Snack> toReturn = new ArrayList<>();
        List<String> names = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "select distinct name from Users join Purchase using (uid) join Snacks using (sid) where uid = ? order by pid desc limit 5;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, uid);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        // Populate map
        for (String name : names) {
            Snack snack = getSnack(name);
            toReturn.add(snack);
        }
        return toReturn;
    }

    public int checkCard(String name, int number) {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "select * from Cards where name = ? and number = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, number);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return rs.getInt("cid");
            }

        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();

                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return -1;
    }
    

    public boolean saveCard(int uid, int cid) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "insert or replace into Saved_Cards (uid, cid) values(?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, uid);
            statement.setInt(2, cid);
            statement.executeUpdate();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();

                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    public List<String> getCard(int uid) {
        Connection connection = null;
        try {
            List<String> toReturn = new ArrayList<>();
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "select name, number from Saved_Cards join Cards where uid = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, uid);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                toReturn.add(rs.getString("name"));
                toReturn.add(rs.getString("number"));
                return toReturn;
            }

        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();

                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public void getCancelledTransactions() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "select username, time, payment_type from Snack_Transaction join Users using (uid) where payment_type != 'card' and payment_type != 'cash';";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String resourcePath = null;

            switch(System.getProperty("os.name")) {
                case "Linux":
                    resourcePath = "";
                    break;
                default:
                    resourcePath = "Documents/";
                    break;
            }

            String path = String.format("/%s/%svending_machine/CancelledTransactions.txt", System.getProperty("user.home"), resourcePath);

            // Resetting file before appending contents again
            try {
                FileWriter wiper = new FileWriter(path);
                wiper.write("");
                wiper.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            while(rs.next()) {
                try {
                    FileWriter myWriter = new FileWriter(path, true);
                    BufferedWriter bw = new BufferedWriter(myWriter);
                    PrintWriter post = new PrintWriter(bw);
                    post.println(rs.getString("username") + " " + rs.getString("time") + " " + rs.getString("payment_type"));
                    post.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public boolean sellerUpdateCurrency(int val, int quantity)
    {
        Connection connection = null;
        try {

            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);

            String sql = String.format("update Currency set amount = ? where value = ?;");


            PreparedStatement statement;
            statement = connection.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setInt(2, val);
            statement.executeUpdate();
        }


         catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return true;
    }

    public void TransactionsRecord() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "select * from Snack_Transaction where cost != 0.0";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            String resourcePath = null;
            switch(System.getProperty("os.name")) {
                case "Linux":
                    resourcePath = "";
                    break;
                default:
                    resourcePath = "Documents/";
                    break;
            }
            String path = String.format("/%s/%svending_machine/Transactions.txt", System.getProperty("user.home"), resourcePath);
            
            // Resetting transaction records before rewriting
            try {
                FileWriter wiper = new FileWriter(path);
                wiper.write("");
                wiper.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(rs.next()) {
                try {
                    FileWriter myWriter = new FileWriter(path, true);
                    myWriter.write(rs.getString("tid") + " " + rs.getString("cost") + " " + rs.getString("change_given") + " " + rs.getString("payment_type") + " " + rs.getString("time") + "\n");
                    myWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void writeUsers() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "select username, type from Users;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            String resourcePath = null;
            switch(System.getProperty("os.name")) {
                case "Linux":
                    resourcePath = "";
                    break;
                default:
                    resourcePath = "Documents/";
                    break;
            }
            String path = String.format("/%s/%svending_machine/Users.txt", System.getProperty("user.home"), resourcePath);
            
            // Resetting transaction records before rewriting
            try {
                FileWriter wiper = new FileWriter(path);
                wiper.write("");
                wiper.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(rs.next()) {
                try {
                    FileWriter myWriter = new FileWriter(path, true);
                    myWriter.write(rs.getString("username") + " " + rs.getString("type") + "\n");
                    myWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    /**
     * Seller stuff
     */

    public void AvailableRecord() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "select * from Snacks where quantity > 0";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            String resourcePath = null;
            switch(System.getProperty("os.name")) {
                case "Linux":
                    resourcePath = "";
                    break;
                default:
                    resourcePath = "Documents/";
                    break;
            }
            String path = String.format("/%s/%svending_machine/available.txt", System.getProperty("user.home"), resourcePath);
            
            // Resetting transaction records before rewriting
            try {
                FileWriter wiper = new FileWriter(path);
                wiper.write("");
                wiper.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(rs.next()) {
                try {
                    FileWriter myWriter = new FileWriter(path, true);
                    myWriter.write(rs.getString("sid") + ": " + rs.getString("name") + " | Quantity: " + rs.getString("quantity") + " Price: $" + rs.getString("price") + "\n");
                    myWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void SoldRecord() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.filepath);
            String sql = "select sid, name, COUNT(pid) as sold from Snacks NATURAL JOIN Purchase GROUP BY sid";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            String resourcePath = null;
            switch(System.getProperty("os.name")) {
                case "Linux":
                    resourcePath = "";
                    break;
                default:
                    resourcePath = "Documents/";
                    break;
            }
            String path = String.format("/%s/%svending_machine/sold.txt", System.getProperty("user.home"), resourcePath);
            
            // Resetting transaction records before rewriting
            try {
                FileWriter wiper = new FileWriter(path);
                wiper.write("");
                wiper.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(rs.next()) {
                try {
                    FileWriter myWriter = new FileWriter(path, true);
                    myWriter.write(rs.getString("sid") + ": " + rs.getString("name") + " | " + rs.getString("sold") + " sold\n");
                    myWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}

