package com.px.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OPAS
 */
@XmlRootElement(name = "UserTypeOrder")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ประเภทผู้ใช้งานระบบสำหรับจัดลำดับ" )
public class UserTypeOrderModel extends VersionModel{
    
    private static final long serialVersionUID = 6613074618349890609L;
    
    @XmlElement(name = "id")
    @ApiParam(name = "id", example = "0", value = "รหัสประเภทผู้ใช้งานระบบสำหรับจัดลำดับ", defaultValue = "0", required = false )
    @Expose 
    @Since(1.0)
    private int id;
    
    @XmlElement(name = "name")
    @ApiParam(name = "name", example = "ทดสอบประเภทผู้ใช้งานระบบสำหรับจัดลำดับ", value = "ชื่อประเภทผู้ใช้งานระบบสำหรับจัดลำดับ", defaultValue = "ทดสอบประเภทผู้ใช้งานระบบสำหรับจัดลำดับ", required = true)
    @Size(max = 100)
    @Expose 
    @Since(1.0)
    private String name;

    public UserTypeOrderModel() {
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(name = "id", example = "0", dataType = "int", value = "รหัสประเภทผู้ใช้งานระบบสำหรับจัดลำดับ", readOnly = true, required = false)
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @ApiModelProperty(name = "name", example = "ทดสอบประเภทผู้ใช้งานระบบสำหรับจัดลำดับ", dataType = "string", value = "ชื่อประเภทผู้ใช้งานระบบสำหรับจัดลำดับ", required = true)
    public void setName(String name) {
        this.name = name;
    }

    
    
    
}
