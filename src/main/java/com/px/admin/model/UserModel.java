package com.px.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.admin.entity.UserStatus;
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
 * @author OPAS
 */
@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ผู้ใช้งานระบบ" )
public class UserModel extends VersionModel{
    private static final long serialVersionUID = 4496483719618213639L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสผู้ใช้งานระบบ", defaultValue = "0", required = false )
    @Expose 
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "name")
    @ApiParam(name = "name", example = "ทดสอบชื่อสำหรับเข้าใช้งานระบบ", value = "ชื่อสำหรับเข้าใช้งานระบบ", defaultValue = "ทดสอบชื่อสำหรับเข้าใช้งานระบบ", required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String name;
    
    @XmlElement(name = "passwords")
    @ApiParam(name = "passwords", example = "123456", value = "รหัสผ่านผู้ใช้งานระบบ", defaultValue = "123456", required = true)
    @Size(max = 100)
    private String passwords;
    
    @XmlElement(name = "activeDate")
    @ApiParam(name = "activeDate", example = "01/01/2559 10:00:00", value = "วันที่ผู้ใช้งานสามารถใช้งานได้", defaultValue = "", required = false)
    @Size(min = 10, max = 10)
    @Expose 
    @Since(1.1)
    private String activeDate;
    
    @XmlElement(name = "expireDate")
    @ApiParam(name = "expireDate", example = "31/01/2559 10:00:00", value = "วันที่หมดอายุของผู้ใช้งาน", defaultValue = "", required = false)
    @Size(min = 10, max = 10)
    @Expose 
    @Since(1.1)
    private String expireDate;
    
    @XmlElement(name = "passwordExpireDate")
    @ApiParam(name = "passwordExpireDate", example = "31/01/2559 10:00:00", value = "วันที่หมดอายุของรหัสผ่าน", defaultValue = "", required = false)
    @Size(min = 10, max = 10)
    @Expose 
    @Since(1.1)
    private String passwordExpireDate;
    
    @XmlElement(name = "status")
    @ApiParam(name = "status", value = "สถานะผู้เข้าใช้งานระบบ", required = false)
    @Expose
    @Since(1.1)
    private UserStatusModel status;

    public UserModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "ชื่อสำหรับผู้เข้าใช้งานระบบ", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @ApiModelProperty(name = "name", example = "usertest", dataType = "string", value = "ชื่อสำหรับเข้าใช้งานระบบ (ควรเป็นภาษาอังกฤษ)", required = true)
    public void setName(String name) {
        this.name = name;
    }

    public String getPasswords() {
        return passwords;
    }

    @ApiModelProperty(name = "passwords", example = "123456", dataType = "string", value = "รหัสผ่านผู้ใช้งานระบบ", required = true)
    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getActiveDate() {
        return activeDate;
    }

    @ApiModelProperty(name = "activeDate", example = "01/01/2559 10:00:00", dataType = "string", value = "วันที่ผู้ใช้งานสามารถใช้งานได้", required = false)
    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    @ApiModelProperty(name = "expireDate", example = "31/01/2559 10:00:00", dataType = "string", value = "วันที่หมดอายุของผู้ใช้งาน", required = false)
    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getPasswordExpireDate() {
        return passwordExpireDate;
    }

    @ApiModelProperty(name = "passwordExpireDate", example = "31/01/2559 10:00:00", dataType = "string", value = "วันที่หมดอายุของรหัสผ่าน", required = false)
    public void setPasswordExpireDate(String passwordExpireDate) {
        this.passwordExpireDate = passwordExpireDate;
    }

    public UserStatusModel getStatus() {
        return status;
    }

    @ApiModelProperty(name = "status", value = "สถานะผู้เข้าใช้งานระบบ", required = false)
    public void setStatus(UserStatusModel status) {
        this.status = status;
    }

}
