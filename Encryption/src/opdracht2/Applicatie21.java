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
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 *
 * @author Riccardo
 */
public class Applicatie21
{
    
    private final static int COUNT = 3313;
    
    private final static String TEXT = "Slime";
    private final static String PASSWORD = "password";    

    public static void main(String[] args)
    {
        try
        {
            
            Encryptor encryptor = new Encryptor();
            byte[] salt = encryptor.generateSalt();
            byte[] encryptedMessage = encryptor.encrypt(TEXT, PASSWORD, COUNT, salt);
            
            encryptor.writeToFile("EncryptedH", salt, encryptedMessage);
            

        } catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(Applicatie21.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex)
        {
            Logger.getLogger(Applicatie21.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex)
        {
            Logger.getLogger(Applicatie21.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex)
        {
            Logger.getLogger(Applicatie21.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex)
        {
            Logger.getLogger(Applicatie21.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex)
        {
            Logger.getLogger(Applicatie21.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex)
        {
            Logger.getLogger(Applicatie21.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Applicatie21.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidParameterSpecException ex)
        {
            Logger.getLogger(Applicatie21.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
