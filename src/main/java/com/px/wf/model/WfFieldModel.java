
package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Mali
 */
@XmlRootElement(name = "wfField")
@ApiModel(description = "ชื่อฟิลด์ของหนังสือสารบรรณ")
public class WfFieldModel extends VersionModel {

    private static final long serialVersionUID = -2443067835898627821L;

    @XmlElement(name = "id")
    @Expose @Since(1.0) private int id;
    @Expose @Since(1.0) private int createdBy;
    @Expose @Since(1.0) private String createdDate;
    @Expose @Since(1.0) private float orderNo;
    @Expose @Since(1.0) private int removedBy;
    @Expose @Since(1.0) private String removedDate;
    @Expose @Since(1.0) private int updatedBy;
    @Expose @Since(1.0) private String updatedDate;
    
    @XmlElement(name = "wfFieldName")
    @ApiParam(name = "wfFieldName", value = "ชื่อฟิลด์ของหนังสือสารบรรณ", required = true)
    @Size(max = 255)
    @Since(1.0)
    @Expose private String wfFieldName;
    
    @XmlElement(name = "wfFieldDescription")
    @ApiParam(name = "wfFieldDescription", value = "คำอธิบายชื่อฟิลด์ของหนังสือสารบรรณ", required = false)
    @Size(max = 50)
    @Since(1.0)
    @Expose private String wfFieldDescription;
     
    @XmlElement(name = "wfFieldType")
    @ApiParam(name = "wfFieldType", value = "ประเภทของชื่อฟิลด์ของหนังสือสารบรรณ", required = true)
    @Size(max = 50)
    @Since(1.0)
    @Expose private String wfFieldType;
    
    @XmlElement(name = "wfFieldLength")
    @ApiParam(name = "wfFieldLength", value = "ความยาวของชื่อฟิลด์ของหนังสือสารบรรณ", required = true)
    @Since(1.0)
    @Expose private int wfFieldLength;
    
    
    public WfFieldModel() {
    }

    public WfFieldModel(int id) {
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

    public String getWfFieldName() {
        return wfFieldName;
    }

    public void setWfFieldName(String wfFieldName) {
        this.wfFieldName = wfFieldName;
    }

    public String getWfFieldDescription() {
        return wfFieldDescription;
    }

    public void setWfFieldDescription(String wfFieldDescription) {
        this.wfFieldDescription = wfFieldDescription;
    }

    public String getWfFieldType() {
        return wfFieldType;
    }

    public void setWfFieldType(String wfFieldType) {
        this.wfFieldType = wfFieldType;
    }

    public int getWfFieldLength() {
        return wfFieldLength;
    }

    public void setWfFieldLength(int wfFieldLength) {
        this.wfFieldLength = wfFieldLength;
    }

}
