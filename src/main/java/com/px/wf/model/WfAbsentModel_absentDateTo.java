
package com.px.wf.model;

import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModel;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CHALERMPOL
 */
@MappedSuperclass
@ApiModel( description = "วันหยุดถึง" )
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "WfAbsentModel_absentDateTo")
public class WfAbsentModel_absentDateTo extends WfAbsentModel {

    private static final long serialVersionUID = -7144900594526117992L;
    
    @XmlElement(name = "absentDateTo")
    @Expose private String absentDateTo;
    
    public WfAbsentModel_absentDateTo() {
    }

    public String getAbsentDateTo() {
        return absentDateTo;
    }

    public void setAbsentDateTo(String absentDateTo) {
        this.absentDateTo = absentDateTo;
    }
}
