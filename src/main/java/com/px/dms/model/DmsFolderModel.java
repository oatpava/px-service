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
@XmlRootElement(name = "DmsFoler")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ที่เก็บเอกสาร")
public class DmsFolderModel extends VersionModel {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสทีเก็บเอกสาร", defaultValue = "0", required = false)
    @Since(1.0)
    @Expose
    private int id;

    @XmlElement(name = "folderType")
    @ApiParam(name = "folderType", example = "C", value = "ชนิดของที่เก็บเอกสาร A = root, C = cabinet, D = drawer, F = folder , S = เอกสารส่วนตัว", required = true)
    @Since(1.0)
    @Expose
    private String folderType;

    @XmlElement(name = "folderParentId")
    @ApiParam(name = "folderParentId", example = "0", value = "รหัสทีเก็บเอกสาร แม่", defaultValue = "0", required = true)
    @Since(1.0)
    @Expose
    private int folderParentId;

    @XmlElement(name = "folderParentType")
    @ApiParam(name = "folderParentType", example = "F", value = "F = folder, S = structure", defaultValue = "F", required = true)
    @Since(1.0)
    @Expose
    private String folderParentType;

    @XmlElement(name = "folderParentKey")
    @ApiParam(name = "folderParentKey", example = "฿1฿", value = "String ของ ParentKey", required = false)
    @Since(1.0)
    @Expose
    private String folderParentKey;

    @XmlElement(name = "folderOrderId")
    @ApiParam(name = "folderOrderId", example = "1", value = "ลำดับการเรียง", required = false)
    @Since(1.0)
    @Expose
    private float folderOrderId;

    @XmlElement(name = "folderNodeLevel")
    @ApiParam(name = "folderNodeLevel", required = false)
    @Since(1.0)
    @Expose
    private int folderNodeLevel;

    @XmlElement(name = "createDate")
    @ApiParam(name = "createdDate", example = "01/01/2559 07:30:45", value = "วันที่สร้าง", defaultValue = "01/01/2559 07:30:45", hidden = true, required = false)
    @Since(1.0)
    @Expose
    private String createDate;

    @XmlElement(name = "removeDate")
    @ApiParam(name = "removeDate", example = "01/01/2559 07:30:45", value = "วันที่สร้าง", defaultValue = "01/01/2559 07:30:45", hidden = true, required = false)
    @Since(1.0)
    @Expose
    private String removeDate;

    @XmlElement(name = "createBy")
    @ApiParam(name = "createBy", example = "1", value = "id ผู้สร้าง", required = false)
    @Since(1.0)
    @Expose
    private int createBy;

    @XmlElement(name = "removeBy")
    @ApiParam(name = "removeBy", example = "1", value = "id ผู้ลบ", required = false)
    @Since(1.0)
    @Expose
    private int removeBy;

    @XmlElement(name = "upDateDate")
    @ApiParam(name = "removeDate", example = "01/01/2559 07:30:45", value = "วันที่สร้าง", defaultValue = "01/01/2559 07:30:45", hidden = true, required = false)
    @Since(1.0)
    @Expose
    private String upDateDate;

    @XmlElement(name = "upDateBy")
    @ApiParam(name = "upDateBy", example = "1", value = "id ผู้แก้ไข", required = false)
    @Since(1.0)
    @Expose
    private int upDateBy;

    @XmlElement(name = "folderDescription")
    @ApiParam(name = "folderDescription", value = "คำอธิบาย", required = false)
    @Since(1.0)
    @Expose
    private String folderDescription;

    @XmlElement(name = "folderName")
    @ApiParam(name = "folderName", value = "ชื่อที่เก็บเอกสาร", required = false)
    @Since(1.0)
    @Expose
    private String folderName;

    @XmlElement(name = "folderTypeExpire")
    @ApiParam(name = "folderTypeExpire", value = "ชนิดวันหมดอายุ  D = day, M = month, Y = year", required = false)
    @Since(1.0)
    @Expose
    private String folderTypeExpire;

    @XmlElement(name = "folderTypeExpireNumber")
    @ApiParam(name = "folderTypeExpireNumber", value = "จำนวนเวลา", required = false)
    @Since(1.0)
    @Expose
    private int folderTypeExpireNumber;

    @XmlElement(name = "documentTypeId")
    @ApiParam(name = "documentTypeId", value = "ประเภทของเอกสารใน ที่เก็บเอกสาร", defaultValue = "1", required = true)
    @Since(1.0)
    @Expose
    private int documentTypeId;


    
    @XmlElement(name = "iconColor")
    @ApiParam(name = "iconColor", example = "", value = "สี icon ", defaultValue = "", required = false)
    @Since(1.0)
    @Expose
    private String iconColor;
    
    
    @XmlElement(name = "icon")
    @ApiParam(name = "icon", example = "", value = "รูป icon", defaultValue = "", required = false)
    @Since(1.0)
    @Expose
    private String icon;
    
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
    
    
     @XmlElement(name = "dmsFolderTypePreExpire")
    @ApiParam(name = "dmsFolderTypePreExpire", value = "ชนิดวันก่อนวันหมดอายุ  D = day, M = month, Y = year", required = false)
    @Since(1.0)
    @Expose
    private String dmsFolderTypePreExpire;

    @XmlElement(name = "dmsFolderTypePreExpireNumber")
    @ApiParam(name = "dmsFolderTypePreExpireNumber", value = "จำนวนเวลา", required = false)
    @Since(1.0)
    @Expose
    private int dmsFolderTypePreExpireNumber;
    
    
    @XmlElement(name = "dmsEmailUserPreExpire")
    @ApiParam(name = "dmsEmailUserPreExpire", value = "email ผู้รับผิดชอบก่อนวันหมดอายุ", required = false)
    @Since(1.0)
    @Expose
    private String dmsEmailUserPreExpire;
    
    
    @XmlElement(name = "dmsUserPreExpire")
    @ApiParam(name = "dmsUserPreExpire", value = "รหัสผู้รับผิดชอบก่อนวันหมดอายุ", required = false)
    @Since(1.0)
    @Expose
    private int dmsUserPreExpire;
    
    
    @XmlElement(name = "dmsUserProfilePreExpire")
    @ApiParam(name = "dmsUserPreExpire", value = "dmsUserProfilePreExpire", required = false)
    @Since(1.0)
    @Expose
    private UserProfileModel dmsUserProfilePreExpire;
    
    @XmlElement(name = "isWfFolderFromType")
    @ApiParam(name = "isWfFolderFromType", example = "N", value = "Y= เป็นเอกสารจากWfType,N= ไม่ได้เป็นเอกสารจากWfType", hidden = true, required = false)
    @Expose
    @Since(1.0)
    private String isWfFolderFromType;
    
    
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
    
    
    
    
    public DmsFolderModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสคำนำหน้า", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getFolderType() {
        return folderType;
    }

    @ApiModelProperty(name = "folderType", example = "F", dataType = "string", value = "ชนิดของที่เก็บเอกสาร A = root, C = cabinet, D = drawer, F = folder , S = เอกสารส่วนตัว", required = true)
    public void setFolderType(String folderType) {
        this.folderType = folderType;
    }

    public int getFolderParentId() {
        return folderParentId;
    }

    @ApiModelProperty(name = "folderParentId", example = "1", dataType = "int", value = "folderParentId", required = true)
    public void setFolderParentId(int folderParentId) {
        this.folderParentId = folderParentId;
    }

    public String getFolderParentType() {
        return folderParentType;
    }

    @ApiModelProperty(name = "folderParentType", example = "F", dataType = "String", value = "F = folder, S = structure", required = true)
    public void setFolderParentType(String folderParentType) {
        this.folderParentType = folderParentType;
    }

    public String getFolderParentKey() {
        return folderParentKey;
    }

    @ApiModelProperty(name = "folderParentKey", example = "฿1฿", dataType = "string", value = "folderParentKey", required = false)
    public void setFolderParentKey(String folderParentKey) {
        this.folderParentKey = folderParentKey;
    }

    public float getFolderOrderId() {
        return folderOrderId;
    }

    @ApiModelProperty(name = "folderOrderId", example = "1", dataType = "int", value = "folderOrderId", required = false)
    public void setFolderOrderId(float folderOrderId) {
        this.folderOrderId = folderOrderId;
    }

    public int getFolderNodeLevel() {
        return folderNodeLevel;
    }

    @ApiModelProperty(name = "folderNodeLevel", example = "0", dataType = "int", value = "folderNodeLevel", required = false)
    public void setFolderNodeLevel(int folderNodeLevel) {
        this.folderNodeLevel = folderNodeLevel;
    }

    public String getCreateDate() {
        return createDate;
    }

    @ApiModelProperty(name = "createdDate", example = "01/01/2559 07:30:45", dataType = "string", value = "วันที่สร้าง", hidden = true, required = false)
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRemoveDate() {
        return removeDate;
    }

    @ApiModelProperty(name = "removeDate", example = "01/01/2559 07:30:45", dataType = "string", value = "วันที่ลบ", hidden = true, required = false)
    public void setRemoveDate(String removeDate) {
        this.removeDate = removeDate;
    }

    public int getCreateBy() {
        return createBy;
    }

     @ApiModelProperty(name = "createBy", example = "0", dataType = "int", value = "createBy", required = false)
    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public int getRemoveBy() {
        return removeBy;
    }

    @ApiModelProperty(name = "removeBy", example = "0", dataType = "int", value = "removeBy", required = false)
    public void setRemoveBy(int removeBy) {
        this.removeBy = removeBy;
    }

    
    public String getUpDateDate() {
        return upDateDate;
    }

    @ApiModelProperty(name = "upDateDate", example = "01/01/2559 07:30:45", dataType = "string", value = "วันที่แก้ไข", hidden = true, required = false)
    public void setUpDateDate(String upDateDate) {
        this.upDateDate = upDateDate;
    }

    public int getUpDateBy() {
        return upDateBy;
    }

    @ApiModelProperty(name = "upDateBy", example = "0", dataType = "int", value = "upDateBy", required = false)
    public void setUpDateBy(int upDateBy) {
        this.upDateBy = upDateBy;
    }

    public String getFolderDescription() {
        return folderDescription;
    }

    @ApiModelProperty(name = "folderDescription", example = "รายละเอียด folder", dataType = "string", value = "รายละเอียด folder", required = false)
    public void setFolderDescription(String folderDescription) {
        this.folderDescription = folderDescription;
    }

    public String getFolderName() {
        return folderName;
    }

    @ApiModelProperty(name = "folderName", example = "ชื่อ folder", dataType = "string", value = "ชื่อ folder", required = false)
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderTypeExpire() {
        return folderTypeExpire;
    }

    @ApiModelProperty(name = "folderTypeExpire", example = "D", dataType = "string", value = "ชนิดวันหมดอายุ D = day, M = month, Y = year", required = false)
    public void setFolderTypeExpire(String folderTypeExpire) {
        this.folderTypeExpire = folderTypeExpire;
    }

   
    public int getFolderTypeExpireNumber() {
        return folderTypeExpireNumber;
    }

     @ApiModelProperty(name = "folderTypeExpireNumber", example = "1", dataType = "int", value = "จำนวนเวลา สัมพันธ์ กับ folderTypeExpire", required = false)
    public void setFolderTypeExpireNumber(int folderTypeExpireNumber) {
        this.folderTypeExpireNumber = folderTypeExpireNumber;
    }

    public int getDocumentTypeId() {
        return documentTypeId;
    }

    @ApiModelProperty(name = "documentTypeId", example = "1", dataType = "int", value = "ประเภทของเอกสารใน ที่เก็บเอกสาร", required = true)
    public void setDocumentTypeId(int documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getIconColor() {
        return iconColor;
    }

     @ApiModelProperty(name = "iconColor", example = "iconColor", dataType = "string", value = "", required = false)
    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }

    public String getIcon() {
        return icon;
    }

     @ApiModelProperty(name = "icon", example = "icon", dataType = "string", value = "icon", required = false)
    public void setIcon(String icon) {
        this.icon = icon;
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

    public String getDmsFolderTypePreExpire() {
        return dmsFolderTypePreExpire;
    }

    @ApiModelProperty(name = "dmsFolderTypePreExpire", example = "D", dataType = "string", value = "ชนิดวันก่อนวันหมดอายุ D = day, M = month, Y = year", required = false)
    public void setDmsFolderTypePreExpire(String dmsFolderTypePreExpire) {
        this.dmsFolderTypePreExpire = dmsFolderTypePreExpire;
    }

    public int getDmsFolderTypePreExpireNumber() {
        return dmsFolderTypePreExpireNumber;
    }

     @ApiModelProperty(name = "dmsFolderTypePreExpireNumber", example = "1", dataType = "int", value = "จำนวนเวลา สัมพันธ์ กับ dmsFolderTypePreExpire", required = false)
    public void setDmsFolderTypePreExpireNumber(int dmsFolderTypePreExpireNumber) {
        this.dmsFolderTypePreExpireNumber = dmsFolderTypePreExpireNumber;
    }

    public String getDmsEmailUserPreExpire() {
        return dmsEmailUserPreExpire;
    }

    @ApiModelProperty(name = "dmsEmailUserPreExpire", example = "praxis0808test@gmail.com", dataType = "string", value = "email ผู้รับผิดชอบ แฟ้มก่อนวันหมดอายุ", required = false)
    public void setDmsEmailUserPreExpire(String dmsEmailUserPreExpire) {
        this.dmsEmailUserPreExpire = dmsEmailUserPreExpire;
    }

    public int getDmsUserPreExpire() {
        return dmsUserPreExpire;
    }

    @ApiModelProperty(name = "dmsUserPreExpire", example = "1", dataType = "int", value = "user id ของผู้ที่รับผิดชอบ", required = true)
    public void setDmsUserPreExpire(int dmsUserPreExpire) {
        this.dmsUserPreExpire = dmsUserPreExpire;
    }

    public UserProfileModel getDmsUserProfilePreExpire() {
        return dmsUserProfilePreExpire;
    }

    public void setDmsUserProfilePreExpire(UserProfileModel dmsUserProfilePreExpire) {
        this.dmsUserProfilePreExpire = dmsUserProfilePreExpire;
    }

    public String getIsWfFolderFromType() {
        return isWfFolderFromType;
    }

    @ApiModelProperty(name = "isWfFolderFromType", example = "N", dataType = "string", value = "Y= เป็นเอกสารจากWfType,N= ไม่ได้เป็นเอกสารจากWfType", required = false)
    public void setIsWfFolderFromType(String isWfFolderFromType) {
        this.isWfFolderFromType = isWfFolderFromType;
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
    
    
    
    

  
    
    

}
