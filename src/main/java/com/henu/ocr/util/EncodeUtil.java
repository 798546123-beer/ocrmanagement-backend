package com.henu.ocr.util;



import org.apache.shiro.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodeUtil {
    @Test
    public static void main(String args[]){
        System.out.println(encodePassword("1234"));
    }
    public static String encodePassword(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            String hash = new String(Hex.encode(digest));
            md.reset();
            md.update(hash.getBytes());
            digest = md.digest();
            return new String(Hex.encode(digest));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}
