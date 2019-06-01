/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.model;

//import com.px.dms.model.post.DocumentTypePostModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.MasterModel;
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
@XmlRootElement(name = "DocumentType")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ประเภทเอกสาร" )
public class DocumentTypeModel extends MasterModel{
    public static final long serialVersionUID = 0L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัส", defaultValue = "0", required = false )
    @Since(1.0)
    @Expose
    private int id;
    
  
     @XmlElement(name = "documentTypeName")
    @ApiParam(name = "documentTypeName",value = "ชื่อประเภทเอกสาร",required = true)
    @Since(1.0)
    @Expose
    private String documentTypeName;
    
    @XmlElement(name = "documentTypeDescription")
    @ApiParam(name = "documentTypeDescription",value = "คำอธิบาย",required = true)
    @Since(1.0)
    @Expose
    private String documentTypeDescription;
    

    public DocumentTypeModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัส", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

     @ApiModelProperty(name = "documentTypeName", example = "ทั่วไป", dataType = "string", value = "ชื่อประเภทเอกสาร", required = true)
    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public String getDocumentTypeDescription() {
        return documentTypeDescription;
    }

     @ApiModelProperty(name = "documentTypeDescription", example = "รายละเอียดเอกสารทั่วไป", dataType = "string", value = "รายละเอียดประเภทเอกสาร", required = false)
    public void setDocumentTypeDescription(String documentTypeDescription) {
        this.documentTypeDescription = documentTypeDescription;
    }


    
    

    
    

   
}
