package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.model.ListOptionModel;
import com.px.share.model.VersionModel;
import com.px.wf.entity.WfContentType;
import com.px.wf.model.WfContentTypeModel;
import com.px.wf.service.WfContentTypeService;
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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

/**
 *
 * @author Mali
 */
@Api(value = "WfContentType ประเภทของสารบรรณ")
@Path("v1/wfContentTypes")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WfContentTypeResource {

    private static final Logger log = Logger.getLogger(WfContentTypeResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "สร้างข้อมูลประเภทของสารบรรณ",
            notes = "สร้างข้อมูลประเภทของสารบรรณ",
            response = WfContentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "WfContentType created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            WfContentTypeModel wfContentTypeModel
    ) {
        log.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(wfContentTypeModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            WfContentType wfContentType = new WfContentType();
            WfContentTypeService wfContentTypeService = new WfContentTypeService();

            //get Data from wfContentTypeModel
            wfContentType.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            wfContentType.setContentTypeName(wfContentTypeModel.getContentTypeName());
            wfContentType.setContentTypeChild(wfContentTypeModel.getContentTypeChild());

            if (wfContentType != null) {
                wfContentType = wfContentTypeService.create(wfContentType);

                wfContentType.setOrderNo(wfContentType.getId());
                wfContentType.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                wfContentType = wfContentTypeService.update(wfContentType);

                responseData.put("data", wfContentTypeService.tranformToModel(wfContentType));
            }

            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "WfField created successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "แก้ไขข้อมูลประเภทของสารบรรณ",
            notes = "แก้ไขข้อมูลประเภทของสารบรรณ",
            response = WfContentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContentType updeted by id success."),
        @ApiResponse(code = 404, message = "WfContentType by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            WfContentTypeModel wfContentTypeModel
    ) {
        log.info("update...");
        log.info("id = " + wfContentTypeModel.getId());
        Gson gs = new GsonBuilder()
                .setVersion(wfContentTypeModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfContentType by id not found in the database.");
        try {
            WfContentTypeService wfContentTypeService = new WfContentTypeService();
            WfContentType wfContentType = wfContentTypeService.getById(wfContentTypeModel.getId());

            if (wfContentType != null) {

                wfContentType.setContentTypeName(wfContentTypeModel.getContentTypeName());
                wfContentType.setContentTypeChild(wfContentTypeModel.getContentTypeChild());

                wfContentType.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                wfContentType = wfContentTypeService.update(wfContentType);

                status = Response.Status.OK;
                responseData.put("data", wfContentTypeService.tranformToModel(wfContentType));
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ลบข้อมูลประเภทของสารบรรณ ด้วย รหัสประเภทของสารบรรณ",
            notes = "ลบข้อมูลประเภทของสารบรรณ ด้วย รหัสประเภทของสารบรรณ",
            response = WfContentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContentType delete by id success."),
        @ApiResponse(code = 404, message = "WfContentType by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("id") int id
    ) {
        log.info("remove...");
        log.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfContentType by id not found in the database.");
        try {
            WfContentTypeService wfContentTypeService = new WfContentTypeService();
            WfContentType wfContentType = wfContentTypeService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (wfContentType != null) {
                status = Response.Status.OK;
                responseData.put("data", wfContentTypeService.tranformToModel(wfContentType));
                responseData.put("message", "");
            }
            responseData.put("success", true);

        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลประเภทสารบรรณ ด้วย รหัสประเภทสารบรรณ",
            notes = "ขอข้อมูลประเภทสารบรรณ ด้วย รหัสประเภทสารบรรณ",
            response = WfContentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContentType by id success."),
        @ApiResponse(code = 404, message = "WfContentType by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสประเภทสารบรรณ", required = true)
            @PathParam("id") int id
    ) {
        log.info("getById...");
        log.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfContentType by id not found in the database.");
        try {
            WfContentTypeService contentTypeService = new WfContentTypeService();
            WfContentType contentType = contentTypeService.getById(id);
            if (contentType != null) {
                status = Response.Status.OK;
                responseData.put("data", contentTypeService.tranformToModel(contentType));
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "รายการทั้งหมดประเภทของสารบรรณ",
            notes = "รายการทั้งหมดประเภทของสารบรรณ",
            responseContainer = "List",
            response = WfContentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContentType list success."),
        @ApiResponse(code = 404, message = "WfContentType list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listAll(
            @BeanParam ListOptionModel listOptionModel
    ) {
        log.info("listAll...");
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfContentType by id not found in the database.");
        try {
            WfContentTypeService contentTypeService = new WfContentTypeService();
            List<WfContentType> listContentType = contentTypeService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if (!listContentType.isEmpty()) {
                ArrayList<WfContentTypeModel> listContentTypeModel = new ArrayList<>();
                for (WfContentType contentType : listContentType) {
                    listContentTypeModel.add(contentTypeService.tranformToModel(contentType));
                }
                listContentTypeModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listContentTypeModel);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
