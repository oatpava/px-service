
package com.px.wf.model.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.share.model.VersionModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TUMKUNG
 */
@XmlRootElement(name = "thegif")
@ApiModel(description = "การสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ")
public class ThegifStatusSendModel extends ThegifPostModel {

    private static final long serialVersionUID = -1277475256903406733L;

    @XmlElement(name = "id")
    @Expose @Since(1.0) private int id;
    @Expose @Since(1.0) private int createdBy;
    @Expose @Since(1.0) private String createdDate;
    @Expose @Since(1.0) private float orderNo;
    @Expose @Since(1.0) private int removedBy;
    @Expose @Since(1.0) private String removedDate;
    @Expose @Since(1.0) private int updatedBy;
    @Expose @Since(1.0) private String updatedDate;
    
    @XmlElement(name = "thegifStatusSend")
    @ApiParam(name = "thegifStatusSend", value = "สถานะการส่ง", required = false)
    @FormParam("thegifStatusSend")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifStatusSend;
    
    @XmlElement(name = "thegifAcceptNo")
    @ApiParam(name = "thegifAcceptNo", value = "", required = false)
    @FormParam("thegifAcceptNo")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifAcceptNo;

    @XmlElement(name = "thegifDepName")
    @ApiParam(name = "thegifDepName", value = "", required = false)
    @FormParam("thegifDepName")
    @Size(max = 500)
    @Since(1.0)
    @Expose private String thegifDepName;    

    @XmlElement(name = "thegifStatusAcceptDate")
    @ApiParam(name = "thegifStatusAcceptDate", value = "", required = false)
    @FormParam("thegifStatusAcceptDate")
    @Size(max = 255)
    @Since(1.0)
    @Expose private String thegifStatusAcceptDate;
    
    public String getThegifStatusSend() {
        return thegifStatusSend;
    }

    public void setThegifStatusSend(String thegifStatusSend) {
        this.thegifStatusSend = thegifStatusSend;
    }

    public String getThegifAcceptNo() {
        return thegifAcceptNo;
    }

    public void setThegifAcceptNo(String thegifAcceptNo) {
        this.thegifAcceptNo = thegifAcceptNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public float getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(float orderNo) {
        this.orderNo = orderNo;
    }

    public int getRemovedBy() {
        return removedBy;
    }

    public void setRemovedBy(int removedBy) {
        this.removedBy = removedBy;
    }

    public String getRemovedDate() {
        return removedDate;
    }

    public void setRemovedDate(String removedDate) {
        this.removedDate = removedDate;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getThegifDepName() {
        return thegifDepName;
    }

    public void setThegifDepName(String thegifDepName) {
        this.thegifDepName = thegifDepName;
    }

    public String getThegifStatusAcceptDate() {
        return thegifStatusAcceptDate;
    }

    public void setThegifStatusAcceptDate(String thegifStatusAcceptDate) {
        this.thegifStatusAcceptDate = thegifStatusAcceptDate;
    }
    
}
