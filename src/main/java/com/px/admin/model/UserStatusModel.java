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
 * @author OPAS
 */
@XmlRootElement(name = "UserStatus")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "สถานะผู้ใช้งานระบบ" )
public class UserStatusModel extends VersionModel{
    private static final long serialVersionUID = 5653001293370879278L;

    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสสถานะผู้ใช้งานระบบ", defaultValue = "0", required = false )
    @Expose
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "name")
    @ApiParam(name = "name",value = "ชื่อสถานะผู้ใช้งานระบบ",required = true)
    @Size(max = 100)
    @Expose 
    @Since(1.0)
    private String name;

    public UserStatusModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสสถานะผู้ใช้งานระบบ", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @ApiModelProperty(name = "name", example = "ทดสอบสถานะผู้ใช้งานระบบ", dataType = "string", value = "ชื่อสถานะผู้ใช้งานระบบ", required = true)
    public void setName(String name) {
        this.name = name;
    }

    
}