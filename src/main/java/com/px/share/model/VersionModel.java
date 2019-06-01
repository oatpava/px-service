package com.px.share.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OPAS
 */
@XmlRootElement(name = "Version")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "version ผลลัพธ์" )
public class VersionModel extends BaseModel{
    
    private static final long serialVersionUID = 9207196691532364120L;
    
    @XmlElement(name = "version")
    @ApiParam(name = "version", value = "version output", required = false) 
    @DefaultValue("1.0") 
    @QueryParam("version") 
    float version;

    public float getVersion() {
        return version;
    }
}
