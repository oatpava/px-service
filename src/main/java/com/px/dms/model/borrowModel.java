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
 * @author top
 */
@XmlRootElement(name = "Borrow")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ยืม-คืน")
public class borrowModel extends VersionModel {

    public static final long serialVersionUID = 0L;

    public borrowModel() {
    }

    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสการยืม", defaultValue = "0", required = false)
    @Expose
    @Since(1.0)
    private int id;

    @XmlElement(name = "userLentId")
    @ApiParam(name = "userLentId", example = "0", value = "userId ผู้ยืม", defaultValue = "0", required = false)
    @Expose
    @Since(1.0)
    private int userLentId;

    @XmlElement(name = "lendDate")
    @ApiParam(name = "lendDate", example = "01/01/2559 07:30:45", value = "วันที่ยืม", required = false)
    @Since(1.0)
    @Expose
    private String lendDate;

    @XmlElement(name = "userReturnId")
    @ApiParam(name = "userReturnId", example = "0", value = "userId ผู้คืน", defaultValue = "0", required = false)
    @Expose
    @Since(1.0)
    private int userReturnId;

    @XmlElement(name = "returnDate")
    @ApiParam(name = "returnDate", example = "0", value = "วันที่คืน", defaultValue = "0", required = false)
    @Expose
    @Since(1.0)
    private String returnDate;

    @XmlElement(name = "retuenDateNum")
    @ApiParam(name = "retuenDateNum", example = "0", value = "วันที่คืน", required = false)
    @Since(1.0)
    @Expose
    private int retuenDateNum;

    @XmlElement(name = "dmsDocument")
    @ApiParam(name = "dmsDocument", value = "dmsDocument", required = false)
    @Expose
    @Since(1.0)
    private DmsDocumentModel dmsDocument;

    @XmlElement(name = "userHandlerId")
    @ApiParam(name = "userHandlerId", example = "0", value = "userId ผู้ดำเนินการ", defaultValue = "0", required = false)
    @Expose
    @Since(1.0)
    private int userHandlerId;

    @XmlElement(name = "userProfileReturn")
    @ApiParam(name = "userProfileReturn", value = "userProfileReturn", required = false)
    @Expose
    @Since(1.0)
    private UserProfileModel userProfileReturn;

    @XmlElement(name = "userProfileLent")
    @ApiParam(name = "userProfileLent", value = "userProfileLent", required = false)
    @Expose
    @Since(1.0)
    private UserProfileModel userProfileLent;

    @XmlElement(name = "userProfileHandler")
    @ApiParam(name = "userProfileHandler", value = "userProfileHandler", required = false)
    @Expose
    @Since(1.0)
    private UserProfileModel userProfileHandler;

    @XmlElement(name = "statusId")
    @ApiParam(name = "statusId", example = "0", value = "statusId", defaultValue = "0", required = false)
    @Expose
    @Since(1.0)
    private int statusId;
    
    @XmlElement(name = "returnName")
    @ApiParam(name = "returnName", value = "returnName", required = false)
    @Since(1.0)
    @Expose
    private String returnName;
    
    @XmlElement(name = "lendName")
    @ApiParam(name = "lendName", value = "lendName", required = false)
    @Since(1.0)
    @Expose
    private String lendName;

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสการยืม", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public int getUserLentId() {
        return userLentId;
    }

    @ApiModelProperty(name = "userLentId", example = "01", dataType = "int", value = "ผู้ยืม", required = false)
    public void setUserLentId(int userLentId) {
        this.userLentId = userLentId;
    }

    public String getLendDate() {
        return lendDate;
    }

    @ApiModelProperty(name = "lendDate", example = "01/01/2559 07:30:45", dataType = "string", value = "วันที่ยืม", required = false)
    public void setLendDate(String lendDate) {
        this.lendDate = lendDate;
    }

    

    public int getUserReturnId() {
        return userReturnId;
    }

    public int getRetuenDateNum() {
        return retuenDateNum;
    }

    @ApiModelProperty(name = "retuenDateNum", example = "01", dataType = "int", value = "เวลายืน", required = false)
    public void setRetuenDateNum(int retuenDateNum) {
        this.retuenDateNum = retuenDateNum;
    }

    public DmsDocumentModel getDmsDocument() {
        return dmsDocument;
    }

    @ApiModelProperty(name = "dmsDocument", value = "เอกสาร", required = false)
    public void setDmsDocument(DmsDocumentModel dmsDocument) {
        this.dmsDocument = dmsDocument;
    }

    public int getUserHandlerId() {
        return userHandlerId;
    }

    @ApiModelProperty(name = "userHandlerId", example = "01", dataType = "int", value = "ผู้ให้ยืม", required = false)
    public void setUserHandlerId(int userHandlerId) {
        this.userHandlerId = userHandlerId;
    }

    public UserProfileModel getUserProfileReturn() {
        return userProfileReturn;
    }

    @ApiModelProperty(name = "userProfileReturn", value = "userProfileReturn", required = false)
    public void setUserProfileReturn(UserProfileModel userProfileReturn) {
        this.userProfileReturn = userProfileReturn;
    }

    public UserProfileModel getUserProfileLent() {
        return userProfileLent;
    }

    @ApiModelProperty(name = "userProfileLent", value = "userProfileLent", required = false)
    public void setUserProfileLent(UserProfileModel userProfileLent) {
        this.userProfileLent = userProfileLent;
    }

    public UserProfileModel getUserProfileHandler() {
        return userProfileHandler;
    }

    @ApiModelProperty(name = "userProfileHandler", value = "userProfileHandler", required = false)
    public void setUserProfileHandler(UserProfileModel userProfileHandler) {
        this.userProfileHandler = userProfileHandler;
    }

    public int getStatusId() {
        return statusId;
    }

    @ApiModelProperty(name = "statusId", example = "0", dataType = "int", value = "statusId 0=ว่าง,1= ยืม,2=คืนเกินกำหนด,3=ยืมเกินกำหนด,4=คืนปกติ", required = false)
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setUserReturnId(int userReturnId) {
        this.userReturnId = userReturnId;
    }

    public String getReturnDate() {
        return returnDate;
    }

    @ApiModelProperty(name = "returnDate", example = "01/01/2559 07:30:45", dataType = "string", value = "วันที่คืน", required = false)
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnName() {
        return returnName;
    }

    @ApiModelProperty(name = "returnName", example = "ผู้คืน", dataType = "string", value = "ผู้คืน", required = false)
    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }

    public String getLendName() {
        return lendName;
    }

    @ApiModelProperty(name = "lendName", example = "ผู้ยืม", dataType = "string", value = "ผู้ยืม", required = false)
    public void setLendName(String lendName) {
        this.lendName = lendName;
    }
    
    

}
