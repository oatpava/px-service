package com.px.authority.model;

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
 * @author peach
 */
@ApiModel( description = "รายการที่มีสิทธิใช้งาน และ รายการที่ไม่มีสิทธิใช้งาน" )
@XmlRootElement(name = "AuthEnableDisableIdList")
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthEnableDisableIdListModel extends VersionModel {
    private static final long serialVersionUID = -5733362822767160441L;
    
    @XmlElement(name = "enableList")
    @ApiParam(name = "enableList", example = "รายการที่มีสิทธิใช้งาน", value = "รายการที่มีสิทธิใช้งาน", defaultValue = "รายการที่มีสิทธิใช้งาน", required = false)
    @Expose
    @Since(1.0)
    private String enableList;
    
    @XmlElement(name = "disableList")
    @ApiParam(name = "disableList", example = "รายการที่ไม่มีสิทธิใช้งาน", value = "รายการที่ไม่มีสิทธิใช้งาน", defaultValue = "รายการที่ไม่มีสิทธิใช้งาน", required = false)
    @Expose
    @Since(1.0)
    private String disableList;

    public AuthEnableDisableIdListModel() {
    }

    public String getEnableList() {
        return enableList;
    }

    @ApiModelProperty(name = "enableList", example = "รายการที่มีสิทธิใช้งาน", dataType = "string", value = "รายการที่มีสิทธิใช้งาน", required = false)
    public void setEnableList(String enableList) {
        this.enableList = enableList;
    }

    public String getDisableList() {
        return disableList;
    }

    @ApiModelProperty(name = "disableList", example = "รายการที่ไม่มีสิทธิใช้งาน", dataType = "string", value = "รายการที่ไม่มีสิทธิใช้งาน", required = false)
    public void setDisableList(String disableList) {
        this.disableList = disableList;
    }
    
    
}
