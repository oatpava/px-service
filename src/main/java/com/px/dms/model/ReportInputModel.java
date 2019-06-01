/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author top
 */
@XmlRootElement(name = "ReportInput")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ReportInput")
public class ReportInputModel {

    public static final long serialVersionUID = 0L;

    @XmlElement(name = "reportOutput")
    @Since(1.0)
    @Expose
    private String reportOutput;

    @XmlElement(name = "folderId")
    @Since(1.0)
    @Expose
    private int folderId;

    @XmlElement(name = "mode")
    @Since(1.0)
    @Expose
    private int mode;

    @XmlElement(name = "docTypeDetialId")
    @Since(1.0)
    @Expose
    private String docTypeDetialId;

    public ReportInputModel() {
    }

    public String getReportOutput() {
        return reportOutput;
    }

    @ApiModelProperty(name = "reportOutput", example = "PDF", dataType = "String", value = "ประเภทไฟล์ของรายงาน XLS , PDF", required = true)
    public void setReportOutput(String reportOutput) {
        this.reportOutput = reportOutput;
    }

    public int getFolderId() {
        return folderId;
    }

    @ApiModelProperty(name = "folderId", example = "1", dataType = "int", value = "folderId", required = true)
    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public int getMode() {
        return mode;
    }

    @ApiModelProperty(name = "mode", example = "1", dataType = "int", value = "ประเภทของรายงาน 1 เอกสารปกติ ,2 เอกสารหมดอายุ ,3 เอกสารค้นหา", required = true)
    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getDocTypeDetialId() {
        return docTypeDetialId;
    }

    @ApiModelProperty(name = "docTypeDetialId", example = "1-2-3-4-5", dataType = "String", value = "id doctypeDetail = 1-2-3-4-5", required = true)
    public void setDocTypeDetialId(String docTypeDetialId) {
        this.docTypeDetialId = docTypeDetialId;
    }

}
