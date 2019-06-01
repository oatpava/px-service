package com.px.share.model;

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
@XmlRootElement(name = "Param")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "การตั้งค่าระบบ" )
public class ParamModel extends VersionModel{
    private static final long serialVersionUID = -245948113389869808L;
    
    @XmlElement(name = "id")
    @Expose 
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "paramName")
    @ApiParam(name = "paramName",value = "ชื่อการตั้งค่า",required = true)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String paramName;
    
    @XmlElement(name = "paramValue")
    @ApiParam(name = "paramValue",value = "ค่าการตั้งค่า",required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String paramValue;

    public ParamModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    
    
}
