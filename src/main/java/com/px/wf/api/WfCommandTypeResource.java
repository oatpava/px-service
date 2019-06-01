
package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.model.VersionModel;
import com.px.wf.entity.WfCommandType;
import com.px.wf.model.WfCommandTypeModel;
import com.px.wf.service.WfCommandTypeService;
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
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
@Api(value = "WfCommandType ประเภทของทะเบียนสารบรรณประเภทย่อยคำสั่ง")
@Path("v1/wfCommandTypes") 
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WfCommandTypeResource {
    private static final Logger LOG = Logger.getLogger(WfCommandTypeResource.class.getName());
    
    @ApiOperation(
            value = "รายการทั้งหมดประเภทของทะเบียนสารบรรณประเภทย่อยคำสั่ง",
            notes = "รายการทั้งหมดประเภทของทะเบียนสารบรรณประเภทย่อยคำสั่ง",
            responseContainer = "List",
            response = WfCommandTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfCommandType list success."),
        @ApiResponse(code = 404, message = "WfCommandType list not found in the database."),
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
        responseData.put("message", "WfCommandType by id not found in the database.");
        try {
            WfCommandTypeService commandTypeService = new WfCommandTypeService();
            List<WfCommandType> listCommandType = commandTypeService.listAll(sort, dir);
            if (!listCommandType.isEmpty()) {
                ArrayList<WfCommandTypeModel> listCommandTypeModel = new ArrayList<>();
                for (WfCommandType commandType : listCommandType) {
                    listCommandTypeModel.add(commandTypeService.tranformToModel(commandType));
                }
                listCommandTypeModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listCommandTypeModel);
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
