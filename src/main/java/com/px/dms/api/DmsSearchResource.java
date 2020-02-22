/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.dms.entity.DmsDocument;
import com.px.dms.model.DmsSearchInputModel;
import com.px.dms.model.DmsSearchModel;
import com.px.dms.model.DmsSearchResultModel;
import com.px.dms.service.DmsDocumentService;
import com.px.dms.service.DmsFolderService;
import com.px.dms.service.DmsSearchService;
import com.px.share.entity.FileAttach;
import com.px.share.model.ListReturnLongModel;
import com.px.share.model.VersionModel;
import com.px.share.service.FileAttachService;
import com.px.share.service.ParamService;
import com.px.share.util.Common;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author top
 */
@Api(value = "Search Dms ค้นหาเอกสารระบบจัดเก็บ")
@Path("v1/searchDms")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DmsSearchResource {

    private static final Logger LOG = Logger.getLogger(DmsSearchResource.class.getName());

    @ApiOperation(
            value = "Create DmsSearch",
            notes = "สร้างข้อมูลสำหรับค้นหา",
            response = DmsSearchModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "DmsSearch created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            @BeanParam VersionModel versionModel,
            DmsSearchModel dmsSearchModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
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
            DmsSearchService dmsSearchService = new DmsSearchService();
            DmsFolderService dmsFolderService = new DmsFolderService();
            String folderP = dmsFolderService.getById(dmsSearchModel.getDocumentFolderId()).getDmsFolderParentKey();
//            System.out.println("folderP -- " + folderP);
            
            String[] parts = folderP.split("฿");
            List<String> temp = new ArrayList<String>();
            for (int i = 1; i < parts.length; i++) {

                temp.add(parts[i]);

            }
            dmsSearchModel.setParentKey(temp);
//            dmsSearchModel.setFullPathName(folderP);
            DmsSearchModel result = dmsSearchService.addData(dmsSearchModel);

            if (result != null) {
                status = Response.Status.CREATED;
                responseData.put("data", result);
                responseData.put("message", "DmsSearch created successfully.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "setUp ElasticSearch",
            notes = "ตั้งค่า",
            response = DmsSearchModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "setUp  successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/setup")
    public Response setUp(
            @BeanParam VersionModel versionModel
    ) {
        LOG.debug("setup...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
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
            DmsSearchService dmsSearchService = new DmsSearchService();
            boolean result = false;
            result = dmsSearchService.setup();

            status = Response.Status.CREATED;
            responseData.put("data", result);
            responseData.put("message", "DmsSearch created successfully.");

            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get dmsSearchId",
            notes = "ขอข้อมูลเอกสารด้วย dmsSearchId ",
            response = DmsSearchModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Search by id success.")
        ,
        @ApiResponse(code = 404, message = "Search by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "dmsSearchId", required = true)
            @PathParam("id") String id
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
        responseData.put("message", "Search by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsSearchService dmsSearchService = new DmsSearchService();
            DmsSearchModel result = dmsSearchService.getData(id);
            if (result != null) {
                status = Response.Status.OK;
                responseData.put("data", result);
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
            value = "Method for update DmsSearch.",
            notes = "แก้ไขข้อมูลเอกสาร",
            response = DmsSearchModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DmsSearch updeted by id success.")
        ,@ApiResponse(code = 404, message = "DmsSearch by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @ApiParam(name = "id", value = "search Id", required = true)
            @PathParam("id") String id,
            @BeanParam VersionModel versionModel,
            DmsSearchModel dmsSearchModel
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
        responseData.put("message", "DmsSearch by id not found in the database.");
        responseData.put("errorMessage", "");

        DmsSearchModel result;
        try {
            DmsSearchService dmsSearchService = new DmsSearchService();

            result = dmsSearchService.updateData(id, dmsSearchModel);
            if (result != null) {
                status = Response.Status.OK;
                responseData.put("data", result);
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
            value = "Method for delete DmsSerach .",
            notes = "ลบข้อมูลเอกสาร",
            response = DmsSearchModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DmsSerach deleted by id success.")
        ,@ApiResponse(code = 404, message = "DmsSerach by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "DmsSerachId", required = true)
            @PathParam("id") String id
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
        responseData.put("message", "DmsSerach by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsSearchService dmsSearchService = new DmsSearchService();
            DmsSearchModel result = dmsSearchService.deleteData(id);
            if (result != null) {
                status = Response.Status.OK;
                responseData.put("data", result);
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
            value = "Method for search from DmsSerach",
            notes = "ค้นหาข้อมูล",
            response = DmsSearchResultModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "search success.")
        ,
        @ApiResponse(code = 404, message = "search not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/search")
    public Response search(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            DmsSearchInputModel dmsSearchInputModel
    ) {
        LOG.debug("search...");
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
        responseData.put("data", new ArrayList());
        responseData.put("message", "search not found in the database.");
        responseData.put("errorMessage", "");
        try {
            String[] fetchSourceList = null;

            if (dmsSearchInputModel.getFetchSource() != null) {
                fetchSourceList = new String[dmsSearchInputModel.getFetchSource().size()];
                int i = 0;
                for (String fetchSource : dmsSearchInputModel.getFetchSource()) {
                    fetchSourceList[i] = fetchSource;
                    i++;
                }
            }

            DmsSearchService searchCCService = new DmsSearchService();
//            System.out.println("1111111111ababab");
            DmsSearchResultModel result = searchCCService.search(dmsSearchInputModel.getForm(), dmsSearchInputModel.getOrder(), fetchSourceList, dmsSearchInputModel.getFrom(), dmsSearchInputModel.getSize(), dmsSearchInputModel.getDefaultSymbolForSpace(), dmsSearchInputModel.getSymbolAnd(), dmsSearchInputModel.getSymbolOr(), dmsSearchInputModel.getSymbolNot(), dmsSearchInputModel.getSymbolWith(), dmsSearchInputModel.getPreHighlightTag(), dmsSearchInputModel.getPostHighlightTag(), userID);
//            System.out.println("2222222222ababab");
            if (result != null) {
                long countAll = result.getSearchAll();
                long count = result.getSearchResult().size();
                long next = 0;
                if (count >= dmsSearchInputModel.getSize()) {
                    next = dmsSearchInputModel.getFrom() + dmsSearchInputModel.getSize();
                }
                if (next >= countAll) {
                    next = 0;
                }
                LocalDateTime nowDate = LocalDateTime.now();
                if (!result.getSearchResult().isEmpty()) {

                    for (int i = 0; i < result.getSearchResult().size(); i++) {
//                for ( DmsSearchModel dmsDocument : result.getSearchResult()) {
                        DmsSearchModel dmsDocument = result.getSearchResult().get(i);
                        String isExp = "N";
                        if (dmsDocument.getCreatedDate() != null) {
                            String str = dmsDocument.getCreatedDate();
                            str = str.replace("Z", "");
                            LocalDateTime dateTime = LocalDateTime.parse(str);
                            String temp = Common.localDateTimeToString(dateTime);
                            result.getSearchResult().get(i).setCreatedDate(temp);
                        }

                        ///////////
                        if (dmsDocument.getUpdatedDate() != null) {
                            String str = dmsDocument.getUpdatedDate();
                            str = str.replace("Z", "");
                            LocalDateTime dateTime = LocalDateTime.parse(str);
                            String temp = Common.localDateTimeToString(dateTime);
                            result.getSearchResult().get(i).setUpdatedDate(temp);
                        }
                        /////
                        if (dmsDocument.getDocumentExpireDate() != null) {
                            String str = dmsDocument.getDocumentExpireDate();
//                            System.out.println("str exp = " + str);
                            str = str.replace("Z", "");
                            LocalDateTime dateTime = LocalDateTime.parse(str);
//                            System.out.println("dateTime0 = " + dateTime);
                            String temp = Common.localDateTimeToString(dateTime);
                            result.getSearchResult().get(i).setDocumentExpireDate(temp);
//                            System.out.println("name  = "+result.getSearchResult().get(i).getDocumentName());

//                            System.out.println("is exp 9 = " + nowDate.isBefore(dateTime));
                            if (nowDate.isBefore(dateTime)) {
//                                System.out.println("aaaabc ");
                                result.getSearchResult().get(i).setIsExp("N");
                            } else {
                                result.getSearchResult().get(i).setIsExp("Y");
                            }
                        }
                        ////
                        if (dmsDocument.getDocumentDate01() != null) {
                            String str = dmsDocument.getDocumentDate01();
                            str = str.replace("Z", "");
                            LocalDateTime dateTime = LocalDateTime.parse(str);
                            String temp = Common.localDateTimeToString(dateTime);
                            result.getSearchResult().get(i).setDocumentDate01(temp);
                        }
                        ////
                        if (dmsDocument.getDocumentDate02() != null) {
                            String str = dmsDocument.getDocumentDate02();
                            str = str.replace("Z", "");
                            LocalDateTime dateTime = LocalDateTime.parse(str);
                            String temp = Common.localDateTimeToString(dateTime);
                            result.getSearchResult().get(i).setDocumentDate02(temp);
                        }
                        ///
                        if (dmsDocument.getDocumentDate03() != null) {
                            String str = dmsDocument.getDocumentDate03();
                            str = str.replace("Z", "");
                            LocalDateTime dateTime = LocalDateTime.parse(str);
                            String temp = Common.localDateTimeToString(dateTime);
                            result.getSearchResult().get(i).setDocumentDate03(temp);
                        }
                        ///
                        if (dmsDocument.getDocumentDate04() != null) {
                            String str = dmsDocument.getDocumentDate04();
                            str = str.replace("Z", "");
                            LocalDateTime dateTime = LocalDateTime.parse(str);
                            String temp = Common.localDateTimeToString(dateTime);
                            result.getSearchResult().get(i).setDocumentDate04(temp);
                        }

                    }

                }

//                System.out.println("111111111111111");
                ListReturnLongModel listReturnModel = new ListReturnLongModel(countAll, count, next);
//                System.out.println("22222222222222");

//                status = Response.Status.OK;
                responseData.put("data", result);
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
        //return result;
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update DmsSearch.",
            notes = "เพิ่มข้อมูลค้นหาด้วย folderId ",
            response = DmsSearchModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DmsSearch updeted by id success.")
        ,@ApiResponse(code = 404, message = "DmsSearch by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/document/{id}")
    public Response updateWithDocumentId(
            @ApiParam(name = "id", value = "", required = true)
            @PathParam("id") int id,
            @BeanParam VersionModel versionModel
    //            DmsSearchModel dmsSearchModel
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
        responseData.put("message", "DmsSearch by id not found in the database.");
        responseData.put("errorMessage", "");

        DmsSearchModel result;
        try {
            DmsSearchService dmsSearchService = new DmsSearchService();
            ParamService paramService = new ParamService();
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            DmsDocument document = dmsDocumentService.getById(id);
            String searchId = document.getDmsSearchId();
            FileAttachService fileAttachService = new FileAttachService();
            List<FileAttach> listFileAttach = fileAttachService.listAllByLinkTypeLinkId("dms", id, "createdDate", "asc");
            List<String> attachName = new ArrayList<String>();
            List<String> fulltext = new ArrayList<String>();
            String url = "";
            String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
            System.out.println(" listFileAttach = " + listFileAttach.size());

            for (FileAttach fileAttach : listFileAttach) {
                url = pathDocumentHttp + fileAttach.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                System.out.println("url = " + url);
                attachName.add(fileAttach.getFileAttachName());
                System.out.println("getFileAttachName = " + fileAttach.getFileAttachName());
                if (fileAttach.getFileAttachType().equalsIgnoreCase(".TXT")) {
//                    url = pathDocumentHttp + fileAttach.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();

                    File file = new File(url);
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String sCurrentLine;

                    while ((sCurrentLine = br.readLine()) != null) {
                        fulltext.add(sCurrentLine);
                    }
                }
                if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOCX")) {
                    InputStream in = new FileInputStream(url);
                    XWPFDocument doc = new XWPFDocument(in);
                    XWPFWordExtractor ex = new XWPFWordExtractor(doc);
                    String text = ex.getText();
                    fulltext.add(text);
//                    System.out.println("text = " + text);
                }
                if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOC")) {
                    File file = new File(url);
                    NPOIFSFileSystem fs = new NPOIFSFileSystem(file);
                    WordExtractor extractor = new WordExtractor(fs.getRoot());

                    for (String rawText : extractor.getParagraphText()) {
                        String text = extractor.stripFields(rawText);
                        fulltext.add(text);
//                        System.out.println(text);
                    }

                }
                if (fileAttach.getFileAttachType().equalsIgnoreCase(".XLS")) {
                    File file = new File(url);
                    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
                    HSSFWorkbook wb = new HSSFWorkbook(fs);
                    HSSFSheet sheet = wb.getSheetAt(0);
                    HSSFRow row;
                    HSSFCell cell;
                    int rows; // No of rows
                    rows = sheet.getPhysicalNumberOfRows();

                    int cols = 0; // No of columns
                    int tmp = 0;

                    for (int i = 0; i < 10 || i < rows; i++) {
                        row = sheet.getRow(i);
                        if (row != null) {
                            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                            if (tmp > cols) {
                                cols = tmp;
//                                System.out.println("cols ="+cols);
                            }
                        }
                    }
                    for (int r = 0; r < rows; r++) {
                        row = sheet.getRow(r);
                        if (row != null) {
                            for (int c = 0; c < cols; c++) {
                                cell = row.getCell((short) c);
                                if (cell != null) {
                                    // Your code here
//                                    System.out.println("cell = "+cell);
                                    fulltext.add(cell.toString());
                                }
                            }
                        }
                    }

                }
                if (fileAttach.getFileAttachType().equalsIgnoreCase(".XLSX")) {
                    File file = new File(url);
//                    FileInputStream fis = new FileInputStream(file);

                    InputStream ExcelFileToRead = new FileInputStream(url);
                    XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

                    XSSFWorkbook test = new XSSFWorkbook();

                    XSSFSheet sheet = wb.getSheetAt(0);
                    XSSFRow row;
                    XSSFCell cell;

                    Iterator rows = sheet.rowIterator();

                    while (rows.hasNext()) {
                        row = (XSSFRow) rows.next();
                        Iterator cells = row.cellIterator();
                        while (cells.hasNext()) {
                            cell = (XSSFCell) cells.next();
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
//                                System.out.print(cell.getStringCellValue() + "--");
                                fulltext.add(cell.getStringCellValue());
                            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                                System.out.print(cell.getNumericCellValue() + "--");
//                                    fulltext.add( cell.getNumericCellValue());
                            }

                        }

//                        System.out.println();
                    }

                }
            }

            DmsSearchModel temp = dmsSearchService.changDocumntToSearch(document);
            temp.setFileAttachName(attachName);
            temp.setFullText(fulltext);
            result = dmsSearchService.updateData(searchId, temp);
            if (result != null) {
                status = Response.Status.OK;
                responseData.put("data", result);
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
