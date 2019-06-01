package com.px.mwp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@XmlRootElement(name = "inOutAssign")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "กำหนดการเปิดกล่องข้อมูลเข้า/กล่องข้อมูลออก")
public class InOutAssignModel extends VersionModel {

    private static final long serialVersionUID = 2155176976103668336L;

    @XmlElement(name = "id")
    @Expose
    @Since(1.0)
    private int id;
    @Expose
    @Since(1.0)
    private int createdBy;
    @Expose
    @Since(1.0)
    private String createdDate;
    @Expose
    @Since(1.0)
    private float orderNo;
    @Expose
    @Since(1.0)
    private int removedBy;
    @Expose
    @Since(1.0)
    private String removedDate;
    @Expose
    @Since(1.0)
    private int updatedBy;
    @Expose
    @Since(1.0)
    private String updatedDate;

    @XmlElement(name = "inOutAssignOwnerId")
    @ApiParam(name = "inOutAssignOwnerId", value = "รหัสเจ้าของกล่องข้อมูลเข้า/กล่องข้อมูลออก", required = true)
    @Expose
    @Since(1.0)
    private int inOutAssignOwnerId;

    @XmlElement(name = "inOutAssignAssignId")
    @ApiParam(name = "inOutAssignAssignId", value = "รหัสผู้ได้รับการกำหนดให้เปิดกล่องข้อมูลเข้า/กล่องข้อมูลออก", required = true)
    @Expose
    @Since(1.0)
    private int inOutAssignAssignId;

    @XmlElement(name = "inOutAssignOwnerType")
    @ApiParam(name = "inOutAssignOwnerType", value = "ประเภทของเจ้าของกล่องข้อมูลเข้า/กล่องข้อมูลออก [0=User, 1=Structure]", required = true)
    @Expose
    @Since(1.0)
    private int inOutAssignOwnerType;

    @XmlElement(name = "inOutAssignIsperiod")
    @ApiParam(name = "inOutAssignIsperiod", value = "กำหนดระยะเวลาหรือไม่ [0=ไม่กำหนด, 1=กำหนดระยะเวลา]", required = true)
    @Expose
    @Since(1.0)
    private int inOutAssignIsperiod;

    @XmlElement(name = "inOutAssignStartDate")
    @ApiParam(name = "inOutAssignStartDate", value = "วันที่เริ่มกำหนด", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String inOutAssignStartDate;

    @XmlElement(name = "inOutAssignEndDate")
    @ApiParam(name = "inOutAssignEndDate", value = "วันที่สิ้นสุดการกำหนด", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String inOutAssignEndDate;

    //oat-add
    @XmlElement(name = "ownerName")
    @Expose
    private String ownerName;

    public InOutAssignModel() {
    }

    public InOutAssignModel(int id) {
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

    public int getInOutAssignOwnerId() {
        return inOutAssignOwnerId;
    }

    @ApiModelProperty(name = "inOutAssignOwnerId", example = "0", dataType = "int", value = "รหัสเจ้าของกล่องข้อมูลเข้า/กล่องข้อมูลออก", required = true)
    public void setInOutAssignOwnerId(int inOutAssignOwnerId) {
        this.inOutAssignOwnerId = inOutAssignOwnerId;
    }

    public int getInOutAssignAssignId() {
        return inOutAssignAssignId;
    }

    @ApiModelProperty(name = "inOutAssignAssignId", example = "0", dataType = "int", value = "รหัสผู้ได้รับการกำหนดให้เปิดกล่องข้อมูลเข้า/กล่องข้อมูลออก", required = true)
    public void setInOutAssignAssignId(int inOutAssignAssignId) {
        this.inOutAssignAssignId = inOutAssignAssignId;
    }

    public int getInOutAssignOwnerType() {
        return inOutAssignOwnerType;
    }

    @ApiModelProperty(name = "inOutAssignOwnerType", example = "0", dataType = "int", value = "ประเภทของเจ้าของกล่องข้อมูลเข้า/กล่องข้อมูลออก [0=User, 1=Structure]", required = true)
    public void setInOutAssignOwnerType(int inOutAssignOwnerType) {
        this.inOutAssignOwnerType = inOutAssignOwnerType;
    }

    public int getInOutAssignIsperiod() {
        return inOutAssignIsperiod;
    }

    @ApiModelProperty(name = "inOutAssignIsperiod", example = "0", dataType = "int", value = "กำหนดระยะเวลาหรือไม่ [0=ไม่กำหนด, 1=กำหนดระยะเวลา]", required = true)
    public void setInOutAssignIsperiod(int inOutAssignIsperiod) {
        this.inOutAssignIsperiod = inOutAssignIsperiod;
    }

    public String getInOutAssignStartDate() {
        return inOutAssignStartDate;
    }

    @ApiModelProperty(name = "inOutAssignStartDate", example = "01/01/2560", dataType = "string", value = "วันที่เริ่มกำหนด", required = false)
    public void setInOutAssignStartDate(String inOutAssignStartDate) {
        this.inOutAssignStartDate = inOutAssignStartDate;
    }

    public String getInOutAssignEndDate() {
        return inOutAssignEndDate;
    }

    @ApiModelProperty(name = "inOutAssignEndDate", example = "31/12/2560", dataType = "string", value = "วันที่สิ้นสุดการกำหนด", required = false)
    public void setInOutAssignEndDate(String inOutAssignEndDate) {
        this.inOutAssignEndDate = inOutAssignEndDate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

}
