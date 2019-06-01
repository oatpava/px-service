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
 * @author Peach
 */
@XmlRootElement(name = "Submodule")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ระบบงานย่อย" )
public class SubmoduleModel extends VersionModel {
    private static final long serialVersionUID = 3466383092208251438L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสระบบงานย่อย", defaultValue = "0", required = false )
    @Expose
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "submoduleCode")
    @ApiParam(name = "submoduleCode", example = "รหัสเรียกระบบงานย่อย", value = "รหัสเรียกระบบงานย่อย", defaultValue = "รหัสเรียกระบบงานย่อย", required = true)
    @Size(max = 10)
    @Expose
    @Since(1.0)
    private String submoduleCode;
    
    @XmlElement(name = "submoduleName")
    @ApiParam(name = "submoduleName", example = "ชื่อระบบงานย่อย", value = "ชื่อระบบงานย่อย", defaultValue = "ชื่อระบบงานย่อย", required = true)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String submoduleName;
    
    @XmlElement(name = "module")
    @ApiParam(name = "module", value = "ระบบงาน", required = true)
    @Expose
    @Since(1.0)
    private ModuleModel module;

    public SubmoduleModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสระบบงานย่อย", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getSubmoduleCode() {
        return submoduleCode;
    }

    @ApiModelProperty(name = "submoduleCode", example = "รหัสเรียกระบบงานย่อย", dataType = "string", value = "รหัสเรียกระบบงานย่อย", required = true)
    public void setSubmoduleCode(String submoduleCode) {
        this.submoduleCode = submoduleCode;
    }

    public String getSubmoduleName() {
        return submoduleName;
    }

    @ApiModelProperty(name = "submoduleName", example = "ชื่อระบบงานย่อย", dataType = "string", value = "ชื่อระบบงานย่อย", required = true)
    public void setSubmoduleName(String submoduleName) {
        this.submoduleName = submoduleName;
    }
    
    public ModuleModel getModule() {
        return module;
    }

    @ApiModelProperty(name = "module", value = "ระบบงาน", required = true)
    public void setModule(ModuleModel module) {
        this.module = module;
    }
    
}
