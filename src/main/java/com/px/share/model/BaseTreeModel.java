package com.px.share.model;

import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.persistence.MappedSuperclass;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Peach
 */
@MappedSuperclass
@ApiModel( description = "BaseTreeModel resource representation" )
@XmlRootElement(name = "BaseTree")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseTreeModel extends VersionModel{
    private static final long serialVersionUID = -8654079749424233997L;
    
    @XmlElement(name = "parentId")
    @ApiParam(name = "parentId",value = "รหัสแม่ของข้อมูล",required = true)
    @FormParam("parentId")
    @Expose private int parentId;
    
    @XmlElement(name = "parentKey")
    @ApiParam(name = "parentKey",value = "กลุ่มรหัสแม่ของข้อมูล",required = true)
    @FormParam("parentKey")
    @Expose private String parentKey;
    
    @XmlElement(name = "nodeLevel")
    @ApiParam(name = "nodeLevel",value = "ลำดับของโครงสร้าง",required = true)
    @FormParam("nodeLevel")
    @Expose private int nodeLevel;
    

    public BaseTreeModel() {
    }

    @ApiModelProperty(name = "parentId",dataType = "int",value = "รหัสแม่ของข้อมูล", required = true,hidden = false )
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @ApiModelProperty(name = "parentKey",dataType = "string",value = "กลุ่มรหัสแม่ของข้อมูล", required = true,hidden = false )
    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    @ApiModelProperty(name = "nodeLevel",dataType = "int",value = "ลำดับของโครงสร้าง", required = true,hidden = false )
    public int getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }
    
    
}
