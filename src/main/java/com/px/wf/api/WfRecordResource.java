package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.model.ListOptionModel;
import com.px.wf.entity.WfRecord;
import com.px.wf.model.WfRecordModel;
import com.px.wf.service.WfRecordService;
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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author oat
 */
@Api(value = "WfRecord ทึกปฏิบัติงาน")
@Path("v1/wfRecord")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WfRecordResource {

    @Context
    HttpHeaders httpHeaders;

    private static final Logger LOG = Logger.getLogger(WfRecordResource.class.getName());

    @ApiOperation(
            value = "สร้างข้อมูลบันทึกปฏิบัติงาน",
            notes = "สร้างข้อมูลบันทึกปฏิบัติงาน",
            response = WfRecordModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "WfRecord created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            WfRecordModel wfRecordModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(wfRecordModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            WfRecordService wfRecordService = new WfRecordService();
            WfRecord wfRecord = new WfRecord();
            wfRecord.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            wfRecord.setContentId(wfRecordModel.getContentId());
            wfRecord.setDescription(wfRecordModel.getDescription());
            wfRecord = wfRecordService.create(wfRecord);
            wfRecord.setOrderNo(wfRecord.getId());
            wfRecord.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            wfRecordService.update(wfRecord);
            status = Response.Status.CREATED;
            responseData.put("data", wfRecordService.tranformToModel(wfRecord));
            responseData.put("success", true);
            responseData.put("message", "WfField created successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "รายการบันทึกปฏิบัติงานโดยรหัสหนังสือ",
            notes = "รายการบันทึกปฏิบัติงานโดยรหัสหนังสือ",
            responseContainer = "List",
            response = WfRecordModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfRecord list success."),
        @ApiResponse(code = 404, message = "WfRecord list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/list/{contentId}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listByContentId(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "contentId", value = "รหัสหนังสือ", required = true)
            @PathParam("contentId") int contentId
    ) {
        LOG.info("listByContentId...");
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfRecord by id not found in the database.");
        try {
            WfRecordService wfRecordService = new WfRecordService();
            List<WfRecord> listWfRecord = wfRecordService.listByContentId(contentId);
            List<WfRecordModel> listWfRecordModel = new ArrayList<>();
            if (!listWfRecord.isEmpty()) {
                listWfRecord.forEach((wfRecord) -> {
                    listWfRecordModel.add(wfRecordService.tranformToModel(wfRecord));
                });
            }
            status = Response.Status.OK;
            responseData.put("data", listWfRecordModel);
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
