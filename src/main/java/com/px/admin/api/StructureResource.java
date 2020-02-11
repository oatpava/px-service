package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Structure;
import com.px.admin.entity.User;
import com.px.admin.entity.UserProfile;
import com.px.admin.entity.VStructure;
import com.px.admin.model.StructureConvertModel;
import com.px.admin.model.StructureModel;
import com.px.admin.model.UserProfileModel;
import com.px.admin.model.VStructureModel;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.admin.service.UserService;
import com.px.admin.service.VStructureService;
import com.px.mwp.entity.StructureFolder;
import com.px.mwp.entity.UserProfileFolder;
import com.px.mwp.service.StructureFolderService;
import com.px.mwp.service.UserProfileFolderService;
import com.px.share.entity.Param;
import com.px.share.model.AuthenticationModel;
import com.px.share.model.ListOptionModel;
import com.px.share.model.ListReturnModel;
import com.px.share.model.VersionModel;
import com.px.share.service.ParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.log4j.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author OPAS
 */
@Api(value = "Structure โครงสร้างหน่วยงาน")
@Path("v1/structures")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class StructureResource {

    private static final Logger LOG = Logger.getLogger(StructureResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create Structure",
            notes = "สร้างข้อมูลโครงสร้างหน่วยงาน",
            response = StructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Structure created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            StructureModel structureModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(structureModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            Structure structure = new Structure();
            structure.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            structure.setStructureDetail(structureModel.getDetail());
            structure.setStructureShortName(structureModel.getShortName());
            structure.setStructureName(structureModel.getName());
            structure.setParentId(structureModel.getParentId());
            structure.setStructureCode(structureModel.getCode());
            StructureService structureService = new StructureService();
            structure = structureService.create(structure);
            responseData.put("data", structureService.tranformToModel(structure));
            responseData.put("message", "Structure created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);

            //LogData For Create
            structureService.saveLogForCreate(structure, httpHeaders.getHeaderString("clientIp"));
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get Structure by id",
            notes = "ขอข้อมูลโครงสร้างหน่วยงาน ด้วย รหัสโครงสร้างหน่วยงาน",
            response = StructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure by id success.")
        ,
        @ApiResponse(code = 404, message = "Structure by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสโครงสร้างหน่วยงาน", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("getById...");
        LOG.debug("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Structure by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            StructureService structureService = new StructureService();
            Structure structure = structureService.getById(id);
            if (structure != null) {
                status = Response.Status.OK;
                responseData.put("data", structureService.tranformToModel(structure));
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update Structure.",
            notes = "แก้ไขข้อมูลโครงสร้างหน่วยงาน",
            response = StructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure updeted by id success.")
        ,@ApiResponse(code = 404, message = "Structure by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @ApiParam(name = "id", value = "รหัสโครงสร้างหน่วยงาน", required = true)
            @PathParam("id") int id,
            StructureModel structureModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(structureModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Structure by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            StructureService structureService = new StructureService();
            Structure structure = structureService.getById(id);
            Structure structureUpdate = null;
            if (structure != null) {
                structure.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                if (structureModel.getParentId() != 0) {
                    structure.setParentId(structureModel.getParentId());
                    structureUpdate = structureService.updateByParentId(structure);
                    if (!structureModel.getCode().equals(structure.getStructureCode())
                            || !structureModel.getName().equals(structure.getStructureName())
                            || !structureModel.getDetail().equals(structure.getStructureDetail())) {
                        structure.setStructureDetail(structureModel.getDetail());
                        structure.setStructureName(structureModel.getName());
                        structure.setStructureShortName(structureModel.getShortName());
                        structure.setStructureCode(structureModel.getCode());
                        structureUpdate = structureService.update(structure);
                    }
                } else {
                    structure.setStructureDetail(structureModel.getDetail());
                    structure.setStructureName(structureModel.getName());
                    structure.setStructureShortName(structureModel.getShortName());
                    structure.setStructureCode(structureModel.getCode());
                    structureUpdate = structureService.update(structure);
                }
                status = Response.Status.OK;
                responseData.put("data", structureService.tranformToModel(structureUpdate));
                responseData.put("message", "");

                //LogData For Update
                structureService.saveLogForUpdate(structure, structureUpdate, httpHeaders.getHeaderString("clientIp"));
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for delete Structure by id.",
            notes = "ลบข้อมูลโครงสร้างหน่วยงาน",
            response = StructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure deleted by id success.")
        ,@ApiResponse(code = 404, message = "Structure by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสโครงสร้างหน่วยงาน", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("remove...");
        LOG.debug("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Structure by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            StructureService structureService = new StructureService();
            UserProfileService userProfileService = new UserProfileService();
            Structure structure = structureService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (structure != null) {
                List<Structure> listStructure = structureService.listChild(structure);
                if (!listStructure.isEmpty()) {
                    for (Structure structure1 : listStructure) {
//                        UserProfileService userProfileService = new UserProfileService();
                        structure1 = structureService.remove(structure1.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        List<Structure> listStructure2 = structureService.listChild(structure1);
                        if (!listStructure2.isEmpty()) {
                            for (Structure structure2 : listStructure2) {
                                structure2 = structureService.remove(structure2.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                List<UserProfile> listUserProfile = userProfileService.listByStructure(structure2, "createdDate", "asc");
                                for (UserProfile userProfile : listUserProfile) {
                                    UserProfile userProfile1 = userProfileService.remove(userProfile.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                    UserService userService = new UserService();
                                    User user = userService.remove(userProfile.getUser().getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                }
                            }
                        }

                        List<UserProfile> listUserProfile = userProfileService.listByStructure(structure1, "createdDate", "asc");
                        for (UserProfile userProfile : listUserProfile) {
                            UserProfile userProfile1 = userProfileService.remove(userProfile.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                            UserService userService = new UserService();
                            User user = userService.remove(userProfile.getUser().getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        }
                    }
                }
                List<UserProfile> listUserProfile = userProfileService.listByStructure(structure, "createdDate", "asc");
                if (!listUserProfile.isEmpty()) {
                    for (UserProfile userProfile : listUserProfile) {
                        UserProfile userProfile1 = userProfileService.remove(userProfile.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        UserService userService = new UserService();
                        User user = userService.remove(userProfile.getUser().getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    }
                }
                status = Response.Status.OK;
                responseData.put("data", structureService.tranformToModel(structure));
                responseData.put("message", "");

                //LogData For Remove
                structureService.saveLogForRemove(structure, httpHeaders.getHeaderString("clientIp"));
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list Structure By Structure Id.",
            notes = "รายการโครงสร้างหน่วยงาน",
            responseContainer = "List",
            response = StructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure list By Structure Id success.")
        ,@ApiResponse(code = 404, message = "Structure list By Structure Id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response list(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "structureId", value = "รหัสหน่วยงาน", required = true)
            @DefaultValue("0")
            @QueryParam("structureId") int structureId
    ) {
//        LOG.debug("list...");
//        LOG.debug("offset = " + listOptionModel.getOffset());
//        LOG.debug("limit = " + listOptionModel.getLimit());
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        ArrayList<StructureModel> listStructureModel = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", listStructureModel);
        responseData.put("success", false);
        responseData.put("message", "Structure list By Structure Id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            Structure structureInput = new Structure();
            structureInput.setId(structureId);
            StructureService structureService = new StructureService();
            List<Structure> listStructure = structureService.listChild(structureInput, listOptionModel.getSort(), listOptionModel.getDir());
            if (!listStructure.isEmpty()) {
                listStructureModel = new ArrayList<>();
                for (Structure structure : listStructure) {
                    listStructureModel.add(structureService.tranformToModel(structure));
                }
                listStructureModel.trimToSize();
                responseData.put("data", listStructureModel);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for search Structure By field.",
            notes = "ค้นหาหน่วยงาน",
            responseContainer = "List",
            response = StructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure search success.")
        ,
        @ApiResponse(code = 404, message = "Structure search not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/search")
    public Response searchByFields(
            @Context UriInfo uriInfo,
            @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.debug("searchByFields...");
        LOG.debug("offset = " + listOptionModel.getOffset());
        LOG.debug("limit = " + listOptionModel.getLimit());
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        ArrayList<StructureModel> listStructureModel = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", listStructureModel);
        responseData.put("success", false);
        responseData.put("message", "Structure search not found in the database.");
        responseData.put("errorMessage", "");
        try {
//            String filedSearch = ",structureName,structureCode,";
            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
//            for (String key : queryParams.keySet()) {
//                if(filedSearch.contains(","+key+",")){
//                    for (String value : queryParams.get(key)) {
//                        String[] valueArray = value.split(",");
//                        int i = 0;
//                        for(i = 0;i<valueArray.length;i++){
//                            LOG.debug("key = " + key + " value = " + valueArray[i]);
//                        }
//                    }
//                }                
//            }
            StructureService structureService = new StructureService();
            List<Structure> listStructure = structureService.search(queryParams, listOptionModel.getOffset(), listOptionModel.getOffset(), listOptionModel.getSort(), listOptionModel.getDir());
            if (!listStructure.isEmpty()) {
                int countAll = structureService.countAll();
                int count = listStructure.size();
                int next = 0;
                if (count >= listOptionModel.getLimit()) {
                    next = listOptionModel.getOffset() + listOptionModel.getLimit();
                }
                if (next >= countAll) {
                    next = 0;
                }
                ListReturnModel listReturnModel = new ListReturnModel(countAll, count, next);
                listStructureModel = new ArrayList<>();
                for (Structure structure : listStructure) {
                    listStructureModel.add(structureService.tranformToModel(structure));
                }
                listStructureModel.trimToSize();
                responseData.put("data", listStructureModel);
                responseData.put("list", listReturnModel);
                responseData.put("message", "");
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for search Structure by Name or Code.",
            notes = "ค้นหาหน่วยงานด้วยชื่อหรือตัวย่อ",
            responseContainer = "List",
            response = StructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure search by Name or Code success.")
        ,
        @ApiResponse(code = 404, message = "Structure search by Name or Code not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/searchnameorcode")
    public Response searchByNameOrCode(
            @Context UriInfo uriInfo,
            @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.debug("searchByNameOrCode...");
        LOG.debug("offset = " + listOptionModel.getOffset());
        LOG.debug("limit = " + listOptionModel.getLimit());
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        ArrayList<StructureModel> listStructureModel = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", listStructureModel);
        responseData.put("success", false);
        responseData.put("message", "Structure search not found in the database.");
        responseData.put("errorMessage", "");
        try {
            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
            StructureService structureService = new StructureService();
            List<Structure> listStructure = structureService.searchByNameOrCode(queryParams, listOptionModel.getOffset(), listOptionModel.getOffset(), listOptionModel.getSort(), listOptionModel.getDir());
            if (!listStructure.isEmpty()) {
                int countAll = structureService.countAll();
                int count = listStructure.size();
                int next = 0;
                if (count >= listOptionModel.getLimit()) {
                    next = listOptionModel.getOffset() + listOptionModel.getLimit();
                }
                if (next >= countAll) {
                    next = 0;
                }
                ListReturnModel listReturnModel = new ListReturnModel(countAll, count, next);
                listStructureModel = new ArrayList<>();
                for (Structure structure : listStructure) {
                    listStructureModel.add(structureService.tranformToModel(structure));
                }
                listStructureModel.trimToSize();
                responseData.put("data", listStructureModel);
                responseData.put("list", listReturnModel);
                responseData.put("message", "");
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list UserProfiles By Structure Id.",
            notes = "รายการผู้ใช้งานในโครงสร้างหน่วยงาน",
            responseContainer = "List",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure list By Structure Id success.")
        ,
        @ApiResponse(code = 404, message = "Structure list By Structure Id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/profiles")
    public Response listProfiles(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "structureId", value = "รหัสหน่วยงาน", required = true)
            @DefaultValue("0") @QueryParam("structureId") int structureId
    ) {
//        LOG.debug("listProfiles....");
//        LOG.debug("offset = " + listOptionModel.getOffset());
//        LOG.debug("limit = " + listOptionModel.getLimit());
//        LOG.debug("sort = " + listOptionModel.getSort());
//        LOG.debug("dir = " + listOptionModel.getDir());
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        ArrayList<UserProfileModel> listUserProfileModel = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", listUserProfileModel);
        responseData.put("success", false);
        responseData.put("message", "UserProfiles list By Structure Id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            Structure structure = new Structure();
            structure.setId(structureId);
            UserProfileService userProfileService = new UserProfileService();
            List<UserProfile> listUserProfile = userProfileService.listByStructure(structure, listOptionModel.getSort(), listOptionModel.getDir());
            if (!listUserProfile.isEmpty()) {
                listUserProfileModel = new ArrayList<>();
                for (UserProfile userProfile : listUserProfile) {
                    listUserProfileModel.add(userProfileService.tranformToModel(userProfile));
                }
                listUserProfileModel.trimToSize();
                responseData.put("data", listUserProfileModel);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "Method for list All Structure.",
            notes = "รายการโครงสร้างหน่วยงานทั้งหมด",
            responseContainer = "List",
            response = StructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure list success.")
        ,
        @ApiResponse(code = 404, message = "Structure list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/all")
    public Response listAll(
            @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.debug("list all...");
        LOG.debug("offset = " + listOptionModel.getOffset());
        LOG.debug("limit = " + listOptionModel.getLimit());
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        ArrayList<StructureModel> listStructureModel = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", listStructureModel);
        responseData.put("success", false);
        responseData.put("message", "Structure list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            StructureService structureService = new StructureService();
            List<Structure> listStructure = structureService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if (!listStructure.isEmpty()) {
                listStructureModel = new ArrayList<>();
                for (Structure structure : listStructure) {
                    listStructureModel.add(structureService.tranformToModel(structure));
                }
                listStructureModel.trimToSize();
                responseData.put("data", listStructureModel);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update Structure.",
            notes = "จัดเรียงผู้ใช้งานด้วย Structure Id",
            response = StructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure updeted by id success.")
        ,@ApiResponse(code = 404, message = "Structure by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateOrderNo/{id}/from/{curId}")
    public Response updateByStructureId(
            @ApiParam(name = "id", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("id") int id,
            @ApiParam(name = "curId", value = "รหัสผู้ใช้ที่นำ Id ใหม่ไปแทรก", required = true)
            @PathParam("curId") int curId,
            StructureModel structureModel
    ) {
        LOG.debug("updateByStructureId...");
        LOG.debug("id = " + id);
        LOG.debug("curId = " + curId);
        Gson gs = new GsonBuilder()
                .setVersion(structureModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Structure by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            StructureService structureService = new StructureService();
//            Structure structure = structureService.getById(id);
            Structure structure = structureService.getByIdNotRemoved(id);
            LOG.debug(structure);
            if (structure != null) {
                Structure structureUpdate = new Structure();
                double orderNoNew = structureService.findOrderNo(curId);
                LOG.debug("orderNoNew : " + orderNoNew);
                structure.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                structure.setOrderNo(orderNoNew);
                structureUpdate = structureService.update(structure);
                LOG.debug(structureUpdate);
                status = Response.Status.OK;
                responseData.put("data", structureService.tranformToModel(structure));
                responseData.put("message", "");
                //LogData For Update
                structureService.saveLogForUpdate(structure, structureUpdate, httpHeaders.getHeaderString("clientIp"));
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list Structure By Profile Id.",
            notes = "รายการโครงสร้างหน่วยงานตามรหัสผู้ใช้งาน",
            responseContainer = "List",
            response = StructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure list By Profile Id success.")
        ,@ApiResponse(code = 404, message = "Structure list By Profile Id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/defaultProfiles")
    public Response listByDefaultProfiles(
            @BeanParam VersionModel versionModel
    ) {
        LOG.debug("listByDefaultProfiles...");
        LOG.debug("userProfileId = " + Integer.parseInt(httpHeaders.getHeaderString("userID")));
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        ArrayList<StructureModel> listStructureModel = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", listStructureModel);
        responseData.put("success", false);
        responseData.put("message", "Structure list By Profile Id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getByIdNotRemoved(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            Structure userProfileStructure = userProfile.getStructure();
            StructureService structureService = new StructureService();
            List<Structure> listStructure = structureService.listFromParentKey(userProfileStructure);
            if (!listStructure.isEmpty()) {
                listStructureModel = new ArrayList<>();
                for (Structure structure : listStructure) {
                    listStructureModel.add(structureService.tranformToModel(structure));
                }
                listStructureModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listStructureModel);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list Structure By Profile Id.",
            notes = "รายการโครงสร้างหน่วยงานตามรหัสผู้ใช้งาน",
            responseContainer = "List",
            response = StructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure list By Profile Id success.")
        ,@ApiResponse(code = 404, message = "Structure list By Profile Id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/defaultProfiles2")
    public Response listByDefaultProfiles2(
            @BeanParam VersionModel versionModel
    ) {
        LOG.debug("listByDefaultProfiles...");
        LOG.debug("userProfileId = " + Integer.parseInt(httpHeaders.getHeaderString("userID")));
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        ArrayList<StructureModel> listStructureModel = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", listStructureModel);
        responseData.put("success", false);
        responseData.put("message", "Structure list By Profile Id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getByIdNotRemoved(Integer.parseInt(httpHeaders.getHeaderString("userID")));
//            Structure userProfileStructure = userProfile.getStructure();
            StructureService structureService = new StructureService();
            Structure structure = structureService.getById(userProfile.getStructure().getId());

            listStructureModel.add(structureService.tranformToModel(structure));

            listStructureModel.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listStructureModel);
            responseData.put("userProfileType", userProfile.getUserProfileType().getId());
            responseData.put("message", "");

            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for check Duplicate Structure by code.",
            notes = "เช็คค่าซ้ำ",
            response = AuthenticationModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure check duplicate by code success.", response = AuthenticationModel.class)
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/checkDup/{code}/name/{name}/id/{id}")
    public Response checkDup(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "code", value = "รหัสอ้างอิงโครงสร้าง", required = false)
            @PathParam("code") String code,
            @ApiParam(name = "name", value = "ชื่อโครงสร้าง", required = false)
            @PathParam("name") String name,
            @ApiParam(name = "id", value = "รหัสโครงสร้าง", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("checkDup...");
        LOG.info("code = " + code);
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Structure by id not found in the database.");
        responseData.put("errorMessage", "");
        AuthenticationModel authenticationModel = new AuthenticationModel(false);
        responseData.put("data", authenticationModel);
        try {
            StructureService structureService = new StructureService();
            if (id != 0) {
                Structure structure = structureService.getById(id);
                if (!code.equals(structure.getStructureCode()) && !name.equals(structure.getStructureName())) {
                    boolean result = structureService.checkDup(code, name);
                    if (result) {
                        authenticationModel = new AuthenticationModel(result);
                        responseData.put("message", "");
                    }
                } else if (name.equals(structure.getStructureName())) {
                    boolean result = structureService.checkDup(code, "");
                    if (result) {
                        authenticationModel = new AuthenticationModel(result);
                        responseData.put("message", "");
                    }
                } else if (code.equals(structure.getStructureCode())) {
                    boolean result = structureService.checkDup("", name);
                    if (result) {
                        authenticationModel = new AuthenticationModel(result);
                        responseData.put("message", "");
                    }
                } else {
                    authenticationModel = new AuthenticationModel(false);
                }
                responseData.put("data", authenticationModel);
            } else {
                boolean result = structureService.checkDup(code, name);
                if (result) {
                    authenticationModel = new AuthenticationModel(result);
                    responseData.put("data", authenticationModel);
                    responseData.put("message", "");
                }
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for import file Excel Structure",
            notes = "นำเข้าข้อมูลข้อมูลโครงสร้าง",
            response = StructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Structure Excel created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })

    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/importFileExcel")
    public Response importFileExcel(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "fileId", value = "รหัสไฟล์", required = true)
            @DefaultValue("0") @QueryParam("fileId") int fileId
    ) {
        LOG.info("importFile...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            StructureService structureService = new StructureService();
            int countRecord = structureService.readFileStrucExcel(fileId, versionModel.getClientIp());

            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("countRecord", countRecord);
//        LOG.info("countRecord = "+countRecord);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();

    }

    @ApiOperation(
            value = "Method for get Structure by id",
            notes = "ขอข้อมูลโครงสร้างหน่วยงาน ด้วย รหัสอ้างอิงโครงสร้าง",
            response = VStructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure by id success.")
        ,
        @ApiResponse(code = 404, message = "Structure by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "structureCode/{code}")
    public Response getByCode(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "code", value = "รหัสอ้างอิงโครงสร้าง", required = true)
            @PathParam("code") String code
    ) {
        LOG.debug("getByCode...");
        LOG.debug("code = " + code);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Structure by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            VStructureService vStructureService = new VStructureService();
            VStructure vStructure = vStructureService.getByCode(code);
            if (vStructure != null) {
                status = Response.Status.OK;
                responseData.put("data", vStructureService.tranformToModel(vStructure));
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list All Convert Structure.",
            notes = "รายการนำเข้าโครงสร้างหน่วยงานทั้งหมด",
            responseContainer = "List",
            response = StructureConvertModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure Convert list success.")
        ,
        @ApiResponse(code = 404, message = "Structure Convert list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/convert")
    public Response listHrisStructure(
            @BeanParam VersionModel versionModel,
            //            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "vstatus", value = "สถานะ", required = false)
            @DefaultValue("0") @QueryParam("vstatus") int vstatus,
            @ApiParam(name = "structureCode", value = "รหัสหน่วยงาน", required = false)
            @DefaultValue("") @QueryParam("structureCode") String structureCode,
            @ApiParam(name = "structureName", value = "ชื่อหน่วยงาน", required = false)
            @DefaultValue("") @QueryParam("structureName") String structureName,
            @ApiParam(name = "strucShortName", value = "ชื่อย่อหน่วยงาน", required = false)
            @DefaultValue("") @QueryParam("strucShortName") String strucShortName,
            @ApiParam(name = "strucDetail", value = "รายละเอียด", required = false)
            @DefaultValue("") @QueryParam("strucDetail") String strucDetail,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("desc") @QueryParam("dir") String dir
    ) {
        LOG.debug("list convert...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        StructureModel structureModel = new StructureModel();
        VStructureModel vStructureModel = new VStructureModel();
        StructureConvertModel structureConvertModel = new StructureConvertModel();
        ArrayList<StructureConvertModel> listStructureConvertModel = new ArrayList<>();
        ArrayList<StructureConvertModel> listStructureConvertModel2 = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", listStructureConvertModel);
        responseData.put("success", false);
        responseData.put("message", "Structure Convert list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            StructureService structureService = new StructureService();
            VStructureService vStructureService = new VStructureService();
            int stat = 0;
            int count = 0;
            int chkSearch = 0;
//            List<Structure> listStructure = structureService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
//            List<VStructure> listVStructure = vStructureService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            List<Structure> listStructure = structureService.listAll(sort, dir);
            List<VStructure> listVStructure = vStructureService.listAll(sort, dir);
            // add
            if (!listVStructure.isEmpty()) {
                for (VStructure vStructure : listVStructure) {
                    vStructureModel = new VStructureModel();
                    if (!listStructure.isEmpty()) {
                        for (Structure structure : listStructure) {
                            if (structure.getStructureCode() != null && vStructure.getStructureCode() != null) {
                                // edit
                                if (structure.getStructureCode().equalsIgnoreCase(vStructure.getStructureCode())) {
                                    if (structure.getStructureShortName() != null && vStructure.getStructureShortName() != null && !structure.getStructureShortName().equalsIgnoreCase(vStructure.getStructureShortName())) {
                                        stat = 3;
                                    } else if (structure.getStructureName() != null && vStructure.getStructureName() != null && !structure.getStructureName().equalsIgnoreCase(vStructure.getStructureName())) {
                                        stat = 3;
                                    } else if (structure.getStructureDetail() != null && vStructure.getStructureDetail() != null && !structure.getStructureDetail().equalsIgnoreCase(vStructure.getStructureDetail())) {
                                        stat = 3;
                                    } else {
                                        stat = 0;
                                    }
                                    structureModel = structureService.tranformToModel(structure);
                                    vStructureModel = vStructureService.tranformToModel(vStructure);
                                    break;
                                }

                            } else {
                                stat = 0;
                                if (structure.getStructureCode() == null && vStructure.getStructureCode() != null) {
                                    stat = 1;
//                                    structureModel = null;
//                                    vStructureModel = vStructureService.tranformToModel(vStructure);
                                }
                            }
                        }

                        if (stat != 0) {
                            if (stat == 1) {
                                structureModel = null;
                                vStructureModel = vStructureService.tranformToModel(vStructure);
                            }
                            structureConvertModel = structureService.tranformToConvertModel(stat, structureModel, vStructureModel);
                        }
                    }
                    if (stat != 0) {
                        listStructureConvertModel.add(structureConvertModel);
                        if (vstatus != 0) {
                            if (vstatus == stat) {
                                chkSearch += 1;
                                listStructureConvertModel2.add(structureConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(structureCode)) {
                            if (((structureConvertModel.getVStructure() != null) && structureConvertModel.getVStructure().getCode() != null && structureConvertModel.getVStructure().getCode().matches(structureCode + "(.*)")) || ((structureConvertModel.getStructure() != null) && structureConvertModel.getStructure().getCode() != null && structureConvertModel.getStructure().getCode().matches(structureCode + "(.*)"))) {
//                            if ((structureConvertModel.getVStructure() != null && structureConvertModel.getVStructure().getCode() != null && structureConvertModel.getVStructure().getCode().matches("(.*)" + structureCode + "(.*)")) || (structureConvertModel.getStructure() != null && structureConvertModel.getStructure().getCode() != null && structureConvertModel.getStructure().getCode().matches("(.*)" + structureCode + "(.*)"))) {
                                chkSearch += 1;
                                listStructureConvertModel2.add(structureConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(structureName)) {
                            if ((structureConvertModel.getVStructure() != null && structureConvertModel.getVStructure().getName() != null && structureConvertModel.getVStructure().getName().matches("(.*)" + structureName + "(.*)")) || (structureConvertModel.getStructure() != null && structureConvertModel.getStructure().getName() != null && structureConvertModel.getStructure().getName().matches("(.*)" + structureName + "(.*)"))) {
                                chkSearch += 1;
                                listStructureConvertModel2.add(structureConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(strucShortName)) {
                            if ((structureConvertModel.getVStructure() != null && structureConvertModel.getVStructure().getShortName() != null && structureConvertModel.getVStructure().getShortName().matches("(.*)" + strucShortName + "(.*)")) || (structureConvertModel.getStructure() != null && structureConvertModel.getStructure().getShortName() != null && structureConvertModel.getStructure().getShortName().matches("(.*)" + strucShortName + "(.*)"))) {
                                chkSearch += 1;
                                listStructureConvertModel2.add(structureConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(strucDetail)) {
                            if ((structureConvertModel.getVStructure() != null && structureConvertModel.getVStructure().getDetail() != null && structureConvertModel.getVStructure().getDetail().matches("(.*)" + strucDetail + "(.*)")) || (structureConvertModel.getStructure() != null && structureConvertModel.getStructure().getDetail() != null && structureConvertModel.getStructure().getDetail().matches("(.*)" + strucDetail + "(.*)"))) {
                                chkSearch += 1;
                                listStructureConvertModel2.add(structureConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        }
                    }
                }
            }
            // remove
            if (!listStructure.isEmpty()) {
                String code = "";
                for (Structure structure : listStructure) {
//                    structureModel = new StructureModel();

                    if (!listVStructure.isEmpty()) {
                        for (VStructure vStructure : listVStructure) {
                            if (vStructure.getStructureCode() != null) {
                                if (structure.getStructureCode() != null) {
                                    if (structure.getStructureCode().equalsIgnoreCase(vStructure.getStructureCode())) {
                                        code = structure.getStructureCode();
                                        stat = 0;
                                        break;
                                    } else {
                                        if (!code.equalsIgnoreCase("") && code.equalsIgnoreCase(vStructure.getStructureCode())) {
                                            stat = 0;
                                            continue;
                                        } else {
                                            stat = 2;
//                                            vStructureModel = null;
                                        }

                                    }
                                } else {
                                    stat = 2;
//                                    vStructureModel = null;
                                }
                            }
                        }
                        if (stat != 0) {
                            if (stat == 2) {
                                vStructureModel = null;
                                structureModel = structureService.tranformToModel(structure);
                            }
                            structureConvertModel = structureService.tranformToConvertModel(stat, structureModel, vStructureModel);
                        }
                    }
                    if (stat != 0) {
                        listStructureConvertModel.add(structureConvertModel);
                        if (vstatus != 0) {
                            if (vstatus == stat) {
                                chkSearch += 1;
                                listStructureConvertModel2.add(structureConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(structureCode)) {
                            if (((structureConvertModel.getVStructure() != null) && structureConvertModel.getVStructure().getCode() != null && structureConvertModel.getVStructure().getCode().matches(structureCode + "(.*)")) || ((structureConvertModel.getStructure() != null) && structureConvertModel.getStructure().getCode() != null && structureConvertModel.getStructure().getCode().matches(structureCode + "(.*)"))) {
//                            if (((structureConvertModel.getVStructure() != null) && structureConvertModel.getVStructure().getCode().equals(structureCode)) || ((structureConvertModel.getStructure() != null && structureConvertModel.getStructure().getCode() != null) && structureConvertModel.getStructure().getCode().equals(structureCode))) {
                                chkSearch += 1;
                                listStructureConvertModel2.add(structureConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(structureName)) {
                            if ((structureConvertModel.getVStructure() != null && structureConvertModel.getVStructure().getName() != null && structureConvertModel.getVStructure().getName().matches("(.*)" + structureName + "(.*)")) || (structureConvertModel.getStructure() != null && structureConvertModel.getStructure().getName() != null && structureConvertModel.getStructure().getName().matches("(.*)" + structureName + "(.*)"))) {
                                chkSearch += 1;
                                listStructureConvertModel2.add(structureConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(strucShortName)) {
                            if ((structureConvertModel.getVStructure() != null && structureConvertModel.getVStructure().getShortName() != null && structureConvertModel.getVStructure().getShortName().matches("(.*)" + strucShortName + "(.*)")) || (structureConvertModel.getStructure() != null && structureConvertModel.getStructure().getShortName() != null && structureConvertModel.getStructure().getShortName().matches("(.*)" + strucShortName + "(.*)"))) {
                                chkSearch += 1;
                                listStructureConvertModel2.add(structureConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(strucDetail)) {
                            if ((structureConvertModel.getVStructure() != null && structureConvertModel.getVStructure().getDetail() != null && structureConvertModel.getVStructure().getDetail().matches("(.*)" + strucDetail + "(.*)")) || (structureConvertModel.getStructure() != null && structureConvertModel.getStructure().getDetail() != null && structureConvertModel.getStructure().getDetail().matches("(.*)" + strucDetail + "(.*)"))) {
                                chkSearch += 1;
                                listStructureConvertModel2.add(structureConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        }
                    }
                }
            }
            listStructureConvertModel.trimToSize();
            responseData.put("data", chkSearch == 0 ? listStructureConvertModel : listStructureConvertModel2);
            responseData.put("message", count + "");

            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update Structure.",
            notes = "ปรับโครงสร้างจากข้อมูลนำเข้า hr",
            responseContainer = "List",
            response = StructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure updeted by id success.")
        ,@ApiResponse(code = 404, message = "Structure by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateConvert")
    public Response updateConvert(
            //            @ApiParam(name = "id", value = "รหัสผู้ใช้งานระบบ", required = true)
            //            @PathParam("id") int id,
            //            @ApiParam(name = "curId", value = "รหัสผู้ใช้ที่นำ Id ใหม่ไปแทรก", required = true)
            //            @PathParam("curId") int curId,
            List<StructureConvertModel> structureConvertModelList
    ) {
        LOG.debug("updateConvert...");
//        LOG.debug("id = " + id);
//        LOG.debug("curId = " + curId);
        Gson gs = new GsonBuilder()
                .setVersion(1)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        StructureConvertModel structureConvertModel = new StructureConvertModel();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Structure by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            StructureService structureService = new StructureService();
            VStructureService vStructureService = new VStructureService();
            UserProfileService userProfileService = new UserProfileService();
            List<StructureModel> listStructureModel = new ArrayList<>();
            Integer parentId = 1;
//            VStructureService vStructureService = new VStructureService();
//            VStructureModel vStructureModel = new VStructureModel();
            Structure structure = new Structure();
            for (StructureConvertModel structureConvert : structureConvertModelList) {
                int stat = structureConvert.getStatus();
                if (stat == 1) {
                    structure = new Structure();
                    VStructure vStructure = new VStructure();
                    structure.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    structure.setStructureCode(structureConvert.getVStructure().getCode());
                    structure.setStructureShortName(structureConvert.getVStructure().getShortName());
                    structure.setStructureName(structureConvert.getVStructure().getName());
                    structure.setStructureDetail(structureConvert.getVStructure().getDetail());
                    parentId = structureService.findParentId(structureConvert.getVStructure().getCode());
                    if (parentId != null) {
                        structure.setParentId(parentId);
                    } else {
                        status = Response.Status.OK;
                        responseData.put("message", "sructure not found");
                        break;
                    }
                    structure = structureService.create(structure);
                    vStructureService.remove(vStructureService.getByCode(structureConvert.getVStructure().getCode()).getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));

                    StructureFolderService structureFolderService = new StructureFolderService();
                    //genInput
                    StructureFolder structureFolder = new StructureFolder();
                    structureFolder.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    structureFolder.setStructureFolderDetail("หนังสือเข้าของ " + structure.getStructureName());
                    structureFolder.setStructureFolderLinkId(0);
                    structureFolder.setStructureFolderName("กล่องหนังสือเข้า");
                    structureFolder.setStructureFolderType("I");
                    structureFolder.setStructureId(structure.getId());
                    structureFolder = structureFolderService.create(structureFolder);
                    //genOutput
                    structureFolder = new StructureFolder();
                    structureFolder.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    structureFolder.setStructureFolderDetail("หนังสือออกของ " + structure.getStructureName());
                    structureFolder.setStructureFolderLinkId(0);
                    structureFolder.setStructureFolderName("กล่องหนังสือออก");
                    structureFolder.setStructureFolderType("O");
                    structureFolder.setStructureId(structure.getId());
                    structureFolder = structureFolderService.create(structureFolder);
                }
                if (stat == 2) {
                    structure = new Structure();
                    structure = structureService.remove(structureConvert.getStructure().getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    if (structure != null) {
                        List<Structure> listStructure = structureService.listChild(structure);
                        if (!listStructure.isEmpty()) {
                            for (Structure structure1 : listStructure) {
//                        UserProfileService userProfileService = new UserProfileService();
                                structure1 = structureService.remove(structure1.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                StructureFolderService structureFolderService = new StructureFolderService();
                                StructureFolder structureFolder = new StructureFolder();
                                structureFolder = structureFolderService.remove(structureFolderService.getByStructureId(structure1.getId(), "I").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                structureFolder = structureFolderService.remove(structureFolderService.getByStructureId(structure1.getId(), "O").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));

                                List<Structure> listStructure2 = structureService.listChild(structure1);
                                if (!listStructure2.isEmpty()) {
                                    for (Structure structure2 : listStructure2) {
                                        structure2 = structureService.remove(structure2.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                        structureFolder = structureFolderService.remove(structureFolderService.getByStructureId(structure2.getId(), "I").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                        structureFolder = structureFolderService.remove(structureFolderService.getByStructureId(structure2.getId(), "O").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));

                                        List<UserProfile> listUserProfile = userProfileService.listByStructure(structure2, "createdDate", "asc");
                                        for (UserProfile userProfile : listUserProfile) {
                                            UserProfile userProfile1 = userProfileService.remove(userProfile.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                            UserService userService = new UserService();
                                            User user = userService.remove(userProfile.getUser().getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));

                                            UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
                                            UserProfileFolder userProfileFolder = new UserProfileFolder();
                                            userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "I").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                            userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "O").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                            userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "Z").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                            userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "W").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));

                                        }
                                    }
                                }

                                List<UserProfile> listUserProfile = userProfileService.listByStructure(structure1, "createdDate", "asc");
                                for (UserProfile userProfile : listUserProfile) {
                                    UserProfile userProfile1 = userProfileService.remove(userProfile.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                    UserService userService = new UserService();
                                    User user = userService.remove(userProfile.getUser().getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));

                                    UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
                                    UserProfileFolder userProfileFolder = new UserProfileFolder();
                                    userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "I").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                    userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "O").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                    userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "Z").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                    userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "W").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                }
                            }
                        }
                        List<UserProfile> listUserProfile = userProfileService.listByStructure(structure, "createdDate", "asc");
                        if (!listUserProfile.isEmpty()) {
                            for (UserProfile userProfile : listUserProfile) {
                                UserProfile userProfile1 = userProfileService.remove(userProfile.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                UserService userService = new UserService();
                                User user = userService.remove(userProfile.getUser().getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));

                                UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
                                UserProfileFolder userProfileFolder = new UserProfileFolder();
                                userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "I").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "O").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "Z").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "W").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                            }
                        }
                    }
                }
                if (stat == 3) {
                    structure = structureService.getById(structureConvert.getStructure().getId());
                    if (structure != null) {
                        structure.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        structure.setStructureCode(structureConvert.getVStructure().getCode());
                        structure.setStructureShortName(structureConvert.getVStructure().getShortName());
                        structure.setStructureName(structureConvert.getVStructure().getName());
                        structure.setStructureDetail(structureConvert.getVStructure().getDetail());
                        structure = structureService.update(structure);

                        StructureFolderService structureFolderService = new StructureFolderService();
                        StructureFolder structureFolder = new StructureFolder();
                        structureFolder = structureFolderService.getByStructureId(structure.getId(), "I");
                        if (structureFolder != null) {
                            structureFolder.setStructureFolderDetail("หนังสือเข้าของ " + structure.getStructureName());
                            structureFolder.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                            structureFolder = structureFolderService.update(structureFolder);
                        }
                        structureFolder = structureFolderService.getByStructureId(structure.getId(), "O");
                        if (structureFolder != null) {
                            structureFolder.setStructureFolderDetail("หนังสือออกของ " + structure.getStructureName());
                            structureFolder.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                            structureFolder = structureFolderService.update(structureFolder);
                        }

                    }

                }
//                structureModel = structureService.tranformToModel(structure);
                listStructureModel.add(structureService.tranformToModel(structure));
                status = Response.Status.OK;
                responseData.put("message", "");
            }
//            status = Response.Status.OK;
            responseData.put("data", listStructureModel);
//            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for import file atternal",
            notes = "นำเข้าข้อมูลผู้ใช้",
            response = VStructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "VStructure Excel created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/importFile")
    public Response importFile(
            List<VStructureModel> listVStructureModel
    //            @BeanParam VersionModel versionModel,
    //            ArrayList<Object> userList
    //            ArrayList<String> userList
    //            @ApiParam(name = "fileId", value = "รหัสไฟล์", required = true)
    //            @DefaultValue("0") @QueryParam("fileId") int fileId
    ) {
        LOG.info("importFile...");
        LOG.info("userList..." + listVStructureModel);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            VStructureService vStructureService = new VStructureService();
            VStructure vStructure = new VStructure();

            List<VStructure> listVStructure = vStructureService.listAllNotRemove("id", "asc");
            if (!listVStructure.isEmpty()) {
                for (VStructure vUserProfile1 : listVStructure) {
                    vStructureService.delete(vUserProfile1);
                }
            }
            for (VStructureModel vStructureModel : listVStructureModel) {
                vStructure.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));//test
                vStructure.setStructureCode(vStructureModel.getCode());
                vStructure.setStructureName(vStructureModel.getName());
                vStructure.setStructureShortName(vStructureModel.getShortName());
                vStructure.setStructureDetail(vStructureModel.getDetail());
                vStructure = vStructureService.create(vStructure);
            }
            responseData.put("data", vStructureService.tranformToModel(vStructure));
            status = Response.Status.CREATED;
            responseData.put("success", true);
//          responseData.put("countRecord", countRecord);
//        LOG.info("countRecord = "+countRecord);
        } catch (Exception ex) {

            LOG.error("Exception = " + ex.getStackTrace());
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();

    }

    @ApiOperation(
            value = "Method for list User.",
            notes = "รายการผู้ใช้งานระบบ",
            response = VStructureModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Name hr list success.")
        ,@ApiResponse(code = 404, message = "Name hr list not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/vstructure")
    public Response listStructure(
            @BeanParam VersionModel versionModel
    ) {
        LOG.debug("list...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "User list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            Client client = ClientBuilder.newClient();
            ParamService paramService = new ParamService();
            Param param = paramService.getByParamName("PATHHRIS");
//            WebTarget base = client.target("http://192.168.142.149/pxservice-hris/api/");
            WebTarget base = client.target(param.getParamValue() + "pxservice-hris/api/");
            WebTarget version = base.path("v1");
            WebTarget title = version.path("/vwNameHrs/structure").queryParam("version", "1").queryParam("api_key", "praXis");

            // Create uriBuilder from path
            Invocation.Builder builder = title.request(MediaType.APPLICATION_JSON);
            Response response = builder.get();
            String responseStr = response.readEntity(String.class);
            responseData.put("data", responseStr);
            responseData.put("message", title.getUri());
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
