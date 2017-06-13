/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicatie2;

import encryption.Encryption;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riccardo
 */
public class applicatie2
{
     public static void main(String[] args)
    {
        try
        {
            StringBuilder sb = new StringBuilder();
            FileReader fileReader = new FileReader("HText.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                sb.append(line).append("\n");
            }
            
            bufferedReader.close();
            
            Message message = new Message(sb.toString(), "PrivateH");
            message.writeToFile("HTextSigneByZero");
            
            
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex)
         {
             Logger.getLogger(applicatie2.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
     

   


}
