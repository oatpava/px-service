package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.model.ListOptionModel;
import com.px.share.model.ListReturnModel;
import com.px.share.model.VersionModel;
import com.px.share.util.TreeUtil;
import com.px.wf.entity.WfDocumentType;
import com.px.wf.model.WfDocumentTypeModel;
import com.px.wf.service.WfDocumentTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.log4j.Logger;

/**
 *
 * @author CHALERMPOL
 */
@Api(value = "WfDocumentType ประเภทเอกสาร")
@Path("v1/wf/wfDocumentTypes")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WfDocumentTypeResource {

    private static final Logger LOG = Logger.getLogger(WfDocumentTypeResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create WfDocumentType",
            notes = "สร้างข้อมูลประเภทเอกสาร",
            response = WfDocumentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "WfDocumentType created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            WfDocumentTypeModel documentTypeModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(documentTypeModel.getVersion())
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
            WfDocumentTypeService documentTypeService = new WfDocumentTypeService();
            WfDocumentType documentType = new WfDocumentType();
            documentType.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            documentType.setDocumentTypeDetail(documentTypeModel.getDetail());
            documentType.setDocumentTypeName(documentTypeModel.getName());
            documentType.setDocumentTypeCode(documentTypeModel.getCode());
            documentType.setParentId(documentTypeModel.getParentId());
            documentType.setNodeLevel(documentTypeModel.getNodeLevel());
            documentType.setParentKey(documentTypeModel.getParentKey());
            documentType = documentTypeService.create(documentType);
            responseData.put("data", documentTypeService.tranformToModel(documentType));
            responseData.put("message", "WfDocumentType created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get WfDocumentType by id",
            notes = "ขอข้อมูลประเภทเอกสาร ด้วย รหัสประเภทเอกสาร",
            response = WfDocumentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfDocumentType by id success.")
        ,
        @ApiResponse(code = 404, message = "WfDocumentType by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสประเภทเอกสาร", required = true)
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
        responseData.put("message", "WfDocumentType by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfDocumentTypeService documentTypeService = new WfDocumentTypeService();
            WfDocumentType documentType = documentTypeService.getById(id);
            if (documentType != null) {
                status = Response.Status.OK;
                responseData.put("data", documentTypeService.tranformToModel(documentType));
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
            value = "Method for update WfDocumentType.",
            notes = "แก้ไขข้อมูลประเภทเอกสาร",
            response = WfDocumentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfDocumentType updeted by id success.")
        ,@ApiResponse(code = 404, message = "WfDocumentType by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(
            WfDocumentTypeModel documentTypeModel
    ) {
        LOG.debug("update...");
        Gson gs = new GsonBuilder()
                .setVersion(documentTypeModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfDocumentType by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfDocumentTypeService documentTypeService = new WfDocumentTypeService();
            WfDocumentType documentType = documentTypeService.getById(documentTypeModel.getId());
            if (documentType != null) {
                if (documentTypeModel.getParentId() != 0) {
                    WfDocumentType documentTypeParent = documentTypeService.getById(documentTypeModel.getParentId());
                    documentType.setDocumentTypeCode(documentTypeParent.getDocumentTypeCode() + documentType.getDocumentTypeCode().substring(documentType.getDocumentTypeCode().length() - 3));
                }else{
                    documentType.setDocumentTypeCode(documentType.getDocumentTypeCode().substring(documentType.getDocumentTypeCode().length() - 3));
                } 
                documentType.setDocumentTypeDetail(documentTypeModel.getDetail());
                documentType.setDocumentTypeName(documentTypeModel.getName());
                documentType.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                documentType.setParentId(documentTypeModel.getParentId());
                documentType.setParentKey(documentTypeService.generateParentKey(documentType));
                documentType.setNodeLevel(TreeUtil.generateNodeLevel(documentType));
                documentType = documentTypeService.update(documentType);
                List<List<WfDocumentType>> listUpdateData =  new ArrayList<List<WfDocumentType>>();
                listUpdateData.add(documentTypeService.listChildByParentId("", "", documentType.getId()));
                documentTypeService.updateDocumentCodeChildByParent(listUpdateData, new ArrayList<Integer>(Arrays.asList(documentType.getId())),new ArrayList<String>(Arrays.asList(documentType.getDocumentTypeCode())));
                status = Response.Status.OK;
                responseData.put("data", documentTypeService.tranformToModel(documentType));
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
            value = "Method for delete WfDocumentType by id.",
            notes = "ลบข้อมูลประเภทเอกสาร",
            response = WfDocumentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfDocumentType deleted by id success.")
        ,@ApiResponse(code = 404, message = "WfDocumentType by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสประเภทเอกสาร", required = true)
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
        responseData.put("message", "WfDocumentType by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfDocumentTypeService documentTypeService = new WfDocumentTypeService();
            WfDocumentType documentType = documentTypeService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (documentType != null) {
                status = Response.Status.OK;
                responseData.put("data", documentTypeService.tranformToModel(documentType));
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
            value = "Method for list WfDocumentType By WfDocumentType Id.",
            notes = "รายการประเภทเอกสาร",
            responseContainer = "List",
            response = WfDocumentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfDocumentType list By WfDocumentType Id success.")
        ,
        @ApiResponse(code = 404, message = "WfDocumentType list By WfDocumentType Id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response list(
            @BeanParam ListOptionModel listOptionModel
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
        ArrayList<WfDocumentTypeModel> listWfDocumentTypeModel = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = status = Response.Status.OK;
        responseData.put("data", listWfDocumentTypeModel);
        responseData.put("success", false);
        responseData.put("message", "WfDocumentType list By WfDocumentType Id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfDocumentTypeService documentTypeService = new WfDocumentTypeService();
            List<WfDocumentType> listWfDocumentType = documentTypeService.list(listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir());
            if (!listWfDocumentType.isEmpty()) {
                listWfDocumentTypeModel = new ArrayList<>();
                for (WfDocumentType documentType : listWfDocumentType) {
                    listWfDocumentTypeModel.add(documentTypeService.tranformToModel(documentType));
                }
                listWfDocumentTypeModel.trimToSize();
                responseData.put("data", listWfDocumentTypeModel);
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
            value = "Method for search WfDocumentType By field.",
            notes = "ค้นหาประเภทเอกสาร",
            responseContainer = "List",
            response = WfDocumentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfDocumentType search success.")
        ,
        @ApiResponse(code = 404, message = "WfDocumentType search not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/search")
    public Response searchByFields(
            @Context UriInfo uriInfo,
            @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.debug("searchByFields...");
        LOG.debug("offset = " + listOptionModel.getOffset());
        LOG.debug("limit = " + listOptionModel.getLimit());
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        ArrayList<WfDocumentTypeModel> listWfDocumentTypeModel = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", listWfDocumentTypeModel);
        responseData.put("success", false);
        responseData.put("message", "WfDocumentType search not found in the database.");
        responseData.put("errorMessage", "");
        try {
//            String filedSearch = ",documentTypeName,documentTypeCode,";
            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
//            for (String key : queryParams.keySet()) {
//                if(filedSearch.contains(","+key+",")){
//                    for (String value : queryParams.get(key)) {
//                        String[] valueArray = value.split(",");
//                        int i = 0;
//                        for(i = 0;i<valueArray.length;i++){
//                            LOG.debug("key = " + key + " value = " + valueArray[i]);
//                        }
//                    }
//                }                
//            }
            WfDocumentTypeService documentTypeService = new WfDocumentTypeService();
            List<WfDocumentType> listWfDocumentType = documentTypeService.search(queryParams, listOptionModel.getOffset(), listOptionModel.getOffset(), listOptionModel.getSort(), listOptionModel.getDir());
            if (!listWfDocumentType.isEmpty()) {
                int countAll = documentTypeService.countAll();
                int count = listWfDocumentType.size();
                int next = 0;
                if (count >= listOptionModel.getLimit()) {
                    next = listOptionModel.getOffset() + listOptionModel.getLimit();
                }
                if (next >= countAll) {
                    next = 0;
                }
                ListReturnModel listReturnModel = new ListReturnModel(countAll, count, next);
                listWfDocumentTypeModel = new ArrayList<>();
                for (WfDocumentType documentType : listWfDocumentType) {
                    listWfDocumentTypeModel.add(documentTypeService.tranformToModel(documentType));
                }
                listWfDocumentTypeModel.trimToSize();
                responseData.put("data", listWfDocumentTypeModel);
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
            value = "Method for search WfDocumentType by Name or Code.",
            notes = "ค้นหาประเภทเอกสารชื่อหรือตัวย่อ",
            responseContainer = "List",
            response = WfDocumentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfDocumentType search by Name or Code success.")
        ,
        @ApiResponse(code = 404, message = "WfDocumentType search by Name or Code not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/searchnameorcode")
    public Response searchByNameOrCode(
            @Context UriInfo uriInfo,
            @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.debug("searchByNameOrCode...");
        LOG.debug("offset = " + listOptionModel.getOffset());
        LOG.debug("limit = " + listOptionModel.getLimit());
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        ArrayList<WfDocumentTypeModel> listWfDocumentTypeModel = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", listWfDocumentTypeModel);
        responseData.put("success", false);
        responseData.put("message", "WfDocumentType search not found in the database.");
        responseData.put("errorMessage", "");
        try {
            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
            WfDocumentTypeService documentTypeService = new WfDocumentTypeService();
            List<WfDocumentType> listWfDocumentType = documentTypeService.searchByNameOrCode(queryParams, listOptionModel.getOffset(), listOptionModel.getOffset(), listOptionModel.getSort(), listOptionModel.getDir());
            if (!listWfDocumentType.isEmpty()) {
                int countAll = documentTypeService.countAll();
                int count = listWfDocumentType.size();
                int next = 0;
                if (count >= listOptionModel.getLimit()) {
                    next = listOptionModel.getOffset() + listOptionModel.getLimit();
                }
                if (next >= countAll) {
                    next = 0;
                }
                ListReturnModel listReturnModel = new ListReturnModel(countAll, count, next);
                listWfDocumentTypeModel = new ArrayList<>();
                for (WfDocumentType documentType : listWfDocumentType) {
                    listWfDocumentTypeModel.add(documentTypeService.tranformToModel(documentType));
                }
                listWfDocumentTypeModel.trimToSize();
                responseData.put("data", listWfDocumentTypeModel);
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
            value = "Method for get all WfDocumentType",
            notes = "ขอข้อมูลประเภทเอกสารทั้งหมด",
            response = WfDocumentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfDocumentType success.")
        ,
        @ApiResponse(code = 404, message = "WfDocumentType not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listAll")
    public Response listAll(
            @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.debug("getById...");
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
        responseData.put("message", "WfDocumentType by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            ArrayList<WfDocumentTypeModel> listWfDocumentTypeModel;
            WfDocumentTypeService documentTypeService = new WfDocumentTypeService();
            List<WfDocumentType> listDocumentType = documentTypeService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if (!listDocumentType.isEmpty()) {
                listWfDocumentTypeModel = new ArrayList<>();
                for (WfDocumentType documentType : listDocumentType) {
                    listWfDocumentTypeModel.add(documentTypeService.tranformToModel(documentType));
                }
                listWfDocumentTypeModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listWfDocumentTypeModel);
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
            value = "Method for get list all parent of WfDocumentType",
            notes = "ขอข้อมูลประเภทเอกสารตั้งต้น",
            response = WfDocumentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "List all parent of WfDocumentType success.")
        ,
        @ApiResponse(code = 404, message = "List all parent of WfDocumentType not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listAllParent")
    public Response listAllParent(
            @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.debug("AllParent...");
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
        responseData.put("message", "List all parent of WfDocumentType not found in the database.");
        responseData.put("errorMessage", "");
        try {
            ArrayList<WfDocumentTypeModel> listWfDocumentTypeModel;
            WfDocumentTypeService documentTypeService = new WfDocumentTypeService();
            List<WfDocumentType> listDocumentType = documentTypeService.listAllParent(listOptionModel.getSort(), listOptionModel.getDir());
            if (!listDocumentType.isEmpty()) {
                listWfDocumentTypeModel = new ArrayList<>();
                for (WfDocumentType documentType : listDocumentType) {
                    listWfDocumentTypeModel.add(documentTypeService.tranformToModel(documentType));
                }
                listWfDocumentTypeModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listWfDocumentTypeModel);
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
            value = "Method for get list WfDocumentType by parentId",
            notes = "ขอข้อมูลประเภทเอกสาร ด้วย รหัสประเภทเอกสารตั้งต้น",
            response = WfDocumentTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "List WfDocumentType by parentId success.")
        ,
        @ApiResponse(code = 404, message = "List WfDocumentType by parentId not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listChild/{parentId}")
    public Response listChildByParentId(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "parentId", value = "รหัสประเภทเอกสารตั้งต้น", required = true)
            @PathParam("parentId") int parentId
    ) {
        LOG.debug("listChildByParentId...");
        LOG.debug("parentId = " + parentId);
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("success", false);
        ArrayList<WfDocumentTypeModel> listWfDocumentTypeModel = new ArrayList<>();
        responseData.put("data", listWfDocumentTypeModel);
        responseData.put("message", "WfDocumentType by parentId not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfDocumentTypeService documentTypeService = new WfDocumentTypeService();
            List<WfDocumentType> listDocumentType = documentTypeService.listChildByParentId(listOptionModel.getSort(), listOptionModel.getDir(), parentId);
            if (!listDocumentType.isEmpty()) {
                for (WfDocumentType documentType : listDocumentType) {
                    listWfDocumentTypeModel.add(documentTypeService.tranformToModel(documentType));
                }
                listWfDocumentTypeModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listWfDocumentTypeModel);
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
