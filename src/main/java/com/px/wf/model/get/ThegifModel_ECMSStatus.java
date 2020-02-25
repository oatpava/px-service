
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
@ApiModel(description = "การสอบถามสถานะส่งหนังสือ")
@XmlRootElement(name = "ThegifModel_ECMSStatus")
public class ThegifModel_ECMSStatus { 
    
    @Expose @Since(1.0) private String ECMSStatus;

    public ThegifModel_ECMSStatus() {
    }

    public String getECMSStatus() {
        return ECMSStatus;
    }

    public void setECMSStatus(String ECMSStatus) {
        this.ECMSStatus = ECMSStatus;
    }
}
