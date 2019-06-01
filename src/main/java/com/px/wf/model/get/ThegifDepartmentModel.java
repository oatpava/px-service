
package com.px.wf.model.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.px.wf.model.post.ThegifDepartmentPostModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "ThegifDepartment")
@ApiModel(description = "โครงสร้างของการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ")
public class ThegifDepartmentModel extends ThegifDepartmentPostModel {

    private static final long serialVersionUID = 1290831931921242206L;
    
    @XmlElement(name = "id")
    @Expose 
    @Since(1.0) private int id;
    @Expose @Since(1.0) private int createdBy;
    @Expose @Since(1.0) private LocalDateTime createdDate;
    @Expose @Since(1.0) private float orderNo;
    @Expose @Since(1.0) private int removedBy;
    @Expose @Since(1.0) private LocalDateTime removedDate;
    @Expose @Since(1.0) private int updatedBy;
    @Expose @Since(1.0) private LocalDateTime updatedDate;
    @Expose @Since(1.0) private int nodeLevel;
    @Expose @Since(1.0) private String parentKey;
    
    public ThegifDepartmentModel() {
    }

    public ThegifDepartmentModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ApiModelProperty(position = 1, name = "id", value = "รหัสของโครงสร้าง", required = true)
    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setOrderNo(float orderNo) {
        this.orderNo = orderNo;
    }

    public void setRemovedBy(int removedBy) {
        this.removedBy = removedBy;
    }

    public void setRemovedDate(LocalDateTime removedDate) {
        this.removedDate = removedDate;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

}
