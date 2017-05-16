package auction.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "select u from User as u"),
    @NamedQuery(name = "User.count", query = "select count(u) from User as u"),
    @NamedQuery(name = "User.findByEmail", query = "select u from User as u where u.email = :userEmail")
})
public class User {

    @Id  
    private String email;
    @OneToMany(mappedBy = "seller")
    Set<Item> offeredItems;

    public  User()
    {
        
    }
    
    public User(String email) {
        this.email = email;
        offeredItems = new HashSet<>();

    }
    
    void addItem(Item item)
    {
        offeredItems.add(item);
    }
    
    public int numberOfOfferedItems()
    {
        return offeredItems.size();
    }

    public Iterator<Item> getOfferedItems()
    {
        return offeredItems.iterator();
    }

    public String getEmail() {
        return email;
    }
    
    @Override
    public boolean equals(Object obj) 
    {
        User u = (User) obj;
        return this.email.equals(u.getEmail());
    }
}
