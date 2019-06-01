package com.px.wf.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "WfContentModel_groupWfContentAndWorkflowfinish")
@ApiModel(description = "รายการของหนังสือซึ่งจัดกลุ่มโดยหนังสือและเรื่องเสร็จ")
public class WfContentModel_groupWfContentAndWorkflowfinish extends WfContentModel {

    private static final long serialVersionUID = 7681648225614317621L;

    @XmlElement(name = "hasFinish")
    @Expose
    @Since(1.0)
    private Boolean hasFinish;

    @XmlElement(name = "isCanceled")
    @Expose
    @Since(1.0)
    private Boolean isCanceled;//oat-add

    @XmlElement(name = "status")
    @Expose
    @Since(1.0)
    private int status;//oat-add

    @XmlElement(name = "commandTypeName")
    @Expose
    @Since(1.0)
    private String commandTypeName;

    @XmlElement(name = "numFileAttach")
    @Expose
    @Since(1.0)
    private int numFileAttach;//oat-add

    @XmlElement(name = "finishByS")
    @Expose
    @Since(1.0)
    private int finishByS;//oat-add

    @XmlElement(name = "cancelBy")
    @Expose
    @Since(1.0)
    private int cancelBy;//oat-add

    @XmlElement(name = "isKeeped")
    @Expose
    @Since(1.0)
    private Boolean isKeeped;//oat-add

    public WfContentModel_groupWfContentAndWorkflowfinish() {
    }

    public Boolean getHasFinish() {
        return hasFinish;
    }

    public void setHasFinish(Boolean hasFinish) {
        this.hasFinish = hasFinish;
    }

    public Boolean getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(Boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCommandTypeName() {
        return commandTypeName;
    }

    public void setCommandTypeName(String commandTypeName) {
        this.commandTypeName = commandTypeName;
    }

    public int getNumFileAttach() {
        return numFileAttach;
    }

    public void setNumFileAttach(int numFileAttach) {
        this.numFileAttach = numFileAttach;
    }

    public int getFinishByS() {
        return finishByS;
    }

    public void setFinishByS(int finishByS) {
        this.finishByS = finishByS;
    }

    public int getCancelBy() {
        return cancelBy;
    }

    public void setCancelBy(int cancelBy) {
        this.cancelBy = cancelBy;
    }

    public Boolean getIsKeeped() {
        return isCanceled;
    }

    public void setIsKeeped(Boolean isKeeped) {
        this.isKeeped = isKeeped;
    }

}
