/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.dao;

import auction.domain.Item;
import auction.domain.User;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Riccardo
 */
public class ItemDAOJPAImpl implements ItemDAO
{

    private final EntityManager em;
    
    public ItemDAOJPAImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int count() {
        Query q = em.createNamedQuery("Item.count", Item.class);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(Item item) {
        if (findByDescription(item.getDescription()) == null) {
            throw new IllegalArgumentException();
        }
        em.persist(item);
    }

    @Override
    public void edit(Item item) {
        if (findByDescription(item.getDescription()) == null) {
            throw new IllegalArgumentException();
        }
        em.merge(item);
    }

//    @Override
//    public void removeAll()
//    {
//        em.createQuery("delete from User").executeUpdate();
//    }

    @Override
    public Item find(Long id)
    {
        if (id < 1) return null;
        return em.find(Item.class, id);
    }

    @Override
    public List<Item> findByDescription(String description)
    {
        Query q = em.createNamedQuery("Item.findByDescription", Item.class);
        q.setParameter("itemDescription", description);
        return q.getResultList();
    }

    @Override
    public void remove(Item item)
    {
        em.remove(em.merge(item));
    }

    @Override
    public List<Item> findAll()
    {
        Query q = em.createNamedQuery("Item.findAll", Item.class);
        return q.getResultList();
    }
    
    @Override
    public void removeAll()
    {
        em.createQuery("delete from Item").executeUpdate();
        em.createQuery("delete from Bid").executeUpdate();
    }
    
}
