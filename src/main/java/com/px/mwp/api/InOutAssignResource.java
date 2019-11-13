package com.px.mwp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.mwp.entity.InOutAssign;
import com.px.mwp.model.InOutAssignModel;
import com.px.mwp.model.InOutAssignModel_groupUserStructure;
import com.px.mwp.service.InOutAssignService;
import com.px.share.model.ListOptionModel;
import com.px.share.model.VersionModel;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author Mali
 */
@Api(value = "InOutAssign กำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก")
@Path("v1/inOutAssigns")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class InOutAssignResource {

    private static final Logger LOG = Logger.getLogger(InOutAssignResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "สร้างข้อมูลกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก",
            notes = "สร้างข้อมูลกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก",
            response = InOutAssignModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "InOutAssign created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            InOutAssignModel inOutAssignPostModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(inOutAssignPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            InOutAssign inOutAssign = new InOutAssign();
            //get Data from InOutFieldPostModel

            inOutAssign.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            inOutAssign.setInOutAssignOwnerId(inOutAssignPostModel.getInOutAssignOwnerId());
            inOutAssign.setInOutAssignAssignId(inOutAssignPostModel.getInOutAssignAssignId());
            inOutAssign.setInOutAssignOwnerType(inOutAssignPostModel.getInOutAssignOwnerType());
            inOutAssign.setInOutAssignStartDate(dateThaiToLocalDateTime(inOutAssignPostModel.getInOutAssignStartDate()));
            inOutAssign.setInOutAssignEndDate(dateThaiToLocalDateTime(inOutAssignPostModel.getInOutAssignEndDate()));

            if (inOutAssignPostModel.getInOutAssignEndDate() == null || inOutAssignPostModel.getInOutAssignEndDate().equals("")) {
                inOutAssign.setInOutAssignIsperiod(0);
            } else {
                inOutAssign.setInOutAssignIsperiod(1);
            }

            InOutAssignService inOutFieldService = new InOutAssignService();
            if (inOutAssign != null) {
                inOutAssign = inOutFieldService.create(inOutAssign);

                inOutAssign.setOrderNo(inOutAssign.getId());
                inOutFieldService.update(inOutAssign);
            }

            status = Response.Status.CREATED;
            responseData.put("data", inOutFieldService.tranformToModel(inOutAssign));
            responseData.put("success", true);
            responseData.put("message", "InOutAssign created successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ลบข้อมูลกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก",
            notes = "ลบข้อมูลกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก",
            response = InOutAssignModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "InOutAssign deleted by id success.")
        ,
        @ApiResponse(code = 404, message = "InOutAssign by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก", required = true)
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
        responseData.put("message", "InOutAssign by id not found in the database.");
        try {
            InOutAssignService inOutAssignService = new InOutAssignService();
            InOutAssign inOutAssign = inOutAssignService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (inOutAssign != null) {
                status = Response.Status.OK;
                responseData.put("data", inOutAssignService.tranformToModel(inOutAssign));
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
            value = "ลบข้อมูลกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก",
            notes = "ลบข้อมูลกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก",
            response = InOutAssignModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "InOutAssign delete by id success.")
        ,
        @ApiResponse(code = 404, message = "InOutAssign by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/delete/{id}")
    public Response delete(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("delete...");
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
        responseData.put("message", "InOutAssign by id not found in the database.");
        try {
            InOutAssignService inOutAssignService = new InOutAssignService();
            InOutAssign inOutAssign = inOutAssignService.getById(id);
            if (inOutAssign != null) {
                inOutAssignService.delete(inOutAssign);

                status = Response.Status.OK;
                responseData.put("data", "Delete success.");
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
            value = "รายการกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก ด้วย รหัสผู้ได้รับการกำหนดให้เปิดกล่องหนังสือเข้า/กล่องหนังสือออก",
            notes = "รายการกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก ด้วย รหัสผู้ได้รับการกำหนดให้เปิดกล่องหนังสือเข้า/กล่องหนังสือออก",
            responseContainer = "List",
            response = InOutAssignModel_groupUserStructure.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "InOutAssign list success.")
        ,
        @ApiResponse(code = 404, message = "InOutAssign list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listByAssignIdHeader")
    public Response listByAssignIdHeader(
            @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.info("listByAssignIdHeader...");
        LOG.info("userID = " + Integer.parseInt(httpHeaders.getHeaderString("userID")));
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
        responseData.put("message", "InOutAssign by id not found in the database.");
        try {
            InOutAssignService inOutAssignService = new InOutAssignService();
            List<InOutAssign> listInoutAssign = inOutAssignService.listByAssignId(Integer.parseInt(httpHeaders.getHeaderString("userID")), listOptionModel.getSort(), listOptionModel.getDir());
            HashMap<String, Integer> hashMapAssignId = new HashMap<String, Integer>();
            if (!listInoutAssign.isEmpty()) {
                ArrayList<InOutAssignModel_groupUserStructure> inOutAssignModel_groupUserStructure = new ArrayList();
                List<UserProfile> listUserProfile = new ArrayList();
                List<Structure> listStructure = new ArrayList();

                //add userID
                StructureService structureService = new StructureService();
                UserProfileService userProfileService = new UserProfileService();
                UserProfile userProfile1 = userProfileService.getById(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                listUserProfile.add(userProfile1);

                String keyText = "";
                for (InOutAssign inOutAssign : listInoutAssign) {
                    keyText = String.valueOf(inOutAssign.getInOutAssignOwnerId()) + String.valueOf(inOutAssign.getInOutAssignOwnerType());
                    if (hashMapAssignId.get(keyText) == null) {
                        if (inOutAssign.getInOutAssignIsperiod() == 0) {
                            if (inOutAssign.getInOutAssignOwnerType() == 0) {
                                UserProfile userProfile = userProfileService.getById(inOutAssign.getInOutAssignOwnerId());
                                if (userProfile.getRemovedBy() == 0) {
                                    listUserProfile.add(userProfile);
                                }
                            } else {
                                Structure structure = structureService.getById(inOutAssign.getInOutAssignOwnerId());
                                if (structure.getRemovedBy() == 0) {
                                    listStructure.add(structure);
                                }
                            }
                            hashMapAssignId.put(keyText, 1);
                        } else {
                            LocalDateTime today = LocalDateTime.now();

                            if ((today.isAfter(inOutAssign.getInOutAssignStartDate()) && today.isBefore(inOutAssign.getInOutAssignEndDate())) || today.equals(inOutAssign.getInOutAssignStartDate()) || today.equals(inOutAssign.getInOutAssignEndDate())) {
                                if (inOutAssign.getInOutAssignOwnerType() == 0) {
                                    UserProfile userProfile = userProfileService.getById(inOutAssign.getInOutAssignOwnerId());
                                    if (userProfile.getRemovedBy() == 0) {
                                        listUserProfile.add(userProfile);
                                    }
                                } else {
                                    Structure structure = structureService.getById(inOutAssign.getInOutAssignOwnerId());
                                    if (structure.getRemovedBy() == 0) {
                                        listStructure.add(structure);
                                    }
                                }
                                hashMapAssignId.put(keyText, 1);
                            }
                        }
                    }
                }
                if (!listStructure.isEmpty()) {
                    for (Structure structure : listStructure) {
                        inOutAssignModel_groupUserStructure.add(inOutAssignService.tranformToModelGroupUserStructure(structure));
                    }
                }
                for (UserProfile userProfile : listUserProfile) {
                    inOutAssignModel_groupUserStructure.add(inOutAssignService.tranformToModelGroupUserStructure(userProfile));
                }

                inOutAssignModel_groupUserStructure.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", inOutAssignModel_groupUserStructure);
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

//    @ApiOperation(
//            value = "รายการกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก ด้วย รหัสผู้ได้รับการกำหนดให้เปิดกล่องหนังสือเข้า/กล่องหนังสือออก",
//            notes = "รายการกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก ด้วย รหัสผู้ได้รับการกำหนดให้เปิดกล่องหนังสือเข้า/กล่องหนังสือออก",
//            responseContainer = "List",
//            response = InOutAssignModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "InOutAssign list success.")
//        ,
//        @ApiResponse(code = 404, message = "InOutAssign list not found in the database.")
//        ,
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/listByAssignId/{assignId}")
//    public Response listByAssignId(
//            @BeanParam ListOptionModel listOptionModel,
//            @ApiParam(name = "assignId", value = "รหัสผู้ได้รับการกำหนดให้เปิดกล่องหนังสือเข้า/กล่องหนังสือออก", required = true)
//            @PathParam("assignId") int assignId
//    ) {
//        LOG.info("listByAssignId...");
//        LOG.info("assignId = " + assignId);
//        Gson gs = new GsonBuilder()
//                .setVersion(listOptionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("data", null);
//        responseData.put("message", "InOutAssign by id not found in the database.");
//        try {
//            InOutAssignService inOutAssignService = new InOutAssignService();
//            List<InOutAssign> listInoutAssign = inOutAssignService.listByAssignId(assignId, listOptionModel.getSort(), listOptionModel.getDir());
//            HashMap<String, Integer> hashMapAssignId = new HashMap<String, Integer>();
//            if (!listInoutAssign.isEmpty()) {
//                ArrayList<InOutAssignModel_groupUserStructure> inOutAssignModel_groupUserStructure = new ArrayList();
//                List<UserProfile> listUserProfile = new ArrayList();
//                List<Structure> listStructure = new ArrayList();
//
//                //add userID
//                StructureService structureService = new StructureService();
//                UserProfileService userProfileService = new UserProfileService();
//                UserProfile userProfile1 = userProfileService.getById(assignId);
//                listUserProfile.add(userProfile1);
//
//                String keyText = "";
//                for (InOutAssign inOutAssign : listInoutAssign) {
//                    keyText = String.valueOf(inOutAssign.getInOutAssignOwnerId()) + String.valueOf(inOutAssign.getInOutAssignOwnerType());
//                    if (hashMapAssignId.get(keyText) == null) {
//                        if (inOutAssign.getInOutAssignIsperiod() == 0) {
//                            if (inOutAssign.getInOutAssignOwnerType() == 0) {
//                                UserProfile userProfile = userProfileService.getById(inOutAssign.getInOutAssignOwnerId());
//                                if (userProfile.getRemovedBy() == 0) {
//                                    listUserProfile.add(userProfile);
//                                }
//                            } else {
//                                Structure structure = structureService.getById(inOutAssign.getInOutAssignOwnerId());
//                                if (structure.getRemovedBy() == 0) {
//                                    listStructure.add(structure);
//                                }
//                            }
//                            hashMapAssignId.put(keyText, 1);
//                        } else {
//                            LocalDateTime today = LocalDateTime.now();
//
//                            if ((today.isAfter(inOutAssign.getInOutAssignStartDate()) && today.isBefore(inOutAssign.getInOutAssignEndDate())) || today.equals(inOutAssign.getInOutAssignStartDate()) || today.equals(inOutAssign.getInOutAssignEndDate())) {
//                                if (inOutAssign.getInOutAssignOwnerType() == 0) {
//                                    UserProfile userProfile = userProfileService.getById(inOutAssign.getInOutAssignOwnerId());
//                                    if (userProfile.getRemovedBy() == 0) {
//                                        listUserProfile.add(userProfile);
//                                    }
//                                } else {
//                                    Structure structure = structureService.getById(inOutAssign.getInOutAssignOwnerId());
//                                    if (structure.getRemovedBy() == 0) {
//                                        listStructure.add(structure);
//                                    }
//                                }
//                                hashMapAssignId.put(keyText, 1);
//                            }
//                        }
//                    }
//                }
//                if (!listStructure.isEmpty()) {
//                    for (Structure structure : listStructure) {
//                        inOutAssignModel_groupUserStructure.add(inOutAssignService.tranformToModelGroupUserStructure(structure));
//                    }
//                }
//                for (UserProfile userProfile : listUserProfile) {
//                    inOutAssignModel_groupUserStructure.add(inOutAssignService.tranformToModelGroupUserStructure(userProfile));
//                }
//
//                inOutAssignModel_groupUserStructure.trimToSize();
//                status = Response.Status.OK;
//                responseData.put("data", inOutAssignModel_groupUserStructure);
//                responseData.put("message", "");
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//
//        return Response.status(status)
//                .entity(gs.toJson(responseData)).build();
//    }
//    @ApiOperation(
//            value = "รายการกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก ด้วย รหัสเจ้าของกล่องหนังสือเข้า/กล่องหนังสือออก",
//            notes = "รายการกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก ด้วย รหัสเจ้าของกล่องหนังสือเข้า/กล่องหนังสือออก",
//            responseContainer = "List",
//            response = InOutAssignModel_groupInOutAssignAndUserName.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "InOutAssign list success."),
//        @ApiResponse(code = 404, message = "InOutAssign list not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/listByOwnerId/{ownerId}/ownerType/{ownerType}")
//    public Response listByOwnerId(
//            @BeanParam ListOptionModel listOptionModel,
//            @ApiParam(name = "ownerId", value = "รหัสเจ้าของกล่องหนังสือเข้า/กล่องหนังสือออก", required = true)
//            @PathParam("ownerId") int ownerId,
//            @ApiParam(name = "ownerType", value = "ประเภทเจ้าของกล่องหนังสือเข้า/กล่องหนังสือออก 0=User 1=Structure", required = true)
//            @PathParam("ownerType") int ownerType
//    ) {
//        LOG.info("listByOwnerId...");
//        LOG.info("ownerId = " + ownerId);
//        LOG.info("ownerType = " + ownerType);
//        Gson gs = new GsonBuilder()
//                .setVersion(listOptionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("data", null);
//        responseData.put("message", "InOutAssign by id not found in the database.");
//        try {
//            Calendar todayDate = Calendar.getInstance();
//            todayDate.set(Calendar.HOUR_OF_DAY, 0);
//            todayDate.set(Calendar.MINUTE, 0);
//            todayDate.set(Calendar.SECOND, 0);
//            todayDate.set(Calendar.MILLISECOND, 0);
//
//            InOutAssignService inOutAssignService = new InOutAssignService();
//            List<InOutAssign> listInoutAssign = inOutAssignService.listByOwnerId(ownerId, ownerType, listOptionModel.getSort(), listOptionModel.getDir());
//            HashMap<Integer, Integer> assignIdHashMap = new HashMap<>();
//            if (!listInoutAssign.isEmpty()) {
//                ArrayList<InOutAssignModel_groupInOutAssignAndUserName> listInOutAssignModel_groupInOutAssignAndUserName = new ArrayList();
//
//                for (InOutAssign inOutAssign : listInoutAssign) {
//                    ArrayList<InOutAssignModel_groupDate> listInOutAssignModel_groupDate = new ArrayList();
//                    if (assignIdHashMap.get(inOutAssign.getInOutAssignAssignId()) == null) {
//                        List<InOutAssign> listInOutAssignByOwnerIdAndAssignId = inOutAssignService.listByOwnerIdAndAssignId(ownerId, ownerType, inOutAssign.getInOutAssignAssignId(), listOptionModel.getSort(), listOptionModel.getDir());
//                        if (!listInOutAssignByOwnerIdAndAssignId.isEmpty()) {
//                            for (InOutAssign inOutAssign2 : listInOutAssignByOwnerIdAndAssignId) {
//                                InOutAssignModel_groupDate inOutAssignModel_groupDate = new InOutAssignModel_groupDate();
//                                inOutAssignModel_groupDate.setId(inOutAssign2.getId());
//                                inOutAssignModel_groupDate.setInOutAssignStartDate(Common.localDateTimeToString(inOutAssign2.getInOutAssignStartDate()));
//                                inOutAssignModel_groupDate.setInOutAssignEndDate(Common.localDateTimeToString(inOutAssign2.getInOutAssignEndDate()));
//
//                                if (inOutAssign2.getInOutAssignEndDate() != null) {
//                                    if (LocalDateTime.ofInstant(todayDate.toInstant(), ZoneId.systemDefault()).isAfter(inOutAssign2.getInOutAssignEndDate())) {
//                                        inOutAssignModel_groupDate.setCheckExpire(true);
//                                    } else {
//                                        inOutAssignModel_groupDate.setCheckExpire(false);
//                                    }
//                                } else {
//                                    inOutAssignModel_groupDate.setCheckExpire(false);
//                                }
//                                listInOutAssignModel_groupDate.add(inOutAssignModel_groupDate);
//                            }
//                        }
//                        listInOutAssignModel_groupInOutAssignAndUserName.add(inOutAssignService.tranformToModelGroupInOutAssignAndUserName(inOutAssign, listInOutAssignModel_groupDate));
//                        assignIdHashMap.put(inOutAssign.getInOutAssignAssignId(), 1);
//                    }
//                }
//
//                listInOutAssignModel_groupInOutAssignAndUserName.trimToSize();
//                status = Response.Status.OK;
//                responseData.put("data", listInOutAssignModel_groupInOutAssignAndUserName);
//                responseData.put("message", "");
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//
//        return Response.status(status)
//                .entity(gs.toJson(responseData)).build();
//    }
    @ApiOperation(
            value = "ลบข้อมูลกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออกที่เกินกำหนดเวลา",
            notes = "ลบข้อมูลกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออกที่เกินกำหนดเวลา",
            response = InOutAssignModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "InOutAssign deleted by id success.")
        ,
        @ApiResponse(code = 404, message = "InOutAssign by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/removeOverEndDate")
    public Response removeOverEndDate(
            @BeanParam VersionModel versionModel
    ) {
        LOG.info("removeOverEndDate...");
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
        responseData.put("message", "InOutAssign by id not found in the database.");
        try {
            InOutAssignService inOutAssignService = new InOutAssignService();
            List<InOutAssign> listInOutAssign = new ArrayList();
            List<InOutAssignModel> listInOutAssignModel = new ArrayList();

            Calendar todayDate = Calendar.getInstance();
            todayDate.set(Calendar.HOUR_OF_DAY, 0);
            todayDate.set(Calendar.MINUTE, 0);
            todayDate.set(Calendar.SECOND, 0);
            todayDate.set(Calendar.MILLISECOND, 0);

            listInOutAssign = inOutAssignService.listAfterEndDate(LocalDateTime.ofInstant(todayDate.toInstant(), ZoneId.systemDefault()));
            if (!listInOutAssign.isEmpty()) {
                for (InOutAssign inOutAssign : listInOutAssign) {
                    listInOutAssignModel.add(inOutAssignService.tranformToModel(inOutAssignService.remove(inOutAssign.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")))));
                }

                status = Response.Status.OK;
                responseData.put("data", listInOutAssignModel);
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
            value = "แก้ไขข้อมูลกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก",
            notes = "แก้ไขข้อมูลกำหนดการเปิดกล่องหนังสือเข้า/กล่องหนังสือออก",
            response = InOutAssignModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowType updeted by id success.")
        ,
        @ApiResponse(code = 404, message = "WorkflowType by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(
            InOutAssignModel inOutAssignModel
    ) {
        LOG.info("update...");
        Gson gs = new GsonBuilder()
                .setVersion(inOutAssignModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "InOutAssign by id not found in the database.");
        try {
            InOutAssignService inOutAssignService = new InOutAssignService();
            InOutAssign inOutAssign = inOutAssignService.getById(inOutAssignModel.getId());
            inOutAssign.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            inOutAssign.setInOutAssignStartDate(dateThaiToLocalDateTime(inOutAssignModel.getInOutAssignStartDate()));
            inOutAssign.setInOutAssignEndDate(dateThaiToLocalDateTime(inOutAssignModel.getInOutAssignEndDate()));

            if (inOutAssignModel.getInOutAssignEndDate() == null || inOutAssignModel.getInOutAssignEndDate().equals("")) {
                inOutAssign.setInOutAssignIsperiod(0);
            } else {
                inOutAssign.setInOutAssignIsperiod(1);
            }
            inOutAssign = inOutAssignService.update(inOutAssign);
            status = Response.Status.OK;
//            inOutAssignService.tranformToModelGroupInOutAssignAndUserName(inOutAssign)
            responseData.put("data", inOutAssignService.tranformToModel(inOutAssign));
            responseData.put("message", "");

            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat
    @ApiOperation(
            value = "list InOutAssign by ownerID",
            notes = "list InOutAssign by ownerID",
            responseContainer = "List",
            response = InOutAssignModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "InOutAssign list success.")
        ,
        @ApiResponse(code = 404, message = "InOutAssign list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/list/{ownerId}/{ownerType}")
    public Response listByOwnerId(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "ownerId", value = "ownerId", required = true)
            @PathParam("ownerId") int ownerId,
            @ApiParam(name = "ownerType", value = "ownerType 0=User 1=Structure", required = true)
            @PathParam("ownerType") int ownerType
    ) {
        LOG.info("listByOwnerId...");
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
        responseData.put("message", "InOutAssign by id not found in the database.");
        try {
//            Calendar todayDate = Calendar.getInstance();
//            todayDate.set(Calendar.HOUR_OF_DAY, 0);
//            todayDate.set(Calendar.MINUTE, 0);
//            todayDate.set(Calendar.SECOND, 0);
//            todayDate.set(Calendar.MILLISECOND, 0);

            InOutAssignService inOutAssignService = new InOutAssignService();
            List<InOutAssign> listInoutAssign = inOutAssignService.listByOwnerId(ownerId, ownerType, listOptionModel.getSort(), listOptionModel.getDir());
            ArrayList<InOutAssignModel> listInOutAssignModel = new ArrayList();
            if (!listInoutAssign.isEmpty()) {
                listInoutAssign.forEach(inoutAssign -> {
                    listInOutAssignModel.add(inOutAssignService.tranformToModel(inoutAssign));
                });

                listInOutAssignModel.trimToSize();
            }
            status = Response.Status.OK;
            responseData.put("data", listInOutAssignModel);
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

    //oat
    @ApiOperation(
            value = "list InOutAssign by assignId",
            notes = "list InOutAssign by assignId",
            responseContainer = "List",
            response = InOutAssignModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "InOutAssign list success.")
        ,
        @ApiResponse(code = 404, message = "InOutAssign list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/list/{assignId}")
    public Response listByAssignId(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "assignId", value = "assignId", required = true)
            @PathParam("assignId") int assignId
    ) {
        LOG.info("listByAssignId...");
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
        responseData.put("message", "InOutAssign by id not found in the database.");
        try {
            InOutAssignService inOutAssignService = new InOutAssignService();
            List<InOutAssign> listInoutAssign = inOutAssignService.listByAssignId(assignId, listOptionModel.getSort(), listOptionModel.getDir());
            List<InOutAssignModel> listInOutAssignModel = new ArrayList();
            if (!listInoutAssign.isEmpty()) {
                listInoutAssign.forEach(inoutAssign -> {
                    listInOutAssignModel.add(inOutAssignService.tranformToModel(inoutAssign));
                });
            }
            status = Response.Status.OK;
            responseData.put("data", listInOutAssignModel);
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

}
