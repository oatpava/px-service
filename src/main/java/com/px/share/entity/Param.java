package com.px.share.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author OPAS
 */
@Entity
@Table(name = "PC_PARAM")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="PARAM_ID"))
})
public class Param extends BaseEntity{
    @Transient
    private static final long serialVersionUID = 7248392570740497162L;
        
    @Column(name="PARAM_NAME", nullable = false,length = 100)
    private String paramName = "";   
    
    @Column(name="PARAM_VALUE", nullable = false,length = 100)
    private String paramValue = "";

    public Param() {
    }

    public Param(String paramName, String paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
    
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(this.paramName);
        result.append(" ");
        result.append(this.paramValue);
        return result.toString();
    }
}
