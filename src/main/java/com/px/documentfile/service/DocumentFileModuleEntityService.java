package com.px.documentfile.service;

import com.px.dms.service.*;
import org.apache.log4j.Logger;
import org.hibernate.boot.MetadataSources;

/**
 * For create DMS Module Entity.
 *
 * @author Mali
 */
public class DocumentFileModuleEntityService {

    private static final Logger LOG = Logger.getLogger(DocumentFileModuleEntityService.class.getName());
    private final int createdBy;

    public DocumentFileModuleEntityService() {
        this.createdBy = 0;
    }

    public MetadataSources listCreateEntity(MetadataSources metadataSource) {
        //Add Entity 

        metadataSource.addAnnotatedClass(com.px.documentfile.entity.DocumentFile.class);

        return metadataSource;
    }

    public void createDefaultData() {

    }

    public void createDocumentType() {

    }

    public void createDocumentTypeDrtail() {

    }

}
