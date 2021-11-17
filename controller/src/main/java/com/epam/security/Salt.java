package com.epam.security;

import com.epam.command.exception.ControllerException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Salt {

    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();


    public static String generateEncryptedPassword(String password,String salt) throws ControllerException {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToStringHex(hashedPassword);
        }catch (NoSuchAlgorithmException e){
            throw new ControllerException(e);
        }
    }

    public static String generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
         random.nextBytes(salt);

        return bytesToStringHex(salt);
    }


    private static String bytesToStringHex(byte[] bytes){

        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0;i< bytes.length;i++){
            int v = bytes[i] & 0xFF;
            hexChars[i *2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    public static boolean verifyPassword(String providedPassword, String fromdatabase, String passwordSalt) throws ControllerException {
        boolean isValid;
        try {
            String generate = generateEncryptedPassword(providedPassword, passwordSalt);
            isValid = generate.equals(fromdatabase);
            return isValid;
        }catch (Exception e){
            throw new ControllerException(e);
        }

    }
}
