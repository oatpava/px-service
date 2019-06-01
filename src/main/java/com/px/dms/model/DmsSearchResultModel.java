/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author top
 */
@XmlRootElement(name = "DmsSearchResult")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ผลลัพธ์การค้นหาเอกสาร")
public class DmsSearchResultModel extends VersionModel {

    private static final long serialVersionUID = 0L;
    
    public DmsSearchResultModel() {
    }
    @XmlElement(name = "searchTime")
    @ApiParam(name = "searchTime", example = "0", value = "เวลาหน่วยมิลลิวินาที ที่ใช้ในการค้นหา", defaultValue = "0", required = false)
    @Since(1.0)
    @Expose
    private long searchTime;
    
    @XmlElement(name = "searchHit")
    @ApiParam(name = "searchHit", example = "0", value = "จำนวนที่ค้นพบ", defaultValue = "0", required = false)
    @Since(1.0)
    @Expose
    private long searchHit;
    
    @XmlElement(name = "searchAll")
    @ApiParam(name = "searchAll", example = "0", value = "จำนวนข้อมูลที่ค้นหา", defaultValue = "0", required = false)
    @Since(1.0)
    @Expose
    private long searchAll;
    
    @XmlElement(name = "searchResult")
    @ApiParam(name = "searchResult", example = "", value = "ผลลัพธ์ที่ค้นพบ", defaultValue = "", required = false)
    @Since(1.0)
    @Expose 
    private List<DmsSearchModel> searchResult;
    
     public long getSearchTime() {
        return searchTime;
    }

    @ApiModelProperty(name = "searchTime", example = "0", dataType = "long", value = "เวลาหน่วยมิลลิวินาที ที่ใช้ในการค้นหา", required = false)
    public void setSearchTime(long searchTime) {
        this.searchTime = searchTime;
    }

    public long getSearchHit() {
        return searchHit;
    }

    @ApiModelProperty(name = "searchHit", example = "0", dataType = "long", value = "จำนวนที่ค้นพบ", required = false)
    public void setSearchHit(long searchHit) {
        this.searchHit = searchHit;
    }

    public long getSearchAll() {
        return searchAll;
    }

    @ApiModelProperty(name = "searchAll", example = "0", dataType = "long", value = "จำนวนข้อมูลที่ค้นหา", required = false)
    public void setSearchAll(long searchAll) {
        this.searchAll = searchAll;
    }

    public List<DmsSearchModel> getSearchResult() {
        return searchResult;
    }

    @ApiModelProperty(name = "searchResult", example = "", dataType = "", value = "ผลลัพธ์ที่ค้นพบ", required = false)
    public void setSearchResult(List<DmsSearchModel> searchResult) {
        this.searchResult = searchResult;
    }
    

}
