package com.px.authority.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.admin.model.StructureModel;
import com.px.admin.model.UserProfileModel;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Peach
 */
@ApiModel( description = "กลุ่มของสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบซึ่งจัดกลุ่มโดยโครงสร้างและประวัติผู้ใช้งาน" )
@XmlRootElement(name = "SubmoduleUserAuth_groupStructureUser")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubmoduleUserAuthModel_groupStructureUser extends VersionModel {
    private static final long serialVersionUID = 5636543152228577068L;
    
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
    
    @XmlElement(name = "authority")
    @Expose
    @Since(1.0)
    private List<SubmoduleUserAuthModel_groupStructureUser_authority> authority;

    public SubmoduleUserAuthModel_groupStructureUser() {
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

    public List<SubmoduleUserAuthModel_groupStructureUser_authority> getAuthority() {
        return authority;
    }

    @ApiModelProperty(name = "authority", value = "สิทธิการใช้งาน", required = false)
    public void setAuthority(List<SubmoduleUserAuthModel_groupStructureUser_authority> authority) {
        this.authority = authority;
    }
    
    
}
