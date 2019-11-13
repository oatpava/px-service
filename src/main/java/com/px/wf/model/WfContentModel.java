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
@XmlRootElement(name = "wfContent")
@ApiModel(description = "สารบรรณ")
public class WfContentModel extends VersionModel {

    private static final long serialVersionUID = -2443067835898627821L;

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
    private String wfContentSpeedStr;
    @Expose
    @Since(1.0)
    private String wfContentSecretStr;
    @Expose
    @Since(1.0)
    private int convertId;

    @XmlElement(name = "wfContentFolderId")
    @ApiParam(name = "wfContentFolderId", value = "รหัสแฟ้มสารบรรณ", required = true)
    @Since(1.0)
    @Expose
    private int wfContentFolderId;

    @XmlElement(name = "wfContentContentPre")
    @ApiParam(name = "wfContentContentPre", value = "เลขทะเบียนขึ้นต้นด้วย", required = false)
    @Size(max = 50)
    @Since(1.0)
    @Expose
    private String wfContentContentPre;

    @XmlElement(name = "wfContentContentYear")
    @ApiParam(name = "wfContentContentYear", value = "ปีของเลขทะเบียน", required = true)
    @Since(1.0)
    @Expose
    private Integer wfContentContentYear;

    @XmlElement(name = "wfContentContentNo")
    @ApiParam(name = "wfContentContentNo", value = "เลขทะเบียน", required = true)
    @Size(max = 50)
    @Since(1.0)
    @Expose
    private String wfContentContentNo;

    @XmlElement(name = "wfContentContentNumber")
    @ApiParam(name = "wfContentContentNumber", value = "หมายเลขของเลขทะเบียน", required = true)
    @Since(1.0)
    @Expose
    private Integer wfContentContentNumber;

    @XmlElement(name = "wfContentContentPoint")
    @ApiParam(name = "wfContentContentPoint", value = "จุดของเลขทะเบียน", required = true)
    @Since(1.0)
    @Expose
    private Integer wfContentContentPoint;

    @XmlElement(name = "wfContentContentDate")
    @ApiParam(name = "wfContentContentDate", value = "วันที่", required = true)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentContentDate;

    @XmlElement(name = "wfContentContentTime")
    @ApiParam(name = "wfContentContentTime", value = "เวลา", required = true)
    @Size(max = 50)
    @Since(1.0)
    @Expose
    private String wfContentContentTime;

    @XmlElement(name = "wfContentBookPre")
    @ApiParam(name = "wfContentBookPre", value = "เลขที่หนังสือขึ้นต้นด้วย", required = false)
    @Size(max = 50)
    @Since(1.0)
    @Expose
    private String wfContentBookPre;

    @XmlElement(name = "wfContentBookYear")
    @ApiParam(name = "wfContentBookYear", value = "ปีของเลขที่หนังสือ", required = false)
    @Since(1.0)
    @Expose
    private Integer wfContentBookYear;

    @XmlElement(name = "wfContentBookNo")
    @ApiParam(name = "wfContentBookNo", value = "เลขที่หนังสือ", required = false)
    @Size(max = 50)
    @Since(1.0)
    @Expose
    private String wfContentBookNo;

    @XmlElement(name = "wfContentBookNumber")
    @ApiParam(name = "wfContentBookNumber", value = "หมายเลขของเลขที่หนังสือ", required = false)
    @Since(1.0)
    @Expose
    private Integer wfContentBookNumber;

    @XmlElement(name = "wfContentBookPoint")
    @ApiParam(name = "wfContentBookPoint", value = "จุดของเลขที่หนังสือ", required = false)
    @Since(1.0)
    @Expose
    private Integer wfContentBookPoint;

    @XmlElement(name = "wfContentBookDate")
    @ApiParam(name = "wfContentBookDate", value = "ลงวันที่", required = true)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentBookDate;

    @XmlElement(name = "wfContentFrom")
    @ApiParam(name = "wfContentFrom", value = "จาก", required = false)
    @Size(max = 1000)
    @Since(1.0)
    @Expose
    private String wfContentFrom;

    @XmlElement(name = "wfContentTo")
    @ApiParam(name = "wfContentTo", value = "ถึง", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentTo;

    @XmlElement(name = "wfContentTitle")
    @ApiParam(name = "wfContentTitle", value = "เรื่อง", required = true)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentTitle;

    @XmlElement(name = "wfContentSpeed")
    @ApiParam(name = "wfContentSpeed", value = "ความเร่งด่วน", required = false)
    @Since(1.0)
    @Expose
    private int wfContentSpeed;

    @XmlElement(name = "wfContentSecret")
    @ApiParam(name = "wfContentSecret", value = "ชั้นความลับ", required = false)
    @Since(1.0)
    @Expose
    private int wfContentSecret;

    @XmlElement(name = "wfContentDescription")
    @ApiParam(name = "wfContentDescription", value = "หมายเหตุ", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentDescription;

    @XmlElement(name = "wfContentReference")
    @ApiParam(name = "wfContentReference", value = "อ้างถึง", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentReference;

    @XmlElement(name = "wfContentAttachment")
    @ApiParam(name = "wfContentAttachment", value = "สิ่งที่ส่งมาด้วย", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentAttachment;

    @XmlElement(name = "wfContentExpireDate")
    @ApiParam(name = "wfContentExpireDate", value = "วันหมดอายุ", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentExpireDate;

    @XmlElement(name = "wfContentOwnername")
    @ApiParam(name = "wfContentOwnername", value = "ผู้รับผิดชอบ", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentOwnername;

    @XmlElement(name = "wfDocumentId")
    @ApiParam(name = "wfDocumentId", value = "รหัสเอกสาร", required = false)
    @Since(1.0)
    @Expose
    private int wfDocumentId;

    @XmlElement(name = "workflowId")
    @ApiParam(name = "workflowId", value = "รหัสผังการไหล", required = false)
    @Since(1.0)
    @Expose
    private int workflowId;

    @XmlElement(name = "inboxId")
    @ApiParam(name = "inboxId", value = "รหัสกล่องหนังสือเข้า", required = false)
    @Since(1.0)
    @Expose
    private int inboxId;

    @XmlElement(name = "wfContentStr01")
    @ApiParam(name = "wfContentStr01", value = "ข้อมูลหนังสือสารบรรณ1", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentStr01;

    @XmlElement(name = "wfContentStr02")
    @ApiParam(name = "wfContentStr02", value = "ข้อมูลหนังสือสารบรรณ2", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentStr02;

    @XmlElement(name = "wfContentStr03")
    @ApiParam(name = "wfContentStr03", value = "ข้อมูลหนังสือสารบรรณ3", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentStr03;

    @XmlElement(name = "wfContentStr04")
    @ApiParam(name = "wfContentStr04", value = "ข้อมูลหนังสือสารบรรณ4", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentStr04;

    @XmlElement(name = "wfContentStr05")
    @ApiParam(name = "wfContentStr05", value = "ข้อมูลหนังสือสารบรรณ5", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentStr05;

    @XmlElement(name = "wfContentStr06")
    @ApiParam(name = "wfContentStr06", value = "ข้อมูลหนังสือสารบรรณ6", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentStr06;

    @XmlElement(name = "wfContentStr07")
    @ApiParam(name = "wfContentStr07", value = "ข้อมูลหนังสือสารบรรณ7", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentStr07;

    @XmlElement(name = "wfContentStr08")
    @ApiParam(name = "wfContentStr08", value = "ข้อมูลหนังสือสารบรรณ8", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentStr08;

    @XmlElement(name = "wfContentStr09")
    @ApiParam(name = "wfContentStr09", value = "ข้อมูลหนังสือสารบรรณ9", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentStr09;

    @XmlElement(name = "wfContentStr10")
    @ApiParam(name = "wfContentStr10", value = "ข้อมูลหนังสือสารบรรณ10", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentStr10;

    @XmlElement(name = "wfContentText01")
    @ApiParam(name = "wfContentText01", value = "ข้อความหนังสือสารบรรณ1", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentText01;

    @XmlElement(name = "wfContentText02")
    @ApiParam(name = "wfContentText02", value = "ข้อความหนังสือสารบรรณ2", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentText02;

    @XmlElement(name = "wfContentText03")
    @ApiParam(name = "wfContentText03", value = "ข้อความหนังสือสารบรรณ3", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentText03;

    @XmlElement(name = "wfContentText04")
    @ApiParam(name = "wfContentText04", value = "ข้อความหนังสือสารบรรณ4", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentText04;

    @XmlElement(name = "wfContentText05")
    @ApiParam(name = "wfContentText05", value = "ข้อความหนังสือสารบรรณ5", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentText05;

    @XmlElement(name = "wfContentText06")
    @ApiParam(name = "wfContentText06", value = "ข้อความหนังสือสารบรรณ6", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentText06;

    @XmlElement(name = "wfContentText07")
    @ApiParam(name = "wfContentText07", value = "ข้อความหนังสือสารบรรณ7", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentText07;

    @XmlElement(name = "wfContentText08")
    @ApiParam(name = "wfContentText08", value = "ข้อความหนังสือสารบรรณ8", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentText08;

    @XmlElement(name = "wfContentText09")
    @ApiParam(name = "wfContentText09", value = "ข้อความหนังสือสารบรรณ9", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentText09;

    @XmlElement(name = "wfContentText10")
    @ApiParam(name = "wfContentText10", value = "ข้อความหนังสือสารบรรณ10", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentText10;

    @XmlElement(name = "wfContentInt01")
    @ApiParam(name = "wfContentInt01", value = "เลขข้อมูลหนังสือสารบรรณ1", required = false)
    @Since(1.0)
    @Expose
    private int wfContentInt01;

    @XmlElement(name = "wfContentInt02")
    @ApiParam(name = "wfContentInt02", value = "เลขข้อมูลหนังสือสารบรรณ2", required = false)
    @Since(1.0)
    @Expose
    private int wfContentInt02;

    @XmlElement(name = "wfContentInt03")
    @ApiParam(name = "wfContentInt03", value = "เลขข้อมูลหนังสือสารบรรณ3", required = false)
    @Since(1.0)
    @Expose
    private int wfContentInt03;

    @XmlElement(name = "wfContentInt04")
    @ApiParam(name = "wfContentInt04", value = "เลขข้อมูลหนังสือสารบรรณ4", required = false)
    @Since(1.0)
    @Expose
    private int wfContentInt04;

    @XmlElement(name = "wfContentInt05")
    @ApiParam(name = "wfContentInt05", value = "เลขข้อมูลหนังสือสารบรรณ5", required = false)
    @Since(1.0)
    @Expose
    private int wfContentInt05;

    @XmlElement(name = "wfContentInt06")
    @ApiParam(name = "wfContentInt06", value = "เลขข้อมูลหนังสือสารบรรณ6", required = false)
    @Since(1.0)
    @Expose
    private int wfContentInt06;

    @XmlElement(name = "wfContentInt07")
    @ApiParam(name = "wfContentInt07", value = "เลขข้อมูลหนังสือสารบรรณ7", required = false)
    @Since(1.0)
    @Expose
    private int wfContentInt07;

    @XmlElement(name = "wfContentInt08")
    @ApiParam(name = "wfContentInt08", value = "เลขข้อมูลหนังสือสารบรรณ8", required = false)
    @Since(1.0)
    @Expose
    private int wfContentInt08;

    @XmlElement(name = "wfContentInt09")
    @ApiParam(name = "wfContentInt09", value = "เลขข้อมูลหนังสือสารบรรณ9", required = false)
    @Since(1.0)
    @Expose
    private int wfContentInt09;

    @XmlElement(name = "wfContentInt10")
    @ApiParam(name = "wfContentInt10", value = "เลขข้อมูลหนังสือสารบรรณ10", required = false)
    @Since(1.0)
    @Expose
    private int wfContentInt10;

    @XmlElement(name = "wfContentDate01")
    @ApiParam(name = "wfContentDate01", value = "วันที่ข้อมูลหนังสือสารบรรณ1", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentDate01;

    @XmlElement(name = "wfContentDate02")
    @ApiParam(name = "wfContentDate02", value = "วันที่ข้อมูลหนังสือสารบรรณ2", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentDate02;

    @XmlElement(name = "wfContentDate03")
    @ApiParam(name = "wfContentDate03", value = "วันที่ข้อมูลหนังสือสารบรรณ3", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentDate03;

    @XmlElement(name = "wfContentDate04")
    @ApiParam(name = "wfContentDate04", value = "วันที่ข้อมูลหนังสือสารบรรณ4", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentDate04;

    @XmlElement(name = "wfContentDate05")
    @ApiParam(name = "wfContentDate05", value = "วันที่ข้อมูลหนังสือสารบรรณ5", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentDate05;

    @XmlElement(name = "wfContentDate06")
    @ApiParam(name = "wfContentDate06", value = "วันที่ข้อมูลหนังสือสารบรรณ6", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentDate06;

    @XmlElement(name = "wfContentDate07")
    @ApiParam(name = "wfContentDate07", value = "วันที่ข้อมูลหนังสือสารบรรณ7", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentDate07;

    @XmlElement(name = "wfContentDate08")
    @ApiParam(name = "wfContentDate08", value = "วันที่ข้อมูลหนังสือสารบรรณ8", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentDate08;

    @XmlElement(name = "wfContentDate09")
    @ApiParam(name = "wfContentDate09", value = "วันที่ข้อมูลหนังสือสารบรรณ9", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentDate09;

    @XmlElement(name = "wfContentDate10")
    @ApiParam(name = "wfContentDate10", value = "วันที่ข้อมูลหนังสือสารบรรณ10", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentDate10;

    public WfContentModel() {
    }

    public WfContentModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(position = 1, name = "id", value = "รหัสสารบรรณ", required = true)
    public void setId(int id) {
        this.id = id;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    @ApiModelProperty(name = "createdBy", example = "0", dataType = "int", value = "ผู้สร้าง", required = false)
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    @ApiModelProperty(name = "createdDate", example = " ", dataType = "string", value = "วันที่สร้าง", required = false)
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public float getOrderNo() {
        return orderNo;
    }

    @ApiModelProperty(name = "orderNo", example = "0", dataType = "int", value = "ลำดับ", required = false)
    public void setOrderNo(float orderNo) {
        this.orderNo = orderNo;
    }

    public int getRemovedBy() {
        return removedBy;
    }

    @ApiModelProperty(name = "removedBy", example = "0", dataType = "int", value = "ผู้ลบ", required = false)
    public void setRemovedBy(int removedBy) {
        this.removedBy = removedBy;
    }

    public String getRemovedDate() {
        return removedDate;
    }

    @ApiModelProperty(name = "removedDate", example = " ", dataType = "string", value = "วันที่ลบ", required = false)
    public void setRemovedDate(String removedDate) {
        this.removedDate = removedDate;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    @ApiModelProperty(name = "updatedBy", example = "0", dataType = "int", value = "ผู้แก้ไข", required = false)
    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    @ApiModelProperty(name = "updatedDate", example = " ", dataType = "string", value = "วันที่แก้ไข", required = false)
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getWfContentFolderId() {
        return wfContentFolderId;
    }

    @ApiModelProperty(name = "wfContentFolderId", example = "1", dataType = "int", value = "รหัสแฟ้มทะเบียน", required = true)
    public void setWfContentFolderId(int wfContentFolderId) {
        this.wfContentFolderId = wfContentFolderId;
    }

    public String getWfContentContentPre() {
        return wfContentContentPre;
    }

    @ApiModelProperty(name = "wfContentContentPre", example = " ", dataType = "string", value = "เลขทะเบียนขึ้นต้นด้วย", required = false)
    public void setWfContentContentPre(String wfContentContentPre) {
        this.wfContentContentPre = wfContentContentPre;
    }

    public Integer getWfContentContentYear() {
        return wfContentContentYear;
    }

    @ApiModelProperty(name = "wfContentContentYear", example = "0", dataType = "int", value = "ปีของเลขทะเบียน", required = false)
    public void setWfContentContentYear(Integer wfContentContentYear) {
        this.wfContentContentYear = wfContentContentYear;
    }

    public String getWfContentContentNo() {
        return wfContentContentNo;
    }

    @ApiModelProperty(name = "wfContentContentNo", example = "00001/2560", dataType = "string", value = "เลขทะเบียน", required = true)
    public void setWfContentContentNo(String wfContentContentNo) {
        this.wfContentContentNo = wfContentContentNo;
    }

    public Integer getWfContentContentNumber() {
        return wfContentContentNumber;
    }

    @ApiModelProperty(name = "wfContentContentNumber", example = "0", dataType = "int", value = "เลขของทะเบียน", required = false)
    public void setWfContentContentNumber(Integer wfContentContentNumber) {
        this.wfContentContentNumber = wfContentContentNumber;
    }

    public Integer getWfContentContentPoint() {
        return wfContentContentPoint;
    }

    @ApiModelProperty(name = "wfContentContentPoint", example = "0", dataType = "int", value = "จุดของเลขทะเบียน", required = false)
    public void setWfContentContentPoint(Integer wfContentContentPoint) {
        this.wfContentContentPoint = wfContentContentPoint;
    }

    public String getWfContentContentDate() {
        return wfContentContentDate;
    }

    @ApiModelProperty(name = "wfContentContentDate", example = "01/02/2560", dataType = "string", value = "วันที่", required = true)
    public void setWfContentContentDate(String wfContentContentDate) {
        this.wfContentContentDate = wfContentContentDate;
    }

    public String getWfContentContentTime() {
        return wfContentContentTime;
    }

    @ApiModelProperty(name = "wfContentContentTime", example = "10:20", dataType = "string", value = "เวลา", required = true)
    public void setWfContentContentTime(String wfContentContentTime) {
        this.wfContentContentTime = wfContentContentTime;
    }

    public String getWfContentBookPre() {
        return wfContentBookPre;
    }

    @ApiModelProperty(name = "wfContentBookPre", example = " ", dataType = "string", value = "เลขที่หนังสือขึ้นต้นด้วย", required = false)
    public void setWfContentBookPre(String wfContentBookPre) {
        this.wfContentBookPre = wfContentBookPre;
    }

    public Integer getWfContentBookYear() {
        return wfContentBookYear;
    }

    @ApiModelProperty(name = "wfContentBookYear", example = "0", dataType = "int", value = "ปีของเลขที่หนังสือ", required = false)
    public void setWfContentBookYear(Integer wfContentBookYear) {
        this.wfContentBookYear = wfContentBookYear;
    }

    public String getWfContentBookNo() {
        return wfContentBookNo;
    }

    @ApiModelProperty(name = "wfContentBookNo", example = "00001/2560", dataType = "string", value = "เลขที่หนังสือ", required = true)
    public void setWfContentBookNo(String wfContentBookNo) {
        this.wfContentBookNo = wfContentBookNo;
    }

    public Integer getWfContentBookNumber() {
        return wfContentBookNumber;
    }

    @ApiModelProperty(name = "wfContentBookNumber", example = "1", dataType = "int", value = "เลขของเลขที่หนังสือ", required = false)
    public void setWfContentBookNumber(Integer wfContentBookNumber) {
        this.wfContentBookNumber = wfContentBookNumber;
    }

    public Integer getWfContentBookPoint() {
        return wfContentBookPoint;
    }

    @ApiModelProperty(name = "wfContentBookPoint", example = "0", dataType = "int", value = "จุดของเลขที่หนังสือ", required = false)
    public void setWfContentBookPoint(Integer wfContentBookPoint) {
        this.wfContentBookPoint = wfContentBookPoint;
    }

    public String getWfContentBookDate() {
        return wfContentBookDate;
    }

    @ApiModelProperty(name = "wfContentBookDate", example = "02/02/2560", dataType = "string", value = "ลงวันที่", required = true)
    public void setWfContentBookDate(String wfContentBookDate) {
        this.wfContentBookDate = wfContentBookDate;
    }

    public String getWfContentFrom() {
        return wfContentFrom;
    }

    @ApiModelProperty(name = "wfContentFrom", example = "ทดสอบจาก", dataType = "string", value = "จาก", required = true)
    public void setWfContentFrom(String wfContentFrom) {
        this.wfContentFrom = wfContentFrom;
    }

    public String getWfContentTo() {
        return wfContentTo;
    }

    @ApiModelProperty(name = "wfContentTo", example = "ทดสอบถึง", dataType = "string", value = "ถึง", required = true)
    public void setWfContentTo(String wfContentTo) {
        this.wfContentTo = wfContentTo;
    }

    public String getWfContentTitle() {
        return wfContentTitle;
    }

    @ApiModelProperty(name = "wfContentTitle", example = "ทดสอบเรื่อง", dataType = "string", value = "เรื่อง", required = true)
    public void setWfContentTitle(String wfContentTitle) {
        this.wfContentTitle = wfContentTitle;
    }

    public int getWfContentSpeed() {
        return wfContentSpeed;
    }

    @ApiModelProperty(name = "wfContentSpeed", example = "1", dataType = "int", value = "ความเร่งด่วน", required = true)
    public void setWfContentSpeed(int wfContentSpeed) {
        this.wfContentSpeed = wfContentSpeed;
    }

    public int getWfContentSecret() {
        return wfContentSecret;
    }

    @ApiModelProperty(name = "wfContentSecret", example = "1", dataType = "int", value = "ชั้นความลับ", required = true)
    public void setWfContentSecret(int wfContentSecret) {
        this.wfContentSecret = wfContentSecret;
    }

    public String getWfContentDescription() {
        return wfContentDescription;
    }

    @ApiModelProperty(name = "wfContentDescription", example = " ", dataType = "string", value = "หมายเหตุ", required = false)
    public void setWfContentDescription(String wfContentDescription) {
        this.wfContentDescription = wfContentDescription;
    }

    public String getWfContentReference() {
        return wfContentReference;
    }

    @ApiModelProperty(name = "wfContentReference", example = " ", dataType = "string", value = "อ้างถึง", required = false)
    public void setWfContentReference(String wfContentReference) {
        this.wfContentReference = wfContentReference;
    }

    public String getWfContentAttachment() {
        return wfContentAttachment;
    }

    @ApiModelProperty(name = "wfContentAttachment", example = " ", dataType = "string", value = "สิ่งที่ส่งมาด้วย", required = false)
    public void setWfContentAttachment(String wfContentAttachment) {
        this.wfContentAttachment = wfContentAttachment;
    }

    public String getWfContentExpireDate() {
        return wfContentExpireDate;
    }

    @ApiModelProperty(name = "wfContentExpireDate", example = "01/05/2560", dataType = "string", value = "วันที่หมดอายุ", required = false)
    public void setWfContentExpireDate(String wfContentExpireDate) {
        this.wfContentExpireDate = wfContentExpireDate;
    }

    public String getWfContentOwnername() {
        return wfContentOwnername;
    }

    @ApiModelProperty(name = "wfContentOwnername", example = "ทดสอบ", dataType = "string", value = "ชื่อเจ้าของหนังสือ", required = true)
    public void setWfContentOwnername(String wfContentOwnername) {
        this.wfContentOwnername = wfContentOwnername;
    }

    public int getWfDocumentId() {
        return wfDocumentId;
    }

    @ApiModelProperty(name = "wfDocumentId", example = "0", dataType = "int", value = "รหัสเอกสารแนบ", required = true)
    public void setWfDocumentId(int wfDocumentId) {
        this.wfDocumentId = wfDocumentId;
    }

    public int getWorkflowId() {
        return workflowId;
    }

    @ApiModelProperty(name = "workflowId", example = "0", dataType = "int", value = "รหัสผังการไหล", required = false)
    public void setWorkflowId(int workflowId) {
        this.workflowId = workflowId;
    }

    public int getInboxId() {
        return inboxId;
    }

    @ApiModelProperty(name = "inboxId", example = "0", dataType = "int", value = "รหัสกล่องหนังสือเข้า", required = false)
    public void setInboxId(int inboxId) {
        this.inboxId = inboxId;
    }

    public String getWfContentStr01() {
        return wfContentStr01;
    }

    @ApiModelProperty(name = "wfContentStr01", example = " ", dataType = "string", value = "ข้อมูลหนังสือสารบรรณ1", required = false)
    public void setWfContentStr01(String wfContentStr01) {
        this.wfContentStr01 = wfContentStr01;
    }

    public String getWfContentStr02() {
        return wfContentStr02;
    }

    @ApiModelProperty(name = "wfContentStr02", example = " ", dataType = "string", value = "ข้อมูลหนังสือสารบรรณ2", required = false)
    public void setWfContentStr02(String wfContentStr02) {
        this.wfContentStr02 = wfContentStr02;
    }

    public String getWfContentStr03() {
        return wfContentStr03;
    }

    @ApiModelProperty(name = "wfContentStr03", example = " ", dataType = "string", value = "ข้อมูลหนังสือสารบรรณ3", required = false)
    public void setWfContentStr03(String wfContentStr03) {
        this.wfContentStr03 = wfContentStr03;
    }

    public String getWfContentStr04() {
        return wfContentStr04;
    }

    @ApiModelProperty(name = "wfContentStr04", example = " ", dataType = "string", value = "ข้อมูลหนังสือสารบรรณ4", required = false)
    public void setWfContentStr04(String wfContentStr04) {
        this.wfContentStr04 = wfContentStr04;
    }

    public String getWfContentStr05() {
        return wfContentStr05;
    }

    @ApiModelProperty(name = "wfContentStr05", example = " ", dataType = "string", value = "ข้อมูลหนังสือสารบรรณ5", required = false)
    public void setWfContentStr05(String wfContentStr05) {
        this.wfContentStr05 = wfContentStr05;
    }

    public String getWfContentStr06() {
        return wfContentStr06;
    }

    @ApiModelProperty(name = "wfContentStr06", example = " ", dataType = "string", value = "ข้อมูลหนังสือสารบรรณ6", required = false)
    public void setWfContentStr06(String wfContentStr06) {
        this.wfContentStr06 = wfContentStr06;
    }

    public String getWfContentStr07() {
        return wfContentStr07;
    }

    @ApiModelProperty(name = "wfContentStr07", example = " ", dataType = "string", value = "ข้อมูลหนังสือสารบรรณ7", required = false)
    public void setWfContentStr07(String wfContentStr07) {
        this.wfContentStr07 = wfContentStr07;
    }

    public String getWfContentStr08() {
        return wfContentStr08;
    }

    @ApiModelProperty(name = "wfContentStr08", example = " ", dataType = "string", value = "ข้อมูลหนังสือสารบรรณ8", required = false)
    public void setWfContentStr08(String wfContentStr08) {
        this.wfContentStr08 = wfContentStr08;
    }

    public String getWfContentStr09() {
        return wfContentStr09;
    }

    @ApiModelProperty(name = "wfContentStr09", example = " ", dataType = "string", value = "ข้อมูลหนังสือสารบรรณ9", required = false)
    public void setWfContentStr09(String wfContentStr09) {
        this.wfContentStr09 = wfContentStr09;
    }

    public String getWfContentStr10() {
        return wfContentStr10;
    }

    @ApiModelProperty(name = "wfContentStr10", example = " ", dataType = "string", value = "ข้อมูลหนังสือสารบรรณ10", required = false)
    public void setWfContentStr10(String wfContentStr10) {
        this.wfContentStr10 = wfContentStr10;
    }

    public String getWfContentText01() {
        return wfContentText01;
    }

    @ApiModelProperty(name = "wfContentText01", example = " ", dataType = "string", value = "ข้อความหนังสือสารบรรณ1", required = false)
    public void setWfContentText01(String wfContentText01) {
        this.wfContentText01 = wfContentText01;
    }

    public String getWfContentText02() {
        return wfContentText02;
    }

    @ApiModelProperty(name = "wfContentText02", example = " ", dataType = "string", value = "ข้อความหนังสือสารบรรณ2", required = false)
    public void setWfContentText02(String wfContentText02) {
        this.wfContentText02 = wfContentText02;
    }

    public String getWfContentText03() {
        return wfContentText03;
    }

    @ApiModelProperty(name = "wfContentText03", example = " ", dataType = "string", value = "ข้อความหนังสือสารบรรณ3", required = false)
    public void setWfContentText03(String wfContentText03) {
        this.wfContentText03 = wfContentText03;
    }

    public String getWfContentText04() {
        return wfContentText04;
    }

    @ApiModelProperty(name = "wfContentText04", example = " ", dataType = "string", value = "ข้อความหนังสือสารบรรณ4", required = false)
    public void setWfContentText04(String wfContentText04) {
        this.wfContentText04 = wfContentText04;
    }

    public String getWfContentText05() {
        return wfContentText05;
    }

    @ApiModelProperty(name = "wfContentText05", example = " ", dataType = "string", value = "ข้อความหนังสือสารบรรณ5", required = false)
    public void setWfContentText05(String wfContentText05) {
        this.wfContentText05 = wfContentText05;
    }

    public String getWfContentText06() {
        return wfContentText06;
    }

    @ApiModelProperty(name = "wfContentText06", example = " ", dataType = "string", value = "ข้อความหนังสือสารบรรณ6", required = false)
    public void setWfContentText06(String wfContentText06) {
        this.wfContentText06 = wfContentText06;
    }

    public String getWfContentText07() {
        return wfContentText07;
    }

    @ApiModelProperty(name = "wfContentText07", example = " ", dataType = "string", value = "ข้อความหนังสือสารบรรณ7", required = false)
    public void setWfContentText07(String wfContentText07) {
        this.wfContentText07 = wfContentText07;
    }

    public String getWfContentText08() {
        return wfContentText08;
    }

    @ApiModelProperty(name = "wfContentText08", example = " ", dataType = "string", value = "ข้อความหนังสือสารบรรณ8", required = false)
    public void setWfContentText08(String wfContentText08) {
        this.wfContentText08 = wfContentText08;
    }

    public String getWfContentText09() {
        return wfContentText09;
    }

    @ApiModelProperty(name = "wfContentText09", example = " ", dataType = "string", value = "ข้อความหนังสือสารบรรณ9", required = false)
    public void setWfContentText09(String wfContentText09) {
        this.wfContentText09 = wfContentText09;
    }

    public String getWfContentText10() {
        return wfContentText10;
    }

    @ApiModelProperty(name = "wfContentText10", example = " ", dataType = "string", value = "ข้อความหนังสือสารบรรณ10", required = false)
    public void setWfContentText10(String wfContentText10) {
        this.wfContentText10 = wfContentText10;
    }

    public int getWfContentInt01() {
        return wfContentInt01;
    }

    @ApiModelProperty(name = "wfContentInt01", example = "0", dataType = "int", value = "เลขข้อมูลหนังสือสารบรรณ1", required = false)
    public void setWfContentInt01(int wfContentInt01) {
        this.wfContentInt01 = wfContentInt01;
    }

    public int getWfContentInt02() {
        return wfContentInt02;
    }

    @ApiModelProperty(name = "wfContentInt02", example = "0", dataType = "int", value = "เลขข้อมูลหนังสือสารบรรณ2", required = false)
    public void setWfContentInt02(int wfContentInt02) {
        this.wfContentInt02 = wfContentInt02;
    }

    public int getWfContentInt03() {
        return wfContentInt03;
    }

    @ApiModelProperty(name = "wfContentInt03", example = "0", dataType = "int", value = "เลขข้อมูลหนังสือสารบรรณ3", required = false)
    public void setWfContentInt03(int wfContentInt03) {
        this.wfContentInt03 = wfContentInt03;
    }

    public int getWfContentInt04() {
        return wfContentInt04;
    }

    @ApiModelProperty(name = "wfContentInt04", example = "0", dataType = "int", value = "เลขข้อมูลหนังสือสารบรรณ4", required = false)
    public void setWfContentInt04(int wfContentInt04) {
        this.wfContentInt04 = wfContentInt04;
    }

    public int getWfContentInt05() {
        return wfContentInt05;
    }

    @ApiModelProperty(name = "wfContentInt05", example = "0", dataType = "int", value = "เลขข้อมูลหนังสือสารบรรณ5", required = false)
    public void setWfContentInt05(int wfContentInt05) {
        this.wfContentInt05 = wfContentInt05;
    }

    public int getWfContentInt06() {
        return wfContentInt06;
    }

    @ApiModelProperty(name = "wfContentInt06", example = "0", dataType = "int", value = "เลขข้อมูลหนังสือสารบรรณ6", required = false)
    public void setWfContentInt06(int wfContentInt06) {
        this.wfContentInt06 = wfContentInt06;
    }

    public int getWfContentInt07() {
        return wfContentInt07;
    }

    @ApiModelProperty(name = "wfContentInt07", example = "0", dataType = "int", value = "เลขข้อมูลหนังสือสารบรรณ7", required = false)
    public void setWfContentInt07(int wfContentInt07) {
        this.wfContentInt07 = wfContentInt07;
    }

    public int getWfContentInt08() {
        return wfContentInt08;
    }

    @ApiModelProperty(name = "wfContentInt08", example = "0", dataType = "int", value = "เลขข้อมูลหนังสือสารบรรณ8", required = false)
    public void setWfContentInt08(int wfContentInt08) {
        this.wfContentInt08 = wfContentInt08;
    }

    public int getWfContentInt09() {
        return wfContentInt09;
    }

    @ApiModelProperty(name = "wfContentInt09", example = "0", dataType = "int", value = "เลขข้อมูลหนังสือสารบรรณ9", required = false)
    public void setWfContentInt09(int wfContentInt09) {
        this.wfContentInt09 = wfContentInt09;
    }

    public int getWfContentInt10() {
        return wfContentInt10;
    }

    @ApiModelProperty(name = "wfContentInt10", example = "0", dataType = "int", value = "เลขข้อมูลหนังสือสารบรรณ10", required = false)
    public void setWfContentInt10(int wfContentInt10) {
        this.wfContentInt10 = wfContentInt10;
    }

    public String getWfContentDate01() {
        return wfContentDate01;
    }

    @ApiModelProperty(name = "wfContentDate01", example = "01/05/2560", dataType = "string", value = "วันที่ข้อมูลหนังสือสารบรรณ1", required = false)
    public void setWfContentDate01(String wfContentDate01) {
        this.wfContentDate01 = wfContentDate01;
    }

    public String getWfContentDate02() {
        return wfContentDate02;
    }

    @ApiModelProperty(name = "wfContentDate02", example = "01/05/2560", dataType = "string", value = "วันที่ข้อมูลหนังสือสารบรรณ2", required = false)
    public void setWfContentDate02(String wfContentDate02) {
        this.wfContentDate02 = wfContentDate02;
    }

    public String getWfContentDate03() {
        return wfContentDate03;
    }

    @ApiModelProperty(name = "wfContentDate03", example = "01/05/2560", dataType = "string", value = "วันที่ข้อมูลหนังสือสารบรรณ3", required = false)
    public void setWfContentDate03(String wfContentDate03) {
        this.wfContentDate03 = wfContentDate03;
    }

    public String getWfContentDate04() {
        return wfContentDate04;
    }

    @ApiModelProperty(name = "wfContentDate04", example = "01/05/2560", dataType = "string", value = "วันที่ข้อมูลหนังสือสารบรรณ4", required = false)
    public void setWfContentDate04(String wfContentDate04) {
        this.wfContentDate04 = wfContentDate04;
    }

    public String getWfContentDate05() {
        return wfContentDate05;
    }

    @ApiModelProperty(name = "wfContentDate05", example = "01/05/2560", dataType = "string", value = "วันที่ข้อมูลหนังสือสารบรรณ5", required = false)
    public void setWfContentDate05(String wfContentDate05) {
        this.wfContentDate05 = wfContentDate05;
    }

    public String getWfContentDate06() {
        return wfContentDate06;
    }

    @ApiModelProperty(name = "wfContentDate06", example = "01/05/2560", dataType = "string", value = "วันที่ข้อมูลหนังสือสารบรรณ6", required = false)
    public void setWfContentDate06(String wfContentDate06) {
        this.wfContentDate06 = wfContentDate06;
    }

    public String getWfContentDate07() {
        return wfContentDate07;
    }

    @ApiModelProperty(name = "wfContentDate07", example = "01/05/2560", dataType = "string", value = "วันที่ข้อมูลหนังสือสารบรรณ7", required = false)
    public void setWfContentDate07(String wfContentDate07) {
        this.wfContentDate07 = wfContentDate07;
    }

    public String getWfContentDate08() {
        return wfContentDate08;
    }

    @ApiModelProperty(name = "wfContentDate08", example = "01/05/2560", dataType = "string", value = "วันที่ข้อมูลหนังสือสารบรรณ8", required = false)
    public void setWfContentDate08(String wfContentDate08) {
        this.wfContentDate08 = wfContentDate08;
    }

    public String getWfContentDate09() {
        return wfContentDate09;
    }

    @ApiModelProperty(name = "wfContentDate09", example = "01/05/2560", dataType = "string", value = "วันที่ข้อมูลหนังสือสารบรรณ9", required = false)
    public void setWfContentDate09(String wfContentDate09) {
        this.wfContentDate09 = wfContentDate09;
    }

    public String getWfContentDate10() {
        return wfContentDate10;
    }

    @ApiModelProperty(name = "wfContentDate10", example = "01/05/2560", dataType = "string", value = "วันที่ข้อมูลหนังสือสารบรรณ10", required = false)
    public void setWfContentDate10(String wfContentDate10) {
        this.wfContentDate10 = wfContentDate10;
    }

    public String getWfContentSpeedStr() {
        return wfContentSpeedStr;
    }

    public void setWfContentSpeedStr(String wfContentSpeedStr) {
        this.wfContentSpeedStr = wfContentSpeedStr;
    }

    public String getWfContentSecretStr() {
        return wfContentSecretStr;
    }

    public void setWfContentSecretStr(String wfContentSecretStr) {
        this.wfContentSecretStr = wfContentSecretStr;
    }

    public int getConvertId() {
        return convertId;
    }

    public void setConvertId(int convertId) {
        this.convertId = convertId;
    }

}
