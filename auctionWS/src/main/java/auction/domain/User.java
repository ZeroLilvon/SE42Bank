package auction.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "select u from User as u"),
    @NamedQuery(name = "User.count", query = "select count(u) from User as u"),
    @NamedQuery(name = "User.findByEmail", query = "select u from User as u where u.email = :userEmail")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    @Id  
    private String email;

    public  User()
    {
        
    }
    
    public User(String email) {
        this.email = email;

    }

    public String getEmail() {
        return email;
    }
    
    @XmlTransient
    public void setEmail(String email){
        this.email = email;
    }
    
    @Override
    public boolean equals(Object obj) 
    {
        User u = (User) obj;
        return this.email.equals(u.getEmail());
    }
}
