/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.entity;

import com.px.share.entity.BaseTreeEntity;
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
@Table(name = "PC_DMS_FOLDER")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "DMS_FOLDER_ID"))
})
public class DmsFolder extends BaseTreeEntity {

    @Transient
    private static final long serialVersionUID = 0L;

    @Column(name = "DOCUMENT_TYPE_ID", nullable = false, columnDefinition = "int default '1'")
    private int documentTypeId;

    @Column(name = "DMS_FOLDER_NAME", nullable = true, length = 255)
    private String dmsFolderName;

    @Column(name = "DMS_FOLDER_TYPE", nullable = true, length = 10)
    private String dmsFolderType;

    @Column(name = "DMS_FOLDER_DESCRIPTION", nullable = true, length = 4000)
    private String dmsFolderDescription;

    @Column(name = "DMS_FOLDER_PARENT_ID", nullable = true, columnDefinition = "int default '0'")
    private int dmsFolderParentId;

    @Column(name = "DMS_FOLDER_PARENT_TYPE", nullable = true, length = 10)
    private String dmsFolderParentType;

    @Column(name = "DMS_FOLDER_PARENT_KEY", nullable = true, length = 255)
    private String dmsFolderParentKey;

    @Column(name = "DMS_FOLDER_ORDER_ID", nullable = false, columnDefinition = "float default '0.0'")
    private float dmsFolderOrderId;

    @Column(name = "DMS_FOLDER_TYPE_EXPIRE", nullable = true, length = 10)
    private String dmsFolderTypeExpire;

    @Column(name = "DMS_FOLDER_TYPE_EXPIRE_NUMBER", nullable = true, columnDefinition = "int default '0'")
    private int dmsFolderTypeExpireNumber;

    @Column(name = "DMS_FOLDER_NODE_LEVEL", nullable = true, columnDefinition = "int default '0'")
    private int dmsFolderNodeLevel;

    @Column(name = "iconColor", nullable = true, length = 255)
    private String iconColor;

    @Column(name = "icon", nullable = true, length = 255)
    private String icon;

    @Column(name = "DMS_FOLDER_TYPE_PRE_EXPIRE", nullable = true, length = 10)
    private String dmsFolderTypePreExpire;

    @Column(name = "TYPE_PRE_EXPIRE_NUMBER", nullable = true, columnDefinition = "int default '0'")
    private int dmsFolderTypePreExpireNumber;
    
    @Column(name = "DMS_USER_PRE_EXPIRE", nullable = true, columnDefinition = "int default '0'")
    private int dmsUserPreExpire;
    
    @Column(name = "DMS_EMAIL_USER_PRE_EXPIRE", nullable = true, length = 255)
    private String dmsEmailUserPreExpire;
    
    @Column(name="WF_DOCUMENT_TYPE_CODE",nullable = true,length = 15)
    private String wfDocumentTypeCode;
    
     @Column(name = "DMS_SEARCH_ID", nullable = true, length = 1000)
    private String dmsSearchId;
    
     @Column(name = "FULL_PATH_NAME", nullable = true, length = 1000)
    private String fullPathName;

    public DmsFolder() {
    }

    public DmsFolder(Integer createdBy, int documentTypeId, String dmsFolderName, String dmsFolderType, String dmsFolderDescription, int dmsFolderParentId, String dmsFolderParentType, String dmsFolderTypeExpire, int dmsFolderTypeExpireNumber, String dmsFolderTypePreExpire, int dmsFolderTypePreExpireNumber,int dmsUserPreExpire,String dmsEmailUserPreExpire,String wfDocumentTypeCode) {
        super(createdBy);
        this.documentTypeId = documentTypeId;
        this.dmsFolderName = dmsFolderName;
        this.dmsFolderType = dmsFolderType;
        this.dmsFolderDescription = dmsFolderDescription;
        this.dmsFolderParentId = dmsFolderParentId;
        this.dmsFolderParentType = dmsFolderParentType;
        this.dmsFolderTypeExpire = dmsFolderTypeExpire;
        this.dmsFolderTypeExpireNumber = dmsFolderTypeExpireNumber;
        this.dmsFolderTypePreExpire = dmsFolderTypePreExpire;
        this.dmsFolderTypePreExpireNumber = dmsFolderTypePreExpireNumber;
        this.dmsUserPreExpire = dmsUserPreExpire;
        this.dmsEmailUserPreExpire = dmsEmailUserPreExpire;
        this.wfDocumentTypeCode=wfDocumentTypeCode;

    }

    public int getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(int documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getDmsFolderName() {
        return dmsFolderName;
    }

    public void setDmsFolderName(String dmsFolderName) {
        this.dmsFolderName = dmsFolderName;
    }

    public String getDmsFolderType() {
        return dmsFolderType;
    }

    public void setDmsFolderType(String dmsFolderType) {
        this.dmsFolderType = dmsFolderType;
    }

    public String getDmsFolderDescription() {
        return dmsFolderDescription;
    }

    public void setDmsFolderDescription(String dmsFolderDescription) {
        this.dmsFolderDescription = dmsFolderDescription;
    }

    public int getDmsFolderParentId() {
        return dmsFolderParentId;
    }

    public void setDmsFolderParentId(int dmsFolderParentId) {
        this.dmsFolderParentId = dmsFolderParentId;
    }

    public String getDmsFolderParentType() {
        return dmsFolderParentType;
    }

    public void setDmsFolderParentType(String dmsFolderParentType) {
        this.dmsFolderParentType = dmsFolderParentType;
    }

    public String getDmsFolderParentKey() {
        return dmsFolderParentKey;
    }

    public void setDmsFolderParentKey(String dmsFolderParentKey) {
        this.dmsFolderParentKey = dmsFolderParentKey;
    }

    public float getDmsFolderOrderId() {
        return dmsFolderOrderId;
    }

    public void setDmsFolderOrderId(float dmsFolderOrderId) {
        this.dmsFolderOrderId = dmsFolderOrderId;
    }

    public String getDmsFolderTypeExpire() {
        return dmsFolderTypeExpire;
    }

    public void setDmsFolderTypeExpire(String dmsFolderTypeExpire) {
        this.dmsFolderTypeExpire = dmsFolderTypeExpire;
    }

    public int getDmsFolderTypeExpireNumber() {
        return dmsFolderTypeExpireNumber;
    }

    public void setDmsFolderTypeExpireNumber(int dmsFolderTypeExpireNumber) {
        this.dmsFolderTypeExpireNumber = dmsFolderTypeExpireNumber;
    }

    public int getDmsFolderNodeLevel() {
        return dmsFolderNodeLevel;
    }

    public void setDmsFolderNodeLevel(int dmsFolderNodeLevel) {
        this.dmsFolderNodeLevel = dmsFolderNodeLevel;
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDmsFolderTypePreExpire() {
        return dmsFolderTypePreExpire;
    }

    public void setDmsFolderTypePreExpire(String dmsFolderTypePreExpire) {
        this.dmsFolderTypePreExpire = dmsFolderTypePreExpire;
    }

    public int getDmsFolderTypePreExpireNumber() {
        return dmsFolderTypePreExpireNumber;
    }

    public void setDmsFolderTypePreExpireNumber(int dmsFolderTypePreExpireNumber) {
        this.dmsFolderTypePreExpireNumber = dmsFolderTypePreExpireNumber;
    }

    public int getDmsUserPreExpire() {
        return dmsUserPreExpire;
    }

    public void setDmsUserPreExpire(int dmsUserPreExpire) {
        this.dmsUserPreExpire = dmsUserPreExpire;
    }

    public String getDmsEmailUserPreExpire() {
        return dmsEmailUserPreExpire;
    }

    public void setDmsEmailUserPreExpire(String dmsEmailUserPreExpire) {
        this.dmsEmailUserPreExpire = dmsEmailUserPreExpire;
    }

    public String getWfDocumentTypeCode() {
        return wfDocumentTypeCode;
    }

    public void setWfDocumentTypeCode(String wfDocumentTypeCode) {
        this.wfDocumentTypeCode = wfDocumentTypeCode;
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
    
    
    
    

}
