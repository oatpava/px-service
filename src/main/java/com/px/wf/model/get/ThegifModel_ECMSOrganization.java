
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
@ApiModel(description = "ข้อมูลหน่วยงาน")
@XmlRootElement(name = "ThegifModel_ECMSOrganization")
public class ThegifModel_ECMSOrganization {
    
    @Expose @Since(1.0) private String ECMSURL;
    @Expose @Since(1.0) private String organizationENName;
    @Expose @Since(1.0) private String organizationID;
    @Expose @Since(1.0) private String organizationTHName;
    
    public ThegifModel_ECMSOrganization() {
    }

    public String getECMSURL() {
        return ECMSURL;
    }

    public void setECMSURL(String ECMSURL) {
        this.ECMSURL = ECMSURL;
    }

    public String getOrganizationENName() {
        return organizationENName;
    }

    public void setOrganizationENName(String organizationENName) {
        this.organizationENName = organizationENName;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getOrganizationTHName() {
        return organizationTHName;
    }

    public void setOrganizationTHName(String organizationTHName) {
        this.organizationTHName = organizationTHName;
    }
    
}
