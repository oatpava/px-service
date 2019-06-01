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
@XmlRootElement(name = "WordBriefDetail")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "รายละเอียดข้อมูลคำเหมือน" )
public class WordBriefDetailModel extends VersionModel{
    
    private static final long serialVersionUID = -2026031332501877381L;
 
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสรายละเอียดข้อมูลคำเหมือน", defaultValue = "0", required = false )
    @Expose 
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "name")
    @ApiParam(name = "name", example = "ทดสอบรายละเอียดข้อมูลคำเหมือน", value = "รายละเอียดข้อมูลคำเหมือน", defaultValue = "ทดสอบรายละเอียดข้อมูลคำเหมือน", required = true)
    @Size(max = 255)
    @Expose 
    @Since(1.0)
    private String name;
    
    @XmlElement(name = "wordBrief")
    @ApiParam(name = "wordBrief", value = "ข้อมูลคำเหมือน", required = true)
    private WordBriefModel wordBrief;

    public WordBriefDetailModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสรายละเอียดข้อมูลคำเหมือน", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @ApiModelProperty(name = "name", example = "฿1฿๑฿one฿หนึ่ง฿", dataType = "string", value = "รายละเอียดข้อมูลคำเหมือน", required = true)
    public void setName(String name) {
        this.name = name;
    }

    public WordBriefModel getWordBrief() {
        return wordBrief;
    }

    @ApiModelProperty(name = "wordBrief", value = "ข้อมูลคำเหมือน", required = true)
    public void setWordBrief(WordBriefModel wordBrief) {
        this.wordBrief = wordBrief;
    }
    
}
