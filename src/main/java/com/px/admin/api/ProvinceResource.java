package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Province;
import com.px.admin.model.ProvinceModel;
import com.px.admin.service.ProvinceService;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author Oat
 */
@Api(value = "Province จังหวัด")
@Path("v1/provinces")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ProvinceResource {

    private static final Logger LOG = Logger.getLogger(ProvinceResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create Province",
            notes = "สร้างข้อมูลจังหวัด",
            response = ProvinceModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Province created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            ProvinceModel provincePostModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(provincePostModel.getVersion())
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
            Province province = new Province();
            province.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            province.setCode(provincePostModel.getCode());
            province.setName(provincePostModel.getName());
            ProvinceService provinceService = new ProvinceService();
            province = provinceService.create(province);
            responseData.put("data", provinceService.tranformToModel(province));
            responseData.put("message", "Province created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get Province by id",
            notes = "ขอข้อมูลจังหวัด ด้วย รหัสจังหวัด",
            response = ProvinceModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Province by id success."),
        @ApiResponse(code = 404, message = "Province by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสจังหวัด", required = true)
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
        responseData.put("message", "Province by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            ProvinceService provinceService = new ProvinceService();
            Province province = provinceService.getById(id);
            if (province != null) {
                status = Response.Status.OK;
                responseData.put("data", provinceService.tranformToModel(province));
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
            value = "Method for update Province.",
            notes = "แก้ไขข้อมูลจังหวัด",
            response = ProvinceModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Province updeted by id success."),
        @ApiResponse(code = 404, message = "Province by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @ApiParam(name = "id", value = "รหัสจังหวัด", required = true)
            @PathParam("id") int id,
            ProvinceModel provincePostModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(provincePostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Province by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            ProvinceService provinceService = new ProvinceService();
            Province province = provinceService.getById(id);
            if (province != null) {
                province.setName(provincePostModel.getName());
                province.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                province = provinceService.update(province);
                status = Response.Status.OK;
                responseData.put("data", provinceService.tranformToModel(province));
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
            value = "Method for delete Province by id.",
            notes = "ลบข้อมูลจังหวัด",
            response = ProvinceModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Province deleted by id success."),
        @ApiResponse(code = 404, message = "Province by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสจังหวัด", required = true)
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
        responseData.put("message", "Province by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            ProvinceService provinceService = new ProvinceService();
            Province province = provinceService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (province != null) {
                status = Response.Status.OK;
                responseData.put("data", provinceService.tranformToModel(province));
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
            value = "Method for list Province.",
            notes = "รายการจังหวัด",
            responseContainer = "List",
            response = ProvinceModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Province list success.", response = ProvinceModel.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Province list not found in the database."),
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
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Province list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            ProvinceService provinceService = new ProvinceService();
            List<Province> listProvince = provinceService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if (!listProvince.isEmpty()) {
                ArrayList<ProvinceModel> listProvinceModel = new ArrayList<>();
                for (Province province : listProvince) {
                    listProvinceModel.add(provinceService.tranformToModel(province));
                }
                listProvinceModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listProvinceModel);
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
