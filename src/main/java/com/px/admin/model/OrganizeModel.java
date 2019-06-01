package com.px.admin.model;

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
 * @author PRAXiS
 */
@XmlRootElement(name = "Organize")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "โครงสร้างหน่วยงานภายนอก" )
public class OrganizeModel extends VersionModel{
    
    private static final long serialVersionUID = -4803844156551345810L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสโครงสร้างหน่วยงานภายนอก", defaultValue = "0", required = false )
    @Expose
    @Since(1.0)
    private int id;    
    
    @XmlElement(name = "name")
    @ApiParam(name = "name", example = "ทดสอบชื่อโครงสร้าง", value = "ชื่อโครงสร้าง", defaultValue = "ทดสอบชื่อโครงสร้าง", required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String name;
    
    @XmlElement(name = "shortName")
    @ApiParam(name = "shortName", example = "ทดสอบชื่อย่อ", value = "ชื่อย่อโครงสร้าง", defaultValue = "ทดสอบชื่อย่อ", required = false)
    @Size(max = 50)
    @Expose
    @Since(1.0)
    private String shortName;
    
    @XmlElement(name = "detail")
    @ApiParam(name = "detail", example = "ทดสอบรายละเอียดโครงสร้าง", value = "รายละเอียดโครงสร้าง", defaultValue = "ทดสอบรายละเอียดโครงสร้าง", required = false)
    @Size(max = 1000)
    @Expose 
    @Since(1.0)
    private String detail;
    
    
    @XmlElement(name = "code")
    @ApiParam(name = "code", example = "111AAAbbb222ddd", value = "รหัสอ้างอิงของโครงสร้าง", defaultValue = "111AAAbbb222ddd", required = false)
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

    public OrganizeModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสโครงสร้างหน่วยงานภายนอก", readOnly = true, required = false)
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

    public String getShortName() {
        return shortName;
    }

    @ApiModelProperty(name = "shortName", example = "ทดสอบชื่อย่อ", dataType = "string", value = "ชื่อย่อ", required = false)
    public void setShortName(String shortName) {
        this.shortName = shortName;
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
    
}
