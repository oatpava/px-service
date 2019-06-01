package com.px.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
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
 * @author Peach
 */
@XmlRootElement(name = "Month")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "เดือน" )
public class MonthModel extends VersionModel {
    private static final long serialVersionUID = -6599539062876396067L;
    
    @Since(1.0)
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสเดือน", defaultValue = "0", required = false )
    @Expose 
    private int id;
    
//    @Since(1.0)
//    @XmlElement(name = "createdDate")
//    @ApiParam(name = "createdDate", example = "01/01/2559 10:00:00", value = "วันที่สร้าง", defaultValue = "", required = false)
//    @Size(min = 10, max = 10)
//    @Expose 
//    private Date createdDate;
    
    @Since(1.0)
    @XmlElement(name = "orderFiscal")
    @ApiParam(name = "orderFiscal",example = "0",value = "ลำดับตามปีงบประมาณ",defaultValue = "0",required = true)
    @Expose
    private int orderFiscal;
    
    @Since(1.0)
    @XmlElement(name = "thaiName")
    @ApiParam(name = "thaiName",example = "มกราคม",value = "ชื่อภาษาไทย",defaultValue = "มกราคม",required = true)
    @Size(max = 50)
    @Expose
    private String thaiName;
    
    @Since(1.0)
    @XmlElement(name = "thaiNameAbbr")
    @ApiParam(name = "thaiNameAbbr",example = "ม.ค.",value = "ชื่อย่อภาษาไทย",defaultValue = "ม.ค.",required = true)
    @Size(max = 10)
    @Expose
    private String thaiNameAbbr;
    
    @Since(1.0)
    @XmlElement(name = "engName")
    @ApiParam(name = "engName",example = "January",value = "ชื่อภาษาอังกฤษ",defaultValue = "January",required = true)
    @Size(max = 30)
    @Expose
    private String engName;
    
    @Since(1.0)
    @XmlElement(name = "engNameAbbr")
    @ApiParam(name = "engNameAbbr",example = "JAN",value = "ชื่อย่อภาษาอังกฤษ",defaultValue = "JAN",required = true)
    @Size(max = 10)
    @Expose
    private String engNameAbbr;

    public MonthModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสไฟล์เอกสารแนบ", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }
    
//    public Date getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(Date createdDate) {
//        this.createdDate = createdDate;
//    }

    public int getOrderFiscal() {
        return orderFiscal;
    }

    public void setOrderFiscal(int orderFiscal) {
        this.orderFiscal = orderFiscal;
    }

    public String getThaiName() {
        return thaiName;
    }

    public void setThaiName(String thaiName) {
        this.thaiName = thaiName;
    }

    public String getThaiNameAbbr() {
        return thaiNameAbbr;
    }

    public void setThaiNameAbbr(String thaiNameAbbr) {
        this.thaiNameAbbr = thaiNameAbbr;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getEngNameAbbr() {
        return engNameAbbr;
    }

    public void setEngNameAbbr(String engNameAbbr) {
        this.engNameAbbr = engNameAbbr;
    }

}
