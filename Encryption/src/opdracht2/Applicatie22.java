/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Riccardo
 */
public class Applicatie22
{
    
    private final static int COUNT = 3313;
    
    private final static String PASSWORD = "password";    
    
    public static void main(String[] args)
    {
        try
        {
            Decryptor decryptor = new Decryptor();
            
            String result = decryptor.decrypt("EncryptedH", PASSWORD, COUNT);
            
            System.out.println(result);
            
        } catch (IllegalBlockSizeException ex)
        {
            Logger.getLogger(Applicatie22.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex)
        {
            Logger.getLogger(Applicatie22.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex)
        {
            Logger.getLogger(Applicatie22.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex)
        {
            Logger.getLogger(Applicatie22.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(Applicatie22.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex)
        {
            Logger.getLogger(Applicatie22.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex)
        {
            Logger.getLogger(Applicatie22.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Applicatie22.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Applicatie22.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
       
    }
}
