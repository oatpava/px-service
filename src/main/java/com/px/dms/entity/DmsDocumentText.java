///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.px.dms.entity;
//
////import com.px.core.entity.documentfile.DocumentFile;
//import com.px.documentfile.entity.documentfile;
//import com.px.share.entity.BaseEntity;
//import javax.persistence.AttributeOverride;
//import javax.persistence.AttributeOverrides;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.Table;
//import javax.persistence.Transient;
//
///**
// *
// * @author TOP
// */
//@Entity
//@Table(name = "PC_DMS_DOCUMENT_TEXT")
//@AttributeOverrides({
//    @AttributeOverride(name="id", column=@Column(name="DMS_DOCUMENT_TEXT_ID"))
//})
//public class DmsDocumentText extends BaseEntity{
//    @Transient
//    public static final long serialVersionUID = 0L;
//    
////    @ManyToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name="DMS_DOCUMENT_ID", nullable = false)
//    private DmsDocument dmsDocumentId;
//    
////    @ManyToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name="DMS_DOCUMENT_FILE_ID", nullable = false)
//    private documentfile dmsDocumentFileId;
//    
//    @Column(name="DMS_DOCUMENT_TEXT_DATA", nullable = true, columnDefinition="Text")
//    private String dmsDocumentTextData;
//
//    public DmsDocumentText() {
//    }
//
//    public DmsDocumentText(DmsDocument dmsDocumentId, documentfile dmsDocumentFileId, String dmsDocumentTextData) {
//        this.dmsDocumentId = dmsDocumentId;
//        this.dmsDocumentFileId = dmsDocumentFileId;
//        this.dmsDocumentTextData = dmsDocumentTextData;
//    }
//
//    public DmsDocumentText(DmsDocument dmsDocumentId, documentfile dmsDocumentFileId, String dmsDocumentTextData, Integer createdBy) {
//        super(createdBy);
//        this.dmsDocumentId = dmsDocumentId;
//        this.dmsDocumentFileId = dmsDocumentFileId;
//        this.dmsDocumentTextData = dmsDocumentTextData;
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
//    public String getDmsDocumentTextData() {
//        return dmsDocumentTextData;
//    }
//
//    public void setDmsDocumentTextData(String dmsDocumentTextData) {
//        this.dmsDocumentTextData = dmsDocumentTextData;
//    }
//    
//    
//    
//}
