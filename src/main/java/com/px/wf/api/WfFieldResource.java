package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.model.VersionModel;
import com.px.wf.entity.WfField;
import com.px.wf.model.WfFieldModel;
import com.px.wf.service.WfFieldService;
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
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

/**
 *
 * @author Mali
 */
@Api(value = "WfField ชื่อฟิลด์ของหนังสือสารบรรณ")
@Path("v1/wfFields")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WfFieldResource {

    private static final Logger log = Logger.getLogger(WfFieldResource.class.getName());

    @ApiOperation(
            value = "สร้างข้อมูลชื่อฟิลด์ของหนังสือสารบรรณ",
            notes = "สร้างข้อมูลชื่อฟิลด์ของหนังสือสารบรรณ",
            response = WfFieldModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "WfField created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            WfFieldModel wfFieldPostModel
    ) {
        log.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(wfFieldPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            WfField wfField = new WfField();
            //get Data from WfFieldModel
            wfField.setCreatedBy(wfFieldPostModel.getUserId());
            wfField.setWfFieldName(wfFieldPostModel.getWfFieldName());
            wfField.setWfFieldDescription(wfFieldPostModel.getWfFieldDescription());
            wfField.setWfFieldType(wfFieldPostModel.getWfFieldType());
            wfField.setWfFieldLength(wfFieldPostModel.getWfFieldLength());

            if (wfField != null) {
                WfFieldService wfFieldService = new WfFieldService();
                wfFieldService.create(wfField);
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
            value = "ขอข้อมูลชื่อฟิลด์ของหนังสือสารบรรณ ด้วย รหัสชื่อฟิลด์ของหนังสือสารบรรณ",
            notes = "ขอข้อมูลชื่อฟิลด์ของหนังสือสารบรรณ ด้วย รหัสชื่อฟิลด์ของหนังสือสารบรรณ",
            response = WfFieldModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfField by id success."),
        @ApiResponse(code = 404, message = "WfField by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสชื่อฟิลด์ของหนังสือสารบรรณ", required = true)
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
        responseData.put("message", "WfField by id not found in the database.");
        try {
            WfFieldService wfFieldService = new WfFieldService();
            WfField wfField = wfFieldService.getById(id);
            if (wfField != null) {
                status = Response.Status.OK;
                responseData.put("data", wfFieldService.tranformToModel(wfField));
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
            value = "รายการทั้งหมดประเภทของชื่อฟิลด์ของหนังสือสารบรรณ",
            notes = "รายการทั้งหมดประเภทของชื่อฟิลด์ของหนังสือสารบรรณ",
            responseContainer = "List",
            response = WfFieldModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfField list success."),
        @ApiResponse(code = 404, message = "WfField list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listAll(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("desc") @QueryParam("dir") String dir
    ) {
        log.info("listAll...");
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
        responseData.put("message", "WfField by id not found in the database.");
        try {
            WfFieldService wfFieldService = new WfFieldService();
            List<WfField> listWfField = wfFieldService.listAll(sort, dir);
            if (!listWfField.isEmpty()) {
                ArrayList<WfFieldModel> listWfFieldModel = new ArrayList<>();
                for (WfField wfField : listWfField) {
                    listWfFieldModel.add(wfFieldService.tranformToModel(wfField));
                }
                listWfFieldModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listWfFieldModel);
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
