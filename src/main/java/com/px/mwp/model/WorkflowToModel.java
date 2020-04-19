package com.px.mwp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.admin.model.StructureModel;
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
@XmlRootElement(name = "WorkflowTo")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "การส่งข้อมูล")
public class WorkflowToModel extends VersionModel {

    private static final long serialVersionUID = -2443067835898627821L;

    @XmlElement(name = "id")
    @Expose
    @Since(1.0)
    private int id;
    @XmlElement(name = "workflow")
    @Expose
    @Since(1.0)
    private WorkflowModel workflow;
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
//    @Expose @Since(1.0) private int workflowToReceiveFlag;
//    @Expose @Since(1.0) private int workflowToOpenFlag;
//    @Expose @Since(1.0) private int workflowToActionFlag;
//    @Expose @Since(1.0) private int workflowToFinishFlag;

    @XmlElement(name = "workflowId")
    @ApiParam(name = "workflowId", value = "รหัสผังการทำงาน", required = true)
    private int workflowId;

    @XmlElement(name = "userProfileId")
    @ApiParam(name = "userProfileId", value = "รหัสผู้รับข้อมูล", required = true)
    @Since(1.0)
    @Expose
    private int userProfileId;

    @XmlElement(name = "structureId")
    @ApiParam(name = "structureId", value = "รหัสหน่วยงานรับข้อมูล", required = true)
    @Since(1.0)
    @Expose
    private int structureId;

    @XmlElement(name = "userProfileFullName")
    @ApiParam(name = "userProfileFullName", value = "ชื่อผู้รับข้อมูล", required = true)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String userProfileFullName;

    @XmlElement(name = "userProfilePosition")
    @ApiParam(name = "userProfilePosition", value = "ตำแหน่งผู้รับข้อมูล", required = true)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String userProfilePosition;

    @XmlElement(name = "workflowToReceiveFlag")
    @ApiParam(name = "workflowToReceiveFlag", value = "", required = true)
    @Since(1.0)
    @Expose
    private int workflowToReceiveFlag;

    @XmlElement(name = "workflowToOpenFlag")
    @ApiParam(name = "workflowToOpenFlag", value = "", required = true)
    @Since(1.0)
    @Expose
    private int workflowToOpenFlag;

    @XmlElement(name = "workflowToActionFlag")
    @ApiParam(name = "workflowToActionFlag", value = "", required = true)
    @Since(1.0)
    @Expose
    private int workflowToActionFlag;

    @XmlElement(name = "workflowToFinishFlag")
    @ApiParam(name = "workflowToFinishFlag", value = "", required = true)
    @Since(1.0)
    @Expose
    private int workflowToFinishFlag;

    @XmlElement(name = "workflowToReceiveDate")
    @ApiParam(name = "workflowToReceiveDate", value = "วันที่ได้รับ", required = true)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String workflowToReceiveDate;

    @XmlElement(name = "workflowToOpenDate")
    @ApiParam(name = "workflowToOpenDate", value = "วันที่เปิด", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String workflowToOpenDate;

    @XmlElement(name = "workflowToActionDate")
    @ApiParam(name = "workflowToActionDate", value = "วันที่กระทำ", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String workflowToActionDate;

    @XmlElement(name = "workflowToFinishDate")
    @ApiParam(name = "workflowToFinishDate", value = "วันที่เสร็จ", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String workflowToFinishDate;

    @XmlElement(name = "structure")
    @Expose
    private StructureModel structure;

    public WorkflowToModel() {
    }

    public WorkflowToModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(position = 1, name = "id", value = "รหัสการส่งข้อมูล", required = true)
    public void setId(int id) {
        this.id = id;
    }

    public WorkflowModel getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkflowModel workflow) {
        this.workflow = workflow;
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

    public int getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(int workflowId) {
        this.workflowId = workflowId;
    }

    public int getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(int userProfileId) {
        this.userProfileId = userProfileId;
    }

    public int getStructureId() {
        return structureId;
    }

    public void setStructureId(int structureId) {
        this.structureId = structureId;
    }

    public String getUserProfileFullName() {
        return userProfileFullName;
    }

    public void setUserProfileFullName(String userProfileFullName) {
        this.userProfileFullName = userProfileFullName;
    }

    public String getUserProfilePosition() {
        return userProfilePosition;
    }

    public void setUserProfilePosition(String userProfilePosition) {
        this.userProfilePosition = userProfilePosition;
    }

    public int getWorkflowToReceiveFlag() {
        return workflowToReceiveFlag;
    }

    public void setWorkflowToReceiveFlag(int workflowToReceiveFlag) {
        this.workflowToReceiveFlag = workflowToReceiveFlag;
    }

    public int getWorkflowToOpenFlag() {
        return workflowToOpenFlag;
    }

    public void setWorkflowToOpenFlag(int workflowToOpenFlag) {
        this.workflowToOpenFlag = workflowToOpenFlag;
    }

    public int getWorkflowToActionFlag() {
        return workflowToActionFlag;
    }

    public void setWorkflowToActionFlag(int workflowToActionFlag) {
        this.workflowToActionFlag = workflowToActionFlag;
    }

    public int getWorkflowToFinishFlag() {
        return workflowToFinishFlag;
    }

    public void setWorkflowToFinishFlag(int workflowToFinishFlag) {
        this.workflowToFinishFlag = workflowToFinishFlag;
    }

    public String getWorkflowToReceiveDate() {
        return workflowToReceiveDate;
    }

    public void setWorkflowToReceiveDate(String workflowToReceiveDate) {
        this.workflowToReceiveDate = workflowToReceiveDate;
    }

    public String getWorkflowToOpenDate() {
        return workflowToOpenDate;
    }

    public void setWorkflowToOpenDate(String workflowToOpenDate) {
        this.workflowToOpenDate = workflowToOpenDate;
    }

    public String getWorkflowToActionDate() {
        return workflowToActionDate;
    }

    public void setWorkflowToActionDate(String workflowToActionDate) {
        this.workflowToActionDate = workflowToActionDate;
    }

    public String getWorkflowToFinishDate() {
        return workflowToFinishDate;
    }

    public void setWorkflowToFinishDate(String workflowToFinishDate) {
        this.workflowToFinishDate = workflowToFinishDate;
    }

    public StructureModel getStructure() {
        return structure;
    }

    public void setStructure(StructureModel structure) {
        this.structure = structure;
    }

}
