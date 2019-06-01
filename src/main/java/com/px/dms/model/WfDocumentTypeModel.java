package com.px.dms.model;

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
 * @author CHALERMPOL
 */
@XmlRootElement(name = "WfDocumentType")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ประเภทเอกสาร" )
public class WfDocumentTypeModel extends VersionModel{

    private static final long serialVersionUID = -1247502951915983259L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสตัวเลือก", defaultValue = "0", required = false )
    @Expose
    @Since(1.0)
    private int id;    
    
    @XmlElement(name = "name")
    @ApiParam(name = "name", example = "ทดสอบชื่อประเภทเอกสาร", value = "ชื่อประเภทเอกสาร", defaultValue = "ทดสอบชื่อประเภทเอกสาร", required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String name;
    
    
    @XmlElement(name = "detail")
    @ApiParam(name = "detail", example = "ทดสอบรายละเอียดประเภทเอกสาร", value = "รายละเอียดประเภทเอกสาร", defaultValue = "ทดสอบรายละเอียดประเภทเอกสาร", required = false)
    @Size(max = 1000)
    @Expose 
    @Since(1.0)
    private String detail;
    
    
    @XmlElement(name = "code")
    @ApiParam(name = "code", example = "000000", value = "รหัสอ้างอิงของประเภทเอกสาร", defaultValue = "000000", required = false)
    @Size(max = 15)
    @Expose 
    @Since(1.0)
    private String code;
    
    @XmlElement(name = "nodeLevel")
    @ApiParam(name = "nodeLevel", example = "0", value = "NodeLevel", defaultValue = "0", required = true)
    @Size(max = 15)
    @Expose 
    @Since(1.0)
    private int nodeLevel;

    @XmlElement(name = "isFolder")
    @ApiParam(name = "isFolder", example = "0", value = "isFolder", defaultValue = "0", required = false)
    @Size(max = 15)
    @Expose 
    @Since(1.0)
    private int isFolder;
    
    @XmlElement(name = "parentId")
    @ApiParam(name = "parentId", example = "0", value = "ParentId", defaultValue = "0", required = true)
    @Expose 
    @Since(1.0)
    private int parentId;
    
    @XmlElement(name = "parentKey")
    @ApiParam(name = "parentKey", example = "฿0฿", value = "ParentKey", defaultValue = "฿0฿", required = true)
    @Size(max = 15)
    @Expose 
    @Since(1.0)
    private String parentKey;

    public WfDocumentTypeModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสตัวเลือก", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @ApiModelProperty(name = "name", example = "ทดสอบชื่อโครงสร้าง", dataType = "string", value = "ชื่อโครงสร้าง", required = true)
    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    @ApiModelProperty(name = "detail", example = "ทดสอบรายละเอียดโครงสร้าง", dataType = "string", value = "รายละเอียดโครงสร้าง", required = false)
    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    @ApiModelProperty(name = "code", example = "111AAAbbb222ddd", dataType = "string", value = "รหัสอ้างอิงของโครงสร้าง", required = false)
    public void setCode(String code) {
        this.code = code;
    }

    public int getNodeLevel() {
        return nodeLevel;
    }

    @ApiModelProperty(name = "nodeLevel", example = "0", dataType = "int", value = "NodeLevel", required = true)
    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public int getParentId() {
        return parentId;
    }

    @ApiModelProperty(name = "parentId", example = "0", dataType = "int", value = "ParentId", required = true)
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParentKey() {
        return parentKey;
    }

    @ApiModelProperty(name = "parentKey", example = "฿0฿", dataType = "string", value = "ParentKey", required = true)
    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    } 

    public int getIsFolder() {
        return isFolder;
    }

    @ApiModelProperty(name = "isFolder", example = "0", dataType = "int", value = "isFolder", required = false)
    public void setIsFolder(int isFolder) {
        this.isFolder = isFolder;
    }
    
    
}
