/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.share.model;

import com.google.gson.annotations.Expose;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Oat
 */
public class RecycleBinSearchModel extends VersionModel {

    @XmlElement(name = "moduleName")
    @Expose
    private String moduleName;

    @XmlElement(name = "description")
    @Expose
    private String description;

    @XmlElement(name = "startDate")
    @Expose
    private String startDate;

    @XmlElement(name = "endDate")
    @Expose
    private String endDate;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
