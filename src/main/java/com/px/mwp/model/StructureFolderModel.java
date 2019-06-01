
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
@ApiModel(description = "แฟ้มของโครงสร้างระบบ")
public class StructureFolderModel extends VersionModel {
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
//    @XmlElement(name = "structure")
//    @Expose @Since(1.0) private StructureModel structure;
    
    @XmlElement(name = "structureId")
    @ApiParam(name = "structureId",value = "รหัสโครงสร้างระบบ",required = true)
    @Expose 
    @Since(1.0)
    private int structureId;
    
    @XmlElement(name = "structureFolderName")
    @ApiParam(name = "structureFolderName",value = "ชื่อแฟ้มของโครงสร้างระบบ",required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String structureFolderName;
    
    @XmlElement(name = "structureFolderType")
    @ApiParam(name = "structureFolderType",value = "ประเภทแฟ้มของโครงสร้างระบบ",required = true)
    @Size(max = 10)
    @Expose 
    @Since(1.0)
    private String structureFolderType;
    
    @XmlElement(name = "structureFolderLinkId")
    @ApiParam(name = "structureFolderLinkId",value = "รหัสเชื่อมโยงแฟ้มของโครงสร้างระบบ",required = true)
    private int structureFolderLinkId;
    
    @XmlElement(name = "structureFolderDetail")
    @ApiParam(name = "structureFolderDetail",value = "รายละเอียดแฟ้มของโครงสร้างระบบ",required = true)
    @Size(max = 1000)
    @Expose 
    @Since(1.0)
    private String structureFolderDetail;

    public StructureFolderModel() {
    }

    public StructureFolderModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getStructureId() {
        return structureId;
    }

    public void setStructureId(int structureId) {
        this.structureId = structureId;
    }

    public String getStructureFolderName() {
        return structureFolderName;
    }

    public void setStructureFolderName(String structureFolderName) {
        this.structureFolderName = structureFolderName;
    }

    public String getStructureFolderType() {
        return structureFolderType;
    }

    public void setStructureFolderType(String structureFolderType) {
        this.structureFolderType = structureFolderType;
    }

    public int getStructureFolderLinkId() {
        return structureFolderLinkId;
    }

    public void setStructureFolderLinkId(int structureFolderLinkId) {
        this.structureFolderLinkId = structureFolderLinkId;
    }

    public String getStructureFolderDetail() {
        return structureFolderDetail;
    }

    public void setStructureFolderDetail(String structureFolderDetail) {
        this.structureFolderDetail = structureFolderDetail;
    }
    
    
}
