/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Verification;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riccardo
 */
public class Apllicatie3
{
     public static void main(String[] args)
    {
        try
        {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("HTextSigneByZero"));
            List<byte[]> list = (List<byte[]>) in.readObject();
            in.close();
            
            Verification verification = new Verification();
            
            boolean result = verification.verifySignature(list.get(0), list.get(1), "PublicH");
            
            if (result){
                System.out.println("Verified");
                System.err.println(new String (list.get(0)));
            }
            else{
                System.out.println("Unverified");
            }
            
            
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Verification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Verification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex)
         {
             Logger.getLogger(Apllicatie3.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
}
