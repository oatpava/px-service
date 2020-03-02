/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.service.UserProfileService;
import com.px.dms.entity.DmsDocument;
import com.px.dms.entity.DmsFolder;
import com.px.dms.model.DmsDocumentModel;
import com.px.dms.model.DmsSearchModel;
import com.px.dms.model.FieldSearchModel;
import com.px.dms.model.ReportInputModel;
import com.px.dms.model.sendEmailModel;
import com.px.dms.service.DmsDocumentService;
import com.px.dms.service.DmsFolderService;
import com.px.dms.service.DmsSearchService;
import com.px.dms.service.WfDocumentTypeService;
import com.px.share.entity.FileAttach;
import com.px.share.entity.Param;
import com.px.share.model.ListOptionModel;
import com.px.share.model.ListReturnModel;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
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
import java.util.ArrayList;
import java.util.HashMap;
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
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedHashSet;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
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
 * @author TOP
 */
@Api(value = "DmsDocument เอกสาร")
@Path("v1/dmsDocuments")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DmsDocumentResource {

    private static final Logger LOG = Logger.getLogger(DmsDocumentResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create Document",
            notes = "สร้างข้อมูลเอกสาร",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Document created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            @BeanParam VersionModel versionModel,
            DmsDocumentModel dmsDocumentModel
    ) {
        LOG.debug("create DmsDocument...");

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
        responseData.put("data", null);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            DmsDocument document = new DmsDocument();
            document.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
//            document.setUpdatedDate(LocalDateTime.now());
//            document.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
//            document.setRemovedBy(1);
            document.setDocumentTypeId(dmsDocumentModel.getDocumentTypeId());
            document.setDmsDocumentName(dmsDocumentModel.getDocumentName());
            document.setDmsFolderId(dmsDocumentModel.getDocumentFolderId());
            document.setDmsDocumentPublicStatus(dmsDocumentModel.getDocumentPublicStatus());
            if (dmsDocumentModel.getExpType() != "" && dmsDocumentModel.getExpType() != null) {
                LocalDateTime nowDate = LocalDateTime.now();
                if (dmsDocumentModel.getExpNumber() > 0) {
                    if (dmsDocumentModel.getExpType().equalsIgnoreCase("Y")) {
                        nowDate.plusYears(dmsDocumentModel.getExpNumber());
                        document.setDmsDocumentExpireDate(nowDate);
                    }
                    if (dmsDocumentModel.getExpType().equalsIgnoreCase("M")) {
                        nowDate.plusMonths(dmsDocumentModel.getExpNumber());
                        document.setDmsDocumentExpireDate(nowDate);
                    }
                    if (dmsDocumentModel.getExpType().equalsIgnoreCase("D")) {
                        nowDate.plusDays(dmsDocumentModel.getExpNumber());
                        document.setDmsDocumentExpireDate(nowDate);
                    }

                }
            }

            if (dmsDocumentModel.getDocumentPublicDate() != null) {
                document.setDmsDocumentPublicDate(dateThaiToLocalDateTime(dmsDocumentModel.getDocumentPublicDate()));
            }

            if (dmsDocumentModel.getDocumentExpireDate() != null) {
                document.setDmsDocumentExpireDate(dateThaiToLocalDateTime(dmsDocumentModel.getDocumentExpireDate()));
            }

            if (dmsDocumentModel.getDocumentDate01() != null) {
                document.setDmsDocumentDatetime01(dateThaiToLocalDateTime(dmsDocumentModel.getDocumentDate01()));
            }
            if (dmsDocumentModel.getDocumentDate02() != null) {
                document.setDmsDocumentDatetime02(dateThaiToLocalDateTime(dmsDocumentModel.getDocumentDate02()));
            }
            if (dmsDocumentModel.getDocumentDate03() != null) {
                document.setDmsDocumentDatetime03(dateThaiToLocalDateTime(dmsDocumentModel.getDocumentDate03()));
            }
            if (dmsDocumentModel.getDocumentDate04() != null) {
                document.setDmsDocumentDatetime04(dateThaiToLocalDateTime(dmsDocumentModel.getDocumentDate04()));
            }

            document.setDmsDocumentFloat01(dmsDocumentModel.getDocumentFloat01());
            document.setDmsDocumentFloat02(dmsDocumentModel.getDocumentFloat02());

            document.setDmsDocumentVarchar01(dmsDocumentModel.getDocumentVarchar01());
            document.setDmsDocumentVarchar02(dmsDocumentModel.getDocumentVarchar02());
            document.setDmsDocumentVarchar03(dmsDocumentModel.getDocumentVarchar03());
            document.setDmsDocumentVarchar04(dmsDocumentModel.getDocumentVarchar04());
            document.setDmsDocumentVarchar05(dmsDocumentModel.getDocumentVarchar05());
            document.setDmsDocumentVarchar06(dmsDocumentModel.getDocumentVarchar06());
            document.setDmsDocumentVarchar07(dmsDocumentModel.getDocumentVarchar07());
            document.setDmsDocumentVarchar08(dmsDocumentModel.getDocumentVarchar08());
            document.setDmsDocumentVarchar09(dmsDocumentModel.getDocumentVarchar09());
            document.setDmsDocumentVarchar10(dmsDocumentModel.getDocumentVarchar10());

            document.setDmsDocumentText01(dmsDocumentModel.getDocumentText01());
            document.setDmsDocumentText02(dmsDocumentModel.getDocumentText02());
            document.setDmsDocumentText03(dmsDocumentModel.getDocumentText03());
            document.setDmsDocumentText04(dmsDocumentModel.getDocumentText04());
            document.setDmsDocumentText05(dmsDocumentModel.getDocumentText05());

            document.setDmsDocumentInt01(dmsDocumentModel.getDocumentInt01());
            document.setDmsDocumentInt02(dmsDocumentModel.getDocumentInt02());
            document.setDmsDocumentInt03(dmsDocumentModel.getDocumentInt03());
            document.setDmsDocumentInt04(dmsDocumentModel.getDocumentInt04());
            document.setDmsDocumentIntComma(dmsDocumentModel.getDmsDocumentIntComma());

//            document.setDmsDocumentIntComma(dmsDocumentPostModel.getDmsDocumentIntComma());
            document.setDmsDocumentSec(dmsDocumentModel.getDmsDocumentSec());

            DmsDocumentService dmsDocumentService = new DmsDocumentService();

            document = dmsDocumentService.create(document);

            dmsDocumentService.saveLogForCreate(document, httpHeaders.getHeaderString("clientIp"));

//            DmsSearchService dmsSearchService = new DmsSearchService();
//            DmsSearchModel temp = dmsSearchService.changDocumntToSearch(document);
//            temp.setType("DOC");
//            DmsSearchModel result = dmsSearchService.addData(temp);
//            document.setDmsSearchId(result.getDmsSearchId());
//            document = dmsDocumentService.update(document);
//            LOG.debug(document);
//            LOG.debug(dmsDocumentService.tranformToModel(document));
            responseData.put("data", dmsDocumentService.tranformToModel(document));
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "Document created successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update dmsDocument.",
            notes = "แก้ไขข้อมูลเอกสาร",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Document updeted by id success."),
        @ApiResponse(code = 404, message = "Document by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสเอกสาร", required = true)
            @PathParam("id") int id,
            DmsDocumentModel dmsDocumentPostModel
    ) {
        LOG.info("update...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(dmsDocumentPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Document by id not found in the database.");
        responseData.put("errorMessage", "");
        try {

            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            DmsDocument document = dmsDocumentService.getById(id);
            if (document != null) {
                document.setDmsDocumentEdit(Integer.parseInt(httpHeaders.getHeaderString("userID")));

                document.setDmsDocumentEditDate(LocalDateTime.now());

                document.setDocumentTypeId(dmsDocumentPostModel.getDocumentTypeId());
                document.setDmsDocumentName(dmsDocumentPostModel.getDocumentName());

                if (dmsDocumentPostModel.getDocumentPublicDate() != null) {
                    document.setDmsDocumentPublicDate(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentPublicDate()));
                }
                document.setDmsDocumentPublicStatus(dmsDocumentPostModel.getDocumentPublicStatus());

                document.setDmsFolderId(dmsDocumentPostModel.getDocumentFolderId());

                if (dmsDocumentPostModel.getDocumentExpireDate() != null) {
                    document.setDmsDocumentExpireDate(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentExpireDate()));
                }

                if (dmsDocumentPostModel.getDocumentDate01() != null) {
                    document.setDmsDocumentDatetime01(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentDate01()));
                }
                if (dmsDocumentPostModel.getDocumentDate02() != null) {
                    document.setDmsDocumentDatetime02(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentDate02()));
                }
                if (dmsDocumentPostModel.getDocumentDate03() != null) {
                    document.setDmsDocumentDatetime03(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentDate03()));
                }
                if (dmsDocumentPostModel.getDocumentDate04() != null) {
                    document.setDmsDocumentDatetime04(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentDate04()));
                }

                document.setDmsDocumentFloat01(dmsDocumentPostModel.getDocumentFloat01());
                document.setDmsDocumentFloat02(dmsDocumentPostModel.getDocumentFloat02());

                document.setDmsDocumentVarchar01(dmsDocumentPostModel.getDocumentVarchar01());
                document.setDmsDocumentVarchar02(dmsDocumentPostModel.getDocumentVarchar02());
                document.setDmsDocumentVarchar03(dmsDocumentPostModel.getDocumentVarchar03());
                document.setDmsDocumentVarchar04(dmsDocumentPostModel.getDocumentVarchar04());
                document.setDmsDocumentVarchar05(dmsDocumentPostModel.getDocumentVarchar05());
                document.setDmsDocumentVarchar06(dmsDocumentPostModel.getDocumentVarchar06());
                document.setDmsDocumentVarchar07(dmsDocumentPostModel.getDocumentVarchar07());
                document.setDmsDocumentVarchar08(dmsDocumentPostModel.getDocumentVarchar08());
                document.setDmsDocumentVarchar09(dmsDocumentPostModel.getDocumentVarchar09());
                document.setDmsDocumentVarchar10(dmsDocumentPostModel.getDocumentVarchar10());

                document.setDmsDocumentText01(dmsDocumentPostModel.getDocumentText01());
                document.setDmsDocumentText02(dmsDocumentPostModel.getDocumentText02());
                document.setDmsDocumentText03(dmsDocumentPostModel.getDocumentText03());
                document.setDmsDocumentText04(dmsDocumentPostModel.getDocumentText04());
                document.setDmsDocumentText05(dmsDocumentPostModel.getDocumentText05());

                document.setDmsDocumentInt01(dmsDocumentPostModel.getDocumentInt01());
                document.setDmsDocumentInt02(dmsDocumentPostModel.getDocumentInt02());
                document.setDmsDocumentInt03(dmsDocumentPostModel.getDocumentInt03());
                document.setDmsDocumentInt04(dmsDocumentPostModel.getDocumentInt04());
                document.setDmsDocumentIntComma(dmsDocumentPostModel.getDmsDocumentIntComma());

                document.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                document.setUpdatedDate(LocalDateTime.now());
                if (dmsDocumentPostModel.getWfTypeId() > 0) {
                    document.setWfTypeId(dmsDocumentPostModel.getWfTypeId());
                }

//                document.setRemovedBy(0);
                 List<String> attachName = new ArrayList<String>();
                List<String> fulltext = new ArrayList<String>();
                ParamService paramService = new ParamService();
                FileAttachService fileAttachService = new FileAttachService();
                List<FileAttach> listFileAttach = fileAttachService.listAllByLinkTypeLinkId("dms", id, "createdDate", "asc");
                if (!listFileAttach.isEmpty()) {
                    for (FileAttach fileAttach : listFileAttach) {
                        System.out.println("fileAttach new id = " + fileAttach.getId());

                        String url = "";
                        String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
                        url = pathDocumentHttp + fileAttach.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                        System.out.println("url = " + url);
                        attachName.add(fileAttach.getFileAttachName());
                        if (fileAttach.getFileAttachType().equalsIgnoreCase(".TXT")) {
                            File file = new File(url);
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String sCurrentLine;

                            while ((sCurrentLine = br.readLine()) != null) {
                                fulltext.add(sCurrentLine);
                            }
                        }
                        if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOCX")) {
                            System.out.println(" is docX ");
                            InputStream in = new FileInputStream(url);
                            XWPFDocument doc = new XWPFDocument(in);
                            XWPFWordExtractor ex = new XWPFWordExtractor(doc);
                            String text = ex.getText();
                            fulltext.add(text);
                        }
                        if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOC")) {
                            System.out.println(" is doc ");
                            File file = new File(url);
                            NPOIFSFileSystem fs = new NPOIFSFileSystem(file);
                            WordExtractor extractor = new WordExtractor(fs.getRoot());
                            for (String rawText : extractor.getParagraphText()) {
                                String text = extractor.stripFields(rawText);
                                fulltext.add(text);
                                System.out.println(text);
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
                                    }
                                }
                            }
                            for (int r = 0; r < rows; r++) {
                                row = sheet.getRow(r);
                                if (row != null) {
                                    for (int c = 0; c < cols; c++) {
                                        cell = row.getCell((short) c);
                                        if (cell != null) {
                                            fulltext.add(cell.toString());
                                        }
                                    }
                                }
                            }

                        }
                        if (fileAttach.getFileAttachType().equalsIgnoreCase(".XLSX")) {

                            InputStream ExcelFileToRead = new FileInputStream(url);
                            XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

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
                                        fulltext.add(cell.getStringCellValue());
                                    } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    }

                                }

                            }

                        }
                        if (fileAttach.getFileAttachType().equalsIgnoreCase(".PDF")) {
                            System.out.println("read text in pdf ");
                            File myFile = new File(url);
                            try (PDDocument doc = PDDocument.load(myFile)) {

                                PDFTextStripper stripper = new PDFTextStripper();
                                String text = stripper.getText(doc);
                                fulltext.add(text);
//                                        System.out.println("Text size: " + text.length() + " characters:");
//                                        System.out.println(text);

                            } catch (Exception ex) {
                                System.out.println("can not read text in pdf file");
                            }
                        }
                    }
                }
                DmsSearchService dmsSearchService = new DmsSearchService();
//                String searchId = document.getDmsSearchId();
//                String useElastic = paramService.getByParamName("USE_ELASTICSEARCH").getParamValue();

              
                   System.out.println("--search table--");
                    //search table
                    System.out.println("--------0001");
                    String temp = dmsSearchService.changDocumntToSearchField(document);
                    System.out.println("--------0002");
                    temp = temp + attachName;
                    temp = temp + fulltext;
                    String temp1 = temp.replaceAll("null"," ");
                    document.setFullText(temp1);

                
                System.out.println("---end");
                
                DmsDocument documentNew = dmsDocumentService.update(document);
                //log update
                if (document.getRemovedBy() != -1) {
                    dmsDocumentService.saveLogForUpdate(document, documentNew, httpHeaders.getHeaderString("clientIp"));

                }


                status = Response.Status.OK;
                responseData.put("data", dmsDocumentService.tranformToModel(documentNew));
                responseData.put("message", "dmsDocument updeted by id success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for delete dmsDocument by id.",
            notes = "ลบข้อมูลรายงาน",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "dmsDocument deleted by id success."),
        @ApiResponse(code = 404, message = "dmsDocument by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสเอกสาร", required = true)
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
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "dmsDocument by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            DmsDocument dmsDocument = dmsDocumentService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            dmsDocumentService.saveLogForRemove(dmsDocument, httpHeaders.getHeaderString("clientIp"));
//            System.out.println("dmsDocument remove =" + dmsDocument.getRemovedBy());
//            DmsSearchService dmsSearchService = new DmsSearchService();
//            DmsSearchModel temp = dmsSearchService.changDocumntToSearch(dmsDocument);
//            String searchId = dmsDocument.getDmsSearchId();
//            temp.setType("DOC");
//            DmsSearchModel result = dmsSearchService.updateData(searchId, temp);

            if (dmsDocument != null) {
//                dmsDocumentService.saveLogForRemove(dmsDocument, versionModel.getClientIp());
                status = Response.Status.OK;
                responseData.put("data", dmsDocumentService.tranformToModel(dmsDocument));
//                responseData.put("result", result);
                responseData.put("message", "dmsDocument deleted by id success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get  document by id.",
            notes = "ดึงข้อมูลเอกสาร ด้วย id",
            //        responseContainer = "List",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "document  success."),
        @ApiResponse(code = 404, message = "document  not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response list(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสเอกสาร", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("get...");
        LOG.info("id = " + id);
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
        responseData.put("data", null);
        responseData.put("message", "document  not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
//            System.out.println(" 1111111");
            DmsDocument document = dmsDocumentService.getById(id);
//            System.out.println("222222");
//            System.out.println("document = "+document.getDmsDocumentName());
            if (document != null) {

                status = Response.Status.OK;
                responseData.put("data", dmsDocumentService.tranformToModel2(document, "N"));
                responseData.put("message", "get dmsDocument  by id success.");

            }

            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

//    @ApiOperation(
//            value = "Method for check  document in folder.",
//            notes = "ตรวจสอบเอกสารในที่เก็บเอกสาร ด้วย folder id",
//            //        responseContainer = "List",
//            response = DmsDocumentModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "check success.")
//        ,
//        @ApiResponse(code = 404, message = "folderid not found in the database.")
//        ,
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/check/{id}")
//    public Response checkDocInFolder(
//            @HeaderParam("userID") int userID,
//            @BeanParam VersionModel versionModel,
//            @ApiParam(name = "id", value = "รหัสที่เก็บเอกสาร", required = true)
//            @PathParam("id") int id
//    ) {
//        log.info("get...");
//        log.info("id = " + id);
//
//        Gson gs = new GsonBuilder()
//                .setVersion(versionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Response.Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "check  not found in the database.");
//        responseData.put("errorMessage", "");
//        try {
//            DmsDocumentService dmsDocumentService = new DmsDocumentService();
//            boolean result = dmsDocumentService.checkDocmentInFolder(id);
//
//            status = Response.Status.OK;
//            responseData.put("data", result);
//            responseData.put("message", "check  document in folder by folder id success.");
//
//            responseData.put("success", true);
//        } catch (Exception ex) {
////            log.error("Exception = "+ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
    @ApiOperation(
            value = "Method for list dmsDocument By folder id.",
            notes = "รายการเอกสารในที่เก็บเอกสาร",
            responseContainer = "List",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "dmsDocument list success."),
        @ApiResponse(code = 404, message = "dmsDocument list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/folder/{folderId}")
    public Response listDocInFolder(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderId", value = "รหัสที่เก็บเอกสาร", required = true)
            @QueryParam("folderId") int folderId,
            @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.info("listDocInFolder...");
        LOG.info("folderId = " + folderId);
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
        responseData.put("data", null);
        responseData.put("message", "dmsDocument list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService DmsDocumentService = new DmsDocumentService();

            LocalDateTime nowDate = LocalDateTime.now();
            DmsFolderService dmsFolderService = new DmsFolderService();
            DmsFolder folder = dmsFolderService.getById(folderId);
            int docType = folder.getDocumentTypeId();
//            System.out.println("listOptionModel.getOffset() - " + listOptionModel.getOffset());
//            System.out.println("listOptionModel.getLimit() - " + listOptionModel.getLimit());
            List<DmsDocument> listDmsDocument = DmsDocumentService.findListDocumentS(folderId, listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir());
            ListReturnModel listReturnModel = new ListReturnModel(0, 0, 0);
            if (!listDmsDocument.isEmpty()) {
                int count = listDmsDocument.size() + listOptionModel.getOffset();
                int countAll = DmsDocumentService.countDocInfolder(folderId);
                int next = 0;
                if (count >= listOptionModel.getLimit()) {
                    next = listOptionModel.getOffset() + listOptionModel.getLimit();
                    if (next >= countAll) {
                        next = 0;
                    }
                }

                listReturnModel = new ListReturnModel(countAll, count, next);
            }
            if (!listDmsDocument.isEmpty()) {
                List<DmsDocumentModel> listDmsDocumentModel = new ArrayList<>();
                for (DmsDocument dmsDocument : listDmsDocument) {
                    String isExp = "N";
                    if (dmsDocument.getDmsDocumentExpireDate() != null) {
                        if (nowDate.isBefore(dmsDocument.getDmsDocumentExpireDate())) {
                            isExp = "N";
                        } else {
                            isExp = "Y";
                        }
                    }

                    listDmsDocumentModel.add(DmsDocumentService.tranformToModel2(dmsDocument, isExp));

                }
                status = Response.Status.OK;
                responseData.put("data", listDmsDocumentModel);
                responseData.put("listReturn", listReturnModel);
                responseData.put("message", "dmsDocument list success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for search field Document With Auth",
            notes = "ค้นหารายการเอกสาร field",
            //            responseContainer = "List",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "dmsDocument list success."),
        @ApiResponse(code = 404, message = "dmsDocument list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/searchDocument")
    public Response searchDocumentWithOutAuth(
            //            @Context UriInfo uriInfo,
            @BeanParam VersionModel versionModel,
            FieldSearchModel fieldSearchModel //            @ApiParam(name = "aa", value = "aaa", required = false)
    //            @QueryParam("aa") String aa

    ) {
//        log.info("searchDocument...");
        System.out.println("searchDocumentWithOutAuth");

        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "dmsDocument list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            System.out.println("-----1");

            DmsDocumentService dmsDocumentService = new DmsDocumentService();

            System.out.println("-----2");
            System.out.println("fieldSearchModel -" + fieldSearchModel.getCreatedDateForm());
            List<DmsDocument> listDocAll = dmsDocumentService.searchDocumentWithOutAuth(fieldSearchModel, fieldSearchModel.getFolderId(), 0, 100, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            System.out.println("listDocAll = " + listDocAll.size());
            LocalDateTime nowDate = LocalDateTime.now();
            if (!listDocAll.isEmpty()) {
                List<DmsDocumentModel> listDmsDocumentModel = new ArrayList<>();
                for (DmsDocument dmsDocument : listDocAll) {

                    String isExp = "N";
                    if (dmsDocument.getDmsDocumentExpireDate() != null) {
                        LocalDateTime temp = dmsDocument.getDmsDocumentExpireDate();

                        if (nowDate.isBefore(dmsDocument.getDmsDocumentExpireDate())) {
                            isExp = "N";
                        } else {
                            isExp = "Y";
                        }
                    }

                    listDmsDocumentModel.add(dmsDocumentService.tranformToModel2(dmsDocument, isExp));
                }
                status = Response.Status.OK;
                responseData.put("data", listDmsDocumentModel);
                responseData.put("message", "dmsDocument list success.");
            }
            responseData.put("success", true);

        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();

    }

    @ApiOperation(
            value = "Method for send email exp doc.",
            notes = "ใช้ส่งเมล",
            responseContainer = "List",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "dmsDocument list success."),
        @ApiResponse(code = 404, message = "dmsDocument list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/emailDocExp")
    public Response sendEmail(
            @BeanParam VersionModel versionModel //                @ApiParam(name = "folderId", value = "รหัสที่เก็บเอกสาร", required = false)
    //                @QueryParam("folderId") int folderId

    ) {
        LOG.info("emailDocExp...");
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
        responseData.put("data", null);
        responseData.put("message", "dmsDocument list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            DmsFolderService dmsFolderService = new DmsFolderService();
            FileAttachService fileAttachService = new FileAttachService();
            UserProfileService userProfileService = new UserProfileService();
            ArrayList<String> fileAttachPath = new ArrayList<>();

            List<DmsDocument> listDocAll = dmsDocumentService.findListDocumentExpire2();

            ArrayList a = new ArrayList();
            for (int i = 0; i < listDocAll.size(); i++) {
                a.add(listDocAll.get(i).getDmsFolderId());

            }
            System.out.println("a size " + a.size());

            ArrayList listFolderId = new ArrayList();

            listFolderId = new ArrayList<String>(new LinkedHashSet<String>(a));//ตัดตัวซ้ำ
            System.out.println("listFolderId = " + listFolderId);
            ArrayList listUserId = new ArrayList();
            List<DmsFolder> listFolder = new ArrayList();

            for (int i = 0; i < listFolderId.size(); i++) {
                DmsFolder folderData = dmsFolderService.getById((int) listFolderId.get(i));
                listFolder.add(folderData);
                int userProfileId = folderData.getDmsUserPreExpire();
                if (userProfileId != 0) {
                    listUserId.add(userProfileId);
                }

            }
            listUserId = new ArrayList<String>(new LinkedHashSet<String>(listUserId));
            System.out.println("listUserId = " + listUserId);
//            System.out.println("listFolder = " + listFolder.get(0).getId());

            //send form userProfile
            for (int i = 0; i < listUserId.size(); i++) {
                int userProfileId = (int) listUserId.get(i);
                int countDocAll = 0;
                String fullPathUrl = " ท่านสามารถเข้าสู่ระบบได้ตามลิงค์นี้ <br>";

                String detailEmail = "<br>";
                for (DmsFolder aFolder : listFolder) {
                    if (aFolder.getDmsUserPreExpire() == userProfileId) {
                        System.out.println("folder send mail id = " + aFolder.getId());
                        List<DmsDocument> listDocAllByFolderId = dmsDocumentService.findListDocumentExpire(aFolder.getId());
                        int countDoc = listDocAllByFolderId.size();
                        countDocAll = countDocAll + countDoc;
                        String parentKey = aFolder.getDmsFolderParentKey();
                        String[] output = parentKey.split("฿");
                        String subPathUrl = "";
                        for (int j = 1; j < output.length; j++) {

                            int folderIdTemp = Integer.parseInt(output[j]);
                            DmsFolder folderDataTemp = dmsFolderService.getById(folderIdTemp);
                            if (j == 1) {
                                subPathUrl = folderDataTemp.getDmsFolderName();
                            } else {
                                subPathUrl = subPathUrl + "/" + folderDataTemp.getDmsFolderName();
                            }
                        }
                        //add detail
                        fullPathUrl = fullPathUrl + subPathUrl + "<br>";
                        detailEmail = detailEmail + "ชื่อแฟ้ม : " + aFolder.getDmsFolderName() + "<br>"
                                + "จำนวน : เอกสาร " + countDoc + " เอกสาร<br>"
                                + "มีดังต่อไปนี้ <br>";
                        for (int k = 0; k < listDocAllByFolderId.size(); k++) {
                            int num = 1 + k;
                            detailEmail = detailEmail + num + "&nbsp;" + listDocAllByFolderId.get(k).getDmsDocumentName() + "<br>";

                        }
                        detailEmail = detailEmail + "<br>";
                    }
                }
                //send mail 
                detailEmail = detailEmail + "<br>" + fullPathUrl;
                detailEmail = "ตรวจพบเอกสารหมดอายุจำนวน " + countDocAll + " เอกสาร <br>" + detailEmail;

                String emailUserProfileId = userProfileService.getById(userProfileId).getUserProfileEmail();
                System.out.println("emailUserProfileId = " + emailUserProfileId);
                if (emailUserProfileId != "" && emailUserProfileId != null) {
                    boolean debug = false;
                    String mailSubject = "[ADOCUMENT] แจ้งเตือนเอกสารหมดอายุ";
                    String mailTo = emailUserProfileId;
                    String mailToCC = "";
                    String mailToBCC = "";
                    String mailBody = detailEmail;
                    String mailType = "html";

                    boolean result = Common.sendEmail(mailSubject, mailTo, mailToCC, mailToBCC, mailBody, fileAttachPath, mailType, debug);
                    System.out.println("mail result = " + result);
                }
            }

//            send from email etc.
            for (int i = 0; i < listFolderId.size(); i++) {
                DmsFolder folderData = dmsFolderService.getById((int) listFolderId.get(i));
                if (folderData.getDmsEmailUserPreExpire() != "" && folderData.getDmsEmailUserPreExpire() != null) {
                    String dataEmail = "";
                    String urlPath = "";
                    String folderName = folderData.getDmsFolderName();
                    String parentKey = folderData.getDmsFolderParentKey();
                    System.out.println(parentKey);
                    int folderIda = folderData.getId();
                    List<DmsDocument> listDocAllTemp = dmsDocumentService.findListDocumentExpire(folderIda);
                    String[] output = parentKey.split("฿");
                    String listNameDoc = "ตรวจพบเอกสารหมดอายุจำนวน " + listDocAllTemp.size() + " เอกสาร<br>";
                    for (int j = 1; j < output.length; j++) {

                        int folderIdTemp = Integer.parseInt(output[j]);
                        DmsFolder folderDataTemp = dmsFolderService.getById(folderIdTemp);
                        if (j == 1) {
                            urlPath = urlPath + folderDataTemp.getDmsFolderName();
                        } else {
                            urlPath = urlPath + "/" + folderDataTemp.getDmsFolderName();
                        }
                    }
                    System.out.println("urlPath = " + urlPath);
//                listNameDoc = listNameDoc + "ตำแหน่งที่อยู่ของเอกสาร " + urlPath + "<br>" + "รายชื่อเอกสาร จำนวน&nbsp;" + listDocAllTemp.size() + "&nbsp;รายการ <br>";
                    listNameDoc = listNameDoc + "ชื่อแฟ้ม : " + folderName + "<br>"
                            + "จำนวน : " + listDocAllTemp.size() + " เอกสาร<br>มีดังต่อไปนี้";
                    for (int k = 0; k < listDocAllTemp.size(); k++) {
                        int num = 1 + k;
                        listNameDoc = listNameDoc + num + "&nbsp;" + listDocAllTemp.get(k).getDmsDocumentName() + "<br>";

                    }
                    listNameDoc = listNameDoc + "ท่านสามารถเข้าสู่ระบบได้ตามลิงค์นี้<br>" + urlPath;
//                System.out.println("listNameDoc = " + listNameDoc);
//                System.out.println("folderData.getDmsEmailUserPreExpire()  = " + folderData.getDmsEmailUserPreExpire());

                    boolean debug = false;
                    String mailSubject = "[ADOCUMENT] แจ้งเตือนเอกสารหมดอายุ";
                    String mailTo = folderData.getDmsEmailUserPreExpire();
                    String mailToCC = "";
                    String mailToBCC = "";
                    String mailBody = listNameDoc;
                    String mailType = "html";
                    if (mailTo != null && mailTo != "") {
                        boolean result = Common.sendEmail(mailSubject, mailTo, mailToCC, mailToBCC, mailBody, fileAttachPath, mailType, debug);
                        System.out.println("mail result = " + result);
                    }
                }
            }
            status = Response.Status.OK;

            responseData.put("data", listFolder);
            responseData.put("message", "dmsDocument list success.");
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

//     @ApiOperation(
//            value = "Method for create ADocument ",
//            notes = "สร้างข้อมูลเอกสาร ของ ADocument",
//            response = DmsDocumentModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 201, message = "Document created successfully.")
//        ,
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/createADocument/{folderId}/{attackId}/{typeCode}/{typeName}")
//    public Response createADocument(
//            @HeaderParam("userID") int userID,
//            @BeanParam VersionModel versionModel,
//            @ApiParam(name = "folderId", value = "folderId", required = true)
//            @PathParam("folderId") int folderId,
//            
//            @ApiParam(name = "attackId", value = "attackId", required = true)
//            @PathParam("attackId") int attackId,
//            
//            @ApiParam(name = "typeCode", value = "typeCode", required = true)
//            @PathParam("typeCode") String typeCode,
//            
//            @ApiParam(name = "typeName", value = "typeName", required = true)
//            @PathParam("typeName") String typeName
//            
//    ) {
//        LOG.debug("create ADocument...");
//
//        Gson gs = new GsonBuilder()
//                .setVersion(versionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
//        responseData.put("success", false);
//        responseData.put("data", null);
//        responseData.put("message", "Internal Server Error!");
//        responseData.put("errorMessage", "");
//        try {
//            DmsDocument document = new DmsDocument();
//            document.setCreatedBy(userID);
//            ParamService paramService = new ParamService();
//            Param aDocumentTypeParam = paramService.getByParamName("ADOCUMENTTYPEID");
//            int docType = Integer.parseInt(aDocumentTypeParam.getParamValue());
//            FileAttachService fileAttachService = new FileAttachService();
//            FileAttach fileAttach = fileAttachService.getById(attackId);
//            
//
//            document.setDocumentTypeId(docType);
//            document.setDmsDocumentName(fileAttach.getFileAttachName());
//            document.setDmsFolderId(folderId);
//            document.setDmsDocumentVarchar01(typeName);
//            document.setDmsDocumentVarchar02(typeCode);
//
//            DmsDocumentService dmsDocumentService = new DmsDocumentService();
//            
//            document = dmsDocumentService.create(document);
//           
////            document.setRemovedBy(1);
////            document = dmsDocumentService.update(document);
//
////            dmsDocumentService.saveLogForCreate(document, dmsDocumentPostModel.getClientIp());
//            LOG.debug(document);
//            LOG.debug(dmsDocumentService.tranformToModel(document));
//            responseData.put("data", dmsDocumentService.tranformToModel(document));
//            status = Response.Status.CREATED;
//            responseData.put("success", true);
//            responseData.put("message", "Document created successfully.");
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
    @ApiOperation(
            value = "Method for create ADocument ",
            notes = "สร้างข้อมูลเอกสาร ของ ADocument",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Document created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/createADocument/{folderId}/{CustomerName}/{ProjectName }")
    public Response createADocument(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderId", value = "folderId", required = true)
            @PathParam("folderId") int folderId,
            @ApiParam(name = "CustomerName", value = "CustomerName", required = true)
            @PathParam("CustomerName") String CustomerName,
            @ApiParam(name = "ProjectName", value = "ProjectName", required = true)
            @PathParam("ProjectName") String ProjectName
    ) {
        LOG.debug("create ADocument...");

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
        responseData.put("data", null);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            DmsDocument document = new DmsDocument();
            document.setCreatedBy(userID);
            ParamService paramService = new ParamService();
            Param aDocumentTypeParam = paramService.getByParamName("ADOCUMENTTYPEID");
            int docType = Integer.parseInt(aDocumentTypeParam.getParamValue());
//            FileAttachService fileAttachService = new FileAttachService();
//            FileAttach fileAttach = fileAttachService.getById(attackId);

            document.setDocumentTypeId(docType);
            document.setDmsDocumentName(" ");
            document.setDmsFolderId(folderId);
//            document.setRemovedBy(1);
            document.setDmsDocumentText01(CustomerName);
            document.setDmsDocumentText03(ProjectName);

            DmsDocumentService dmsDocumentService = new DmsDocumentService();

            document = dmsDocumentService.create(document);

            document.setRemovedBy(1);
            document = dmsDocumentService.update(document);

//            dmsDocumentService.saveLogForCreate(document, dmsDocumentPostModel.getClientIp());
            LOG.debug(document);
            LOG.debug(dmsDocumentService.tranformToModel(document));
            responseData.put("data", dmsDocumentService.tranformToModel(document));
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "Document created successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update ADocument ",
            notes = "แก้ไขเอกสาร ของ ADocument",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Document created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/createADocument/{docId}/{attackId}/{typeCode}/{typeName}")
    @Path(value = "/createADocument/{docId}/{wfTypeId}/{flowId}/{attackName}")
    public Response createADocument(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "docId", value = "docId", required = true)
            @PathParam("docId") int docId,
            @ApiParam(name = "attackName", value = "attackName", required = false)
            @PathParam("attackName") String attackName,
            @ApiParam(name = "wfTypeId", value = "wfTypeId", required = true)
            @PathParam("wfTypeId") int wfTypeId,
            @ApiParam(name = "flowId", value = "flowId", required = true)
            @PathParam("flowId") int flowId,
            @ApiParam(name = "typeCode", value = "typeCode", required = true)
            @PathParam("typeCode") String typeCode
    ) {
        LOG.debug("create ADocument...");

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
        responseData.put("data", null);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            DmsDocument document = dmsDocumentService.getByIdNotRemoved(docId);
//            userID = 117;
            document.setCreatedBy(userID);
            WfDocumentTypeService documentTypeService = new WfDocumentTypeService();
//            WfDocumentType documentType = new WfDocumentType();

            String documentName = "เอกสาร ";
            document.setRemovedBy(0);
//            document.setDocumentTypeId(docType);
            if (!attackName.isEmpty()) {
                document.setDmsDocumentName(attackName);
            } else {
                int docNum = 0;
                List<DmsDocument> listDmsDocument = dmsDocumentService.findListDocumentS(document.getDmsFolderId(), 0, 500, "createdDate", "asc");
                docNum = listDmsDocument.size() + 1;
                documentName = documentName + docNum;
                document.setDmsDocumentName(documentName);
            }

            document.setDmsDocumentVarchar01(documentTypeService.getById(wfTypeId).getDocumentTypeName());
            document.setDmsDocumentVarchar02(documentTypeService.getById(wfTypeId).getDocumentTypeCode());
            document.setDmsDocumentText02(documentTypeService.getById(wfTypeId).getDocumentTypeName());
            document.setWfTypeId(wfTypeId);
            document.setFlowId(flowId);
            DmsFolderService dmsFolderService = new DmsFolderService();
            String fullPath = dmsFolderService.getFullPathName(document.getDmsFolderId());
            document.setFullPathName(fullPath);

            document = dmsDocumentService.update(document);

//            String searchId = document.getDmsSearchId();
//            if (searchId != null) {
//                DmsSearchService dmsSearchService = new DmsSearchService();
//                DmsSearchModel temp = dmsSearchService.changDocumntToSearch(document);
//                DmsSearchModel resultTemp = dmsSearchService.getData(searchId);
//                List<String> attachName = resultTemp.getFileAttachName();
//                List<String> fulltext = resultTemp.getFullText();
//                temp.setFullText(fulltext);
//                temp.setFileAttachName(attachName);
//                temp.setType("DOC");
////                DmsSearchModel result = dmsSearchService.updateData(searchId, temp);
//                document.setDmsSearchId(result.getDmsSearchId());
//            } else {
//                DmsSearchService dmsSearchService = new DmsSearchService();
//                DmsSearchModel temp = dmsSearchService.changDocumntToSearch(document);
//                temp.setType("DOC");
////                DmsSearchModel result = dmsSearchService.addData(temp);
//                document.setDmsSearchId(result.getDmsSearchId());
//                //document = dmsDocumentService.update(document);
//
//            }
            responseData.put("data", dmsDocumentService.tranformToModel(document));
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "Document created successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list dmsDocument By docType idDms.",
            notes = "รายการเอกสารด้วยประเภทเอกสาร",
            responseContainer = "List",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "dmsDocument list success."),
        @ApiResponse(code = 404, message = "dmsDocument list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/DocTypeId/{docTypeId}/{folderId}")
    public Response listDocByDocTypeId(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "docTypeId", value = "docTypeId", required = true)
            @PathParam("docTypeId") int docTypeId,
            @ApiParam(name = "folderId", value = "folderId", required = true)
            @PathParam("folderId") int folderId //            @BeanParam ListOptionModel listOptionModel

    ) {
        LOG.info("listDocByDocTypeId...");

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
        responseData.put("data", null);
        responseData.put("message", "dmsDocument list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService DmsDocumentService = new DmsDocumentService();
            UserProfileService UserProfileService = new UserProfileService();
            LocalDateTime nowDate = LocalDateTime.now();

//            List<DmsDocument> listDmsDocument = DmsDocumentService.listDocByDocType(docTypeId);
            List<DmsDocument> listDmsDocument = DmsDocumentService.listDocByDocType2(docTypeId, folderId, userID);

            if (!listDmsDocument.isEmpty()) {
                List<DmsDocumentModel> listDmsDocumentModel = new ArrayList<>();
                for (DmsDocument dmsDocument : listDmsDocument) {
                    String isExp = "N";
                    if (dmsDocument.getDmsDocumentExpireDate() != null) {
                        if (nowDate.isBefore(dmsDocument.getDmsDocumentExpireDate())) {
                            isExp = "N";
                        } else {
                            isExp = "Y";
                        }
                    }

                    listDmsDocumentModel.add(DmsDocumentService.tranformToModel2(dmsDocument, isExp));
                }
                status = Response.Status.OK;
                responseData.put("data", listDmsDocumentModel);
                responseData.put("message", "dmsDocument list success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list dmsDocument By docType wfTypeId.",
            notes = "รายการเอกสารด้วยประเภทเอกสารของ wf",
            responseContainer = "List",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "dmsDocument list success."),
        @ApiResponse(code = 404, message = "dmsDocument list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listDocByWfDocTypeId/{wfDocTypeId}/{folderId}")
    public Response listDocByWfDocTypeId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "wfDocTypeId", value = "wfDocTypeId", required = true)
            @PathParam("wfDocTypeId") int wfDocTypeId,
            @ApiParam(name = "folderId", value = "folderId", required = true)
            @PathParam("folderId") int folderId //            @BeanParam ListOptionModel listOptionModel

    ) {
        LOG.info("listDocByDocTypeId...");

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
        responseData.put("data", null);
        responseData.put("message", "dmsDocument list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService DmsDocumentService = new DmsDocumentService();
            UserProfileService UserProfileService = new UserProfileService();
            LocalDateTime nowDate = LocalDateTime.now();

//            List<DmsDocument> listDmsDocument = DmsDocumentService.listDocByDocType(docTypeId);
            List<DmsDocument> listDmsDocument = DmsDocumentService.listDocByDocType3(wfDocTypeId, folderId);

            if (!listDmsDocument.isEmpty()) {
                List<DmsDocumentModel> listDmsDocumentModel = new ArrayList<>();
                for (DmsDocument dmsDocument : listDmsDocument) {
                    String isExp = "N";
                    if (dmsDocument.getDmsDocumentExpireDate() != null) {
                        if (nowDate.isBefore(dmsDocument.getDmsDocumentExpireDate())) {
                            isExp = "N";
                        } else {
                            isExp = "Y";
                        }
                    }

                    listDmsDocumentModel.add(DmsDocumentService.tranformToModel2(dmsDocument, isExp));
                }
                status = Response.Status.OK;
                responseData.put("data", listDmsDocumentModel);
                responseData.put("message", "dmsDocument list success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get  document by flow id.",
            notes = "ดึงข้อมูลเอกสาร ด้วย flow id",
            //        responseContainer = "List",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "document  success."),
        @ApiResponse(code = 404, message = "document  not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listDocByflowId/{id}")
    public Response listByWfId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "flow Id", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("get...");
        LOG.info("id = " + id);
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
        responseData.put("data", null);
        responseData.put("message", "document  not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            System.out.println("id = " + id);
            List<DmsDocument> listDmsDocument = dmsDocumentService.listByWfId(id);
            System.out.println("listDmsDocument = " + listDmsDocument.size());
            if (!listDmsDocument.isEmpty()) {
                List<DmsDocumentModel> listDmsDocumentModel = new ArrayList<>();
                for (DmsDocument dmsDocument : listDmsDocument) {

                    listDmsDocumentModel.add(dmsDocumentService.tranformToModel(dmsDocument));
                }
                status = Response.Status.OK;
                responseData.put("data", listDmsDocumentModel);
                responseData.put("message", "dmsDocument list success.");

            }

            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get  document by wfTypeid and folderId.",
            notes = "ดึงข้อมูลเอกสาร ด้วย wf id",
            //        responseContainer = "List",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "document  success."),
        @ApiResponse(code = 404, message = "document  not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listByFolderIdAndWfType/{folderId}/{wfTypeId}")
    public Response listByFolderIdAndWfType(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderId", value = "folder Id", required = true)
            @PathParam("folderId") int folderId,
            @ApiParam(name = "wfTypeId", value = "wfType Id", required = true)
            @PathParam("wfTypeId") int wfTypeId
    ) {
        LOG.info("get...");

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
        responseData.put("data", null);
        responseData.put("message", "document  not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            DmsFolderService dmsFolderService = new DmsFolderService();
            List<DmsDocument> listDmsDocument = new ArrayList<>();
            List<DmsFolder> listFolder = dmsFolderService.getListChildFolderList(folderId);

            outerloop:
            for (int i = 0; i < listFolder.size(); i++) {
                System.out.println(" listFolder = " + listFolder.get(i).getId());
                listDmsDocument = dmsDocumentService.listByFolderIdAndWfType(listFolder.get(i).getId(), wfTypeId);
                System.out.println("listDmsDocument 00= " + listDmsDocument);
                System.out.println("size = " + listDmsDocument.size());
                if (listDmsDocument.size() > 0) {
                    for (int j = 0; j < listDmsDocument.size(); j++) {
                        System.out.println("listDmsDocument 11= " + listDmsDocument.get(j).getDmsDocumentName());
                    }
                    break outerloop;
                }
            }

//            List<DmsDocument> listDmsDocument = dmsDocumentService.listByFolderIdAndWfType(folderId,wfTypeId);
            if (!listDmsDocument.isEmpty()) {
                List<DmsDocumentModel> listDmsDocumentModel = new ArrayList<>();
                for (DmsDocument dmsDocument : listDmsDocument) {

                    listDmsDocumentModel.add(dmsDocumentService.tranformToModel(dmsDocument));
                }
                status = Response.Status.OK;
                responseData.put("data", listDmsDocumentModel);
                responseData.put("message", "dmsDocument list success.");

            }

            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for search Document With DocType And WithOutAuth  ",
            notes = "ค้นหารายการเอกสาร ไม่ใส่สิทธ์ จาก ประเภทเอกสาร",
            //            responseContainer = "List",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "dmsDocument list success."),
        @ApiResponse(code = 404, message = "dmsDocument list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/searchDocumentWithDocType/{docTypeId}/{isWfType}")
    public Response searchDocumentWithDocType(
            @BeanParam VersionModel versionModel,
            FieldSearchModel fieldSearchModel,
            @ApiParam(name = "docTypeId", value = "docType Id", required = true)
            @PathParam("docTypeId") int docTypeId,
            @ApiParam(name = "isWfType", value = "Y คือประเภทของ wf ,N คือประเภทของ Dms", required = true)
            @PathParam("isWfType") String isWfType,
            @BeanParam ListOptionModel listOptionModel
    ) {
//        log.info("searchDocument...");
        System.out.println("searchDocumentWithDocType");

        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "dmsDocument list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            System.out.println("-----1");

            DmsDocumentService dmsDocumentService = new DmsDocumentService();

            System.out.println("-----2");
            List<DmsDocument> listDocAll = dmsDocumentService.searchDocumentWithDocType(fieldSearchModel, docTypeId, isWfType, listOptionModel.getOffset(), listOptionModel.getLimit());
            System.out.println("listDocAll = " + listDocAll.size());
            LocalDateTime nowDate = LocalDateTime.now();
            if (!listDocAll.isEmpty()) {
                List<DmsDocumentModel> listDmsDocumentModel = new ArrayList<>();
                for (DmsDocument dmsDocument : listDocAll) {

                    String isExp = "N";
                    if (dmsDocument.getDmsDocumentExpireDate() != null) {
                        LocalDateTime temp = dmsDocument.getDmsDocumentExpireDate();

                        if (nowDate.isBefore(dmsDocument.getDmsDocumentExpireDate())) {
                            isExp = "N";
                        } else {
                            isExp = "Y";
                        }
                    }

                    listDmsDocumentModel.add(dmsDocumentService.tranformToModel2(dmsDocument, isExp));
                }
                status = Response.Status.OK;
                responseData.put("data", listDmsDocumentModel);
                responseData.put("message", "dmsDocument list success.");
            }
            responseData.put("success", true);

        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();

    }

    @ApiOperation(
            value = "Method for create Document and search data",
            notes = "สร้างข้อมูลเอกสาร และ ข้อมูลการค้นหา ",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Document created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/createDoc/createSearch")
    public Response createAndSearchData(
            @BeanParam VersionModel versionModel,
            DmsDocumentModel dmsDocumentModel
    ) {
        LOG.debug("create DmsDocument and seearch data ...");

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
        responseData.put("data", null);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            DmsDocument document = new DmsDocument();
//userID =1;
            document.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));

            document.setDocumentTypeId(dmsDocumentModel.getDocumentTypeId());
            document.setDmsDocumentName(dmsDocumentModel.getDocumentName());
            document.setDmsFolderId(dmsDocumentModel.getDocumentFolderId());
            document.setDmsDocumentPublicStatus(dmsDocumentModel.getDocumentPublicStatus());

            if (dmsDocumentModel.getExpType() != "" && dmsDocumentModel.getExpType() != null) {
                LocalDateTime nowDate = LocalDateTime.now();
                if (dmsDocumentModel.getExpNumber() > 0) {
                    if (dmsDocumentModel.getExpType().equalsIgnoreCase("Y")) {
                        nowDate.plusYears(dmsDocumentModel.getExpNumber());
                        document.setDmsDocumentExpireDate(nowDate);
                    }
                    if (dmsDocumentModel.getExpType().equalsIgnoreCase("M")) {
                        nowDate.plusMonths(dmsDocumentModel.getExpNumber());
                        document.setDmsDocumentExpireDate(nowDate);
                    }
                    if (dmsDocumentModel.getExpType().equalsIgnoreCase("D")) {
                        nowDate.plusDays(dmsDocumentModel.getExpNumber());
                        document.setDmsDocumentExpireDate(nowDate);
                    }

                }
            }

            if (dmsDocumentModel.getDocumentPublicDate() != null) {
                document.setDmsDocumentPublicDate(dateThaiToLocalDateTime(dmsDocumentModel.getDocumentPublicDate()));
            }

            if (dmsDocumentModel.getDocumentExpireDate() != null) {
                document.setDmsDocumentExpireDate(dateThaiToLocalDateTime(dmsDocumentModel.getDocumentExpireDate()));
            }

            if (dmsDocumentModel.getDocumentDate01() != null) {
                document.setDmsDocumentDatetime01(dateThaiToLocalDateTime(dmsDocumentModel.getDocumentDate01()));
            }
            if (dmsDocumentModel.getDocumentDate02() != null) {
                document.setDmsDocumentDatetime02(dateThaiToLocalDateTime(dmsDocumentModel.getDocumentDate02()));
            }
            if (dmsDocumentModel.getDocumentDate03() != null) {
                document.setDmsDocumentDatetime03(dateThaiToLocalDateTime(dmsDocumentModel.getDocumentDate03()));
            }
            if (dmsDocumentModel.getDocumentDate04() != null) {
                document.setDmsDocumentDatetime04(dateThaiToLocalDateTime(dmsDocumentModel.getDocumentDate04()));
            }

            document.setDmsDocumentFloat01(dmsDocumentModel.getDocumentFloat01());
            document.setDmsDocumentFloat02(dmsDocumentModel.getDocumentFloat02());

            document.setDmsDocumentVarchar01(dmsDocumentModel.getDocumentVarchar01());
            document.setDmsDocumentVarchar02(dmsDocumentModel.getDocumentVarchar02());
            document.setDmsDocumentVarchar03(dmsDocumentModel.getDocumentVarchar03());
            document.setDmsDocumentVarchar04(dmsDocumentModel.getDocumentVarchar04());
            document.setDmsDocumentVarchar05(dmsDocumentModel.getDocumentVarchar05());
            document.setDmsDocumentVarchar06(dmsDocumentModel.getDocumentVarchar06());
            document.setDmsDocumentVarchar07(dmsDocumentModel.getDocumentVarchar07());
            document.setDmsDocumentVarchar08(dmsDocumentModel.getDocumentVarchar08());
            document.setDmsDocumentVarchar09(dmsDocumentModel.getDocumentVarchar09());
            document.setDmsDocumentVarchar10(dmsDocumentModel.getDocumentVarchar10());

            document.setDmsDocumentText01(dmsDocumentModel.getDocumentText01());
            document.setDmsDocumentText02(dmsDocumentModel.getDocumentText02());
            document.setDmsDocumentText03(dmsDocumentModel.getDocumentText03());
            document.setDmsDocumentText04(dmsDocumentModel.getDocumentText04());
            document.setDmsDocumentText05(dmsDocumentModel.getDocumentText05());

            document.setDmsDocumentInt01(dmsDocumentModel.getDocumentInt01());
            document.setDmsDocumentInt02(dmsDocumentModel.getDocumentInt02());
            document.setDmsDocumentInt03(dmsDocumentModel.getDocumentInt03());
            document.setDmsDocumentInt04(dmsDocumentModel.getDocumentInt04());
            document.setDmsDocumentIntComma(dmsDocumentModel.getDmsDocumentIntComma());

//            document.setDmsDocumentIntComma(dmsDocumentPostModel.getDmsDocumentIntComma());
            document.setDmsDocumentSec(dmsDocumentModel.getDmsDocumentSec());

            DmsDocumentService dmsDocumentService = new DmsDocumentService();

            document = dmsDocumentService.create(document);

            //log 
            System.out.println("aaaaa222");
            dmsDocumentService.saveLogForCreate(document, httpHeaders.getHeaderString("clientIp"));
            System.out.println("aaaa3333");

            DmsSearchService dmsSearchService = new DmsSearchService();
            DmsSearchModel temp = dmsSearchService.changDocumntToSearch(document);
            temp.setType("DOC");
            DmsSearchModel result = dmsSearchService.addData(temp);
            document.setDmsSearchId(result.getDmsSearchId());
            document = dmsDocumentService.update(document);

            LOG.debug(document);
            LOG.debug(dmsDocumentService.tranformToModel(document));
            responseData.put("data", dmsDocumentService.tranformToModel(document));
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "Document created successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update dmsDocument and search data.",
            notes = "แก้ไขข้อมูลเอกสารและข้อมูลสำหรับค้นหา",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Document updeted by id success."),
        @ApiResponse(code = 404, message = "Document by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateDoc/updateSearch/{id}")
    public Response updateDocAndSearch(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสเอกสาร", required = true)
            @PathParam("id") int id,
            DmsDocumentModel dmsDocumentPostModel
    ) {
        LOG.info("updateDocAndSearch...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(dmsDocumentPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Document by id not found in the database.");
        responseData.put("errorMessage", "");
        try {

            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            DmsDocument document = dmsDocumentService.getById(id);
            if (document != null) {
                document.setDmsDocumentEdit(Integer.parseInt(httpHeaders.getHeaderString("userID")));

                document.setDmsDocumentEditDate(LocalDateTime.now());

                document.setDocumentTypeId(dmsDocumentPostModel.getDocumentTypeId());
                document.setDmsDocumentName(dmsDocumentPostModel.getDocumentName());

                if (dmsDocumentPostModel.getDocumentPublicDate() != null) {
                    document.setDmsDocumentPublicDate(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentPublicDate()));
                }
                document.setDmsDocumentPublicStatus(dmsDocumentPostModel.getDocumentPublicStatus());

                document.setDmsFolderId(dmsDocumentPostModel.getDocumentFolderId());

                if (dmsDocumentPostModel.getDocumentExpireDate() != null) {
                    document.setDmsDocumentExpireDate(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentExpireDate()));
                }

                if (dmsDocumentPostModel.getDocumentDate01() != null) {
                    document.setDmsDocumentDatetime01(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentDate01()));
                }
                if (dmsDocumentPostModel.getDocumentDate02() != null) {
                    document.setDmsDocumentDatetime02(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentDate02()));
                }
                if (dmsDocumentPostModel.getDocumentDate03() != null) {
                    document.setDmsDocumentDatetime03(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentDate03()));
                }
                if (dmsDocumentPostModel.getDocumentDate04() != null) {
                    document.setDmsDocumentDatetime04(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentDate04()));
                }

                document.setDmsDocumentFloat01(dmsDocumentPostModel.getDocumentFloat01());
                document.setDmsDocumentFloat02(dmsDocumentPostModel.getDocumentFloat02());

                document.setDmsDocumentVarchar01(dmsDocumentPostModel.getDocumentVarchar01());
                document.setDmsDocumentVarchar02(dmsDocumentPostModel.getDocumentVarchar02());
                document.setDmsDocumentVarchar03(dmsDocumentPostModel.getDocumentVarchar03());
                document.setDmsDocumentVarchar04(dmsDocumentPostModel.getDocumentVarchar04());
                document.setDmsDocumentVarchar05(dmsDocumentPostModel.getDocumentVarchar05());
                document.setDmsDocumentVarchar06(dmsDocumentPostModel.getDocumentVarchar06());
                document.setDmsDocumentVarchar07(dmsDocumentPostModel.getDocumentVarchar07());
                document.setDmsDocumentVarchar08(dmsDocumentPostModel.getDocumentVarchar08());
                document.setDmsDocumentVarchar09(dmsDocumentPostModel.getDocumentVarchar09());
                document.setDmsDocumentVarchar10(dmsDocumentPostModel.getDocumentVarchar10());

                document.setDmsDocumentText01(dmsDocumentPostModel.getDocumentText01());
                document.setDmsDocumentText02(dmsDocumentPostModel.getDocumentText02());
                document.setDmsDocumentText03(dmsDocumentPostModel.getDocumentText03());
                document.setDmsDocumentText04(dmsDocumentPostModel.getDocumentText04());
                document.setDmsDocumentText05(dmsDocumentPostModel.getDocumentText05());

                document.setDmsDocumentInt01(dmsDocumentPostModel.getDocumentInt01());
                document.setDmsDocumentInt02(dmsDocumentPostModel.getDocumentInt02());
                document.setDmsDocumentInt03(dmsDocumentPostModel.getDocumentInt03());
                document.setDmsDocumentInt04(dmsDocumentPostModel.getDocumentInt04());
                document.setDmsDocumentIntComma(dmsDocumentPostModel.getDmsDocumentIntComma());

                document.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                document.setUpdatedDate(LocalDateTime.now());

                DmsDocument documentNew = dmsDocumentService.update(document);

                dmsDocumentService.saveLogForUpdate(document, documentNew, httpHeaders.getHeaderString("clientIp"));

                DmsSearchService dmsSearchService = new DmsSearchService();
                DmsSearchModel temp = dmsSearchService.changDocumntToSearch(document);
                String searchId = documentNew.getDmsSearchId();
                temp.setType("DOC");
//                DmsSearchModel result = dmsSearchService.updateData(searchId, temp);

//                dmsDocumentService.saveLogForUpdate(document, documentNew, dmsDocumentPostModel.getClientIp());
                status = Response.Status.OK;
                responseData.put("data", dmsDocumentService.tranformToModel(documentNew));
                responseData.put("message", "dmsDocument updeted by id success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create Document remove = -1",
            notes = "สร้างข้อมูลเอกสาร ที่ ผู้ลบ id  = -1",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Document created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/createCreate")
    public Response createCreate(
            @BeanParam VersionModel versionModel,
            DmsDocumentModel dmsDocumentModel
    ) {
        LOG.debug("create DmsDocument...");

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
        responseData.put("data", null);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            DmsDocument document = new DmsDocument();
//userID =1;
            document.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));

            DmsFolderService dmsFolderService = new DmsFolderService();
            DmsFolder folder = dmsFolderService.getById(dmsDocumentModel.getDocumentFolderId());
//            int expNum = folder.getDmsFolderTypeExpireNumber();
//            String expType = folder.getDmsFolderTypeExpire();

//            System.out.println("folder exp num = "+expNum);
//            System.out.println("folder exp type = "+expType);
//            
//            if(expNum == 0){
//                System.out.println(" expNum == 0 ");
//            document.setDmsDocumentExpireDate(LocalDateTime.now());
//            }
            document.setRemovedBy(-1);
            document.setDocumentTypeId(dmsDocumentModel.getDocumentTypeId());
            document.setDmsDocumentName(dmsDocumentModel.getDocumentName());
            document.setDmsFolderId(dmsDocumentModel.getDocumentFolderId());
            document.setDmsDocumentPublicStatus(dmsDocumentModel.getDocumentPublicStatus());

            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            document = dmsDocumentService.create(document);
            document.setRemovedBy(-1);
            document = dmsDocumentService.update(document);
            responseData.put("data", dmsDocumentService.tranformToModel(document));
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "Document created successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for updateCreate dmsDocument and remove = 0.",
            notes = "แก้ไขข้อมูลการสร้างเอกสาร และ update ผู้ลบ= 0",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Document updateCreate by id success."),
        @ApiResponse(code = 404, message = "Document by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/createUpdate/{id}")
    public Response updateCreate(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสเอกสาร", required = true)
            @PathParam("id") int id,
            DmsDocumentModel dmsDocumentPostModel
    ) {
        LOG.info("update...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(dmsDocumentPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Document by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            DmsDocument document = dmsDocumentService.getById(id);

//            System.out.println("wf TypeId -- " + dmsDocumentPostModel.getWfTypeId());
//             System.out.println("dmsDocumentPostModel = "+dmsDocumentPostModel.getDocumentPublicDate());
            if (document != null) {

                document.setDmsDocumentEdit(Integer.parseInt(httpHeaders.getHeaderString("userID")));

                document.setDmsDocumentEditDate(LocalDateTime.now());

                document.setDocumentTypeId(dmsDocumentPostModel.getDocumentTypeId());

                document.setDmsDocumentName(dmsDocumentPostModel.getDocumentName());

                if (dmsDocumentPostModel.getDocumentPublicDate() != null) {

                    document.setDmsDocumentPublicDate(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentPublicDate()));
                }

                document.setDmsDocumentPublicStatus(dmsDocumentPostModel.getDocumentPublicStatus());

                document.setDmsFolderId(dmsDocumentPostModel.getDocumentFolderId());

                if (dmsDocumentPostModel.getDocumentExpireDate() != null) {

                    document.setDmsDocumentExpireDate(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentExpireDate()));

                }

                if (dmsDocumentPostModel.getDocumentDate01() != null) {
                    document.setDmsDocumentDatetime01(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentDate01()));
                }

                if (dmsDocumentPostModel.getDocumentDate02() != null) {
                    document.setDmsDocumentDatetime02(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentDate02()));
                }

                if (dmsDocumentPostModel.getDocumentDate03() != null) {
                    document.setDmsDocumentDatetime03(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentDate03()));
                }

                if (dmsDocumentPostModel.getDocumentDate04() != null) {
                    document.setDmsDocumentDatetime04(dateThaiToLocalDateTime(dmsDocumentPostModel.getDocumentDate04()));
                }

                document.setDmsDocumentFloat01(dmsDocumentPostModel.getDocumentFloat01());
                document.setDmsDocumentFloat02(dmsDocumentPostModel.getDocumentFloat02());

                document.setDmsDocumentVarchar01(dmsDocumentPostModel.getDocumentVarchar01());
                document.setDmsDocumentVarchar02(dmsDocumentPostModel.getDocumentVarchar02());
                document.setDmsDocumentVarchar03(dmsDocumentPostModel.getDocumentVarchar03());
                document.setDmsDocumentVarchar04(dmsDocumentPostModel.getDocumentVarchar04());
                document.setDmsDocumentVarchar05(dmsDocumentPostModel.getDocumentVarchar05());
                document.setDmsDocumentVarchar06(dmsDocumentPostModel.getDocumentVarchar06());
                document.setDmsDocumentVarchar07(dmsDocumentPostModel.getDocumentVarchar07());
                document.setDmsDocumentVarchar08(dmsDocumentPostModel.getDocumentVarchar08());
                document.setDmsDocumentVarchar09(dmsDocumentPostModel.getDocumentVarchar09());
                document.setDmsDocumentVarchar10(dmsDocumentPostModel.getDocumentVarchar10());

                document.setDmsDocumentText01(dmsDocumentPostModel.getDocumentText01());
                document.setDmsDocumentText02(dmsDocumentPostModel.getDocumentText02());
                document.setDmsDocumentText03(dmsDocumentPostModel.getDocumentText03());
                document.setDmsDocumentText04(dmsDocumentPostModel.getDocumentText04());
                document.setDmsDocumentText05(dmsDocumentPostModel.getDocumentText05());

                document.setDmsDocumentInt01(dmsDocumentPostModel.getDocumentInt01());
                document.setDmsDocumentInt02(dmsDocumentPostModel.getDocumentInt02());
                document.setDmsDocumentInt03(dmsDocumentPostModel.getDocumentInt03());
                document.setDmsDocumentInt04(dmsDocumentPostModel.getDocumentInt04());
                document.setDmsDocumentIntComma(dmsDocumentPostModel.getDmsDocumentIntComma());

                document.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                document.setUpdatedDate(LocalDateTime.now());

//                document.setWfTypeId(dmsDocumentPostModel.getWfTypeId());
                DmsFolderService dmsFolderService = new DmsFolderService();

                String fullPath = dmsFolderService.getFullPathName(document.getDmsFolderId());

                document.setFullPathName(fullPath);

                document.setRemovedBy(0);

                List<String> attachName = new ArrayList<String>();
                List<String> fulltext = new ArrayList<String>();
                ParamService paramService = new ParamService();
                FileAttachService fileAttachService = new FileAttachService();
                List<FileAttach> listFileAttach = fileAttachService.listAllByLinkTypeLinkId("dms", id, "createdDate", "asc");
                if (!listFileAttach.isEmpty()) {
                    for (FileAttach fileAttach : listFileAttach) {
                        System.out.println("fileAttach new id = " + fileAttach.getId());

                        String url = "";
                        String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
                        url = pathDocumentHttp + fileAttach.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                        System.out.println("url = " + url);
                        attachName.add(fileAttach.getFileAttachName());
                        if (fileAttach.getFileAttachType().equalsIgnoreCase(".TXT")) {
                            File file = new File(url);
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String sCurrentLine;

                            while ((sCurrentLine = br.readLine()) != null) {
                                fulltext.add(sCurrentLine);
                            }
                        }
                        if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOCX")) {
                            System.out.println(" is docX ");
                            InputStream in = new FileInputStream(url);
                            XWPFDocument doc = new XWPFDocument(in);
                            XWPFWordExtractor ex = new XWPFWordExtractor(doc);
                            String text = ex.getText();
                            fulltext.add(text);
                        }
                        if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOC")) {
                            System.out.println(" is doc ");
                            File file = new File(url);
                            NPOIFSFileSystem fs = new NPOIFSFileSystem(file);
                            WordExtractor extractor = new WordExtractor(fs.getRoot());
                            for (String rawText : extractor.getParagraphText()) {
                                String text = extractor.stripFields(rawText);
                                fulltext.add(text);
                                System.out.println(text);
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
                                    }
                                }
                            }
                            for (int r = 0; r < rows; r++) {
                                row = sheet.getRow(r);
                                if (row != null) {
                                    for (int c = 0; c < cols; c++) {
                                        cell = row.getCell((short) c);
                                        if (cell != null) {
                                            fulltext.add(cell.toString());
                                        }
                                    }
                                }
                            }

                        }
                        if (fileAttach.getFileAttachType().equalsIgnoreCase(".XLSX")) {

                            InputStream ExcelFileToRead = new FileInputStream(url);
                            XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

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
                                        fulltext.add(cell.getStringCellValue());
                                    } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    }

                                }

                            }

                        }
                        if (fileAttach.getFileAttachType().equalsIgnoreCase(".PDF")) {
                            System.out.println("read text in pdf ");
                            File myFile = new File(url);
                            try (PDDocument doc = PDDocument.load(myFile)) {

                                PDFTextStripper stripper = new PDFTextStripper();
                                String text = stripper.getText(doc);
                                fulltext.add(text);
//                                        System.out.println("Text size: " + text.length() + " characters:");
//                                        System.out.println(text);

                            } catch (Exception ex) {
                                System.out.println("can not read text in pdf file");
                            }
                        }
                    }
                }
                DmsSearchService dmsSearchService = new DmsSearchService();
//                String searchId = document.getDmsSearchId();
//                String useElastic = paramService.getByParamName("USE_ELASTICSEARCH").getParamValue();

              
                   System.out.println("--search table--");
                    //search table
                    System.out.println("--------0001");
                    String temp = dmsSearchService.changDocumntToSearchField(document);
                    System.out.println("--------0002");
                    temp = temp + attachName;
                    temp = temp + fulltext;
                    String temp1 = temp.replaceAll("null"," ");
                    document.setFullText(temp1);

                
                System.out.println("---end");
                  document = dmsDocumentService.update(document);
                   dmsDocumentService.saveLogForCreate(document, httpHeaders.getHeaderString("clientIp"));
                status = Response.Status.OK;
                responseData.put("data", dmsDocumentService.tranformToModel(document));                 
                responseData.put("message", "dmsDocument updeted by id success.");
            } else {
                status = Response.Status.OK;
                responseData.put("data", "");
//                dmsDocumentService.saveLogForCreate(document, httpHeaders.getHeaderString("clientIp"));
//                document = dmsDocumentService.update(document);

              
                responseData.put("data", dmsDocumentService.tranformToModel(document));
                responseData.put("message", "dmsDocument updeted by id success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for move dmsDocument.",
            notes = "ย้ายเอกสาร",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "dmsDocument move by id success."),
        @ApiResponse(code = 404, message = "dmsDocument by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}/{FolderId}")
    public Response MoveDoc(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสเอกสาร 1,2,3,4", required = true)
            @PathParam("id") String id,
            @ApiParam(name = "FolderId", value = "รหัสที่เก็บเอกสาร", required = true)
            @PathParam("FolderId") int FolderId
    ) {
        LOG.info("MoveDoc...");
        LOG.info("id = " + id);
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
        responseData.put("data", null);
        responseData.put("message", "dmsDocument by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
//            DmsDocument dmsDocument = dmsDocumentService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
//            dmsDocumentService.saveLogForMove(dmsDocument, httpHeaders.getHeaderString("clientIp"));
            DmsSearchService dmsSearchService = new DmsSearchService();
            String[] parts = id.split(",");
            if (parts.length == 0) {
                parts[0] = id;
            }

            for (String temp : parts) {
                int docId = Integer.parseInt(temp);
                DmsDocument dmsDocument = dmsDocumentService.getById(docId);
                int folderIdOld = dmsDocument.getDmsFolderId();
                dmsDocument.setDmsFolderId(FolderId);
                DmsFolderService dmsFolderService = new DmsFolderService();
                String fullPath = dmsFolderService.getFullPathName(FolderId);
                dmsDocument.setFullPathName(fullPath);
                dmsDocument = dmsDocumentService.update(dmsDocument);
//                dmsDocumentService.saveLogForMove(dmsDocument, folderIdOld,httpHeaders.getHeaderString("clientIp"));
                String searchId = dmsDocument.getDmsSearchId();

//                if (searchId != null) {
//                    DmsSearchModel resultTemp = dmsSearchService.getData(searchId);
////                    System.out.println("resultTemp folder 1= "+resultTemp.getDocumentFolderId());
//                    resultTemp.setDocumentFolderId(FolderId);
////                    System.out.println("resultTemp folder = "+resultTemp.getDocumentFolderId());
//                    DmsSearchModel result = dmsSearchService.updateDataMove(searchId, FolderId);
//                } else {
////                    DmsSearchService dmsSearchService = new DmsSearchService();
//                    DmsSearchModel temp2 = dmsSearchService.changDocumntToSearch(dmsDocument);
//                    temp2.setType("DOC");
//                    DmsSearchModel result = dmsSearchService.addData(temp2);
//                    dmsDocument.setDmsSearchId(result.getDmsSearchId());
//                    dmsDocument = dmsDocumentService.update(dmsDocument);
//
//                }
            }

//                dmsDocumentService.saveLogForRemove(dmsDocument, versionModel.getClientIp());
            status = Response.Status.OK;
            responseData.put("data", true);
            responseData.put("success", true);
            responseData.put("message", "dmsDocument move by id success.");

            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "send email dmsDocument.",
            notes = "ส่งอีเมล",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "dmsDocument send email success."),
        @ApiResponse(code = 404, message = "Error!."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/sendEmail")
    public Response sendEmail(
            @BeanParam VersionModel versionModel,
            sendEmailModel sendEmail
    ) {
        LOG.info("sendEmail...");

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
        responseData.put("data", null);
        responseData.put("message", "Error!");
        responseData.put("errorMessage", "");
        try {

            boolean debug = false;
            ArrayList<String> fileAttachPath = new ArrayList<>();//get real path
//            System.out.println("sendEmail.getListAttachID() = " + sendEmail.getListAttachID());
            String[] attId = sendEmail.getListAttachID().split(",");
            FileAttachService fileAttachService = new FileAttachService();
//             FileAttach FileAttach = new FileAttach();
            String FileAttach = "";
            for (int i = 0; i < attId.length; i++) {
                FileAttach = fileAttachService.moveToTempPath(Integer.parseInt(attId[i]));
//                 System.out.println("FileAttach path = "+FileAttach);
                fileAttachPath.add(FileAttach);
            }

            String mailSubject = sendEmail.getSubject();
            String mailTo = sendEmail.getTo();
            String mailToCC = sendEmail.getCc();
            String mailToBCC = sendEmail.getBcc();
            String mailBody = sendEmail.getDetail();
            String mailType = "html";
            if (mailTo != null && mailTo != "") {
                boolean result = Common.sendEmail(mailSubject, mailTo, mailToCC, mailToBCC, mailBody, fileAttachPath, mailType, debug);
                System.out.println("mail result = " + result);
            }

            status = Response.Status.OK;
            responseData.put("data", true);
            responseData.put("success", true);
            responseData.put("message", "dmsDocument send email success.");
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list dmsDocumentexp in folder.",
            notes = "รายการเอกสารหมดอายุในที่เก็บเอกสาร",
            responseContainer = "List",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "dmsDocument list success."),
        @ApiResponse(code = 404, message = "dmsDocument list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/exp/{id}")
    public Response listDocExp(
            @HeaderParam("userID") int userID,
            @ApiParam(name = "id", value = "รหัสที่เก็บเอกสาร", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("list exp ...");
        LOG.info("id = " + id);
        Gson gs = new Gson();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "dmsDocument list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService DmsDocumentService = new DmsDocumentService();

            List<DmsDocument> listDmsDocument = DmsDocumentService.findListDocumentExpire(id);
            if (!listDmsDocument.isEmpty()) {
                List<DmsDocumentModel> listDmsDocumentModel = new ArrayList<>();
                for (DmsDocument dmsDocument : listDmsDocument) {
                    listDmsDocumentModel.add(DmsDocumentService.tranformToModel2(dmsDocument, "Y"));
                }
                status = Response.Status.OK;
                responseData.put("data", listDmsDocumentModel);
                responseData.put("message", "dmsDocument list success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create dynamic report data from SearchCC",
            notes = "สร้างข้อมูลรายงานจากการค้นหา",
            response = String.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "create dynamic report data success."),
        @ApiResponse(code = 404, message = "create dynamic report data not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/createDynamicReportDataFromSearch")
    //@Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response createDynamicReportDataFromSearch(
            @BeanParam VersionModel versionModel,
            ReportInputModel ReportInputModel //        @ApiParam(name = "reportOutput", value = "ประเภทไฟล์ของรายงาน  XLS , PDF", required = true) 
    //        @PathParam("reportOutput") String reportOutput,
    //        
    //        @ApiParam(name = "folderId", value = "folderId", required = true) 
    //        @PathParam("folderId") int folderId,
    //        
    //        @ApiParam(name = "mode", value = "ประเภทของรายงาน 1 เอกสารปกติ ,2 เอกสารหมดอายุ ,3 เอกสารค้นหา ", required = true) 
    //        @PathParam("mode") int mode,
    //        
    //        @ApiParam(name = "docTypeDetialId", value = "id doctypeDetail = 1-2-3-4-5", required = true) 
    //        @PathParam("docTypeDetialId") String docTypeDetialId

    ) {
        LOG.debug("createReportDataFromSearch...");
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
        responseData.put("message", "create report data not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            String result = dmsDocumentService.createDynamicReportDataFromSearch(ReportInputModel.getReportOutput(), ReportInputModel.getFolderId(), ReportInputModel.getMode(), ReportInputModel.getDocTypeDetialId());

            if (result != null && !result.isEmpty()) {

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
