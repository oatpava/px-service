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
@ApiModel( description = "รายการของสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบซึ่งจัดกลุ่มโดยโครงสร้างและประวัติผู้ใช้งาน" )
@XmlRootElement(name = "SubmoduleUserAuth_groupStructureUser_authority")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubmoduleUserAuthModel_groupStructureUser_authority extends VersionModel  {
    private static final long serialVersionUID = -6067742463070425188L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสรายการของสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบซึ่งจัดกลุ่มโดยโครงสร้างและประวัติผู้ใช้งาน", defaultValue = "0", required = false )
    @Expose
    @Since(1.0)
    private int id;
    
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

    public SubmoduleUserAuthModel_groupStructureUser_authority() {
    }

    public int getId() {
        return id;
    }
    
    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
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
