/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.mwp.entity;

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
@Table(name = "PC_PRIVATE_GROUP")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "ID"))
})
public class PrivateGroup extends BaseEntity {

    private static final long serialVersionUID = 9183841376511427647L;

    @Column(name = "OWNER_ID", columnDefinition = "int default '0'")
    private int ownerId;

    @Column(name = "GROUP_NAME", nullable = false, length = 1000)
    private String groupName;

    @Column(name = "TYPE", columnDefinition = "int default '0'")
    private int type;

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getType() {
        return type;
    }

    public void setTYpe(int type) {
        this.type = type;
    }
}
