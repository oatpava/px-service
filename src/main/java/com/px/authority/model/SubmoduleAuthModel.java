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
@XmlRootElement(name = "SubmoduleAuth")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "สิทธิการใช้ระบบงานย่อย" )
public class SubmoduleAuthModel extends VersionModel{
    private static final long serialVersionUID = -5408782722940335575L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสสิทธิการใช้ระบบงานย่อย", defaultValue = "0", required = false )
    @Expose
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "submoduleAuthCode")
    @ApiParam(name = "submoduleAuthCode", example = "รหัสเรียกสิทธิการใช้ระบบงานย่อย", value = "รหัสเรียกสิทธิการใช้ระบบงานย่อย", defaultValue = "รหัสเรียกสิทธิการใช้ระบบงานย่อย", required = true)
    @Size(max = 10)
    @Expose
    @Since(1.0)
    private String submoduleAuthCode;
    
    @XmlElement(name = "submodule")
    @ApiParam(name = "submodule", value = "ระบบงานย่อย", required = true)
    @Expose
    @Since(1.0)
    private SubmoduleModel submodule;
    
    @XmlElement(name = "auth")
    @ApiParam(name = "auth", value = "สิทธิ", required = true)
    @Expose
    @Since(1.0)
    private AuthModel auth;

    public SubmoduleAuthModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสสิทธิการใช้ระบบงานย่อย", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }
    
    public String getSubmoduleAuthCode() {
        return submoduleAuthCode;
    }

    @ApiModelProperty(name = "submoduleAuthCode", example = "รหัสเรียกสิทธิการใช้ระบบงานย่อย", dataType = "string", value = "รหัสเรียกสิทธิการใช้ระบบงานย่อย", required = true)
    public void setSubmoduleAuthCode(String submoduleAuthCode) {
        this.submoduleAuthCode = submoduleAuthCode;
    }

    public SubmoduleModel getSubmodule() {
        return submodule;
    }

    @ApiModelProperty(name = "submodule", value = "ระบบงานย่อย", required = true)
    public void setSubmodule(SubmoduleModel submodule) {
        this.submodule = submodule;
    }

    public AuthModel getAuth() {
        return auth;
    }

    @ApiModelProperty(name = "auth", value = "สิทธิ", required = true)
    public void setAuth(AuthModel auth) {
        this.auth = auth;
    }
    
}
