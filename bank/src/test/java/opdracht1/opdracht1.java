/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht1;

import bank.dao.AccountDAO;
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
    
    @Test
    public void Test6() 
    {
        Account acc = new Account(1L);
        Account acc2 = new Account(2L);
        Account acc9 = new Account(9L);

        // scenario 1
        Long balance1 = 100L;
        em.getTransaction().begin();
        em.persist(acc);
        acc.setBalance(balance1);
        em.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifieren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
        assertEquals(100L, (long)acc.getBalance());
        Long cid = acc.getId();
        
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class, cid);
        assertEquals(100L, (long)found.getBalance());
        em2.getTransaction().commit();


        // scenario 2
        Long balance2a = 211L;
        acc = new Account(2L);
        em.getTransaction().begin();
        acc9 = em.merge(acc);
        acc.setBalance(balance2a);
        acc9.setBalance(balance2a+balance2a);
        em.getTransaction().commit();
        assertEquals(211L,(long)acc.getBalance());
        assertEquals(422L, (long)acc9.getBalance());
        cid = acc9.getId();
        
        em2.getTransaction().begin();
        found = em2.find(Account.class, cid);
        assertEquals(422L, (long)found.getBalance());
        em2.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id. 
        // HINT: gebruik acccountDAO.findByAccountNr
        


        // scenario 3
        Long balance3b = 322L;
        Long balance3c = 333L;
        acc = new Account(3L);
        em.getTransaction().begin();
        acc2 = em.merge(acc);
        assertFalse(em.contains(acc)); // verklaar
        assertTrue(em.contains(acc2)); // verklaar
        assertNotEquals(acc,acc2);  //verklaar
        acc2.setBalance(balance3b);
        acc.setBalance(balance3c);
        em.getTransaction().commit() ;
        
        assertEquals(333L,(long)acc.getBalance());
        assertEquals(322L, (long)acc2.getBalance());
        cid = acc2.getId();
        
        em2.getTransaction().begin();
        found = em2.find(Account.class, cid);
        assertEquals(322L, (long)found.getBalance());
        em2.getTransaction().commit();
        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.


        // scenario 4
        Account account = new Account(114L) ;
        account.setBalance(450L) ;
        EntityManager em = emf.createEntityManager() ;
        em.getTransaction().begin() ;
        em.persist(account) ;
        em.getTransaction().commit() ;

        Account account2 = new Account(114L) ;
        Account tweedeAccountObject = account2 ;
        tweedeAccountObject.setBalance(650l) ;
        assertEquals((Long)650L,account2.getBalance()) ;  //verklaar
        account2.setId(account.getId()) ;
        em.getTransaction().begin() ;
        account2 = em.merge(account2) ;
        assertSame(account,account2) ;  //verklaar
        assertTrue(em.contains(account2)) ;  //verklaar
        assertFalse(em.contains(tweedeAccountObject)) ;  //verklaar
        tweedeAccountObject.setBalance(850l) ;
        assertEquals((Long)650L,account.getBalance()) ;  //verklaar
        assertEquals((Long)650L,account2.getBalance()) ;  //verklaar
        em.getTransaction().commit() ;
        em.close() ;

    }
    
     
    @Test
    public void Test7() 
    {
        Account acc1 = new Account(77L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        //Database bevat nu een account.

        // scenario 1        
        Account accF1;
        Account accF2;
        accF1 = em.find(Account.class, acc1.getId());
        accF2 = em.find(Account.class, acc1.getId());
        assertSame(accF1, accF2);

        // scenario 2        
        accF1 = em.find(Account.class, acc1.getId());
        em.clear();
        accF2 = em.find(Account.class, acc1.getId());
        assertNotSame(accF1, accF2);
        // Clear:  causes all managed entities to become detached. 
        // So it for accF2 it has to reatach the entity causing it to be on a different memory location

    }

    @Test
    public void Test8() 
    {
        Account acc1 = new Account(88L);
        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit();
        Long id = acc1.getId();
        //Database bevat nu een account.

        em.remove(acc1);
        assertEquals(id, acc1.getId()); // acc1 is still saved localy       
        Account accFound = em.find(Account.class, id);
        assertNull(accFound);
        // The account no longer exists in the DB so it cant be found and returns null.

    }
}


/*
        1.	Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
        2.	Welke SQL statements worden gegenereerd?
        3.	Wat is het eindresultaat in de database?
        4.	Verklaring van bovenstaande drie observaties.
*/