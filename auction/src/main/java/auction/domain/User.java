package auction.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "select u from User as u"),
    @NamedQuery(name = "User.count", query = "select count(u) from User as u"),
    @NamedQuery(name = "User.findByEmail", query = "select u from User as u where u.email = :userEmail")
})
public class User {

    @Id  
    private String email;

    public User(String email) {
        this.email = email;

    }

    public String getEmail() {
        return email;
    }
}