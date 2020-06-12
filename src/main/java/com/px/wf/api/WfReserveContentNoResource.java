package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.entity.Param;
import com.px.share.model.ListOptionModel;
import com.px.share.model.VersionModel;
import com.px.share.service.ParamService;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
import com.px.wf.entity.WfFolder;
import com.px.wf.entity.WfReserveContentNo;
import com.px.wf.model.WfReserveContentNoModel;
import com.px.wf.service.WfContentService;
import com.px.wf.service.WfFolderService;
import com.px.wf.service.WfReserveContentNoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.Calendar;
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
@Api(value = "WfReserveContentNo จองเลข")
@Path("v1/wfReserveContentNos")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WfReserveContentNoResource {

    @Context
    HttpHeaders httpHeaders;

    private static final Logger log = Logger.getLogger(WfReserveContentNoResource.class.getName());

    //oat-add
    @ApiOperation(
            value = "Method for create WfReserveContentNo",
            notes = "สร้างข้อมูลเลขจอง"
    //response = WfReserveContentNoModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "WfReserveContentNo created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{num}/{style}")
    public Response create(
            WfReserveContentNoModel reserveModel,
            @ApiParam(name = "num", value = "num", required = true)
            @PathParam("num") int num,
            @ApiParam(name = "style", value = "style", required = true)
            @PathParam("style") int style//0 = startNumber create all, 1 = startNumber create only last
    ) {
        log.info("reserve...");
        Gson gs = new GsonBuilder()
                .setVersion(reserveModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            if (num > 0) {
                WfReserveContentNoService reserveContentNoService = new WfReserveContentNoService();
                WfContentService wfContentService = new WfContentService();
                WfFolderService wfFolderService = new WfFolderService();
                WfFolder wfFolder = wfFolderService.getById(reserveModel.getReserveContentNoFolderId());

                ParamService paramservice = new ParamService();
                int contentFormat = (wfFolder.getWfContentType2().getId() == 5)//ทะเบียนคำสั่งเลข3หลัก, ปกติ5หลัก
                        ? Integer.parseInt(paramservice.getByParamName("ORDERFORMAT").getParamValue()) : Integer.parseInt(paramservice.getByParamName("CONTENTFORMAT").getParamValue());

                int actionId = Integer.parseInt(httpHeaders.getHeaderString("userID"));

                int lastContentNumber = wfContentService.getMaxContentNo(wfFolder.getWfFolderPreContentNo(), reserveModel.getReserveContentNoFolderId(), reserveModel.getReserveContentNoContentYear());
                int reserveContentNumber = reserveContentNoService.getMaxContentNumber(reserveModel.getReserveContentNoFolderId(), reserveModel.getReserveContentNoContentYear());
                if (lastContentNumber < reserveContentNumber) {
                    lastContentNumber = reserveContentNumber;
                }
                
                if (style == 1) {
                    lastContentNumber += num;
                    num = 1;
                }
                
                for (int i = 0; i < num; i++) {
                    int tmpContentNumber = lastContentNumber + i;
                    String tmpContentNo = wfContentService.convertContentNo(reserveModel.getReserveContentNoContentYear(), tmpContentNumber, 0,
                            wfFolder.getWfFolderPreContentNo(), contentFormat);
                    WfReserveContentNo reserveContentNo = new WfReserveContentNo();
                    reserveContentNo.setCreatedBy(reserveModel.getUserId());
                    reserveContentNo.setReserveContentNoFolderId(reserveModel.getReserveContentNoFolderId());
                    reserveContentNo.setReserveContentNoContentNo(tmpContentNo);
                    reserveContentNo.setReserveContentNoContentDate(dateThaiToLocalDateTime(reserveModel.getReserveContentNoContentDate()));
                    reserveContentNo.setReserveContentNoContentTime(reserveModel.getReserveContentNoContentTime());
                    reserveContentNo.setReserveContentNoUserId(reserveModel.getReserveContentNoUserId());
                    reserveContentNo.setReserveContentNoContentYear(reserveModel.getReserveContentNoContentYear());
                    reserveContentNo.setReserveContentNoContentNumber(tmpContentNumber);
                    reserveContentNo.setReserveContentNoNote(reserveModel.getReserveContentNoNote());
//                    reserveContentNo.setReserveContentNoStatus(0);
                    reserveContentNo.setReserveContentNoStatus(reserveModel.getReserveContentNoStatus());
                    reserveContentNo.setReserveContentNoStructureId(reserveModel.getReserveContentNoStructureId());
                    reserveContentNo.setCreatedBy(actionId);
                    reserveContentNo = reserveContentNoService.create(reserveContentNo);
                    reserveContentNo.setOrderNo(reserveContentNo.getId());
                    reserveContentNo.setUpdatedBy(actionId);
                    reserveContentNoService.update(reserveContentNo);
                }
                //responseData.put("data", reserveContentNoService.tranformToModel(reserveContentNo));
                responseData.put("data", null);
                status = Response.Status.CREATED;
                responseData.put("success", true);
                responseData.put("message", "Reserve ContentNo successfully.");
            }
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "Method for cancel/update used WfReserveContentNo.",
            notes = "ยกเลิกการจองเลข/ใช้เลขจอง"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfReserveContentNo updeted by id success.")
        ,
        @ApiResponse(code = 404, message = "WfReserveContentNo by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
//    public Response cancel(
    public Response updateStatus(
            WfReserveContentNoModel reserveModel
    ) {
        log.info("update reserve...");
        Gson gs = new GsonBuilder()
                .setVersion(reserveModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfReserveContentNo by id not found in the database.");
        try {
            WfReserveContentNoService reserveContentNoService = new WfReserveContentNoService();
            WfReserveContentNo reserveContentNo = reserveContentNoService.getById(reserveModel.getId());
            if (reserveContentNo != null) {
                int reserveStatus = reserveModel.getReserveContentNoStatus();
                if (reserveStatus == 2) {
                    reserveContentNo.setReserveContentNoNote(reserveModel.getReserveContentNoNote());
                }
                reserveContentNo.setReserveContentNoStatus(reserveStatus);
                reserveContentNo.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                reserveContentNoService.update(reserveContentNo);
                status = Response.Status.OK;
                responseData.put("data", null);
                responseData.put("message", "WfReserveContentNo update status success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "Method for get list of WfReserveContentNo.",
            notes = "รายการเลขจอง",
            responseContainer = "List",
            response = WfReserveContentNoModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfReserveContentNo list success.")
        ,
        @ApiResponse(code = 404, message = "WfReserveContentNo list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{folderId}/{dateBegin}/{dateEnd}")
//        @Path(value = "/{folderId}")
    public Response listByDate(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "folderId", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("folderId") int folderId,
            @ApiParam(name = "dateBegin", value = "วันที่จองจาก", required = true)
            @PathParam("dateBegin") String dateBegin,
            @ApiParam(name = "dateEnd", value = "วันที่จองถึง", required = true)
            @PathParam("dateEnd") String dateEnd
    ) {
        log.info("listReserveContentNo...");
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
        responseData.put("message", "WfReserveContentNo list not found in the database.");
        try {
            WfReserveContentNoService reserveContentNoService = new WfReserveContentNoService();
            List<WfReserveContentNo> listReserveContentNo = reserveContentNoService.listByDate(folderId, dateBegin, dateEnd, listOptionModel.getSort(), listOptionModel.getDir());
//            List<WfReserveContentNo> listReserveContentNo = reserveContentNoService.list(folderId, listOptionModel.getSort(), listOptionModel.getDir());

            if (!listReserveContentNo.isEmpty()) {
                ArrayList<WfReserveContentNoModel> listReserveContentNoModel = new ArrayList();

                listReserveContentNo.forEach((reserveContentNo) -> {
                    listReserveContentNoModel.add(reserveContentNoService.tranformToModel(reserveContentNo));
                    //listReserveContentNoModel.add(reserveContentNoService.tranformToModelGroupReserveContentNoAndUser(reserveContentNo));
                });

                listReserveContentNoModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listReserveContentNoModel);
                responseData.put("message", "");
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

//    @ApiOperation(
//            value = "Method for get DateDefault For Search WfReserveContentNo.",
//            notes = "ขอวันที่สำหรับค้นหาเลขจอง",
//            responseContainer = "List",
//            response = WfReserveContentNoModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "get DateDefault For Search WfReserveContentNo success.")
//        ,
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/getDateDefaultForSearchReserveContentNo")
//    public Response getDateDefaultForSearchReserveContentNo(
//            @BeanParam VersionModel versionModel
//    ) {
//        log.info("getDateDefaultForSearchReserveContentNo...");
//        Gson gs = new GsonBuilder()
//                .setVersion(versionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        Gson gs1 = new Gson();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "get DateDefault For Search WfReserveContentNo not success.");
//        try {
//
//            //StartDate
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//            calendar.set(Calendar.MILLISECOND, 0);
//
//            //EndDate
//            Calendar calendar1 = Calendar.getInstance();
//            calendar1.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 0);
//            calendar1.set(Calendar.HOUR_OF_DAY, 0);
//            calendar1.set(Calendar.MINUTE, 0);
//            calendar1.set(Calendar.SECOND, 0);
//            calendar1.set(Calendar.MILLISECOND, 0);
//
//            ArrayList hasArrayList = new ArrayList();
//            HashMap hashJson = new HashMap();
//
//            hashJson.put("startDate", calendar.getTime());
//            hashJson.put("endDate", calendar1.getTime());
//            hasArrayList.add(hashJson);
//
//            status = Response.Status.OK;
//            responseData.put("data", hasArrayList);
//            responseData.put("message", "");
//
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//
//        return Response.status(status)
//                .entity(gs.toJson(responseData)).build();
//    }
    //oat-add
    @ApiOperation(
            value = "Method for get list of WfReserveContentNo.",
            notes = "รายการเลขจอง",
            responseContainer = "List",
            response = WfReserveContentNoModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfReserveContentNo list success.")
        ,
        @ApiResponse(code = 404, message = "WfReserveContentNo list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/user/{folderId}")
    public Response listByUser(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "folderId", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("folderId") int folderId
    ) {
        log.info("listReserveContentNo...");
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
        responseData.put("message", "WfReserveContentNo list not found in the database.");
        try {
            WfReserveContentNoService reserveContentNoService = new WfReserveContentNoService();

            List<WfReserveContentNo> listReserveContentNo = reserveContentNoService.listByUser(folderId, Integer.parseInt(httpHeaders.getHeaderString("userID")), listOptionModel.getSort(), listOptionModel.getDir());
            ArrayList<WfReserveContentNoModel> listReserveContentNoModel = new ArrayList();
            if (!listReserveContentNo.isEmpty()) {
                listReserveContentNo.forEach((reserveContentNo) -> {
                    listReserveContentNoModel.add(reserveContentNoService.tranformToModel(reserveContentNo));
                });
                listReserveContentNoModel.trimToSize();
            }
            status = Response.Status.OK;
            responseData.put("data", listReserveContentNoModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (NumberFormatException ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "Method for get list of WfReserveContentNo.",
            notes = "รายการเลขจอง",
            responseContainer = "List",
            response = WfReserveContentNoModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfReserveContentNo list success.")
        ,
        @ApiResponse(code = 404, message = "WfReserveContentNo list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/canceled/{folderId}")
    public Response listCanceled(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "folderId", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("folderId") int folderId
    ) {
        log.info("listReserveContentNo...");
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
        responseData.put("message", "WfReserveContentNo list not found in the database.");
        try {
            WfReserveContentNoService reserveContentNoService = new WfReserveContentNoService();
            List<WfReserveContentNo> listReserveContentNo = reserveContentNoService.listCanceled(folderId, listOptionModel.getSort(), listOptionModel.getDir());
            ArrayList<WfReserveContentNoModel> listReserveContentNoModel = new ArrayList();
            if (!listReserveContentNo.isEmpty()) {
                for (WfReserveContentNo reserveContentNo : listReserveContentNo) {
                    listReserveContentNoModel.add(reserveContentNoService.tranformToModel(reserveContentNo));
                }
                listReserveContentNoModel.trimToSize();
            }
            status = Response.Status.OK;
            responseData.put("data", listReserveContentNoModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "ลบข้อมูลเลขจอง",
            notes = "ลบข้อมูลเลขจอง"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Outbox delete by id success.")
        ,
        @ApiResponse(code = 404, message = "Outbox by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสเลขจอง", required = true)
            @PathParam("id") int id
    ) {
        log.info("delete...");
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
        responseData.put("message", "Outbox by id not found in the database.");
        try {
            WfReserveContentNoService reserveContentNoService = new WfReserveContentNoService();
            WfReserveContentNo reserveContentNo = reserveContentNoService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (reserveContentNo != null) {
                status = Response.Status.OK;
                responseData.put("data", "Delete success.");
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
            value = "Method for get list of WfReserveContentNo.",
            notes = "รายการเลขจอง",
            responseContainer = "List",
            response = WfReserveContentNoModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfReserveContentNo list success.")
        ,
        @ApiResponse(code = 404, message = "WfReserveContentNo list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/structure/{folderId}/{structureId}")
    public Response listByStructure(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "folderId", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("folderId") int folderId,
            @ApiParam(name = "structureId", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("structureId") int structureId
    ) {
        log.info("listReserveContentNo...");
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
        responseData.put("message", "WfReserveContentNo list not found in the database.");
        try {
            WfReserveContentNoService reserveContentNoService = new WfReserveContentNoService();

            List<WfReserveContentNo> listReserveContentNo = reserveContentNoService.listByStructure(folderId, structureId, listOptionModel.getSort(), listOptionModel.getDir());
            ArrayList<WfReserveContentNoModel> listReserveContentNoModel = new ArrayList();
            if (!listReserveContentNo.isEmpty()) {
                listReserveContentNo.forEach((reserveContentNo) -> {
                    listReserveContentNoModel.add(reserveContentNoService.tranformToModel(reserveContentNo));
                });
                listReserveContentNoModel.trimToSize();
            }
            status = Response.Status.OK;
            responseData.put("data", listReserveContentNoModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (NumberFormatException ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

}
