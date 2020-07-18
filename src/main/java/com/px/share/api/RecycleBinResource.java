package com.px.share.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Title;
import com.px.admin.model.TitleModel;
import com.px.admin.service.TitleService;
import com.px.dms.entity.DmsDocument;
import com.px.dms.service.DmsDocumentService;
import com.px.dms.service.DmsSearchService;
import com.px.share.entity.RecycleBin;
import com.px.share.model.RecycleBinModel;
import com.px.share.service.RecycleBinService;
import com.px.share.model.ListOptionModel;
import com.px.share.model.ListReturnModel;
import com.px.share.model.RecycleBinSearchModel;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
@Api(value = "RecycleBin ถังขยะ")
@Path("v1/recycleBins")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class RecycleBinResource {

    private static final Logger LOG = Logger.getLogger(RecycleBinResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for get RecycleBin by id",
            notes = "ขอข้อมูลถังขยะ ด้วย รหัสถังขยะ",
            response = RecycleBinModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "RecycleBin by id success.")
        ,
        @ApiResponse(code = 404, message = "RecycleBin by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสถังขยะ", required = true)
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
        responseData.put("message", "RecycleBin by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            RecycleBinService recycleBinService = new RecycleBinService();
            RecycleBin recycleBin = recycleBinService.getById(id);
            if (recycleBin != null) {
                status = Response.Status.OK;
                responseData.put("data", recycleBinService.tranformToModel(recycleBin));
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
            value = "ลบข้อมูลข้อมูลถังขยะด้วยรหัสข้อมูลถังขยะ",
            notes = "ลบข้อมูลถังขยะ",
            response = RecycleBinModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "RecycleBin deleted by id success.")
        ,@ApiResponse(code = 404, message = "RecycleBin by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสถังขยะ", required = true)
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
        responseData.put("message", "RecycleBin by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            RecycleBinService recycleBinService = new RecycleBinService();
            RecycleBin recycleBin = recycleBinService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (recycleBin != null) {
                status = Response.Status.OK;
                responseData.put("data", recycleBinService.tranformToModel(recycleBin));
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
            value = "Method for search RecycleBin By firstName.",
            notes = "ค้นหาถังขยะ",
            responseContainer = "List",
            response = RecycleBinModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "RecycleBin search success.")
        ,
        @ApiResponse(code = 404, message = "RecycleBin search not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/search")
    public Response searchByFields(
            @Context UriInfo uriInfo,
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "mode", value = "โหมดการค้นหา", required = false)
            @DefaultValue("0")
            @QueryParam("mode") int mode
    ) {
        LOG.debug("searchByFields...");
        LOG.debug("offset = " + listOptionModel.getOffset());
        LOG.debug("limit = " + listOptionModel.getLimit());
        LOG.debug("mode = " + mode);
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
        responseData.put("message", "RecycleBin search not found in the database.");
        responseData.put("errorMessage", "");
        responseData.put("data", "");
        ListReturnModel listReturnModel = new ListReturnModel(0, 0, 0);
        responseData.put("list", listReturnModel);
        try {
            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();

            RecycleBinService recycleBinService = new RecycleBinService();
            List<RecycleBin> listRecycleBin = null;
            if (mode == 0) {
                listRecycleBin = recycleBinService.searchByUserProfileId(queryParams, listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
            } else {
                listRecycleBin = recycleBinService.search(queryParams, listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir());
            }
            if (!listRecycleBin.isEmpty()) {
                int countAll = 0;
                if (mode == 0) {
                    countAll = recycleBinService.countSearchByUserProfileId(queryParams, Integer.parseInt(httpHeaders.getHeaderString("userID")));
                } else {
                    countAll = recycleBinService.countSearch(queryParams);
                }
                int count = listRecycleBin.size();
                int next = 0;
                if (count >= listOptionModel.getLimit()) {
                    next = listOptionModel.getOffset() + listOptionModel.getLimit();
                }
                if (next >= countAll) {
                    next = 0;
                }
                listReturnModel = new ListReturnModel(countAll, count, next);
                ArrayList<RecycleBinModel> listRecycleBinModel = new ArrayList<>();
                RecycleBinModel recycleBinModel = null;
                for (RecycleBin recycleBin : listRecycleBin) {
                    recycleBinModel = recycleBinService.tranformToModel(recycleBin);
                    listRecycleBinModel.add(recycleBinModel);
                }
                listRecycleBinModel.trimToSize();
                responseData.put("data", listRecycleBinModel);
                responseData.put("list", listReturnModel);
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
            value = "Method for Restore RecycleBin by id.",
            notes = "กู้คืนข้อมูล",
            response = RecycleBinModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "RecycleBin restored by id success.")
        ,@ApiResponse(code = 404, message = "RecycleBin restored by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}/restore")
    public Response restore(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสถังขยะ", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("update...");
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
        responseData.put("message", "RecycleBin by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            RecycleBinService recycleBinService = new RecycleBinService();
            RecycleBin recycleBin = recycleBinService.getByIdNotRemoved(id);
            RecycleBin recycleBinUpdate = null;

            if (recycleBin != null) {
                recycleBinUpdate = recycleBinService.restoreEntity(recycleBin);
//                if ("com.px.dms.entity.DmsDocument".equals(recycleBin.getEntityName())) {
//                    DmsDocumentService dmsDocumentService = new DmsDocumentService();
//                    DmsDocument dmsDocument = dmsDocumentService.getById(recycleBin.getLinkId());
//                    String searchId = dmsDocument.getDmsSearchId();
//                    DmsSearchService dmsSearchService = new DmsSearchService();
//                    dmsSearchService.updateDatareStore(searchId);
//                }

                recycleBinUpdate = recycleBinService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
                status = Response.Status.OK;
                responseData.put("data", recycleBinService.tranformToModel(recycleBinUpdate));
                responseData.put("message", "");

                //LogData For Update
                recycleBinService.saveLogForRestoreEntity(recycleBin, httpHeaders.getHeaderString("clientIp"));
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
            value = "รายการถังขยะโดยรหัสผู้ใช้งาน",
            notes = "รายการถังขยะโดยรหัสผู้ใช้งาน",
            responseContainer = "List",
            response = RecycleBinModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "RecycleBin list success.")
        ,
        @ApiResponse(code = 404, message = "RecycleBin list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/user/{userId}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listByUserId(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "userId", value = "รายการรหัสผู้ใช้งาน", required = true)
            @PathParam("userId") int userId
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
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "RecycleBin by userId not found in the database.");
        try {
            List<RecycleBinModel> listRecycleBinModel = new ArrayList<>();
            ListReturnModel listReturnModel = new ListReturnModel(0, 0, 0);
            
            RecycleBinService recycleBinService = new RecycleBinService();
            List<RecycleBin> listRecycleBin = recycleBinService.listByUserId(listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir(), userId);                    
            if (!listRecycleBin.isEmpty()) {
                for (RecycleBin recycleBin : listRecycleBin) {
                    listRecycleBinModel.add(recycleBinService.tranformToModel(recycleBin));
                }
                int count = listRecycleBin.size() + listOptionModel.getOffset();
                int countAll = recycleBinService.countListByUserId(userId);
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
            responseData.put("data", listRecycleBinModel);
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
            value = "Method for search RecycleBin",
            notes = "Method for search RecycleBin",
            response = RecycleBinModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "RecycleBin list success.")
        ,
        @ApiResponse(code = 404, message = "RecycleBin list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/search")
    public Response search(
            @BeanParam ListOptionModel listOptionModel,
            RecycleBinSearchModel recycleBinSearchModel
    ) {
//      log.info("searchInbox...");
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
        responseData.put("message", "RecycleBin list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            RecycleBinService recycleBinService = new RecycleBinService();
            List<RecycleBin> listRecycleBin = recycleBinService.searchByModel(recycleBinSearchModel, listOptionModel.getSort(), listOptionModel.getDir());
            ArrayList<RecycleBinModel> listRecycleBinModel = new ArrayList<>();
            if (!listRecycleBin.isEmpty()) {
                listRecycleBin.forEach((outbox) -> {
                    listRecycleBinModel.add(recycleBinService.tranformToModel(outbox));
                });
                listRecycleBinModel.trimToSize();

            }
            status = Response.Status.OK;
            responseData.put("data", listRecycleBinModel);
            responseData.put("message", "inbox list success.");
            responseData.put("success", true);

        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
