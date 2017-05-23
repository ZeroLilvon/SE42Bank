package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.dao.UserDAO;
import auction.dao.UserDAOJPAImpl;
import nl.fontys.util.Money;
import auction.domain.Bid;
import auction.domain.Item;
import auction.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AuctionMgr  {

    private EntityManagerFactory emf;
    
    public AuctionMgr()
    {
        emf = Persistence.createEntityManagerFactory("auctionPU");
    }
    
   /**
     * @param id
     * @return het item met deze id; als dit item niet bekend is wordt er null
     *         geretourneerd
     */
    public Item getItem(Long id) {
        
        Item item = null;
        
        EntityManager em = emf.createEntityManager();
        
        try
        {
            em.getTransaction().begin();
            ItemDAO itemDAO = new ItemDAOJPAImpl(em);
            item = itemDAO.find(id);
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            em.getTransaction().rollback();
        }
        finally
        {
            em.close();
        }
        return item;
    }

  
   /**
     * @param description
     * @return een lijst met items met @desciption. Eventueel lege lijst.
     */
    public List<Item> findItemByDescription(String description) {
        
        List<Item> items = null;
        
        EntityManager em = emf.createEntityManager();
        
        try
        {
            em.getTransaction().begin();
            ItemDAO itemDAO = new ItemDAOJPAImpl(em);
            items = itemDAO.findByDescription(description);
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            em.getTransaction().rollback();
        }
        finally
        {
            em.close();
        }
        return items;
    }

    /**
     * @param item
     * @param buyer
     * @param amount
     * @return het nieuwe bod ter hoogte van amount op item door buyer, tenzij
     *         amount niet hoger was dan het laatste bod, dan null
     */
    public Bid newBid(Item item, User buyer, Money amount) {
         
        EntityManager em = emf.createEntityManager();
        Bid bid = null;
        try
        {
            em.getTransaction().begin();
            ItemDAO itemDAO = new ItemDAOJPAImpl(em);
            if ((item = itemDAO.find(item.getId())) == null)
            {
                return null;
            }
            
            if (item.getHighestBid() != null && item.getHighestBid().getAmount().compareTo(amount) != -1)
            {
                return null;
            }
            
            item.newBid(buyer, amount);
            itemDAO.edit(item);
            em.getTransaction().commit();
            bid = item.getHighestBid();
        }
        catch (Exception ex)
        {
            em.getTransaction().rollback();
        }
        finally
        {
            em.close();
        }
        return bid;
    }
}
