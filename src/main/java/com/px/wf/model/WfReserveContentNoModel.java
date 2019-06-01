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
@XmlRootElement(name = "wFReserveContent")
@ApiModel(description = "การจองเลขทะเบียน")
public class WfReserveContentNoModel extends VersionModel {

    private static final long serialVersionUID = -2189158404163269760L;

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
    @Expose
    @Since(1.0)
    private int reserveContentNoStatus;

    @XmlElement(name = "reserveContentNoFolderId")
    @ApiParam(name = "reserveContentNoFolderId", value = "รหัสแฟ้มสารบรรณ", required = true)
    @Since(1.0)
    @Expose
    private int reserveContentNoFolderId;

    @XmlElement(name = "reserveContentNoContentNo")
    @ApiParam(name = "reserveContentNoContentNo", value = "เลขทะเบียน", required = false)
    @Size(max = 50)
    @Since(1.0)
    @Expose
    private String reserveContentNoContentNo;

    @XmlElement(name = "reserveContentNoContentDate")
    @ApiParam(name = "reserveContentNoContentDate", value = "วันที่", required = false)//oat-edit
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String reserveContentNoContentDate;

    @XmlElement(name = "reserveContentNoContentTime")
    @ApiParam(name = "reserveContentNoContentTime", value = "เวลา", required = false)//oat-edit
    @Size(max = 50)
    @Since(1.0)
    @Expose
    private String reserveContentNoContentTime;

    @XmlElement(name = "reserveContentNoUserId")
    @ApiParam(name = "reserveContentNoUserId", value = "รหัสผู้จองเลข", required = true)
    @Since(1.0)
    @Expose
    private int reserveContentNoUserId;

    @XmlElement(name = "reserveContentNoContentYear")
    @ApiParam(name = "reserveContentNoContentYear", value = "ปีของเลขทะเบียน", required = false)
    @Since(1.0)
    @Expose
    private Integer reserveContentNoContentYear;

    @XmlElement(name = "reserveContentNoContentNumber")
    @ApiParam(name = "reserveContentNoContentNumber", value = "หมายเลขของเลขทะเบียน", required = false)
    @Since(1.0)
    @Expose
    private Integer reserveContentNoContentNumber;

    @XmlElement(name = "reserveContentNoNote")
    @ApiParam(name = "reserveContentNoNote", value = "หมายเหตุ", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String reserveContentNoNote;
    //oat-add
    @XmlElement(name = "reserveContentNoUserName")
    @ApiParam(name = "reserveContentNoUserName", value = "ผู้จองเลข", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String reserveContentNoUserName;

    public WfReserveContentNoModel() {
    }

    public WfReserveContentNoModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(position = 1, name = "id", value = "รหัสการจองเลขทะเบียน", required = true)
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

    public String getReserveContentNoContentDate() {
        return reserveContentNoContentDate;
    }

    public void setReserveContentNoContentDate(String reserveContentNoContentDate) {
        this.reserveContentNoContentDate = reserveContentNoContentDate;
    }

    public int getReserveContentNoStatus() {
        return reserveContentNoStatus;
    }

    public void setReserveContentNoStatus(int reserveContentNoStatus) {
        this.reserveContentNoStatus = reserveContentNoStatus;
    }

    public int getReserveContentNoFolderId() {
        return reserveContentNoFolderId;
    }

    public void setReserveContentNoFolderId(int reserveContentNoFolderId) {
        this.reserveContentNoFolderId = reserveContentNoFolderId;
    }

    public String getReserveContentNoContentNo() {
        return reserveContentNoContentNo;
    }

    public void setReserveContentNoContentNo(String reserveContentNoContentNo) {
        this.reserveContentNoContentNo = reserveContentNoContentNo;
    }

    public String getReserveContentNoContentTime() {
        return reserveContentNoContentTime;
    }

    public void setReserveContentNoContentTime(String reserveContentNoContentTime) {
        this.reserveContentNoContentTime = reserveContentNoContentTime;
    }

    public int getReserveContentNoUserId() {
        return reserveContentNoUserId;
    }

    public void setReserveContentNoUserId(int reserveContentNoUserId) {
        this.reserveContentNoUserId = reserveContentNoUserId;
    }

    public Integer getReserveContentNoContentYear() {
        return reserveContentNoContentYear;
    }

    public void setReserveContentNoContentYear(Integer reserveContentNoContentYear) {
        this.reserveContentNoContentYear = reserveContentNoContentYear;
    }

    public Integer getReserveContentNoContentNumber() {
        return reserveContentNoContentNumber;
    }

    public void setReserveContentNoContentNumber(Integer reserveContentNoContentNumber) {
        this.reserveContentNoContentNumber = reserveContentNoContentNumber;
    }

    public String getReserveContentNoNote() {
        return reserveContentNoNote;
    }

    public void setReserveContentNoNote(String reserveContentNoNote) {
        this.reserveContentNoNote = reserveContentNoNote;
    }
    
    public String getReserveContentNoUserName() {
        return reserveContentNoUserName;
    }

    public void setReserveContentNoUserName(String reserveContentNoUserName) {
        this.reserveContentNoUserName = reserveContentNoUserName;
    }

}
