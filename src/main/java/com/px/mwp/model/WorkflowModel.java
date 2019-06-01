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
@XmlRootElement(name = "workflow")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ผังการไหล")
public class WorkflowModel extends VersionModel {

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

    @XmlElement(name = "linkId")
    @ApiParam(name = "linkId", value = "รหัสเชื่อมโยงเอกสาร1", required = false)
    @Since(1.0)
    @Expose
    private int linkId;

    @XmlElement(name = "linkId2")
    @ApiParam(name = "linkId2", value = "รหัสเชื่อมโยงเอกสาร2", required = false)
    @Since(1.0)
    @Expose
    private int linkId2;

    @XmlElement(name = "linkId3")
    @ApiParam(name = "linkId3", value = "รหัสเชื่อมโยงเอกสาร3", required = false)
    @Since(1.0)
    @Expose
    private int linkId3;

    @XmlElement(name = "workflowActionId")
    @ApiParam(name = "workflowActionId", value = "รหัสผู้ทำงาน", required = false)
    @Since(1.0)
    @Expose
    private int workflowActionId;

    @XmlElement(name = "workflowActionIdType")
    @ApiParam(name = "workflowActionIdType", value = "ประเภทของรหัสผู้ทำงาน 0=user 1=structure", required = false)
    @Since(1.0)
    @Expose
    private int workflowActionIdType;

    @XmlElement(name = "workflowActionName")
    @ApiParam(name = "workflowActionName", value = "ชื่อผู้ทำงาน", required = false)
    @Size(max = 1000)
    @Since(1.0)
    @Expose
    private String workflowActionName;

    @XmlElement(name = "workflowActionPosition")
    @ApiParam(name = "workflowActionPosition", value = "", required = false)
    @Size(max = 1000)
    @Since(1.0)
    @Expose
    private String workflowActionPosition;

    @XmlElement(name = "workflowActionType")
    @ApiParam(name = "workflowActionType", value = "ประเภทการกระทำ", required = false)
    @Size(max = 10)
    @Since(1.0)
    @Expose
    private String workflowActionType;

    @XmlElement(name = "workflowActionDate")
    @ApiParam(name = "workflowActionDate", value = "วันทีกระทำ", required = false)
    @Size(max = 50)
    @Since(1.0)
    private String workflowActionDate;

    @XmlElement(name = "workflowDescription")
    @ApiParam(name = "workflowDescription", value = "หมายเหตุ", required = false)
    @Size(max = 1000)
    @Since(1.0)
    @Expose
    private String workflowDescription;

    @XmlElement(name = "workflowNote")
    @ApiParam(name = "workflowNote", value = "บันทึก", required = false)
    @Size(max = 1000)
    @Since(1.0)
    @Expose
    private String workflowNote;

    @XmlElement(name = "workflowTitle")
    @ApiParam(name = "workflowTitle", value = "ชื่อเรื่อง", required = false)
    @Size(max = 1000)
    @Since(1.0)
    @Expose
    private String workflowTitle;

    @XmlElement(name = "workflowStr01")
    @ApiParam(name = "workflowStr01", value = "ข้อมูลผังการไหล1", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String workflowStr01;

    @XmlElement(name = "workflowStr02")
    @ApiParam(name = "workflowStr02", value = "ข้อมูลผังการไหล2", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String workflowStr02;

    @XmlElement(name = "workflowStr03")
    @ApiParam(name = "workflowStr03", value = "ข้อมูลผังการไหล3", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String workflowStr03;

    @XmlElement(name = "workflowStr04")
    @ApiParam(name = "workflowStr04", value = "ข้อมูลผังการไหล4", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String workflowStr04;

    @XmlElement(name = "workflowDate01")
    @ApiParam(name = "workflowDate01", value = "วันที่ข้อมูลผังการไหล1", required = false)
    @Size(max = 255)
    @Since(1.0)
    private String workflowDate01;

    @XmlElement(name = "workflowDate02")
    @ApiParam(name = "workflowDate02", value = "วันที่ข้อมูลผังการไหล2", required = false)
    @Size(max = 255)
    @Since(1.0)
    private String workflowDate02;

    @XmlElement(name = "workflowDetail")
    @Expose
    @Since(1.0)
    private String workflowDetail;

    public WorkflowModel() {
    }

    public WorkflowModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(position = 1, name = "id", value = "รหัสผังการไหล", required = true)
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

    public int getLinkId() {
        return linkId;
    }

    @ApiModelProperty(name = "linkId", example = "0", dataType = "int", value = "รหัสเชื่อมโยงเอกสาร1", required = true)
    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public int getLinkId2() {
        return linkId2;
    }

    @ApiModelProperty(name = "linkId2", example = "0", dataType = "int", value = "รหัสเชื่อมโยงเอกสาร2", required = false)
    public void setLinkId2(int linkId2) {
        this.linkId2 = linkId2;
    }

    public int getLinkId3() {
        return linkId3;
    }

    @ApiModelProperty(name = "linkId3", example = "0", dataType = "int", value = "รหัสเชื่อมโยงเอกสาร3", required = false)
    public void setLinkId3(int linkId3) {
        this.linkId3 = linkId3;
    }

    public int getWorkflowActionId() {
        return workflowActionId;
    }

    @ApiModelProperty(name = "workflowActionId", example = "0", dataType = "int", value = "รหัสผู้ทำงาน", required = true)
    public void setWorkflowActionId(int workflowActionId) {
        this.workflowActionId = workflowActionId;
    }

    public int getWorkflowActionIdType() {
        return workflowActionIdType;
    }

    @ApiModelProperty(name = "workflowActionIdType", example = "0", dataType = "int", value = "ประเภทของรหัสผู้ทำงาน 0=user 1=structure", required = true)
    public void setWorkflowActionIdType(int workflowActionIdType) {
        this.workflowActionIdType = workflowActionIdType;
    }

    public String getWorkflowActionName() {
        return workflowActionName;
    }

    @ApiModelProperty(name = "workflowActionName", example = "ผู้ทดสอบ", dataType = "string", value = "ชื่อผู้ทำงาน", required = true)
    public void setWorkflowActionName(String workflowActionName) {
        this.workflowActionName = workflowActionName;
    }

    public String getWorkflowActionPosition() {
        return workflowActionPosition;
    }

    @ApiModelProperty(name = "workflowActionPosition", example = "ตำแหน่งผู้ทดสอบ", dataType = "string", value = "ตำแหน่งผู้ทำงาน", required = true)
    public void setWorkflowActionPosition(String workflowActionPosition) {
        this.workflowActionPosition = workflowActionPosition;
    }

    public String getWorkflowActionType() {
        return workflowActionType;
    }

    @ApiModelProperty(name = "workflowActionType", example = "N", dataType = "string", value = "ประเภทการกระทำ", required = true)
    public void setWorkflowActionType(String workflowActionType) {
        this.workflowActionType = workflowActionType;
    }

    public String getWorkflowActionDate() {
        return workflowActionDate;
    }

    @ApiModelProperty(name = "workflowActionDate", example = "01/05/2560", dataType = "string", value = "วันทีกระทำ", required = true)
    public void setWorkflowActionDate(String workflowActionDate) {
        this.workflowActionDate = workflowActionDate;
    }

    public String getWorkflowDescription() {
        return workflowDescription;
    }

    @ApiModelProperty(name = "workflowDescription", example = "", dataType = "string", value = "หมายเหตุ", required = false)
    public void setWorkflowDescription(String workflowDescription) {
        this.workflowDescription = workflowDescription;
    }

    public String getWorkflowNote() {
        return workflowNote;
    }

    @ApiModelProperty(name = "workflowNote", example = "", dataType = "string", value = "บันทึก", required = false)
    public void setWorkflowNote(String workflowNote) {
        this.workflowNote = workflowNote;
    }

    public String getWorkflowTitle() {
        return workflowTitle;
    }

    @ApiModelProperty(name = "workflowTitle", example = "เรื่องทดสอบ", dataType = "string", value = "ชื่อเรื่อง", required = true)
    public void setWorkflowTitle(String workflowTitle) {
        this.workflowTitle = workflowTitle;
    }

    public String getWorkflowStr01() {
        return workflowStr01;
    }

    @ApiModelProperty(name = "workflowStr01", example = " ", dataType = "string", value = "ข้อมูลผังการไหล1", required = false)
    public void setWorkflowStr01(String workflowStr01) {
        this.workflowStr01 = workflowStr01;
    }

    public String getWorkflowStr02() {
        return workflowStr02;
    }

    @ApiModelProperty(name = "workflowStr02", example = " ", dataType = "string", value = "ข้อมูลผังการไหล2", required = false)
    public void setWorkflowStr02(String workflowStr02) {
        this.workflowStr02 = workflowStr02;
    }

    public String getWorkflowStr03() {
        return workflowStr03;
    }

    @ApiModelProperty(name = "workflowStr03", example = " ", dataType = "string", value = "ข้อมูลผังการไหล3", required = false)
    public void setWorkflowStr03(String workflowStr03) {
        this.workflowStr03 = workflowStr03;
    }

    public String getWorkflowStr04() {
        return workflowStr04;
    }

    @ApiModelProperty(name = "workflowStr04", example = " ", dataType = "string", value = "ข้อมูลผังการไหล4", required = false)
    public void setWorkflowStr04(String workflowStr04) {
        this.workflowStr04 = workflowStr04;
    }

    public String getWorkflowDate01() {
        return workflowDate01;
    }

    @ApiModelProperty(name = "workflowDate01", example = "01/05/2560", dataType = "string", value = "วันที่ข้อมูลผังการไหล1", required = false)
    public void setWorkflowDate01(String workflowDate01) {
        this.workflowDate01 = workflowDate01;
    }

    public String getWorkflowDate02() {
        return workflowDate02;
    }

    @ApiModelProperty(name = "workflowDate02", example = "01/05/2560", dataType = "string", value = "วันที่ข้อมูลผังการไหล2", required = false)
    public void setWorkflowDate02(String workflowDate02) {
        this.workflowDate02 = workflowDate02;
    }

    public String getWorkflowDetail() {
        return workflowDetail;
    }

    public void setWorkflowDetail(String workflowDetail) {
        this.workflowDetail = workflowDetail;
    }

}
