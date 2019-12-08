package com.px.mwp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.mwp.entity.Inbox;
import com.px.mwp.entity.UserProfileFolder;
import com.px.mwp.entity.StructureFolder;
import com.px.mwp.model.InboxModel;
import com.px.mwp.model.InboxSearchModel;
import com.px.mwp.service.InboxService;
import com.px.mwp.service.UserProfileFolderService;
import com.px.mwp.service.StructureFolderService;
import com.px.share.entity.TempTable;
import com.px.share.model.ListOptionModel;
import com.px.share.model.ListReturnModel;
import com.px.share.model.VersionModel;
import com.px.share.service.TempTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import javax.ws.rs.POST;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
import io.swagger.annotations.ApiParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author Oat
 */
@Api(value = "Inbox กล่องข้อมูลเข้า")
@Path("v1/inboxs")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class InboxResource {

    private static final Logger LOG = Logger.getLogger(InboxResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "สร้างข้อมูลกล่องข้อมูลเข้า",
            notes = "สร้างข้อมูลกล่องข้อมูลเข้า",
            response = InboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Inbox created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            InboxModel inboxPostModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(inboxPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            InboxService inboxService = new InboxService();
            Inbox inbox = new Inbox();
            //get Data from InboxPostModel
            int inboxApprove = 0;
//            Param param = new Param();
//            ParamService paramservice = new ParamService();
//            param = paramservice.getByParamName("INBOXAFTERSEND"); //0=ลบจากขอ้มูลเข้า  1=ไม่ลบจากข้อมูลเข้า  2=ลบออกจากฐานข้อมูล
//            String inboxAfterAction = param.getParamValue();

            //get current
            LocalDateTime dateToday = LocalDateTime.now();

            inbox.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            inbox.setUserProfileFolderId(inboxPostModel.getUserProfileFolderId());
            inbox.setStructureFolderId(inboxPostModel.getStructureFolderId());
            inbox.setInboxFrom(inboxPostModel.getInboxFrom());
            inbox.setInboxTo(inboxPostModel.getInboxTo());
            inbox.setInboxTitle(inboxPostModel.getInboxTitle());
            inbox.setInboxSendDate(dateToday);
            inbox.setInboxReceiveDate(dateThaiToLocalDateTime(inboxPostModel.getInboxReceiveDate()));
            inbox.setInboxReceiveDateDefine(dateThaiToLocalDateTime(inboxPostModel.getInboxReceiveDateDefine()));
            inbox.setInboxReceiveFlag(0);
            inbox.setInboxOpenDate(dateThaiToLocalDateTime(inboxPostModel.getInboxOpenDate()));
            inbox.setInboxOpenDateDefine(dateThaiToLocalDateTime(inboxPostModel.getInboxOpenDateDefine()));
            inbox.setInboxOpenFlag(0);
            inbox.setInboxActionDate(dateThaiToLocalDateTime(inboxPostModel.getInboxActionDate()));
            inbox.setInboxActionDateDefine(dateThaiToLocalDateTime(inboxPostModel.getInboxActionDateDefine()));
            inbox.setInboxActionFlag(0);
            inbox.setInboxFinishDate(dateThaiToLocalDateTime(inboxPostModel.getInboxFinishDate()));
            inbox.setInboxFinishDateDefine(dateThaiToLocalDateTime(inboxPostModel.getInboxFinishDateDefine()));
            inbox.setInboxFinishFlag(0);
            inbox.setLetterStatus1(0);
            inbox.setLetterStatus2(0);
            inbox.setLetterStatus3(0);
            inbox.setModuleId(inboxPostModel.getModuleId());
            inbox.setLinkId(inboxPostModel.getLinkId());
            inbox.setLinkId2(inboxPostModel.getLinkId2());
            inbox.setLinkType(inboxPostModel.getLinkType());
            inbox.setInboxStr01(inboxPostModel.getInboxStr01());
            inbox.setInboxStr02(inboxPostModel.getInboxStr02());
            inbox.setInboxStr03(inboxPostModel.getInboxStr03());
            inbox.setInboxStr04(inboxPostModel.getInboxStr04());
            inbox.setInboxDate01(dateThaiToLocalDateTime(inboxPostModel.getInboxDate01()));
            inbox.setInboxDate02(dateThaiToLocalDateTime(inboxPostModel.getInboxDate02()));
            inbox.setInboxDescription(inboxPostModel.getInboxDescription());
            inbox.setInboxNote(inboxPostModel.getInboxNote());
            inbox.setWorkflowId(inboxPostModel.getWorkflowId());
            inbox.setInboxKey(inboxPostModel.getInboxKey());
            if (inboxPostModel.getInboxApprove() == 1) {
                inboxApprove = Integer.parseInt(httpHeaders.getHeaderString("userID"));
            }
            inbox.setInboxApprove(inboxApprove);
            inbox.setInboxApproveStatus(inboxPostModel.getInboxApproveStatus());
            inbox.setInboxSpeed(inboxPostModel.getInboxSpeed());

            inbox = inboxService.create(inbox);
            inbox.setOrderNo(inbox.getId());
            inboxService.update(inbox);

//                //inbox
//                if (inboxPostModel.getLinkType().equals("I")) {
//                    if (inboxAfterAction.equals("") || inboxAfterAction.equals("0") || inboxAfterAction == null) {
//                        inboxService.remove(inboxPostModel.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
//                    } else if (inboxAfterAction.equals("2")) {
//                        Inbox inbox1 = inboxService.getById(inboxPostModel.getId());
//                        inboxService.delete(inbox1);
//                    }
//                }
            //LogData For Create
            inboxService.saveLogForCreate(inbox, httpHeaders.getHeaderString("clientIp"));
            status = Response.Status.CREATED;
            responseData.put("success", inboxService.tranformToModel(inbox));
            responseData.put("message", "inbox created successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลกล่องข้อมูลเข้า ด้วย รหัสกล่องข้อมูลเข้า",
            notes = "ขอข้อมูลกล่องข้อมูลเข้า ด้วย รหัสกล่องข้อมูลเข้า",
            response = InboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Inbox by id success."),
        @ApiResponse(code = 404, message = "Inbox by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสกล่องข้อมูลเข้า", required = true)
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
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Inbox by id not found in the database.");
        try {
            InboxService inboxService = new InboxService();
            Inbox inbox = inboxService.getById(id);
            if (inbox != null) {
                //checkletterStatus
                int newLetterStatus1 = inboxService.checkLetterStatus(inbox, 1);
                int newLetterStatus2 = inboxService.checkLetterStatus(inbox, 2);
                int newLetterStatus3 = inboxService.checkLetterStatus(inbox, 3);

                int oldLetterStatus1 = inbox.getLetterStatus1();
                int oldLetterStatus2 = inbox.getLetterStatus2();
                int oldLetterStatus3 = inbox.getLetterStatus3();

                //open letter
                if (newLetterStatus1 != oldLetterStatus1) {
                    inbox.setLetterStatus1(newLetterStatus1);
                    inboxService.update(inbox);
                }

                //action letter
                if (newLetterStatus2 != oldLetterStatus2) {
                    inbox.setLetterStatus2(newLetterStatus2);
                    inboxService.update(inbox);
                }

                //finish letter
                if (newLetterStatus3 != oldLetterStatus3) {
                    inbox.setLetterStatus3(newLetterStatus3);
                    inboxService.update(inbox);
                }

                status = Response.Status.OK;
                responseData.put("data", inboxService.tranformToModel(inbox));
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
            value = "ลบข้อมูลกล่องข้อมูลเข้า",
            notes = "ลบข้อมูลกล่องข้อมูลเข้า",
            response = InboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Inbox removed by id success."),
        @ApiResponse(code = 404, message = "Inbox by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสกล่องข้อมูลเข้า", required = true)
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
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Inbox by id not found in the database.");
        try {
            InboxService inboxService = new InboxService();
            Inbox inbox = inboxService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (inbox != null) {
                status = Response.Status.OK;
                responseData.put("data", inboxService.tranformToModel(inbox));
                responseData.put("message", "");
            }
            responseData.put("success", true);

            //LogData For Remove
            inboxService.saveLogForRemove(inbox, httpHeaders.getHeaderString("clientIp"));
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "รายการกล่องข้อมูลเข้าโดยรหัสหน่วยงาน",
            notes = "รายการกล่องข้อมูลเข้าโดยรหัสหน่วยงาน",
            responseContainer = "List",
            response = InboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Inbox list success."),
        @ApiResponse(code = 404, message = "Inbox list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/structure/{structureId}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listByStructureId(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "structureId", value = "structureId", required = true)
            @PathParam("structureId") int structureId
    ) {
        LOG.debug("listByStructureId...");
        LOG.debug("structureId = " + structureId);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Inbox list by structureId not found in the database.");
        try {
            InboxService inboxService = new InboxService();
            List<InboxModel> listInboxModel = new ArrayList<>();
            ListReturnModel listReturnModel = new ListReturnModel(0, 0, 0);

            StructureFolder structureFolder = new StructureFolderService().getByStructureId(structureId, "I");
            if (structureFolder != null) {
                int structureFolderId = structureFolder.getId();
                List<Inbox> listInbox = inboxService.listByStructureFolderId(structureFolderId, listOptionModel.getLimit(), listOptionModel.getOffset(), listOptionModel.getSort(), listOptionModel.getDir(), 0);
                if (!listInbox.isEmpty()) {
                    int count = listInbox.size() + listOptionModel.getOffset();
                    int countAll = inboxService.countListByStructureFolderId(structureFolderId, 0);
                    int next = 0;
                    if (count >= listOptionModel.getLimit()) {
                        next = listOptionModel.getOffset() + listOptionModel.getLimit();
                        if (next >= countAll) {
                            next = 0;
                        }
                    }
                    listReturnModel = new ListReturnModel(countAll, count, next);
                    for (Inbox inbox : listInbox) {
//                        //checkletterStatus
//                        int newLetterStatus1 = inboxService.checkLetterStatus(inbox, 1);
//                        int newLetterStatus2 = inboxService.checkLetterStatus(inbox, 2);
//                        int newLetterStatus3 = inboxService.checkLetterStatus(inbox, 3);
//
//                        int oldLetterStatus1 = inbox.getLetterStatus1();
//                        int oldLetterStatus2 = inbox.getLetterStatus2();
//                        int oldLetterStatus3 = inbox.getLetterStatus3();
//
//                        //open letter
//                        if (newLetterStatus1 != oldLetterStatus1) {
//                            inbox.setLetterStatus1(newLetterStatus1);
//                            inboxService.update(inbox);
//                        }
//                        //action letter
//                        if (newLetterStatus2 != oldLetterStatus2) {
//                            inbox.setLetterStatus2(newLetterStatus2);
//                            inboxService.update(inbox);
//                        }
//                        //finish letter
//                        if (newLetterStatus3 != oldLetterStatus3) {
//                            inbox.setLetterStatus3(newLetterStatus3);
//                            inboxService.update(inbox);
//                        }
                        if (inbox.getInboxReceiveFlag() == 0) {
                            inbox.setInboxReceiveDate(LocalDateTime.now());
                            inbox.setInboxReceiveFlag(1);
                            inboxService.update(inbox);
                        }
                        inbox.setLetterStatus1(inboxService.checkLetterStatus(inbox, 1));
                        inbox.setLetterStatus2(inboxService.checkLetterStatus(inbox, 2));
                        inbox.setLetterStatus3(inboxService.checkLetterStatus(inbox, 3));
                        listInboxModel.add(inboxService.tranformToModel(inbox));
                    }
                }
            }
            status = Response.Status.OK;
            responseData.put("data", listInboxModel);
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

    @ApiOperation(
            value = "แก้ไขวันที่เปิด",
            notes = "แก้ไขวันที่เปิด",
            response = InboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Inbox by id success."),
        @ApiResponse(code = 404, message = "Inbox by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateOpenDate")
    public Response updateOpenDate(
            InboxModel inboxPostModel
    ) {
        LOG.debug("updateOpenDate...");
        Gson gs = new GsonBuilder()
                .setVersion(inboxPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Inbox by id not found in the database.");
        try {
            InboxService inboxService = new InboxService();
            Inbox inbox = inboxService.getById(inboxPostModel.getId());
            if (inbox != null) {
                inbox.setInboxOpenDate(LocalDateTime.now());
                inbox.setInboxOpenFlag(1);
                inbox.setLetterStatus1(inboxService.checkLetterStatus(inbox, 1));
                inbox = inboxService.update(inbox);
                status = Response.Status.OK;
                responseData.put("data", inboxService.tranformToModel(inbox));
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
            value = "แก้ไขวันที่กระทำ",
            notes = "แก้ไขวันที่กระทำ",
            response = InboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Inbox by id success."),
        @ApiResponse(code = 404, message = "Inbox by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateActionDate")
    public Response updateActionDate(
            InboxModel inboxPostModel
    ) {
        LOG.debug("updateActionDate...");
        Gson gs = new GsonBuilder()
                .setVersion(inboxPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Inbox by id not found in the database.");
        try {
            InboxService inboxService = new InboxService();
            Inbox inbox = inboxService.getById(inboxPostModel.getId());
            if (inbox != null) {
                inbox.setInboxActionDate(LocalDateTime.now());
                inbox.setInboxActionFlag(1);
                inbox.setLetterStatus2(inboxService.checkLetterStatus(inbox, 2));
                inboxService.update(inbox);

                status = Response.Status.OK;
                responseData.put("data", inboxService.tranformToModel(inbox));
                responseData.put("message", "");
            }
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
            value = "แก้ไขวันที่เสร็จ",
            notes = "แก้ไขวันที่เสร็จ",
            response = InboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Inbox by id success."),
        @ApiResponse(code = 404, message = "Inbox by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateFinishDate")
    public Response updateFinishDate(
            InboxModel inboxPostModel
    ) {
        LOG.debug("updateFinishDate...");
        Gson gs = new GsonBuilder()
                .setVersion(inboxPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Inbox by id not found in the database.");
        try {
            InboxService inboxService = new InboxService();
            Inbox inbox = inboxService.getById(inboxPostModel.getId());

            inbox.setInboxFinishDate(LocalDateTime.now());
            inbox.setInboxFinishFlag(1);
            inbox.setLetterStatus3(inboxService.checkLetterStatus(inbox, 3));
            inboxService.update(inbox);

            status = Response.Status.OK;
            responseData.put("data", inboxService.tranformToModel(inbox));
            responseData.put("message", "");

            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for search Inbox",
            notes = "Method for search Inbox",
            responseContainer = "List",
            response = InboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Inbox list success."),
        @ApiResponse(code = 404, message = "Inbox list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/search")
    public Response search(
            @BeanParam ListOptionModel listOptionModel,
            InboxSearchModel inboxSearchModel
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
            InboxService inboxService = new InboxService();
            int folderId;
            String search;
            if (inboxSearchModel.getUserId() == 0) {//searchStructure
                search = "structureFolderId";
                StructureFolderService structureFolderService = new StructureFolderService();
                folderId = structureFolderService.getByStructureId(inboxSearchModel.getStructureId(), "I").getId();
            } else {//searchUser
                search = "userProfileFolderId";
                UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
                folderId = userProfileFolderService.getByUserProfileId(inboxSearchModel.getUserId(), "I").getId();
            }

            List<Inbox> listInbox = inboxService.searchInboxByModel(search, folderId, inboxSearchModel, listOptionModel.getSort(), listOptionModel.getDir());
            ArrayList<InboxModel> listInboxModel = new ArrayList<>();
            if (!listInbox.isEmpty()) {
                listInbox.forEach((inbox) -> {
                    listInboxModel.add(inboxService.tranformToModel(inbox));
                });
                listInboxModel.trimToSize();
            }
            status = Response.Status.OK;
            responseData.put("data", listInboxModel);
            responseData.put("message", "inbox list success.");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "get List of Inbox by workflowId",
            notes = "get List of Inbox by workflowId",
            responseContainer = "List",
            response = InboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Inbox list success."),
        @ApiResponse(code = 404, message = "Inbox list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/list/workflow/{workflowId}")
    public Response listByLinktId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "workflowId", value = "workflowId", required = true)
            @PathParam("workflowId") int workflowId
    ) {
        LOG.info("listByLinktId...");
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
        responseData.put("message", "Inbox by workflowId not found in the database.");
        try {
            InboxService inboxService = new InboxService();
            List<Inbox> listInbox = inboxService.listAllByWorkflowId(workflowId, "orderNo", "asc");
            List<InboxModel> listInboxModel = new ArrayList<>();
            if (!listInbox.isEmpty()) {
                for (Inbox inbox : listInbox) {
                    if (inbox.getRemovedBy() == 0) {
                        listInboxModel.add(inboxService.tranformToModel(inbox));
                    } else if (inbox.getInboxOpenFlag() == 1) {
                        listInboxModel.add(inboxService.tranformToModel(inbox));
                    }
                }
            }
            status = Response.Status.OK;
            responseData.put("data", listInboxModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "รายการกล่องข้อมูลเข้าโดยรหัสผู้ใช้งาน",
            notes = "รายการกล่องข้อมูลเข้าโดยรหัสผู้ใช้งาน",
            responseContainer = "List",
            response = InboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Inbox list success."),
        @ApiResponse(code = 404, message = "Inbox list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/user/{userId}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listByUserProfileId(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "userId", value = "รหัสผู้ใช้งาน", required = true)
            @PathParam("userId") int userId
    ) {
        LOG.debug("listByUserProfileId...");
        LOG.debug("userId = " + userId);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Inbox by id not found in the database.");
        try {
            InboxService inboxService = new InboxService();
            List<InboxModel> listInboxModel = new ArrayList<>();
            ListReturnModel listReturnModel = new ListReturnModel(0, 0, 0);
            UserProfileFolder userProfileFolder = new UserProfileFolderService().getByUserProfileId(userId, "I");
            if (userProfileFolder != null) {
                int userProfileFolderId = userProfileFolder.getId();
                List<Inbox> listInbox = inboxService.listByUserProfileFolderId(userProfileFolderId, listOptionModel.getLimit(), listOptionModel.getOffset(), listOptionModel.getSort(), listOptionModel.getDir(), 0);
                if (!listInbox.isEmpty()) {
                    int count = listInbox.size() + listOptionModel.getOffset();
                    int countAll = inboxService.countListByUserProfileFolderId(userProfileFolderId, 0);
                    int next = 0;
                    if (count >= listOptionModel.getLimit()) {
                        next = listOptionModel.getOffset() + listOptionModel.getLimit();
                        if (next >= countAll) {
                            next = 0;
                        }
                    }
                    listReturnModel = new ListReturnModel(countAll, count, next);
                    for (Inbox inbox : listInbox) {
                        if (inbox.getInboxReceiveFlag() == 0) {
                            inbox.setInboxReceiveDate(LocalDateTime.now());
                            inbox.setInboxReceiveFlag(1);
                            inboxService.update(inbox);
                        }
                        inbox.setLetterStatus1(inboxService.checkLetterStatus(inbox, 1));
                        inbox.setLetterStatus2(inboxService.checkLetterStatus(inbox, 2));
                        inbox.setLetterStatus3(inboxService.checkLetterStatus(inbox, 3));
                        listInboxModel.add(inboxService.tranformToModel(inbox));
                    }
                }
            }
            status = Response.Status.OK;
            responseData.put("data", listInboxModel);
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

    @ApiOperation(
            value = "ลบข้อมูลกล่องข้อมูลเข้า ไม่สร้างถังขยะ",
            notes = "ลบข้อมูลกล่องข้อมูลเข้า ไม่สร้างถังขย",
            response = InboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Inbox removed by id success."),
        @ApiResponse(code = 404, message = "Inbox by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/removeNoRecyc/{id}")
    public Response removeNoRecyc(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสกล่องข้อมูลเข้า", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("removeNoRecyc...");
        LOG.debug("id = " + id);
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
        responseData.put("message", "Inbox by id not found in the database.");
        try {
            InboxService inboxService = new InboxService();
            Inbox inbox = inboxService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (inbox != null) {
                status = Response.Status.OK;
                responseData.put("data", inboxService.tranformToModel(inbox));
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
            value = "profile outbox",
            notes = "profile outbox",
            response = InboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfileFolder by userID success."),
        @ApiResponse(code = 404, message = "UserProfileFolder by userID not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/userProfileFolderIdTypeO/{userID}/{userType}")
    public Response getUserProfileFolderIdTypeO(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userID", value = "user/structure Id", required = true)
            @PathParam("userID") int userID,
            @ApiParam(name = "userType", value = "user/structure type", required = true)
            @PathParam("userType") int userType
    ) {
        LOG.debug("getUserProfileFolderIdTypeO...");
        LOG.debug("id = " + userID);
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
        responseData.put("message", "UserProfileFolder by userID not found in the database.");
        try {
            int[] result = {0, 0};

            if (userType == 0) {
                UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
                List<UserProfileFolder> listUserProfileFolder = userProfileFolderService.listByUserProfileId(userID, "O");
                result[0] = listUserProfileFolder.get(0).getId();
                result[1] = 0;
            } else {
                StructureFolderService structureFolderService = new StructureFolderService();
                List<StructureFolder> listStructureFolder = structureFolderService.listByStructureId(userID, "O");
                result[0] = 0;
                result[1] = listStructureFolder.get(0).getId();
            }
            status = Response.Status.OK;
            responseData.put("data", result);
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
            value = "report Inbox",
            notes = "report Inbox",
            responseContainer = "List",
            response = InboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Inbox list success."),
        @ApiResponse(code = 404, message = "Inbox list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/report/{jobType}")
    public Response report(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "jobType", value = "jobType", required = true)
            @PathParam("jobType") String jobType,
            InboxSearchModel inboxSearchModel
    ) {
        LOG.info("report...");
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
            InboxService inboxService = new InboxService();
            int folderId;
            String search;

            String colHeader = "";
            if (inboxSearchModel.getUserId() == 0) {//searchStructure
                search = "structureFolderId";
                StructureFolderService structureFolderService = new StructureFolderService();
                folderId = structureFolderService.getByStructureId(inboxSearchModel.getStructureId(), "I").getId();

                colHeader = "หน่วยงาน: " + new StructureService().getById(inboxSearchModel.getStructureId()).getStructureName();
            } else {//searchUser
                search = "userProfileFolderId";
                UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
                folderId = userProfileFolderService.getByUserProfileId(inboxSearchModel.getUserId(), "I").getId();

                colHeader = "เจ้าหน้าที่: " + new UserProfileService().getById(inboxSearchModel.getUserId()).getUserProfileFullName();
            }
            String dateBegin = "ตั้งแต่วันที่ ";
            String start = inboxSearchModel.getInboxStartDate();
            if (start != null && !"".equals(start)) {
                dateBegin += inboxService.getDateRange(start);
            } else {
                dateBegin += "-";
            }
            String dateEnd = " ถึงวันที่ ";
            String end = inboxSearchModel.getInboxEndDate();
            if (end != null && !"".equals(end)) {
                dateEnd += inboxService.getDateRange(end);
            } else {
                dateEnd += "-";
            }

            List<Inbox> listInbox = inboxService.searchInboxByModel(search, folderId, inboxSearchModel, listOptionModel.getSort(), listOptionModel.getDir());
            if (!listInbox.isEmpty()) {
                TempTableService tempTableService = new TempTableService();
                for (Inbox inbox : listInbox) {
                    InboxModel inboxModel = inboxService.tranformToModel(inbox);

                    TempTable tempTable = new TempTable();
                    tempTable.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    tempTable.setComputerName(httpHeaders.getHeaderString("clientIp"));
                    tempTable.setJobType(jobType);

                    tempTable.setText01(inboxModel.getInboxFrom());
                    tempTable.setText02(inboxModel.getInboxTitle());
                    tempTable.setStr01(inboxModel.getInboxSendDate());
                    tempTable.setText03(inboxModel.getInboxNote());
                    tempTable.setText04(inboxModel.getInboxDescription());
                    tempTable.setStr02(inboxModel.getInboxOpenFlag() == 0 ? "ยังไม่เปิดอ่าน" : "เปิดอ่านแล้ว");
                    tempTable.setText05(colHeader);
                    tempTable.setText06(dateBegin + dateEnd);
                    tempTableService.create(tempTable);
                }
            }
            status = Response.Status.OK;
            responseData.put("data", null);
            responseData.put("message", "inbox list success.");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
            value = "updateข้อมูลการส่งกล่องข้อมูลเข้า",
            notes = "uodateข้อมูลการส่งกล่องข้อมูลเข้า",
            response = InboxModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Inbox created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateSendNote(
            InboxModel inboxPostModel
    ) {
        LOG.info("update...");
        Gson gs = new GsonBuilder()
                .setVersion(inboxPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            InboxService inboxService = new InboxService();
            Inbox inbox = inboxService.getById(inboxPostModel.getId());
            if (inbox != null) {
                inbox.setInboxNote(inboxPostModel.getInboxNote());
            }
            inboxService.update(inbox);
            status = Response.Status.CREATED;
            responseData.put("data", inboxService.tranformToModel(inbox));
            responseData.put("message", "Inbox updated successfully.");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
