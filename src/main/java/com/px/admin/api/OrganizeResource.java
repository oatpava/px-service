package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Organize;
import com.px.admin.model.OrganizeModel;
import com.px.admin.service.OrganizeService;
import com.px.share.model.AuthenticationModel;
import com.px.share.model.ListOptionModel;
import com.px.share.model.VersionModel;
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
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author PRAXiS
 */
@Api(value = "Organize โครงสร้างหน่วยงานภายนอก")
@Path("v1/organizes")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class OrganizeResource {

    private static final Logger LOG = Logger.getLogger(OrganizeResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create Organize",
            notes = "สร้างข้อมูลโครงสร้างหน่วยงานภายนอก",
            response = OrganizeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Organize created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            OrganizeModel organizeModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(organizeModel.getVersion())
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
            Organize organize = new Organize();
            organize.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            organize.setOrganizeDetail(organizeModel.getDetail());
            organize.setOrganizeShortName(organizeModel.getShortName());
            organize.setOrganizeName(organizeModel.getName());
            organize.setParentId(organizeModel.getParentId());
            organize.setOrganizeCode(organizeModel.getCode());
            OrganizeService organizeService = new OrganizeService();
            organize = organizeService.create(organize);
            responseData.put("data", organizeService.tranformToModel(organize));
            responseData.put("message", "Organize created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);

            //LogData For Create
//            organizeService.saveLogForCreate(organize, httpHeaders.getHeaderString("clientIp"));
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get Organize by id",
            notes = "ขอข้อมูลโครงสร้างหน่วยงานภายนอก ด้วย รหัสโครงสร้างหน่วยงานภายนอก",
            response = OrganizeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Organize by id success.")
        ,
        @ApiResponse(code = 404, message = "Organize by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสโครงสร้างหน่วยงานภายนอก", required = true)
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
        responseData.put("message", "Organize by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            OrganizeService organizeService = new OrganizeService();
            Organize organize = organizeService.getById(id);
            if (organize != null) {
                status = Response.Status.OK;
                responseData.put("data", organizeService.tranformToModel(organize));
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
            value = "Method for update Organize.",
            notes = "แก้ไขข้อมูลโครงสร้างหน่วยงานภายนอก",
            response = OrganizeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Organize updeted by id success.")
        ,@ApiResponse(code = 404, message = "Organize by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @ApiParam(name = "id", value = "รหัสโครงสร้างหน่วยงานภายนอก", required = true)
            @PathParam("id") int id,
            OrganizeModel organizeModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(organizeModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Organize by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            OrganizeService organizeService = new OrganizeService();
            Organize organize = organizeService.getById(id);
            Organize organizeUpdate = null;
            if (organize != null) {
                organize.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                organize.setParentId(organizeModel.getParentId());
                organize.setOrganizeDetail(organizeModel.getDetail());
                organize.setOrganizeName(organizeModel.getName());
                organize.setOrganizeShortName(organizeModel.getShortName());
                organize.setOrganizeCode(organizeModel.getCode());
                organizeUpdate = organizeService.update(organize);
                status = Response.Status.OK;
                responseData.put("data", organizeService.tranformToModel(organizeUpdate));
                responseData.put("message", "");

                //LogData For Update
//                organizeService.saveLogForUpdate(organize, organizeUpdate, httpHeaders.getHeaderString("clientIp"));
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
            value = "Method for delete Organize by id.",
            notes = "ลบข้อมูลโครงสร้างหน่วยงานภายนอก",
            response = OrganizeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Organize deleted by id success.")
        ,@ApiResponse(code = 404, message = "Organize by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสโครงสร้างหน่วยงานภายนอก", required = true)
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
        responseData.put("message", "Organize by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            OrganizeService organizeService = new OrganizeService();
            Organize organize = organizeService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (organize != null) {
                status = Response.Status.OK;
                responseData.put("data", organizeService.tranformToModel(organize));
                responseData.put("message", "");

                //LogData For Remove
//                organizeService.saveLogForRemove(organize, httpHeaders.getHeaderString("clientIp"));
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
            value = "Method for list Organize By Organize Id.",
            notes = "รายการโครงสร้างหน่วยงานภายนอก",
            responseContainer = "List",
            response = OrganizeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Organize list By Organize Id success.")
        ,@ApiResponse(code = 404, message = "Organize list By Organize Id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response list(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "organizeId", value = "รหัสหน่วยงานภายนอก", required = true)
            @DefaultValue("0")
            @QueryParam("organizeId") int organizeId
    ) {
        LOG.debug("list...");
        LOG.debug("offset = " + listOptionModel.getOffset());
        LOG.debug("limit = " + listOptionModel.getLimit());
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        ArrayList<OrganizeModel> listOrganizeModel = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", listOrganizeModel);
        responseData.put("success", false);
        responseData.put("message", "Organize list By Organize Id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            Organize organizeInput = new Organize();
            organizeInput.setId(organizeId);
            OrganizeService organizeService = new OrganizeService();
            List<Organize> listOrganize = organizeService.listChild(organizeInput, listOptionModel.getSort(), listOptionModel.getDir());
            if (!listOrganize.isEmpty()) {
                listOrganizeModel = new ArrayList<>();
                for (Organize organize : listOrganize) {
                    listOrganizeModel.add(organizeService.tranformToModel(organize));
                }
                listOrganizeModel.trimToSize();
                responseData.put("data", listOrganizeModel);
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
            value = "Method for list All Organize.",
            notes = "รายการโครงสร้างหน่วยงานทั้งหมด",
            responseContainer = "List",
            response = OrganizeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Organize list success.")
        ,
        @ApiResponse(code = 404, message = "Organize list not found in the database.")
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
        ArrayList<OrganizeModel> listOrganizeModel = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", listOrganizeModel);
        responseData.put("success", false);
        responseData.put("message", "Organize list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            OrganizeService organizeService = new OrganizeService();
            List<Organize> listOrganize = organizeService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if (!listOrganize.isEmpty()) {
                listOrganizeModel = new ArrayList<>();
                for (Organize organize : listOrganize) {
                    listOrganizeModel.add(organizeService.tranformToModel(organize));
                }
                listOrganizeModel.trimToSize();
                responseData.put("data", listOrganizeModel);
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
            value = "Method for check Duplicate Organize by code.",
            notes = "เช็คค่าซ้ำ",
            response = AuthenticationModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Organize check duplicate by code success.", response = AuthenticationModel.class)
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/checkDup/{code}/name/{name}/id/{id}")
    public Response checkDup(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "code", value = "รหัสอ้างอิงโครงสร้าง", required = true)
            @PathParam("code") String code,
            @ApiParam(name = "name", value = "ชื่อโครงสร้าง", required = true)
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
        responseData.put("message", "Organize by id not found in the database.");
        responseData.put("errorMessage", "");
        AuthenticationModel authenticationModel = new AuthenticationModel(false);
        responseData.put("data", authenticationModel);
        try {
            OrganizeService organizeService = new OrganizeService();
            if (id != 0) {
                Organize organize = organizeService.getById(id);
                if (!code.equals(organize.getOrganizeCode()) || !name.equals(organize.getOrganizeName())) {
                    boolean result = organizeService.checkDup(code,name);
                    if (result) {
                        authenticationModel = new AuthenticationModel(result);
                        responseData.put("message", "");
                    }
                } else {
                    authenticationModel = new AuthenticationModel(false);
                }
                responseData.put("data", authenticationModel);
            } else {
                boolean result = organizeService.checkDup(code,name);
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
}
