
package com.px.wf.model.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Mali
 */
@MappedSuperclass
@XmlRootElement(name = "ThegifDepartmentModel_HasChild")
@ApiModel(description = "เช็คลูกของหน่วยงานภายใต้โครงสร้างของการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ")
public class ThegifDepartmentModel_hasChild extends ThegifDepartmentModel{

    private static final long serialVersionUID = -6144296836869373052L;
    
    @Expose 
    @Since(1.0) private boolean hasChild;

    public ThegifDepartmentModel_hasChild() {
    }

    public boolean isHasChild() {
        return hasChild;
    }
    
    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }
}
