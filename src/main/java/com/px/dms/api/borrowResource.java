/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.dms.entity.DmsDocument;
import com.px.dms.entity.borrow;
import com.px.dms.model.borrowModel;
import com.px.dms.service.DmsDocumentService;
import com.px.dms.service.borrowService;
import com.px.share.model.VersionModel;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author top
 */
@Api(value = "borrow ยืมคืน")
@Path("v1/borrow")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class borrowResource {

    private static final Logger LOG = Logger.getLogger(borrowResource.class.getName());

    @ApiOperation(
            value = "Method for create borrow",
            notes = "สร้างข้อมูล ยืม-คืน",
            response = borrowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "borrow created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            borrowModel borrowModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            borrow borrow = new borrow();
            borrowService borrowService = new borrowService();
            DmsDocumentService DmsDocumentService = new DmsDocumentService();
            DmsDocument doc = DmsDocumentService.getById(borrowModel.getDmsDocument().getId());
            doc.setBorrowStatus(1);

            borrow.setCreatedBy(userID);
            borrow.setLendDate(dateThaiToLocalDateTime(borrowModel.getLendDate()));
            borrow.setDmsDocument(doc);
            borrow.setUserHandlerId(borrowModel.getUserHandlerId());
            borrow.setUserLentId(borrowModel.getUserLentId());
            borrow.setUserReturnId(0);
//            borrow.setReturnName(borrowModel.getReturnName());
            borrow.setLendName(borrowModel.getLendName());
            borrow.setNumLentDay(borrowModel.getRetuenDateNum());

            borrow = borrowService.create(borrow);
            DmsDocumentService.update(doc);

            if (borrow != null) {
                responseData.put("data", borrowService.tranformToModel(borrow, 0));
            }
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "borrow created successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update borrow.",
            notes = "แก้ไขที่การยืม",
            response = borrowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "borrow updeted by id success.")
        ,
        @ApiResponse(code = 404, message = "borrow by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}/status/{id2}")
    public Response update(
            @BeanParam VersionModel versionModel,
            @HeaderParam("userID") int userID,
            @ApiParam(name = "id", value = "รหัส ยืม-คืน", required = true)
            @PathParam("id") int id,
            @ApiParam(name = "id2", value = "status 1=อัพเดตปกติ,2=อัพเดต คืน", required = true)
            @PathParam("id2") int id2,
            borrowModel borrowModel
    ) {
        LOG.info("update...");
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
        responseData.put("data", null);
        responseData.put("message", "Folder by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
//            borrow borrow = new borrow();
            borrowService borrowService = new borrowService();
            DmsDocumentService DmsDocumentService = new DmsDocumentService();
            borrow borrow = borrowService.getById(id);

            if (borrow != null) {
                if (id2 == 1) {
                    borrow.setNumLentDay(borrowModel.getRetuenDateNum());
                    borrow.setUserLentId(borrowModel.getUserLentId());
                    borrow.setLendName(borrowModel.getLendName());
                }
                if (id2 == 2) {
                    borrow.setUserReturnId(borrowModel.getUserReturnId());
                    borrow.setReturnName(borrowModel.getReturnName());
//                    borrow.setRetuenDate(LocalDateTime.now());
                    borrow.setRetuenDate(dateThaiToLocalDateTime(borrowModel.getReturnDate()));
//                    borrow.setRemovedBy(userID);           
                    borrow.setUpdatedBy(userID);
                    //borrowstatus ว่าง
                    System.out.println("");

                    DmsDocument doc = DmsDocumentService.getById(borrow.getDmsDocument().getId());
//                    String searchIdInDms = doc.getDmsSearchIdINDms();
//                    DmsSearchService dmsSearchService = new DmsSearchService();
//                    dmsSearchService.updateDataBorrow(searchIdInDms, true);
                }
                DmsDocument doc = DmsDocumentService.getById(borrow.getDmsDocument().getId());
                doc.setBorrowStatus(0);
                DmsDocumentService.update(doc);
                borrow = borrowService.update(borrow);
                status = Response.Status.OK;
                responseData.put("data", borrowService.tranformToModel(borrow, 1));
                responseData.put("message", "folder updeted by id success.");
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
            value = "Method for get  borrow by id.",
            notes = "ดึงข้อมูลเอกสาร ยืม คืน ด้วย id",
            //        responseContainer = "List",
            response = borrowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "get borrow  success.")
        ,
        @ApiResponse(code = 404, message = "borrow  not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response list(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสเอกสาร", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("get...");
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
        responseData.put("data", null);
        responseData.put("message", "document  not found in the database.");
        responseData.put("errorMessage", "");
        try {
            borrowService borrowService = new borrowService();
            DmsDocumentService DmsDocumentService = new DmsDocumentService();
//            Doc borrow = borrowService.getById(id);
            DmsDocument DmsDocument = DmsDocumentService.getById(id);

            boolean result = borrowService.checkStatusDocBorrow(DmsDocument);
            System.out.println("result = " + result);
            if (result) {
                //ยืม หรือ ยืมเกินกำหนด
                List<borrow> borrowList = borrowService.ListBorrow(DmsDocument);

                if (!borrowList.isEmpty()) {
                    ArrayList<borrowModel> borrowModel = new ArrayList<>();
                    LocalDateTime nowDate = LocalDateTime.now();
                    int statusTemp = 0;
                    for (borrow borrow : borrowList) {

                        int numBorrow = borrow.getNumLentDay();
                        LocalDateTime returnDate = nowDate.plusDays(numBorrow);

                        if (nowDate.isBefore(returnDate)) {
                            statusTemp = 1;
                            //ยืม
                        } else {
//                            ยืมเกินกำหนด
                            statusTemp = 3;
                        }

                        borrowModel.add(borrowService.tranformToModel(borrow, statusTemp));
                    }
                    status = Response.Status.OK;
                    responseData.put("status", statusTemp);
                    responseData.put("data", borrowModel);
                    responseData.put("message", "");
                }

            } else {
//                ว่าง
                responseData.put("status", 0);
                responseData.put("data", null);
                status = Response.Status.OK;
                responseData.put("message", "get dmsDocument  by id success.");
            }

            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list history borrow.",
            notes = "รายการ borrow",
            responseContainer = "List",
            response = borrowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "borrow list success.")
        ,
        @ApiResponse(code = 404, message = "borrow list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listHistory/{id}")
    public Response list(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสเอกสาร", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("list...");

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
        responseData.put("data", null);
        responseData.put("message", "borrow list not found in the database.");
        responseData.put("errorMessage", "");
        try {

            borrowService borrowService = new borrowService();

            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            DmsDocument document = dmsDocumentService.getById(id);

            List<borrow> listBorrow = borrowService.getListIdNotRemoved(document);

            if (!listBorrow.isEmpty()) {
                ArrayList<borrowModel> borrowModel = new ArrayList<>();
                for (borrow borrow : listBorrow) {
                    int tempStatus = 0;
                    if (borrow.getRetuenDate() != null) {
//                    คืน
                        if (borrow.getRetuenDate().isBefore(borrow.getLendDate().plusDays(borrow.getNumLentDay()))) {
                            //คืนปกติ
                            borrowModel.add(borrowService.tranformToModel(borrow, 4));
                        } else {
//                            คืนเกินกำหนด
                            borrowModel.add(borrowService.tranformToModel(borrow, 2));
                        }

                    } else {
                        if (borrow.getRetuenDate() == null) {
//                            ยืม
                            LocalDateTime nowDate = LocalDateTime.now();
                            if (nowDate.isBefore(borrow.getLendDate().plusDays(borrow.getNumLentDay()))) {
//                            ยืมปกติ
                                borrowModel.add(borrowService.tranformToModel(borrow, 1));
                            } else {
                                borrowModel.add(borrowService.tranformToModel(borrow, 3));

                            }

                        }

                    }

//                    borrowModel.add(borrowService.tranformToModel(borrow));
                }
                status = Response.Status.OK;
                responseData.put("data", borrowModel);
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
            value = "Method for delete borrow by id.",
            notes = "ลบข้อมูลการยืม-คืน",
            response = borrowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "borrow deleted by id success.")
        ,
        @ApiResponse(code = 404, message = "borrow by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @HeaderParam("userID") int userID,
            @ApiParam(name = "id", value = "รหัสเอกสาร", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("remove...");
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
        responseData.put("data", null);
        responseData.put("message", "borrow by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
//            DmsDocumentService dmsDocumentService = new DmsDocumentService();
//            DmsDocument dmsDocument = dmsDocumentService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            borrowService borrowService = new borrowService();
            borrow borrow = borrowService.remove(id, userID);

            if (borrow != null) {
//                dmsDocumentService.saveLogForRemove(dmsDocument, versionModel.getClientIp());
                status = Response.Status.OK;
                responseData.put("data", borrowService.tranformToModel(borrow, 0));
                responseData.put("message", "borrow deleted by id success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list borrow.",
            notes = "รายการยืม",
            responseContainer = "List",
            response = borrowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "borrow list success.")
        ,
        @ApiResponse(code = 404, message = "borrow list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "borrow Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response list(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel
    ) {
        LOG.info("list...");
//        LOG.info("offset = "+offset);
//        LOG.info("limit = "+limit);
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
        responseData.put("data", null);
        responseData.put("message", "Field list not found in the database.");
        responseData.put("errorMessage", "");
        try {
//             DmsFieldService dmsFieldService = new DmsFieldService();
//             List<DmsField> listField = dmsFieldService.listAll(sort, dir);

            borrowService borrowService = new borrowService();
            List<borrow> listBorrow = borrowService.listAll("orderNo", "asc");

            if (!listBorrow.isEmpty()) {
                ArrayList<borrowModel> borrowModel = new ArrayList<>();
                for (borrow borrow : listBorrow) {
                    int tempStatus = 0;
                    if (borrow.getUserLentId() > 0 && borrow.getUserReturnId() > 0) {
//                    คืน
                        if (borrow.getRetuenDate().isBefore(borrow.getLendDate().plusDays(borrow.getNumLentDay()))) {
                            //คืนปกติ
                            borrowModel.add(borrowService.tranformToModel(borrow, 4));
                        } else {
//                            คืนเกินกำหนด
                            borrowModel.add(borrowService.tranformToModel(borrow, 2));
                        }

                    } else {
                        if (borrow.getUserReturnId() == 0) {
//                            ยืม
                            LocalDateTime nowDate = LocalDateTime.now();
                            if (nowDate.isBefore(borrow.getLendDate().plusDays(borrow.getNumLentDay()))) {
//                            ยืมปกติ
                                borrowModel.add(borrowService.tranformToModel(borrow, 1));
                            } else {
                                borrowModel.add(borrowService.tranformToModel(borrow, 3));

                            }

                        }

                    }

//                    borrowModel.add(borrowService.tranformToModel(borrow));
                }
                status = Response.Status.OK;
                responseData.put("data", borrowModel);
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

}
