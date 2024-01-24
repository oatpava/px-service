package com.px.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.admin.model.UserProfileModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oat
 */
@XmlRootElement(name = "FileAttachApprove")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "การรับรองไฟล์เอกสารแนบ")
public class FileAttachApproveModel extends VersionModel {

    private static final long serialVersionUID = -240120;

    @XmlElement(name = "id")
    @ApiParam(name = "id", value = "รหัสไการรับรองไฟล์เอกสารแนบ", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "id", dataType = "int", value = "รหัสไการรับรองไฟล์เอกสารแนบ")
    private int id;

    @XmlElement(name = "createdDate")
    @ApiParam(name = "createdDate", value = "วันและเวลาที่สร้าง", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "createdDate", dataType = "string", value = "วันและเวลาที่สร้าง")
    private String createdDate;

    @XmlElement(name = "fileAttachId")
    @ApiParam(name = "fileAttachId", value = "รหัสไฟล์เอกสารแนบ", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "fileAttachId", dataType = "int", value = "รหัสไฟล์เอกสารแนบ")
    private int fileAttachId;

    @XmlElement(name = "userProfileId")
    @ApiParam(name = "userProfileId", value = "รหัสผู้ใช้งานระบบ", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "userProfileId", dataType = "int", value = "รหัสผู้ใช้งานระบบ")
    private int userProfileId;

    @XmlElement(name = "userProfile")
    @ApiParam(name = "userProfile", value = "ผู้ใช้งานระบบ", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "userProfile", value = "ผู้ใช้งานระบบ")
    private UserProfileModel userProfile;

    public FileAttachApproveModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getFileAttachId() {
        return fileAttachId;
    }

    public void setFileAttachId(int fileAttachId) {
        this.fileAttachId = fileAttachId;
    }

    public int getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(int userProfileId) {
        this.userProfileId = userProfileId;
    }

    public UserProfileModel getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileModel userProfile) {
        this.userProfile = userProfile;
    }

}
