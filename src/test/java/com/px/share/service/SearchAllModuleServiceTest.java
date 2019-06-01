package com.px.share.service;

import com.px.share.util.Common;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Peach
 */
public class SearchAllModuleServiceTest {

    public SearchAllModuleServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Ignore
    @Test
    public void testSetup() {
        System.out.println("start...");
        SearchAllModuleService searchAllModuleService = new SearchAllModuleService();
        boolean result = searchAllModuleService.setup();
        System.out.println("result = "+result);
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testAddData() {
        System.out.println("start...");
        SearchAllModuleService searchAllModuleService = new SearchAllModuleService();
        int moduleId = 0;
        String linkType = "TEST";
        int linkId = 0;
        String title ="ทดสอบครั้งที่ 1";
        String content ="ข้าวใหม่ปลามัน\n" +
                        "สํานวนสุภาษิตนี้ หมายความ ของใหม่ๆอะไรก็ดูดีไปหมด มักใช้เปรียบเทียบสามีภรรยาที่เพิ่งแต่งงานกันใหม่ๆ ความรักยังหวานชื่นอะไรก็หอมหวานไปซะหมด\n" +
                        "\n" +
                        "ที่มาของสํานวน คนในสมัยโบราณถือว่า ข้าวที่เก็บเกี่ยวในครึ่งปีหลัง เป็นข้าวที่ดีกว่าข้าวเก่า และปลาเป็นอาหารคู่กับข้าว ส่วนปลามันคือปลาในน้ำลดมีมันมากรับประทานอร่อย ซึ่งช่วงฤดูกาลเก็บเกี่ยวข้าวใหม่จะตรงกับช่วง ที่มีปลามันพอดี";
        Date contentDate = new Date();
        String searchContent = "ข้าวใหม่ปลามันสํานวนสุภาษิตนี้ หมายความ ของใหม่ๆอะไรก็ดูดีไปหมด มักใช้เปรียบเทียบสามีภรรยาที่เพิ่งแต่งงานกันใหม่ๆ ความรักยังหวานชื่นอะไรก็หอมหวานไปซะหมดที่มาของสํานวน คนในสมัยโบราณถือว่า ข้าวที่เก็บเกี่ยวในครึ่งปีหลัง เป็นข้าวที่ดีกว่าข้าวเก่า และปลาเป็นอาหารคู่กับข้าว ส่วนปลามันคือปลาในน้ำลดมีมันมากรับประทานอร่อย ซึ่งช่วงฤดูกาลเก็บเกี่ยวข้าวใหม่จะตรงกับช่วง ที่มีปลามันพอดี";
        IndexResponse result = searchAllModuleService.addData(moduleId, linkType, linkId, title, content, contentDate, searchContent);
        System.out.println("result = "+result.toString());
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testGetData() {
        System.out.println("start...");
        SearchAllModuleService searchAllModuleService = new SearchAllModuleService();
        String id = "AVpAjxlfa-hFsj9ogwaT";
        GetResponse result = searchAllModuleService.getData(id);
        System.out.println("result = "+result.toString());
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testUpdateData() {
        System.out.println("start...");
        SearchAllModuleService searchAllModuleService = new SearchAllModuleService();
        String id = "AVpAjxlfa-hFsj9ogwaT";
        int moduleId = 0;
        String linkType = "TEST";
        int linkId = 0;
        String title ="ปลากัด";
        String content ="ปลากัดมีรูปร่างเพรียวยาวและแบนข้าง หัวมีขนาดเล็ก ครีบก้นยาวจรดครีบหาง หางแบนกลม มีอวัยวะช่วยหายใจบนผิวน้ำได้โดยใช้ปากฮุบอากาศโดยไม่ต้องผ่านเหงือกเหมือนปลาทั่วไป เกล็ดสากเป็นแบบทีนอยด์ ปกคลุมจนถึงหัว ริมฝีปากหนา ตาโต ครีบอกคู่แรกยาวใช้สำหรับสัมผัส ปลาตัวผู้มีสีน้ำตาลเหลือบแดงและน้ำเงินหรือเขียว ครีบสีแดงและมีแถบสีเหลืองประ ในขณะที่ปลาตัวเมียสีจะซีดอ่อนและมีขนาดลำตัวที่เล็กกว่ามากจนเห็นได้ชัด";
        Date contentDate = new Date();
        String searchContent = "ปลากัดมีรูปร่างเพรียวยาวและแบนข้าง หัวมีขนาดเล็ก ครีบก้นยาวจรดครีบหาง หางแบนกลม มีอวัยวะช่วยหายใจบนผิวน้ำได้โดยใช้ปากฮุบอากาศโดยไม่ต้องผ่านเหงือกเหมือนปลาทั่วไป เกล็ดสากเป็นแบบทีนอยด์ ปกคลุมจนถึงหัว ริมฝีปากหนา ตาโต ครีบอกคู่แรกยาวใช้สำหรับสัมผัส ปลาตัวผู้มีสีน้ำตาลเหลือบแดงและน้ำเงินหรือเขียว ครีบสีแดงและมีแถบสีเหลืองประ ในขณะที่ปลาตัวเมียสีจะซีดอ่อนและมีขนาดลำตัวที่เล็กกว่ามากจนเห็นได้ชัด";
        UpdateResponse result = searchAllModuleService.updateData(id,moduleId, linkType, linkId, title, content, contentDate, searchContent);
        System.out.println("result = "+result.toString());
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testDeleteData() {
        System.out.println("start...");
        SearchAllModuleService searchAllModuleService = new SearchAllModuleService();
        String id = "AVpAsymha-hFsj9ogwaW";
        DeleteResponse result = searchAllModuleService.deleteData(id);
        System.out.println("result = "+result.toString());
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testSearch() {
        System.out.println("start...");
        SearchAllModuleService searchAllModuleService = new SearchAllModuleService();
        String searchText = "ปลาน้ำตาล";
        int from = 0;
        int size = 0;
        String defaultSymbolForSpace = "AND";
        String symbolAnd = "AND";
        String symbolOr = "OR";
        String symbolNot = "NOT";
        String symbolWith = "WITH";

        SearchResponse result = searchAllModuleService.search(searchText, from, size, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith);
        System.out.println("result = "+result.toString());
        System.out.println("end...");
    }
    
//    @Test
    public void testEncrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        String secret = "42444678902180239428358646519815165132078951561321646432131696998878616";
        String plainText = "HelloWorld";
        
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] key = secret.getBytes("UTF-8");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        key = md5.digest(key);
        key = Arrays.copyOf(key, 16);
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptText = new String(Base64.encodeBase64(encrypted));
        System.out.println(encryptText);
        System.out.println("------");
        
        cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        String decryptText = new String(cipher.doFinal(Base64.decodeBase64(encryptText)));
        System.out.println(decryptText);
        System.out.println("------");
    }
    
//    @Test
    public void testDecrypt() throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        String secret = "42444678902180239428358646519815165132078951561321646432131696998878616";
        String encrypted = "U2FsdGVkX19wN8XI0w0e93fGb4aRHeXXcDF+L9tF9a38HBBoQDBh21ArLNmSiTc+ZtJT8TTFawAHq0nKndicNQ==";
        String encrypted64 = "VTJGc2RHVmtYMTlRQkFNcnoxdUVTaVVDVHpnS2hDaE9ZWEVsQnhTejVCSDBpOHBySGVmU2JydUVnYWxRQ08wKzIzRzg3MkRSTW9FV0ZKZnBXbTN4WVE9PQ==";
        byte[] result;
//        encrypted = Base64.decodeBase64(encrypted64).toString();
        result = Base64.decodeBase64(encrypted64);
//        System.out.println(new String(result));
        encrypted = new String(result);
        
        byte[] cipherData = Base64.decodeBase64(encrypted);
        byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);
        
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
        SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
        IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);
        
        byte[] encryptedByte = Arrays.copyOfRange(cipherData, 16, cipherData.length);
        Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
        aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedData = aesCBC.doFinal(encryptedByte);
        String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);

        System.out.println(decryptedText);
    }
    
    public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

    int digestLength = md.getDigestLength();
    int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
    byte[] generatedData = new byte[requiredLength];
    int generatedLength = 0;

    try {
        md.reset();

        // Repeat process until sufficient data has been generated
        while (generatedLength < keyLength + ivLength) {

            // Digest data (last digest if available, password data, salt if available)
            if (generatedLength > 0)
                md.update(generatedData, generatedLength - digestLength, digestLength);
            md.update(password);
            if (salt != null)
                md.update(salt, 0, 8);
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
        if (ivLength > 0)
            result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

        return result;

    } catch (DigestException e) {
        throw new RuntimeException(e);

    } finally {
        // Clean out temporary data
        Arrays.fill(generatedData, (byte)0);
    }
}
}
