package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "folderProperty")
@ApiModel(description = "คุณสมบัติของทะเบียน")
public class FolderPropertyModel extends VersionModel{

    private static final long serialVersionUID = -2443067835898627821L;

    @XmlElement(name = "id")
    @Expose 
    @Since(1.0) private int id;
    @Expose @Since(1.0) private int createdBy;
    @Expose @Since(1.0) private String createdDate;
    @Expose @Since(1.0) private float orderNo;
    @Expose @Since(1.0) private int removedBy;
    @Expose @Since(1.0) private String removedDate;
    @Expose @Since(1.0) private int updatedBy;
    @Expose @Since(1.0) private String updatedDate;
    
    @XmlElement(name = "folderPropertyType")
    @ApiParam(name = "folderPropertyType", value = "ประเภทคุณสมบัติ เช่น T= สารบรรณ", required = true)
    @Size(max = 1)
    @Since(1.0)
    @Expose private String folderPropertyType;

    @XmlElement(name = "folderPropertyFolderId")
    @ApiParam(name = "folderPropertyFolderId", value = "รหัสทะเบียน", required = true)
    @Since(1.0)
    @Expose private int folderPropertyFolderId;

    @XmlElement(name = "folderPropertyFieldId")
    @ApiParam(name = "folderPropertyFieldId", value = "รหัสฟิลด์", required = true)
    @Since(1.0)
    @Expose private int folderPropertyFieldId;

    @XmlElement(name = "folderPropertyFieldName")
    @ApiParam(name = "folderPropertyFieldName", value = "ชื่อฟิลด์", required = true)
    @Size(max = 255)
    @Since(1.0)
    @Expose private String folderPropertyFieldName;

    @XmlElement(name = "folderPropertyFieldDescription")
    @ApiParam(name = "folderPropertyFieldDescription", value = "รายละเอียดฟิลด์", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose private String folderPropertyFieldDescription;

    @XmlElement(name = "folderPropertyFieldType")
    @ApiParam(name = "folderPropertyFieldType", value = "ประเภทฟิลด์", required = true)
    @Size(max = 50)
    @Since(1.0)
    @Expose private String folderPropertyFieldType;

    @XmlElement(name = "folderPropertyFieldLength")
    @ApiParam(name = "folderPropertyFieldLength", value = "จำนวนอักษร", required = true)
    @Since(1.0)
    @Expose private int folderPropertyFieldLength;

    @XmlElement(name = "folderPropertyPList")
    @ApiParam(name = "folderPropertyPList", value = "แสดงในรายการหรือไม่", required = false)
    @Size(max = 1)
    @Since(1.0)
    @Expose private String folderPropertyPList;

    @XmlElement(name = "folderPropertyPListWidth")
    @ApiParam(name = "folderPropertyPListWidth", value = "ความกว้างฟิลด์", required = true)
    @Since(1.0)
    @Expose private int folderPropertyPListWidth;

    @XmlElement(name = "folderPropertyPSearch")
    @ApiParam(name = "folderPropertyPSearch", value = "แสดงในช่องค้นหาหรือไม่", required = false)
    @Size(max = 1)
    @Since(1.0)
    @Expose private String folderPropertyPSearch;

    @XmlElement(name = "folderPropertyPView")
    @ApiParam(name = "folderPropertyPView", value = "กรอกข้อมูลหรือไม่", required = false)
    @Size(max = 1)
    @Since(1.0)
    @Expose private String folderPropertyPView;

    @XmlElement(name = "folderPropertyPLookupId")
    @ApiParam(name = "folderPropertyPLookupId", value = "รหัสการค้นหา", required = false)
    @Since(1.0)
    @Expose private int folderPropertyPLookupId;
    
    public FolderPropertyModel() {
    }

    public FolderPropertyModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(position = 1, name = "id", value = "รหัสคุณสมบัติของทะเบียน", required = true)
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

    public String getFolderPropertyType() {
        return folderPropertyType;
    }

    public void setFolderPropertyType(String folderPropertyType) {
        this.folderPropertyType = folderPropertyType;
    }

    public int getFolderPropertyFolderId() {
        return folderPropertyFolderId;
    }

    public void setFolderPropertyFolderId(int folderPropertyFolderId) {
        this.folderPropertyFolderId = folderPropertyFolderId;
    }

    public int getFolderPropertyFieldId() {
        return folderPropertyFieldId;
    }

    public void setFolderPropertyFieldId(int folderPropertyFieldId) {
        this.folderPropertyFieldId = folderPropertyFieldId;
    }

    public String getFolderPropertyFieldName() {
        return folderPropertyFieldName;
    }

    public void setFolderPropertyFieldName(String folderPropertyFieldName) {
        this.folderPropertyFieldName = folderPropertyFieldName;
    }

    public String getFolderPropertyFieldDescription() {
        return folderPropertyFieldDescription;
    }

    public void setFolderPropertyFieldDescription(String folderPropertyFieldDescription) {
        this.folderPropertyFieldDescription = folderPropertyFieldDescription;
    }

    public String getFolderPropertyFieldType() {
        return folderPropertyFieldType;
    }

    public void setFolderPropertyFieldType(String folderPropertyFieldType) {
        this.folderPropertyFieldType = folderPropertyFieldType;
    }

    public int getFolderPropertyFieldLength() {
        return folderPropertyFieldLength;
    }

    public void setFolderPropertyFieldLength(int folderPropertyFieldLength) {
        this.folderPropertyFieldLength = folderPropertyFieldLength;
    }

    public String getFolderPropertyPList() {
        return folderPropertyPList;
    }

    public void setFolderPropertyPList(String folderPropertyPList) {
        this.folderPropertyPList = folderPropertyPList;
    }

    public int getFolderPropertyPListWidth() {
        return folderPropertyPListWidth;
    }

    public void setFolderPropertyPListWidth(int folderPropertyPListWidth) {
        this.folderPropertyPListWidth = folderPropertyPListWidth;
    }

    public String getFolderPropertyPSearch() {
        return folderPropertyPSearch;
    }

    public void setFolderPropertyPSearch(String folderPropertyPSearch) {
        this.folderPropertyPSearch = folderPropertyPSearch;
    }

    public String getFolderPropertyPView() {
        return folderPropertyPView;
    }

    public void setFolderPropertyPView(String folderPropertyPView) {
        this.folderPropertyPView = folderPropertyPView;
    }

    public int getFolderPropertyPLookupId() {
        return folderPropertyPLookupId;
    }

    public void setFolderPropertyPLookupId(int folderPropertyPLookupId) {
        this.folderPropertyPLookupId = folderPropertyPLookupId;
    }

}
