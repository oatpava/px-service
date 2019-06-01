/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.model;

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
@XmlRootElement(name = "elasticSearch")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ค้นหา")
public class ElasticSearchModel extends VersionModel{
     public static final long serialVersionUID = 0L;
     
    @XmlElement(name = "indexName")
    @ApiParam(name = "indexName", example = "dms", value = "indexName",   required = true)
    @Expose
    @Since(1.0)
    private String indexName;
     
    @XmlElement(name = "indexType")
    @ApiParam(name = "indexType", example = "dms", value = "indexType", required = true)
    @Expose
    @Since(1.0)
    private String indexType;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "1", value = "id",   required = false)
    @Expose
    @Since(1.0)
    private String id;
    
    @XmlElement(name = "field")
    @ApiParam(name = "field", example = "fieldname1,fieldname2,fieldname3", value = "field", defaultValue = "field name",  required = false)
    @Expose
    @Since(1.0)
    private String field;
    
    @XmlElement(name = "fieldData")
    @ApiParam(name = "fieldData", example = "fieldData1,fieldData2,fieldData3", value = "fieldData", defaultValue = "fieldData",  required = false)
    @Expose
    @Since(1.0)
    private String fieldData;
    
    public ElasticSearchModel() {
    }

    public String getIndexName() {
        return indexName;
    }

      @ApiModelProperty(name = "indexName", example = "dms", dataType = "string", value = "module", required = true)
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexType() {
        return indexType;
    }

      @ApiModelProperty(name = "indexType", example = "dms", dataType = "string", value = "sub module", required = true)
    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getId() {
        return id;
    }

      @ApiModelProperty(name = "id", example = "1", dataType = "string", value = "link id", required = false)
    public void setId(String id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

      @ApiModelProperty(name = "field", example = "fieldname1,fieldname2,fieldname3", dataType = "string", value = "field name", required = false)
    public void setField(String field) {
        this.field = field;
    }

    public String getFieldData() {
        return fieldData;
    }

      @ApiModelProperty(name = "fieldData", example = "fieldData1,fieldData2,fieldData3 ", dataType = "string", value = "ชื่อ เอกสาร", required = false)
    public void setFieldData(String fieldData) {
        this.fieldData = fieldData;
    }
    
    
}
