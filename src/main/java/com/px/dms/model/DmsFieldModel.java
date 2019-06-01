/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.model;

//import com.px.dms.model.post.DmsFieldPostModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TOP
 */
@XmlRootElement(name = "DmsField")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "DmsField")
public class DmsFieldModel extends VersionModel {

    public static final long serialVersionUID = 0L;

    @XmlElement(name = "id")
    @Since(1.0)
    @Expose
    private int id;

    @XmlElement(name = "fieldName")
    @ApiParam(name = "fieldName", value = "fieldName", example = "ชื่อเรื่อง",required = true)
    @Since(1.0)
    @Expose
    private String fieldName;

    @XmlElement(name = "fieldType")
    @ApiParam(name = "fieldType", value = "fieldType", example = "VARCHAR",required = true)
    @Since(1.0)
    @Expose
    private String fieldType;

    @XmlElement(name = "fieldMap")
    @ApiParam(name = "fieldMap", value = "fieldMap", example = "dmsDocumentName", required = true)
    @Since(1.0)
    @Expose
    private String fieldMap;

   

    public DmsFieldModel() {
    }

    
    public int getId() {
        return id;
    }

     @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัส", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

     @ApiModelProperty(name = "fieldName", example = "ชื่อเรื่อง", dataType = "string", value = "fieldName", required = true)
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

     @ApiModelProperty(name = "fieldType", example = "VARCHAR", dataType = "string", value = "fieldType", required = true)
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldMap() {
        return fieldMap;
    }

     @ApiModelProperty(name = "fieldMap", example = "dmsDocumentName", dataType = "string", value = "fieldMap", required = true)
    public void setFieldMap(String fieldMap) {
        this.fieldMap = fieldMap;
    }

 
    
    

}
