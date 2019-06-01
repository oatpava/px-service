package com.px.authority.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.admin.model.StructureModel;
import com.px.admin.model.UserProfileModel;
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
@XmlRootElement(name = "SubmoduleUserAuth")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "สิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ" )
public class SubmoduleUserAuthModel extends VersionModel {
    private static final long serialVersionUID = 4753823954114225609L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ", defaultValue = "0", required = false )
    @Expose
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "structure")
    @ApiParam(name = "structure", value = "โครงสร้าง", required = false)
    @Expose
    @Since(1.0)
    private StructureModel structure;
    
    @XmlElement(name = "userProfile")
    @ApiParam(name = "userProfile", value = "ประวัติผู้ใช้งานระบบ", required = false)
    @Expose
    @Since(1.0)
    private UserProfileModel userProfile;
    
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
    

    public SubmoduleUserAuthModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public StructureModel getStructure() {
        return structure;
    }

    @ApiModelProperty(name = "structure", value = "โครงสร้าง", required = false)
    public void setStructure(StructureModel structure) {
        this.structure = structure;
    }

    public UserProfileModel getUserProfile() {
        return userProfile;
    }

    @ApiModelProperty(name = "userProfile", value = "ประวัติผู้ใช้งานระบบ", required = false)
    public void setUserProfile(UserProfileModel userProfile) {
        this.userProfile = userProfile;
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
