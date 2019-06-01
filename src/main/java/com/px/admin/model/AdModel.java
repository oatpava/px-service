package com.px.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OPAS
 */
@XmlRootElement(name = "Ad")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ข้อมูล AD" )
public class AdModel extends VersionModel{
    
    private static final long serialVersionUID = 5310569097401305638L;
    
    @XmlElement(name = "id")    
    @Expose
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "name")
    @Size(max = 100)
    @Expose 
    @Since(1.0)
    private String name;
    
    @XmlElement(name = "host")
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String host;
    
    @XmlElement(name = "port")
    @Size(max = 5)
    @Expose 
    @Since(1.0)
    private String port;
    
    @XmlElement(name = "base")
    @Size(max = 100)
    @Expose 
    @Since(1.0)
    private String base;
    
    @XmlElement(name = "user")
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String user;
    
    @XmlElement(name = "pass")
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String pass;
    
    @XmlElement(name = "type")
    @Size(max = 5)
    @Expose 
    @Since(1.0)
    private String type;
    
    @XmlElement(name = "prefix")
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String prefix;
    
    @XmlElement(name = "suffix")
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String suffix;
    
    @XmlElement(name = "attribute")
    @Size(max = 20)
    @Expose 
    @Since(1.0)
    @ApiModelProperty(name = "attribute", example = "Praxis Ad", dataType = "string", value = "ชื่ออ้างอิง AD", required = true)
    private String attribute;

    public AdModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสข้อมูล AD", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @ApiModelProperty(name = "name", example = "Praxis Ad", dataType = "string", value = "ชื่ออ้างอิง AD", required = true)
    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    @ApiModelProperty(name = "host", example = "ค่า AD Host", dataType = "string", value = "AD Host", required = true)
    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    @ApiModelProperty(name = "port", example = "ค่า AD Port", dataType = "string", value = "AD Port", required = true)
    public void setPort(String port) {
        this.port = port;
    }

    public String getBase() {
        return base;
    }

    @ApiModelProperty(name = "base", example = "ค่า AD Base", dataType = "string", value = "AD Base", required = true)
    public void setBase(String base) {
        this.base = base;
    }

    public String getUser() {
        return user;
    }

    @ApiModelProperty(name = "user", example = "ชื่อ AD UserName", dataType = "string", value = "ชื่อ AD UserName", required = true)
    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    @ApiModelProperty(name = "pass", example = "รหัสผ่าน AD Password", dataType = "string", value = "รหัสผ่านของ AD or LDAP", required = true)
    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    @ApiModelProperty(name = "type", example = "AD", dataType = "string", value = "ประเภทการเชื่อมต่อ AD or LDAP", required = true)
    public void setType(String type) {
        this.type = type;
    }

    public String getPrefix() {
        return prefix;
    }

    @ApiModelProperty(name = "prefix", example = "ทดสอบ AD Prefix", dataType = "string", value = "AD Prefix", required = false)
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    @ApiModelProperty(name = "suffix", example = "ทดสอบ AD Suffix", dataType = "string", value = "AD Suffix", required = false)
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    
}
