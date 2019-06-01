package com.px.share.model;

import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author OPAS
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MasterModel extends VersionModel{
    
    private static final long serialVersionUID = -6780452050287553045L;
    
    @XmlElement(name = "type")
    @ApiParam(name = "type", example = "ประเภทของ master", value = "ประเภทของ master", defaultValue = "ประเภทของ master", required = false)
    @Size(max = 50)
    @Expose 
    private String type;

    public MasterModel() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
