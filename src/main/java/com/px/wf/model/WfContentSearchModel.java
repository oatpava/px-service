/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.wf.model;

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
 * @author Oat
 */
@XmlRootElement(name = "WfContent Search")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ค้นหาหนังสือ")
public class WfContentSearchModel extends VersionModel {

    private static final long serialVersionUID = 4820417823365552340L;

    @XmlElement(name = "wfContentFolderId")
    @ApiParam(name = "wfContentFolderId", value = "รหัสแฟ้มสารบรรณ", required = true)
    @Since(1.0)
    @Expose
    private int wfContentFolderId;

    @XmlElement(name = "wfContentContentYear")
    @ApiParam(name = "wfContentContentYear", value = "ปีของเลขทะเบียน", required = true)
    @Since(1.0)
    @Expose
    private Integer wfContentContentYear;

    @XmlElement(name = "wfContentContentNo")
    @ApiParam(name = "wfContentContentNo", value = "เลขทะเบียน", required = true)
    @Size(max = 15)
    @Since(1.0)
    @Expose
    private String wfContentContentNo;

    @XmlElement(name = "wfContentStartContentNo")
    @ApiParam(name = "wfContentStartContentNo", value = "ลำดับเลขทะเบียนจาก", required = true)
    @Since(1.0)
    @Expose
    private int wfContentStartContentNo;

    @XmlElement(name = "wfContentEndContentNo")
    @ApiParam(name = "wfContentEndContentNo", value = "ลำดับเลขทะเบียนถึง", required = true)
    @Since(1.0)
    @Expose
    private int wfContentEndContentNo;

    @XmlElement(name = "wfContentContentStartDate")
    @ApiParam(name = "wfContentContentStartDate", value = "จากวันที่", required = true)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentContentStartDate;

    @XmlElement(name = "wfContentContentEndDate")
    @ApiParam(name = "wfContentContentEndDate", value = "ถึงวันที่", required = true)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentContentEndDate;

    @XmlElement(name = "wfContentBookNo")
    @ApiParam(name = "wfContentBookNo", value = "เลขที่หนังสือ", required = false)
    @Size(max = 50)
    @Since(1.0)
    @Expose
    private String wfContentBookNo;

    @XmlElement(name = "wfContentBookStartDate")
    @ApiParam(name = "wfContentBookStartDate", value = "จากลงวันที่", required = true)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentBookStartDate;

    @XmlElement(name = "wfContentBookEndDate")
    @ApiParam(name = "wfContentBookEndDate", value = "ถึงลงวันที่", required = true)
    @Size(max = 255)
    @Since(1.0)
    @Expose
    private String wfContentBookEndDate;

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

    @XmlElement(name = "status")
    @ApiParam(name = "status", value = "สถานะ", required = false)
    @Since(1.0)
    @Expose
    private int status;

    @XmlElement(name = "userName")
    @ApiParam(name = "userName", value = "ผู้รับผิดชอบ", required = false)
    @Since(1.0)
    @Expose
    private String userName;

    @XmlElement(name = "fileAttachText")
    @ApiParam(name = "fileAttachText", value = "ข้อความในเอกสารแนบ", required = false)
    @Size(max = 1000)
    @Since(1.0)
    @Expose
    private String fileAttachText;

    @XmlElement(name = "wfContentDescription")
    @ApiParam(name = "wfContentDescription", value = "หมายเหตุ", required = false)
    @Size(max = 4000)
    @Since(1.0)
    @Expose
    private String wfContentDescription;

    @XmlElement(name = "wfContentStr03")
    @ApiParam(name = "wfContentStr03", value = "ไปรษณีย์ลงทะเบียน", required = false)
    @Since(1.0)
    @Expose
    private String wfContentStr03;

    public int getWfContentFolderId() {
        return wfContentFolderId;
    }

    @ApiModelProperty(name = "wfContentFolderId", example = "1", dataType = "int", value = "รหัสแฟ้มทะเบียน", required = true)
    public void setWfContentFolderId(int wfContentFolderId) {
        this.wfContentFolderId = wfContentFolderId;
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

    public int getWfContentStartContentNo() {
        return wfContentStartContentNo;
    }

    @ApiModelProperty(name = "wfContentStartContentNo", example = "1", dataType = "string", value = "ลำดับเลขทะเบียนจาก", required = true)
    public void setWfContentStartContentNo(int wfContentStartContentNo) {
        this.wfContentStartContentNo = wfContentStartContentNo;
    }

    public int getWfContentEndContentNo() {
        return wfContentEndContentNo;
    }

    @ApiModelProperty(name = "wfContentEndContentNo", example = "99999", dataType = "string", value = "ลำดับเลขทะเบียนถึง", required = true)
    public void setWfContentEndContentNo(int wfContentEndContentNo) {
        this.wfContentEndContentNo = wfContentEndContentNo;
    }

    public String getWfContentContentStartDate() {
        return wfContentContentStartDate;
    }

    //@ApiModelProperty(name = "wfContentContentEndDate", example = "01/02/2560", dataType = "string", value = "วันที่", required = true)
    public void setWfContentContentStartDate(String wfContentContentStartDate) {
        this.wfContentContentStartDate = wfContentContentStartDate;
    }

    public String getWfContentContentEndDate() {
        return wfContentContentEndDate;
    }

    //@ApiModelProperty(name = "wfContentContentEndDate", example = "01/02/2560", dataType = "string", value = "วันที่", required = true)
    public void setWfContentContentEndDate(String wfContentContentEndDate) {
        this.wfContentContentEndDate = wfContentContentEndDate;
    }

    public String getWfContentBookNo() {
        return wfContentBookNo;
    }

    @ApiModelProperty(name = "wfContentBookNo", example = "00001/2560", dataType = "string", value = "เลขที่หนังสือ", required = true)
    public void setWfContentBookNo(String wfContentBookNo) {
        this.wfContentBookNo = wfContentBookNo;
    }

    public String getWfContentBookStartDate() {
        return wfContentBookStartDate;
    }

    //@ApiModelProperty(name = "wfContentBookDate", example = "02/02/2560", dataType = "string", value = "ลงวันที่", required = true)
    public void setWfContentBookStartDate(String wfContentBookStartDate) {
        this.wfContentBookStartDate = wfContentBookStartDate;
    }

    public String getWfContentBookEndDate() {
        return wfContentBookEndDate;
    }

    //@ApiModelProperty(name = "wfContentBookDate", example = "02/02/2560", dataType = "string", value = "ลงวันที่", required = true)
    public void setWfContentBookEndDate(String wfContentBookEndDate) {
        this.wfContentBookEndDate = wfContentBookEndDate;
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

    public int getStatus() {
        return status;
    }

    @ApiModelProperty(name = "status", example = "1", dataType = "int", value = "สถานะ", required = false)
    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    @ApiModelProperty(name = "userName", example = "oat", dataType = "string", value = "ผู้รับผิดชอบ", required = false)
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileAttachText() {
        return fileAttachText;
    }

    @ApiModelProperty(name = "fileAttachText", example = "xxx", dataType = "string", value = "ข้อความในเอกสารแนบ", required = false)
    public void setFileAttachText(String fileAttachText) {
        this.fileAttachText = fileAttachText;
    }

    public String getWfContentDescription() {
        return wfContentDescription;
    }

    @ApiModelProperty(name = "wfContentDescription", example = "ทดสอบหมายเหตุ", dataType = "string", value = "หมายเหตุ", required = true)
    public void setWfContentDescription(String wfContentDescription) {
        this.wfContentDescription = wfContentDescription;
    }

    public String getWfContentStr03() {
        return wfContentStr03;
    }

    public void setWfContentStr03(String wfContentStr03) {
        this.wfContentStr03 = wfContentStr03;
    }

}
