/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
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
public class Encryptor
{
    private byte[] ivBytes;

    public byte[] encrypt(String text, String password, int count, byte[] salt) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidParameterSpecException
    {
        
//        PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, count);
//
//        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
//        SecretKeyFactory keyFac = SecretKeyFactory.getInstance(algorithm);
//        SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
//
//        Cipher pbeCipher = Cipher.getInstance(algorithm);
//        pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
        
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, count, 128);
        SecretKey secretKey = skf.generateSecret(spec);
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretSpec);
        AlgorithmParameters params = cipher.getParameters();
        ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(text.getBytes());


        return encryptedTextBytes;
    }

    public byte[] generateSalt()
    {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[8];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public void writeToFile(String filename, byte[] salt, byte[] text) throws FileNotFoundException, IOException
    {
        List<byte[]> list = new ArrayList<byte[]>();
        list.add(salt);
        list.add(text);
        list.add(ivBytes);
        
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
        out.writeObject(list);
        out.close();
        System.out.println("Your file is ready.");
    }

}
