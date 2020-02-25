package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.wf.entity.ThegifDepartment;
import com.px.wf.model.get.ThegifDepartmentModel;
import com.px.wf.model.get.ThegifDepartmentModel_hasChild;
import com.px.wf.model.post.ThegifDepartmentPostModel;
import com.px.wf.service.ThegifDepartmentService;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import org.apache.log4j.Logger;

/**
 *
 * @author Mali
 */

@Api(value = "ThegifDepartment โครงสร้างของการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ")
@Path("v1/thegifDepartments")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ThegifDepartmentResource {

    private static final Logger log = Logger.getLogger(ThegifDepartmentResource.class.getName());

    @ApiOperation(
            value = "Method for create ThegifDepartment",
            notes = "สร้างข้อมูลโครงสร้างการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ",
            response = ThegifDepartmentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "ThegifDepartment created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            @HeaderParam("userProfileId") int userProfileId,
            @HeaderParam("clientIp") String clientIp, 
            @BeanParam ThegifDepartmentPostModel thegifDepartmentPostModel
    ) {
        log.info("create...");
        log.info("userProfileId..."+userProfileId);
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
            ThegifDepartment thegifDepartment = new ThegifDepartment();
            //userID = 1;
            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
            thegifDepartment.setCreatedBy(userProfileId);
            if (thegifDepartment != null) {
                thegifDepartment.setThegifDepartmentName(thegifDepartmentPostModel.getThegifDepartmentName());
                thegifDepartment.setThegifDepartmentCode(thegifDepartmentPostModel.getThegifDepartmentCode());
                thegifDepartment.setParentId(thegifDepartmentPostModel.getParentId());
                thegifDepartment.setThegifDepartmentServiceName(thegifDepartmentPostModel.getThegifDepartmentServiceName());
                //nodelevel and parentKey
                ThegifDepartment parentThegifDepartment = new ThegifDepartment();
                parentThegifDepartment = thegifDepartmentService.getById(thegifDepartmentPostModel.getParentId());

                thegifDepartment.setNodeLevel(parentThegifDepartment.getNodeLevel() + 1);
                thegifDepartment.setParentKey(parentThegifDepartment.getParentKey() + thegifDepartment.getId() + "฿");
                thegifDepartment = thegifDepartmentService.create(thegifDepartment);

                thegifDepartment.setOrderNo(thegifDepartment.getId());
                thegifDepartment.setUpdatedBy(userProfileId);
                thegifDepartmentService.update(thegifDepartment);

                responseData.put("data", thegifDepartmentService.tranformToModel(thegifDepartment));
            }
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "ThegifDepartment created successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for delete ThegifDepartment by id.",
            notes = "ลบข้อมูลโครงสร้างการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ",
            response = ThegifDepartmentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ThegifDepartment deleted by id success."),
        @ApiResponse(code = 404, message = "ThegifDepartment by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @HeaderParam("userProfileId") int userProfileId,
            @HeaderParam("clientIp") String clientIp, 
            @ApiParam(name = "id", value = "รหัสโครงสร้างการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ", required = true)
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
        responseData.put("message", "ThegifDepartment by id not found in the database.");
        try {
            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
            ThegifDepartment thegifDepartment = thegifDepartmentService.remove(id, userProfileId);
            if (thegifDepartment != null) {
                status = Response.Status.OK;
                responseData.put("data", thegifDepartmentService.tranformToModel(thegifDepartment));
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
            value = "Method for list ThegifDepartment By ThegifDepartment Id.",
            notes = "รายการโครงสร้างหน่วยงานลูกในหน่วยงาน",
            responseContainer = "List",
            response = ThegifDepartmentModel_hasChild.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ThegifDepartment list By thegifDepartment Id success."),
        @ApiResponse(code = 404, message = "ThegifDepartment list By thegifDepartment Id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listByThegifDepartmentId")
    public Response listByThegifDepartmentId(
            @HeaderParam("userProfileId") int userProfileId,
            @HeaderParam("clientIp") String clientIp, 
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("asc") @QueryParam("dir") String dir,
            @ApiParam(name = "thegifDepartmentId", value = "รหัสหน่วยงาน", required = true)
            @DefaultValue("0") @QueryParam("thegifDepartmentId") int thegifDepartmentId
    ) {
        log.info("listByThegifDepartmentId...");
        log.info("sort = " + sort);
        log.info("dir = " + dir);
        log.info("thegifDepartmentId = " + thegifDepartmentId);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "thegifDepartmentId list By thegifDepartment Id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            ThegifDepartment thegifDepartmentInput = new ThegifDepartment();
            thegifDepartmentInput.setId(thegifDepartmentId);

            ArrayList<ThegifDepartmentModel_hasChild> listThegifDepartmentModel_hasChild = new ArrayList<>();

            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
            List<ThegifDepartment> listThegifDepartment = thegifDepartmentService.listChild(thegifDepartmentInput, sort, dir);

            if (!listThegifDepartment.isEmpty()) {
                for (ThegifDepartment thegifDepartment : listThegifDepartment) {
                    listThegifDepartmentModel_hasChild.add(thegifDepartmentService.tranformToModelCheckHasChild(thegifDepartment));
                }
            }

            listThegifDepartmentModel_hasChild.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listThegifDepartmentModel_hasChild);
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
            value = "Method for ThegifDepartment By ThegifDepartmentCode.",
            notes = "ขอข้อมูลโครงสร้างหน่วยงาน ด้วย รหัสหน่วยงาน",
            response = ThegifDepartmentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ThegifDepartment By ThegifDepartmentCode success."),
        @ApiResponse(code = 404, message = "ThegifDepartment By ThegifDepartmentCode not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getByThegifDepartmentCode")
    public Response getByThegifDepartmentCode(
            @HeaderParam("userProfileId") int userProfileId,
            @HeaderParam("clientIp") String clientIp, 
            @ApiParam(name = "thegifDepartmentCode", value = "รหัสหน่วยงาน", required = true)
            @QueryParam("thegifDepartmentCode") String thegifDepartmentCode
    ) {
        log.info("getByThegifDepartmentCode...");
        log.info("thegifDepartmentCode = " + thegifDepartmentCode);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "thegifDepartmentId By thegifDepartmentCode not found in the database.");
        responseData.put("errorMessage", "");
        try {
            ThegifDepartment thegifDepartment = new ThegifDepartment();
            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
            thegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(thegifDepartmentCode);

            status = Response.Status.OK;
            responseData.put("data", thegifDepartmentService.tranformToModel(thegifDepartment));
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
            value = "Method for search ThegifDepartment",
            notes = "ค้นหารายการโครงสร้างหน่วยงาน",
            responseContainer = "List",
            response = ThegifDepartmentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ThegifDepartment list success."),
        @ApiResponse(code = 404, message = "ThegifDepartment list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/searchThegifDepartment")
    public Response SearchThegifDepartment(
            @Context UriInfo uriInfo,
            @ApiParam(name = "thegifDepartmentName", value = "ชื่อหน่วยงาน", required = false)
            @QueryParam("thegifDepartmentName") String thegifDepartmentName,
            @ApiParam(name = "thegifDepartmentCode", value = "รหัสหน่วยงาน", required = false)
            @QueryParam("thegifDepartmentCode") String thegifDepartmentCode,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("desc") @QueryParam("dir") String dir
    ) {
        log.info("SearchThegifDepartment...");
        log.info("thegifDepartmentName = " + thegifDepartmentName);
        log.info("thegifDepartmentCode = " + thegifDepartmentCode);
        log.info("sort = " + sort);
        log.info("dir = " + dir);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        Gson gs1 = new Gson();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "thegifDepartment list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();

            String fieldSearch = "thegifDepartmentName,thegifDepartmentCode";
            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
            for (String key : queryParams.keySet()) {
                if (fieldSearch.contains(key)) {
                    for (String value : queryParams.get(key)) {
                        String[] valueArray = value.split(",");
                        int i = 0;
                        for (i = 0; i < valueArray.length; i++) {
                            log.info("key = " + key + " value = " + valueArray[i]);
                        }
                    }
                }
            }
            List<ThegifDepartment> listThegifDepartment = thegifDepartmentService.searchThegifDepartment(queryParams, sort, dir);
            ArrayList<ThegifDepartmentModel> listThegifDepartmentModel = new ArrayList();
            
            if (!listThegifDepartment.isEmpty()) {
                for (ThegifDepartment thegifDepartment : listThegifDepartment) {
                    listThegifDepartmentModel.add(thegifDepartmentService.tranformToModel(thegifDepartment));
                }
            }

            listThegifDepartmentModel.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listThegifDepartmentModel);
            responseData.put("message", "");
            responseData.put("success", true);

        } catch (Exception ex) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

}
