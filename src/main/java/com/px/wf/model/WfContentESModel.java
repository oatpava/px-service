/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.FileAttachWfSearchModel;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oat
 */
@XmlRootElement(name = "WfContent Elastic Search")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "ค้นหาหนังสือ")
public class WfContentESModel extends VersionModel {

    private static final long serialVersionUID = 1074108769088963092L;

    @XmlElement(name = "folderId")
    @ApiParam(name = "folderId", value = "folderId", required = true)
    @Since(1.0)
    @Expose
    private int folderId;

    @XmlElement(name = "linkId")
    @ApiParam(name = "linkId", value = "linkId", required = true)
    @Since(1.0)
    @Expose
    private int linkId;

    @XmlElement(name = "searchId")
    @ApiParam(name = "searchId", value = "searchId", defaultValue = "0", required = false)
    @Since(1.0)
    @Expose
    private String searchId;

    @XmlElement(name = "fileAttachs")
    @ApiParam(name = "fileAttachs", value = "fileAttachs", required = false)
    @Since(1.0)
    @Expose
    private List<FileAttachWfSearchModel> fileAttachs;

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public List<FileAttachWfSearchModel> getFileAttachs() {
        return fileAttachs;
    }

    public void setFileAttachs(List<FileAttachWfSearchModel> fileAttachs) {
        this.fileAttachs = fileAttachs;
    }

}
