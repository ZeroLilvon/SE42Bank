package webservice;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import java.sql.SQLException;
import java.util.List;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nl.fontys.util.Money;
import util.DatabaseCleaner;

/**
 *
 * @author Adriaan
 */
@WebService
public class WebAuction {
    
    private Registration registration = new Registration();
    private Auction auction = new Auction();
    
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("auctionPU");
    private EntityManager em = emf.createEntityManager();
    private DatabaseCleaner DC = new DatabaseCleaner(em);

    public User registerUser(String email){      
        return registration.registerUser(email);
    }
    
    public User getUser(String email){
        return registration.getUser(email);
    }
    
    public Item getItem(Long id){
        return auction.getItem(id);
    }
    
    public List<Item> findItemByDescription(String description) {
        return auction.findItemByDescription(description);
    }
    
        public Bid newBid(Item item, User buyer, Money amount){
        return auction.newBid(item, buyer, amount);
    }
    
    public Item offerItem(User seller, Category cat, String description){
        return auction.offerItem(seller, cat, description);
    }
    
    public boolean revokeItem(Item item){
        return auction.revokeItem(item);
    }
    
    public String getBar(){
        return "foo";
    }
    
    public void requestTestCleanup(){
        try {
            DC.clean();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
