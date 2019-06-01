///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.px.dms.model;
//
//import com.px.dms.entity.DmsDocument;
////import com.px.dms.model.post.DmsDocumentTextPostModel;
//import com.px.documentfile.entity.documentfile;
//import com.px.share.model.VersionModel;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiParam;
//import javax.validation.constraints.Size;
//import javax.ws.rs.FormParam;
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;
//
///**
// *
// * @author TOP
// */
//@XmlRootElement(name = "DmsDocumentText")
//@ApiModel(description = "ข้อความเอกสาร")
//public class DmsDocumentTextModel extends  VersionModel {
//
//    public static final long serialVersionUID = 0L;
//
//    @XmlElement(name = "id")
//    private int id;
//    
//    @XmlElement(name = "dmsDocumentId")
//    private DmsDocument dmsDocumentId;
//    
//    @XmlElement(name = "dmsDocumentFileId")
//    private documentfile dmsDocumentFileId;
//    
//    @XmlElement(name = "documentId")
//    @ApiParam(name = "documentId",value = "เลขที่ ที่เก็บเอกสาร",required = true)
//    private int documentId;
//    
//    @XmlElement(name = "documentFileId")
//    @ApiParam(name = "documentFileId",value = "เลขที่ เอกสารแนบ",required = true)
//    private int documentFileId;
//    
//    @XmlElement(name = "documentTextData")
//    @ApiParam(name = "documentTextData",value = "ข้อความในเอกสารแนบ",required = false)
//    private String documentTextData;
//
//    public DmsDocumentTextModel() {
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public DmsDocument getDmsDocumentId() {
//        return dmsDocumentId;
//    }
//
//    public void setDmsDocumentId(DmsDocument dmsDocumentId) {
//        this.dmsDocumentId = dmsDocumentId;
//    }
//
//    public documentfile getDmsDocumentFileId() {
//        return dmsDocumentFileId;
//    }
//
//    public void setDmsDocumentFileId(documentfile dmsDocumentFileId) {
//        this.dmsDocumentFileId = dmsDocumentFileId;
//    }
//
//    public int getDocumentId() {
//        return documentId;
//    }
//
//    public void setDocumentId(int documentId) {
//        this.documentId = documentId;
//    }
//
//    public int getDocumentFileId() {
//        return documentFileId;
//    }
//
//    public void setDocumentFileId(int documentFileId) {
//        this.documentFileId = documentFileId;
//    }
//
//    public String getDocumentTextData() {
//        return documentTextData;
//    }
//
//    public void setDocumentTextData(String documentTextData) {
//        this.documentTextData = documentTextData;
//    }
//
//  
//
//   
//    
//
//}
