
package com.px.mwp.model;

import com.google.gson.annotations.Expose;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@MappedSuperclass
@ApiModel( description = "ช่วงเวลาในการกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก" )
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "InOutAssignModel_groupDate")
public class InOutAssignModel_groupDate extends VersionModel {

    private static final long serialVersionUID = 7924389114906057002L;
    
    @XmlElement(name = "id")
    @Expose private int id;
        
    @XmlElement(name = "inOutAssignStartDate")
    @Expose private String inOutAssignStartDate;
    
    @XmlElement(name = "inOutAssignEndDate")
    @Expose private String inOutAssignEndDate;
    
    @XmlElement(name = "checkExpire")
    @Expose private boolean checkExpire;
    
    public InOutAssignModel_groupDate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInOutAssignStartDate() {
        return inOutAssignStartDate;
    }

    public void setInOutAssignStartDate(String inOutAssignStartDate) {
        this.inOutAssignStartDate = inOutAssignStartDate;
    }

    public String getInOutAssignEndDate() {
        return inOutAssignEndDate;
    }

    public void setInOutAssignEndDate(String inOutAssignEndDate) {
        this.inOutAssignEndDate = inOutAssignEndDate;
    }

    public boolean isCheckExpire() {
        return checkExpire;
    }

    public void setCheckExpire(boolean checkExpire) {
        this.checkExpire = checkExpire;
    }
   
}
