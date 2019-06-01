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
@XmlRootElement(name = "PositionType")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ประเภทตำแหน่งข้าราชการ" )
public class PositionTypeModel extends VersionModel{
    
    private static final long serialVersionUID = 4474213887516856541L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสประเภทตำแหน่ง", defaultValue = "0", required = false)
    @Expose 
    private int id;
    
    @XmlElement(name = "name")
    @ApiParam(name = "name", example = "ทดสอบชื่อประเภทตำแหน่ง", value = "ชื่อประเภทตำแหน่ง", defaultValue = "ทดสอบชื่อประเภทตำแหน่ง", required = true)
    @Size(max = 100)
    @Expose
    @Since(1.0)
    private String name;
    
    @XmlElement(name = "abbr")
    @ApiParam(name = "abbr", example = "กAa", value = "สัญญลักษ์ประเภทตำแหน่ง", defaultValue = "กAa", required = false)
    @Size(max = 3)
    @Expose
    @Since(1.0)
    private String abbr;

    public PositionTypeModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสประเภทตำแหน่ง", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @ApiModelProperty(name = "name", example = "ทดสอบชื่อประเภทตำแหน่ง", dataType = "string", value = "ชื่อประเภทตำแหน่ง", required = true)
    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    @ApiModelProperty(name = "abbr", example = "กAa", dataType = "string", value = "สัญญลักษ์ประเภทตำแหน่ง", required = false)
    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    
}
