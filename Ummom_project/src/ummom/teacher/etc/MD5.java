package ummom.teacher.etc;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public static String getMD5Hash(String s){
        MessageDigest m =null;
        String hash = null;

        try{
            m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(),0,s.length());
            hash = new BigInteger(1,m.digest()).toString(16);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return hash;
    }
}
