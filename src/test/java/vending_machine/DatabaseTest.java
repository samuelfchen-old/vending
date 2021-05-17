package vending_machine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;
import java.util.*;
import java.util.Map;
import java.util.HashMap;

import vending_machine.Database;
import vending_machine.User;
import vending_machine.snacks.*;

public class DatabaseTest {

    @Before
    public String init() {
        String resourcePath = null;
        switch(System.getProperty("os.name")) {
            case "Linux":
                resourcePath = "";
                break;
            default:
                resourcePath = "Documents/";
                break;
        }

        String filePath = String.format("/%s/%svending_machine", System.getProperty("user.home"), resourcePath);
        new File(filePath).mkdirs();

        String path = String.format(filePath + "/vending.db");
        new File(path).delete();
        return path;
    }

    @Test
    public void testConstructor() {
        Database db = new Database("test");
        assertNotNull(db);
    }

    // TODO
    @Test
    public void testInit() {

    }

    @Test
    public void testDoLogin() {
        Database db = new Database("test");
        assertNull(db.doLogin("random", "random"));
        Database d = new Database(init());
        d.init();
        User u = d.doLogin("admin", "admin");
        assertNotNull(u);
        assertEquals("admin", u.getName());
        assertEquals(0, u.getId());
        assertEquals("owner", u.getType());

        User invalid = d.doLogin("invalid", "notapassword");
        assertNull(invalid);
    }

    @Test
    public void testCheckUsername() {
        Database d = new Database(init());
        d.init();
        assertEquals("admin", d.checkUsername("admin"));
        assertEquals("anonymous", d.checkUsername("anonymous"));
        assertNull(d.checkUsername("notausername"));
    }

    @Test
    public void testCreateAccount() {
        Database d = new Database(init());
        d.init();
        assertTrue(d.createAccount("test", "password", "customer"));
        assertFalse(d.createAccount("example", "example", null));
        assertFalse(d.createAccount("example", null, "seller"));
        assertFalse(d.createAccount(null, "example", "seller"));
        assertFalse(d.createAccount("admin", "password", "customer"));
    }

    @Test
    public void testRemoveUser() {
        Database d = new Database(init());
        d.init();
        assertFalse(d.removeUser("admin"));
        d.createAccount("test", "password", "customer");
        assertTrue(d.removeUser("test"));
    }

    @Test
    public void testGetUsers() {
        Database d = new Database(init());
        d.init();
        List<User> users = d.getUsers();
        assertEquals(3, users.size());
        d.createAccount("newuser", "password", "customer");
        users = d.getUsers();
        assertEquals(4, users.size());
    }

    @Test
    public void testAddCurrency() {
        Database db = new Database("test");
        assertFalse(db.addCurrency());
        Database d = new Database(init());
        d.init();
        assertTrue(d.addCurrency());
    }

    @Test
    public void testGetCurrency() {
        Database d = new Database(init());
        d.init();
        d.addCurrency();
        Map<Integer, Integer> currency = d.getCurrency();
        assertEquals(10, currency.size());
        for (Map.Entry<Integer, Integer> e : currency.entrySet()) {
            assertEquals(5, e.getValue());
        }
    }

    @Test
    public void testUpdateCurrency() {
        Database d = new Database(init());
        d.init();
        d.addCurrency();
        Map<Integer, Integer> currency = d.getCurrency();
        assertEquals(5, currency.get(5000));
        assertEquals(5, currency.get(2000));
        Map<Integer, Integer> tester = new HashMap<>();
        tester.put(5000, 1);
        d.updateCurrency(tester, true);
        currency = d.getCurrency();
        assertEquals(6, currency.get(5000));
        tester = new HashMap<>();
        tester.put(2000, 2);
        d.updateCurrency(tester, false);
        currency = d.getCurrency();
        assertEquals(3, currency.get(2000));
    }

    @Test
    public void testAddPurchase() {
        Database d = new Database(init());
        d.init();
        assertEquals(d.addPurchase(1, 1, 1), true);
    }

    @Test
    public void testSnackInvalid() {
        Database d = new Database(init());
        d.init();
        assertEquals(d.addSnack(null, "chip", 1.0f, 1), false);
        assertEquals(d.addSnack("Name", null, 1.0f, 1), false);
        assertEquals(d.addSnack("name", "chip", 0.0f, 1), false);
        assertEquals(d.addSnack("name", "chip", 0.5f, -1), false);
        assertEquals(d.addSnack("name", "invalidcategory", 0.5f, 1), false);
    }

    @Test
    public void testAddSnack() {
        Database d = new Database(init());
        d.init();
        assertTrue(d.addSnack("newsnack", "chip", 1.5f, 10));
        assertEquals("newsnack", d.getSnack("newsnack").getName());
        assertEquals("chip", d.getSnack("newsnack").getCategory());
        assertEquals(1.5f, d.getSnack("newsnack").getPrice());
        assertEquals(10, d.getSnack("newsnack").getQuantity());
    }

    @Test
    public void testGetSnackInvalid() {
        Database d = new Database(init());
        d.init();
        assertNull(d.getSnack("notasnackname"));
    }

    @Test
    public void testGetSnack() {
        Database d = new Database(init());
        d.init();
        Snack s = d.getSnack("Kettle");
        assertTrue(s instanceof Chips);
        s = d.getSnack("Bounty");
        assertTrue(s instanceof Chocolates);
        s = d.getSnack("Sprite");
        assertTrue(s instanceof Drinks);
        s = d.getSnack("Mentos");
        assertTrue(s instanceof Candies);
    }

    @Test
    public void testGetSnackID() {
        Database d = new Database(init());
        d.init();
        assertEquals(1, d.getSnackId("Snickers"));
        assertEquals(2, d.getSnackId("Mars"));
    }

    @Test
    public void testChangeSnackQuantity() {
        Database d = new Database(init());
        d.init();
        assertTrue(d.changeSnackQuantity(1, 10));
        Snack s = d.getSnack("Snickers");
        assertEquals(10, s.getQuantity());
    }

    @Test
    public void testChangeSnackPrice() {
        Database d = new Database(init());
        d.init();
        assertTrue(d.changeSnackPrice(1, 15.0));
        Snack s = d.getSnack("Snickers");
        assertEquals(15.0, s.getPrice());
    }

    @Test
    public void testGetRecentSnacks() {
        Database d = new Database(init());
        d.init();
        d.addTransaction(1, 10.0, 5.0, "cash");
        d.addPurchase(1, d.getCurrentTransactionID(), 3);
        List<Snack> snacks = d.getRecentSnacks(1);
        assertEquals(1, snacks.size());
        assertTrue(snacks.get(0) instanceof Chocolates);
    }

    @Test
    public void testGetCard() {
        Database d = new Database(init());
        d.init();
        d.saveCard(1, 1);
        List<String> cards = d.getCard(1);
        assertEquals("Charles", cards.get(0));
        assertEquals("40691", cards.get(1));
    }

    @Test
    public void testSellerUpdateCurrency() {
        Database d = new Database(init());
        d.init();
        d.sellerUpdateCurrency(500, 10);
        Map<Integer, Integer> currencies = d.getCurrency();
        assertEquals(currencies.get(500), 10);
    }

    @Test
    public void testTransactionsRecord() {
        Database d = new Database(init());
        d.init();
        d.addTransaction(1, 10.0, 5.0, "cash");
        d.TransactionsRecord();
    }

    @Test
    public void testSoldRecord() {
        Database d = new Database(init());
        d.init();
        d.addTransaction(1, 10.0, 5.0, "cash");
        d.addPurchase(1, d.getCurrentTransactionID(), 3);
        d.SoldRecord();
    }
}