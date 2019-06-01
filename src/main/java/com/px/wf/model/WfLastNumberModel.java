/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiParam;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Oat
 */
public class WfLastNumberModel {
    
    @XmlElement(name = "year")
    @ApiParam(name = "year", value = "year", required = true)
    @Since(1.0)
    @Expose
    private int year;
    
    @XmlElement(name = "contentNumber")
    @ApiParam(name = "contentNumber", value = "contentNumber", required = true)
    @Since(1.0)
    @Expose
    private int contentNumber;
    
    @XmlElement(name = "bookNumber")
    @ApiParam(name = "bookNumber", value = "bookNumber", required = true)
    @Since(1.0)
    @Expose
    private int bookNumber;
    
    @XmlElement(name = "point")
    @ApiParam(name = "point", value = "point", required = true)
    @Since(1.0)
    @Expose
    private int point;
    
    @XmlElement(name = "contentFormat")
    @ApiParam(name = "contentFormat", value = "contentFormat", required = true)
    @Since(1.0)
    @Expose
    private int contentFormat;
    
    @XmlElement(name = "bookFormat")
    @ApiParam(name = "bookFormat", value = "bookFormat", required = true)
    @Since(1.0)
    @Expose
    private int bookFormat;
    
    @XmlElement(name = "contentNo")
    @ApiParam(name = "contentNo", value = "contentNo", required = true)
    @Since(1.0)
    @Expose
    private String contentNo;
    
    @XmlElement(name = "bookNo")
    @ApiParam(name = "bookNo", value = "bookNo", required = true)
    @Since(1.0)
    @Expose
    private String bookNo;

    public WfLastNumberModel() {
    }

    public WfLastNumberModel(int year, int contentNumber, int bookNumber, int point, int contentFormat, int bookFormat, String contentNo, String bookNo) {
        this.year = year;
        this.contentNumber = contentNumber;
        this.bookNumber = bookNumber;
        this.point = point;
        this.contentFormat = contentFormat;
        this.bookFormat = bookFormat;
        this.contentNo = contentNo;
        this.bookNo = bookNo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getContentNumber() {
        return contentNumber;
    }

    public void setContentNumber(int contentNumber) {
        this.contentNumber = contentNumber;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getContentFormat() {
        return contentFormat;
    }

    public void setContentFormat(int contentFormat) {
        this.contentFormat = contentFormat;
    }

    public int getBookFormat() {
        return bookFormat;
    }

    public void setBookFormat(int bookFormat) {
        this.bookFormat = bookFormat;
    }

    public String getContentNo() {
        return contentNo;
    }

    public void setContentNo(String contentNo) {
        this.contentNo = contentNo;
    }

    public String getBookNo() {
        return bookNo;
    }

    public void setBookNo(String bookNo) {
        this.bookNo = bookNo;
    }

}
