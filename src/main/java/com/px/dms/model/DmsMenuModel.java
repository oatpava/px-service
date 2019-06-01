/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.model;

//import com.px.dms.model.post.DmsMenuPostModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TOP
 */
@XmlRootElement(name = "DmsManu")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "เมนูระบบจัดเก็บ")
public class DmsMenuModel extends VersionModel {

    public static final long serialVersionUID = 0L;

    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "dmsMenuName")
    @ApiParam(name = "dmsMenuName",value = "ชื่อเมนู",required = false)
    @Since(1.0)
    @Expose
    private String dmsMenuName;
    
    @XmlElement(name = "dmsMenuAuthorityDetail")
    @ApiParam(name = "dmsMenuAuthorityDetail",value = "dmsMenuAuthorityDetail",required = false)
    @Since(1.0)
    @Expose
    private String dmsMenuAuthorityDetail;
    
    @XmlElement(name = "dmsMenuType")
    @ApiParam(name = "dmsMenuType",value = "ประเภทเมนู",required = false)
    @Since(1.0)
    @Expose
    private String dmsMenuType;
    
    @XmlElement(name = "dmsMenuAuthoritySystem")
    @ApiParam(name = "dmsMenuAuthoritySystem",value = "dmsMenuAuthoritySystem",required = false)
    @Since(1.0)
    @Expose
    private String dmsMenuAuthoritySystem;
    
    @XmlElement(name = "dmsMenuAuthorityOrderType")
    @ApiParam(name = "dmsMenuAuthorityOrderType",value = "dmsMenuAuthorityOrderType",required = true)
    @Since(1.0)
    @Expose
    private int dmsMenuAuthorityOrderType;
    
    
    public DmsMenuModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDmsMenuName() {
        return dmsMenuName;
    }

    public void setDmsMenuName(String dmsMenuName) {
        this.dmsMenuName = dmsMenuName;
    }

    public String getDmsMenuAuthorityDetail() {
        return dmsMenuAuthorityDetail;
    }

    public void setDmsMenuAuthorityDetail(String dmsMenuAuthorityDetail) {
        this.dmsMenuAuthorityDetail = dmsMenuAuthorityDetail;
    }

    public String getDmsMenuType() {
        return dmsMenuType;
    }

    public void setDmsMenuType(String dmsMenuType) {
        this.dmsMenuType = dmsMenuType;
    }

    public String getDmsMenuAuthoritySystem() {
        return dmsMenuAuthoritySystem;
    }

    public void setDmsMenuAuthoritySystem(String dmsMenuAuthoritySystem) {
        this.dmsMenuAuthoritySystem = dmsMenuAuthoritySystem;
    }

    public int getDmsMenuAuthorityOrderType() {
        return dmsMenuAuthorityOrderType;
    }

    public void setDmsMenuAuthorityOrderType(int dmsMenuAuthorityOrderType) {
        this.dmsMenuAuthorityOrderType = dmsMenuAuthorityOrderType;
    }

  

   

    
    

}
