package com.px.share.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class SMSService {
    private static final Logger LOG = Logger.getLogger(ParamService.class.getName());
    
    public SMSService() {
    }
    
    public String sendSMS(String massage,String sendTo){
        ParamService paramService = new ParamService();
        HashMap result = new HashMap();
        String targetUri = paramService.getByParamName("SMS_GATEWAY").getParamValue();
        //กำหนดว่าจะ Charge เงิน Customer หรือไม่ (Default ค่าเป็น Y)
        String Charge = "N";
        //ในการ Push SMS นั้น CMD ต้องมีค่าเป็น SENDMSG
        String Cmd = "SENDMSG";
        //ข้อความภาษาอังกฤษ หรือไทย
        String Content = "กต.00000/2562 เรื่อง ทดสอบส่ง sms จากระบบ";
        String Report = "N";
        //เบอร์มือถือที่จะส่ง SMS ไป Ex: 668xxxxxxxx
//        String SendTo = "66950155015";
        String SendTo = sendTo;
        //ได้รับ Delivery Report ว่า SMS ได้ส่งถึงมือถือเรียบร้อยแล้ว
        String TransID = "BULK";
        
        WebTarget webTarget = ClientBuilder.newClient().target(targetUri);
        webTarget.queryParam("Charge", Charge);
        webTarget.queryParam("Cmd", Cmd);
        webTarget.queryParam("Content", Content);
        webTarget.queryParam("Report", Report);
        webTarget.queryParam("SendTo", SendTo);
        webTarget.queryParam("TransID", TransID);
        Response responseTarget = webTarget.request(MediaType.TEXT_PLAIN).buildGet().invoke();
        LOG.debug("statusMessage = "+responseTarget.getStatus());
        if (responseTarget.getStatus() == 200) {
            String responseStr = responseTarget.readEntity(String.class);
            ObjectMapper om = new ObjectMapper();
            try {
                result = (HashMap) om.readValue(responseStr, HashMap.class);
                HashMap responseDataHashMap = (HashMap) om.readValue(responseStr, HashMap.class);
                String statusMessage = (String)responseDataHashMap.get("status_message");
                LOG.debug("statusMessage = "+statusMessage);
                int resultCode = ((Integer)responseDataHashMap.get("result")).intValue();
                LOG.debug("resultCode = "+resultCode);
            } catch (IOException ex) {
                LOG.debug(ex);
            }
        }
        
//        String pathOTP = targetUri+"?username=";
//        WebTarget webTarget = ClientBuilder.newClient().target(pathOTP);
//        Response response = webTarget.request(MediaType.TEXT_PLAIN).buildGet().invoke();
//        if (response.getStatus() == 200) {
//            String responseStr = response.readEntity(String.class);
//            ObjectMapper om = new ObjectMapper();
//            result = (HashMap) om.readValue(responseStr, HashMap.class);
//            HashMap responseDataHashMap = (HashMap) om.readValue(responseStr, HashMap.class);
//            String statusMessage = (String)responseDataHashMap.get("status_message");
//            int resultCode = ((Integer)responseDataHashMap.get("result")).intValue();
//        }
        return "";
    }
}
