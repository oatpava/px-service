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
@XmlRootElement(name = "Position")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ตำแหน่งข้าราชการ" )
public class PositionModel extends VersionModel{
    
    private static final long serialVersionUID = -1983998569656588612L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id",value = "รหัสตำแหน่งข้าราชการ",required = true)
    @Since(1.0)
    @Expose 
    private int id;
    
    
    @XmlElement(name = "name")
    @ApiParam(name = "name",value = "ชื่อตำแหน่ง",required = true)
    @Size(max = 100)
    @Expose
    @Since(1.0)
    private String name;
    
    @XmlElement(name = "nameEng")
    @ApiParam(name = "nameEng",value = "ชื่อตำแหน่งภาษาอังกฤษ",required = false)
    @Size(max = 100)
    @Expose
    @Since(1.1)
    private String nameEng;
    
    
    @XmlElement(name = "nameExtra")
    @ApiParam(name = "nameExtra",value = "ชื่อตำแหน่งเพิ่มเติม",required = true)
    @Expose
    @Since(1.0)
    private String nameExtra;

    public PositionModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสตำแหน่งข้าราชการ", required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @ApiModelProperty(name = "name", example = "ทดสอบชื่อตำแหน่ง", dataType = "string", value = "ชื่อตำแหน่ง", required = true)
    public void setName(String name) {
        this.name = name;
    }

    public String getNameEng() {
        return nameEng;
    }

    @ApiModelProperty(name = "nameEng", example = "ทดสอบชื่อตำแหน่ง Eng", dataType = "string", value = "ชื่อตำแหน่งภาษาอังกฤษ", required = false)
    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getNameExtra() {
        return nameExtra;
    }

    @ApiModelProperty(name = "nameExtra", example = "ทดสอบชื่อตำแหน่งเพิ่มเติม", dataType = "string", value = "ชื่อตำแหน่งเพิ่มเติม", required = false)
    public void setNameExtra(String nameExtra) {
        this.nameExtra = nameExtra;
    }

    
}
