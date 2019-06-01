package com.px.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.MasterModel;
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
@XmlRootElement(name = "Title")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "คำนำหน้า" )
public class TitleModel extends MasterModel{
    private static final long serialVersionUID = 6288616506977318114L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสคำนำหน้า", defaultValue = "0", required = false )
    @Since(1.0)
    @Expose
    private int id;
    
    @XmlElement(name = "name")
    @ApiParam(name = "name", example = "ทดสอบชื่อคำนำหน้า", value = "ชื่อคำนำหน้า", defaultValue = "ทดสอบชื่อคำนำหน้า", required = true)
    @Size(max = 50)
    @Expose 
    private String name;
    
    @XmlElement(name = "nameEng")
    @ApiParam(name = "nameEng", example = "test Title", value = "ชื่อคำนำหน้า ภาษาอังกฤษ", defaultValue = "test Title", required = false)
    @Size(max = 50)
    @Expose 
    private String nameEng;
    
    @XmlElement(name = "createdDate")
    @ApiParam(name = "createdDate", example = "01/01/2559 07:30:45", value = "วันที่สร้าง", defaultValue = "01/01/2559 07:30:45", hidden = true, required = false)
    @Since(1.0)
    @Expose
    private String createdDate;

    public TitleModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสคำนำหน้า", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @ApiModelProperty(name = "name", example = "ทดสอบชื่อคำนำหน้า", dataType = "string", value = "ชื่อคำนำหน้า", required = true)
    public void setName(String name) {
        this.name = name;
    }

    public String getNameEng() {
        return nameEng;
    }

    @ApiModelProperty(name = "nameEng", example = "test Title", dataType = "string", value = "ชื่อคำนำหน้า ภาษาอังกฤษ", required = false)
    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    @ApiModelProperty(name = "createdDate", example = "01/01/2559 07:30:45", dataType = "string", value = "วันที่สร้าง", hidden = true, required = false)
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }


    
    
}
