package com.px.share.util;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.entity.Param;
import com.px.share.service.ParamService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

/**
 *
 * @author OPAS
 */
public class Common {

    private static final Logger LOG = Logger.getLogger(Common.class);

    public Common() {
    }

    public static boolean sendEmail(String mailSubject, String mailTo, String mailToCC, String mailToBCC, String mailBody, ArrayList fileAttachPath, String mailType, boolean debug) {
        checkNotNull(mailSubject, "mailSubject must not be null");
        checkNotNull(mailTo, "mailTo must not be null");
//        checkNotNull(mailBody, "mailBody must not be null");
        String server = "smtp.gmail.com";
        String port = "25";
        String from = "admin@praxis.co.th";
        String user = "";
        String pass = "";
        if (mailType.equals("")) {
            mailType = "html";
        }
        ParamService paramService = new ParamService();
        List<Param> listParam = paramService.listByParamName("EMAIL", 0, 10, "createdDate", "asc");
        for (Param emailParam : listParam) {
            switch (emailParam.getParamName()) {
                case "EMAIL_PORT":
                    if (!emailParam.getParamValue().equals("")) {
                        port = emailParam.getParamValue();
                    }
                    break;
                case "EMAIL_USER_PASS":
                    if (!emailParam.getParamValue().equals("")) {
                        pass = emailParam.getParamValue();
                    }
                    break;
                case "EMAIL_SYSTEM_FROM":
                    if (!emailParam.getParamValue().equals("")) {
                        from = emailParam.getParamValue();
                    }
                    break;
                case "EMAIL_SERVER":
                    if (!emailParam.getParamValue().equals("")) {
                        server = emailParam.getParamValue();
                    }
                    break;
                case "EMAIL_USER_NAME":
                    if (!emailParam.getParamValue().equals("")) {
                        user = emailParam.getParamValue();
                    }
                    break;
            }
        }
        Email email = new Email(server, port, from, user, pass);
        return email.send(mailSubject, mailTo, mailToCC, mailToBCC, mailBody, fileAttachPath, mailType, debug);
    }
    
    public static boolean sendEmailCustomFrom(String mailSubject, String mailFrom, String mailTo, String mailToCC, String mailToBCC, String mailBody, ArrayList fileAttachPath, String mailType, boolean debug) {
        checkNotNull(mailSubject, "mailSubject must not be null");
        checkNotNull(mailTo, "mailTo must not be null");
//        checkNotNull(mailBody, "mailBody must not be null");
        String server = "smtp.gmail.com";
        String port = "25";
        String user = "";
        String pass = "";
        if (mailType.equals("")) {
            mailType = "html";
        }
        ParamService paramService = new ParamService();
        List<Param> listParam = paramService.listByParamName("EMAIL", 0, 10, "createdDate", "asc");
        for (Param emailParam : listParam) {
            switch (emailParam.getParamName()) {
                case "EMAIL_PORT":
                    if (!emailParam.getParamValue().equals("")) {
                        port = emailParam.getParamValue();
                    }
                    break;
                case "EMAIL_USER_PASS":
                    if (!emailParam.getParamValue().equals("")) {
                        pass = emailParam.getParamValue();
                    }
                    break;
                case "EMAIL_SERVER":
                    if (!emailParam.getParamValue().equals("")) {
                        server = emailParam.getParamValue();
                    }
                    break;
                case "EMAIL_USER_NAME":
                    if (!emailParam.getParamValue().equals("")) {
                        user = emailParam.getParamValue();
                    }
                    break;
            }
        }
        Email email = new Email(server, port, mailFrom, user, pass);
        return email.send(mailSubject, mailTo, mailToCC, mailToBCC, mailBody, fileAttachPath, mailType, debug);
    }

    public static String noNull(String data, String defaultValue) {
        if (data == null || data.isEmpty()) {
            return defaultValue;
        } else {
            return data;
        }
    }

    public static Date dateThaiToEng(String inputDate) {
        Date result = null;
        if (inputDate != null && (inputDate.length() > 0)) {
            inputDate = inputDate.replaceAll("-", "/");
            String[] arrayInputDateTime = inputDate.split(" ");
            if (arrayInputDateTime.length > 2) {
                throw new NumberFormatException("input format is wrong.");
            }

            String[] arrayDate;
            String[] arrayTime;
            int year = 0;
            int month = 0;
            int day = 0;
            int hour = 0;
            int minite = 0;
            int second = 0;
            //        int millisecond = 0;

            arrayDate = arrayInputDateTime[0].split("/");
            year = Integer.parseInt(arrayDate[2]) - 543;
            month = Integer.parseInt(arrayDate[1]) - 1;
            day = Integer.parseInt(arrayDate[0]);

            if (arrayInputDateTime.length == 2) {
                arrayTime = arrayInputDateTime[1].split(":");
                hour = Integer.parseInt(arrayTime[0]);
                minite = Integer.parseInt(arrayTime[1]);
                second = Integer.parseInt(arrayTime[2]);
            }
            //        calendar = new GregorianCalendar(year,month,day,hour,minite,second);
            //        LOG.info("y = "+calendar.get(Calendar.YEAR));
            //        LOG.info("M = "+calendar.get(Calendar.MONTH));
            //        LOG.info("d = "+calendar.get(Calendar.DAY_OF_MONTH));
            //        LOG.info("h = "+calendar.get(Calendar.HOUR_OF_DAY));
            //        LOG.info("m = "+calendar.get(Calendar.MINUTE));
            //        LOG.info("sec = "+calendar.get(Calendar.SECOND));
            //        LOG.info("millisec = "+calendar.get(Calendar.MILLISECOND));
            result = new GregorianCalendar(year, month, day, hour, minite, second).getTime();
        }
        return result;
    }

    public static LocalDateTime dateThaiToLocalDateTime(String inputDate) {
        LocalDateTime result = null;
        if (inputDate != null && (inputDate.length() > 0)) {
            DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy[ [HH][:mm][:ss][.SSS]]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter(Locale.getDefault()).withChronology(ThaiBuddhistChronology.INSTANCE);
            String[] arrayInputDateTime = inputDate.split(" ");
            if (arrayInputDateTime.length > 2) {
                throw new NumberFormatException("input format is wrong.");
            }

            result = LocalDateTime.parse(inputDate, DATE_FORMAT);
        }
        return result;

    }

    public static String localDateTimeToString(LocalDateTime localDateTime) {
        String resultDate = "";
        if (localDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formatDateTime = localDateTime.format(formatter);
            String[] arrayInputDateTime = formatDateTime.split(" ");
            int year = 0;
            String dm = arrayInputDateTime[0].substring(0, arrayInputDateTime[0].length() - 4);
            String y = arrayInputDateTime[0].substring(arrayInputDateTime[0].length() - 4);
            year = Integer.parseInt(y) + 543;
            resultDate = dm + year + " " + arrayInputDateTime[1];
        }
        return resultDate;
    }

    //oat-add
    public static String localDateTimeToString2(LocalDateTime localDateTime) {
        String resultDate = "";
        if (localDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String formatDateTime = localDateTime.format(formatter);
            String[] arrayInputDateTime = formatDateTime.split(" ");
            int year = 0;
            String dm = arrayInputDateTime[0].substring(0, arrayInputDateTime[0].length() - 4);
            String y = arrayInputDateTime[0].substring(arrayInputDateTime[0].length() - 4);
            year = Integer.parseInt(y) + 543;
            resultDate = dm + year + " " + arrayInputDateTime[1];
        }
        return resultDate;
    }

    //oat-add
    public static String localDateTimeToString3(LocalDateTime localDateTime, String time) {
        String resultDate = "";
        if (localDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formatDateTime = localDateTime.format(formatter);
            int year = 0;
            String dm = formatDateTime.substring(0, formatDateTime.length() - 4);
            String y = formatDateTime.substring(formatDateTime.length() - 4);
            year = Integer.parseInt(y) + 543;
            resultDate = dm + year + " " + time;
        }
        return resultDate;
    }

    //oat-add
    public static String localDateTimeToString4(LocalDateTime localDateTime) {
        String resultDate = "";
        if (localDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formatDateTime = localDateTime.format(formatter);
            int year = 0;
            String dm = formatDateTime.substring(0, formatDateTime.length() - 4);
            String y = formatDateTime.substring(formatDateTime.length() - 4);
            year = Integer.parseInt(y) + 543;
            resultDate = dm + year;
        }
        return resultDate;
    }

    public static String calculateFileToString(long fileSize) {
        String result = "0";
        if (fileSize < 1000000) {
            // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
            //fileSize = (fileSize / 1024);
            result = "ขนาดไฟล์ " + Long.toString(fileSize / 1024) + " KB.";
        } else if (fileSize > 1000000) {
            // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
            long fileSizeInKB = fileSize / 1024;
            // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
            //fileSize = fileSizeInKB / 1024;
            result = "ขนาดไฟล์ " + Long.toString(fileSizeInKB / 1024) + " MB.";
        }
        return result;
    }

    public static DetachedCriteria createOrder(DetachedCriteria criteria, String sort, String dir) {
        if (!sort.isEmpty()) {
            if ((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")) {
                criteria.addOrder(Order.asc(sort));
            }
            if ((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")) {
                criteria.addOrder(Order.desc(sort));
            }
        } else {
            criteria.addOrder(Order.desc("createdDate"));
        }
        return criteria;
    }

    public static boolean encodeFile(String srcFile, String outFile) throws FileNotFoundException, IOException {
        boolean result = false;
        String tempPath = outFile.substring(0, outFile.lastIndexOf(File.separator));
        File f = new File(tempPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        FileOutputStream fout;
        try (FileInputStream fin = new FileInputStream(srcFile)) {
            fout = new FileOutputStream(outFile);
            byte[] bKey = PxInit.KEY.getBytes();
            fout.write(PxInit.HEADER.getBytes());
            int len = bKey.length;
            byte[] buf = new byte[len];
            int numread = 0;
            while ((numread = fin.read(buf)) > 0) {
                xorByte(buf, bKey, numread);
                fout.write(buf, 0, numread);
            }
        }
        fout.flush();
        fout.close();
        File pathFileOut = new File(outFile);
        if (pathFileOut.exists()) {
            result = true;
        }
        return result;
    }

    public static boolean decodeFile(String srcFile, String outFile) throws Exception {
        boolean result = false;
        String tempPath = outFile.substring(0, outFile.lastIndexOf(File.separator));
        File f = new File(tempPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        byte[] fileInByte = readDecodeFile(srcFile);
        File returnFile = new File(outFile);
        if (returnFile.exists()) {
            result = true;
        } else {
            FileOutputStream fos;
            fos = new FileOutputStream(returnFile);
            fos.write(fileInByte);
            fos.flush();
            fos.close();
            result = true;
        }
        return result;
    }

    private static byte[] readDecodeFile(String srcFile) throws Exception {
        byte[] bKey = PxInit.KEY.getBytes();
        byte[] ret = new byte[0];
        File f = new File(srcFile);
        int fileSize = (int) f.length();
        // check if in encode or not
        try (FileInputStream fin = new FileInputStream(srcFile)) {
            // check if in encode or not
            byte[] bufHead = new byte[PxInit.HEADER.length()];
            int numread = fin.read(bufHead);
            if (numread == PxInit.HEADER.length()) {
                if (equalByte(bufHead, PxInit.HEADER.getBytes())) {
                    // read and decode it
                    byte[] buf = new byte[bKey.length];
                    ret = new byte[fileSize - numread];
                    int offset = 0;
                    while ((numread = fin.read(buf)) > 0) {
                        // xor buf here
                        xorByte(buf, bKey, numread);
                        // copy to ret
                        cpyByte(ret, buf, offset, numread);
                        offset += numread;
                    }
                    return (ret);
                }
            }
            // here just return it content no decode
            ret = new byte[fileSize];
            cpyByte(ret, bufHead, numread);
            fin.read(ret, numread, fileSize - numread);
        }
        return (ret);
    }

    private static boolean equalByte(byte[] b1, byte[] b2) {
        for (int i = 0; i < b1.length; i++) {
            if (b1[i] != b2[i]) {
                return (false);
            }
        }
        return (true);
    }

    private static void cpyByte(byte[] b1, byte[] b2, int num) {
        System.arraycopy(b2, 0, b1, 0, num);
    }

    private static void cpyByte(byte[] b1, byte[] b2, int start, int num) {
        System.arraycopy(b2, 0, b1, start, num);
    }

    private static void xorByte(byte[] b1, byte[] b2, int num) {
        for (int i = 0; i < num; i++) {
            b1[i] ^= b2[i];
        }
    }

    public static String[] getTokenStrings(String strValue, String symBol, boolean keepDelimeters) {
        String[] result;
        if (keepDelimeters) {
            result = strValue.split("(?=[" + symBol + "])|(?<=[" + symBol + "])");
        } else {
            result = strValue.split(symBol);
        }

        return result;
    }

    public List<String> readMasterFileToList(String masterFileName) {
        List<String> resultList = new ArrayList<>();
        String pathMasterFile = PxInit.PathMasterFile + "/" + masterFileName;
        ClassLoader classLoader = getClass().getClassLoader();
        File masterFile = new File(classLoader.getResource(pathMasterFile).getFile());
        LOG.debug("masterFile AbsolutePath = " + masterFile.getAbsolutePath() + " " + masterFile.exists());
        if (masterFile.exists()) {
            LOG.debug("Found Master File : " + masterFileName);
            try (Stream<String> stream = Files.lines(Paths.get(masterFile.getAbsolutePath()))) {
                //1. filter line 3
                //2. convert all content to upper case
                //3. convert it into a List
                resultList = stream
                        //                                .filter(line -> !line.startsWith("line3"))
                        //                                .map(String::toUpperCase)
                        .skip(1)
                        .map(line -> line.replace("\"\"", "NULLNULLNULL"))
                        .map(line -> line.replace("\"", ""))
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
//            resultList.forEach(System.out::println);
        } else {
            LOG.debug("Not Found Master File : " + masterFileName + " in resource/" + PxInit.PathMasterFile);
        }
        return resultList;
    }
    
    public static String encryptString(String data){
        String result = "";
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] key = PxInit.KEY.getBytes("UTF-8");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            key = md5.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
            result = new String(Base64.encodeBase64(encrypted));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            LOG.error(ex);
        }
        return result;
    }
    
    public static String decryptString(String enpryptData){
        String result = "";
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] key = PxInit.KEY.getBytes("UTF-8");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            key = md5.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            result = new String(cipher.doFinal(Base64.decodeBase64(enpryptData)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            LOG.error(ex);
        }
        return result;
    }
    
    public static String decryptQueryParam(String encryptedParam){
        String result = "";
        try {
            byte[] decodeParam = Base64.decodeBase64(encryptedParam);
            byte[] cipherData = Base64.decodeBase64(new String(decodeParam));
            byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, PxInit.KEY.getBytes(StandardCharsets.UTF_8), md5);
            SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
            IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);
            
            byte[] encryptedByte = Arrays.copyOfRange(cipherData, 16, cipherData.length);
            Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
            aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] decryptedData = aesCBC.doFinal(encryptedByte);
            result = new String(decryptedData, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            LOG.error(ex);
        }
        return result;
    }
    
    private static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

        int digestLength = md.getDigestLength();
        int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
        byte[] generatedData = new byte[requiredLength];
        int generatedLength = 0;

        try {
            md.reset();

            // Repeat process until sufficient data has been generated
            while (generatedLength < keyLength + ivLength) {

                // Digest data (last digest if available, password data, salt if available)
                if (generatedLength > 0) {
                    md.update(generatedData, generatedLength - digestLength, digestLength);
                }
                md.update(password);
                if (salt != null) {
                    md.update(salt, 0, 8);
                }
                md.digest(generatedData, generatedLength, digestLength);

                // additional rounds
                for (int i = 1; i < iterations; i++) {
                    md.update(generatedData, generatedLength, digestLength);
                    md.digest(generatedData, generatedLength, digestLength);
                }

                generatedLength += digestLength;
            }

            // Copy key and IV into separate byte arrays
            byte[][] result = new byte[2][];
            result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
            if (ivLength > 0) {
                result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);
            }

            return result;

        } catch (DigestException e) {
            throw new RuntimeException(e);

        } finally {
            // Clean out temporary data
            Arrays.fill(generatedData, (byte) 0);
        }
    }
}
