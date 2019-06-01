package com.px.wf.model.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import java.util.List;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@MappedSuperclass
@ApiModel( description = "รายการของการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ" )
@XmlRootElement(name = "ThegifModel_groupShowList2")
public class ThegifModel_groupShowList2 extends ThegifModel{
    @Expose @Since(1.0) private List<ThegifModel_groupShowList> td;
    
    public ThegifModel_groupShowList2() {
    }
    
    public List<ThegifModel_groupShowList> getTd() {
        return td;
    }

    public void setTd(List<ThegifModel_groupShowList> td) {
        this.td = td;
    }
    
}
