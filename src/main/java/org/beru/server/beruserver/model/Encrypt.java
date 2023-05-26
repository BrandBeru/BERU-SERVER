package org.beru.server.beruserver.model;

import java.util.Base64;

public class Encrypt {
    public static String encrypt(String text){
        return Base64.getEncoder().encodeToString(text.getBytes());
    }
    public static String decrypt(String text){
        return new String(Base64.getDecoder().decode(text.getBytes()));
    }
}
