
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
@XmlRootElement(name = "inOutField")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ชื่อฟิลด์ของกล่องหนังสือเข้า/กล่องหนังสือออก")
public class InOutFieldModel extends VersionModel {
    
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

    @XmlElement(name = "inoutFieldName")
    @ApiParam(name = "inoutFieldName", value = "ชื่อฟิลด์ของกล่องหนังสือเข้า/กล่องหนังสือออก", required = false)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String inoutFieldName;
    
    @XmlElement(name = "inoutFieldDescription")
    @ApiParam(name = "inoutFieldDescription", value = "คำอธิบายชื่อฟิลด์ของกล่องหนังสือเข้า/กล่องหนังสือออก", required = true)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String inoutFieldDescription;
     
    @XmlElement(name = "inoutFieldType")
    @ApiParam(name = "inoutFieldType", value = "ประเภทของชื่อฟิลด์ของกล่องหนังสือเข้า/กล่องหนังสือออก", required = false)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String inoutFieldType;
    
    @XmlElement(name = "inoutFieldLength")
    @ApiParam(name = "inoutFieldLength", value = "ความยาวของชื่อฟิลด์ขอกล่องหนังสือเข้า/กล่องหนังสือออก", required = false)
    @Expose 
    @Since(1.0)
    private int inoutFieldLength;
    
    public InOutFieldModel() {
    }

    public InOutFieldModel(int id) {
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

    public String getInoutFieldName() {
        return inoutFieldName;
    }

    public void setInoutFieldName(String inoutFieldName) {
        this.inoutFieldName = inoutFieldName;
    }

    public String getInoutFieldDescription() {
        return inoutFieldDescription;
    }

    public void setInoutFieldDescription(String inoutFieldDescription) {
        this.inoutFieldDescription = inoutFieldDescription;
    }

    public String getInoutFieldType() {
        return inoutFieldType;
    }

    public void setInoutFieldType(String inoutFieldType) {
        this.inoutFieldType = inoutFieldType;
    }

    public int getInoutFieldLength() {
        return inoutFieldLength;
    }

    public void setInoutFieldLength(int inoutFieldLength) {
        this.inoutFieldLength = inoutFieldLength;
    }

}
