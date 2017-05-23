package auction.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

@Entity
public class Bid {
    
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private FontysTime time;
    @ManyToOne
    private User buyer;
    @Embedded
    private Money amount;
    @OneToOne (mappedBy = "highest") 
    private Item madeFor;

    public Bid()
    {
        
    }

    public Item getMadeFor()
    {
        return madeFor;
    }
    
    public Bid(User buyer, Money amount, Item item) {
       this.buyer = buyer;
       this.amount = amount;
       this.madeFor = item;
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
    
    @Override
    public boolean equals(Object o) 
    {
        Bid b = (Bid) o;
        return this.madeFor.equals(b.getMadeFor()) && this.buyer.equals(b.getBuyer());
    }
}
