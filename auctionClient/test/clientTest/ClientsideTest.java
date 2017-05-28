/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientTest;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import webservice.Bid;
import webservice.Category;
import webservice.Item;
import webservice.Money;
import webservice.User;

/**
 *
 * @author Adriaan
 */
public class ClientsideTest {
    
    private String mailString = "example@mail.net";
    
    public ClientsideTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
        requestTestCleanup();
    }
    
    @Test
    public void RegistrationTest(){
        User u = registerUser(mailString);
        assertTrue(mailString.equals(u.getEmail()));
        User u2 = getUser(mailString);
        assertTrue(u.getEmail().equals(u2.getEmail()));
    }
    
    @Test
    public void AuctionTest(){
        User u = registerUser(mailString);
        
        // Confirm no item with id 0 exists
        assertEquals(null, getItem(0L));
        
        // Add an item, check description and category
        Category mycat = new Category();
        mycat.setDescription("exampleCat");
        String desc = "A bar of soap";
        Item i = offerItem(u, mycat, desc);
        assertTrue(desc.equals(i.getDescription()));
        assertTrue("exampleCat".equals(i.getCategory().getDescription()));
        
        // Confirm item has been added
        Item i2 = getItem(i.getId());
        System.out.println(i2.getDescription());
        
        // Add another item
        offerItem(u, mycat, desc);
        // Retrieve items
        List<Item> itemList = findItemByDescription(desc);
        assertEquals(2, itemList.size());
        assertTrue(itemList.get(0).getDescription().equals(itemList.get(1).getDescription()));
        
        // place a bid
        Money m = new Money();
        m.setCurrency("undefined");
        Bid b = newBid(i, u, m);
        System.out.println(b.getBuyer());
        
        // Revoke an item, test should return false because item has a bid
        assertFalse(revokeItem(i));
    }

    // <editor-fold defaultstate="collapsed" desc=" Registration section ">
    
    private static User registerUser(java.lang.String arg0) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.registerUser(arg0);
    }
    
    private static User getUser(java.lang.String arg0) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.getUser(arg0);
    }

// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Auction section ">
    private static Item getItem(java.lang.Long arg0) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.getItem(arg0);
    }
    
    private static java.util.List<webservice.Item> findItemByDescription(java.lang.String arg0) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.findItemByDescription(arg0);
    }
    
    private static Bid newBid(webservice.Item arg0, webservice.User arg1, webservice.Money arg2) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.newBid(arg0, arg1, arg2);
    }
    
    private static Item offerItem(webservice.User arg0, webservice.Category arg1, java.lang.String arg2) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.offerItem(arg0, arg1, arg2);
    }
    
    private static boolean revokeItem(webservice.Item arg0) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.revokeItem(arg0);
    }

// </editor-fold>
    
    // Misc.
        private static String getBar() {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.getBar();
    }

    private static void requestTestCleanup() {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        port.requestTestCleanup();
    }
        
    
}
