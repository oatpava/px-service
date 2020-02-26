package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.wf.entity.ThegifDocFile;
import com.px.wf.model.get.ThegifDepartmentModel;
import com.px.wf.model.get.ThegifDocFileModel;
import com.px.wf.model.post.ThegifDocFilePostModel;
import com.px.wf.service.ThegifDocFileService;
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
import javax.ws.rs.HeaderParam;
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
@Api(value = "ThegifDocFile เอกสารของการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ")
@Path("v1/thegifDocFiles")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ThegifDocFileResource {

    private static final Logger log = Logger.getLogger(ThegifDocFileResource.class.getName());

    @ApiOperation(
            value = "Method for create ThegifDocFile",
            notes = "สร้างข้อมูลเอกสารของการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ",
            response = ThegifDocFileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "ThegifDocFile created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            @HeaderParam("userProfileId") int userProfileId,
            @HeaderParam("clientIp") String clientIp,
            @BeanParam ThegifDocFilePostModel thegifDocFilePostModel
    ) {
        log.info("create...");
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
            ThegifDocFile thegifDocFile = new ThegifDocFile();
            //userID = 1;
            ThegifDocFileService thegifDocFileService = new ThegifDocFileService();
            thegifDocFile.setCreatedBy(userProfileId);
            if (thegifDocFile != null) {
                thegifDocFile.setThegifId(thegifDocFilePostModel.getThegifId());
                thegifDocFile.setThegifDocFileFileId(thegifDocFilePostModel.getThegifDocFileFileId());
                thegifDocFile.setThegifDocFileLetter(thegifDocFilePostModel.getThegifDocFileLetter());
                thegifDocFile.setThegifDocFileLetterType(thegifDocFilePostModel.getThegifDocFileLetterType());
                thegifDocFile.setDmsDocumentId(thegifDocFilePostModel.getDmsDocumentId());
                thegifDocFile = thegifDocFileService.create(thegifDocFile);

                thegifDocFile.setOrderNo(thegifDocFile.getId());
                thegifDocFile.setUpdatedBy(userProfileId);
                thegifDocFileService.update(thegifDocFile);

                responseData.put("data", thegifDocFileService.tranformToModel(thegifDocFile));
            }
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "ThegifDocFile created successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for delete ThegifDocFile by id.",
            notes = "ลบข้อมูลเอกสารของการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ",
            response = ThegifDepartmentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ThegifDocFile deleted by id success."),
        @ApiResponse(code = 404, message = "ThegifDocFile by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @HeaderParam("userProfileId") int userProfileId,
            @HeaderParam("clientIp") String clientIp,
            @ApiParam(name = "id", value = "รหัสเอกสารของการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ", required = true)
            @PathParam("id") int id
    ) {
        log.info("remove...");
        log.info("id = " + id);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "ThegifDocFile by id not found in the database.");
        try {
            ThegifDocFileService thegifDocFileService = new ThegifDocFileService();
            ThegifDocFile thegifDocFile = thegifDocFileService.remove(id, userProfileId);
            if (thegifDocFile != null) {
                status = Response.Status.OK;
                responseData.put("data", thegifDocFileService.tranformToModel(thegifDocFile));
                responseData.put("message", "");
            }
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list ThegifDocFile By Thegif Id.",
            notes = "ขอรายการเอกสาร ด้วย รหัสการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ",
            responseContainer = "List",
            response = ThegifDocFile.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ThegifDocFile list By Thegif Id success."),
        @ApiResponse(code = 404, message = "ThegifDocFile list By Thegif Id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listByThegifId")
    public Response listByThegifId(
            @HeaderParam("userProfileId") int userProfileId,
            @HeaderParam("clientIp") String clientIp,
            @ApiParam(name = "thegifId", value = "รหัสหน่วยงาน", required = true)
            @DefaultValue("0") @QueryParam("thegifId") int thegifId,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("asc") @QueryParam("dir") String dir
    ) {
        log.info("listByThegifId...");
        log.info("thegifId = " + thegifId);
        log.info("sort = " + sort);
        log.info("dir = " + dir);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "ThegifDocFile list By Thegif Id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            ThegifDocFileService thegifDocFileService = new ThegifDocFileService();
            List<ThegifDocFile> listDocFile = new ArrayList<>();
            listDocFile = thegifDocFileService.listByThegifId(thegifId, sort, dir);

            ArrayList<ThegifDocFileModel> listDocFileModel = new ArrayList<>();
            if (!listDocFile.isEmpty()) {
                for (ThegifDocFile thegifDocFile : listDocFile) {
                    listDocFileModel.add(thegifDocFileService.tranformToModelAndPathFile(thegifDocFile));
                }
            }
            listDocFileModel.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listDocFileModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    } 

    @ApiOperation(
            value = "Method for list ThegifDocFile By Thegif Id.",
            notes = "ขอรายการเอกสาร ด้วย รหัสการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ",
            responseContainer = "List",
            response = ThegifDocFile.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ThegifDocFile list By Thegif Id success."),
        @ApiResponse(code = 404, message = "ThegifDocFile list By Thegif Id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listByThegifIdfor85")
    public Response listByThegifIdfor85(
            @HeaderParam("userProfileId") int userProfileId,
            @HeaderParam("clientIp") String clientIp,
            @ApiParam(name = "thegifId", value = "รหัสหน่วยงาน", required = true)
            @DefaultValue("0") @QueryParam("thegifId") int thegifId,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("asc") @QueryParam("dir") String dir
    ) {
        log.info("listByThegifIdfor85...");
        log.info("thegifId = " + thegifId);
        log.info("sort = " + sort);
        log.info("dir = " + dir);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "ThegifDocFile list By Thegif Id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            ThegifDocFileService thegifDocFileService = new ThegifDocFileService();
            List<ThegifDocFile> listDocFile = new ArrayList<>();
            listDocFile = thegifDocFileService.listByThegifId(thegifId, sort, dir);

            ArrayList<ThegifDocFileModel> listDocFileModel = new ArrayList<>();
            if (!listDocFile.isEmpty()) {
                for (ThegifDocFile thegifDocFile : listDocFile) {
                    listDocFileModel.add(thegifDocFileService.tranformToModelAndPathFile(thegifDocFile));
                }
            }
            listDocFileModel.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listDocFileModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }    
    
}
