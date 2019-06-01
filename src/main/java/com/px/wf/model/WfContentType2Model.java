package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "WfContentType2")
@ApiModel(description = "ประเภทย่อยของประเภทสารบรรณ")
public class WfContentType2Model extends VersionModel {

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

    @XmlElement(name = "wfContentType2Name")
    @ApiParam(name = "wfContentType2Name", value = "ชื่อประเภทย่อยของสารบรรณ", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentType2Name;

    @XmlElement(name = "contentType2Date")
    @ApiParam(name = "contentType2Date", value = "วันที่", required = false)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String contentType2Date;//oat-add

    public WfContentType2Model() {
    }

    public WfContentType2Model(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(position = 1, name = "id", value = "รหัสประเภทย่อยของสารบรรณ", required = true)
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

    public String getWfContentType2Name() {
        return wfContentType2Name;
    }

    public void setWfContentType2Name(String wfContentType2Name) {
        this.wfContentType2Name = wfContentType2Name;
    }

    public String getContentType2Date() {
        return contentType2Date;
    }

    @ApiModelProperty(name = "contentType2Date", example = "01/02/2560", dataType = "string", value = "วันที่", required = false)
    public void setContentType2Date(String contentType2Date) {
        this.contentType2Date = contentType2Date;
    }
}
