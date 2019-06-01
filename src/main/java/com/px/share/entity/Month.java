package com.px.share.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Opas
 */
@Entity
@Table(name = "PC_MONTH")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="MONTH_ID"))
})
public class Month extends BaseEntity{
    @Transient
    private static final long serialVersionUID = 2958430810382591506L;
    
    @Column(name="ORDER_FISCAL", nullable = false)
    private Integer orderFiscal;
    
    @Column(name="THAI_NAME", nullable = false,length = 100)
    private String thaiName;
    
    @Column(name="THAI_NAME_ABBR", nullable = false,length = 100)
    private String thaiNameAbbr;
    
    @Column(name="ENG_NAME", nullable = false,length = 100)
    private String engName;
    
    @Column(name="ENG_NAME_ABBR", nullable = false,length = 100)
    private String engNameAbbr;

    public Month() {
    }

    public Integer getOrderFiscal() {
        return orderFiscal;
    }

    public void setOrderFiscal(Integer orderFiscal) {
        this.orderFiscal = orderFiscal;
    }

    public String getThaiName() {
        return thaiName;
    }

    public void setThaiName(String thaiName) {
        this.thaiName = thaiName;
    }

    public String getThaiNameAbbr() {
        return thaiNameAbbr;
    }

    public void setThaiNameAbbr(String thaiNameAbbr) {
        this.thaiNameAbbr = thaiNameAbbr;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getEngNameAbbr() {
        return engNameAbbr;
    }

    public void setEngNameAbbr(String engNameAbbr) {
        this.engNameAbbr = engNameAbbr;
    }

}
