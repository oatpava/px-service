package com.px.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
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
@XmlRootElement(name = "UserProfileType")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ประเภทผู้ใช้งานระบบ" )
public class UserProfileTypeModel extends VersionModel{
    
    private static final long serialVersionUID = -8086426615547205615L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสประเภทผู้ใช้งานระบบ", defaultValue = "0", required = false )
    @Expose 
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "name")
    @ApiParam(name = "name", example = "ทดสอบชื่อประเภทผู้ใช้งานระบบ", value = "ชื่อประเภทผู้ใช้งานระบบ", required = true)
    @Size(max = 100)
    @Expose 
    @Since(1.0)
    private String name;

    public UserProfileTypeModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
}
