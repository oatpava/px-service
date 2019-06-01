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
@XmlRootElement(name = "UserParam")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "การตั้งค่าระบบผู้ใช้" )
public class UserParamModel extends VersionModel{
    
    private static final long serialVersionUID = -4551330838201212728L;
    
    @XmlElement(name = "id")
    @Expose 
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "user")
    @ApiParam(name = "user", value = "ผู้เข้าใช้งานระบบ", required = true)
    @Expose
    @Since(1.1)
    private UserModel user;
    
    @XmlElement(name = "paramName")
    @ApiParam(name = "paramName",value = "ชื่อการตั้งค่าผู้ใช้",required = true)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String paramName;
    
    @XmlElement(name = "paramValue")
    @ApiParam(name = "paramValue",value = "ค่าการตั้งค่าผู้ใช้",required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String paramValue;

    public UserParamModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสการตั้งค่าระบบผู้ใช้", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    @ApiModelProperty(name = "user", value = "ผู้เข้าใช้งานระบบ", required = true)
    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getParamName() {
        return paramName;
    }

    @ApiModelProperty(name = "paramName", example = "", dataType = "string", value = "ชื่อการตั้งค่าผู้ใช้", required = true)
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    @ApiModelProperty(name = "paramValue", example = "", dataType = "string", value = "ค่าการตั้งค่าผู้ใช้", required = true)
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
    
}
