/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.admin.model.UserProfileModel;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TOP
 */
@XmlRootElement(name = "Document")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "เอกสาร")
public class DmsDocumentModel extends VersionModel {

    public static final long serialVersionUID = 0L;

    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสเอกสาร", defaultValue = "0", required = false)
    @Expose
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "createdDate")
    @ApiParam(name = "createdDate", example = "01/01/2559 07:30:45", value = "วันที่สร้าง", defaultValue = "01/01/2559 07:30:45", hidden = true, required = false)
    @Since(1.0)
    @Expose
    private String createdDate;
    
    @XmlElement(name = "createdBy")
    @ApiParam(name = "createdBy", example = "0", value = "id ผู้สร้าง", required = false)
    @Since(1.0)
    @Expose
    private int createdBy;

    @XmlElement(name = "removedDate")
    @ApiParam(name = "removedDate", example = "01/01/2559 07:30:45", value = "วันที่สร้าง", defaultValue = "01/01/2559 07:30:45", hidden = true, required = false)
    @Since(1.0)
    @Expose
    private String removedDate;

    @XmlElement(name = "removedBy")
    @ApiParam(name = "removedBy", example = "0", value = "id ผู้ลบ", required = false)
    @Since(1.0)
    @Expose
    private int removedBy;
    
     @XmlElement(name = "updatedDate")
    @ApiParam(name = "updatedDate", example = "01/01/2559 07:30:45", value = "วันที่สร้าง", defaultValue = "01/01/2559 07:30:45", hidden = true, required = false)
    @Since(1.0)
    @Expose
    private String updatedDate;

    @XmlElement(name = "updatedBy")
    @ApiParam(name = "updatedBy", example = "0", value = "id ผู้แก้ไข", required = false)
    @Since(1.0)
    @Expose
    private int updatedBy;
    
    @XmlElement(name = "documentPublicDate")
    @ApiParam(name = "documentPublicDate", example = "01/01/2559 07:30:45", value = "documentPublicDate", defaultValue = "01/01/2559 07:30:45", hidden = true, required = false)
    @Expose
    @Since(1.0)
    private String documentPublicDate;

    @XmlElement(name = "documentExpireDate")
    @ApiParam(name = "documentExpireDate", example = "01/01/2559 07:30:45", value = "วันหมดอายุ", defaultValue = "01/01/2559 07:30:45", hidden = true, required = false)
    @Expose
    @Since(1.0)
    private String documentExpireDate;
    
    @XmlElement(name = "dmsDocumentPreExpireDate")
    @ApiParam(name = "dmsDocumentPreExpireDate", example = "01/01/2559 07:30:45", value = "วันก่อนวันหมดอายุ", defaultValue = "01/01/2559 07:30:45", hidden = true, required = false)
    @Expose
    @Since(1.0)
    private String dmsDocumentPreExpireDate;

    @ApiParam(name = "documentDate01", example = "01/01/2559 07:30:45", value = "documentDate01", defaultValue = "01/01/2559 07:30:45", required = false)
    @XmlElement(name = "documentDate01")
    @Expose
    @Since(1.0)
    private String documentDate01;

    @XmlElement(name = "documentDate02")
    @ApiParam(name = "documentDate02", example = "01/01/2559 07:30:45", value = "documentDate02", defaultValue = "01/01/2559 07:30:45", required = false)
    @Expose
    @Since(1.0)
    private String documentDate02;

    @XmlElement(name = "documentDate03")
    @ApiParam(name = "documentDate03", example = "01/01/2559 07:30:45", value = "documentDate03", defaultValue = "01/01/2559 07:30:45", required = false)
    @Expose
    @Since(1.0)
    private String documentDate03;

    @XmlElement(name = "documentDate04")
    @ApiParam(name = "documentDate04", example = "01/01/2559 07:30:45", value = "documentDate04", defaultValue = "01/01/2559 07:30:45", required = false)
    @Expose
    @Since(1.0)
    private String documentDate04;

    @XmlElement(name = "documentTypeId")
    @ApiParam(name = "documentTypeId", value = "documentTypeId", required = true)
    @Since(1.0)
    @Expose
    private int documentTypeId;

    @XmlElement(name = "documentName")
    @ApiParam(name = "documentName", value = "documentName", required = false)
    @Since(1.0)
    @Expose
    private String documentName;

    @XmlElement(name = "documentPublicStatus")
    @ApiParam(name = "documentPublicStatus", value = "documentPublicStatus", required = false)
    @Since(1.0)
    @Expose
    private String documentPublicStatus;

    @XmlElement(name = "documentFolderId")
    @ApiParam(name = "documentFolderId", value = "documentFolderId", required = true)
    @Since(1.0)
    @Expose
    private int documentFolderId;

    @XmlElement(name = "documentFloat01")
    @ApiParam(name = "documentFloat01", value = "documentFloat01", required = false)
    @Since(1.0)
    @Expose
    private float documentFloat01;

    @XmlElement(name = "documentFloat02")
    @ApiParam(name = "documentFloat02", value = "documentFloat02", required = false)
    @Since(1.0)
    @Expose
    private float documentFloat02;

    @XmlElement(name = "documentVarchar01")
    @ApiParam(name = "documentVarchar01", value = "documentVarchar01", required = false)
    @Since(1.0)
    @Expose
    private String documentVarchar01;

    @XmlElement(name = "documentVarchar02")
    @ApiParam(name = "documentVarchar02", value = "documentVarchar02", required = false)
    @Since(1.0)
    @Expose
    private String documentVarchar02;

    @XmlElement(name = "documentVarchar03")
    @ApiParam(name = "documentVarchar03", value = "documentVarchar03", required = false)
    @Since(1.0)
    @Expose
    private String documentVarchar03;

    @XmlElement(name = "documentVarchar04")
    @ApiParam(name = "documentVarchar04", value = "documentVarchar04", required = false)
    @Since(1.0)
    @Expose
    private String documentVarchar04;

    @XmlElement(name = "documentVarchar05")
    @ApiParam(name = "documentVarchar05", value = "documentVarchar05", required = false)
    @Since(1.0)
    @Expose
    private String documentVarchar05;

    @XmlElement(name = "documentVarchar06")
    @ApiParam(name = "documentVarchar06", value = "documentVarchar06", required = false)
    @Since(1.0)
    @Expose
    private String documentVarchar06;

    @XmlElement(name = "documentVarchar07")
    @ApiParam(name = "documentVarchar07", value = "documentVarchar07", required = false)
    @Since(1.0)
    @Expose
    private String documentVarchar07;

    @XmlElement(name = "documentVarchar08")
    @ApiParam(name = "documentVarchar08", value = "documentVarchar08", required = false)
    @Since(1.0)
    @Expose
    private String documentVarchar08;

    @XmlElement(name = "documentVarchar09")
    @ApiParam(name = "documentVarchar09", value = "documentVarchar09", required = false)
    @Since(1.0)
    @Expose
    private String documentVarchar09;

    @XmlElement(name = "documentVarchar10")
    @ApiParam(name = "documentVarchar10", value = "documentVarchar10", required = false)
    @Since(1.0)
    @Expose
    private String documentVarchar10;

    @XmlElement(name = "documentText01")
    @ApiParam(name = "documentText01", value = "documentText01", required = false)
    @Since(1.0)
    @Expose
    private String documentText01;

    @XmlElement(name = "documentText02")
    @ApiParam(name = "documentText02", value = "documentText02", required = false)
    @Since(1.0)
    @Expose
    private String documentText02;

    @XmlElement(name = "documentText03")
    @ApiParam(name = "documentText03", value = "documentText03", required = false)
    @Since(1.0)
    @Expose
    private String documentText03;
    
    @XmlElement(name = "documentText04")
    @ApiParam(name = "documentText04", value = "documentText04", required = false)
    @Since(1.0)
    @Expose
    private String documentText04;
    
    @XmlElement(name = "documentText05")
    @ApiParam(name = "documentText05", value = "documentText05", required = false)
    @Since(1.0)
    @Expose
    private String documentText05;
    
    @XmlElement(name = "documentInt01")
    @ApiParam(name = "documentInt01", value = "documentInt01", required = false)
    @Since(1.0)
    @Expose
    private int documentInt01;

    @XmlElement(name = "documentInt02")
    @ApiParam(name = "documentInt02", value = "documentInt02", required = false)
    @Since(1.0)
    @Expose
    private int documentInt02;

    @XmlElement(name = "documentInt03")
    @ApiParam(name = "documentInt03", value = "documentInt03", required = false)
    @Since(1.0)
    @Expose
    private int documentInt03;

    @XmlElement(name = "documentInt04")
    @ApiParam(name = "documentInt04", value = "documentInt04", required = false)
    @Since(1.0)
    @Expose
    private int documentInt04;

    @XmlElement(name = "dmsDocumentIntComma")
    @ApiParam(name = "dmsDocumentIntComma", value = "dmsDocumentIntComma", required = false)
    @Since(1.0)
    @Expose
    private int dmsDocumentIntComma;

    @XmlElement(name = "dmsDocumentSec")
    @ApiParam(name = "dmsDocumentSec", value = "dmsDocumentSec", required = false)
    @Since(1.0)
    @Expose
    private int dmsDocumentSec;

    @XmlElement(name = "expType")
    @ApiParam(name = "expType", value = "Y=ปี , M =เดือน ,D=วัน สัมพันกัน expNumber เอาไปสร้างวันหมดอายุ แบบ auto", required = false)
    @Since(1.0)
    @Expose
    private String expType;

    @XmlElement(name = "expNumber")
    @ApiParam(name = "expNumber", value = "expNumber", required = false)
    @Since(1.0)
    @Expose
    private int expNumber;
    
    
    @XmlElement(name = "userProfileCreate")
    @ApiParam(name = "userProfileCreate", value = "userProfileCreate", required = false)
    @Expose
    @Since(1.0)
    private UserProfileModel userProfileCreate;
    
    @XmlElement(name = "userProfileUpdate")
    @ApiParam(name = "userProfileUpdate", value = "userProfileUpdate", required = false)
    @Expose
    @Since(1.0)
    private UserProfileModel userProfileUpdate;
    
    @XmlElement(name = "userProfileDel")
    @ApiParam(name = "userProfileDel", value = "userProfileDel", required = false)
    @Expose
    @Since(1.0)
    private UserProfileModel userProfileDel;
    
    
    @XmlElement(name = "isExp")
    @ApiParam(name = "isExp", example = "N", value = "Y= เอกสารหมดอายุ,N= เอกสารยังไม่ไหมอายุ", hidden = true, required = false)
    @Expose
    @Since(1.0)
    private String isExp;
    
    @XmlElement(name = "wfTypeId")
    @ApiParam(name = "wfTypeId", value = "wfTypeId", required = false)
    @Since(1.0)
    @Expose
    private int wfTypeId;
    
    @XmlElement(name = "flowId")
    @ApiParam(name = "flowId", value = "flowId", required = false)
    @Since(1.0)
    @Expose
    private int flowId;
    
    @XmlElement(name = "dmsSearchId")
    @ApiParam(name = "dmsSearchId", value = "dmsSearchId", required = false)
    @Since(1.0)
    @Expose
    private String dmsSearchId;
    
    @XmlElement(name = "fullPathName")
    @ApiParam(name = "fullPathName", value = "fullPathName", required = false)
    @Since(1.0)
    @Expose
    private String fullPathName;
    
    @XmlElement(name = "borrowStatus")
    @ApiParam(name = "borrowStatus", value = "borrowStatus", required = false)
    @Since(1.0)
    @Expose
    private int borrowStatus;
    
    

    public DmsDocumentModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสเอกสาร", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentPublicDate() {
        return documentPublicDate;
    }

    @ApiModelProperty(name = "documentPublicDate", example = "01/01/2559 07:30:45", dataType = "string", value = "documentPublicDate", required = false)
    public void setDocumentPublicDate(String documentPublicDate) {
        this.documentPublicDate = documentPublicDate;
    }
       
       

    public String getDocumentExpireDate() {
        return documentExpireDate;
    }

    @ApiModelProperty(name = "documentExpireDate", example = "01/01/2559 07:30:45", dataType = "string", value = "documentExpireDate",  required = false)
    public void setDocumentExpireDate(String documentExpireDate) {
        this.documentExpireDate = documentExpireDate;
    }

    public String getDocumentDate01() {
        return documentDate01;
    }

    @ApiModelProperty(name = "documentDate01", example = "01/01/2559 07:30:45", dataType = "string", value = "documentDate01", required = false)
    public void setDocumentDate01(String documentDate01) {
        this.documentDate01 = documentDate01;
    }

    public String getDocumentDate02() {
        return documentDate02;
    }

    @ApiModelProperty(name = "documentDate02", example = "01/01/2559 07:30:45", dataType = "string", value = "documentDate02", required = false)
    public void setDocumentDate02(String documentDate02) {
        this.documentDate02 = documentDate02;
    }

    public String getDocumentDate03() {
        return documentDate03;
    }

    @ApiModelProperty(name = "documentDate03", example = "01/01/2559 07:30:45", dataType = "string", value = "documentDate03", required = false)
    public void setDocumentDate03(String documentDate03) {
        this.documentDate03 = documentDate03;
    }

    public String getDocumentDate04() {
        return documentDate04;
    }

    @ApiModelProperty(name = "documentDate04", example = "01/01/2559 07:30:45", dataType = "string", value = "documentDate04",  required = false)
    public void setDocumentDate04(String documentDate04) {
        this.documentDate04 = documentDate04;
    }

    public int getDocumentTypeId() {
        return documentTypeId;
    }

    @ApiModelProperty(name = "documentTypeId", example = "1", dataType = "int", value = "ประเภทเอกสาร", required = true)
    public void setDocumentTypeId(int documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getDocumentName() {
        return documentName;
    }

    @ApiModelProperty(name = "documentName", example = "ทดสอบชื่อ", dataType = "string", value = "ชื่อเอกสาร", required = false)
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentPublicStatus() {
        return documentPublicStatus;
    }

    @ApiModelProperty(name = "documentPublicStatus", example = "1", dataType = "string", value = "documentPublicStatus", required = false)
    public void setDocumentPublicStatus(String documentPublicStatus) {
        this.documentPublicStatus = documentPublicStatus;
    }

    public int getDocumentFolderId() {
        return documentFolderId;
    }

    @ApiModelProperty(name = "documentFolderId", example = "1", dataType = "string", value = "id ที่เก็บเอกสาร", required = true)
    public void setDocumentFolderId(int documentFolderId) {
        this.documentFolderId = documentFolderId;
    }

    public float getDocumentFloat01() {
        return documentFloat01;
    }

    @ApiModelProperty(name = "documentFloat01", example = "01", dataType = "float", value = "documentFloat01", required = false)
    public void setDocumentFloat01(float documentFloat01) {
        this.documentFloat01 = documentFloat01;
    }

    public float getDocumentFloat02() {
        return documentFloat02;
    }

    @ApiModelProperty(name = "documentFloat02", example = "02", dataType = "float", value = "documentFloat02", required = false)
    public void setDocumentFloat02(float documentFloat02) {
        this.documentFloat02 = documentFloat02;
    }

    public String getDocumentVarchar01() {
        return documentVarchar01;
    }

    @ApiModelProperty(name = "documentVarchar01", example = "Varchar01", dataType = "string", value = "documentVarchar01", required = false)
    public void setDocumentVarchar01(String documentVarchar01) {
        this.documentVarchar01 = documentVarchar01;
    }

    public String getDocumentVarchar02() {
        return documentVarchar02;
    }

    @ApiModelProperty(name = "documentVarchar02", example = "Varchar02", dataType = "string", value = "documentVarchar02", required = false)
    public void setDocumentVarchar02(String documentVarchar02) {
        this.documentVarchar02 = documentVarchar02;
    }

    public String getDocumentVarchar03() {
        return documentVarchar03;
    }

    @ApiModelProperty(name = "documentVarchar03", example = "Varchar03", dataType = "string", value = "documentVarchar03", required = false)
    public void setDocumentVarchar03(String documentVarchar03) {
        this.documentVarchar03 = documentVarchar03;
    }

    public String getDocumentVarchar04() {
        return documentVarchar04;
    }

    @ApiModelProperty(name = "documentVarchar04", example = "Varchar04", dataType = "string", value = "documentVarchar04", required = false)
    public void setDocumentVarchar04(String documentVarchar04) {
        this.documentVarchar04 = documentVarchar04;
    }

    public String getDocumentVarchar05() {
        return documentVarchar05;
    }

    @ApiModelProperty(name = "documentVarchar05", example = "Varchar05", dataType = "string", value = "documentVarchar05", required = false)
    public void setDocumentVarchar05(String documentVarchar05) {
        this.documentVarchar05 = documentVarchar05;
    }

    public String getDocumentVarchar06() {
        return documentVarchar06;
    }

    @ApiModelProperty(name = "documentVarchar06", example = "Varchar06", dataType = "string", value = "documentVarchar06", required = false)
    public void setDocumentVarchar06(String documentVarchar06) {
        this.documentVarchar06 = documentVarchar06;
    }

    public String getDocumentVarchar07() {
        return documentVarchar07;
    }

    @ApiModelProperty(name = "documentVarchar07", example = "Varchar07", dataType = "string", value = "documentVarchar07", required = false)
    public void setDocumentVarchar07(String documentVarchar07) {
        this.documentVarchar07 = documentVarchar07;
    }

    public String getDocumentVarchar08() {
        return documentVarchar08;
    }

    @ApiModelProperty(name = "documentVarchar08", example = "Varchar08", dataType = "string", value = "documentVarchar08", required = false)
    public void setDocumentVarchar08(String documentVarchar08) {
        this.documentVarchar08 = documentVarchar08;
    }

    public String getDocumentVarchar09() {
        return documentVarchar09;
    }

    @ApiModelProperty(name = "documentVarchar09", example = "Varchar09", dataType = "string", value = "documentVarchar09", required = false)
    public void setDocumentVarchar09(String documentVarchar09) {
        this.documentVarchar09 = documentVarchar09;
    }

    public String getDocumentVarchar10() {
        return documentVarchar10;
    }

    @ApiModelProperty(name = "documentVarchar10", example = "Varchar10", dataType = "string", value = "documentVarchar10", required = false)
    public void setDocumentVarchar10(String documentVarchar10) {
        this.documentVarchar10 = documentVarchar10;
    }

    public String getDocumentText01() {
        return documentText01;
    }

    @ApiModelProperty(name = "documentText01", example = "Text01", dataType = "string", value = "documentText01", required = false)
    public void setDocumentText01(String documentText01) {
        this.documentText01 = documentText01;
    }

    public String getDocumentText02() {
        return documentText02;
    }

    @ApiModelProperty(name = "documentText02", example = "Text02", dataType = "string", value = "documentText02", required = false)
    public void setDocumentText02(String documentText02) {
        this.documentText02 = documentText02;
    }

    public String getDocumentText03() {
        return documentText03;
    }

    @ApiModelProperty(name = "documentText03", example = "Text03", dataType = "string", value = "documentText03", required = false)
    public void setDocumentText03(String documentText03) {
        this.documentText03 = documentText03;
    }

    public String getDocumentText04() {
        return documentText04;
    }

    @ApiModelProperty(name = "documentText04", example = "Text04", dataType = "string", value = "documentText04", required = false)
    public void setDocumentText04(String documentText04) {
        this.documentText04 = documentText04;
    }
    
    public String getDocumentText05() {
        return documentText05;
    }

    @ApiModelProperty(name = "documentText05", example = "Text05", dataType = "string", value = "documentText05", required = false)
    public void setDocumentText05(String documentText05) {
        this.documentText05 = documentText05;
    }

    public int getDocumentInt01() {
        return documentInt01;
    }

    @ApiModelProperty(name = "documentInt01", example = "01", dataType = "int", value = "documentInt01", required = false)
    public void setDocumentInt01(int documentInt01) {
        this.documentInt01 = documentInt01;
    }

    public int getDocumentInt02() {
        return documentInt02;
    }

    @ApiModelProperty(name = "documentInt02", example = "02", dataType = "int", value = "documentInt02", required = false)
    public void setDocumentInt02(int documentInt02) {
        this.documentInt02 = documentInt02;
    }

    public int getDocumentInt03() {
        return documentInt03;
    }

    @ApiModelProperty(name = "documentInt03", example = "03", dataType = "int", value = "documentInt03", required = false)
    public void setDocumentInt03(int documentInt03) {
        this.documentInt03 = documentInt03;
    }

    public int getDocumentInt04() {
        return documentInt04;
    }

    @ApiModelProperty(name = "documentInt04", example = "04", dataType = "int", value = "documentInt04", required = false)
    public void setDocumentInt04(int documentInt04) {
        this.documentInt04 = documentInt04;
    }

    public int getDmsDocumentIntComma() {
        return dmsDocumentIntComma;
    }

    @ApiModelProperty(name = "dmsDocumentIntComma", example = "1", dataType = "int", value = "dmsDocumentIntComma", required = false)
    public void setDmsDocumentIntComma(int dmsDocumentIntComma) {
        this.dmsDocumentIntComma = dmsDocumentIntComma;
    }

    public int getDmsDocumentSec() {
        return dmsDocumentSec;
    }

    @ApiModelProperty(name = "dmsDocumentSec", example = "1", dataType = "int", value = "ชั้นความลับ", required = false)
    public void setDmsDocumentSec(int dmsDocumentSec) {
        this.dmsDocumentSec = dmsDocumentSec;
    }

    public String getExpType() {
        return expType;
    }

    @ApiModelProperty(name = "expType", example = "M", dataType = "String", value = "Y=ปี , M =เดือน ,D=วัน สัมพันกัน expNumber เอาไปสร้างวันหมดอายุ แบบ auto", required = false)
    public void setExpType(String expType) {
        this.expType = expType;
    }

    public int getExpNumber() {
        return expNumber;
    }

    @ApiModelProperty(name = "expNumber", example = "1", dataType = "int", value = "เวลาหมดอายุ", required = false)
    public void setExpNumber(int expNumber) {
        this.expNumber = expNumber;
    }
    
     public String getCreatedDate() {
        return createdDate;
    }

    @ApiModelProperty(name = "createdDate", example = "01/01/2559 07:30:45", dataType = "string", value = "วันที่สร้าง", hidden = true, required = false)
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getRemovedDate() {
        return removedDate;
    }

    @ApiModelProperty(name = "removeDate", example = "01/01/2559 07:30:45", dataType = "string", value = "วันที่ลบ", hidden = true, required = false)
    public void setRemovedDate(String removedDate) {
        this.removedDate = removedDate;
    }

    public int getCreatedBy() {
        return createdBy;
    }

     @ApiModelProperty(name = "createdBy", example = "0", dataType = "int", value = "createBy", required = false)
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getRemovedBy() {
        return removedBy;
    }

    @ApiModelProperty(name = "removedBy", example = "0", dataType = "int", value = "removeBy", required = false)
    public void setRemovedBy(int removedBy) {
        this.removedBy = removedBy;
    }

    
    public String getUpdatedDate() {
        return updatedDate;
    }

    @ApiModelProperty(name = "upDatedDate", example = "01/01/2559 07:30:45", dataType = "string", value = "วันที่แก้ไข", hidden = true, required = false)
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    @ApiModelProperty(name = "updatedBy", example = "0", dataType = "int", value = "upDateBy", required = false)
    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public UserProfileModel getUserProfileCreate() {
        return userProfileCreate;
    }

    @ApiModelProperty(name = "userProfileCreate", value = "userProfileCreate", required = false)
    public void setUserProfileCreate(UserProfileModel userProfileCreate) {
        this.userProfileCreate = userProfileCreate;
    }

    public UserProfileModel getUserProfileUpdate() {
        return userProfileUpdate;
    }

    @ApiModelProperty(name = "userProfileUpdate", value = "userProfileUpdate", required = false)
    public void setUserProfileUpdate(UserProfileModel userProfileUpdate) {
        this.userProfileUpdate = userProfileUpdate;
    }

    public UserProfileModel getUserProfileDel() {
        return userProfileDel;
    }

    @ApiModelProperty(name = "userProfileDel", value = "userProfileDel", required = false)
    public void setUserProfileDel(UserProfileModel userProfileDel) {
        this.userProfileDel = userProfileDel;
    }

    public String getDmsDocumentPreExpireDate() {
        return dmsDocumentPreExpireDate;
    }

    @ApiModelProperty(name = "dmsDocumentPreExpireDate", example = "01/01/2559 07:30:45", dataType = "string", value = "วันก่อนวันหมดอายุ",  required = false)
    public void setDmsDocumentPreExpireDate(String dmsDocumentPreExpireDate) {
        this.dmsDocumentPreExpireDate = dmsDocumentPreExpireDate;
    }

    public String getIsExp() {
        return isExp;
    }

     @ApiModelProperty(name = "isExp", example = "N", dataType = "string", value = "Y= เอกสารหมดอายุ,N= เอกสารยังไม่ไหมอายุ", required = false)
    public void setIsExp(String isExp) {
        this.isExp = isExp;
    }

    public int getWfTypeId() {
        return wfTypeId;
    }

    @ApiModelProperty(name = "wfTypeId", example = "1", dataType = "int", value = "ประเภทเอกสารจาก wf", required = false)
    public void setWfTypeId(int wfTypeId) {
        this.wfTypeId = wfTypeId;
    }

    public int getFlowId() {
        return flowId;
    }

     @ApiModelProperty(name = "flowId", example = "1", dataType = "int", value = "work flow Id", required = false)
    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    public String getDmsSearchId() {
        return dmsSearchId;
    }

     @ApiModelProperty(name = "dmsSearchId", example = "111", dataType = "string", value = "dmsSearchId", required = false)
    public void setDmsSearchId(String dmsSearchId) {
        this.dmsSearchId = dmsSearchId;
    }

    public String getFullPathName() {
        return fullPathName;
    }

    @ApiModelProperty(name = "fullPathName", example = "ตู้1/ลิ้นชัก1/แฟ้ม1", dataType = "string", value = "dmsSearchId", required = false)
    public void setFullPathName(String fullPathName) {
        this.fullPathName = fullPathName;
    }

    public int getBorrowStatus() {
        return borrowStatus;
    }

     @ApiModelProperty(name = "borrowStatus", example = "0", dataType = "int", value = "borrowStatus 0 = ว่าง , 1 = ถูกยืม", required = false)
    public void setBorrowStatus(int borrowStatus) {
        this.borrowStatus = borrowStatus;
    }
    
    
    
    
    
    
    

   
    
    
    
    
    


}
