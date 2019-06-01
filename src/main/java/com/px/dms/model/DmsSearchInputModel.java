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
@XmlRootElement(name = "DmsSearchInput")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ข้อมูลสำหรับค้นหาเอกสาร")
public class DmsSearchInputModel extends VersionModel {

    private static final long serialVersionUID = 4573554434763754964L;
    
     public DmsSearchInputModel() {
    }
    
    @XmlElement(name = "form")
    @ApiParam(name = "form", example = "", value = "ข้อมูลค้นหา", defaultValue = "", required = false)
    @Since(1.0)
    @Expose 
    private List<DmsSearchFormModel> form;
    
    @XmlElement(name = "fetchSource")
    @ApiParam(name = "fetchSource", example = "", value = "ข้อมูลแสดง", defaultValue = "", required = false)
    @Since(1.0)
    @Expose 
    private List<String> fetchSource;
    
    @XmlElement(name = "order")
    @ApiParam(name = "order", example = "asc", value = "เรียงลำดับข้อมูล", defaultValue = "asc", required = false)
    @Since(1.0)
    @Expose 
    private String order;
    
    @XmlElement(name = "from")
    @ApiParam(name = "from", example = "0", value = "ตำแหน่งเริ่มต้น", defaultValue = "0", required = true)
    @Since(1.0)
    @Expose
    private int from;
    
    @XmlElement(name = "size")
    @ApiParam(name = "size", example = "0", value = "จำนวน", defaultValue = "0", required = true)
    @Since(1.0)
    @Expose
    private int size;
    
    @XmlElement(name = "defaultSymbolForSpace")
    @ApiParam(name = "defaultSymbolForSpace", example = ".หรือ.", value = "ตรรกะสำหรับเว้นวรรค", defaultValue = ".หรือ.", required = true)
    @Since(1.0)
    @Expose 
    private String defaultSymbolForSpace;
    
    @XmlElement(name = "symbolAnd")
    @ApiParam(name = "symbolAnd", example = ".และ.", value = "สัญลักษณ์แทนตรรกะและ", defaultValue = ".และ.", required = true)
    @Since(1.0)
    @Expose 
    private String symbolAnd;
    
    @XmlElement(name = "symbolOr")
    @ApiParam(name = "symbolOr", example = ".หรือ.", value = "สัญลักษณ์แทนตรรกะหรือ", defaultValue = ".หรือ.", required = true)
    @Since(1.0)
    @Expose 
    private String symbolOr;
    
    @XmlElement(name = "symbolNot")
    @ApiParam(name = "symbolNot", example = ".ไม่.", value = "สัญลักษณ์แทนตรรกะไม่", defaultValue = ".ไม่.", required = true)
    @Since(1.0)
    @Expose 
    private String symbolNot;
    
    @XmlElement(name = "symbolWith")
    @ApiParam(name = "symbolWith", example = ".ตามด้วย.", value = "สัญลักษณ์แทนตรรกะตามด้วย", defaultValue = ".ตามด้วย.", required = true)
    @Since(1.0)
    @Expose 
    private String symbolWith;
    
    @XmlElement(name = "preHighlightTag")
    @ApiParam(name = "preHighlightTag", example = "<mark>", value = "Tag นำหน้าข้อความที่จะ highlight", defaultValue = "<mark>", required = false)
    @Since(1.0)
    @Expose 
    private String preHighlightTag;
    
    @XmlElement(name = "postHighlightTag")
    @ApiParam(name = "postHighlightTag", example = "</mark>", value = "Tag ตามหลังข้อความที่จะ highlight", defaultValue = "</mark>", required = false)
    @Since(1.0)
    @Expose 
    private String postHighlightTag;
    

    public List<DmsSearchFormModel> getForm() {
        return form;
    }

    @ApiModelProperty(name = "form", example = "", dataType = "", value = "ข้อมูลค้นหา", required = false)
    public void setForm(List<DmsSearchFormModel> form) {
        this.form = form;
    }

    public List<String> getFetchSource() {
        return fetchSource;
    }

    @ApiModelProperty(name = "fetchSource", example = "", dataType = "", value = "ข้อมูลแสดง", required = false)
    public void setFetchSource(List<String> fetchSource) {
        this.fetchSource = fetchSource;
    }

    public String getOrder() {
        return order;
    }

    @ApiModelProperty(name = "order", example = "asc", dataType = "string", value = "เรียงลำดับข้อมูล", required = false)
    public void setOrder(String order) {
        this.order = order;
    }

    public int getFrom() {
        return from;
    }

    @ApiModelProperty(name = "from", example = "0", dataType = "int", value = "ตำแหน่งเริ่มต้น", required = true)
    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    @ApiModelProperty(name = "size", example = "0", dataType = "int", value = "จำนวน", required = true)
    public void setSize(int size) {
        this.size = size;
    }

    public String getDefaultSymbolForSpace() {
        return defaultSymbolForSpace;
    }

    @ApiModelProperty(name = "defaultSymbolForSpace", example = ".หรือ.", dataType = "string", value = "ตรรกะสำหรับเว้นวรรค", required = true)
    public void setDefaultSymbolForSpace(String defaultSymbolForSpace) {
        this.defaultSymbolForSpace = defaultSymbolForSpace;
    }

    public String getSymbolAnd() {
        return symbolAnd;
    }

    @ApiModelProperty(name = "symbolAnd", example = ".และ.", dataType = "string", value = "สัญลักษณ์แทนตรรกะและ", required = true)
    public void setSymbolAnd(String symbolAnd) {
        this.symbolAnd = symbolAnd;
    }

    public String getSymbolOr() {
        return symbolOr;
    }

    @ApiModelProperty(name = "symbolOr", example = ".หรือ.", dataType = "string", value = "สัญลักษณ์แทนตรรกะหรือ", required = true)
    public void setSymbolOr(String symbolOr) {
        this.symbolOr = symbolOr;
    }

    public String getSymbolNot() {
        return symbolNot;
    }

    @ApiModelProperty(name = "symbolNot", example = ".ไม่.", dataType = "string", value = "สัญลักษณ์แทนตรรกะไม่", required = true)
    public void setSymbolNot(String symbolNot) {
        this.symbolNot = symbolNot;
    }

    public String getSymbolWith() {
        return symbolWith;
    }

    @ApiModelProperty(name = "symbolWith", example = ".ตามด้วย.", dataType = "string", value = "สัญลักษณ์แทนตรรกะตามด้วย", required = true)
    public void setSymbolWith(String symbolWith) {
        this.symbolWith = symbolWith;
    }

    public String getPreHighlightTag() {
        return preHighlightTag;
    }

    @ApiModelProperty(name = "preHighlightTag", example = "<mark>", dataType = "string", value = "Tag นำหน้าข้อความที่จะ highlight", required = false)
    public void setPreHighlightTag(String preHighlightTag) {
        this.preHighlightTag = preHighlightTag;
    }

    public String getPostHighlightTag() {
        return postHighlightTag;
    }

    @ApiModelProperty(name = "postHighlightTag", example = "</mark>", dataType = "string", value = "Tag ตามหลังข้อความที่จะ highlight", required = false)
    public void setPostHighlightTag(String postHighlightTag) {
        this.postHighlightTag = postHighlightTag;
    }
    

}
