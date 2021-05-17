import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import java.io.File;
import java.util.List;
import java.util.*;
import java.util.Map;
import java.util.HashMap;

import vending_machine.*;
import vending_machine.snacks.*;

public class ModelTest {

    Model model;
    
    @BeforeEach
    public void init() {

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

        File toDelete = new File(filePath + "/vending.db");
        toDelete.delete();

        Database db = new Database(filePath + "/vending.db");
        db.init();
        model = new Model(db);
    }

    @Test
    public void testConstructor() {
        assertNotNull(model);
    }

    @Test 
    public void testCheckLogin() {
        ModelResponse ret = model.checkLogin("admin", "admin");
        assertEquals(ret.success, true);

        ret = model.checkLogin("admin", "wrong");
        assertEquals(ret.success, false);
    }

    @Test 
    public void testRemoveUsername() {
        model.checkLogin("admin", "admin");
        ModelResponse ret = model.checkUsername("admin");
        assertEquals(ret.success, false);

        ret = model.checkUsername("anonymous");
        assertEquals(ret.success, false);

        ret = model.checkUsername("test");
        assertEquals(ret.success, false);

        ret = model.checkUsername("seller");
        assertEquals(ret.success, true);
    }

    @Test 
    public void testCreateAccount() {
        assertTrue(model.createAccount("newacc", "password", "password", "customer").success);
        assertFalse(model.createAccount("new", "pas", "pas", "customer").success);
        assertFalse(model.createAccount("new", "pass", "word", "customer").success);
        assertFalse(model.createAccount("admin", "pass", "pass", "customer").success);
    }

    @Test void testGetSnacks() {
        assertTrue(model.getSnackInfo().success);
    }

    @Test
    public void testGetSingleSnack() {
        assertTrue(model.getSingleSnackInfo("Kettle").success);
        assertFalse(model.getSingleSnackInfo("Cettle").success);   
    }

    @Test
    public void testGetRecentSnacks() {
        assertTrue(model.getRecentSnacks(1).success);
    }

    @Test 
    public void testGetCostEmpty() {
        Map<Snack, Integer> snacks = new HashMap<>();
        assertEquals(model.getCost(snacks), 0);
    }

    @Test
    public void testGetCostFull() {
        Snack kettle = (Snack) model.getSingleSnackInfo("Kettle").data;
        Snack bounty = (Snack) model.getSingleSnackInfo("Bounty").data;

        Map<Snack, Integer> snacks = new HashMap<>();
        snacks.put(kettle, 2);
        snacks.put(bounty, 3);

        assertEquals((int) model.getCost(snacks) - 14, 0);
    }

    @Test
    public void testCashTransaction() {
        model.checkLogin("anonymous", "anonymous");

        Snack kettle = (Snack) model.getSingleSnackInfo("Kettle").data;
        Snack bounty = (Snack) model.getSingleSnackInfo("Bounty").data;

        Map<Snack, Integer> snacks = new HashMap<>();
        snacks.put(kettle, 2);
        snacks.put(bounty, 0);

        Map<Integer, Integer> cash = new HashMap<>();
        assertTrue(model.cashTransaction(snacks, cash, 0).success);
    }

    @Test
    public void testCardTransaction() {
        model.checkLogin("anonymous", "anonymous");

        Snack kettle = (Snack) model.getSingleSnackInfo("Kettle").data;
        Snack bounty = (Snack) model.getSingleSnackInfo("Bounty").data;

        Map<Snack, Integer> snacks = new HashMap<>();
        snacks.put(kettle, 2);
        snacks.put(bounty, 0);

        assertTrue(model.cardTransaction(snacks, "Charles", 40691, 0).success);
        assertFalse(model.cardTransaction(snacks, "Charles", 40692, 0).success);
        assertTrue(model.cardTransaction(snacks, "Charles", 40691, 1).success);

    }

    @Test
    public void testGetChange() {
        model.checkLogin("anonymous", "anonymous");

        assertTrue(model.getChange(0).success);
        assertTrue(model.getChange(20).success);
        assertFalse(model.getChange(-1).success);
        assertTrue(model.getChange(185).success);
        assertFalse(model.getChange(122).success);
        assertTrue(model.getChange(120).success);
    }

    @Test 
    public void testStaticMethods() {
        model.checkLogin("admin", "admin");

        model.getCard();
        model.writeCancelledTransactions();
        model.writeTransactions();
        model.writeUsers();
        model.writeAvailable();
        model.writeSold();
    }

    @Test
    public void testUpdatePrice() {
        assertTrue(model.updatePrice("Kettle", 0.3).success);
    }

    @Test
    public void testUpdateStock() {
        assertTrue(model.updateStock("Kettle", 2).success);
    }

    @Test
    public void testAddSnack() {
        assertTrue(model.addSnack("Kettle", "chip").success);
        assertFalse(model.addSnack("Kettle", "chop").success);
    }
}