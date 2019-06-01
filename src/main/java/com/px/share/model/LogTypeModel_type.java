package com.px.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "LogTypeModel_type")
@ApiModel( description = "รายการประเภทของ log ต่างๆ " )
public class LogTypeModel_type extends VersionModel{
    
    private static final long serialVersionUID = -7915046483905612504L;
    @Since(1.0)
    @XmlElement(name = "typeName")
    @Expose
    private String typeName;
    @Since(1.0)
    @XmlElement(name = "type")
    @Expose
    private int type;

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setType(int type) {
        this.type = type;
    }
}
