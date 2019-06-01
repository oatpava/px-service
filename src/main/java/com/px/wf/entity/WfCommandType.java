package com.px.wf.entity;

import com.px.share.entity.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Mali
 */
@Entity
@Table(name = "PC_WF_COMMAND_TYPE")
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "WF_COMMAND_TYPE_ID"))
})
public class WfCommandType extends BaseEntity {

    @Transient
    private static final long serialVersionUID = 8984482987036135516L;
    
    @Column(name = "WF_COMMAND_TYPE_NAME", nullable = true, length = 255)
    private String commandTypeName;

    public String getCommandTypeName() {
        return commandTypeName;
    }

    public void setCommandTypeName(String commandTypeName) {
        this.commandTypeName = commandTypeName;
    }
}
