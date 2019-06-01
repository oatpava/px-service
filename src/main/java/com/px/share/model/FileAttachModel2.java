/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author top
 */
@XmlRootElement(name = "FileAttach2")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ไฟล์เอกสารแนบ2")
public class FileAttachModel2 extends FileAttachModel {

    private static final long serialVersionUID = 0L;

    public FileAttachModel2() {
    }

    @XmlElement(name = "createdDate")
    @ApiParam(name = "createdDate", example = "01/01/2559 07:30:45", value = "วันที่สร้าง", defaultValue = "01/01/2559 07:30:45", hidden = true, required = false)
    @Since(1.0)
    @Expose
    private String createdDate;

    @XmlElement(name = "createdName")
    @ApiParam(name = "createdName", value = "createdName", required = false)
    @Since(1.0)
    @Expose
    private String createdName;
    
    //oat-add
    @XmlElement(name = "createdBy")
    @ApiParam(name = "createdBy", value = "createdBy", required = false)
    @Since(1.0)
    @Expose
    private int createdBy;

    public String getCreatedDate() {
        return createdDate;
    }

     @ApiModelProperty(name = "createdDate", example = "01/01/2559 07:30:45", dataType = "string", value = "วันที่สร้าง", hidden = true, required = false)
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedName() {
        return createdName;
    }

     @ApiModelProperty(name = "createdName", example = "ชื่อทดสอบ", dataType = "string", value = "ชื่อทดสอบ", hidden = true, required = false)
    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }
    
    public int getCreatedBy() {
        return createdBy;
    }

    @ApiModelProperty(name = "createdBy", example = "1", dataType = "int", value = "รหัสผู้อัพ", hidden = true, required = false)
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

}
