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
@ApiModel( description = "ผู้ได้รับมอบหมายในการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก" )
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "InOutAssignModel_groupUserStructure")
public class InOutAssignModel_groupUserStructure extends VersionModel {

    private static final long serialVersionUID = 7457758126215955792L;
    
    @XmlElement(name = "id")
    @Expose private int id;
    
    @XmlElement(name = "inOutAssignOwnerType")
    @Expose private int inOutAssignOwnerType;
    
    @XmlElement(name = "fullName")
    @Expose private String fullName;
    
    public InOutAssignModel_groupUserStructure() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInOutAssignOwnerType() {
        return inOutAssignOwnerType;
    }

    public void setInOutAssignOwnerType(int inOutAssignOwnerType) {
        this.inOutAssignOwnerType = inOutAssignOwnerType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
}
