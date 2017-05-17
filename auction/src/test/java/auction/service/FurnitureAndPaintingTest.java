package auction.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import auction.domain.*;
import java.util.Iterator;
import nl.fontys.util.Money;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FurnitureAndPaintingTest {

    private AuctionMgr auctionMgr = new AuctionMgr();
    private RegistrationMgr registrationMgr = new RegistrationMgr();
    private SellerMgr sellerMgr = new SellerMgr();

    @Before
    public void setUp() throws Exception 
    {
        sellerMgr.removeAll();
        registrationMgr.removeAll();
    }

    @Test
    public void newFurniture() {
        String omsch = "omsch1";
        String iemand1 = "iemand1@def";
        User u1 = registrationMgr.registerUser(iemand1);
        User u2 = registrationMgr.registerUser("iemand2@def");
        Category cat = new Category("cat2");

        Item furniture1 = sellerMgr.offerFurniture(u1, cat, "broodkast", "ijzer");
        assertEquals("seller of item correct", furniture1.getSeller(), u1);

        User foundUser = registrationMgr.getUser(iemand1);
        Iterator<Item> it = foundUser.getOfferedItems();
        Item firstItem = it.next();
   //        int xxx = 22;
        assertEquals("item added in offeredItems", furniture1, firstItem);
        Item item2 = sellerMgr.offerPainting(u1, cat, omsch, "Nachtwacht", "Rembrandt");
        it = registrationMgr.getUser(iemand1).getOfferedItems();
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertFalse(it.hasNext());

        //de volgende code verwijderen als Item abstract is
        Item item3 = sellerMgr.offerItem(u1, new Category("boek"), "The science of Discworld");
        it = registrationMgr.getUser(iemand1).getOfferedItems();
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertTrue(it.hasNext());
        it.next();
        assertFalse(it.hasNext());

        assertNull(furniture1.getHighestBid());
        Bid bid = auctionMgr.newBid(furniture1, u2, new Money(150000, Money.EURO));
        furniture1 = bid.getMadeFor();
        assertNotNull(furniture1.getHighestBid());

        Item foundFurniture = auctionMgr.getItem(furniture1.getId());
        int i = 3;
        assertEquals(foundFurniture.getHighestBid(), bid);
        assertTrue(foundFurniture.getClass() == Furniture.class);
    }
}
