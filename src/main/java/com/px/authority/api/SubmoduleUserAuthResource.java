package com.px.authority.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Submodule;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.StructureService;
import com.px.admin.service.SubmoduleService;
import com.px.admin.service.UserProfileService;
import com.px.authority.entity.Auth;
import com.px.authority.entity.SubmoduleUserAuth;
import com.px.authority.model.SubmoduleUserAuthModel;
import com.px.authority.service.SubmoduleAuthService;
import com.px.authority.service.SubmoduleUserAuthService;
import com.px.dms.entity.DmsFolder;
import com.px.dms.service.DmsFolderService;
import com.px.share.entity.TempTable;
import com.px.share.service.TempTableService;
import com.px.wf.entity.WfFolder;
import com.px.wf.service.WfFolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author Peach
 */
@Api(value = "SubmoduleUserAuth สิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ")
@Path("v1/authority/submoduleUserAuths")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class SubmoduleUserAuthResource {

    private static final Logger LOG = Logger.getLogger(SubmoduleUserAuthResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create SubmoduleUserAuth",
            notes = "สร้างข้อมูลสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ",
            response = SubmoduleUserAuthResource.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "SubmoduleUserAuth created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            SubmoduleUserAuthModel submoduleUserAuthModel
    ) {
        LOG.info("create...");
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
            SubmoduleUserAuth submoduleUserAuth = new SubmoduleUserAuth();
            submoduleUserAuth.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            submoduleUserAuth.setLinkId(submoduleUserAuthModel.getLinkId());
            submoduleUserAuth.setAuthority(submoduleUserAuthModel.getAuthority());

            StructureService structureService = new StructureService();
            //submoduleUserAuth.setStructure(structureService.getById(submoduleUserAuthModel.getStructureId()));
            submoduleUserAuth.setStructure(structureService.getByIdNotRemoved(submoduleUserAuthModel.getStructure().getId()));
            UserProfileService userProfileService = new UserProfileService();
            //submoduleUserAuth.setUserProfile(userProfileService.getById(submoduleUserAuthModel.getUserProfileId()));
            submoduleUserAuth.setUserProfile(userProfileService.getByIdNotRemoved(submoduleUserAuthModel.getUserProfile().getId()));
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            submoduleUserAuth.setSubmoduleAuth(submoduleAuthService.getById(submoduleUserAuthModel.getSubmoduleAuth().getId()));

            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            submoduleUserAuth = submoduleUserAuthService.create(submoduleUserAuth);
            responseData.put("data", submoduleUserAuthService.tranformToModel(submoduleUserAuth));
            LOG.info("submoduleUserAuth Service : " + gs.toJson(submoduleUserAuthService.tranformToModel(submoduleUserAuth)));
            responseData.put("message", "");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get SubmoduleUserAuth by id",
            notes = "ขอข้อมูลสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ ด้วย รหัสสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ",
            response = SubmoduleUserAuthModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth by id success.")
        ,
        @ApiResponse(code = 404, message = "SubmoduleUserAuth by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @HeaderParam("userID") int userID,
            @ApiParam(name = "id", value = "รหัสสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("getById...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "SubmoduleUserAuth by id not found in the database.");
        try {
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            SubmoduleUserAuth submoduleUserAuth = submoduleUserAuthService.getById(id);
            if (submoduleUserAuth != null) {
                status = Response.Status.OK;
                responseData.put("data", submoduleUserAuthService.tranformToModel(submoduleUserAuth));
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
            value = "Method for update SubmoduleUserAuth.",
            notes = "แก้ไขข้อมูลสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ",
            response = SubmoduleUserAuthModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth updeted by id success.")
        ,@ApiResponse(code = 404, message = "SubmoduleUserAuth by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @ApiParam(name = "id", value = "รหัสสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ", required = true)
            @PathParam("id") int id,
            SubmoduleUserAuthModel submoduleUserAuthModel
    ) {
        LOG.info("update...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "SubmoduleUserAuth by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            SubmoduleUserAuth submoduleUserAuth = submoduleUserAuthService.getById(id);
            if (submoduleUserAuth != null) {
                submoduleUserAuth.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                submoduleUserAuth.setLinkId(submoduleUserAuthModel.getLinkId());
                submoduleUserAuth.setAuthority(submoduleUserAuthModel.getAuthority());

                StructureService structureService = new StructureService();
                //submoduleUserAuth.setStructure(structureService.getById(submoduleUserAuthModel.getStructureId()));
                submoduleUserAuth.setStructure(structureService.getByIdNotRemoved(submoduleUserAuthModel.getStructure().getId()));
                UserProfileService userProfileService = new UserProfileService();
                //submoduleUserAuth.setUserProfile(userProfileService.getById(submoduleUserAuthModel.getUserProfileId()));
                submoduleUserAuth.setUserProfile(userProfileService.getByIdNotRemoved(submoduleUserAuthModel.getUserProfile().getId()));
                SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
                submoduleUserAuth.setSubmoduleAuth(submoduleAuthService.getById(submoduleUserAuthModel.getSubmoduleAuth().getId()));

                submoduleUserAuth = submoduleUserAuthService.update(submoduleUserAuth);
                status = Response.Status.OK;
                responseData.put("data", submoduleUserAuthService.tranformToModel(submoduleUserAuth));
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
            value = "Method for delete SubmoduleUserAuth by id.",
            notes = "ลบข้อมูลสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ",
            response = SubmoduleUserAuthModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth deleted by id success.")
        ,@ApiResponse(code = 404, message = "SubmoduleUserAuth by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @ApiParam(name = "id", value = "รหัสสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("remove...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "SubmoduleUserAuth by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            SubmoduleUserAuth submoduleUserAuth = submoduleUserAuthService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (submoduleUserAuth != null) {
                status = Response.Status.OK;
                responseData.put("data", submoduleUserAuthService.tranformToModel(submoduleUserAuth));
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
            value = "Method for list SubmoduleUserAuth.",
            notes = "รายการสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ",
            responseContainer = "List",
            response = SubmoduleUserAuthModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth list success.")
        ,
        @ApiResponse(code = 404, message = "SubmoduleUserAuth list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response list(
            @HeaderParam("userID") int userID,
            @ApiParam(name = "offset", value = "ตำแหน่งเริ่มต้น", required = true)
            @DefaultValue("0") @QueryParam("offset") int offset,
            @ApiParam(name = "limit", value = "จำนวนข้อมูลที่ต้องการ", required = true)
            @DefaultValue("20") @QueryParam("limit") int limit,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("orderNo") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("asc") @QueryParam("dir") String dir
    ) {
        LOG.info("list...");
        LOG.info("offset = " + offset);
        LOG.info("limit = " + limit);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "SubmoduleUserAuth list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            List<SubmoduleUserAuth> listSubmoduleUserAuth = submoduleUserAuthService.listAll(sort, dir);
            if (!listSubmoduleUserAuth.isEmpty()) {
                ArrayList<SubmoduleUserAuthModel> listSubmoduleUserAuthModel = new ArrayList<>();
                for (SubmoduleUserAuth submoduleUserAuth : listSubmoduleUserAuth) {
                    listSubmoduleUserAuthModel.add(submoduleUserAuthService.tranformToModel(submoduleUserAuth));
                }
                listSubmoduleUserAuthModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listSubmoduleUserAuthModel);
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
            value = "Method for get SubmoduleUserAuth enable authority Value",
            notes = "ขอข้อมูลสถานะสิทธิใช้งานได้ของสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ",
            response = String.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth enable authority Value success.")
        ,
        @ApiResponse(code = 404, message = "SubmoduleUserAuth enable authority Value not found.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/enableAuthorityValue")
    public Response getEnableAuthorityValue(
            @HeaderParam("userID") int userID
    ) {
        LOG.info("getEnableAuthorityValue...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "SubmoduleUserAuth enable authority Value not found.");
        try {
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            status = Response.Status.OK;
            responseData.put("data", submoduleUserAuthService.getEnableAuthValue());
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
            value = "Method for get SubmoduleUserAuth disable authority Value",
            notes = "ขอข้อมูลสถานะสิทธิใช้งานไม่ได้ของสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ",
            response = String.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth disable authority Value success.")
        ,
        @ApiResponse(code = 404, message = "SubmoduleUserAuth disable authority Value not found.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/disableAuthorityValue")
    public Response getDisableAuthorityValue(
            @HeaderParam("userID") int userID
    ) {
        LOG.info("getDisableAuthorityValue...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "SubmoduleUserAuth disable authority Value not found.");
        try {
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            status = Response.Status.OK;
            responseData.put("data", submoduleUserAuthService.getDisableAuthValue());
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
            value = "Method for list SubmoduleUserAuth.",
            notes = "รายการสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบจัดเก็บ",
            responseContainer = "List",
            response = Auth.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth list success.")
        ,
        @ApiResponse(code = 404, message = "SubmoduleUserAuth list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/dmsFolder/{id}/report/{jobType}")
    public Response listByDmsFolder(
            @ApiParam(name = "id", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("id") int id,
            @ApiParam(name = "jobType", value = "รหัสประเภทงาน", required = true)
            @PathParam("jobType") String jobType
    ) {
        LOG.info("list...");
        LOG.info("id..." + id);
        LOG.info("jobType..." + jobType);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
//        Response.Status status = Response.Status.NOT_FOUND;
        Response.Status status = Response.Status.OK;
        responseData.put("data", new ArrayList<>());
//        responseData.put("success", false);
        responseData.put("message", "SubmoduleUserAuth list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            TempTableService tempTableService = new TempTableService();
            List<TempTable> listTempTable = tempTableService.listByJobType(Integer.parseInt(httpHeaders.getHeaderString("userID")), jobType, "", "");
//            List<TempTable> listTempTable = tempTableService.listByComputerNameAndJobType(1, "127.0.0.1", jobType, "", "");

            if (!listTempTable.isEmpty()) {
//            if (listTempTable.size() != 0) {
                for (TempTable tempTable : listTempTable) {
                    tempTableService.delete(tempTable);
                }
            }

            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            DmsFolderService dmsFolderService = new DmsFolderService();
            DmsFolder dmsFolder1 = dmsFolderService.getByIdNotRemoved(id);
            List<DmsFolder> listDmsFolder = dmsFolderService.listFolderByparenID(id, 0, 500);
            if (!listDmsFolder.isEmpty()) {
                ArrayList<SubmoduleUserAuthModel> listSubmoduleUserAuthModel = new ArrayList<>();
                for (DmsFolder dmsFolder : listDmsFolder) {
                    listSubmoduleUserAuthModel = new ArrayList<>();
                    List<SubmoduleUserAuth> listSubmoduleUserAuth = submoduleUserAuthService.listByDmsFolders(dmsFolder.getId());
//                    ArrayList<SubmoduleUserAuthModel> listSubmoduleUserAuthModel = new ArrayList<>();
                    if (!listSubmoduleUserAuth.isEmpty()) {
                        for (SubmoduleUserAuth submoduleUserAuth : listSubmoduleUserAuth) {
                            listSubmoduleUserAuthModel.add(submoduleUserAuthService.tranformToModel(submoduleUserAuth));
                        }

                        listSubmoduleUserAuthModel.forEach(model -> {
                            TempTable tempTable = new TempTable();
                            String subName = "";
//                        DmsFolder dmsFolder1 = new DmsFolderService().getByIdNotRemoved(model.getLinkId());

                            tempTable.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                            tempTable.setComputerName(httpHeaders.getHeaderString("clientIp"));
//                tempTable.setCreatedBy(1);
//                tempTable.setComputerName("127.0.0.1");
                            tempTable.setJobType(jobType);
                            tempTable.setInt01(model.getId());
                            tempTable.setInt01(model.getSubmoduleAuth().getAuth().getId());
                            tempTable.setStr01(dmsFolder1.getDmsFolderName());
                            tempTable.setStr02(dmsFolder.getDmsFolderName());
                            tempTable.setStr03(model.getSubmoduleAuth().getAuth().getAuthName());
                            if (model.getStructure() != null) {
                                subName = model.getStructure().getName();

                            } else if (model.getUserProfile() != null) {
                                subName = model.getUserProfile().getTitle().getName() + model.getUserProfile().getFullName();
                            }

                            tempTable.setStr04(subName);
                            tempTable.setStr05("1".equalsIgnoreCase(model.getAuthority()) ? "/" : "X");
//                            tempTable.setText01(model.getModuleIcon());

                            if (tempTable != null) {
                                tempTable = tempTableService.create(tempTable);
                                tempTable.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                tempTable.setOrderNo(tempTable.getId());
                                tempTable = tempTableService.update(tempTable);
                            }
                        });
                    }
                    listSubmoduleUserAuthModel.trimToSize();
                    status = Response.Status.OK;
                    responseData.put("data", listSubmoduleUserAuthModel);
                    responseData.put("message", "");
                }
            }
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
            value = "Method for list SubmoduleUserAuth.",
            notes = "รายการสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบสารบรรณ",
            responseContainer = "List",
            response = Auth.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth list success.")
        ,
        @ApiResponse(code = 404, message = "SubmoduleUserAuth list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/wfFolder/{id}/report/{jobType}")
    public Response listByWfFolder(
            @ApiParam(name = "id", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("id") int id,
            @ApiParam(name = "jobType", value = "รหัสประเภทงาน", required = true)
            @PathParam("jobType") String jobType
    ) {
        LOG.info("list...");
        LOG.info("id..." + id);
        LOG.info("jobType..." + jobType);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
//        Response.Status status = Response.Status.NOT_FOUND;
        Response.Status status = Response.Status.OK;
        responseData.put("data", new ArrayList<>());
//        responseData.put("success", false);
        responseData.put("message", "SubmoduleUserAuth list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            TempTableService tempTableService = new TempTableService();
            List<TempTable> listTempTable = tempTableService.listByJobType(Integer.parseInt(httpHeaders.getHeaderString("userID")), jobType, "", "");
//            List<TempTable> listTempTable = tempTableService.listByComputerNameAndJobType(1, "127.0.0.1", jobType, "", "");

            if (!listTempTable.isEmpty()) {
//            if (listTempTable.size() != 0) {
                for (TempTable tempTable : listTempTable) {
                    tempTableService.delete(tempTable);
                }
            }

            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            WfFolderService wfFolderService = new WfFolderService();
            WfFolder wfFolder1 = wfFolderService.getByIdNotRemoved(id);
            List<WfFolder> listWfFolder = wfFolderService.listByParentId(id);
            if (!listWfFolder.isEmpty()) {
                ArrayList<SubmoduleUserAuthModel> listSubmoduleUserAuthModel = new ArrayList<>();
                for (WfFolder wfFolder : listWfFolder) {
//                    listSubmoduleUserAuthModel = new ArrayList<>();
                    List<SubmoduleUserAuth> listSubmoduleUserAuth = submoduleUserAuthService.listByDmsFolders(wfFolder.getId());
//                    ArrayList<SubmoduleUserAuthModel> listSubmoduleUserAuthModel = new ArrayList<>();
                    if (!listSubmoduleUserAuth.isEmpty()) {
                        listSubmoduleUserAuthModel = new ArrayList<>();
                        for (SubmoduleUserAuth submoduleUserAuth : listSubmoduleUserAuth) {
                            listSubmoduleUserAuthModel.add(submoduleUserAuthService.tranformToModel(submoduleUserAuth));
                        }
//                    }
                        if (!listSubmoduleUserAuthModel.isEmpty()) {
                            listSubmoduleUserAuthModel.forEach(model -> {
                                TempTable tempTable = new TempTable();
                                String subName = "";
//                        DmsFolder dmsFolder1 = new DmsFolderService().getByIdNotRemoved(model.getLinkId());

                                tempTable.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                tempTable.setComputerName(httpHeaders.getHeaderString("clientIp"));
//                tempTable.setCreatedBy(1);
//                tempTable.setComputerName("127.0.0.1");
                                tempTable.setJobType(jobType);
                                tempTable.setInt01(model.getId());
                                tempTable.setInt01(model.getSubmoduleAuth().getAuth().getId());
                                tempTable.setStr01(wfFolder1.getWfFolderName());
                                tempTable.setStr02(wfFolder.getWfFolderName());
                                tempTable.setStr03(model.getSubmoduleAuth().getAuth().getAuthName());
                                if (model.getStructure() != null) {
                                    subName = model.getStructure().getName();

                                } else if (model.getUserProfile() != null) {
                                    subName = model.getUserProfile().getTitle().getName() + model.getUserProfile().getFullName();
                                }

                                tempTable.setStr04(subName);
                                tempTable.setStr05("1".equalsIgnoreCase(model.getAuthority()) ? "/" : "X");
//                            tempTable.setText01(model.getModuleIcon());

                                if (tempTable != null) {
                                    tempTable = tempTableService.create(tempTable);
                                    tempTable.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                    tempTable.setOrderNo(tempTable.getId());
                                    tempTable = tempTableService.update(tempTable);
                                }
                            });
                        }
                    }
                    listSubmoduleUserAuthModel.trimToSize();
                    status = Response.Status.OK;
                    responseData.put("data", listSubmoduleUserAuthModel);
                    responseData.put("message", "");
                }
            }
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
            value = "Method for list SubmoduleUserAuth.",
            notes = "รายการสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบจัดเก็บ",
            responseContainer = "List",
            response = Auth.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth list success.")
        ,
        @ApiResponse(code = 404, message = "SubmoduleUserAuth list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/dmsFolderUser/{id}/{auth}/report/{jobType}")
    public Response listByDmsFolderUser(
            @ApiParam(name = "id", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("id") int id,
            @ApiParam(name = "auth", value = "รหัสสิทธิ", required = true)
            @PathParam("auth") int auth,
            @ApiParam(name = "jobType", value = "รหัสประเภทงาน", required = true)
            @PathParam("jobType") String jobType
    ) {
        LOG.info("list...");
        LOG.info("id..." + id);
        LOG.info("auth..." + auth);
        LOG.info("jobType..." + jobType);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
//        Response.Status status = Response.Status.OK;
        responseData.put("data", new ArrayList<>());
//        responseData.put("success", false);
        responseData.put("message", "SubmoduleUserAuth list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            TempTableService tempTableService = new TempTableService();
            List<TempTable> listTempTable = tempTableService.listByJobType(Integer.parseInt(httpHeaders.getHeaderString("userID")), jobType, "", "");
//            List<TempTable> listTempTable = tempTableService.listByComputerNameAndJobType(1, "127.0.0.1", jobType, "", "");

            if (!listTempTable.isEmpty()) {
//            if (listTempTable.size() != 0) {
                for (TempTable tempTable : listTempTable) {
                    tempTableService.delete(tempTable);
                }
            }

            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            DmsFolderService dmsFolderService = new DmsFolderService();
            DmsFolder dmsFolder1 = dmsFolderService.getByIdNotRemoved(id);
            List<DmsFolder> listDmsFolder = dmsFolderService.listFolderByparenID(id, 0, 500);
            if (!listDmsFolder.isEmpty()) {
                ArrayList<SubmoduleUserAuthModel> listSubmoduleUserAuthModel = new ArrayList<>();
                for (DmsFolder dmsFolder : listDmsFolder) {
//                    listSubmoduleUserAuthModel = new ArrayList<>();
                    List<SubmoduleUserAuth> listSubmoduleUserAuth = submoduleUserAuthService.listUserByDmsFoldersAuth(dmsFolder.getId(), auth);
                    if (!listSubmoduleUserAuth.isEmpty()) {
                        listSubmoduleUserAuthModel = new ArrayList<>();
                        for (SubmoduleUserAuth submoduleUserAuth : listSubmoduleUserAuth) {
                            listSubmoduleUserAuthModel.add(submoduleUserAuthService.tranformToModel(submoduleUserAuth));
                        }
                    }
                    if (!listSubmoduleUserAuthModel.isEmpty()) {
                        listSubmoduleUserAuthModel.forEach(model -> {
                            TempTable tempTable = new TempTable();
                            String subName = "";
//                        DmsFolder dmsFolder1 = new DmsFolderService().getByIdNotRemoved(model.getLinkId());
                            if (model.getUserProfile() != null) {
                                tempTable.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                tempTable.setComputerName(httpHeaders.getHeaderString("clientIp"));
//                tempTable.setCreatedBy(1);
//                tempTable.setComputerName("127.0.0.1");
                                tempTable.setJobType(jobType);
                                tempTable.setInt01(model.getId());
                                tempTable.setStr01(dmsFolder1.getDmsFolderName());
                                tempTable.setStr02(dmsFolder.getDmsFolderName());
                                tempTable.setStr03(model.getSubmoduleAuth().getAuth().getAuthName());
                                tempTable.setStr04(model.getUserProfile().getUser().getName());
                                tempTable.setStr05(model.getUserProfile().getTitle().getName() + model.getUserProfile().getFullName());

                                if (tempTable != null) {
                                    tempTable = tempTableService.create(tempTable);
                                    tempTable.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                    tempTable.setOrderNo(tempTable.getId());
                                    tempTable = tempTableService.update(tempTable);
                                }

                            }
                        });
                    }
                }
                listSubmoduleUserAuthModel.trimToSize();
//                status = Response.Status.OK;
                responseData.put("data", listSubmoduleUserAuthModel);
                responseData.put("message", "");

            }
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

    @ApiOperation(
            value = "Method for list SubmoduleUserAuth.",
            notes = "รายการสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบจัดเก็บ",
            responseContainer = "List",
            response = Auth.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth list success.")
        ,
        @ApiResponse(code = 404, message = "SubmoduleUserAuth list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/wfFolderUser/{id}/{auth}/report/{jobType}")
    public Response listByWfFolderUser(
            @ApiParam(name = "id", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("id") int id,
            @ApiParam(name = "auth", value = "รหัสสิทธิ", required = true)
            @PathParam("auth") int auth,
            @ApiParam(name = "jobType", value = "รหัสประเภทงาน", required = true)
            @PathParam("jobType") String jobType
    ) {
        LOG.info("list...");
        LOG.info("id..." + id);
        LOG.info("auth..." + auth);
        LOG.info("jobType..." + jobType);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
//        Response.Status status = Response.Status.OK;
        responseData.put("data", new ArrayList<>());
//        responseData.put("success", false);
        responseData.put("message", "SubmoduleUserAuth list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            TempTableService tempTableService = new TempTableService();
            List<TempTable> listTempTable = tempTableService.listByJobType(Integer.parseInt(httpHeaders.getHeaderString("userID")), jobType, "", "");
//            List<TempTable> listTempTable = tempTableService.listByComputerNameAndJobType(1, "127.0.0.1", jobType, "", "");

            if (!listTempTable.isEmpty()) {
//            if (listTempTable.size() != 0) {
                for (TempTable tempTable : listTempTable) {
                    tempTableService.delete(tempTable);
                }
            }

            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            WfFolderService wfFolderService = new WfFolderService();
            WfFolder wfFolder1 = wfFolderService.getByIdNotRemoved(id);
            List<WfFolder> listWfFolder = wfFolderService.listByParentId(id);
            if (!listWfFolder.isEmpty()) {
                ArrayList<SubmoduleUserAuthModel> listSubmoduleUserAuthModel = new ArrayList<>();
                for (WfFolder wfFolder : listWfFolder) {
//                    listSubmoduleUserAuthModel = new ArrayList<>();
                    List<SubmoduleUserAuth> listSubmoduleUserAuth = submoduleUserAuthService.listUserByDmsFoldersAuth(wfFolder.getId(), auth);
                    if (!listSubmoduleUserAuth.isEmpty()) {
                        listSubmoduleUserAuthModel = new ArrayList<>();
                        for (SubmoduleUserAuth submoduleUserAuth : listSubmoduleUserAuth) {
                            listSubmoduleUserAuthModel.add(submoduleUserAuthService.tranformToModel(submoduleUserAuth));
                        }
                    }
                    if (!listSubmoduleUserAuthModel.isEmpty()) {
                        listSubmoduleUserAuthModel.forEach(model -> {
                            TempTable tempTable = new TempTable();
                            String subName = "";
//                        DmsFolder dmsFolder1 = new DmsFolderService().getByIdNotRemoved(model.getLinkId());
                            if (model.getUserProfile() != null) {
                                tempTable.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                tempTable.setComputerName(httpHeaders.getHeaderString("clientIp"));
//                tempTable.setCreatedBy(1);
//                tempTable.setComputerName("127.0.0.1");
                                tempTable.setJobType(jobType);
                                tempTable.setInt01(model.getId());
                                tempTable.setStr01(wfFolder1.getWfFolderName());
                                tempTable.setStr02(wfFolder.getWfFolderName());
                                tempTable.setStr03(model.getSubmoduleAuth().getAuth().getAuthName());
                                tempTable.setStr04(model.getUserProfile().getUser().getName());
                                tempTable.setStr05(model.getUserProfile().getTitle().getName() + model.getUserProfile().getFullName());

                                if (tempTable != null) {
                                    tempTable = tempTableService.create(tempTable);
                                    tempTable.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                                    tempTable.setOrderNo(tempTable.getId());
                                    tempTable = tempTableService.update(tempTable);
                                }

                            }
                        });
                    }
                }
                listSubmoduleUserAuthModel.trimToSize();
//                status = Response.Status.OK;
                responseData.put("data", listSubmoduleUserAuthModel);
                responseData.put("message", "");

            }
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
    
    @ApiOperation(
            value = "Method for list SubmoduleUserAuth.",
            notes = "รายการสิทธิเปิดปิดเมนูของระบบงานย่อยในแต่ละผู้ดูแลระบบ",
            responseContainer = "List",
            response = SubmoduleUserAuthModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth list success.")
        ,
        @ApiResponse(code = 404, message = "SubmoduleUserAuth list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/submoduleUserAuth")
    public Response listMenuAdmin(
            //            @HeaderParam("userID") int userID,
            @ApiParam(name = "offset", value = "ตำแหน่งเริ่มต้น", required = true)
            @DefaultValue("0")
            @QueryParam("offset") int offset,
            @ApiParam(name = "limit", value = "จำนวนข้อมูลที่ต้องการ", required = true)
            @DefaultValue("20")
            @QueryParam("limit") int limit,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("orderNo")
            @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("asc")
            @QueryParam("dir") String dir
    ) {
        LOG.info("list...");
        LOG.info("offset = " + offset);
        LOG.info("limit = " + limit);
        LOG.info("userID = " + Integer.parseInt(httpHeaders.getHeaderString("userID")));
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "SubmoduleUserAuth list not found in the database.");
        responseData.put("errorMessage", "");
        try {
//            List<UserProfile> listUserProfile = new UserProfileService().listByUserId(Integer.parseInt(httpHeaders.getHeaderString("userID")), sort, dir);
            UserProfile userProfile = new UserProfileService().getById(Integer.parseInt(httpHeaders.getHeaderString("userID")));
//            if (!listUserProfile.isEmpty()) {
//                for (UserProfile userProfile : listUserProfile) {
                    if (userProfile.getUserProfileType().getId() == 1 || userProfile.getUserProfileType().getId() == 3) {
                        SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
                        Submodule submodule = new SubmoduleService().getBySubmoduleCode("admin");
                        List<SubmoduleUserAuth> listSubmoduleUserAuth = submoduleUserAuthService.getAuthorityByUserProfile(submodule, userProfile);
                        if (!listSubmoduleUserAuth.isEmpty()) {
                            ArrayList<SubmoduleUserAuthModel> listSubmoduleUserAuthModel = new ArrayList<>();
                            for (SubmoduleUserAuth submoduleUserAuth : listSubmoduleUserAuth) {
                                if (!submoduleUserAuth.getAuthority().equalsIgnoreCase("2")) {
                                    listSubmoduleUserAuthModel.add(submoduleUserAuthService.tranformToModel(submoduleUserAuth));
                                }
                            }
                            listSubmoduleUserAuthModel.trimToSize();
//                          status = Response.Status.OK;
                            responseData.put("data", listSubmoduleUserAuthModel);
                            responseData.put("message", "");
                        }
                    }
//                }
//            }
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
