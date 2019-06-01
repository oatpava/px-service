/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.documentfile.entity;

import com.px.share.entity.BaseEntity;
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
@Table(name = "PC_DOCUMENT_FILE")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "DOCUMENT_FILE_ID"))
})
public class DocumentFile extends BaseEntity {

    @Transient
    public static final long serialVersionUID = 0L;

    @Column(name = "SUB_MODULE_ID", nullable = false, length = 10)
    private int moduleId;

    @Column(name = "LINK_ID", nullable = false, length = 10)
    private int linkId;

    @Column(name = "LINK_ID2", nullable = false, length = 10)
    private int linkId2;

    @Column(name = "LINK_TYPE", nullable = true, length = 255)
    private String linkType;

    @Column(name = "FILE_ORDER", nullable = true, length = 10)
    private int fileOrder;

    @Column(name = "DOCUMENT_FILE_NAME", nullable = true, length = 255)
    private String documentFileName;

    @Column(name = "DOCUMENT_FILE_TYPE", nullable = true, length = 100)
    private String documentFileType;

    @Column(name = "CAPTION", nullable = true, length = 255)
    private String caption;

    @Column(name = "OWNER_NAME", nullable = true, length = 255)
    private String ownerName;

    @Column(name = "IS_TEXT", nullable = true)
    private int isText;

    @Column(name = "IMAGE_COUNT", nullable = true)
    private int imageCount;

    @Column(name = "REFERENCE_ID", nullable = true, length = 10)
    private int referenceId;

    @Column(name = "DOCUMENT_FILE_SEC", nullable = true, length = 10)
    private int documentFileSec;

    @Column(name = "FILE_SIZE", nullable = true, length = 100)
    private int fileSize;

    @Column(name = "MD5", nullable = true, length = 255)
    private String md5;

    @Column(name = "GROUP_FILE_ID", nullable = true, length = 10)
    private int groupFileId;
    
    public DocumentFile() {
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public int getLinkId2() {
        return linkId2;
    }

    public void setLinkId2(int linkId2) {
        this.linkId2 = linkId2;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public int getFileOrder() {
        return fileOrder;
    }

    public void setFileOrder(int fileOrder) {
        this.fileOrder = fileOrder;
    }

    public String getDocumentFileName() {
        return documentFileName;
    }

    public void setDocumentFileName(String documentFileName) {
        this.documentFileName = documentFileName;
    }

    public String getDocumentFileType() {
        return documentFileType;
    }

    public void setDocumentFileType(String documentFileType) {
        this.documentFileType = documentFileType;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getIsText() {
        return isText;
    }

    public void setIsText(int isText) {
        this.isText = isText;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public int getDocumentFileSec() {
        return documentFileSec;
    }

    public void setDocumentFileSec(int documentFileSec) {
        this.documentFileSec = documentFileSec;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getGroupFileId() {
        return groupFileId;
    }

    public void setGroupFileId(int groupFileId) {
        this.groupFileId = groupFileId;
    }
    
    
    

}
