/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht1;

import bank.domain.Account;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import util.DatabaseCleaner;

/**
 *
 * @author Riccardo
 */
public class opdracht1
{
    DatabaseCleaner DC;
    EntityManagerFactory emf;
    EntityManager em;
    
    public opdracht1()
    {
    }
    
    @Before
    public void setUp() throws SQLException
    {
        emf = Persistence.createEntityManagerFactory("bankPU");
        em = emf.createEntityManager();
        DC = new DatabaseCleaner(em);
        
    }
    
    @After
    public void tearDown() throws SQLException
    {
        DC.clean();
    }

 
    /*
        INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?) bind => [111, 0, 0]
        SELECT LAST_INSERT_ID()  AccountId: 1
        DELETE FROM ACCOUNT
    */
    @Test
    public void Test1() 
    {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        // Not yet commited so no accounts to return (It will be Null)
        assertNull(account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());
        // Has been comittted so there is more than 0 accounts to get
        assertTrue(account.getId() > 0L);

    }
}


/*
        1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
        2.	Welke SQL statements worden gegenereerd?
        3.	Wat is het eindresultaat in de database?
        4.	Verklaring van bovenstaande drie observaties.
*/