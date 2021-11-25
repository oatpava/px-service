package com.px.wf.service;

import com.px.share.entity.Param;
import com.px.share.service.ParamService;
import com.px.wf.entity.WfContentType;
import com.px.wf.entity.WfContentType2;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.boot.MetadataSources;

/**
 *
 * @author Mali
 */
public class WfModuleEntityService {

    private static final Logger LOG = Logger.getLogger(WfModuleEntityService.class.getName());
    private final int createdBy;

    public WfModuleEntityService() {
        this.createdBy = 0;
    }

    public MetadataSources listCreateEntity(MetadataSources metadataSource) {
        //Add Entity 
        metadataSource.addAnnotatedClass(com.px.wf.entity.FolderProperty.class);
        metadataSource.addAnnotatedClass(com.px.wf.entity.Thegif.class);
        metadataSource.addAnnotatedClass(com.px.wf.entity.ThegifDepartment.class);
        metadataSource.addAnnotatedClass(com.px.wf.entity.ThegifDocFile.class);
        metadataSource.addAnnotatedClass(com.px.wf.entity.WfCommandName.class);
        metadataSource.addAnnotatedClass(com.px.wf.entity.WfCommandType.class);
        metadataSource.addAnnotatedClass(com.px.wf.entity.WfContent.class);
        metadataSource.addAnnotatedClass(com.px.wf.entity.WfContentType.class);
        metadataSource.addAnnotatedClass(com.px.wf.entity.WfContentType2.class);
        metadataSource.addAnnotatedClass(com.px.wf.entity.WfField.class);
        metadataSource.addAnnotatedClass(com.px.wf.entity.WfFolder.class);
        metadataSource.addAnnotatedClass(com.px.wf.entity.WfReserveContentNo.class);
        metadataSource.addAnnotatedClass(com.px.wf.entity.WfDocumentType.class);
        metadataSource.addAnnotatedClass(com.px.wf.entity.WfAbsent.class);
        metadataSource.addAnnotatedClass(com.px.wf.entity.WfRecord.class);

        return metadataSource;
    }

    public void createDefaultData() {
        createDataParam();
        creteContentType();
        creteContentType2();
    }

    private void createDataParam() {
        Map<String, String> listParam = new HashMap();
        listParam.put("CONTENTFORMAT", "5");
        listParam.put("BOOKNOFORMAT", "5");
        listParam.put("ORDERFORMAT", "3");//oat-add
        listParam.put("INBOXAFTERREGISTER", "0");
        listParam.put("CANCELFINISH", "0");
        listParam.put("WFCONTENTSENDPAGE", "O,1|A,2|F,0|C,0");
        listParam.put("PRIORITYSHORTCUT", "N");
        listParam.put("DELETETORESERVE", "Y");
        listParam.put("INBOXAFTERSEND", "0");
        listParam.put("INBOXAFTERACTION", "0");
        listParam.put("INBOXAFTERREPLY", "0");
        //listParam.put("OUTBOXCANCELSEND", "0");//oat-edit
        listParam.put("OUTBOXCANCELSEND", "2");
        listParam.put("SETSTARTCONTENTNO", "0");
        listParam.put("BOOKNOEQUALCONNO", "Y");//oat-still no check
        listParam.put("THEGIF_PAHT", "C:\\px8\\Data\\Document\\");
        listParam.put("SENDAPPROVEINBOX", "0");
        listParam.put("RESULTAPPROVEINBOX", "0");
        listParam.put("ECMSDEPCODE", "-");//oat-edit
        listParam.put("ECMSSERVICE", "-");//oat-edit
        listParam.put("ECMSDELETEREQUEST", "Y");
        listParam.put("RESERVEFOLDERS", "0");//oat-add
        listParam.put("EMAILATTACHSIZE", "25");//oat-add
        listParam.put("SHAREBOOKNO", "Y");

        ParamService paramService = new ParamService();
        Param param = null;
        for (String key : listParam.keySet()) {
            param = paramService.getByParamName(key);
            if (param == null) {
                LOG.info("Param " + key + " not found!! Auto create " + key + ". ");
                param = new Param();
                param.setCreatedBy(this.createdBy);
                param.setParamName(key);
                param.setParamValue(listParam.get(key));
                param = paramService.create(param);
                param.setOrderNo(param.getId());
                param = paramService.update(param);
            }
        }
    }
    
    private void creteContentType() {
        WfContentTypeService contentTypeService = new WfContentTypeService();
        WfContentType contentType = contentTypeService.getById(1);
        WfContentType result = null;
        if (contentType == null) {
            String name = "ทะเบียนรับ,ทะเบียนส่ง,ทะเบียนอื่นๆ, หนังสือเวียน";
            String child = "1,2,3,4,5-1,2,3,4,5-1-6,7,8,9,10";
            String[] nameArray = name.split(",");
            String[] childArray = child.split("-");
            int i = 0;
            for (i = 0; i < nameArray.length; i++) {
                contentType = new WfContentType();
                contentType.setCreatedBy(this.createdBy);
                contentType.setContentTypeName(nameArray[i]);
                contentType.setContentTypeChild(childArray[i]);
                result = contentTypeService.create(contentType);
                result.setOrderNo(result.getId());
                result = contentTypeService.update(result);
            }
        }
    }
    
    private void creteContentType2() {
        WfContentType2Service contentType2Service = new WfContentType2Service();
        WfContentType2 contentType2 = contentType2Service.getById(1);
        WfContentType2 result = null;
        if (contentType2 == null) {
            String name = ",ภายใน,ภายนอก,กลาง,คำสั่ง,ประเภท1,ประเภท2,ประเภท3,ประเภท4,ประเภท5";
            String[] nameArray = name.split(",");
            int i = 0;
            for (i = 0; i < nameArray.length; i++) {
                contentType2 = new WfContentType2();
                contentType2.setCreatedBy(this.createdBy);
                contentType2.setContentType2Name(nameArray[i]);   
                result = contentType2Service.create(contentType2);
                result.setOrderNo(result.getId());
                result = contentType2Service.update(result);
            }
        }
    }
}
