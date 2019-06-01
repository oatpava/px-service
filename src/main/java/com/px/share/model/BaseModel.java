package com.px.share.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.ws.rs.HeaderParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OPAS
 */
@MappedSuperclass
@ApiModel( description = "BaseModel resource representation" )
@XmlRootElement(name = "Base")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseModel implements Serializable{
    private static final long serialVersionUID = 5048290740099666536L;
    
    @XmlElement(name = "userID")
    @ApiModelProperty(name = "userID",dataType = "int",value = "รหัสผู้ใช้งาน", required = true ,hidden = true)
    @HeaderParam("userID") 
    @NotNull
    private int userId;
    
    @XmlElement(name = "userType")
    @ApiModelProperty(name = "userType",dataType = "string",value = "ประเภทผู้ใช้งาน", required = true,hidden = true )
    @HeaderParam("userType")
    @NotNull 
    private String userType;
    
    @XmlElement(name = "clientIp")
    @ApiModelProperty(name = "clientIp",dataType = "string",value = "ip address", required = false,hidden = true )
    @HeaderParam("clientIp")
    private String clientIp;
    
    public BaseModel() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
    
    

}
