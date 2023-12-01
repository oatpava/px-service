package com.px.admin.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Oat
 */
@Entity
@Table(name = "PC_PROVINCE")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "PROVINCE_ID"))
})
public class Province extends BaseEntity {

    @Column(name = "CODE", nullable = false, length = 10)
    private String code;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
