package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Holiday;
import com.px.admin.model.HolidayModel;
import com.px.admin.service.HolidayService;
import com.px.share.model.ListOptionModel;
import com.px.share.model.VersionModel;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
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
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
@Api(value = "Holiday วันหยุดประจำปี")
@Path("v1/holidays")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class HolidayResource {
    private static final Logger LOG = Logger.getLogger(HolidayResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create Holiday", 
        notes = "สร้างวันหยุดประจำปี",
        response = HolidayModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "Holiday created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        HolidayModel holidayModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(holidayModel.getVersion())
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
        try{
            Holiday holiday = new Holiday();
            holiday.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            holiday.setHolidayDate(dateThaiToLocalDateTime(holidayModel.getHolidayDate()));
            holiday.setHolidayName(holidayModel.getHolidayName());
            HolidayService holidayService = new HolidayService();
            holiday = holidayService.create(holiday);
            
            status = Response.Status.CREATED;
            responseData.put("data", holidayService.tranformToModel(holiday));
            responseData.put("success", true);
            responseData.put("message", "Holiday created successfully.");
            
            //LogData For Create
            holidayService.saveLogForCreate(holiday,httpHeaders.getHeaderString("clientIp"));
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for get Holiday by id", 
        notes = "ขอวันหยุดประจำปี ด้วย รหัสวันหยุด", 
        response = HolidayModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Holiday by id success." ),
        @ApiResponse( code = 404, message = "Holiday by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสวันหยุด", required = true) 
        @PathParam("id") int id
    ) {
        LOG.debug("getById...");
        LOG.debug("id = "+id);
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
        responseData.put("message", "Holiday by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            HolidayService holidayService = new HolidayService();
            Holiday holiday = holidayService.getById(id);
            if(holiday!=null){
                status = Response.Status.OK;
                responseData.put("data", holidayService.tranformToModel(holiday));
                responseData.put("message", "");
            }
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for update Holiday.", 
        notes = "แก้ไขวันหยุดประจำปี", 
        response = HolidayModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Holiday updeted by id success." )
        ,@ApiResponse( code = 404, message = "Holiday by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสวันหยุด", required = true) 
        @PathParam("id") int id, 
        HolidayModel holidayModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(holidayModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Holiday by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            HolidayService holidayService = new HolidayService();
            Holiday holiday = holidayService.getById(id);
            Holiday holidayUpdate = null;
            if(holiday!=null){
                holiday.setHolidayName(holidayModel.getHolidayName());
                holiday.setHolidayDate(dateThaiToLocalDateTime(holidayModel.getHolidayDate()));
                holiday.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                holidayUpdate = holidayService.update(holiday);
                status = Response.Status.OK;
                responseData.put("data", holidayService.tranformToModel(holidayUpdate));
                responseData.put("message", "");
                
                //LogData For Update
                holidayService.saveLogForUpdate(holiday,holidayUpdate, httpHeaders.getHeaderString("clientIp"));
            }
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
        value = "Method for delete Holiday by id.", 
        notes = "ลบวันหยุดประจำปี", 
        response = HolidayModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Holiday deleted by id success." )
        ,@ApiResponse( code = 404, message = "Holiday by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสวันหยุด", required = true) 
        @PathParam("id") int id
    ) {
        LOG.debug("remove...");
        LOG.debug("id = "+id);
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
        responseData.put("message", "Holiday by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            HolidayService holidayService = new HolidayService();
            Holiday holiday = holidayService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(holiday!=null){
                status = Response.Status.OK;
                responseData.put("data", holidayService.tranformToModel(holiday));
                responseData.put("message", "");
                
                //LogData For Remove
                holidayService.saveLogForRemove(holiday, httpHeaders.getHeaderString("clientIp"));
            }
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
        value = "Method for list Holiday.", 
        notes = "รายการวันหยุด", 
        responseContainer = "List",
        response = HolidayModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Holiday list success." ),
        @ApiResponse( code = 404, message = "Holiday list not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response list(
        @BeanParam ListOptionModel listOptionModel,
        @ApiParam(name = "dateFrom", value = "วันที่เริ่ม", required = false) 
        @DefaultValue("0") @QueryParam("dateFrom") String dateFrom,
        @ApiParam(name = "dateTo", value = "วันที่เริ่ม", required = false) 
        @DefaultValue("0") @QueryParam("dateTo") String dateTo
    ) {
        LOG.debug("list...");
        LOG.debug("offset = "+listOptionModel.getOffset());
        LOG.debug("limit = "+listOptionModel.getLimit());
        LOG.debug("dateFrom = "+dateFrom);
        LOG.debug("dateTo = "+dateTo);
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
        responseData.put("message", "Holiday list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            HolidayService holidayService = new HolidayService();
            List<Holiday> listHoliday = null;
            if(dateFrom.equalsIgnoreCase("0")){
                listHoliday = holidayService.listByCurrentYear(listOptionModel.getOffset(),listOptionModel.getLimit(),listOptionModel.getSort(), listOptionModel.getDir());
            }else{
                listHoliday = holidayService.listByDateRange(dateThaiToLocalDateTime(dateFrom),dateThaiToLocalDateTime(dateTo),listOptionModel.getOffset(),listOptionModel.getLimit(),listOptionModel.getSort(), listOptionModel.getDir());
            }
            if(!listHoliday.isEmpty()){
                ArrayList<HolidayModel> listHolidayModel = new ArrayList<>();
                for (Holiday holiday : listHoliday) {
                    listHolidayModel.add(holidayService.tranformToModel(holiday));
                }
                listHolidayModel.trimToSize();
                responseData.put("data", listHolidayModel);
                responseData.put("message", "");
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Count workday (Except holiday and weekend)", 
        notes = "นับวันทำการ", 
        response = HolidayModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Count workday success." ),
        @ApiResponse( code = 404, message = "Count workday not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/countWorkday")
    public Response countWorkday(
        @BeanParam ListOptionModel listOptionModel,
        @ApiParam(name = "beginDate", value = "วันที่เริ่มต้น", required = true) 
        @DefaultValue("") @QueryParam("beginDate") String beginDate,
        @ApiParam(name = "endDate", value = "วันที่สิ้นสุด", required = true) 
        @DefaultValue("") @QueryParam("endDate") String endDate
    ) {
        LOG.debug("countWorkday...");
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
        responseData.put("message", "Holiday by id not found in the database.");
        responseData.put("errorMessage", "");
        try{            
            HolidayService holidayService = new HolidayService();
            int numwork = holidayService.countWorkday(beginDate,endDate);
            HashMap numMap = new HashMap();
            numMap.put("numWorkday",numwork);
            responseData.put("data", numMap);
            responseData.put("message", "");
            status = Response.Status.OK;
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
