package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.admin.entity.UserProfile;
import com.px.share.util.Common;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oat
 */
@XmlRootElement(name = "ImportProfileModel")
@ApiModel(description = "ข้อมูลเการเชื่อมระบบ")
public class ImportProfileModel {

    @XmlElement(name = "id")
    @ApiParam(name = "id", value = "รหัสข้อมูลเการเชื่อมระบบ", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "id", dataType = "int", value = "รหัสข้อมูลเการเชื่อมระบบ", required = true)
    private int id;

    @XmlElement(name = "createdBy")
    @ApiParam(name = "createdBy", value = "รหัสข้อมูลเผู้สร้าง", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "createdBy", dataType = "int", value = "รหัสข้อมูลเผู้สร้าง", required = true)
    private int createdBy;

    @XmlElement(name = "createdDate")
    @ApiParam(name = "createdDate", example = "18/06/2568 10:00", value = "วันที่สร้าง", required = true)
    @Size(min = 10, max = 16)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "createdDate", dataType = "string", value = "วันที่สร้าง", required = true)
    private String createdDate;

    @XmlElement(name = "updatedBy")
    @ApiParam(name = "updatedBy", value = "รหัสข้อมูลเผู้แก้ไข", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "updatedBy", dataType = "int", value = "รหัสข้อมูลเผู้แก้ไข", required = false)
    private int updatedBy;

    @XmlElement(name = "updatedDate")
    @ApiParam(name = "updatedDate", example = "18/06/2568 10:00", value = "วันที่แก้ไข", required = false)
    @Size(min = 10, max = 16)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "updatedDate", dataType = "string", value = "วันที่แก้ไข", required = false)
    private String updatedDate;

    @XmlElement(name = "key")
    @ApiParam(name = "key", value = "Key ที่ใช้ยืนยันตัวตน", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "key", dataType = "string", value = "Key ที่ใช้ยืนยันตัวตน", required = true)
    private String key;

    @XmlElement(name = "name")
    @ApiParam(name = "name", value = "ชื่อระบบ", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "name", dataType = "string", value = "ชื่อระบบ", required = true)
    private String name;

    @XmlElement(name = "creatorFullName")
    @ApiParam(name = "creatorFullName", value = "ชื่อผู้สร้าง", required = true)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "creatorFullName", dataType = "string", value = "ชื่อผู้สร้าง", required = true)
    private String creatorFullName;

    @XmlElement(name = "updaterFullName")
    @ApiParam(name = "updaterFullName", value = "ชื่อผู้แก้ไข", required = false)
    @Expose
    @Since(1.0)
    @ApiModelProperty(name = "updaterFullName", dataType = "string", value = "ชื่อผู้แก้ไข", required = false)
    private String updaterFullName;

    public ImportProfileModel() {
    }

    public ImportProfileModel(UserProfile userProfile, String creatorFullName, String updaterFullname) {
        this.id = userProfile.getId();
        this.createdBy = userProfile.getCreatedBy();
        this.createdDate = Common.localDateTimeToString2(userProfile.getCreatedDate());
        this.updatedBy = userProfile.getUpdatedBy();
        this.updatedDate = Common.localDateTimeToString2(userProfile.getUpdatedDate());
        this.key = userProfile.getUserProfileCode();
        this.name = userProfile.getUserProfileFullName();
        this.creatorFullName = creatorFullName;
        this.updaterFullName = updaterFullname;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorFullName() {
        return creatorFullName;
    }

    public void setCreatorFullName(String creatorFullName) {
        this.creatorFullName = creatorFullName;
    }

    public String getUpdaterFullName() {
        return updaterFullName;
    }

    public void setUpdaterFullName(String updaterFullName) {
        this.updaterFullName = updaterFullName;
    }

}
