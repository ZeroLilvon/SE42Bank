package webservice;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import java.util.List;
import javax.jws.WebService;
import nl.fontys.util.Money;

/**
 *
 * @author Adriaan
 */
@WebService
public class WebAuction {
    
    private Registration registration = new Registration();
    private Auction auction = new Auction();
    
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
}
