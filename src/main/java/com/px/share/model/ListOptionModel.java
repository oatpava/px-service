package com.px.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
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
@XmlRootElement(name = "listOption")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "Option สำหรับ list ผลลัพธ์" )
public class ListOptionModel extends VersionModel{
    
    private static final long serialVersionUID = 4032137102897080405L;
    
    @XmlElement(name = "offset")
    @ApiParam(name = "offset", value = "ตำแหน่งเริ่มต้น", required = true) 
    @DefaultValue("0") 
    @QueryParam("offset")
    @Expose
    @Since(1.0)
    int offset;
    
    @XmlElement(name = "limit")
    @ApiParam(name = "limit", value = "จำนวนข้อมูลที่ต้องการ", required = true) 
    @DefaultValue("20") 
    @QueryParam("limit") 
    int limit;
    
    @XmlElement(name = "sort")
    @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false) 
    @DefaultValue("createdDate") 
    @QueryParam("sort") 
    String sort;
    
    @XmlElement(name = "dir")
    @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false) 
    @DefaultValue("asc") 
    @QueryParam("dir") 
    String dir;

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public String getSort() {
        return sort;
    }

    public String getDir() {
        return dir;
    }
    
    
}
