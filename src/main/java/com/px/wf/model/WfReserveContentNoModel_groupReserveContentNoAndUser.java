
package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "ReserveContentNoModel_groupReserveContentNoAndUser")
@ApiModel(description = "รายการของเลขจองที่จัดกลุ่มโดยเลขจองและชื่อผู้ใช้งาน")
public class WfReserveContentNoModel_groupReserveContentNoAndUser extends WfReserveContentNoModel {

    private static final long serialVersionUID = 7650605405418694954L;
    
    @XmlElement(name = "userName")
    @Expose 
    @Since(1.0)
    private String userName;
    
    public WfReserveContentNoModel_groupReserveContentNoAndUser() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
