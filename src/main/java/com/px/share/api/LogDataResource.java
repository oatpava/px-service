package com.px.share.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Structure;
import com.px.admin.entity.User;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.admin.service.UserService;
import com.px.share.entity.LogData;
import com.px.share.entity.TempTable;
import com.px.share.model.ListOptionModel;
import com.px.share.model.ListReturnModel;
import com.px.share.model.LogDataModel;
import com.px.share.model.LogDataModel_report;
import com.px.share.model.LogTypeModel;
import com.px.share.model.LogTypeModel_type;
import com.px.share.model.TempTableModel;
import com.px.share.model.VersionModel;
import com.px.share.service.LogDataService;
import com.px.share.service.TempTableService;
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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
@Api(value = "LogData ประวัติการใช้งานระบบ")
@Path("v1/logDatas")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class LogDataResource {

    private static final Logger LOG = Logger.getLogger(LogDataResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for get LogData by id",
            notes = "ขอข้อมูลประวัติการใช้งานระบบ ด้วย รหัสประวัติการใช้งานระบบ",
            response = LogDataModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "LogData by id success.")
        ,
        @ApiResponse(code = 404, message = "LogData by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสประวัติการใช้งานระบบ", required = true)
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
        responseData.put("message", "LogData by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            LogDataService logDataService = new LogDataService();
            LogData logData = logDataService.getById(id);
            if (logData != null) {
                status = Response.Status.OK;
                responseData.put("data", logDataService.tranformToModel(logData));
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
            value = "Method for search LogData By firstName.",
            notes = "ค้นหาประวัติการใช้งานระบบ",
            responseContainer = "List",
            response = LogDataModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "LogData search success.")
        ,
        @ApiResponse(code = 404, message = "LogData search not found in the database.")
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
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", new ArrayList<>());
        responseData.put("message", "UserProfile search not found in the database.");
        responseData.put("errorMessage", "");
        try {
            String fieldSearch = ",createdBy,createdDateBegin,createdDateEnd,type,moduleName,description,";
            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
            LogDataService logDataService = new LogDataService();
            List<LogDataModel_report> listLogDataModel_report = new ArrayList<>();
            List<LogData> listLogData = logDataService.search(queryParams, listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir());
            LOG.debug(listLogData.size());
            LOG.debug(listLogData.isEmpty());

            int createdBy = 0,type = 0;
//            List<Integer> type = new ArrayList<Integer>();
            String createdDateBegin = "", createdDateEnd = "", moduleName = "", description = "";

            List<String> listModuleName = new ArrayList();
            listModuleName.add(LogData.MODULE_WF);
            listModuleName.add(LogData.MODULE_DMS);
            listModuleName.add(LogData.MODULE_LEAVE);
            listModuleName.add(LogData.MODULE_MEETING);
            listModuleName.add(LogData.MODULE_VEHICLE);
            listModuleName.add(LogData.MODULE_SUPPLY);
            listModuleName.add(LogData.MODULE_SLIP);
            listModuleName.add(LogData.MODULE_ADMIN);

            List<String> listModuleNameThai = new ArrayList();
            listModuleNameThai.add("ระบบสารบรรณอิเล็กทรอนิกส์");
            listModuleNameThai.add("ระบบจัดเก็บเอกสาร");
            listModuleNameThai.add("ระบบการลา");
            listModuleNameThai.add("ระบบจองห้องประชุม");
            listModuleNameThai.add("ระบบจองรถยนต์");
            listModuleNameThai.add("ระบบพัสดุ");
            listModuleNameThai.add("ระบบสลิปเงินเดือน");
            listModuleNameThai.add("ผู้ดูแลระบบ");

            for (String key : queryParams.keySet()) {
                if (fieldSearch.contains("," + key + ",")) {
                    for (String value : queryParams.get(key)) {
                        String[] valueArray = value.split(",");
                        int i = 0;
                        for (i = 0; i < valueArray.length; i++) {
                            switch (key) {
                                case "createdBy":
                                    if (valueArray[i] != null && !"".equalsIgnoreCase(valueArray[i])) {
                                        createdBy = Integer.parseInt(valueArray[i]);
                                    }
                                    break;
                                case "createdDateBegin":
                                    createdDateBegin = valueArray[i];
                                    break;
                                case "createdDateEnd":
                                    createdDateEnd = valueArray[i];
                                    break;
                                case "type":
//                                    if (valueArray[i] != null && !"".equalsIgnoreCase(valueArray[i])) {
//                                        String[] typeArr = valueArray[i].split("฿");
//                                        List<Integer> typeInt = new ArrayList<Integer>();
//                                        for (i = 0; i < typeArr.length; i++) {
//                                            typeInt.add(Integer.parseInt(typeArr[i]));
//                                        }
//                                        type = typeInt;
//                                    }
//                                    if (valueArray[i] != null && !"".equalsIgnoreCase(valueArray[i])){
//                                        type = Integer.parseInt(valueArray[i]);
//                                    }
                                    break;
                                case "moduleName":
                                    moduleName = valueArray[i];
                                    break;
                                case "description":
                                    description = valueArray[i];
                                    break;
                                case "subModule":
                                    description = valueArray[i];
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }

            if (!listLogData.isEmpty()) {
                int countAll = logDataService.countSearch(queryParams);
                int count = listLogData.size();
                int next = 0;
                if (count >= listOptionModel.getLimit()) {
                    next = listOptionModel.getOffset() + listOptionModel.getLimit();
                }
                if (next >= countAll) {
                    next = 0;
                }
                ListReturnModel listReturnModel = new ListReturnModel(countAll, count, next);
//                for (String key : queryParams.keySet()) {
//                    if(fieldSearch.contains(","+key+",")){
//                        for (String value : queryParams.get(key)) {
//                            String[] valueArray = value.split(",");
//                            int i = 0;
//                            for(i = 0;i<valueArray.length;i++){

//                if(createdBy == 0 && "".equalsIgnoreCase(createdDateBegin) && "".equalsIgnoreCase(createdDateEnd) && "".equalsIgnoreCase(moduleName) && "".equalsIgnoreCase(description)){
                if (!"".equalsIgnoreCase(createdDateBegin) && !"".equalsIgnoreCase(createdDateEnd)) {
                    if (!"".equalsIgnoreCase(moduleName)) {
                        for (int i = 0; i < listModuleName.size(); i++) {
                            if (moduleName.equalsIgnoreCase(listModuleName.get(i))) {
                                int result = logDataService.countLogByModuleName(moduleName, createdDateBegin, createdDateEnd);
                                if (result != 0) {
                                    LogDataModel_report logDataModel_report = logDataService.tranformToModelReport(listModuleNameThai.get(i), result);
                                    listLogDataModel_report.add(logDataModel_report);
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < listModuleName.size(); i++) {

                            int result = logDataService.countLogByModuleName(listModuleName.get(i), createdDateBegin, createdDateEnd);
                            if (result != 0) {
                                LogDataModel_report logDataModel_report = logDataService.tranformToModelReport(listModuleNameThai.get(i), result);
                                listLogDataModel_report.add(logDataModel_report);
                            }
                        }
                    }

                } else {
                    if (!"".equalsIgnoreCase(moduleName)) {
                        for (int i = 0; i < listModuleName.size(); i++) {
                            if (moduleName.equalsIgnoreCase(listModuleName.get(i))) {
                                int result = logDataService.countLogByModuleNameNoDate(moduleName);
                                if (result != 0) {
                                    LogDataModel_report logDataModel_report = logDataService.tranformToModelReport(listModuleNameThai.get(i), result);
                                    listLogDataModel_report.add(logDataModel_report);
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < listModuleName.size(); i++) {
                            int result = logDataService.countLogByModuleNameNoDate(listModuleName.get(i));
                            if (result != 0) {
                                LogDataModel_report logDataModel_report = logDataService.tranformToModelReport(listModuleNameThai.get(i), result);
                                listLogDataModel_report.add(logDataModel_report);
                            }
                        }
                    }
                }
//                        }
//                    }
//                }

                ArrayList<LogDataModel> listLogDataModel = new ArrayList<>();
                LogDataModel logDataModel = null;
                for (LogData logData : listLogData) {
                    logDataModel = logDataService.tranformToModel(logData);
                    listLogDataModel.add(logDataModel);
                }
                listLogDataModel.trimToSize();
                responseData.put("data", listLogDataModel);
                responseData.put("list", listReturnModel);
                responseData.put("listModule", listLogDataModel_report);
                responseData.put("message", "");
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list LogType for Dropdown.",
            notes = "รายการประเภท log ต่างๆ สำหรับทำ dropdown",
            responseContainer = "List",
            response = LogTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "LogType for Dropdown success.")
        ,
        @ApiResponse(code = 404, message = "LogType for Dropdown not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/logTypes")
    public Response listLogTypes(
            @BeanParam VersionModel versionModel
    ) {
        LOG.debug("listLogTypes...");
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
        responseData.put("message", "LogType for Dropdown not found in the database.");
        responseData.put("errorMessage", "");
        responseData.put("data", "");
        ListReturnModel listReturnModel = new ListReturnModel(0, 0, 0);
        responseData.put("list", listReturnModel);
        try {
            ArrayList<LogTypeModel> listLogTypeModel = new ArrayList<>();
            LogTypeModel logTypeModel = new LogTypeModel();

            logTypeModel.setModule(LogData.MODULE_ADMIN);
            logTypeModel.setModuleName("ทั้งหมด");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_MWP);
            logTypeModel.setModuleName("กล่องข้อมูลเข้า");
            logTypeModel.setSubModule("ข้อมูลเข้า");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_MWP);
            logTypeModel.setModuleName("กล่องข้อมูลออก");
            logTypeModel.setSubModule("ข้อมูลออก");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_MWP);
            logTypeModel.setModuleName("แฟ้มส่วนตัว");
            logTypeModel.setSubModule("ข้อมูลส่วนตัว");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_WF);
            logTypeModel.setModuleName("ระบบสารบรรณ");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_WF);
            logTypeModel.setModuleName("ระบบสารบรรณ");
            logTypeModel.setSubModule("แฟ้มทะเบียน");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_WF);
            logTypeModel.setModuleName("ระบบสารบรรณ");
            logTypeModel.setSubModule("เอกสาร");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_WF);
            logTypeModel.setModuleName("ระบบสารบรรณ");
            logTypeModel.setSubModule("เอกสารแนบ");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_DMS);
            logTypeModel.setModuleName("ระบบจัดเก็บเอกสาร");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_DMS);
            logTypeModel.setModuleName("ระบบจัดเก็บเอกสาร");
            logTypeModel.setSubModule("ตู้");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_DMS);
            logTypeModel.setModuleName("ระบบจัดเก็บเอกสาร");
            logTypeModel.setSubModule("ลิ้นชัก");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_DMS);
            logTypeModel.setModuleName("ระบบจัดเก็บเอกสาร");
            logTypeModel.setSubModule("แฟ้ม");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_DMS);
            logTypeModel.setModuleName("ระบบจัดเก็บเอกสาร");
            logTypeModel.setSubModule("เอกสาร");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_LEAVE);
            logTypeModel.setModuleName("ระบบการลา");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_MEETING);
            logTypeModel.setModuleName("ระบบจองห้องประชุม");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_VEHICLE);
            logTypeModel.setModuleName("ระบบจองรถยนต์");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_HR);
            logTypeModel.setModuleName("ระบบบุคคลากร");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_SUPPLY);
            logTypeModel.setModuleName("ระบบงานพัสดุ");
            listLogTypeModel.add(logTypeModel);

            logTypeModel = new LogTypeModel();
            logTypeModel.setModule(LogData.MODULE_SLIP);
            logTypeModel.setModuleName("ระบบสลิปเงินเดือน");
            listLogTypeModel.add(logTypeModel);

            listLogTypeModel.trimToSize();
            responseData.put("data", listLogTypeModel);
            responseData.put("list", listReturnModel);
            responseData.put("message", "");
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
            value = "Method for count All Log By moduleName .",
            notes = "ขอรายการ log ทั้งหมดด้วย ชื่อโมดูล",
            response = LogDataModel_report.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Count All Log success.")
        ,
        @ApiResponse(code = 404, message = "Log not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/countAllLogByModuleName")
    public Response countAllLogByModuleName(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "moduleName", value = "ชื่อโมดูล", required = true)
            @QueryParam("moduleName") String moduleName,
            @ApiParam(name = "startDate", value = "วันที่เริ่ม", required = true)
            @QueryParam("startDate") String startDate,
            @ApiParam(name = "endDate", value = "วันที่สิ้นสุด", required = true)
            @QueryParam("endDate") String endDate
    ) {
        LOG.debug("countAllLogByModuleName...");
        LOG.debug("moduleName = " + moduleName);
        LOG.debug("startDate = " + startDate);
        LOG.debug("endDate = " + endDate);
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
        responseData.put("message", "Log not found in the database.");
        try {
            LogDataService logDataService = new LogDataService();
            int result = logDataService.countLogByModuleName(moduleName, startDate, endDate);

            LogDataModel_report logDataModel_report = logDataService.tranformToModelReport(moduleName, result);

            responseData.put("data", logDataModel_report);
            responseData.put("message", "");
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
            value = "Method for count All Log Group By moduleName .",
            notes = "ขอรายการ log ทั้งหมดแยกตาม ชื่อโมดูล"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Count All Log success.")
        ,
        @ApiResponse(code = 404, message = "Log not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/countAllLogGroupByModuleName")
    public Response countAllLogGroupByModuleName(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "startDate", value = "วันที่เริ่ม", required = true)
            @QueryParam("startDate") String startDate,
            @ApiParam(name = "endDate", value = "วันที่สิ้นสุด", required = true)
            @QueryParam("endDate") String endDate
    ) {
        LOG.debug("countAllLogGroupByModuleName...");
        LOG.debug("startDate = " + startDate);
        LOG.debug("endDate = " + endDate);
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
        responseData.put("message", "Log not found in the database.");
        try {
            LogDataService logDataService = new LogDataService();
            ArrayList<LogDataModel_report> listLogDataModel_report = new ArrayList();

            List<String> listModuleName = new ArrayList();
            listModuleName.add(LogData.MODULE_WF);
            listModuleName.add(LogData.MODULE_DMS);
            listModuleName.add(LogData.MODULE_LEAVE);
            listModuleName.add(LogData.MODULE_MEETING);
            listModuleName.add(LogData.MODULE_VEHICLE);
            listModuleName.add(LogData.MODULE_SUPPLY);
            listModuleName.add(LogData.MODULE_SLIP);

            List<String> listModuleNameThai = new ArrayList();
            listModuleNameThai.add("ระบบสารบรรณอิเล็กทรอนิกส์");
            listModuleNameThai.add("ระบบจัดเก็บเอกสาร");
            listModuleNameThai.add("ระบบการลา");
            listModuleNameThai.add("ระบบจองห้องประชุม");
            listModuleNameThai.add("ระบบจองรถยนต์");
            listModuleNameThai.add("ระบบพัสดุ");
            listModuleNameThai.add("ระบบสลิปเงินเดือน");

            for (int i = 0; i < listModuleName.size(); i++) {
                int result = logDataService.countLogByModuleName(listModuleName.get(i), startDate, endDate);
                LogDataModel_report logDataModel_report = logDataService.tranformToModelReport(listModuleNameThai.get(i), result);
                listLogDataModel_report.add(logDataModel_report);
            }

            listLogDataModel_report.trimToSize();
            responseData.put("data", listLogDataModel_report);
            responseData.put("message", "");
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
            value = "Method for count Log By Structure Id.",
            notes = "ขอรายการ log ด้วยรหัสหน่วยงาน"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Count Log success.")
        ,
        @ApiResponse(code = 404, message = "Log not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/countLogByStructureId")
    public Response countLogByStructureId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "structureId", value = "รหัสหน่วยงาน", required = true)
            @QueryParam("structureId") int structureId,
            @ApiParam(name = "startDate", value = "วันที่เริ่ม", required = true)
            @QueryParam("startDate") String startDate,
            @ApiParam(name = "endDate", value = "วันที่สิ้นสุด", required = true)
            @QueryParam("endDate") String endDate
    ) {
        LOG.debug("countLogByStructureId...");
        LOG.debug("structureId = " + structureId);
        LOG.debug("startDate = " + startDate);
        LOG.debug("endDate = " + endDate);
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
        responseData.put("message", "Log not found in the database.");
        try {
            LogDataService logDataService = new LogDataService();
            ArrayList<LogDataModel_report> listLogDataModel_report = new ArrayList();

            UserProfileService userProfileService = new UserProfileService();
            StructureService structureService = new StructureService();
            Structure structure = structureService.getById(structureId);
            List<UserProfile> listUserProfile = new ArrayList();

            if (structure.getNodeLevel() == 0) {

            } else if (structure.getNodeLevel() == 1 || (structure.getNodeLevel() == 2 && structure.getStructureType() == 1)) {
                listUserProfile = userProfileService.listByStructureParentKey(structure.getParentKey(), "", "");
            } else {
                listUserProfile = userProfileService.listByStructure(structure, "", "");
            }
            List<Integer> listUserProfileId = new ArrayList();

            for (UserProfile tempUserProfile : listUserProfile) {
                listUserProfileId.add(tempUserProfile.getId());
            }

            List<String> listModuleName = new ArrayList();
            listModuleName.add(LogData.MODULE_WF);
            listModuleName.add(LogData.MODULE_DMS);
            listModuleName.add(LogData.MODULE_LEAVE);
            listModuleName.add(LogData.MODULE_MEETING);
            listModuleName.add(LogData.MODULE_VEHICLE);
            listModuleName.add(LogData.MODULE_SUPPLY);
            listModuleName.add(LogData.MODULE_SLIP);

            List<String> listModuleNameThai = new ArrayList();
            listModuleNameThai.add("ระบบสารบรรณอิเล็กทรอนิกส์");
            listModuleNameThai.add("ระบบจัดเก็บเอกสาร");
            listModuleNameThai.add("ระบบการลา");
            listModuleNameThai.add("ระบบจองห้องประชุม");
            listModuleNameThai.add("ระบบจองรถยนต์");
            listModuleNameThai.add("ระบบพัสดุ");
            listModuleNameThai.add("ระบบสลิปเงินเดือน");

            for (int i = 0; i < listModuleName.size(); i++) {
                int result = logDataService.countLogByModuleName(listModuleName.get(i), listUserProfileId, startDate, endDate);
                LogDataModel_report logDataModel_report = logDataService.tranformToModelReport(listModuleNameThai.get(i), result);
                listLogDataModel_report.add(logDataModel_report);
            }

            listLogDataModel_report.trimToSize();
            responseData.put("data", listLogDataModel_report);
            responseData.put("message", "");
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
            value = "Method for count Login All Structure.",
            notes = "ขอรายการการเข้าใช้งานในระบบของทุกหน่วยงาน"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Count Login success.")
        ,
        @ApiResponse(code = 404, message = "Login not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/countLoginAllStructure")
    public Response countLoginAllStructure(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "startDate", value = "วันที่เริ่ม", required = true)
            @QueryParam("startDate") String startDate,
            @ApiParam(name = "endDate", value = "วันที่สิ้นสุด", required = true)
            @QueryParam("endDate") String endDate
    ) {
        LOG.debug("countLoginAllStructure...");
        LOG.debug("startDate = " + startDate);
        LOG.debug("endDate = " + endDate);
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
        responseData.put("message", "Login not found in the database.");
        try {
            LogDataService logDataService = new LogDataService();
            ArrayList<LogDataModel_report> listLogDataModel_report = new ArrayList();

            UserProfileService userProfileService = new UserProfileService();
            StructureService structureService = new StructureService();
            List<Structure> listStructure = structureService.listStructureByType(1, "structureName", "asc");

            for (Structure structure : listStructure) {
                List<UserProfile> listUserProfile = userProfileService.listByStructureParentKey(structure.getParentKey(), "", "");
                List<Integer> listUserProfileId = new ArrayList();
                for (UserProfile tempUserProfile : listUserProfile) {
                    listUserProfileId.add(tempUserProfile.getId());
                }

                int result = logDataService.countLogByModuleName(LogData.MODULE_ADMIN, listUserProfileId, 4, startDate, endDate);
                LogDataModel_report logDataModel_report = logDataService.tranformToModelReport(structure.getStructureName(), result);
                listLogDataModel_report.add(logDataModel_report);
            }

            listLogDataModel_report.trimToSize();
            responseData.put("data", listLogDataModel_report);
            responseData.put("message", "");
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
            value = "Method for count All Log Group By userId .",
            notes = "ขอรายการ log ทั้งหมดแยกตาม รหัสผู้ใช้งานระบบ"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Count All Log success.")
        ,
        @ApiResponse(code = 404, message = "Log not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/countLogByUserId")
    public Response countLogByUserId(
            @BeanParam VersionModel versionModel,
            @BeanParam ListOptionModel listOptionModel
    //            @BeanParam VersionModel versionModel,
    //            @ApiParam(name = "startDate", value = "วันที่เริ่ม", required = true)
    //            @QueryParam("startDate") String startDate,
    //            @ApiParam(name = "endDate", value = "วันที่สิ้นสุด", required = true)
    //            @QueryParam("endDate") String endDate
    ) {
        LOG.debug("countLogByUserId...");
//        LOG.debug("startDate = " + startDate);
//        LOG.debug("endDate = " + endDate);
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
        responseData.put("message", "Log not found in the database.");
        try {
            int userCreated = 0;
            LogDataService logDataService = new LogDataService();
            ArrayList<LogDataModel_report> listLogDataModel_report = new ArrayList();
            List<LogData> listLogData = new LogDataService().listAll(listOptionModel.getSort(), listOptionModel.getDir());
            for (LogData logData : listLogData) {
                int result = logDataService.countLogByUserId(logData.getLinkId());
//                User user  = new UserService().getById(logData.getLinkId());
                User user = new UserService().getById(logData.getLinkId());

                if (logData.getCreatedBy() <= 0) {
                    user = new UserService().getById(1);
                }
                LogDataModel_report logDataModel_report = logDataService.tranformToModelReport(user.getUserName(), result);
                listLogDataModel_report.add(logDataModel_report);
            }

            listLogDataModel_report.trimToSize();
            responseData.put("data", listLogDataModel_report);
            responseData.put("message", "");
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for search log.",
            notes = "ค้นหาประวัติการใช้งาน"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Search Log success.")
        ,
        @ApiResponse(code = 404, message = "Log not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/SearchLog")
    public Response SearchLog(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "moduleName", value = "ระบบ", required = false)
            @DefaultValue("") @QueryParam("moduleName") String moduleName,
            @ApiParam(name = "startDate", value = "วันที่เริ่ม", required = false)
            @DefaultValue("") @QueryParam("startDate") String startDate,
            @ApiParam(name = "endDate", value = "วันที่สิ้นสุด", required = false)
            @DefaultValue("") @QueryParam("endDate") String endDate,
            @ApiParam(name = "listType", value = "รายการประเภทของ log 1,2,3,...", required = false)
            @DefaultValue("") @QueryParam("listType") String listType,
            @ApiParam(name = "listUserProfileId", value = "รายการรหัสผู้ใช้งานในระบบ 1,2,3,...", required = false)
            @DefaultValue("") @QueryParam("listUserProfileId") String listUserProfileId,
            @ApiParam(name = "offset", value = "ตำแหน่งเริ่มต้น", required = true)
            @DefaultValue("0") @QueryParam("offset") int offset,
            @ApiParam(name = "limit", value = "จำนวนข้อมูลที่ต้องการ", required = true)
            @DefaultValue("50") @QueryParam("limit") int limit,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("desc") @QueryParam("dir") String dir
    ) {
        LOG.debug("SearchLog...");
        LOG.debug("moduleName = " + moduleName);
        LOG.debug("startDate = " + startDate);
        LOG.debug("endDate = " + endDate);
        LOG.debug("listType = " + listType);
        LOG.debug("listUserProfileId = " + listUserProfileId);
        LOG.debug("offset = " + offset);
        LOG.debug("limit = " + limit);
        LOG.debug("sort = " + sort);
        LOG.debug("dir = " + dir);
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
        responseData.put("message", "Log not found in the database.");
        try {
            LogDataService logDataService = new LogDataService();
            List<LogData> listLogData = new ArrayList();
            List<LogDataModel_report> listLogDataModel_report = new ArrayList<>();
            //listUserProfile
            List<Integer> listUserProfile = new ArrayList();
            if (!listUserProfileId.equals("")) {
                String[] tempList = listUserProfileId.split(",");
                for (int i = 0; i < tempList.length; i++) {
                    listUserProfile.add(Integer.parseInt(tempList[i]));
                }
            }

            //type
            List<Integer> listType1 = new ArrayList();
            if (!listType.equals("")) {
                String[] tempList = listType.split(",");
                for (int i = 0; i < tempList.length; i++) {
                    listType1.add(Integer.parseInt(tempList[i]));
                }
            }

            List<String> listModuleName = new ArrayList();
            listModuleName.add(LogData.MODULE_WF);
            listModuleName.add(LogData.MODULE_DMS);
            listModuleName.add(LogData.MODULE_LEAVE);
            listModuleName.add(LogData.MODULE_MEETING);
            listModuleName.add(LogData.MODULE_VEHICLE);
            listModuleName.add(LogData.MODULE_SUPPLY);
            listModuleName.add(LogData.MODULE_SLIP);
            listModuleName.add(LogData.MODULE_ADMIN);

            List<String> listModuleNameThai = new ArrayList();
            listModuleNameThai.add("ระบบสารบรรณอิเล็กทรอนิกส์");
            listModuleNameThai.add("ระบบจัดเก็บเอกสาร");
            listModuleNameThai.add("ระบบการลา");
            listModuleNameThai.add("ระบบจองห้องประชุม");
            listModuleNameThai.add("ระบบจองรถยนต์");
            listModuleNameThai.add("ระบบพัสดุ");
            listModuleNameThai.add("ระบบสลิปเงินเดือน");
            listModuleNameThai.add("ผู้ดูแลระบบ");

            listLogData = logDataService.listLogByModuleName(moduleName, listUserProfile, listType1, startDate, endDate, offset, limit, sort, dir);
            ArrayList<LogDataModel> listLogDataModel = new ArrayList();

            int countAll = logDataService.countListLogByModuleName(moduleName, listUserProfile, listType1, startDate, endDate);
            int count = listLogData.size();
            int next = 0;
            if (count >= limit) {
                next = offset + limit;
            }
            if (next >= countAll) {
                next = 0;
            }
            ListReturnModel listReturnModel = new ListReturnModel(countAll, count, next);
            if (!"".equalsIgnoreCase(moduleName)) {
                for (int i = 0; i < listModuleName.size(); i++) {
                    if (moduleName.equalsIgnoreCase(listModuleName.get(i))) {
                        int result = logDataService.countLogByModuleName(moduleName, startDate, endDate);
                        if (result != 0) {
                            LogDataModel_report logDataModel_report = logDataService.tranformToModelReport(listModuleNameThai.get(i), result);
                            listLogDataModel_report.add(logDataModel_report);
                        }
                    }
                }
            } else {
                for (int i = 0; i < listModuleName.size(); i++) {
                    int result = logDataService.countLogByModuleName(listModuleName.get(i), startDate, endDate);
                    if (result != 0) {
                        LogDataModel_report logDataModel_report = logDataService.tranformToModelReport(listModuleNameThai.get(i), result);
                        listLogDataModel_report.add(logDataModel_report);
                    }
                }
            }
            if (!listLogData.isEmpty()) {
                for (LogData logData : listLogData) {
                    listLogDataModel.add(logDataService.tranformToModel(logData));
                }
            }

            listLogDataModel.trimToSize();
            responseData.put("data", listLogDataModel);
            responseData.put("list", listReturnModel);
            responseData.put("listModule", listLogDataModel_report);
            responseData.put("message", "");
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
            value = "Method for list LogType for Dropdown.",
            notes = "รายการประเภท log ต่างๆ สำหรับทำ dropdown",
            responseContainer = "List",
            response = LogTypeModel_type.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "LogType for Dropdown success.")
        ,
        @ApiResponse(code = 404, message = "LogType for Dropdown not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listDataTypesLog")
    public Response listDataTypesLog(
            @BeanParam VersionModel versionModel
    ) {
        LOG.debug("listDataTypesLog...");
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
        responseData.put("message", "LogType for Dropdown not found in the database.");
        responseData.put("errorMessage", "");
        responseData.put("data", "");
        ListReturnModel listReturnModel = new ListReturnModel(0, 0, 0);
        responseData.put("list", listReturnModel);
        try {
            ArrayList<LogTypeModel_type> listLogTypeModel = new ArrayList<>();
            LogTypeModel_type logTypeModel_type = new LogTypeModel_type();

            logTypeModel_type.setType(9);
            logTypeModel_type.setTypeName("เปิดใช้");
            listLogTypeModel.add(logTypeModel_type);

            logTypeModel_type = new LogTypeModel_type();
            logTypeModel_type.setType(1);
            logTypeModel_type.setTypeName("สร้างเอกสาร");
            listLogTypeModel.add(logTypeModel_type);

            logTypeModel_type = new LogTypeModel_type();
            logTypeModel_type.setType(2);
            logTypeModel_type.setTypeName("แก้ไขเอกสาร");
            listLogTypeModel.add(logTypeModel_type);

            logTypeModel_type = new LogTypeModel_type();
            logTypeModel_type.setType(3);
            logTypeModel_type.setTypeName("ลบเอกสาร");
            listLogTypeModel.add(logTypeModel_type);

            listLogTypeModel.trimToSize();
            responseData.put("data", listLogTypeModel);
            responseData.put("list", listReturnModel);
            responseData.put("message", "");
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
            value = "Method for add report to temp table",
            notes = "Method for add report to temp table",
            response = TempTableModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "TempTable created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/reportType/{jobType}")
    public Response addReptoTemp(
            List<LogDataModel> listLogDataModel,
            @ApiParam(name = "jobType", value = "รหัสประเภทงาน", required = true)
            @PathParam("jobType") String jobType
    ) {
        LOG.debug("create...");
        LOG.debug("jobType..." + jobType);
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
            TempTableService tempTableService = new TempTableService();
            List<TempTable> listTempTable = tempTableService.listByComputerNameAndJobType(Integer.parseInt(httpHeaders.getHeaderString("userID")), httpHeaders.getHeaderString("clientIp"), jobType, "", "");
//            List<TempTable> listTempTable = tempTableService.listByComputerNameAndJobType(1, "127.0.0.1", jobType, "", "");

            if (!listTempTable.isEmpty()) {
//            if (listTempTable.size() != 0) {
                for (TempTable tempTable : listTempTable) {
                    tempTableService.delete(tempTable);
                }
            }

            listLogDataModel.forEach(model -> {
                TempTable tempTable = new TempTable();

                tempTable.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                tempTable.setComputerName(httpHeaders.getHeaderString("clientIp"));
//                tempTable.setCreatedBy(1);
//                tempTable.setComputerName("127.0.0.1");
                tempTable.setJobType(jobType);
                tempTable.setInt01(model.getId());
                tempTable.setStr01(model.getModuleName());
                tempTable.setStr02(model.getUserProfileName());
                tempTable.setStr03(model.getCreatedDate());
                tempTable.setStr04(model.getIpAddress());
                tempTable.setStr05(model.getDescription());
                tempTable.setText01(model.getModuleIcon());

                if (tempTable != null) {
                    tempTable = tempTableService.create(tempTable);
                    tempTable.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    tempTable.setOrderNo(tempTable.getId());
                    tempTable = tempTableService.update(tempTable);
                }
            });

            responseData.put("data", null);
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "TempTable created successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
