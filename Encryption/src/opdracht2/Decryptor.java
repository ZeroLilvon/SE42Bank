/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Riccardo
 */
public class Decryptor
{

    public String decrypt(String fileName, String password, int count) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IOException, FileNotFoundException, ClassNotFoundException
    {

        List<byte[]> fileContent = readFromFile(fileName);

//        PBEParameterSpec pbeParamSpec = new PBEParameterSpec(fileContent.get(0), count);
//
//        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
//        SecretKeyFactory keyFac = SecretKeyFactory.getInstance(algorithm);
//        SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
//
//        Cipher pbeCipher = Cipher.getInstance(algorithm);
//        pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);
//
//        byte[] decryptedText = pbeCipher.doFinal(fileContent.get(1));

        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), fileContent.get(0), count, 128);
        SecretKey secretKey = skf.generateSecret(spec);
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretSpec, new IvParameterSpec(fileContent.get(2)));

        byte[] decryptedText = cipher.doFinal(fileContent.get(1));;

        return new String(decryptedText);
    }

    public List<byte[]> readFromFile(String filename) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
        List<byte[]> list = (List<byte[]>) in.readObject();
        in.close();

        return list;
    }
}
