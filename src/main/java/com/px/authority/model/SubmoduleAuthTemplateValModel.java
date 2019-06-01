package com.px.authority.model;

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
 * @author Peach
 */
@XmlRootElement(name = "SubmoduleAuthTemplateVal")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "สิทธิของรูปแบบการใช้งานระบบงานย่อย" )
public class SubmoduleAuthTemplateValModel extends VersionModel {
    private static final long serialVersionUID = -2153179526914349774L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสสิทธิของรูปแบบการใช้งานระบบงานย่อย", defaultValue = "0", required = false )
    @Expose
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "submoduleAuthTemplate")
    @ApiParam(name = "submoduleAuthTemplate", value = "รูปแบบการใช้งานระบบงานย่อย", required = true)
    @Expose
    @Since(1.0)
    private SubmoduleAuthTemplateModel submoduleAuthTemplate;
    
    @XmlElement(name = "submoduleAuth")
    @ApiParam(name = "submoduleAuth", value = "สิทธิการใช้ระบบงานย่อย", required = true)
    @Expose
    @Since(1.0)
    private SubmoduleAuthModel submoduleAuth;
    
    @XmlElement(name = "linkId")
    @ApiParam(name = "linkId", example = "0", value = "รหัสเชื่อมโยง", required = false)
    @Expose
    @Since(1.0)
    private int linkId;
    
    @XmlElement(name = "authority")
    @ApiParam(name = "authority", example = "1", value = "สิทธิ", defaultValue = "2", required = true)
    @Size(max = 10)
    @Expose
    @Since(1.0)
    private String authority;

    public SubmoduleAuthTemplateValModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสสิทธิของรูปแบบการใช้งานระบบงานย่อย", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public SubmoduleAuthTemplateModel getSubmoduleAuthTemplate() {
        return submoduleAuthTemplate;
    }

    @ApiModelProperty(name = "submoduleAuthTemplate", value = "รูปแบบการใช้งานระบบงานย่อย", required = true)
    public void setSubmoduleAuthTemplate(SubmoduleAuthTemplateModel submoduleAuthTemplate) {
        this.submoduleAuthTemplate = submoduleAuthTemplate;
    }

    public SubmoduleAuthModel getSubmoduleAuth() {
        return submoduleAuth;
    }

    @ApiModelProperty(name = "submoduleAuth", value = "สิทธิการใช้ระบบงานย่อย", required = true)
    public void setSubmoduleAuth(SubmoduleAuthModel submoduleAuth) {
        this.submoduleAuth = submoduleAuth;
    }

    public int getLinkId() {
        return linkId;
    }

    @ApiModelProperty(name = "linkId", example = "0", dataType = "int", value = "รหัสเชื่อมโยง", required = false)
    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public String getAuthority() {
        return authority;
    }

    @ApiModelProperty(name = "authority", example = "1", dataType = "string", value = "สิทธิ", required = true)
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    
}
