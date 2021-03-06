package auction.domain;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import nl.fontys.util.Money;

@Entity
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "select i from Item as i"),
    @NamedQuery(name = "Item.count", query = "select count(i) from Item as i"),
    @NamedQuery(name = "Item.findByDescription", query = "select i from Item as i where i.description = :itemDescription")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Item implements Comparable {

    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User seller;
    @Embedded
    private Category category;
    private String description;
    @OneToOne (cascade = {CascadeType.PERSIST})
    private Bid highest = null;

    public Item()
    {
        
    }
    
    public Item(User seller, Category category, String description) {
        this.category = category;
        this.description = description;
        this.seller = seller;
        seller.addItem(this);
    }

    public void setSeller(User seller)
    {
        this.seller = seller;
    }

    public Long getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Bid getHighestBid() {
        return highest;
    }

    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount, this);
        return highest;
    }

    public int compareTo(Object arg0) {
        //TODO
        return -1;
    }

    @Override
    public boolean equals(Object o) 
    {
        Item i  = (Item) o;
        return this.id == i.getId();
    }

    @Override
    public int hashCode() 
    {
        return seller.getEmail().hashCode() + description.hashCode();
    }
}
