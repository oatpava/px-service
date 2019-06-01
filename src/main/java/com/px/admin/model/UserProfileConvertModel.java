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
@XmlRootElement(name = "UserProfile")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "เปรียบเทียบผู้ใช้งานระบบ" )
public class UserProfileConvertModel extends VersionModel{
    
    private static final long serialVersionUID = 4253144368441916477L;
    
    @XmlElement(name = "status")
    @ApiParam(name = "status", example = "1", value = "(1=เพิ่ม,2=ลบ,3=แก้ไข)", defaultValue = "สถานะแต่ละเรคคอร์ด", required = true)
    @Size(max = 3)
    @Expose 
    @Since(1.0)
    private int status;
    
    @XmlElement(name = "userProfile")
    @ApiParam(name = "userProfile", value = "ผู้ใช้งานระบบ", required = false)
    @Expose 
    @Since(1.0)
    private UserProfileModel userProfile;
    
    @XmlElement(name = "vUserProfile")
    @ApiParam(name = "vUserProfile", value = "view ผู้ใช้งานระบบ", required = false)
    @Expose 
    @Since(1.0)
    private VUserProfileModel vUserProfile;

    public UserProfileConvertModel() {
    }

    public int getStatus() {
        return status;
    }

    @ApiModelProperty(name = "status", example = "0", dataType = "int", value = "สถานะ", required = true)
    public void setStatus(int status) {
        this.status = status;
    }

    public UserProfileModel getUserProfile() {
        return userProfile;
    }

    @ApiModelProperty(name = "userProfile", value = "โครงสร้างหน่วยงาน", required = false)
    public void setUserProfile(UserProfileModel userProfile) {
        this.userProfile = userProfile;
    }

    public VUserProfileModel getVUserProfile() {
        return vUserProfile;
    }

    @ApiModelProperty(name = "vUserProfile", value = "view โครงสร้างหน่วยงาน", required = false)
    public void setVUserProfile(VUserProfileModel vUserProfile) {
        this.vUserProfile = vUserProfile;
    }

    
}
