package com.px.share.util;

public class SMTPAuthenticator extends javax.mail.Authenticator {
    private String user;
    private String password;
    
    public SMTPAuthenticator(String user, String password) {
        this.user = user;
        this.password = password; 
    }
    
    @Override
    public javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(this.user, this.password);
    }
}
