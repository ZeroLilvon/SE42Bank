package auction.dao;

import auction.domain.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public class UserDAOJPAImpl implements UserDAO {

    private final EntityManager em;
    
    public UserDAOJPAImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int count() {
        Query q = em.createNamedQuery("User.count", User.class);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(User user) {
         if (findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }
        em.persist(user);
    }

    @Override
    public void edit(User user) {
        if (findByEmail(user.getEmail()) == null) {
            throw new IllegalArgumentException();
        }
        em.merge(user);
    }


    @Override
    public List<User> findAll() {
        Query q = em.createNamedQuery("User.findAll", User.class);
        return q.getResultList();
    }

    @Override
    public User findByEmail(String email) {
        Query q = em.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("userEmail", email);
        User u = null;
        try
        {
            u = (User) q.getSingleResult();
        } 
        catch (NoResultException ex)
        {
            System.out.println("Ex");
        }
        return u;
    }

    @Override
    public void remove(User user) {
        em.remove(em.merge(user));
    }

    @Override
    public void removeAll()
    {
        em.createQuery("delete from User").executeUpdate();
    }
}
