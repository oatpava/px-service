package com.px.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OPAS
 */
@XmlRootElement(name = "Authentication")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel( description = "ข้อมูล Authentication" )
public class AuthenticationModel extends VersionModel{
    private static final long serialVersionUID = -6329377963723831808L;
    
    @XmlElement(name = "result")
    @Expose 
    @Since(1.0)
    private boolean result;

    public AuthenticationModel() {
    }

    public AuthenticationModel(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
