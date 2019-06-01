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
 * @author OPAS
 */
@XmlRootElement(name = "Holiday")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "วันหยุดประจำปี")
public class HolidayModel extends VersionModel{
    
    private static final long serialVersionUID = -6997707028027408395L;
    
    @XmlElement(name = "id")
    @Expose 
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "holidayDate", required = true)
    @ApiParam(name = "holidayDate", value = "วันหยุด", required = true)
    @Expose 
    @Since(1.0)
    private String holidayDate;
    
    @XmlElement(name = "holidayName", required = true)
    @ApiParam(name = "holidayName", value = "ชื่อวันหยุด", required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String holidayName;

    public HolidayModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(String holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    
    
}
