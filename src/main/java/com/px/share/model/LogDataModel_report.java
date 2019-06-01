
package com.px.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "LogDataModel_report")
@ApiModel(description = "จำนวนข้อมูลประวัติการใช้งานระบบ")
public class LogDataModel_report extends VersionModel{

    private static final long serialVersionUID = -2422897624938178323L;
    
    @Since(1.0)
    @XmlElement(name = "moduleName")
    @Expose
    private String moduleName;
    
    @Since(1.0)
    @XmlElement(name = "countLog")
    @Expose
    private int countLog;
    
    public LogDataModel_report() {
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setCountLog(int countLog) {
        this.countLog = countLog;
    }

}
