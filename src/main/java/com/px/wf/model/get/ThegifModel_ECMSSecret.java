
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
@ApiModel(description = "ข้อมูลรหัสชั้นความลับ")
@XmlRootElement(name = "ThegifModel_ECMSSecret")
public class ThegifModel_ECMSSecret { 
    
    @Expose @Since(1.0) private String secretValue;
    @Expose @Since(1.0) private String secretDescription;
    
    public ThegifModel_ECMSSecret() {
    }

    public String getSecretValue() {
        return secretValue;
    }

    public void setSecretValue(String secretValue) {
        this.secretValue = secretValue;
    }

    public String getSecretDescription() {
        return secretDescription;
    }

    public void setSecretDescription(String secretDescription) {
        this.secretDescription = secretDescription;
    }

}
