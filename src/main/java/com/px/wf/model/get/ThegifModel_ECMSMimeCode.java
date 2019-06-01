
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
@ApiModel(description = "ข้อมูลประเภทไฟล์เอกสาร")
@XmlRootElement(name = "ThegifModel_ECMSMimeCode")
public class ThegifModel_ECMSMimeCode {  
    
    @Expose @Since(1.0) private String contentType;
    @Expose @Since(1.0) private String fileExtension;
    
    public ThegifModel_ECMSMimeCode() {
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
    
}
