package com.px.share.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title: Class for Email</p>
 * <p>
 * Description: </p>
 * <p>
 * Copyright: Copyright (c) 2546</p>
 * <p>
 * Company: Praxis Co.,LTD.</p>
 *
 * @author : Aniwat Kotdee
 * @version 1.0
 */
public class Email {

    private static final Logger LOG = Logger.getLogger(Email.class.getName());
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private final String server;
    private final String port;
    private final String from;
    private final String user;
    private final String pass;
    public String MsgError = "";

    public static String getVersion() {
        return "2.5.0";
    }

    public Email(String server, String port, String from, String user, String pass) {
        this.server = server;
        this.port = port;
        this.from = from;
        this.user = user;
        this.pass = pass;
    }

    public String getServer() {
        return server;
    }

    public String getPort() {
        return port;
    }

    public String getFrom() {
        return from;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public void setMsgError(String MsgError1) {
        char ch1 = (char) 32;
        char ch2 = (char) 13;
        char ch3 = (char) 10;
        int a = MsgError1.indexOf(ch2);
        int b = MsgError1.indexOf(ch3);
        if (a != -1) {
            MsgError1 = MsgError1.replace(ch2, ch1);
        }
        if (b != -1) {
            MsgError1 = MsgError1.replace(ch3, ch1);
        }
        int intstart1 = MsgError1.indexOf("nested exception is:");
        int intstart2 = -1;
        if (intstart1 != -1) {
            intstart2 = MsgError1.indexOf("nested exception is:", (intstart1 + 21));
        }
        if (intstart2 != -1) {
            MsgError1 = MsgError1.substring(intstart1 + 21, intstart2);
        } else {
            MsgError1 = MsgError1.substring(intstart1 + 21);
        }
        if (MsgError1.contains("AuthenticationFailedException")) {
            MsgError1 = "ไม่สามารถเชื่อมต่อกับ Mail Server ได้";
        } else if (MsgError1.contains("Unknown SMTP host")) {
            MsgError1 = "ไม่พบ Mail Server";
        } else if (MsgError1.contains("InvalidAddresses")) {
            MsgError1 = "ไม่พบ Email ผู้ส่ง";
        } else if (MsgError1.contains("IOException")) {
            MsgError1 = "ไม่พบไฟล์เอกสารแนบ";
        } else {
        }
        MsgError = MsgError1;
    }

    public boolean send(String mailSubject, String mailTo, String mailCC, String mailBCC, String mailBody, ArrayList<String> fileAttachPath, String mailType, boolean debug) {
        boolean result = false;
        Properties props = new Properties();
        props.put("mail.smtp.host", server);
        props.put("mail.debug", debug);
        if (!port.equals("")) {
            props.put("mail.smtp.port", port);
            if (port.equals("587")) {
                props.put("mail.smtp.starttls.enable", "true");
            }
        }
        javax.mail.Authenticator auth = null;
        if (user.equalsIgnoreCase("")) {
            //props.put("mail.smtp.auth", "false");
            props.put("mail.imap.auth.plain.disable", "true");
            props.put("mail.imap.auth.ntlm.disable", "true");
        } else {
            props.put("mail.smtp.auth", "true");
            auth = new SMTPAuthenticator(user, pass);
        }
//        Session mailSession = Session.getDefaultInstance(props, auth);
        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
        mailSession.setDebug(debug);
        try {
            Transport transport = mailSession.getTransport("smtp");
            MimeMessage message = new MimeMessage(mailSession);

            message.setSubject(mailSubject, "UTF-8");
            message.setFrom(new InternetAddress(from));

            String[] to_ret = mailTo.split(";");
            for (int x = 0; x <= to_ret.length - 1; x++) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to_ret[x]));
            }

            if ((mailCC != null) && (!mailCC.equals(""))) {
                String[] cc_ret = mailCC.split(";");
                for (int x = 0; x <= cc_ret.length - 1; x++) {
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc_ret[x]));
                }
            }

            if ((mailBCC != null) && (!mailBCC.equals(""))) {
                String[] bcc_ret = mailBCC.split(";");
                for (int x = 0; x <= bcc_ret.length - 1; x++) {
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc_ret[x]));
                }
            }

            MimeMultipart multipart = new MimeMultipart("related");
            String htmlText = mailBody;
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setHeader("Content-Type", "text/" + mailType + "; charset=\"utf-8\"");
            messageBodyPart.setText(htmlText, "UTF-8", mailType);

            multipart.addBodyPart(messageBodyPart);

            if (!fileAttachPath.isEmpty()) {
                File fAttach = null;
                FileDataSource fds = null;
                for (String filePath : fileAttachPath) {
                    MimeBodyPart mbp2 = new MimeBodyPart();
                    fAttach = new File(filePath);
                    if (fAttach.exists()) {
                        fds = new FileDataSource(filePath);
                        mbp2.setDataHandler(new DataHandler(fds));
                        mbp2.setFileName(MimeUtility.encodeText(fds.getName()));
                        multipart.addBodyPart(mbp2);
                    } else {
                        LOG.info("File attach path Not Found!!");
                    }
                }
            }
            // put everything together
            message.setSentDate(new Date());
            message.setContent(multipart);
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            result = true;
        } catch (MessagingException | UnsupportedEncodingException ex) {
            LOG.error("Email Exception = " + ex);
            MsgError = ex.toString();
            setMsgError(MsgError);
        }
        return result;
    }
}
