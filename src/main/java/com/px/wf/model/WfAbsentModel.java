package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CHALERMPOL
 */
@XmlRootElement(name = "WfAbsent")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "วันหยุดพนักงาน")
public class WfAbsentModel extends VersionModel{

    private static final long serialVersionUID = -7871064641276737729L;
        
    @XmlElement(name = "id")
    @Expose 
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "absentDate", required = true)
    @ApiParam(name = "absentDate", value = "วันหยุด", required = true)
    @Expose 
    @Since(1.0)
    private String absentDate;
    
    @XmlElement(name = "userId")
    @ApiParam(name = "userId", value = "รหัสพนักงาน", required = true)
    @Since(1.0)
    @Expose
    private int userId;

    public WfAbsentModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbsentDate() {
        return absentDate;
    }

    public void setAbsentDate(String absentDate) {
        this.absentDate = absentDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    
    
}
