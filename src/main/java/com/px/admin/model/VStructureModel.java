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
 * @author PRAXiS
 */
@XmlRootElement(name = "VStructure")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "view โครงสร้างหน่วยงาน" )
public class VStructureModel extends VersionModel{
    
    private static final long serialVersionUID = -4625766083883688307L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสตัวเลือก", defaultValue = "0", required = false )
    @Expose
    @Since(1.0)
    private int id;    
    
    @XmlElement(name = "name")
//    @ApiParam(name = "name", example = "ทดสอบชื่อโครงสร้าง", value = "ชื่อโครงสร้าง", defaultValue = "ทดสอบชื่อโครงสร้าง", required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String name;
    
    @XmlElement(name = "shortName")
//    @ApiParam(name = "name", example = "ทดสอบชื่อย่อ", value = "ชื่อย่อโครงสร้าง", defaultValue = "ทดสอบชื่อย่อ", required = true)
    @Size(max = 50)
    @Expose 
    @Since(1.0)
    private String shortName;
    
    
    @XmlElement(name = "detail")
//    @ApiParam(name = "detail", example = "ทดสอบรายละเอียดโครงสร้าง", value = "รายละเอียดโครงสร้าง", defaultValue = "ทดสอบรายละเอียดโครงสร้าง", required = false)
    @Size(max = 1000)
    @Expose 
    @Since(1.0)
    private String detail;
    
    
    @XmlElement(name = "code")
//    @ApiParam(name = "code", example = "111AAAbbb222ddd", value = "รหัสอ้างอิงของโครงสร้าง", defaultValue = "111AAAbbb222ddd", required = false)
    @Size(max = 15)
    @Expose 
    @Since(1.0)
    private String code;

    public VStructureModel() {
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
}
