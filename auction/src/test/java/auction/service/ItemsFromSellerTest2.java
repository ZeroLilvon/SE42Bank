package auction.service;

import auction.domain.Bid;
import org.junit.Ignore;
import javax.persistence.*;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import java.util.Iterator;
import nl.fontys.util.Money;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemsFromSellerTest2 {

    private AuctionMgr auctionMgr = new AuctionMgr();
    private RegistrationMgr registrationMgr = new RegistrationMgr();
    private SellerMgr sellerMgr = new SellerMgr();

    public ItemsFromSellerTest2() {
    }

    @Before
    public void setUp() throws Exception 
    {
        sellerMgr.removeAll();
        registrationMgr.removeAll();
    }

    @Test
 //   @Ignore
    public void TestThisStuff() {

        String email = "ifu1@nl";
        String omsch1 = "omsch_ifu1";

        User user1 = registrationMgr.registerUser(email);
        assertEquals(0, user1.numberOfOfferedItems());
        User user2 = registrationMgr.registerUser("Email@nl");

        Category cat = new Category("cat2");
        Item item1 = sellerMgr.offerItem(user1, cat, omsch1);
        Bid new1 = auctionMgr.newBid(item1, user2, new Money(10, "eur"));
        
        long a = item1.getId();
        long b = new1.getMadeFor().getId();
        // test number of items belonging to user1
        assertTrue(a == b);
        
        assertEquals(user2, new1.getMadeFor().getHighestBid().getBuyer());

    }
}
