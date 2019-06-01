package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.model.VersionModel;
import com.px.wf.entity.WfCommandName;
import com.px.wf.model.WfCommandNameModel;
import com.px.wf.service.WfCommandNameService;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

/**
 *
 * @author Mali
 */
@Api(value = "WfCommandName รายการชื่อของคำสั่ง/ประกาศ/ระเบียบ")
@Path("v1/wfCommandNames")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WfCommandNameResource{

    private static final Logger LOG = Logger.getLogger(WfCommandNameResource.class.getName());

    @ApiOperation(
            value = "สร้างข้อมูลชื่อของคำสั่ง/ประกาศ/ระเบียบ",
            notes = "สร้างข้อมูลชื่อของคำสั่ง/ประกาศ/ระเบียบ",
            response = WfCommandNameModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "WfCommandName created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            WfCommandNameModel wfCommandNamePostModel
    ) {
        LOG.info("create...");
        LOG.info("wfCommandNamePostModel = " + wfCommandNamePostModel.getWfCommandNameName());
        Gson gs = new GsonBuilder()
                .setVersion(wfCommandNamePostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            WfCommandNameService wfCommandNameService = new WfCommandNameService();
            WfCommandName wfCommandName = new WfCommandName();
            wfCommandName.setCreatedBy(wfCommandNamePostModel.getUserId());

            if (wfCommandName != null) {
                wfCommandName.setWfCommandNameName(wfCommandNamePostModel.getWfCommandNameName());
                wfCommandName = wfCommandNameService.create(wfCommandName);

                wfCommandName.setOrderNo(wfCommandName.getId());
                wfCommandNameService.update(wfCommandName);

                responseData.put("data", wfCommandNameService.tranformToModel(wfCommandName));
                status = Response.Status.CREATED;
                responseData.put("success", true);
                responseData.put("message", "WfCommandName created successfully.");
            }
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
            value = "ลบข้อมูลรายการชื่อของคำสั่ง/ประกาศ/ระเบียบ ด้วย รหัสรายการชื่อของคำสั่ง/ประกาศ/ระเบียบ",
            notes = "ลบข้อมูลรายการชื่อของคำสั่ง/ประกาศ/ระเบียบ ด้วย รหัสรายการชื่อของคำสั่ง/ประกาศ/ระเบียบ",
            response = WfCommandNameModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfCommandName delete by id success."),
        @ApiResponse(code = 404, message = "WfCommandName by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/delete/{id}")
    public Response delete(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสรายการชื่อของคำสั่ง/ประกาศ/ระเบียบ", required = true)
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
        responseData.put("message", "WfCommandName by id not found in the database.");
        try {
            WfCommandNameService wfCommandNameService = new WfCommandNameService();
            WfCommandName wfCommandName = wfCommandNameService.getById(id);

            if (wfCommandName != null) {
                wfCommandNameService.delete(wfCommandName);

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
            value = "ลบข้อมูลรายการชื่อของคำสั่ง/ประกาศ/ระเบียบ ด้วย รหัสรายการชื่อของคำสั่ง/ประกาศ/ระเบียบ",
            notes = "ลบข้อมูลรายการชื่อของคำสั่ง/ประกาศ/ระเบียบ ด้วย รหัสรายการชื่อของคำสั่ง/ประกาศ/ระเบียบ",
            response = WfCommandNameModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfCommandName deleted by id success."),
        @ApiResponse(code = 404, message = "WfCommandName by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสแฟ้มทะเบียน", required = true)
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
        responseData.put("message", "WfCommandName by id not found in the database.");
        try {
            WfCommandNameService wfCommandNameService = new WfCommandNameService();
            WfCommandName wfCommandName = wfCommandNameService.remove(id, versionModel.getUserId());
            if (wfCommandName != null) {
                status = Response.Status.OK;
                responseData.put("data", wfCommandNameService.tranformToModel(wfCommandName));
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
            value = "รายการทั้งหมดของชื่อของคำสั่ง/ประกาศ/ระเบียบ",
            notes = "รายการทั้งหมดของชื่อของคำสั่ง/ประกาศ/ระเบียบ",
            responseContainer = "List",
            response = WfCommandNameModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfCommandName list success."),
        @ApiResponse(code = 404, message = "WfCommandName list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listAll(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("asc") @QueryParam("dir") String dir
    ) {
        LOG.info("listAll...");
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
        responseData.put("message", "WfCommandName by id not found in the database.");
        try {
            WfCommandNameService wfCommandNameService = new WfCommandNameService();
            List<WfCommandName> listWfCommandName = wfCommandNameService.listAll(sort, dir);
            if (!listWfCommandName.isEmpty()) {
                ArrayList<WfCommandNameModel> listWfCommandNameModel = new ArrayList<>();
                for (WfCommandName wfCommandName : listWfCommandName) {
                    listWfCommandNameModel.add(wfCommandNameService.tranformToModel(wfCommandName));
                }
                listWfCommandNameModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listWfCommandNameModel);
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
