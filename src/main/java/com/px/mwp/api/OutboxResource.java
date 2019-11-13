package com.px.mwp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.service.UserProfileService;
import com.px.mwp.entity.Outbox;
import com.px.mwp.entity.UserProfileFolder;
import com.px.mwp.model.OutboxModel;
import com.px.mwp.model.OutboxSearchModel;
import com.px.mwp.service.OutboxService;
import com.px.mwp.service.UserProfileFolderService;
import com.px.share.entity.TempTable;
import com.px.share.model.ListOptionModel;
import com.px.share.model.ListReturnModel;
import com.px.share.model.VersionModel;
import com.px.share.service.TempTableService;
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
import javax.ws.rs.POST;
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
 * @author Oat
 */
@Api(value = "Outbox กล่องหนังสือออก")
@Path("v1/outboxs")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class OutboxResource {

    private static final Logger LOG = Logger.getLogger(OutboxResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "สร้างข้อมูลกล่องหนังสือออก",
            notes = "สร้างข้อมูลกล่องหนังสือออก",
            response = OutboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Outbox created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            OutboxModel outboxPostModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(outboxPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            OutboxService outboxService = new OutboxService();
            Outbox outbox = new Outbox();

            outbox.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            outbox.setUserProfileFolderId(outboxPostModel.getUserProfileFolderId());
            outbox.setStructureFolderId(outboxPostModel.getStructureFolderId());
            outbox.setOutboxFrom(outboxPostModel.getOutboxFrom());
            outbox.setOutboxTo(outboxPostModel.getOutboxTo());
            outbox.setOutboxTitle(outboxPostModel.getOutboxTitle());
            outbox.setOutboxSendDate(LocalDateTime.now());
            outbox.setOutboxOpenDate(dateThaiToLocalDateTime(outboxPostModel.getOutboxOpenDate()));
            outbox.setOutboxOpenDateDefine(dateThaiToLocalDateTime(outboxPostModel.getOutboxOpenDateDefine()));
            outbox.setOutboxOpenFlag(0);
            outbox.setOutboxActionDate(dateThaiToLocalDateTime(outboxPostModel.getOutboxActionDate()));
            outbox.setOutboxActionDateDefine(dateThaiToLocalDateTime(outboxPostModel.getOutboxActionDateDefine()));
            outbox.setOutboxActionFlag(0);
            outbox.setOutboxReceiveDate(dateThaiToLocalDateTime(outboxPostModel.getOutboxReceiveDate()));
            outbox.setOutboxReceiveDateDefine(dateThaiToLocalDateTime(outboxPostModel.getOutboxReceiveDateDefine()));
            outbox.setOutboxReceiveFlag(0);
            outbox.setOutboxFinishDate(dateThaiToLocalDateTime(outboxPostModel.getOutboxFinishDate()));
            outbox.setOutboxFinishDateDefine(dateThaiToLocalDateTime(outboxPostModel.getOutboxFinishDateDefine()));
            outbox.setOutboxFinishFlag(0);
            outbox.setLetterStatus1(0);
            outbox.setLetterStatus2(0);
            outbox.setLetterStatus3(0);
            outbox.setModuleId(outboxPostModel.getModuleId());
            outbox.setLinkId(outboxPostModel.getLinkId());
            outbox.setLinkId2(outboxPostModel.getLinkId2());
            outbox.setLinkType(outboxPostModel.getLinkType());
            outbox.setOutboxStr01(outboxPostModel.getOutboxStr01());
            outbox.setOutboxStr02(outboxPostModel.getOutboxStr02());
            outbox.setOutboxStr03(outboxPostModel.getOutboxStr03());
            outbox.setOutboxStr04(outboxPostModel.getOutboxStr04());
            outbox.setOutboxDate01(dateThaiToLocalDateTime(outboxPostModel.getOutboxDate01()));
            outbox.setOutboxDate02(dateThaiToLocalDateTime(outboxPostModel.getOutboxDate02()));
            outbox.setOutboxDescription(outboxPostModel.getOutboxDescription());
            outbox.setOutboxNote(outboxPostModel.getOutboxNote());
            outbox.setWorkflowId(outboxPostModel.getWorkflowId());
            outbox.setOutboxApprove(outboxPostModel.getOutboxApprove());
            outbox.setOutboxSpeed(outboxPostModel.getOutboxSpeed());

            outbox = outboxService.create(outbox);
            outbox.setOrderNo(outbox.getId());
            outboxService.update(outbox);

            status = Response.Status.CREATED;
            responseData.put("data", outboxService.tranformToModel(outbox));
            responseData.put("message", "outbox created successfully.");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ลบข้อมูลกล่องหนังสือออก",
            notes = "ลบข้อมูลกล่องหนังสือออก",
            response = OutboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Outbox deleted by id success.")
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
            @ApiParam(name = "id", value = "รหัสกล่องหนังสือออก", required = true)
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
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Outbox by id not found in the database.");
        try {
            OutboxService outboxService = new OutboxService();
            Outbox outbox = outboxService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (outbox != null) {
                status = Response.Status.OK;
                responseData.put("data", outboxService.tranformToModel(outbox));
                responseData.put("message", "");
            }
            responseData.put("success", true);

            //LogData For Remove
            outboxService.saveLogForRemove(outbox, httpHeaders.getHeaderString("clientIp"));
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "รายการกล่องหนังสือออกโดยรหัสผู้ใช้งาน",
            notes = "รายการกล่องหนังสือออกโดยรหัสผู้ใช้งาน",
            responseContainer = "List",
            response = OutboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Outbox list success.")
        ,
        @ApiResponse(code = 404, message = "Outbox list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/user/{userId}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listByUserId(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "userId", value = "รหัสผู้ใช้งาน", required = true)
            @PathParam("userId") int userId
    ) {
        LOG.info("listByUserProfileFolderId...");
        LOG.info("offset = " + listOptionModel.getOffset());
        LOG.info("limit = " + listOptionModel.getLimit());
        LOG.info("sort = " + listOptionModel.getSort());
        LOG.info("dir = " + listOptionModel.getDir());
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
        responseData.put("data", null);
        responseData.put("message", "Outbox by id not found in the database.");
        try {
            List<OutboxModel> listOutboxModel = new ArrayList<>();
            ListReturnModel listReturnModel = new ListReturnModel(0, 0, 0);

            OutboxService outboxService = new OutboxService();
            UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
            UserProfileFolder userProfileFolder = userProfileFolderService.getByUserProfileId(userId, "O");

            List<Outbox> listOutbox = outboxService.listByUserProfileFolderId(userProfileFolder.getId(), listOptionModel.getLimit(), listOptionModel.getOffset(), listOptionModel.getSort(), listOptionModel.getDir());
            if (!listOutbox.isEmpty()) {
                for (Outbox outbox : listOutbox) {
                    listOutboxModel.add(outboxService.tranformToModel_cc(outbox));
                }                               
                int count = listOutbox.size() + listOptionModel.getOffset();
                int countAll = outboxService.countListByUserProfileFolderId(userProfileFolder.getId());
                int next = 0;
                if (count >= listOptionModel.getLimit()) {
                    next = listOptionModel.getOffset() + listOptionModel.getLimit();
                    if (next >= countAll) {
                        next = 0;
                    }
                }
                listReturnModel = new ListReturnModel(countAll, count, next);
            }
            status = Response.Status.OK;
            responseData.put("data", listOutboxModel);
            responseData.put("listReturn", listReturnModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "get List of Outbox by linkId",
            notes = "get List of Outbox by linkId",
            responseContainer = "List",
            response = OutboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Outbox list success.")
        ,
        @ApiResponse(code = 404, message = "Outbox list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/list/{linkId}")
    public Response listByLinktId(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "linkId", value = "linkId", required = true)
            @PathParam("linkId") int linkId
    ) {
        LOG.info("listByLinktId...");
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
        responseData.put("message", "Outbox by id not found in the database.");
        try {
            OutboxService outboxService = new OutboxService();
            List<Outbox> listOutbox = outboxService.listByLinkId(linkId, listOptionModel.getSort(), listOptionModel.getDir());
            List<OutboxModel> listOutboxModel = new ArrayList<>();
            if (!listOutbox.isEmpty()) {
                for (Outbox outbox : listOutbox) {
                    listOutboxModel.add(outboxService.tranformToModel(outbox));
                }
            }
            status = Response.Status.OK;
            responseData.put("data", listOutboxModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "Method for search Outbox",
            notes = "Method for search Outbox",
            response = OutboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Outbox list success.")
        ,
        @ApiResponse(code = 404, message = "Outbox list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/search")
    public Response search(
            @BeanParam ListOptionModel listOptionModel,
            OutboxSearchModel outboxSearchModel
    ) {
        LOG.info("searchInbox...");
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "inbox list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            OutboxService outboxService = new OutboxService();
            UserProfileFolderService userProfileFolderService = new UserProfileFolderService();

            int usereProfileFolderId = userProfileFolderService.getByUserProfileId(Integer.parseInt(httpHeaders.getHeaderString("userID")), "O").getId();

            List<Outbox> listOutbox = outboxService.searchUserOutboxByModel(usereProfileFolderId, outboxSearchModel, listOptionModel.getSort(), listOptionModel.getDir());
            ArrayList<OutboxModel> listOutboxModel = new ArrayList<>();
            if (!listOutbox.isEmpty()) {
                listOutbox.forEach((outbox) -> {
//                    listOutboxModel.add(outboxService.tranformToModel(outbox));
                    listOutboxModel.add(outboxService.tranformToModel_cc(outbox));
                });
                listOutboxModel.trimToSize();

            }
            status = Response.Status.OK;
            responseData.put("data", listOutboxModel);
            responseData.put("message", "Outbox list success.");
            responseData.put("success", true);

        } catch (Exception ex) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "Method for search Outbox",
            notes = "Method for search Outbox",
            responseContainer = "List",
            response = OutboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Outbox list success.")
        ,
        @ApiResponse(code = 404, message = "Outbox list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/report/{jobType}")
    public Response report(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "jobType", value = "jobType", required = true)
            @PathParam("jobType") String jobType,
            OutboxSearchModel outboxSearchModel
    ) {
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "Outbox list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            OutboxService outboxService = new OutboxService();
            UserProfileFolderService userProfileFolderService = new UserProfileFolderService();

            int usereProfileFolderId = userProfileFolderService.getByUserProfileId(Integer.parseInt(httpHeaders.getHeaderString("userID")), "O").getId();

            String colHeader = "เจ้าหน้าที่: " + new UserProfileService().getById(Integer.parseInt(httpHeaders.getHeaderString("userID"))).getUserProfileFullName();
            String dateBegin = "ตั้งแต่วันที่ ";
            String start = outboxSearchModel.getOutboxStartDate();
            if (start != null && !"".equals(start)) {
                dateBegin += outboxService.getDateRange(start);
            } else {
                dateBegin += "-";
            }
            String dateEnd = " ถึงวันที่ ";
            String end = outboxSearchModel.getOutboxEndDate();
            if (end != null && !"".equals(end)) {
                dateEnd += outboxService.getDateRange(end);
            } else {
                dateEnd += "-";
            }

            List<Outbox> listOutbox = outboxService.searchUserOutboxByModel(usereProfileFolderId, outboxSearchModel, listOptionModel.getSort(), listOptionModel.getDir());
            if (!listOutbox.isEmpty()) {
                TempTableService tempTableService = new TempTableService();
                for (Outbox outbox : listOutbox) {
                    OutboxModel outboxModel = outboxService.tranformToModel_cc(outbox);

                    TempTable tempTable = new TempTable();
                    tempTable.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    tempTable.setComputerName(httpHeaders.getHeaderString("clientIp"));
                    tempTable.setJobType(jobType);

                    tempTable.setText01(outboxModel.getOutboxTo());
                    tempTable.setText02(outboxModel.getOutboxTitle());
                    tempTable.setStr01(outboxModel.getOutboxSendDate());
                    tempTable.setText03(outboxModel.getOutboxNote());
                    tempTable.setText04(outboxModel.getOutboxDescription());
                    tempTable.setText05(colHeader);
                    tempTable.setText06(dateBegin + dateEnd);
                    tempTable = tempTableService.create(tempTable);
//                    tempTable.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
//                    tempTable.setOrderNo(tempTable.getId());
//                    tempTable = tempTableService.update(tempTable);
                }
            }
            status = Response.Status.OK;
            responseData.put("data", null);
            responseData.put("message", "Outbox list success.");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

}
