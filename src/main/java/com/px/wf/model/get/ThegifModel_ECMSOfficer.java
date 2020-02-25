
package com.px.wf.model.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@MappedSuperclass
@ApiModel(description = "ข้อมูลการผู้ใช้งาน ecms")
@XmlRootElement(name = "ThegifModel_ECMSOfficer")
public class ThegifModel_ECMSOfficer { 
    
    @Expose @Since(1.0) private String WSURL;
    @Expose @Since(1.0) private String name;//หน่วยงาน
    @Expose @Since(1.0) private String lName;//กระทรวง
    @Expose @Since(1.0) private String title;//ชื่อผู้ใช้งาน
    @Expose @Since(1.0) private String ministryId;//รหัสกระทรวง
    @Expose @Since(1.0) private String deptId;//รหัสหน่วยงาน
    
    public ThegifModel_ECMSOfficer() {
    }

    public String getWSURL() {
        return WSURL;
    }

    public void setWSURL(String WSURL) {
        this.WSURL = WSURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMinistryId() {
        return ministryId;
    }

    public void setMinistryId(String ministryId) {
        this.ministryId = ministryId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    
}
