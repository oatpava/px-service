package com.px.mwp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "UserProfileFolder")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "แฟ้มของผู้ใช้งานระบบ")
public class UserProfileFolderModel extends VersionModel {
    private static final long serialVersionUID = -6025156709868344139L;

    @XmlElement(name = "id")
    @Expose @Since(1.0) private int id;
    @Expose @Since(1.0) private int createdBy;
    @Expose @Since(1.0) private String createdDate;
    @Expose @Since(1.0) private float orderNo;
    @Expose @Since(1.0) private int removedBy;
    @Expose @Since(1.0) private String removedDate;
    @Expose @Since(1.0) private int updatedBy;
    @Expose @Since(1.0) private String updatedDate;
//    @XmlElement(name = "userProfile")
//    @Expose @Since(1.0) private UserProfileModel userProfile;
    
    @XmlElement(name = "userProfileId")
    @ApiParam(name = "userProfileId",value = "รหัสประวัติผู้ใช้งานระบบ",required = true)
    @Expose 
    @Since(1.0)
    private int userProfileId;
    
    @XmlElement(name = "userProfileFolderName")
    @ApiParam(name = "userProfileFolderName",value = "ชื่อแฟ้มของผู้ใช้งานระบบ",required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String userProfileFolderName;
    
    @XmlElement(name = "userProfileFolderLinkId")
    @ApiParam(name = "userProfileFolderLinkId",value = "รหัสเชื่อมโยงแฟ้มของผู้ใช้งานระบบ",required = true)
    @Expose private int userProfileFolderLinkId;
    
    @XmlElement(name = "userProfileFolderType")
    @ApiParam(name = "userProfileFolderType",value = "ประเภทแฟ้มของผู้ใช้งานระบบ",required = true)
    @Size(max = 10)
    @Expose 
    @Since(1.0)
    private String userProfileFolderType;
    
    @XmlElement(name = "userProfileFolderDetail")
    @ApiParam(name = "userProfileFolderDetail",value = "รายละเอียดแฟ้มของผู้ใช้งานระบบ",required = true)
    @Size(max = 1000)
    @Expose 
    @Since(1.0)
    private String userProfileFolderDetail;

    public UserProfileFolderModel() {
    }

    public UserProfileFolderModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public float getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(float orderNo) {
        this.orderNo = orderNo;
    }

    public int getRemovedBy() {
        return removedBy;
    }

    public void setRemovedBy(int removedBy) {
        this.removedBy = removedBy;
    }

    public String getRemovedDate() {
        return removedDate;
    }

    public void setRemovedDate(String removedDate) {
        this.removedDate = removedDate;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(int userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getUserProfileFolderName() {
        return userProfileFolderName;
    }

    public void setUserProfileFolderName(String userProfileFolderName) {
        this.userProfileFolderName = userProfileFolderName;
    }

    public String getUserProfileFolderType() {
        return userProfileFolderType;
    }

    public void setUserProfileFolderType(String userProfileFolderType) {
        this.userProfileFolderType = userProfileFolderType;
    }

    public String getUserProfileFolderDetail() {
        return userProfileFolderDetail;
    }

    public void setUserProfileFolderDetail(String userProfileFolderDetail) {
        this.userProfileFolderDetail = userProfileFolderDetail;
    }

    public int getUserProfileFolderLinkId() {
        return userProfileFolderLinkId;
    }

    public void setUserProfileFolderLinkId(int userProfileFolderLinkId) {
        this.userProfileFolderLinkId = userProfileFolderLinkId;
    }
}
