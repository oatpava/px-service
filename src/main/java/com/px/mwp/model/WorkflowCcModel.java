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
@XmlRootElement(name = "WorkflowCc")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "การส่งสำเนาข้อมูล")
public class WorkflowCcModel extends VersionModel {

    private static final long serialVersionUID = -2443067835898627821L;

    @XmlElement(name = "id")
    @Expose @Since(1.0) private int id;
    @XmlElement(name = "workflow")
    @Expose @Since(1.0) private WorkflowModel workflow;
    @Expose @Since(1.0) private int createdBy;
    @Expose @Since(1.0) private String createdDate;
    @Expose @Since(1.0) private float orderNo;
    @Expose @Since(1.0) private int removedBy;
    @Expose @Since(1.0) private String removedDate;
    @Expose @Since(1.0) private int updatedBy;
    @Expose @Since(1.0) private String updatedDate;
    @Expose @Since(1.0) private int workflowCcReceiveFlag;
    @Expose @Since(1.0) private int workflowCcOpenFlag;
    @Expose @Since(1.0) private int workflowCcActionFlag;
    @Expose @Since(1.0) private int workflowCcFinishFlag;
    
    
    @XmlElement(name = "workflowId")
    @ApiParam(name = "workflowId", value = "รหัสผังการทำงาน", required = true)
    private int workflowId;

    @XmlElement(name = "userProfileId")
    @ApiParam(name = "userProfileId", value = "รหัสผู้รับสำเนาข้อมูล", required = true)
    @Since(1.0)
    @Expose private int userProfileId;
    
    @XmlElement(name = "structureId")
    @ApiParam(name = "structureId", value = "รหัสหน่วยงานรับสำเนาข้อมูล", required = true)
    @Since(1.0)
    @Expose private int structureId;

    @XmlElement(name = "userProfileFullName")
    @ApiParam(name = "userProfileFullName", value = "ชื่อผู้รับสำเนาข้อมูล", required = true)
    @Size(max = 255)
    @Since(1.0)
    @Expose private String userProfileFullName;

    @XmlElement(name = "userProfilePosition")
    @ApiParam(name = "userProfilePosition", value = "ตำแหน่งผู้รับสำเนาข้อมูล", required = true)
    @Size(max = 255)
    @Since(1.0)
    @Expose private String userProfilePosition;
    
    @XmlElement(name = "workflowCcReceiveDate")
    @ApiParam(name = "workflowCcReceiveDate", value = "วันที่ได้รับ", required = true)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String workflowCcReceiveDate;

    @XmlElement(name = "workflowCcOpenDate")
    @ApiParam(name = "workflowCcOpenDate", value = "วันที่เปิด", required = false)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String workflowCcOpenDate;
    
    @XmlElement(name = "workflowCcActionDate")
    @ApiParam(name = "workflowCcActionDate", value = "วันที่กระทำ", required = false)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String workflowCcActionDate;

    @XmlElement(name = "workflowCcFinishDate")
    @ApiParam(name = "workflowCcFinishDate", value = "วันที่เสร็จ", required = false)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String workflowCcFinishDate;

    public WorkflowCcModel() {
    }

    public WorkflowCcModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(position = 1, name = "id", value = "รหัสการส่งสำเนาข้อมูล", required = true)
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

    public int getWorkflowCcReceiveFlag() {
        return workflowCcReceiveFlag;
    }

    public void setWorkflowCcReceiveFlag(int workflowCcReceiveFlag) {
        this.workflowCcReceiveFlag = workflowCcReceiveFlag;
    }

    public int getWorkflowCcOpenFlag() {
        return workflowCcOpenFlag;
    }

    public void setWorkflowCcOpenFlag(int workflowCcOpenFlag) {
        this.workflowCcOpenFlag = workflowCcOpenFlag;
    }

    public int getWorkflowCcActionFlag() {
        return workflowCcActionFlag;
    }

    public void setWorkflowCcActionFlag(int workflowCcActionFlag) {
        this.workflowCcActionFlag = workflowCcActionFlag;
    }

    public int getWorkflowCcFinishFlag() {
        return workflowCcFinishFlag;
    }

    public void setWorkflowCcFinishFlag(int workflowCcFinishFlag) {
        this.workflowCcFinishFlag = workflowCcFinishFlag;
    }

    public String getWorkflowCcReceiveDate() {
        return workflowCcReceiveDate;
    }

    public void setWorkflowCcReceiveDate(String workflowCcReceiveDate) {
        this.workflowCcReceiveDate = workflowCcReceiveDate;
    }

    public String getWorkflowCcOpenDate() {
        return workflowCcOpenDate;
    }

    public void setWorkflowCcOpenDate(String workflowCcOpenDate) {
        this.workflowCcOpenDate = workflowCcOpenDate;
    }

    public String getWorkflowCcActionDate() {
        return workflowCcActionDate;
    }

    public void setWorkflowCcActionDate(String workflowCcActionDate) {
        this.workflowCcActionDate = workflowCcActionDate;
    }

    public String getWorkflowCcFinishDate() {
        return workflowCcFinishDate;
    }

    public void setWorkflowCcFinishDate(String workflowCcFinishDate) {
        this.workflowCcFinishDate = workflowCcFinishDate;
    }
   
}
