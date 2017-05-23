package auction.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

@Entity
public class Bid {
    
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private FontysTime time;
    @ManyToOne (cascade = {CascadeType.REMOVE})
    private User buyer;
    @Embedded
    private Money amount;

    public Bid()
    {
        
    }
    
    public Bid(User buyer, Money amount) {
       this.buyer = buyer;
       this.amount = amount;
    }

    public FontysTime getTime() {
        return time;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }
}
