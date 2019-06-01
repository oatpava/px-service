
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
@ApiModel(description = "ข้อมูลรหัสกระทรวง")
@XmlRootElement(name = "ThegifModel_ECMSMinistry")
public class ThegifModel_ECMSMinistry {
    
    @Expose @Since(1.0) private String ministryID;
    @Expose @Since(1.0) private String ministryTHName;
    
    public ThegifModel_ECMSMinistry() {
    }

    public String getMinistryID() {
        return ministryID;
    }

    public void setMinistryID(String ministryID) {
        this.ministryID = ministryID;
    }

    public String getMinistryTHName() {
        return ministryTHName;
    }

    public void setMinistryTHName(String ministryTHName) {
        this.ministryTHName = ministryTHName;
    }
}
