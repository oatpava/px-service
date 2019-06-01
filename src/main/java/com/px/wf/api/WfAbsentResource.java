package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.model.ListOptionModel;
import com.px.share.model.VersionModel;
import com.px.share.util.Common;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
import com.px.wf.entity.WfAbsent;
import com.px.wf.model.WfAbsentModel;
import com.px.wf.model.WfAbsentModel_absentDateTo;
import com.px.wf.service.WfAbsentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
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
 * @author CHALERMPOL
 */
@Api(value = "WfAbsent วันหยุดพนักงาน")
@Path("v1/wfAbsent")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WfAbsentResource {

    private static final Logger LOG = Logger.getLogger(WfAbsentResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create WfAbsent",
            notes = "สร้างวันหยุดพนักงาน",
            response = WfAbsentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "WfAbsent created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            WfAbsentModel_absentDateTo wfAbsentModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(wfAbsentModel.getVersion())
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
            WfAbsentService wfAbsentService = new WfAbsentService();
            LocalDateTime absentDateFrom = dateThaiToLocalDateTime(wfAbsentModel.getAbsentDate());
            LocalDateTime absentDateTo = dateThaiToLocalDateTime(wfAbsentModel.getAbsentDateTo());
            while (!absentDateFrom.isAfter(absentDateTo)) {
                WfAbsent wfAbsent = wfAbsentService.checkIfExist(wfAbsentModel.getUserId(), absentDateFrom);
                if (wfAbsent == null) {
                    wfAbsent = new WfAbsent();
                    wfAbsent.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    wfAbsent.setAbsentDate(absentDateFrom);
                    wfAbsent.setUserId(wfAbsentModel.getUserId());
                    wfAbsent = wfAbsentService.create(wfAbsent);
                    //LogData For Create
//                    wfAbsentService.saveLogForCreate(wfAbsent, httpHeaders.getHeaderString("clientIp"));
                }
                absentDateFrom = absentDateFrom.plusDays(1);
            }

            status = Response.Status.CREATED;
//            responseData.put("data", wfAbsentService.tranformToModel(wfAbsent));
            responseData.put("success", true);
            responseData.put("message", "WfAbsent created successfully.");

        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get WfAbsent by id",
            notes = "ขอวันหยุดพนักงาน ด้วย รหัสวันหยุด",
            response = WfAbsentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfAbsent by id success.")
        ,
        @ApiResponse(code = 404, message = "WfAbsent by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสวันหยุด", required = true)
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
        responseData.put("message", "WfAbsent by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfAbsentService wfAbsentService = new WfAbsentService();
            WfAbsent wfAbsent = wfAbsentService.getById(id);
            if (wfAbsent != null) {
                status = Response.Status.OK;
                responseData.put("data", wfAbsentService.tranformToModel(wfAbsent));
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
            value = "Method for update WfAbsent.",
            notes = "แก้ไขวันหยุดพนักงาน",
            response = WfAbsentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfAbsent updeted by id success.")
        ,@ApiResponse(code = 404, message = "WfAbsent by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @ApiParam(name = "id", value = "รหัสวันหยุด", required = true)
            @PathParam("id") int id,
            WfAbsentModel wfAbsentModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(wfAbsentModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfAbsent by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfAbsentService wfAbsentService = new WfAbsentService();
            WfAbsent wfAbsent = wfAbsentService.getById(id);
            WfAbsent wfAbsentUpdate = null;
            if (wfAbsent != null) {
                wfAbsent.setAbsentDate(dateThaiToLocalDateTime(wfAbsentModel.getAbsentDate()));
                wfAbsent.setUserId(wfAbsentModel.getUserId());
                wfAbsent.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                wfAbsentUpdate = wfAbsentService.update(wfAbsent);
                status = Response.Status.OK;
                responseData.put("data", wfAbsentService.tranformToModel(wfAbsentUpdate));
                responseData.put("message", "");

                //LogData For Update
                wfAbsentService.saveLogForUpdate(wfAbsent, wfAbsentUpdate, httpHeaders.getHeaderString("clientIp"));
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
            value = "Method for delete WfAbsent by id.",
            notes = "ลบวันหยุดพนักงาน",
            response = WfAbsentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfAbsent deleted by id success.")
        ,@ApiResponse(code = 404, message = "WfAbsent by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสวันหยุด", required = true)
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
        responseData.put("message", "WfAbsent by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfAbsentService wfAbsentService = new WfAbsentService();
            WfAbsent wfAbsent = wfAbsentService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (wfAbsent != null) {
                status = Response.Status.OK;
                responseData.put("data", wfAbsentService.tranformToModel(wfAbsent));
                responseData.put("message", "");

                //LogData For Remove
                wfAbsentService.saveLogForRemove(wfAbsent, httpHeaders.getHeaderString("clientIp"));
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
            value = "Method for list WfAbsent.",
            notes = "รายการวันหยุด",
            responseContainer = "List",
            response = WfAbsentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfAbsent list success.")
        ,
        @ApiResponse(code = 404, message = "WfAbsent list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response list(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "dateFrom", value = "วันที่เริ่ม", required = false)
            @DefaultValue("0") @QueryParam("dateFrom") String dateFrom,
            @ApiParam(name = "dateTo", value = "วันที่เริ่ม", required = false)
            @DefaultValue("0") @QueryParam("dateTo") String dateTo
    ) {
        LOG.debug("list...");
        LOG.debug("offset = " + listOptionModel.getOffset());
        LOG.debug("limit = " + listOptionModel.getLimit());
        LOG.debug("dateFrom = " + dateFrom);
        LOG.debug("dateTo = " + dateTo);
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
        responseData.put("message", "WfAbsent list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfAbsentService wfAbsentService = new WfAbsentService();
            List<WfAbsent> listWfAbsent = null;
            if (dateFrom.equalsIgnoreCase("0")) {
                listWfAbsent = wfAbsentService.listByCurrentYear(listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir());
            } else {
                listWfAbsent = wfAbsentService.listByDateRange(dateThaiToLocalDateTime(dateFrom), dateThaiToLocalDateTime(dateTo), listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir());
            }
            if (!listWfAbsent.isEmpty()) {
                ArrayList<WfAbsentModel> listWfAbsentModel = new ArrayList<>();
                for (WfAbsent wfAbsent : listWfAbsent) {
                    listWfAbsentModel.add(wfAbsentService.tranformToModel(wfAbsent));
                }
                listWfAbsentModel.trimToSize();
                responseData.put("data", listWfAbsentModel);
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
            value = "Method for list WfAbsent by year.",
            notes = "รายการวันหยุดตามปี",
            responseContainer = "List",
            response = WfAbsentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfAbsent list success.")
        ,
        @ApiResponse(code = 404, message = "WfAbsent list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listAbsentByYear/{year}")
    public Response listByYear(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "year", value = "ปีที่ค้นหา", required = true)
            @PathParam("year") String year
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
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfAbsent list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfAbsentService wfAbsentService = new WfAbsentService();
            List<WfAbsent> listWfAbsent = wfAbsentService.listByDateRange(dateThaiToLocalDateTime("01/01/" + year), dateThaiToLocalDateTime("31/12/" + year), listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir());
            if (!listWfAbsent.isEmpty()) {
                ArrayList<WfAbsentModel> listWfAbsentModel = new ArrayList<>();
                for (WfAbsent wfAbsent : listWfAbsent) {
                    listWfAbsentModel.add(wfAbsentService.tranformToModel(wfAbsent));
                }
                listWfAbsentModel.trimToSize();
                responseData.put("data", listWfAbsentModel);
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
    /*
    @ApiOperation(
        value = "Count workday (Except wfAbsent and weekend)", 
        notes = "นับวันทำการ", 
        response = WfAbsentModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Count workday success." ),
        @ApiResponse( code = 404, message = "Count workday not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/countWorkday")
    public Response countWorkday(
        @BeanParam ListOptionModel listOptionModel,
        @ApiParam(name = "beginDate", value = "วันที่เริ่มต้น", required = true) 
        @DefaultValue("") @QueryParam("beginDate") String beginDate,
        @ApiParam(name = "endDate", value = "วันที่สิ้นสุด", required = true) 
        @DefaultValue("") @QueryParam("endDate") String endDate
    ) {
        LOG.debug("countWorkday...");
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
        responseData.put("message", "WfAbsent by id not found in the database.");
        responseData.put("errorMessage", "");
        try{            
            WfAbsentService wfAbsentService = new WfAbsentService();
            int numwork = wfAbsentService.countWorkday(beginDate,endDate);
            HashMap numMap = new HashMap();
            numMap.put("numWorkday",numwork);
            responseData.put("data", numMap);
            responseData.put("message", "");
            status = Response.Status.OK;
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
     */
}
