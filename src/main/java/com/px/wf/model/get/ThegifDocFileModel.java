
package com.px.wf.model.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.wf.model.post.ThegifDocFilePostModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "ThegifDocFile")
@ApiModel(description = "เอกสารของการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ")
public class ThegifDocFileModel extends ThegifDocFilePostModel {

    private static final long serialVersionUID = -4332133567976937352L;
    
    @XmlElement(name = "id")
    @Expose 
    @Since(1.0) private int id;
    @Expose @Since(1.0) private int createdBy;
    @Expose @Since(1.0) private String createdDate;
    @Expose @Since(1.0) private float orderNo;
    @Expose @Since(1.0) private int removedBy;
    @Expose @Since(1.0) private String removedDate;
    @Expose @Since(1.0) private int updatedBy;
    @Expose @Since(1.0) private String updatedDate;
    
    public ThegifDocFileModel() {
    }

    public ThegifDocFileModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(position = 1, name = "id", value = "รหัสการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ", required = true)
    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setOrderNo(float orderNo) {
        this.orderNo = orderNo;
    }

    public void setRemovedBy(int removedBy) {
        this.removedBy = removedBy;
    }

    public void setRemovedDate(String removedDate) {
        this.removedDate = removedDate;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

}
