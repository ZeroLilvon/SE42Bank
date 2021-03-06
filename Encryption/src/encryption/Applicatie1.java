/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class Applicatie1
{
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
            fileOut.write(publicKey.getEncoded());
            fileOut.flush();
            fileOut.close();
            
            
            fileOut = new FileOutputStream("PrivateH");
            fileOut.write(privateKey.getEncoded());
            fileOut.flush();
            fileOut.close();
            
            
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(Applicatie1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Applicatie1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(Applicatie1.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
