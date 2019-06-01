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
@Table(name = "PC_DMS_FIELD")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="DMS_FIELD_ID")),
//    @AttributeOverride(name="orderNo", column=@Column(name="DMS_FIELD_NAME_ORDER"))
})
public class DmsField extends BaseEntity{
    @Transient
    public static final long serialVersionUID = 0L;
    
    @Column(name="DMS_FIELD_NAME", nullable = true,length = 255)
    private String dmsFieldName;
    
    @Column(name="DMS_FIELD_TYPE", nullable = true,length = 50)
    private String dmsFieldType;
    
    @Column(name="DMS_FIELD_MAP", nullable = true,length = 50)
    private String dmsFieldMap;
    


    public DmsField() {
    }

    public DmsField(String dmsFieldName, String dmsFieldType, String dmsFieldMap, Integer createdBy) {
        super(createdBy);
        this.dmsFieldName = dmsFieldName;
        this.dmsFieldType = dmsFieldType;
        this.dmsFieldMap = dmsFieldMap;
      
    }

    public String getDmsFieldName() {
        return dmsFieldName;
    }

    public void setDmsFieldName(String dmsFieldName) {
        this.dmsFieldName = dmsFieldName;
    }

    public String getDmsFieldType() {
        return dmsFieldType;
    }

    public void setDmsFieldType(String dmsFieldType) {
        this.dmsFieldType = dmsFieldType;
    }

    public String getDmsFieldMap() {
        return dmsFieldMap;
    }

    public void setDmsFieldMap(String dmsFieldMap) {
        this.dmsFieldMap = dmsFieldMap;
    }

   
    
    
    
}
