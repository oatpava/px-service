
package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "WfContentModel_MaxContentNoAndBookNo")
@ApiModel(description = "รายการเลขที่สามารถใช้ได้ล่าสุดของเลขทะเบียน และ เลขที่หนังสือ")
public class WfContentModel_groupContentNoAndBookNo implements Serializable {

    private static final long serialVersionUID = -4327415308031223483L;
     
    @Expose @Since(1.0) private String wfContentNo;
    @Expose @Since(1.0) private int wfContentYear;
    @Expose @Since(1.0) private int wfContentPoint;
    @Expose @Since(1.0) private int wfContentNumber;
    @Expose @Since(1.0) private String wfFolderPre;
    
    public WfContentModel_groupContentNoAndBookNo() {
    }

    public void setWfContentNo(String wfContentNo) {
        this.wfContentNo = wfContentNo;
    }

    public void setWfContentYear(int wfContentYear) {
        this.wfContentYear = wfContentYear;
    }

    public void setWfContentPoint(int wfContentPoint) {
        this.wfContentPoint = wfContentPoint;
    }

    public void setWfContentNumber(int wfContentNumber) {
        this.wfContentNumber = wfContentNumber;
    }

    public void setWfFolderPre(String wfFolderPre) {
        this.wfFolderPre = wfFolderPre;
    }
}
