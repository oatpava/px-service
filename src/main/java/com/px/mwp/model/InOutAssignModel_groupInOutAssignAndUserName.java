
package com.px.mwp.model;

import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModel;
import java.util.ArrayList;
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
@ApiModel( description = "กำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออกและข้อมูลชื่อผู้ได้รับการกำหนดสิทธิ์" )
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "InOutAssignModel_groupInOutAssignAndUserName")
public class InOutAssignModel_groupInOutAssignAndUserName extends InOutAssignModel {

    private static final long serialVersionUID = -460590801908748599L;
    
    @XmlElement(name = "assignName")
    @Expose private String assignName;
    
//    @XmlElement(name = "checkExpire")
//    @Expose private Boolean checkExpire;
    
    @XmlElement(name = "listInOutAssignModel_groupDate")
    @Expose private ArrayList<InOutAssignModel_groupDate> listInOutAssignModel_groupDate;
    
    public InOutAssignModel_groupInOutAssignAndUserName() {
    }

    public String getAssignName() {
        return assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }
    
    public ArrayList<InOutAssignModel_groupDate> getListInOutAssignModel_groupDate() {
        return listInOutAssignModel_groupDate;
    }

    public void setListInOutAssignModel_groupDate(ArrayList<InOutAssignModel_groupDate> listInOutAssignModel_groupDate) {
        this.listInOutAssignModel_groupDate = listInOutAssignModel_groupDate;
    }
}
