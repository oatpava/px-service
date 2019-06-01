/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author TOP
 */
@Entity
        @Table(name = "PC_DMS_MENU")
        @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "DMS_MENU_ID")),
//        @AttributeOverride(name = "orderNo", column = @Column(name = "DOCUMENT_TYPE_DETAIL_ORDER"))
})
public class DmsMenu extends BaseEntity{
    @Transient
    public static final long serialVersionUID = 0L;
    
    @Column(name = "DMS_MENU_NAME", nullable = true, length = 255)
    private String dmsMenuName;

    @Column(name = "DMS_MENU_AUTHORITY_DETAIL", nullable = false, length = 10)
    private String dmsMenuAuthorityDetail;

    @Column(name = "DMS_MENU_AUTHORITY_SYSTEM", nullable = false, length = 10)
    private String dmsMenuAuthoritySystem;

    @Column(name = "DMS_MENU_TYPE", nullable = false, length = 10)
    private String dmsMenuType;

    @Column(name = "DMS_MENU_AUTHORTYPE_TYPE_ORDER", nullable = true, length = 10)
    private int dmsMenuAuthorityOrderType;

    public DmsMenu() {
    }

    public DmsMenu(String dmsMenuName, String dmsMenuAuthorityDetail, String dmsMenuAuthoritySystem, String dmsMenuType, int dmsMenuAuthorityOrderType, Integer createdBy) {
        super(createdBy);
        this.dmsMenuName = dmsMenuName;
        this.dmsMenuAuthorityDetail = dmsMenuAuthorityDetail;
        this.dmsMenuAuthoritySystem = dmsMenuAuthoritySystem;
        this.dmsMenuType = dmsMenuType;
        this.dmsMenuAuthorityOrderType = dmsMenuAuthorityOrderType;
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

    public String getDmsMenuAuthoritySystem() {
        return dmsMenuAuthoritySystem;
    }

    public void setDmsMenuAuthoritySystem(String dmsMenuAuthoritySystem) {
        this.dmsMenuAuthoritySystem = dmsMenuAuthoritySystem;
    }

    public String getDmsMenuType() {
        return dmsMenuType;
    }

    public void setDmsMenuType(String dmsMenuType) {
        this.dmsMenuType = dmsMenuType;
    }

    public int getDmsMenuAuthorityOrderType() {
        return dmsMenuAuthorityOrderType;
    }

    public void setDmsMenuAuthorityOrderType(int dmsMenuAuthorityOrderType) {
        this.dmsMenuAuthorityOrderType = dmsMenuAuthorityOrderType;
    }

    
      
}
