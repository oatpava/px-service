
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
@ApiModel(description = "ข้อมูลรหัสชั้นความเร็ว")
@XmlRootElement(name = "ThegifModel_ECMSSpeed")
public class ThegifModel_ECMSSpeed { 
    
    @Expose @Since(1.0) private String speedValue;
    @Expose @Since(1.0) private String speedDescription;
    
    public ThegifModel_ECMSSpeed() {
    }

    public String getSpeedValue() {
        return speedValue;
    }

    public void setSpeedValue(String speedValue) {
        this.speedValue = speedValue;
    }

    public String getSpeedDescription() {
        return speedDescription;
    }

    public void setSpeedDescription(String speedDescription) {
        this.speedDescription = speedDescription;
    }
    
}
