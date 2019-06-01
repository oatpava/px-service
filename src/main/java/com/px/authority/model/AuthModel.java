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
@XmlRootElement(name = "Auth")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "สิทธิ" )
public class AuthModel extends VersionModel{
    private static final long serialVersionUID = -6816579319770524879L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสสิทธิ", defaultValue = "0", required = false )
    @Expose
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "authName")
    @ApiParam(name = "authName", example = "ชื่อสิทธิ", value = "ชื่อสิทธิ", defaultValue = "ชื่อสิทธิ", required = true)
    @Size(max = 255)
    @Expose
    @Since(1.0)
    private String authName;
    

    public AuthModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสสิทธิ", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getAuthName() {
        return authName;
    }

    @ApiModelProperty(name = "authName", example = "ชื่อสิทธิ", dataType = "string", value = "ชื่อสิทธิ", required = true)
    public void setAuthName(String authName) {
        this.authName = authName;
    }
    
    
}
