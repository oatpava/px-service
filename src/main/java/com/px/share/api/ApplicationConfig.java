package com.px.share.api;

import io.swagger.jaxrs.config.BeanConfig;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author OPAS
 */
@ApplicationPath("api")
public class ApplicationConfig extends Application {
    private static final Logger LOG = Logger.getLogger(ApplicationConfig.class.getName());
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        try {
            String path = "../../../../../../build.prop";
            Properties props;
            try (InputStream stream = getClass().getResourceAsStream(path)) {
                props = new Properties();
                props.load(stream);
            }
            String version = (String) props.get("build.revision");

            resources.add(com.px.share.filter.RequestFilter.class);
            resources.add(com.px.share.filter.ResponseFilter.class);

            resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
            resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

            BeanConfig beanConfig = new BeanConfig();
            beanConfig.setVersion(version);
            beanConfig.setSchemes(new String[]{"http"});
    //        beanConfig.setHost("localhost:81"); 
            beanConfig.setBasePath("/pxservice-dpim/api");
            beanConfig.setResourcePackage("com.px.share.api,com.px.admin.api,com.px.authority.api,com.px.dms.api,com.px.documentfile.api,com.px.menu.api,com.px.wf.api,com.px.mwp.api");
//            beanConfig.setResourcePackage("com.px.share.api,com.px.admin.api,com.px.authority.api,com.px.menu.api,com.px.wf.api,com.px.mwp.api");
            beanConfig.setScan(true);
            
            // Add additional features such as support for Multipart.
            resources.add(MultiPartFeature.class);
            
            addRestResourceClasses(resources);
        }catch (IOException ex) {
            LOG.error("Exception = "+ex.getMessage());
//            responseData.put("errorMessage", ex.getMessage());
        }        
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.px.admin.api.AdResource.class);
        resources.add(com.px.admin.api.HolidayResource.class);
        resources.add(com.px.admin.api.LookupDetailResource.class);
        resources.add(com.px.admin.api.LookupResource.class);
        resources.add(com.px.admin.api.ModuleResource.class);
        resources.add(com.px.admin.api.OrganizeResource.class);
        resources.add(com.px.admin.api.PositionResource.class);
        resources.add(com.px.admin.api.PositionTypeResource.class);
        resources.add(com.px.admin.api.ProvinceResource.class);
        resources.add(com.px.admin.api.StructureResource.class);
        resources.add(com.px.admin.api.SubmoduleResource.class);
        resources.add(com.px.admin.api.TitleResource.class);
        resources.add(com.px.admin.api.UserParamResource.class);
        resources.add(com.px.admin.api.UserProfileResource.class);
        resources.add(com.px.admin.api.UserProfileTypeResource.class);
        resources.add(com.px.admin.api.UserResource.class);
        resources.add(com.px.admin.api.UserStatusResource.class);
        resources.add(com.px.admin.api.UserTypeOrderResource.class);
        resources.add(com.px.admin.api.WordBriefDetailResource.class);
        resources.add(com.px.admin.api.WordBriefResource.class);
        resources.add(com.px.authority.api.AuthResource.class);
        resources.add(com.px.authority.api.ModuleUserAuthResource.class);
        resources.add(com.px.authority.api.SubmoduleAuthResource.class);
        resources.add(com.px.authority.api.SubmoduleAuthTemplateResource.class);
        resources.add(com.px.authority.api.SubmoduleAuthTemplateValResource.class);
        resources.add(com.px.authority.api.SubmoduleUserAuthResource.class);
        resources.add(com.px.dms.api.DmsDocumentResource.class);
        resources.add(com.px.dms.api.DmsFieldResource.class);
        resources.add(com.px.dms.api.DmsFolderResource.class);
        resources.add(com.px.dms.api.DmsSearchResource.class);
        resources.add(com.px.dms.api.DocumentTypeDetailResource.class);
        resources.add(com.px.dms.api.DocumentTypeResource.class);
        resources.add(com.px.dms.api.ElasticSearchResource.class);
        resources.add(com.px.dms.api.WfDocumentTypeResource.class);
        resources.add(com.px.dms.api.borrowResource.class);
        resources.add(com.px.documentfile.api.DocumentFileResource.class);
        resources.add(com.px.menu.api.MenuResource.class);
        resources.add(com.px.menu.api.MenuSubmoduleAuthResource.class);
        resources.add(com.px.menu.api.MenuTypeResource.class);
        resources.add(com.px.mwp.api.ElasticSearchResource.class);
        resources.add(com.px.mwp.api.InOutAssignResource.class);
        resources.add(com.px.mwp.api.InboxResource.class);
        resources.add(com.px.mwp.api.InoutFieldResource.class);
        resources.add(com.px.mwp.api.OutboxResource.class);
        resources.add(com.px.mwp.api.PrivateGroupResource.class);
        resources.add(com.px.mwp.api.StructureFolderResource.class);
        resources.add(com.px.mwp.api.UserProfileFolderResource.class);
        resources.add(com.px.mwp.api.WorkflowCcResource.class);
        resources.add(com.px.mwp.api.WorkflowResource.class);
        resources.add(com.px.mwp.api.WorkflowToResource.class);
        resources.add(com.px.mwp.api.WorkflowTypeResource.class);
        resources.add(com.px.share.api.DynamicJasperReportResource.class);
        resources.add(com.px.share.api.FileAttachResource.class);
        resources.add(com.px.share.api.LevelBarResource.class);
        resources.add(com.px.share.api.LogDataResource.class);
        resources.add(com.px.share.api.MonthResource.class);
        resources.add(com.px.share.api.NotificationsResource.class);
        resources.add(com.px.share.api.ParamResource.class);
        resources.add(com.px.share.api.RecycleBinResource.class);
        resources.add(com.px.share.api.SearchAllModuleResource.class);
        resources.add(com.px.share.api.TempTableResource.class);
        resources.add(com.px.share.filter.RequestFilter.class);
        resources.add(com.px.wf.api.FolderPropertyResource.class);
        resources.add(com.px.wf.api.ImportResource.class);
        resources.add(com.px.wf.api.ThegifDepartmentResource.class);
        resources.add(com.px.wf.api.ThegifDocFileResource.class);
        resources.add(com.px.wf.api.ThegifResource.class);
        resources.add(com.px.wf.api.WfAbsentResource.class);
        resources.add(com.px.wf.api.WfCommandNameResource.class);
        resources.add(com.px.wf.api.WfCommandTypeResource.class);
        resources.add(com.px.wf.api.WfContentResource.class);
        resources.add(com.px.wf.api.WfContentType2Resource.class);
        resources.add(com.px.wf.api.WfContentTypeResource.class);
        resources.add(com.px.wf.api.WfDocumentTypeResource.class);
        resources.add(com.px.wf.api.WfFieldResource.class);
        resources.add(com.px.wf.api.WfFolderResource.class);
        resources.add(com.px.wf.api.WfRecordResource.class);
        resources.add(com.px.wf.api.WfReserveContentNoResource.class);
    }
    
}
