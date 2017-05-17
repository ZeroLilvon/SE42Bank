package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.dao.UserDAO;
import auction.dao.UserDAOJPAImpl;
import auction.domain.Category;
import auction.domain.Furniture;
import auction.domain.Item;
import auction.domain.Painting;
import auction.domain.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SellerMgr {

    
    private EntityManagerFactory emf;
    
    public SellerMgr()
    {
        emf = Persistence.createEntityManagerFactory("auctionPU");
    }
    /**
     * @param seller
     * @param cat
     * @param description
     * @return het item aangeboden door seller, behorende tot de categorie cat
     *         en met de beschrijving description
     */
    public Item offerItem(User seller, Category cat, String description) {
        
        EntityManager em = emf.createEntityManager();
        Item item = null;
        try
        {
            em.getTransaction().begin();
            ItemDAO itemDAO = new ItemDAOJPAImpl(em);
            item = new Item(seller, cat, description);
            itemDAO.create(item);
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
    
    public Item offerFurniture(User seller, Category cat, String description, String material)
    {
        EntityManager em = emf.createEntityManager();
        Item item = null;
        try
        {
            em.getTransaction().begin();
            ItemDAO itemDAO = new ItemDAOJPAImpl(em);
            item = new Furniture(material, seller, cat, description);
            itemDAO.create(item);
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
    
    public Item offerPainting(User seller, Category cat, String description, String painter, String title)
    {
        EntityManager em = emf.createEntityManager();
        Item item = null;
        try
        {
            em.getTransaction().begin();
            ItemDAO itemDAO = new ItemDAOJPAImpl(em);
            item = new Painting(title, painter, seller, cat, description);
            itemDAO.create(item);
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
     * @param item
     * @return true als er nog niet geboden is op het item. Het item word verwijderd.
     *         false als er al geboden was op het item.
     */
    public boolean revokeItem(Item item) {
        
        EntityManager em = emf.createEntityManager();
       
        try
        {
            em.getTransaction().begin();
            ItemDAO itemDAO = new ItemDAOJPAImpl(em);

            if (itemDAO.find(item.getId()).getHighestBid() != null)
            {
                return false;
            }
            
            itemDAO.remove(item);
            em.getTransaction().commit();
        }
        catch (Exception ex)
        {
            em.getTransaction().rollback();
            return false;
        }
        finally
        {
            em.close();
        }
        return true;
    }
    
     public void removeAll()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            ItemDAO itemDAO = new ItemDAOJPAImpl(em);
            itemDAO.removeAll();
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
    }
}
