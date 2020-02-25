
package com.px.wf.model.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "ThegifDepartment")
@ApiModel(description = "โครงสร้างของการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ")
public class ThegifDepartmentPostModel extends VersionModel {

    private static final long serialVersionUID = 5986112947141934271L;
    
    @XmlElement(name = "thegifDepartmentName")
    @ApiParam(name = "thegifDepartmentName", value = "ชื่อโครงสร้าง", required = false)
    @FormParam("thegifDepartmentName")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifDepartmentName;
    
    @XmlElement(name = "thegifDepartmentCode")
    @ApiParam(name = "thegifDepartmentCode", value = "รหัสโครงสร้าง", required = false)
    @FormParam("thegifDepartmentCode")
    @Size(max = 50)
    @Since(1.0)
    @Expose private String thegifDepartmentCode;
    
    @XmlElement(name = "parentId")
    @ApiParam(name = "parentId", value = "รหัสแม่ของโครงสร้าง", required = true)
    @FormParam("parentId")
    @Since(1.0)
    @Expose private Integer parentId;
    
    @XmlElement(name = "thegifDepartmentServiceName")
    @ApiParam(name = "thegifDepartmentServiceName", value = "service ของปลายทางสำหรับส่ง", required = false)
    @FormParam("thegifDepartmentServiceName")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifDepartmentServiceName;
    
    public ThegifDepartmentPostModel() {
    }

    public String getThegifDepartmentName() {
        return thegifDepartmentName;
    }

    public void setThegifDepartmentName(String thegifDepartmentName) {
        this.thegifDepartmentName = thegifDepartmentName;
    }

    public String getThegifDepartmentCode() {
        return thegifDepartmentCode;
    }

    public void setThegifDepartmentCode(String thegifDepartmentCode) {
        this.thegifDepartmentCode = thegifDepartmentCode;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getThegifDepartmentServiceName() {
        return thegifDepartmentServiceName;
    }

    public void setThegifDepartmentServiceName(String thegifDepartmentServiceName) {
        this.thegifDepartmentServiceName = thegifDepartmentServiceName;
    }

}
