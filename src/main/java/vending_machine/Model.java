package vending_machine;

import java.sql.ResultSet;
import java.util.*;
import vending_machine.snacks.*;

public class Model {

    User user;
    Database db;

    public Model(Database db){
        this.user = null;
        this.db = db;
    };

    public ModelResponse checkLogin(String username, String password) {
        
        User login = db.doLogin(username, password);
        if(login == null) {
            return new ModelResponse(false, "Username/Password did not match!");
        }
        this.user = login;
        return new ModelResponse(true);
    }

    public ModelResponse checkUsername(String username) {
        String valid = db.checkUsername(username);
        if (valid != null) {
            if (!username.equals(user.getName()) || !username.equals("admin")) {
                if (username.equals("anonymous")) {
                    return new ModelResponse(false, "Cannot remove anonymous access!");
                }
                db.removeUser(username);
                return new ModelResponse(true, "Successfully removed user!");
            } else {
                return new ModelResponse(false, "Cannot remove yourself!");
            }
        } else {
            return new ModelResponse(false, "No user found!");
        }
    }

    public ModelResponse createAccount(String username, String password, String password2, String accountType) {
        if(!password.equals(password2)) {
            return new ModelResponse(false, "Passwords did not match!");
        }
        if(password.length() < 4) {
            return new ModelResponse(false, "Password should be at least 5 characters!");
        }
        if(this.db.createAccount(username, password, accountType)) {
            return new ModelResponse(true);
        }
        return new ModelResponse(false, "That username is already taken!");
    }

    public ModelResponse getSnackInfo() {
        Map<String, List<Snack>> toReturn = db.getAllSnacks();

        return new ModelResponse(true, "Snacks fetched successfully", toReturn);
    }

    public ModelResponse getSingleSnackInfo(String snackName) {
        Snack snack = null;
        snack = db.getSnack(snackName);

        if (snack == null) {
            return new ModelResponse(false);
        }

        return new ModelResponse(true, "Snack fetched successfully", snack);
    }

    public ModelResponse getRecentSnacks(int uid) {
        List<Snack> toReturn = db.getRecentSnacks(uid);
        return new ModelResponse(true, "Snacks fetched successfully", toReturn);
    }

    public double getCost(Map<Snack, Integer> snacks) {
        double cost = 0.00;
        for(Map.Entry<Snack, Integer> entry: snacks.entrySet()) {
            cost += (entry.getKey().getPrice() * entry.getValue());
        }
        return cost; 
    }

    // TODO
    public ModelResponse cashTransaction(Map<Snack, Integer> snacks, Map<Integer, Integer> payment, int changeAmount) {
        db.updateCurrency(payment, true);
        // now time to deal with change aiya

        // Log transaction, and update snack amounts
        double cost = this.getCost(snacks);

        db.addTransaction(this.user.getId(), cost, changeAmount, "cash");


        int tid = db.getCurrentTransactionID();

        for(Map.Entry<Snack, Integer> entry: snacks.entrySet()) {
            if(entry.getValue() > 0) {
                int sid = db.getSnackId(entry.getKey().getName());
                db.addPurchase(this.user.getId(), tid, sid);
                db.changeSnackQuantity(sid, entry.getKey().getQuantity() - entry.getValue());
            }
        } 

        return getChange(changeAmount);
    }

    // Please pass a map of Snacks as keys with the the number requested to buy as values
    public ModelResponse cardTransaction(Map<Snack, Integer> snacks, String name, int number, int saveCard) {

        // Quantity check is handled by GUI
        double cost = this.getCost(snacks);

        int cid = db.checkCard(name, number);
        if(cid == -1) {
            return new ModelResponse(false, "Invalid card details!");
        }
        if(saveCard == 0) {
            db.saveCard(this.user.getId(), cid);
        }

        db.addTransaction(this.user.getId(), cost, 0.00, "card");

        int tid = db.getCurrentTransactionID();

        for(Map.Entry<Snack, Integer> entry: snacks.entrySet()) {
            if(entry.getValue() > 0) {
                int sid = db.getSnackId(entry.getKey().getName());
                db.addPurchase(this.user.getId(), tid, sid);
                db.changeSnackQuantity(sid, entry.getKey().getQuantity() - entry.getValue());
            }
        } 

        return new ModelResponse(true);
    }

    // TODO by someone: Add change table to DB, create change calculation method (probably return a map of change or null if not possible), 
    // create cashTransaction method (removing cash from db for change and adding cash to db from payment, return response if change not available etc.)


    // 
    public ModelResponse getChange(int change) {
        if (change == 0) {
            return new ModelResponse(true, "no change provided", null);
        }

        TreeMap<Integer, Integer> currencies = db.getCurrency();

        TreeMap<Integer, Integer> ret = new TreeMap<>();

        Map<Integer, Integer> reverse = currencies.descendingMap();

        // Set<Integer> keys = currencies.keySet(); 

        for (Map.Entry<Integer, Integer> entry : reverse.entrySet()) {

            int value = entry.getKey();
            int stock = entry.getValue();
            // System.out.println(value);

            while (stock > 0 && change >= value) {
                change -= value;
                stock--;
                reverse.put(value, stock);
                if (ret.containsKey(value)) {
                    ret.put(value, ret.get(value)+1);
                } else {
                    ret.put(value, 1);
                }
                
            }

        }

        if (change != 0) {
            return new ModelResponse(false, "not enough stored currency, please use exact change");
        }

        db.updateCurrency(ret, false);



        return new ModelResponse(true, "change successful", ret);
    }

    public List<String> getCard() {
        List<String> cardInfo = db.getCard(this.user.getId());
        return cardInfo;

    }

    public void writeCancelledTransactions() {
       db.getCancelledTransactions(); 
    }

    public void writeTransactions()
    {
        db.TransactionsRecord();
    }

    public void writeUsers() {
        db.writeUsers();
    }
    /**
     * Seller stuff
     */

    public ModelResponse updatePrice(String snackName, double newPrice) {
        db.changeSnackPrice(db.getSnackId(snackName), newPrice);
        return new ModelResponse(true);
    }

    public ModelResponse updateStock(String snackName, int newStock) {
        db.changeSnackQuantity(db.getSnackId(snackName), newStock);
        return new ModelResponse(true);
    }

    public ModelResponse addSnack(String snackName, String category) {
        if (db.addSnack(snackName, category, 0.05f, 0)) {
            return new ModelResponse(true);
        } else {
            return new ModelResponse(false, "snack add error");
        }
    }

    public void writeAvailable()
    {
        db.AvailableRecord();
    }

    public void writeSold() {
        db.SoldRecord();
    }

}