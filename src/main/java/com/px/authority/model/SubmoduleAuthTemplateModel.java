package com.px.authority.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.admin.model.SubmoduleModel;
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
@XmlRootElement(name = "SubmoduleAuthTemplate")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "รูปแบบการใช้งานระบบงานย่อย" )
public class SubmoduleAuthTemplateModel extends VersionModel{
    private static final long serialVersionUID = 4996800495977124776L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสรูปแบบการใช้งานระบบงานย่อย", defaultValue = "0", required = false )
    @Expose
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "submoduleAuthTemplateName")
    @ApiParam(name = "submoduleAuthTemplateName", example = "ชื่อรูปแบบการใช้งานระบบงานย่อย", value = "ชื่อรูปแบบการใช้งานระบบงานย่อย", defaultValue = "ชื่อรูปแบบการใช้งานระบบงานย่อย", required = true)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String submoduleAuthTemplateName;
    
    @XmlElement(name = "submodule")
    @ApiParam(name = "submodule", value = "ระบบงานย่อย", required = true)
    @Expose
    @Since(1.0)
    private SubmoduleModel submodule;

    public SubmoduleAuthTemplateModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสรูปแบบการใช้งานระบบงานย่อย", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }
    
    public String getSubmoduleAuthTemplateName() {
        return submoduleAuthTemplateName;
    }

    @ApiModelProperty(name = "submoduleAuthTemplateName", example = "ชื่อรูปแบบการใช้งานระบบงานย่อย", dataType = "string", value = "ชื่อรูปแบบการใช้งานระบบงานย่อย", required = true)
    public void setSubmoduleAuthTemplateName(String submoduleAuthTemplateName) {
        this.submoduleAuthTemplateName = submoduleAuthTemplateName;
    }

    public SubmoduleModel getSubmodule() {
        return submodule;
    }

    @ApiModelProperty(name = "submodule", value = "ระบบงานย่อย", required = true)
    public void setSubmodule(SubmoduleModel submodule) {
        this.submodule = submodule;
    }
    
}
