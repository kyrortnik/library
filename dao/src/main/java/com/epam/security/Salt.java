package com.epam.security;

import com.epam.exception.DAOException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Salt {

    private final char[] hexArray = "0123456789ABCDEF".toCharArray();


    public boolean verifyPassword(char[] providedPassword, String fromDatabase, String passwordSalt) throws DAOException {
        boolean isValid;
        try {
            String generate = generateEncryptedPassword(providedPassword, passwordSalt);
            isValid = generate.equals(fromDatabase);
            return isValid;
        } catch (Exception e) {
            throw new DAOException(e);
        }

    }

    public String generateEncryptedPassword(char[] password, String salt) throws DAOException {
        try {
            String passwordString = String.valueOf(password);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] hashedPassword = md.digest(passwordString.getBytes(StandardCharsets.UTF_8));
            return bytesToStringHex(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new DAOException(e);
        }
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return bytesToStringHex(salt);
    }


    private String bytesToStringHex(byte[] bytes) {

        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
