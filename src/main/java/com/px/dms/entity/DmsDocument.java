/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.entity;

import com.px.share.entity.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author TOP
 */
@Entity
@Table(name = "PC_DMS_DOCUMENT")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "DMS_DOCUMENT_ID"))
})
public class DmsDocument extends BaseEntity {

    @Transient
    public static final long serialVersionUID = 0L;

    @Column(name = "DMS_FOLDER_ID", nullable = true, columnDefinition = "int default '0'")
    private int dmsFolderId;

    @Column(name = "DOCUMENT_TYPE_ID", nullable = false, columnDefinition = "int default '1'")
    private int documentTypeId;

    @Column(name = "DMS_DOCUMENT_EDIT", nullable = true, columnDefinition = "int default '0'")
    private int dmsDocumentEdit;

    @Column(name = "DMS_DOCUMENT_EDIT_DATE")
//    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private LocalDateTime dmsDocumentEditDate;

    @Column(name = "DMS_DOCUMENT_PUBLIC_STATUS", nullable = true, length = 10)
    private String dmsDocumentPublicStatus;

    @Column(name = "DMS_DOCUMENT_PUBLIC_DATE")
//    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private LocalDateTime dmsDocumentPublicDate;

    @Column(name = "DMS_DOCUMENT_NAME", nullable = true, length = 1000)
    private String dmsDocumentName;

    @Column(name = "DMS_DOCUMENT_EXPIRE_DATE")
//    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private LocalDateTime dmsDocumentExpireDate;

    @Column(name = "DMS_DOCUMENT_PRE_EXPIRE_DATE")
//    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private LocalDateTime dmsDocumentPreExpireDate;

    @Column(name = "DMS_DOCUMENT_VARCHAR01", nullable = true, length = 255)
    private String dmsDocumentVarchar01;

    @Column(name = "DMS_DOCUMENT_VARCHAR02", nullable = true, length = 255)
    private String dmsDocumentVarchar02;

    @Column(name = "DMS_DOCUMENT_VARCHAR03", nullable = true, length = 255)
    private String dmsDocumentVarchar03;

    @Column(name = "DMS_DOCUMENT_VARCHAR04", nullable = true, length = 255)
    private String dmsDocumentVarchar04;

    @Column(name = "DMS_DOCUMENT_VARCHAR05", nullable = true, length = 255)
    private String dmsDocumentVarchar05;

    @Column(name = "DMS_DOCUMENT_VARCHAR06", nullable = true, length = 255)
    private String dmsDocumentVarchar06;

    @Column(name = "DMS_DOCUMENT_VARCHAR07", nullable = true, length = 255)
    private String dmsDocumentVarchar07;

    @Column(name = "DMS_DOCUMENT_VARCHAR08", nullable = true, length = 255)
    private String dmsDocumentVarchar08;

    @Column(name = "DMS_DOCUMENT_VARCHAR09", nullable = true, length = 255)
    private String dmsDocumentVarchar09;

    @Column(name = "DMS_DOCUMENT_VARCHAR10", nullable = true, length = 255)
    private String dmsDocumentVarchar10;

    @Column(name = "DMS_DOCUMENT_TEXT01", nullable = true, length = 1000)
    private String dmsDocumentText01;

    @Column(name = "DMS_DOCUMENT_TEXT02", nullable = true, length = 1000)
    private String dmsDocumentText02;

    @Column(name = "DMS_DOCUMENT_TEXT03", nullable = true, length = 1000)
    private String dmsDocumentText03;

    @Column(name = "DMS_DOCUMENT_TEXT04", nullable = true, length = 1000)
    private String dmsDocumentText04;

    @Column(name = "DMS_DOCUMENT_TEXT05", nullable = true, length = 1000)
    private String dmsDocumentText05;

    @Column(name = "DMS_DOCUMENT_INT01", nullable = true, columnDefinition = "int default '0'")
    private int dmsDocumentInt01;

    @Column(name = "DMS_DOCUMENT_INT02", nullable = true, columnDefinition = "int default '0'")
    private int dmsDocumentInt02;

    @Column(name = "DMS_DOCUMENT_INT03", nullable = true, columnDefinition = "int default '0'")
    private int dmsDocumentInt03;

    @Column(name = "DMS_DOCUMENT_INT04", nullable = true, columnDefinition = "int default '0'")
    private int dmsDocumentInt04;

    @Column(name = "DMS_DOCUMENT_INT05", nullable = true, columnDefinition = "int default '0'")
    private int dmsDocumentIntComma;

    @Column(name = "DMC_DOCUMENT_FLOAT01", nullable = true, columnDefinition = "float default '0.0'")
    private float dmsDocumentFloat01;

    @Column(name = "DMC_DOCUMENT_FLOAT02", nullable = true, columnDefinition = "float default '0.0'")
    private float dmsDocumentFloat02;

    @Column(name = "DMS_DOCUMENT_DATETIME01")
    //
    private LocalDateTime dmsDocumentDate01;

    @Column(name = "DMS_DOCUMENT_DATETIME02")
    //
    private LocalDateTime dmsDocumentDate02;

    @Column(name = "DMS_DOCUMENT_DATETIME03")
    //
    private LocalDateTime dmsDocumentDate03;

    @Column(name = "DMS_DOCUMENT_DATETIME04")
    //
    private LocalDateTime dmsDocumentDate04;

    @Column(name = "DMS_DOCUMENT_SEC", nullable = true, length = 10)
    private int dmsDocumentSec;

    @Column(name = "F_DOC_ID", nullable = true, columnDefinition = "int default '0'")
    private int dmsDocumentFDocId;

    @Column(name = "CONVERT_ID", nullable = true, columnDefinition = "int default '0'")
    private int CONVERT_ID;

    @Column(name = "INBOX_APPOVE", nullable = true, columnDefinition = "int default '0'")
    private int inboxApprove;

    @Column(name = "wfTypeId", nullable = true, columnDefinition = "int default '0'")
    private int wfTypeId;

    @Column(name = "flowId", nullable = true, columnDefinition = "int default '0'")
    private int flowId;

    @Column(name = "DMS_SEARCH_ID", nullable = true, length = 1000)
    private String dmsSearchId;

    @Column(name = "FULL_PATH_NAME", nullable = true, length = 1000)
    private String fullPathName;

    @Column(name = "BORROW_STATUS", nullable = true, columnDefinition = "int default '0'")
    private int borrowStatus;

    @Column(name = "FULL_TEXT", columnDefinition = "TEXT")
    private String fullText;

    public DmsDocument() {
    }

    public DmsDocument(int dmsFolderId, int documentTypeId, int dmsDocumentEdit, LocalDateTime dmsDocumentEditDate, String dmsDocumentPublicStatus, LocalDateTime dmsDocumentPublicDate, String dmsDocumentName, LocalDateTime dmsDocumentExpireDate,
            String dmsDocumentVarchar01, String dmsDocumentVarchar02, String dmsDocumentVarchar03, String dmsDocumentVarchar04,
            String dmsDocumentVarchar05, String dmsDocumentVarchar06, String dmsDocumentText01, String dmsDocumentText02, String dmsDocumentText03, String dmsDocumentText04, int dmsDocumentInt01, int dmsDocumentInt02, int dmsDocumentInt03, int dmsDocumentInt04, float dmsDocumentFloat01,
            float dmsDocumentFloat02, LocalDateTime dmsDocumentDatetime01, LocalDateTime dmsDocumentDatetime02, LocalDateTime dmsDocumentDatetime03, LocalDateTime dmsDocumentDatetime04, Integer createdBy, int dmsDocumentSec, LocalDateTime dmsDocumentPreExpireDate
    ) {
        super(createdBy);
        this.dmsFolderId = dmsFolderId;
        this.documentTypeId = documentTypeId;
        this.dmsDocumentEdit = dmsDocumentEdit;
        this.dmsDocumentEditDate = dmsDocumentEditDate;
        this.dmsDocumentPublicStatus = dmsDocumentPublicStatus;
        this.dmsDocumentPublicDate = dmsDocumentPublicDate;
        this.dmsDocumentName = dmsDocumentName;
        this.dmsDocumentExpireDate = dmsDocumentExpireDate;
        this.dmsDocumentVarchar01 = dmsDocumentVarchar01;
        this.dmsDocumentVarchar02 = dmsDocumentVarchar02;
        this.dmsDocumentVarchar03 = dmsDocumentVarchar03;
        this.dmsDocumentVarchar04 = dmsDocumentVarchar04;
        this.dmsDocumentVarchar05 = dmsDocumentVarchar05;
        this.dmsDocumentVarchar06 = dmsDocumentVarchar06;
        this.dmsDocumentText01 = dmsDocumentText01;
        this.dmsDocumentText02 = dmsDocumentText02;
        this.dmsDocumentText03 = dmsDocumentText03;
        this.dmsDocumentText04 = dmsDocumentText04;
        this.dmsDocumentInt01 = dmsDocumentInt01;
        this.dmsDocumentInt02 = dmsDocumentInt02;
        this.dmsDocumentInt03 = dmsDocumentInt03;
        this.dmsDocumentInt04 = dmsDocumentInt04;
        this.dmsDocumentFloat01 = dmsDocumentFloat01;
        this.dmsDocumentFloat02 = dmsDocumentFloat02;
        this.dmsDocumentDate01 = dmsDocumentDatetime01;
        this.dmsDocumentDate02 = dmsDocumentDatetime02;
        this.dmsDocumentDate03 = dmsDocumentDatetime03;
        this.dmsDocumentDate04 = dmsDocumentDatetime04;
        this.dmsDocumentSec = dmsDocumentSec;
        this.dmsDocumentPreExpireDate = dmsDocumentPreExpireDate;

    }

    public int getDmsFolderId() {
        return dmsFolderId;
    }

    public void setDmsFolderId(int dmsFolderId) {
        this.dmsFolderId = dmsFolderId;
    }

    public int getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(int documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public int getDmsDocumentEdit() {
        return dmsDocumentEdit;
    }

    public void setDmsDocumentEdit(int dmsDocumentEdit) {
        this.dmsDocumentEdit = dmsDocumentEdit;
    }

    public LocalDateTime getDmsDocumentEditDate() {
        return dmsDocumentEditDate;
    }

    public void setDmsDocumentEditDate(LocalDateTime dmsDocumentEditDate) {
        this.dmsDocumentEditDate = dmsDocumentEditDate;
    }

    public String getDmsDocumentPublicStatus() {
        return dmsDocumentPublicStatus;
    }

    public void setDmsDocumentPublicStatus(String dmsDocumentPublicStatus) {
        this.dmsDocumentPublicStatus = dmsDocumentPublicStatus;
    }

    public LocalDateTime getDmsDocumentPublicDate() {
        return dmsDocumentPublicDate;
    }

    public void setDmsDocumentPublicDate(LocalDateTime dmsDocumentPublicDate) {
        this.dmsDocumentPublicDate = dmsDocumentPublicDate;
    }

    public String getDmsDocumentName() {
        return dmsDocumentName;
    }

    public void setDmsDocumentName(String dmsDocumentName) {
        this.dmsDocumentName = dmsDocumentName;
    }

    public LocalDateTime getDmsDocumentExpireDate() {
        return dmsDocumentExpireDate;
    }

    public void setDmsDocumentExpireDate(LocalDateTime dmsDocumentExpireDate) {
        this.dmsDocumentExpireDate = dmsDocumentExpireDate;
    }

    public String getDmsDocumentVarchar01() {
        return dmsDocumentVarchar01;
    }

    public void setDmsDocumentVarchar01(String dmsDocumentVarchar01) {
        this.dmsDocumentVarchar01 = dmsDocumentVarchar01;
    }

    public String getDmsDocumentVarchar02() {
        return dmsDocumentVarchar02;
    }

    public void setDmsDocumentVarchar02(String dmsDocumentVarchar02) {
        this.dmsDocumentVarchar02 = dmsDocumentVarchar02;
    }

    public String getDmsDocumentVarchar03() {
        return dmsDocumentVarchar03;
    }

    public void setDmsDocumentVarchar03(String dmsDocumentVarchar03) {
        this.dmsDocumentVarchar03 = dmsDocumentVarchar03;
    }

    public String getDmsDocumentVarchar04() {
        return dmsDocumentVarchar04;
    }

    public void setDmsDocumentVarchar04(String dmsDocumentVarchar04) {
        this.dmsDocumentVarchar04 = dmsDocumentVarchar04;
    }

    public String getDmsDocumentVarchar05() {
        return dmsDocumentVarchar05;
    }

    public void setDmsDocumentVarchar05(String dmsDocumentVarchar05) {
        this.dmsDocumentVarchar05 = dmsDocumentVarchar05;
    }

    public String getDmsDocumentVarchar06() {
        return dmsDocumentVarchar06;
    }

    public void setDmsDocumentVarchar06(String dmsDocumentVarchar06) {
        this.dmsDocumentVarchar06 = dmsDocumentVarchar06;
    }

    public String getDmsDocumentText01() {
        return dmsDocumentText01;
    }

    public int getDmsDocumentSec() {
        return dmsDocumentSec;
    }

    public void setDmsDocumentSec(int dmsDocumentSec) {
        this.dmsDocumentSec = dmsDocumentSec;
    }

    public void setDmsDocumentText01(String dmsDocumentText01) {
        this.dmsDocumentText01 = dmsDocumentText01;
    }

    public String getDmsDocumentText02() {
        return dmsDocumentText02;
    }

    public void setDmsDocumentText02(String dmsDocumentText02) {
        this.dmsDocumentText02 = dmsDocumentText02;
    }

    public String getDmsDocumentText03() {
        return dmsDocumentText03;
    }

    public void setDmsDocumentText03(String dmsDocumentText03) {
        this.dmsDocumentText03 = dmsDocumentText03;
    }

    public String getDmsDocumentText04() {
        return dmsDocumentText04;
    }

    public void setDmsDocumentText04(String dmsDocumentText04) {
        this.dmsDocumentText04 = dmsDocumentText04;
    }

    public int getDmsDocumentInt01() {
        return dmsDocumentInt01;
    }

    public void setDmsDocumentInt01(int dmsDocumentInt01) {
        this.dmsDocumentInt01 = dmsDocumentInt01;
    }

    public int getDmsDocumentInt02() {
        return dmsDocumentInt02;
    }

    public void setDmsDocumentInt02(int dmsDocumentInt02) {
        this.dmsDocumentInt02 = dmsDocumentInt02;
    }

    public int getDmsDocumentInt03() {
        return dmsDocumentInt03;
    }

    public void setDmsDocumentInt03(int dmsDocumentInt03) {
        this.dmsDocumentInt03 = dmsDocumentInt03;
    }

    public int getDmsDocumentInt04() {
        return dmsDocumentInt04;
    }

    public void setDmsDocumentInt04(int dmsDocumentInt04) {
        this.dmsDocumentInt04 = dmsDocumentInt04;
    }

    public float getDmsDocumentFloat01() {
        return dmsDocumentFloat01;
    }

    public void setDmsDocumentFloat01(float dmsDocumentFloat01) {
        this.dmsDocumentFloat01 = dmsDocumentFloat01;
    }

    public float getDmsDocumentFloat02() {
        return dmsDocumentFloat02;
    }

    public void setDmsDocumentFloat02(float dmsDocumentFloat02) {
        this.dmsDocumentFloat02 = dmsDocumentFloat02;
    }

    public LocalDateTime getDmsDocumentDatetime01() {
        return dmsDocumentDate01;
    }

    public void setDmsDocumentDatetime01(LocalDateTime dmsDocumentDatetime01) {
        this.dmsDocumentDate01 = dmsDocumentDatetime01;
    }

    public LocalDateTime getDmsDocumentDatetime02() {
        return dmsDocumentDate02;
    }

    public void setDmsDocumentDatetime02(LocalDateTime dmsDocumentDatetime02) {
        this.dmsDocumentDate02 = dmsDocumentDatetime02;
    }

    public LocalDateTime getDmsDocumentDatetime03() {
        return dmsDocumentDate03;
    }

    public void setDmsDocumentDatetime03(LocalDateTime dmsDocumentDatetime03) {
        this.dmsDocumentDate03 = dmsDocumentDatetime03;
    }

    public LocalDateTime getDmsDocumentDatetime04() {
        return dmsDocumentDate04;
    }

    public void setDmsDocumentDatetime04(LocalDateTime dmsDocumentDatetime04) {
        this.dmsDocumentDate04 = dmsDocumentDatetime04;
    }

    public String getDmsDocumentVarchar07() {
        return dmsDocumentVarchar07;
    }

    public void setDmsDocumentVarchar07(String dmsDocumentVarchar07) {
        this.dmsDocumentVarchar07 = dmsDocumentVarchar07;
    }

    public String getDmsDocumentVarchar08() {
        return dmsDocumentVarchar08;
    }

    public void setDmsDocumentVarchar08(String dmsDocumentVarchar08) {
        this.dmsDocumentVarchar08 = dmsDocumentVarchar08;
    }

    public String getDmsDocumentVarchar09() {
        return dmsDocumentVarchar09;
    }

    public void setDmsDocumentVarchar09(String dmsDocumentVarchar09) {
        this.dmsDocumentVarchar09 = dmsDocumentVarchar09;
    }

    public String getDmsDocumentVarchar10() {
        return dmsDocumentVarchar10;
    }

    public void setDmsDocumentVarchar10(String dmsDocumentVarchar10) {
        this.dmsDocumentVarchar10 = dmsDocumentVarchar10;
    }

    public int getDmsDocumentFDocId() {
        return dmsDocumentFDocId;
    }

    public void setDmsDocumentFDocId(int dmsDocumentFDocId) {
        this.dmsDocumentFDocId = dmsDocumentFDocId;
    }

    public int getCONVERT_ID() {
        return CONVERT_ID;
    }

    public void setCONVERT_ID(int CONVERT_ID) {
        this.CONVERT_ID = CONVERT_ID;
    }

    public int getInboxApprove() {
        return inboxApprove;
    }

    public void setInboxApprove(int inboxApprove) {
        this.inboxApprove = inboxApprove;
    }

    public int getDmsDocumentIntComma() {
        return dmsDocumentIntComma;
    }

    public void setDmsDocumentIntComma(int dmsDocumentIntComma) {
        this.dmsDocumentIntComma = dmsDocumentIntComma;
    }

    public LocalDateTime getDmsDocumentPreExpireDate() {
        return dmsDocumentPreExpireDate;
    }

    public void setDmsDocumentPreExpireDate(LocalDateTime dmsDocumentPreExpireDate) {
        this.dmsDocumentPreExpireDate = dmsDocumentPreExpireDate;
    }

    public String getDmsDocumentText05() {
        return dmsDocumentText05;
    }

    public void setDmsDocumentText05(String dmsDocumentText05) {
        this.dmsDocumentText05 = dmsDocumentText05;
    }

    public int getWfTypeId() {
        return wfTypeId;
    }

    public void setWfTypeId(int wfTypeId) {
        this.wfTypeId = wfTypeId;
    }

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    public String getDmsSearchId() {
        return dmsSearchId;
    }

    public void setDmsSearchId(String dmsSearchId) {
        this.dmsSearchId = dmsSearchId;
    }

    public String getFullPathName() {
        return fullPathName;
    }

    public void setFullPathName(String fullPathName) {
        this.fullPathName = fullPathName;
    }

    public int getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(int borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }
    
    

}
