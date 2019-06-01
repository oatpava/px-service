/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.entity;

import javax.persistence.Transient;

/**
 *
 * @author TOP
 */
public class ElasticSearch {
    @Transient
    public static final long serialVersionUID = 0L;
    
    String fieldName;
    String fieldData;
    
    public ElasticSearch() {
    }
    
    public ElasticSearch( String fieldName,String fieldData) {
         this.fieldName = fieldName;
         this.fieldData = fieldData;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldData() {
        return fieldData;
    }

    public void setFieldData(String fieldData) {
        this.fieldData = fieldData;
    }
    
}
