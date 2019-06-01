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
@XmlRootElement(name = "LookupDetail")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "รายละเอียดข้อมูลตัวเลือก" )
public class LookupDetailModel extends VersionModel{
    
    private static final long serialVersionUID = -106718628279217390L;
 
    @XmlElement(name = "id")
    @ApiParam(name = "id",value = "รหัสรายละเอียดข้อมูลตัวเลือก",required = true)
    @Expose 
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "name")
    @ApiParam(name = "name",value = "ชื่อรายละเอียดข้อมูลตัวเลือก",required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String name;
    
    @XmlElement(name = "lookupId")
    @ApiParam(name = "lookupId",value = "รหัสข้อมูลตัวเลือก",required = true)
    @Expose
    @Since(1.0)
    private int lookupId;

    public LookupDetailModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสรายละเอียดข้อมูลตัวเลือก", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @ApiModelProperty(name = "name", example = "ทดสอบรายละเอียดตัวเลือก", dataType = "string", value = "ชื่อรายละเอียดข้อมูลตัวเลือก", required = true)
    public void setName(String name) {
        this.name = name;
    }

    public int getLookupId() {
        return lookupId;
    }

    @ApiModelProperty(name = "lookupId", example = "ทดสอบตัวเลือก", dataType = "int", value = "รหัสตัวเลือก", required = true)
    public void setLookupId(int lookupId) {
        this.lookupId = lookupId;
    }

    
    
    
}
