
package com.px.mwp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mali
 */
@XmlRootElement(name = "WorkflowModel_groupFlow")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "รายการผังการไหลแบบรูปภาพ")
public class WorkflowModel_groupFlow implements Serializable {

    private static final long serialVersionUID = -2476539195763612504L;
    
    @XmlElement(name = "topic")
    @Expose @Since(1.0) private String topic;
    @XmlElement(name = "workflowModel_groupNodeFlow")
    @Expose @Since(1.0) private List<WorkflowModel_groupNodeFlow> workflowModel_groupNodeFlow;
    @XmlElement(name = "workflowModel_groupLinkFlow")
    @Expose @Since(1.0) private List<WorkflowModel_groupLinkFlow> workflowModel_groupLinkFlow;
    
    public WorkflowModel_groupFlow() {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<WorkflowModel_groupNodeFlow> getWorkflowModel_groupNodeFlow() {
        return workflowModel_groupNodeFlow;
    }

    public void setWorkflowModel_groupNodeFlow(List<WorkflowModel_groupNodeFlow> workflowModel_groupNodeFlow) {
        this.workflowModel_groupNodeFlow = workflowModel_groupNodeFlow;
    }

    public List<WorkflowModel_groupLinkFlow> getWorkflowModel_groupLinkFlow() {
        return workflowModel_groupLinkFlow;
    }

    public void setWorkflowModel_groupLinkFlow(List<WorkflowModel_groupLinkFlow> workflowModel_groupLinkFlow) {
        this.workflowModel_groupLinkFlow = workflowModel_groupLinkFlow;
    }

}
