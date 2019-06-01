package com.px.admin.entity;

import com.px.share.util.BCrypt;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class DigitalKey {
    private static final Logger LOG = Logger.getLogger(DigitalKey.class.getName());
    private final Random random = new Random((new Date()).getTime());
    private final String encryptKey;
    public DigitalKey(String inputKey) {
        this.encryptKey = inputKey;
    }
    
    public String getEncryptDigitalKey(){        
        return BCrypt.hashpw(this.encryptKey, BCrypt.gensalt(4));
    }
    
    public boolean validateDigitalKey(String inboxKey,String encryptDigitalKey) throws UnsupportedEncodingException{
        return BCrypt.checkpw(decrypt(inboxKey), encryptDigitalKey);
    }
    
    public String encrypt(){
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        byte[] message = this.encryptKey.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(salt)+Base64.getEncoder().encodeToString(message);
    }
    
    public String decrypt(String encryptKey) throws UnsupportedEncodingException{
        String result = "";
        if (encryptKey.length() > 12) {
            String cipher = encryptKey.substring(12);
            result = new String(Base64.getDecoder().decode(cipher), "UTF-8");
        }
        return result;
    }    
}
