
package com.px.wf.entity;

import com.px.share.entity.BaseTreeEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Mali
 */
@Entity
@Table(name = "PC_THEGIF_DEPARTMENT")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "THEGIF_DEPARTMENT_ID"))
})
public class ThegifDepartment extends BaseTreeEntity {

    @Transient
    private static final long serialVersionUID = 6897225926368522449L;

    @Column(name = "THEGIF_DEPARTMENT_NAME", length = 255)
    private String thegifDepartmentName;
    
    @Column(name = "THEGIF_DEPARTMENT_CODE", length = 50)
    private String thegifDepartmentCode;

    @Column(name = "THEGIF_DEPARTMENT_SERVICE_NAME", length = 255)
    private String thegifDepartmentServiceName;
    
    public ThegifDepartment() {
    }

    public String getThegifDepartmentName() {
        return thegifDepartmentName;
    }

    public void setThegifDepartmentName(String thegifDepartmentName) {
        this.thegifDepartmentName = thegifDepartmentName;
    }

    public String getThegifDepartmentCode() {
        return thegifDepartmentCode;
    }

    public void setThegifDepartmentCode(String thegifDepartmentCode) {
        this.thegifDepartmentCode = thegifDepartmentCode;
    }

    public String getThegifDepartmentServiceName() {
        return thegifDepartmentServiceName;
    }

    public void setThegifDepartmentServiceName(String thegifDepartmentServiceName) {
        this.thegifDepartmentServiceName = thegifDepartmentServiceName;
    }
}
