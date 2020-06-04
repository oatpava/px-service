package com.px.authority.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.service.SubmoduleService;
import com.px.authority.entity.SubmoduleAuthTemplate;
import com.px.authority.model.SubmoduleAuthTemplateModel;
import com.px.authority.service.SubmoduleAuthTemplateService;
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
@Api(value = "SubmoduleAuthTemplate รูปแบบการใช้งานระบบงานย่อย")
@Path("v1/authority/submoduleAuthTemplates")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class SubmoduleAuthTemplateResource {

    private static final Logger LOG = Logger.getLogger(SubmoduleAuthTemplateResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create SubmoduleAuthTemplate",
            notes = "สร้างข้อมูลรูปแบบการใช้งานระบบงานย่อย",
            response = SubmoduleAuthTemplateResource.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "SubmoduleAuthTemplate created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            SubmoduleAuthTemplateModel submoduleAuthTemplateModel
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
            SubmoduleAuthTemplate submoduleAuthTemplate = new SubmoduleAuthTemplate();
            submoduleAuthTemplate.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            submoduleAuthTemplate.setSubmoduleAuthTemplateName(submoduleAuthTemplateModel.getSubmoduleAuthTemplateName());

            SubmoduleService submoduleService = new SubmoduleService();
            submoduleAuthTemplate.setSubmodule(submoduleService.getById(submoduleAuthTemplateModel.getSubmodule().getId()));

            SubmoduleAuthTemplateService submoduleAuthTemplateService = new SubmoduleAuthTemplateService();
            submoduleAuthTemplate = submoduleAuthTemplateService.create(submoduleAuthTemplate);
            status = Response.Status.CREATED;
            responseData.put("data", submoduleAuthTemplateService.tranformToModel(submoduleAuthTemplate));
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get SubmoduleAuthTemplate by id",
            notes = "ขอข้อมูลรูปแบบการใช้งานระบบงานย่อย ด้วย รหัสรูปแบบการใช้งานระบบงานย่อย",
            response = SubmoduleAuthTemplateModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleAuthTemplate by id success."),
        @ApiResponse(code = 404, message = "SubmoduleAuthTemplate by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @HeaderParam("userID") int userID,
            @ApiParam(name = "id", value = "รหัสรูปแบบการใช้งานระบบงานย่อย", required = true)
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
        responseData.put("message", "SubmoduleAuthTemplate by id not found in the database.");
        try {
            SubmoduleAuthTemplateService submoduleAuthTemplateService = new SubmoduleAuthTemplateService();
            SubmoduleAuthTemplate submoduleAuthTemplate = submoduleAuthTemplateService.getById(id);
            if (submoduleAuthTemplate != null) {
                status = Response.Status.OK;
                responseData.put("data", submoduleAuthTemplateService.tranformToModel(submoduleAuthTemplate));
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
            value = "Method for update SubmoduleAuthTemplate.",
            notes = "แก้ไขข้อมูลรูปแบบการใช้งานระบบงานย่อย",
            response = SubmoduleAuthTemplateModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleAuthTemplate updeted by id success."),
        @ApiResponse(code = 404, message = "SubmoduleAuthTemplate by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(
            SubmoduleAuthTemplateModel submoduleAuthTemplateModel
    ) {
        LOG.info("update...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "SubmoduleAuthTemplate by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            SubmoduleAuthTemplateService submoduleAuthTemplateService = new SubmoduleAuthTemplateService();
            SubmoduleAuthTemplate submoduleAuthTemplate = submoduleAuthTemplateService.getById(submoduleAuthTemplateModel.getId());
            if (submoduleAuthTemplate != null) {
                submoduleAuthTemplate.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                submoduleAuthTemplate.setSubmoduleAuthTemplateName(submoduleAuthTemplateModel.getSubmoduleAuthTemplateName());

                SubmoduleService submoduleService = new SubmoduleService();
                submoduleAuthTemplate.setSubmodule(submoduleService.getById(submoduleAuthTemplateModel.getSubmodule().getId()));

                submoduleAuthTemplate = submoduleAuthTemplateService.update(submoduleAuthTemplate);
                status = Response.Status.OK;
                responseData.put("data", submoduleAuthTemplateService.tranformToModel(submoduleAuthTemplate));
                responseData.put("message", "");
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
            value = "Method for delete SubmoduleAuthTemplate by id.",
            notes = "ลบข้อมูลรูปแบบการใช้งานระบบงานย่อย",
            response = SubmoduleAuthTemplateModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleAuthTemplate deleted by id success."),
        @ApiResponse(code = 404, message = "SubmoduleAuthTemplate by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @ApiParam(name = "id", value = "รหัสรูปแบบการใช้งานระบบงานย่อย", required = true)
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
        responseData.put("message", "SubmoduleAuthTemplate by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            SubmoduleAuthTemplateService submoduleAuthTemplateService = new SubmoduleAuthTemplateService();
            SubmoduleAuthTemplate submoduleAuthTemplate = submoduleAuthTemplateService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (submoduleAuthTemplate != null) {
                status = Response.Status.OK;
                responseData.put("data", submoduleAuthTemplateService.tranformToModel(submoduleAuthTemplate));
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
            value = "Method for list SubmoduleAuthTemplate.",
            notes = "รายการรูปแบบการใช้งานระบบงานย่อย",
            responseContainer = "List",
            response = SubmoduleAuthTemplateModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleAuthTemplate list success."),
        @ApiResponse(code = 404, message = "SubmoduleAuthTemplate list not found in the database."),
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
        responseData.put("message", "SubmoduleAuthTemplate list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            SubmoduleAuthTemplateService submoduleAuthTemplateService = new SubmoduleAuthTemplateService();
            List<SubmoduleAuthTemplate> listSubmoduleAuthTemplate = submoduleAuthTemplateService.listAll(sort, dir);
            ArrayList<SubmoduleAuthTemplateModel> listSubmoduleAuthTemplateModel = new ArrayList<>();
            if (!listSubmoduleAuthTemplate.isEmpty()) {
                for (SubmoduleAuthTemplate submoduleAuthTemplate : listSubmoduleAuthTemplate) {
                    listSubmoduleAuthTemplateModel.add(submoduleAuthTemplateService.tranformToModel(submoduleAuthTemplate));
                }
                listSubmoduleAuthTemplateModel.trimToSize();
            }
            status = Response.Status.OK;
            responseData.put("data", listSubmoduleAuthTemplateModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
