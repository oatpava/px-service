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
@XmlRootElement(name = "Lookup")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ข้อมูลตัวเลือก",discriminator = "type" )
public class LookupModel extends VersionModel{
    
    private static final long serialVersionUID = 7303175071003984705L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสตัวเลือก", defaultValue = "0", required = false )
    @Expose 
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "name")
    @ApiParam(name = "name", example = "ทดสอบตัวเลือก", value = "ชื่อตัวเลือก", defaultValue = "ทดสอบตัวเลือก", required = true )
    @Size(max = 100)
    @Since(1.0)
    @Expose private String name;

    public LookupModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสตัวเลือก", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    @ApiModelProperty(name = "name", example = "ทดสอบตัวเลือก", dataType = "string", value = "ชื่อตัวเลือก", required = true)
    public void setName(String name) {
        this.name = name;
    }

    
    
}
