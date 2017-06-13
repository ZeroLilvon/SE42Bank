/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riccardo
 */
public class Encryption
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair kp = kpg.genKeyPair();
            Key publicKey = kp.getPublic();
            Key privateKey = kp.getPrivate();
            
            FileOutputStream fileOut = new FileOutputStream("PublicH");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(publicKey);
            fileOut.close();
            out.close();
            
             fileOut = new FileOutputStream("PrivateH");
             out = new ObjectOutputStream(fileOut);
            out.writeObject(privateKey);
            fileOut.close();
            out.close();
            
        } catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
