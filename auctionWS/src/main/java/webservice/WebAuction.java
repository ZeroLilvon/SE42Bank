package webservice;

import auction.domain.Item;
import auction.domain.User;
import javax.jws.WebService;

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
    
    public String getBar(){
        return "foo";
    }
}
