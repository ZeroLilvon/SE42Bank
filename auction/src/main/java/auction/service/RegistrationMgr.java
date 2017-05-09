package auction.service;

import java.util.*;
import auction.domain.User;
import auction.dao.UserDAOCollectionImpl;
import auction.dao.UserDAO;
import auction.dao.UserDAOJPAImpl;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RegistrationMgr {
    
    private EntityManagerFactory emf;
    
    public RegistrationMgr()
    {
        emf = Persistence.createEntityManagerFactory("auctionPU");
    }
    /**
     * Registreert een gebruiker met het als parameter gegeven e-mailadres, mits
     * zo'n gebruiker nog niet bestaat.
     * @param email
     * @return Een Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres (nieuw aangemaakt of reeds bestaand). Als het e-mailadres
     * onjuist is ( het bevat geen '@'-teken) wordt null teruggegeven.
     */
    public User registerUser(String email) {
        if (!email.contains("@")) {
            return null;
        }
        EntityManager em = emf.createEntityManager();
        User user = null;
        try
        {
            em.getTransaction().begin();
            UserDAO userDAO = new UserDAOJPAImpl(em);
            
            user = userDAO.findByEmail(email);
            if (user != null) 
            {
                return user;
            }
            
            user = new User(email);
            userDAO.create(user);
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
        return user;
    }

    /**
     *
     * @param email een e-mailadres
     * @return Het Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres of null als zo'n User niet bestaat.
     */
    public User getUser(String email) {
      
        User user = null;
        
        EntityManager em = emf.createEntityManager();
        
        
        try
        {
            em.getTransaction().begin();
            UserDAO userDAO = new UserDAOJPAImpl(em);
            user = userDAO.findByEmail(email);
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
        return user;
    }

    /**
     * @return Een iterator over alle geregistreerde gebruikers
     */
    public List<User> getUsers() {
        
        List<User> users = null;
        
        EntityManager em = emf.createEntityManager();
        
        try
        {
            em.getTransaction().begin();
            UserDAO userDAO = new UserDAOJPAImpl(em);
            users = userDAO.findAll();
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
        return users;
    }
    
    public void removeAll()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            UserDAO userDAO = new UserDAOJPAImpl(em);
            userDAO.removeAll();
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
