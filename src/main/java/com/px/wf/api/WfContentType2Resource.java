package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.model.ListOptionModel;
import com.px.share.model.VersionModel;
import com.px.wf.entity.WfContentType2;
import com.px.wf.entity.WfContentType;
import com.px.wf.model.WfContentType2Model;
import com.px.wf.service.WfContentType2Service;
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
@Api(value = "WfContentType2 ประเภทย่อยของประเภทสารบรรณ")
@Path("v1/wfContentType2s")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WfContentType2Resource {

    private static final Logger log = Logger.getLogger(WfContentType2Resource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "สร้างข้อมูลประเภทย่อยของประเภทสารบรรณ",
            notes = "สร้างข้อมูลประเภทย่อยของประเภทสารบรรณ",
            response = WfContentType2Model.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "WfContentType2 created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            WfContentType2Model wfContentType2Model
    ) {
        log.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(wfContentType2Model.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            WfContentType2 wfContentType2 = new WfContentType2();
            WfContentType2Service wfContentType2Service = new WfContentType2Service();

            //get Data from wfContentTypeModel
            wfContentType2.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            wfContentType2.setContentType2Name(wfContentType2Model.getWfContentType2Name());

            wfContentType2.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            wfContentType2 = wfContentType2Service.update(wfContentType2);

            if (wfContentType2 != null) {
                wfContentType2 = wfContentType2Service.create(wfContentType2);

                wfContentType2.setOrderNo(wfContentType2.getId());
                wfContentType2.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                wfContentType2 = wfContentType2Service.update(wfContentType2);

                responseData.put("data", wfContentType2Service.tranformToModel(wfContentType2));
            }

            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "wfContentType2 created successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "แก้ไขข้อมูลประเภทย่อยของประเภทสารบรรณ",
            notes = "แก้ไขข้อมูลประเภทย่อยของประเภทสารบรรณ",
            response = WfContentType2Model.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContentType2 updeted by id success."),
        @ApiResponse(code = 404, message = "WfContentType2 by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            WfContentType2Model wfContentType2Model
    ) {
        log.info("update...");
        log.info("id = " + wfContentType2Model.getId());
        Gson gs = new GsonBuilder()
                .setVersion(wfContentType2Model.getVersion())
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
            WfContentType2Service wfContentType2Service = new WfContentType2Service();
            WfContentType2 wfContentType2 = wfContentType2Service.getById(wfContentType2Model.getId());

            if (wfContentType2 != null) {
                wfContentType2.setContentType2Name(wfContentType2Model.getWfContentType2Name());

                wfContentType2.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                wfContentType2 = wfContentType2Service.update(wfContentType2);

                status = Response.Status.OK;
                responseData.put("data", wfContentType2Service.tranformToModel(wfContentType2));
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
            value = "ลบข้อมูลประเภทย่อยของสารบรรณ ด้วย รหัสประเภทย่อยของสารบรรณ",
            notes = "ลบข้อมูลประเภทย่อยของสารบรรณ ด้วย รหัสประเภทย่อยของสารบรรณ",
            response = WfContentType2Model.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContentType2 delete by id success."),
        @ApiResponse(code = 404, message = "WfContentType2 by id not found in the database."),
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
        responseData.put("message", "WfContentType2 by id not found in the database.");
        try {
            WfContentType2Service wfContentType2Service = new WfContentType2Service();
            WfContentType2 wfContentType2 = wfContentType2Service.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (wfContentType2 != null) {
                status = Response.Status.OK;
                responseData.put("data", wfContentType2Service.tranformToModel(wfContentType2));
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
            value = "ประเภทย่อยของประเภทสารบรรณ",
            notes = "ประเภทย่อยของประเภทสารบรรณ",
            responseContainer = "List",
            response = WfContentType2Model.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContentType2 list success."),
        @ApiResponse(code = 404, message = "WfContentType2 list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listByContentTypeId/{contentTypeId}")
    public Response listByContentTypeId(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "contentTypeId", value = "รหัสประเภทของสารบรรณ", required = true)
            @PathParam("contentTypeId") int contentTypeId
    ) {
        log.info("listByContentTypeId...");
        log.info("contentTypeId = " + contentTypeId);
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
        responseData.put("message", "WfContentType2 by id not found in the database.");
        try {
            WfContentType2Service contentType2Service = new WfContentType2Service();
            WfContentTypeService contentTypeService = new WfContentTypeService();
            WfContentType contentType = contentTypeService.getById(contentTypeId);
            String childContentType = contentType.getContentTypeChild();

            if (!childContentType.equals(null) && !childContentType.equals("")) {
                List<Integer> listContentTypeId = new ArrayList<Integer>();
                String[] tmpContentTypeId = childContentType.split(",");
                for (int i = 0; i < tmpContentTypeId.length; i++) {
                    listContentTypeId.add(Integer.parseInt(tmpContentTypeId[i]));
                }

                List<WfContentType2> listContentType2 = contentType2Service.listByWfContentType(listContentTypeId,listOptionModel.getSort(),listOptionModel.getDir());
                if (!listContentType2.isEmpty()) {
                    ArrayList<WfContentType2Model> listContentType2Model = new ArrayList<>();
                    for (WfContentType2 contentType2 : listContentType2) {
                        listContentType2Model.add(contentType2Service.tranformToModel(contentType2));
                    }
                    listContentType2Model.trimToSize();
                    status = Response.Status.OK;
                    responseData.put("data", listContentType2Model);
                    responseData.put("message", "");
                }
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "รายการทั้งหมดประเภทย่อยของประเภทสารบรรณ",
            notes = "รายการทั้งหมดประเภทย่อยของประเภทสารบรรณ",
            responseContainer = "List",
            response = WfContentType2Model.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContentType2 list success."),
        @ApiResponse(code = 404, message = "WfContentType2 list not found in the database."),
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
            WfContentType2Service contentType2Service = new WfContentType2Service();
            List<WfContentType2> listContentType2 = contentType2Service.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if (!listContentType2.isEmpty()) {
                ArrayList<WfContentType2Model> listContentType2Model = new ArrayList<>();
                for (WfContentType2 contentType2 : listContentType2) {
                    listContentType2Model.add(contentType2Service.tranformToModel(contentType2));
                }
                listContentType2Model.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listContentType2Model);
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
            value = "ขอข้อมูลประเภทย่อยของประเภทสารบรรณ ด้วย รหัสประเภทย่อยของประเภทสารบรรณ",
            notes = "ขอข้อมูลประเภทย่อยของประเภทสารบรรณ ด้วย รหัสประเภทย่อยของประเภทสารบรรณ",
            response = WfContentType2Model.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContentType2 by id success."),
        @ApiResponse(code = 404, message = "WfContentType2 by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสประเภทย่อยของประเภทสารบรรณ", required = true)
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
        responseData.put("message", "WfContentType2 by id not found in the database.");
        try {
            WfContentType2Service contentType2Service = new WfContentType2Service();
            WfContentType2 contentType2 = contentType2Service.getById(id);
            if (contentType2 != null) {
                status = Response.Status.OK;
                responseData.put("data", contentType2Service.tranformToModel(contentType2));
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
