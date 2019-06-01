/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oat
 */
@XmlRootElement(name = "WfFolderContentAuthModel")
@ApiModel(description = "สิทธิ์ระบบย่อยหนังสือของแฟ้มทะเบียน")
public class WfFolderContentAuthModel extends VersionModel {

    private static final long serialVersionUID = -4092082930575355046L;

    @XmlElement(name = "id")
    @Expose
    private int id;

    @XmlElement(name = "name")
    @Expose
    private String name;

    @XmlElement(name = "subModuleAuthId")
    @Expose
    private int subModuleAuthId;

    @XmlElement(name = "auth")
    @Expose
    private boolean auth;

//    @XmlElement(name = "openContent")
//    @Expose
//    @Since(1.0)
//    private boolean openContent;
//
//    @XmlElement(name = "createContent")
//    @Expose
//    @Since(1.0)
//    private boolean createContent;
//
//    @XmlElement(name = "editContent")
//    @Expose
//    @Since(1.0)
//    private boolean editContent;
//
//    @XmlElement(name = "deleteContent")
//    @Expose
//    @Since(1.0)
//    private boolean deleteContent;
//
//    @XmlElement(name = "sendContent")
//    @Expose
//    @Since(1.0)
//    private boolean sendContent;
//
//    @XmlElement(name = "registerContent")
//    @Expose
//    @Since(1.0)
//    private boolean registerContent;
//
//    @XmlElement(name = "finishContent")
//    @Expose
//    @Since(1.0)
//    private boolean finishContent;
//
//    @XmlElement(name = "keepContent")
//    @Expose
//    @Since(1.0)
//    private boolean keepContent;
//
//    @XmlElement(name = "cancelContent")
//    @Expose
//    @Since(1.0)
//    private boolean cancelContent;
//
//    @XmlElement(name = "workflow")
//    @Expose
//    @Since(1.0)
//    private boolean workflow;
//
//    @XmlElement(name = "fileattach")
//    @Expose
//    @Since(1.0)
//    private boolean fileattach;
//
//    @XmlElement(name = "annotation")
//    @Expose
//    @Since(1.0)
//    private boolean annotation;
//
//    @XmlElement(name = "print")
//    @Expose
//    @Since(1.0)
//    private boolean print;
//
//    @XmlElement(name = "download")
//    @Expose
//    @Since(1.0)
//    private boolean download;
//
//    @XmlElement(name = "email")
//    @Expose
//    @Since(1.0)
//    private boolean email;
//
//    public WfFolderContentAuthModel() {
//    }
//
//    public boolean getOpenContent() {
//        return openContent;
//    }
//
//    public void setOpenContent(boolean openContent) {
//        this.openContent = openContent;
//    }
//
//    public boolean getCreateContent() {
//        return createContent;
//    }
//
//    public void setCreateContent(boolean createContent) {
//        this.createContent = createContent;
//    }
//
//    public boolean getEditContent() {
//        return editContent;
//    }
//
//    public void setEditContent(boolean editContent) {
//        this.editContent = editContent;
//    }
//
//    public boolean getDeleteContent() {
//        return deleteContent;
//    }
//
//    public void setDeleteContent(boolean deleteContent) {
//        this.deleteContent = deleteContent;
//    }
//
//    public boolean getSendContent() {
//        return sendContent;
//    }
//
//    public void setSendContent(boolean sendContent) {
//        this.sendContent = sendContent;
//    }
//
//    public boolean getRegisterContent() {
//        return registerContent;
//    }
//
//    public void setRegisterContent(boolean registerContent) {
//        this.registerContent = registerContent;
//    }
//
//    public boolean getFinishContent() {
//        return finishContent;
//    }
//
//    public void setFinishContent(boolean finishContent) {
//        this.finishContent = finishContent;
//    }
//
//    public boolean getKeepContent() {
//        return keepContent;
//    }
//
//    public void setKeepContent(boolean keepContent) {
//        this.keepContent = keepContent;
//    }
//
//    public boolean getCancelContent() {
//        return cancelContent;
//    }
//
//    public void setCancelContent(boolean cancelContent) {
//        this.cancelContent = cancelContent;
//    }
//
//    public boolean getWorkflow() {
//        return workflow;
//    }
//
//    public void setWorkflow(boolean workflow) {
//        this.workflow = workflow;
//    }
//
//    public boolean getFileattach() {
//        return fileattach;
//    }
//
//    public void setFileattach(boolean fileattach) {
//        this.fileattach = fileattach;
//    }
//
//    public boolean getAnnotation() {
//        return annotation;
//    }
//
//    public void setAnnotation(boolean annotation) {
//        this.annotation = annotation;
//    }
//
//    public boolean getPrint() {
//        return print;
//    }
//
//    public void setPrint(boolean print) {
//        this.print = print;
//    }
//
//    public boolean getDownload() {
//        return download;
//    }
//
//    public void setDownload(boolean download) {
//        this.download = download;
//    }
//
//    public boolean getEmail() {
//        return email;
//    }
//
//    public void setEmail(boolean email) {
//        this.email = email;
//    }
//    
//    public boolean[] getAllAuth() {
//        boolean[] auth = new boolean[15];
//        auth[0] = this.openContent;
//        auth[1] = this.createContent;
//        auth[2] = this.editContent;
//        auth[3] = this.deleteContent;
//        auth[4] = this.sendContent;
//        auth[5] = this.registerContent;
//        auth[6] = this.finishContent;
//        auth[7] = this.keepContent;
//        auth[8] = this.cancelContent;
//        auth[9] = this.workflow;
//        auth[10] = this.fileattach;
//        auth[11] = this.annotation;
//        auth[12] = this.print;
//        auth[13] = this.download;
//        auth[14] = this.email;
//        return auth;
//    }
//
//    public void setAllAuth(boolean[] auth) {
//        this.openContent = auth[0];
//        this.createContent = auth[1];
//        this.editContent = auth[2];
//        this.deleteContent = auth[3];
//        this.sendContent = auth[4];
//        this.registerContent = auth[5];
//        this.finishContent = auth[6];
//        this.keepContent = auth[7];
//        this.cancelContent = auth[8];
//        this.workflow = auth[9];
//        this.fileattach = auth[10];
//        this.annotation = auth[11];
//        this.print = auth[12];
//        this.download = auth[13];
//        this.email = auth[14];
//    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubModuleAuthId() {
        return subModuleAuthId;
    }

    public void setSubModuleAuthId(int subModuleAuthId) {
        this.subModuleAuthId = subModuleAuthId;
    }

    public boolean getAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

}
