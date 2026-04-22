package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Alert;
import com.px.admin.model.AlertModel;
import com.px.admin.service.AlertService;
import com.px.share.model.ListOptionModel;
import com.px.share.model.ListReturnModel;
import com.px.share.model.VersionModel;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
@Api(value = "Alert การแ้จงเตือน")
@Path("v1/alerts")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AlertResource {

    private static final Logger LOG = Logger.getLogger(AlertResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create Alert",
            notes = "สร้างการแจ้งเตือน",
            response = AlertModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Alert created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            AlertModel alertModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(alertModel.getVersion())
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
            AlertService alertService = new AlertService();
            Alert alert = new Alert();
            alert.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            alert.setStartDate(dateThaiToLocalDateTime(alertModel.getStartDate()));
            alert.setEndDate(dateThaiToLocalDateTime(alertModel.getEndDate()));
            alert.setMessage(alertModel.getMessage());
            alert.setActive(alertModel.getActive() ? "Y" : "N");
            alert = alertService.create(alert);

            status = Response.Status.CREATED;
            responseData.put("data", alertService.tranformToModel(alert));
            responseData.put("success", true);
            responseData.put("message", "Alert created successfully.");

        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get Alert by id",
            notes = "ขอการแจ้งเตือน ด้วย รหัสการแจ้งเตือน",
            response = AlertModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Alert by id success."),
        @ApiResponse(code = 404, message = "Alert by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสการแจ้งเตือน", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("getById...");
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
        responseData.put("message", "Alert by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            AlertService alertService = new AlertService();
            Alert alert = alertService.getById(id);
            if (alert != null) {
                status = Response.Status.OK;
                responseData.put("data", alertService.tranformToModel(alert));
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
            value = "Method for update Alert.",
            notes = "แก้ไขการแจ้งเตือน",
            response = AlertModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Alert updeted by id success."),
        @ApiResponse(code = 404, message = "Alert by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(
            AlertModel alertModel
    ) {
        LOG.debug("update...");
        Gson gs = new GsonBuilder()
                .setVersion(alertModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Alert by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            AlertService alertService = new AlertService();
            Alert alert = alertService.getById(alertModel.getId());
            if (alert != null) {
                alert.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                alert.setStartDate(dateThaiToLocalDateTime(alertModel.getStartDate()));
                alert.setEndDate(dateThaiToLocalDateTime(alertModel.getEndDate()));
                alert.setMessage(alertModel.getMessage());
                alert.setActive(alertModel.getActive() ? "Y" : "N");
                alert = alertService.update(alert);

                status = Response.Status.OK;
                responseData.put("data", alertService.tranformToModel(alert));
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
            value = "Method for delete Alert by id.",
            notes = "ลบการแจ้งเตือน",
            response = AlertModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Alert deleted by id success."),
        @ApiResponse(code = 404, message = "Alert by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสการแจ้งเตือน", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("remove...");
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
        responseData.put("message", "Alert by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            AlertService alertService = new AlertService();
            Alert alert = alertService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (alert != null) {
                status = Response.Status.OK;
                responseData.put("data", alertService.tranformToModel(alert));
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
            value = "Method for list Alert.",
            notes = "รายการการแจ้งเตือน",
            responseContainer = "List",
            response = AlertModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Alert list success."),
        @ApiResponse(code = 404, message = "Alert list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response list(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "active", value = "การใช้งาน", required = false)
            @QueryParam("active") Boolean active
    ) {
        LOG.debug("list...");
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
        responseData.put("message", "Alert list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            boolean islistAll = (listOptionModel.getLimit() == -1);

            AlertService alertService = new AlertService();
            ArrayList<AlertModel> listAlertModel = new ArrayList<>();
            ListReturnModel listReturnModel = new ListReturnModel(0, 0, 0);

            List<Alert> listAlert = islistAll
                    ? alertService.listAll(active, listOptionModel.getSort(), listOptionModel.getDir())
                    : alertService.list(active, listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir());
            if (!listAlert.isEmpty()) {
                for (Alert alert : listAlert) {
                    listAlertModel.add(alertService.tranformToModel(alert));
                }
                listAlertModel.trimToSize();

                if (islistAll) {
                    int count = listAlertModel.size() + listOptionModel.getOffset();
                    int countAll = alertService.countAll(active);
                    int next = 0;
                    if (count >= listOptionModel.getLimit()) {
                        next = listOptionModel.getOffset() + listOptionModel.getLimit();
                        if (next >= countAll) {
                            next = 0;
                        }
                    }
                    listReturnModel = new ListReturnModel(countAll, count, next);
                }
            }
            status = Response.Status.OK;
            responseData.put("success", true);
            responseData.put("data", listAlertModel);
            responseData.put("listReturn", listReturnModel);
            responseData.put("message", "");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list current Alert.",
            notes = "รายการการแจ้งเตือน ณ ปัจจุบัน",
            responseContainer = "List",
            response = AlertModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Alert list success."),
        @ApiResponse(code = 404, message = "Alert list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/current")
    public Response listCurrent(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "excludeIdList", value = "excludeIdList", required = false)
            @QueryParam("excludeIdList") String excludeIdList
    ) {
        LOG.debug("listCurrent...");
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
        responseData.put("message", "Alert list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            List<Integer> listExcludeId = null;
            if (excludeIdList != null && excludeIdList.length() != 0) {
                listExcludeId = Arrays.stream(excludeIdList.split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            }

            AlertService alertService = new AlertService();
            ArrayList<AlertModel> listAlertModel = new ArrayList<>();

            List<Alert> listAlert = alertService.listAllCurrent(listExcludeId);
            if (!listAlert.isEmpty()) {
                for (Alert alert : listAlert) {
                    listAlertModel.add(alertService.tranformToModel(alert));
                }
                listAlertModel.trimToSize();
            }
            status = Response.Status.OK;
            responseData.put("success", true);
            responseData.put("data", listAlertModel);
            responseData.put("message", "");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

}
