
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
@XmlRootElement(name = "ThegifDocFileModel_andPathFile")
@ApiModel(description = "เอกสารของงการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐและเส้นทางเชื่อมต่อเอกสาร")
public class ThegifDocFileModel_andPathFile extends ThegifDocFileModel{

    private static final long serialVersionUID = -9024074483714984993L;
    
    @Expose 
    @Since(1.0) private String pathFile;

    public ThegifDocFileModel_andPathFile() {
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }
    
}
