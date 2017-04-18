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
    
    /*
        No Querries have been run
    */
    @Test
    public void Test2() 
    {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        assertNull(account.getId());
        em.getTransaction().rollback();
        assertNull(account.getId());

    }
    
    /*
        INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?) bind => [111, 0, 0]
        SELECT LAST_INSERT_ID()
        DELETE FROM ACCOUNT
    */
    @Test
    public void Test3() 
    {
        Long expected = -100L;
        Account account = new Account(111L);
        account.setId(expected);
        em.getTransaction().begin();
        em.persist(account);
        // Querry not yet send to DB so ID is still -100. (Auto-increment hasnt changed anything yet)
        assertEquals(expected, account.getId());
        em.flush();
        // Querry sent to databse. Since ID is not sent to DB Auto-increment changed ID from -100 to correct value.
        assertNotEquals(expected, account.getId());
        em.getTransaction().commit();
        // Changes have been saved (Commited)
    }
    
    /*
        INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?) bind => [114, 400, 0]
        SELECT LAST_INSERT_ID()
        SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ID = ?) bind => [33]
        DELETE FROM ACCOUNT
    */
    @Test
    public void Test4() 
    {
        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        assertEquals(expectedBalance, account.getBalance());
        // Commits new user with balance of 400 to DB. Gets latest added and checks Balance.
        Long cid = account.getId();
        account = null;
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class, cid);
        // Select User with the ID of preveous insert. Gets balance and checks it.
        assertEquals(expectedBalance, found.getBalance());

    }
    
    /*
        INSERT INTO ACCOUNT (ACCOUNTNR, BALANCE, THRESHOLD) VALUES (?, ?, ?) bind => [114, 400, 0]
        SELECT LAST_INSERT_ID()
        SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ID = ?) bind => [109] 
        UPDATE ACCOUNT SET BALANCE = ? WHERE (ID = ?) bind => [3313, 109] 
        SELECT ID, ACCOUNTNR, BALANCE, THRESHOLD FROM ACCOUNT WHERE (ID = ?) bind => [109] 
        DELETE FROM ACCOUNT
    */
    @Test
    public void Test5() 
    {
        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        assertEquals(expectedBalance, account.getBalance());
        // Commits new user with balance of 400 to DB. Gets latest added and checks Balance.
        Long cid = account.getId();
        
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class, cid);
        // Select User with the ID of preveous insert. Gets balance and checks it.
        assertEquals(expectedBalance, found.getBalance());
        em2.getTransaction().commit();
        
        // Starts first transaction again and changes balance
        em.getTransaction().begin();
        account.setBalance(3313L);
        em.getTransaction().commit();
        
        // Refresh found from DB (Gets the account again)
        em2.refresh(found);
        assertEquals(3313L, (long)found.getBalance());

    }

    
}


/*
        1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
        2.	Welke SQL statements worden gegenereerd?
        3.	Wat is het eindresultaat in de database?
        4.	Verklaring van bovenstaande drie observaties.
*/