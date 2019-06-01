
package com.px.mwp.service;

import com.px.mwp.entity.WorkflowType;
import org.apache.log4j.Logger;
import org.hibernate.boot.MetadataSources;


/**
 *
 * @author Mali
 */
public class MwpModuleEntityService {
    private static final Logger LOG = Logger.getLogger(MwpModuleEntityService.class.getName());
    private final int createdBy;

    public MwpModuleEntityService() {
        this.createdBy = 0;
    }

    public MetadataSources listCreateEntity(MetadataSources metadataSource) {
        //Add Entity 
        metadataSource.addAnnotatedClass(com.px.mwp.entity.InOutAssign.class);
        metadataSource.addAnnotatedClass(com.px.mwp.entity.InOutField.class);
        metadataSource.addAnnotatedClass(com.px.mwp.entity.Inbox.class);
        metadataSource.addAnnotatedClass(com.px.mwp.entity.Outbox.class);
        metadataSource.addAnnotatedClass(com.px.mwp.entity.StructureFolder.class);
        metadataSource.addAnnotatedClass(com.px.mwp.entity.UserProfileFolder.class);
        metadataSource.addAnnotatedClass(com.px.mwp.entity.Workflow.class);
        metadataSource.addAnnotatedClass(com.px.mwp.entity.WorkflowCc.class);
        metadataSource.addAnnotatedClass(com.px.mwp.entity.WorkflowTo.class);
        metadataSource.addAnnotatedClass(com.px.mwp.entity.WorkflowType.class);
        metadataSource.addAnnotatedClass(com.px.mwp.entity.PrivateGroup.class);
        metadataSource.addAnnotatedClass(com.px.mwp.entity.PrivateGroupUser.class);

        return metadataSource;
    }

    public void createDefaultData() {
        creaWorkflowType();
    }
    
    private void creaWorkflowType() {
        WorkflowTypeService workflowTypeService = new WorkflowTypeService();
        WorkflowType workflowType = workflowTypeService.getById(1);
        WorkflowType result = null;
        if (workflowType == null) {
            String name = "เพื่อโปรดพิจารณา,เพื่อโปรดพิจารณาอนุมัติ,เพื่อโปรดพิจารณาอนุญาต,เพื่อโปรดพิจารณาลงนาม,เพื่อโปรดพิจารณาประชาสัมพันธ์,"
                    + "เพื่อโปรดพิจารณาดำเนินการ,เพื่อพิจารณา,เพื่อโปรดทราบ,เพื่อทราบ,เพื่อส่งต่อไป,เพื่อเก็บรวบรวมเรื่อง,อื่นๆ...,อนุมัติ,ไม่อนุมัติ,ส่งใบลา,"
                    + "ดำเนินการต่อไป,เพื่อขออนุมัติ,ทราบ,พิจารณาดำเนินการ,ทราบและดำเนินการต่อไป";
            String[] nameArray = name.split(",");
            int i = 0;
            for (i = 0; i < nameArray.length; i++) {
                workflowType = new WorkflowType();
                workflowType.setCreatedBy(this.createdBy);
                workflowType.setWorkflowTypeTitle(nameArray[i]);
                result = workflowTypeService.create(workflowType);
                result.setOrderNo(result.getId());
                result = workflowTypeService.update(result);
            }
        }
    }
}
