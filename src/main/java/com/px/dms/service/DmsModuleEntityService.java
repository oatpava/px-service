package com.px.dms.service;

import com.px.dms.entity.DmsField;
import com.px.dms.entity.DmsFolder;
import com.px.dms.entity.DocumentType;
import com.px.dms.entity.DocumentTypeDetail;
import com.px.share.entity.Param;
import com.px.share.service.ParamService;
import org.apache.log4j.Logger;
import org.hibernate.boot.MetadataSources;

/**
 * For create DMS Module Entity.
 *
 * @author Mali
 */
public class DmsModuleEntityService {

    private static final Logger LOG = Logger.getLogger(DmsModuleEntityService.class.getName());
    private final int createdBy;

    public DmsModuleEntityService() {
        this.createdBy = 0;
    }

    public MetadataSources listCreateEntity(MetadataSources metadataSource) {
        //Add Entity 
      
        metadataSource.addAnnotatedClass(com.px.dms.entity.DmsDocument.class);
//        metadataSource.addAnnotatedClass(com.px.dms.entity.DmsDocumentText.class);
        metadataSource.addAnnotatedClass(com.px.dms.entity.DmsField.class);
        metadataSource.addAnnotatedClass(com.px.dms.entity.DmsFolder.class);
        metadataSource.addAnnotatedClass(com.px.dms.entity.DmsMenu.class);
        metadataSource.addAnnotatedClass(com.px.dms.entity.DocumentType.class);
        metadataSource.addAnnotatedClass(com.px.dms.entity.DocumentTypeDetail.class);
        metadataSource.addAnnotatedClass(com.px.dms.entity.ElasticSearch.class);
        metadataSource.addAnnotatedClass(com.px.dms.entity.WfDocumentType.class);
        metadataSource.addAnnotatedClass(com.px.dms.entity.borrow.class);

        return metadataSource;
    }

    public void createDefaultData() {
        System.out.println("---- createDefaultData -------");
        createDmsField();
        System.out.println(" createDmsField ");
        createDocumentType();
        System.out.println(" createDocumentType ");
        createDocumentTypeDrtail();
        System.out.println(" createDocumentTypeDrtail ");
        createDmsFolder();
//ES       
//        setupElasticSearch();
    }

    public void createDocumentType() {

        DocumentTypeService documentTypeService = new DocumentTypeService();
        DocumentType documentType1 = new DocumentType();
        DocumentType documentType2 = documentTypeService.getById(2);
        if (documentType2 == null) {
            String name = "เอกสารทั่วไป0,เอกสารทั่วไป1,เอกสารทั่วไป2,เอกสารสารบรรณ";
            String description = "รายละเอียดเอกสารทั่วไป0,รายละเอียดเอกสารทั่วไป1,รายละเอียดเอกสารทั่วไป2,เอกสารแนบหนังสือ";
            String[] nameArray = name.split(",");
            String[] descriptionArray = description.split(",");
            documentType1.setCreatedBy(1);
            int i = 0;
            for (i = 0; i < nameArray.length; i++) {
                documentType1.setDocumentTypeDescription(descriptionArray[i]);
                documentType1.setDocumentTypeName(nameArray[i]);
                documentTypeService.create(documentType1);
            }
            DocumentType documentType3 = documentTypeService.getById(1);
            documentType3.setRemovedBy(1);
            documentTypeService.update(documentType3);
        }

    }

    public void createDocumentTypeDrtail() {
        DocumentTypeDetail documentTypeDetail1 = new DocumentTypeDetail();
        DocumentTypeDetailService documentTypeDetailService = new DocumentTypeDetailService();
        DocumentTypeDetail documentTypeDetail2 = documentTypeDetailService.getById(1);
        documentTypeDetail1.setCreatedBy(1);
        if (documentTypeDetail2 == null) {
            String docType = "2,2,2,2,2,2,3,3";
            String name = "ชื่อเรื่อง,วันที่สร้างเอกสาร,ผู้สร้างเอกสาร,วันที่แก้ไขเอกสารล่าสุด,ผู้แก้ไขเอกสารล่าสุด,วันที่หมดอายุ,"
                    + "ชื่อเรื่อง,วันที่";
            String field = "1,2,3,4,5,6,"
                    + "1,2";
            documentTypeDetail1.setDocumentTypeDetailAlignment("Y");
            documentTypeDetail1.setDocumentTypeDetailColumnColor("#000000");
            documentTypeDetail1.setDocumentTypeDetailColumnWidth(200);
            documentTypeDetail1.setDocumentTypeDetailDefault("DetailDefault");
            documentTypeDetail1.setDocumentTypeDetailEdit("Y");
            documentTypeDetail1.setDocumentTypeDetailFontBold("Y");
            documentTypeDetail1.setDocumentTypeDetailFontColor("#000000");
            documentTypeDetail1.setDocumentTypeDetailFontItalic("Y");
            documentTypeDetail1.setDocumentTypeDetailFontUnderline("Y");
            documentTypeDetail1.setDocumentTypeDetailLookup(0);
            documentTypeDetail1.setDocumentTypeDetailRequire("Y");
            documentTypeDetail1.setDocumentTypeDetailSearch("Y");
            documentTypeDetail1.setDocumentTypeDetailShowComma("Y");
            documentTypeDetail1.setDocumentTypeDetailUnique("Y");
            documentTypeDetail1.setDocumentTypeDetailView("Y");
            String[] docTypeArray = docType.split(",");
            String[] nameArray = name.split(",");
            String[] fieldNameArray = field.split(",");
            int i = 0;
            for (i = 0; i < docTypeArray.length; i++) {
                documentTypeDetail1.setDocumentTypeId(Integer.parseInt(docTypeArray[i]));
                documentTypeDetail1.setDmsFieldId(Integer.parseInt(fieldNameArray[i]));
                documentTypeDetail1.setDocumentTypeDetailName(nameArray[i]);
                documentTypeDetailService.create(documentTypeDetail1);
            }

        }

    }

    public void createDmsField() {
        DmsFieldService dmsFieldService = new DmsFieldService();
        DmsField DmsField = new DmsField();
        DmsField DmsField2 = null;
        DmsField2 = dmsFieldService.getById(1);
//         System.out.println("id = "+id);
//        System.out.println("DmsField2 = " + DmsField2);
        if (DmsField2 == null) {
            String map = "documentName,createdDate,createdBy,updatedDate,updatedBy,documentExpireDate,"
                    + "documentDate01,documentDate02,documentDate03,documentDate04,"
                    + "documentFloat01,documentFloat02,"
                    + "documentInt01,documentInt02,documentInt03,documentInt04,dmsDocumentIntComma,"
                    + "documentText01,documentText02,documentText03,documentText04,documentText05,"
                    + "documentVarchar01,documentVarchar02,documentVarchar03,documentVarchar04,documentVarchar05,documentVarchar06,"
                    + "documentVarchar07,documentVarchar08,documentVarchar09,documentVarchar10,";
            String fieldName = "ชื่อเรื่อง,วันที่สร้างเอกสาร,ผู้สร้างเอกสาร,วันที่แก้ไขเอกสารล่าสุด,ผู้แก้ไขเอกสารล่าสุด,วันที่หมดอายุ,"
                    + "DATETIME01,DATETIME02,DATETIME03,DATETIME04,"
                    + "FLOAT01,FLOAT02,"
                    + "INT01,INT02,INT03,INT04,INT05,"
                    + "TEXT01,TEXT02,TEXT03,TEXT04,TEXT05,"
                    + "VARCHAR01,VARCHAR02,VARCHAR03,VARCHAR04,VARCHAR05,VARCHAR06,VARCHAR07,VARCHAR08,VARCHAR09,VARCHAR10";
            String fieldType = "VARCHAR,DATETIME,INTEGER,DATETIME,INTEGER,DATETIME,"
                    + "DATETIME,DATETIME,DATETIME,DATETIME,"
                    + "FLOAT,FLOAT,"
                    + "INTEGER,INTEGER,INTEGER,INTEGER,INTEGER,"
                    + "TEXT,TEXT,TEXT,TEXT,TEXT,"
                    + "VARCHAR,VARCHAR,VARCHAR,VARCHAR,VARCHAR,VARCHAR,VARCHAR,VARCHAR,VARCHAR,VARCHAR,";
//                    + "";

            String[] mapArray = map.split(",");
            String[] fieldNameArray = fieldName.split(",");
            String[] fieldTypeArray = fieldType.split(",");
            int i = 0;

            DmsField.setCreatedBy(1);
            for (i = 0; i < mapArray.length; i++) {
                DmsField.setDmsFieldMap(mapArray[i]);
                DmsField.setDmsFieldName(fieldNameArray[i]);
                DmsField.setDmsFieldType(fieldTypeArray[i]);
                dmsFieldService.create(DmsField);
            }
        }
    }

    public void createDmsFolder() {
        DmsFolder dmsFolder = new DmsFolder();
        DmsFolder check = null;
        DmsFolderService dmsFolderService = new DmsFolderService();
        check = dmsFolderService.getById(1);
        if (check == null) {
            dmsFolder.setCreatedBy(1);
            dmsFolder.setDmsFolderParentId(0);
            dmsFolder.setDmsFolderParentKey("฿1฿");
            dmsFolder.setDmsFolderName("ระบบจัดเก็บเอกสาร");
            dmsFolder.setDmsFolderNodeLevel(1);
            dmsFolder.setDmsFolderOrderId(1);
            dmsFolder.setDmsFolderParentId(0);
            dmsFolder.setParentKey("฿1฿");
            dmsFolder.setDmsFolderParentType("A");
            dmsFolder.setDmsFolderType("A");
            dmsFolder.setNodeLevel(1);
            dmsFolder.setParentId(0);
            dmsFolder.setDocumentTypeId(1);
            dmsFolderService.create(dmsFolder);
        }

     

        DocumentTypeDetailService documentTypeDetailService = new DocumentTypeDetailService();
        ParamService paramService = new ParamService();
        Param param = new Param();

        Param aDocumentTypeParam = paramService.getByParamName("ADOCUMENTTYPEID");
        DocumentType documentType = new DocumentType();
        DocumentTypeService documentTypeService = new DocumentTypeService();

        int docTypeId = documentTypeService.duplicateDoctype("ADocumentType");

        if (aDocumentTypeParam == null) {

            if (docTypeId == 0) {

                documentType.setDocumentTypeName("เอกสารพิเศษ");
                documentType.setDocumentTypeDescription("เอกสารพิเศษ");
                documentType.setCreatedBy(1);
                documentType = documentTypeService.create(documentType);

                param.setCreatedBy(0);
                param.setParamName("ADOCUMENTTYPEID");
                param.setParamValue(Integer.toString(documentType.getId()));
                paramService.create(param);

                DocumentTypeDetail documentTypeDetail = new DocumentTypeDetail(documentType.getId(), 1, "ชื่อเอกสาร", "Y", "Y", "Y", "Y", "Y", "Y", "#000000", "#000000", "Y", "Y", "Y", "Default", 0, 200, "Y", 1);
                documentTypeDetail = documentTypeDetailService.create(documentTypeDetail);
                DocumentTypeDetail ocumentTypeDetail2 = new DocumentTypeDetail(documentType.getId(), 21, "ประเภทเอกสาร", "Y", "Y", "Y", "Y", "Y", "Y", "#000000", "#000000", "Y", "Y", "Y", "Default", 0, 200, "Y", 1);
                documentTypeDetail = documentTypeDetailService.create(ocumentTypeDetail2);
            } else {

                param.setCreatedBy(0);
                param.setParamName("ADOCUMENTTYPEID");
                param.setParamValue(Integer.toString(docTypeId));
                paramService.create(param);

                DocumentTypeDetail documentTypeDetail = new DocumentTypeDetail(docTypeId, 1, "ชื่อเรื่อง", "Y", "Y", "Y", "Y", "Y", "Y", "#000000", "#000000", "Y", "Y", "Y", "Default", 0, 200, "Y", 1);
                documentTypeDetail = documentTypeDetailService.create(documentTypeDetail);
                DocumentTypeDetail ocumentTypeDetail2 = new DocumentTypeDetail(docTypeId, 17, "ประเภทเอกสาร", "Y", "Y", "Y", "Y", "Y", "Y", "#000000", "#000000", "Y", "Y", "Y", "Default", 0, 200, "Y", 1);
                documentTypeDetail = documentTypeDetailService.create(ocumentTypeDetail2);

            }

        }
    }

//ES    
//     public void setupElasticSearch(){
//        try {
//             DmsSearchService DmsSearchService= new DmsSearchService();
//        
//            if(!DmsSearchService.isIndexExist()){
//                LOG.info("searchCCIndex not found!! Auto create searchCCIndex. ");
//                long t1 = -System.currentTimeMillis();
//                boolean result = DmsSearchService.setup();
//                if(result==true){
//                    LOG.info("searchCCIndex created successfully."+(t1+System.currentTimeMillis()));
//                }
//            }
//        } catch(Exception e){
//            LOG.info("Exception:SearchCCModuleEntityService[setupElasticSearch] = "+e);
//        }
//    }
}
