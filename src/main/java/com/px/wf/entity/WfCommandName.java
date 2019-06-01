
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
@Table(name = "PC_WF_COMMAND_NAME") 
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "WF_COMMAND_NAME_ID"))
})
public class WfCommandName extends BaseEntity {

    @Transient
    private static final long serialVersionUID = -2452707511321269886L;
    
    @Column(name = "WF_COMMAND_NAME_NAME", nullable = true, length = 255)
    private String wfCommandNameName;

    public String getWfCommandNameName() {
        return wfCommandNameName;
    }

    public void setWfCommandNameName(String wfCommandNameName) {
        this.wfCommandNameName = wfCommandNameName;
    }

}
