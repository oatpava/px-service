package com.px.share.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.entity.TempTable;
import com.px.share.model.TempTableModel;
import com.px.share.service.TempTableService;
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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
@Api(value = "TempTable ตารางชั่วคราว")
@Path("v1/tempTables")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class TempTableResource {

    private static final Logger LOG = Logger.getLogger(TempTableResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create TempTable",
            notes = "สร้างข้อมูลตารางชั่วคราว",
            response = TempTableModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "TempTable created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            TempTableModel tempTablePostModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            TempTable tempTable = new TempTable();
            TempTableService tempTableService = new TempTableService();
            //get Data from TempTablePostModel
            //userID = 1;
            tempTable.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            tempTable.setComputerName(httpHeaders.getHeaderString("clientIp"));
            tempTable.setJobType(tempTablePostModel.getJobType());
            tempTable.setInt01(tempTablePostModel.getInt01());
            tempTable.setInt02(tempTablePostModel.getInt02());
            tempTable.setInt03(tempTablePostModel.getInt03());
            tempTable.setInt04(tempTablePostModel.getInt04());
            tempTable.setInt05(tempTablePostModel.getInt05());
            tempTable.setStr01(tempTablePostModel.getStr01());
            tempTable.setStr02(tempTablePostModel.getStr02());
            tempTable.setStr03(tempTablePostModel.getStr03());
            tempTable.setStr04(tempTablePostModel.getStr04());
            tempTable.setStr05(tempTablePostModel.getStr05());
            tempTable.setText01(tempTablePostModel.getText01());
            tempTable.setText02(tempTablePostModel.getText02());
            tempTable.setText03(tempTablePostModel.getText03());
            tempTable.setText04(tempTablePostModel.getText04());
            tempTable.setText05(tempTablePostModel.getText05());
            tempTable.setText06(tempTablePostModel.getText06());
            tempTable.setText07(tempTablePostModel.getText07());
            tempTable.setText08(tempTablePostModel.getText08());
            tempTable.setText09(tempTablePostModel.getText09());
            tempTable.setText10(tempTablePostModel.getText10());

            if (tempTable != null) {
                tempTable = tempTableService.create(tempTable);

                tempTable.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                tempTable.setOrderNo(tempTable.getId());
                tempTable = tempTableService.update(tempTable);
                responseData.put("data", tempTableService.tranformToModel(tempTable));
            }

            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "TempTable created successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for delete temptable by computername and jobtype.",
            notes = "ลบข้อมูลตารางชั่วคราวด้วยไอพีเครื่องและประเภทงาน",
            response = TempTableModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Temptable delete by id success.")
        ,
        @ApiResponse(code = 404, message = "Temptable by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/deleteByJobType/{jobType}")
    public Response deleteByJobType(
            @ApiParam(name = "jobType", value = "ประเภทงาน", required = true)
            @PathParam("jobType") String jobType
    ) {
        LOG.debug("deleteByJobType...");
        LOG.debug("jobType = " + jobType);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Temptable by id not found in the database.");
        try {
            TempTableService tempTableService = new TempTableService();
            List<TempTable> listTempTable = tempTableService.listByComputerNameAndJobType(Integer.parseInt(httpHeaders.getHeaderString("userID")), httpHeaders.getHeaderString("clientIp"), jobType, "", "");
            if (!listTempTable.isEmpty()) {
                for (TempTable tempTable : listTempTable) {
                    tempTableService.delete(tempTable);
                }
//                status = Response.Status.OK;
//                responseData.put("data", "Delete success.");
//                responseData.put("message", "");
            }
            //oat-edit
            status = Response.Status.OK;
            responseData.put("data", "Delete success.");
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list TempTable By ComputerName And JobType.",
            notes = "ขอรายการตารางชั่วคราวด้วยชื่อคอมพิวเตอร์และประเภทงาน",
            responseContainer = "List",
            response = TempTableModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "TempTable list success.")
        ,
        @ApiResponse(code = 404, message = "TempTable list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.TEXT_PLAIN})
    @Path(value = "/listByComputerNameAndJobType")
    public Response listByComputerNameAndJobType(
            @ApiParam(name = "jobType", value = "ประเภทงาน", required = true)
            @QueryParam("jobType") String jobType,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("desc") @QueryParam("dir") String dir
    ) {
        LOG.debug("listByComputerNameAndJobType...");
        LOG.debug("clientIp = " + httpHeaders.getHeaderString("clientIp"));
        LOG.debug("jobType = " + jobType);
        LOG.debug("sort = " + sort);
        LOG.debug("dir = " + dir);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "TempTable by id not found in the database.");
        try {
            TempTableService tempTableService = new TempTableService();
            List<TempTable> listTempTable = tempTableService.listByComputerNameAndJobType(Integer.parseInt(httpHeaders.getHeaderString("userID")), httpHeaders.getHeaderString("clientIp"), jobType, sort, dir);
            ArrayList<TempTableModel> listTempTableModel = new ArrayList();

            if (!listTempTable.isEmpty()) {
                for (TempTable tempTable : listTempTable) {
                    listTempTableModel.add(tempTableService.tranformToModel(tempTable));
                }
            }
            listTempTableModel.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listTempTableModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "Method for create TempTable",
            notes = "สร้างข้อมูลตารางชั่วคราว",
            response = TempTableModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "TempTable created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/createList")
    public Response createList(
            List<TempTableModel> tempTablePostModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            TempTableService tempTableService = new TempTableService();
            tempTablePostModel.forEach(model -> {
                TempTable tempTable = new TempTable();

                tempTable.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                tempTable.setComputerName(httpHeaders.getHeaderString("clientIp"));
                tempTable.setJobType(model.getJobType());
                tempTable.setInt01(model.getInt01());
                tempTable.setInt02(model.getInt02());
                tempTable.setInt03(model.getInt03());
                tempTable.setInt04(model.getInt04());
                tempTable.setInt05(model.getInt05());
                tempTable.setStr01(model.getStr01());
                tempTable.setStr02(model.getStr02());
                tempTable.setStr03(model.getStr03());
                tempTable.setStr04(model.getStr04());
                tempTable.setStr05(model.getStr05());
                tempTable.setText01(model.getText01());
                tempTable.setText02(model.getText02());
                tempTable.setText03(model.getText03());
                tempTable.setText04(model.getText04());
                tempTable.setText05(model.getText05());
                tempTable.setText06(model.getText06());
                tempTable.setText07(model.getText07());
                tempTable.setText08(model.getText08());
                tempTable.setText09(model.getText09());
                tempTable.setText10(model.getText10());

                if (tempTable != null) {
                    tempTable = tempTableService.create(tempTable);
                    tempTable.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    tempTable.setOrderNo(tempTable.getId());
                    tempTable = tempTableService.update(tempTable);
                }
            });

            responseData.put("data", null);
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "TempTable created successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
