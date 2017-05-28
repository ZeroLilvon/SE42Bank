package auction.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Category{

    @Column (name ="CAT_DESCRIPTION")
    private String description;
    
    private Category() {
        description = "undefined";
    }

    
    public Category(String description) {
        this.description = description;
    }

    public String getDiscription() {
        return description;
    }
}
