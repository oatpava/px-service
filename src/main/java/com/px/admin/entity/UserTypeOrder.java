package com.px.admin.entity;

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
@Table(name = "PC_USER_TYPE_ORDER")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="USER_TYPE_ORDER_ID"))
})
public class UserTypeOrder extends BaseEntity{
    @Transient
    private static final long serialVersionUID = 4689135833015532694L;
    
    @Column(name="USER_TYPE_ORDER_NAME", nullable = false,length = 100)
    private String userTypeOrderName;

    public UserTypeOrder() {
    }

    public UserTypeOrder(String userTypeOrderName, Integer createdBy) {
        super(createdBy);
        this.userTypeOrderName = userTypeOrderName;
    }

    public String getUserTypeOrderName() {
        return userTypeOrderName;
    }

    public void setUserTypeOrderName(String userTypeOrderName) {
        this.userTypeOrderName = userTypeOrderName;
    }
    
    
}
