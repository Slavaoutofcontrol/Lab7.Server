package utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordManager {
    public String hashPassword(String password){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-384");
            byte[] pass = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger bigInteger = new BigInteger(1, pass);
            return bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
