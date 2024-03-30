package com.px.share.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.dms.entity.DmsDocument;
import com.px.dms.model.DmsSearchModel;
import com.px.dms.service.DmsDocumentService;
import com.px.dms.service.DmsSearchService;
import com.px.share.entity.FileAttach;
import com.px.share.model.ListOptionModel;
import com.px.share.model.VersionModel;
import com.px.share.model.FileAttachModel;
import com.px.share.model.FileAttachBase64Model;
import com.px.share.model.FileAttachModel2;
import com.px.share.model.FileAttachWfSearchModel;
import com.px.share.service.FileAttachService;
import com.px.share.service.ParamService;
import com.px.wf.entity.WfContent;
import com.px.wf.model.WfContentESModel;
import com.px.wf.service.WfContentService;
import com.px.wf.service.WfSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.apache.log4j.Logger;
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
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author OPAS
 */
@Api(value = "FileAttach ไฟล์แนบ")
@Path("v1/fileAttachs")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class FileAttachResource {

    private static final Logger LOG = Logger.getLogger(FileAttachResource.class);

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create FileAttach",
            notes = "สร้างข้อมูลเอกสารแนบ",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "FileAttach created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @POST
    public Response create(
            //        @BeanParam FileAttachModel fileAttachModel,
            @FormDataParam("file") InputStream uploadInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            //        @FormDataParam("fileAttachName") String fileAttachName,
            //        @FormDataParam("fileAttachType") String fileAttachType,
            //        @FormDataParam("fileAttachSize") float fileAttachSize,
            @FormDataParam("version") int version,
            @FormDataParam("linkType") String linkType,
            @FormDataParam("linkId") int linkId,
            @FormDataParam("referenceId") int referenceId,
            @FormDataParam("secrets") int secrets
    ) throws UnsupportedEncodingException {
        LOG.debug("create...");
//        Map<String, String> queryParams = fileDetail.getParameters();
//        for (String key : queryParams.keySet()) {
//            String value = queryParams.get(key);
//            LOG.debug(key+" = " +value);
//        }
//        LOG.debug("fileAttachName = " +fileAttachModel.getFileAttachName());
//        LOG.debug("fileAttachType = " +fileAttachModel.getFileAttachType());
//        LOG.debug("linkId = " +fileAttachModel.getLinkId());
//        LOG.debug("linkType = " +fileAttachModel.getLinkType());
        LOG.debug("fileDetail.getFileName() = " + fileDetail.getFileName());
//        LOG.debug("fileAttachName = " +fileAttachName);
//        LOG.debug("fileAttachType = " +fileAttachType);
//        LOG.debug("fileAttachSize = " +fileAttachSize);
        LOG.debug("version = " + version);
        LOG.debug("linkType = " + linkType);
        LOG.debug("linkId = " + linkId);
        LOG.debug("referenceId = " + referenceId);
        LOG.debug("secrets = " + secrets);
        String newFileName = new String(fileDetail.getFileName().getBytes("iso-8859-1"), "UTF-8");
        LOG.debug("newFileName = " + newFileName);
        Gson gs = new GsonBuilder()
                .setVersion(version)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("data", null);
        responseData.put("success", false);
        String tempFile = "";
        try {
//            if(fileAttachModel.getFileAttachName() == null) fileAttachModel.setFileAttachName(fileAttachName);
//            if(fileAttachModel.getFileAttachType() == null) fileAttachModel.setFileAttachType(fileAttachType);
//            if(fileAttachModel.getLinkId() == 0) fileAttachModel.setLinkId(linkId);
//            if(fileAttachModel.getLinkType() == null) fileAttachModel.setLinkType(linkType);
            ParamService paramService = new ParamService();
            FileAttach fileAttach = new FileAttach();
            fileAttach.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            fileAttach.setFileAttachName(newFileName);
            fileAttach.setFileAttachType(newFileName.substring(newFileName.lastIndexOf(".")).toUpperCase());
            fileAttach.setLinkType(linkType);
            fileAttach.setLinkId(linkId);
            fileAttach.setReferenceId(0);
            fileAttach.setSecrets(secrets);

            FileAttachService fileAttachService = new FileAttachService();
            fileAttach = fileAttachService.create(fileAttach);
            if (fileAttach != null) {
                String pathDocumentTemp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
                String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_HTTP").getParamValue();
                LOG.debug("pathDocumentTemp = " + pathDocumentTemp);
                LOG.debug("pathDocumentHttp = " + pathDocumentHttp);
                String fileSave = pathDocumentTemp + fileAttach.getLinkType() + File.separator + fileAttachService.buildFilePathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                LOG.debug("fileSave = " + fileSave);
                tempFile = fileSave;
                fileAttachService.saveFile(uploadInputStream, fileSave);

                fileSave = fileAttachService.moveToRealPath(fileAttach.getId());

                File file = new File(fileSave);
                fileAttach.setFileAttachSize(file.length());
                fileAttach = fileAttachService.update(fileAttach);
                LOG.debug("fileAttach getId = " + fileAttach.getId());
                if (referenceId > 0) {
//                    List<FileAttach> listOldFileAttach = fileAttachService.listAllByLinkTypeLinkId(linkType, linkId,"","");
//                    LOG.debug("listOldFileAttach = "+listOldFileAttach.size());
//                    for (FileAttach oldFileAttach : listOldFileAttach) {
//                        if(oldFileAttach.getId() != fileAttach.getId()){
//                            oldFileAttach.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
//                            oldFileAttach.setReferenceId(fileAttach.getId());
//                        }
//                    }
                    FileAttach oldFileAttach = fileAttachService.getByIdNotRemoved(referenceId);
                    oldFileAttach.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    oldFileAttach.setReferenceId(fileAttach.getId());
                    oldFileAttach = fileAttachService.update(oldFileAttach);

                    fileAttach.setOrderNo(oldFileAttach.getOrderNo());
                    fileAttach = fileAttachService.update(fileAttach);
                }

                FileAttachModel newFileAttachModel = fileAttachService.tranformToModel(fileAttach);
                String url = pathDocumentHttp + linkType + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                String thumbnailUrl = fileAttachService.buildThumbhunailUrl(fileAttach.getFileAttachType(), url);
                String resultUrlNoName = pathDocumentHttp + linkId + "/" + fileAttachService.buildHtmlPathExtNoName(fileAttach.getId());
                LOG.debug("resultUrlNoName = " + resultUrlNoName);
                newFileAttachModel.setUrl(url);
                newFileAttachModel.setThumbnailUrl(thumbnailUrl);
                responseData.put("data", newFileAttachModel);

            }
            ///แคะ text
            DmsSearchModel result;
            int id = linkId;
            DmsSearchService dmsSearchService = new DmsSearchService();
//            ParamService paramService = new ParamService();
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            DmsDocument document = dmsDocumentService.getById(id);
            String searchId = document.getDmsSearchId();
//            FileAttachService fileAttachService = new FileAttachService();
//            List<FileAttach> listFileAttach = fileAttachService.listAllByLinkTypeLinkId("dms", id, "createdDate", "asc");
            List<String> attachName = new ArrayList<String>();
            List<String> fulltext = new ArrayList<String>();
            if (searchId != null) {
                DmsSearchModel resultTemp = dmsSearchService.getData(searchId);
                attachName = resultTemp.getFileAttachName();
                fulltext = resultTemp.getFullText();
            }
            String url = "";
            String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
            url = pathDocumentHttp + fileAttach.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
            attachName.add(fileAttach.getFileAttachName());
            if (fileAttach.getFileAttachType().equalsIgnoreCase(".TXT")) {
                File file = new File(tempFile);
                BufferedReader br = new BufferedReader(new FileReader(file));
                String sCurrentLine;

                while ((sCurrentLine = br.readLine()) != null) {
                    fulltext.add(sCurrentLine);
                }
            } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOCX")) {
                XWPFDocument doc = new XWPFDocument(new FileInputStream(tempFile));
                XWPFWordExtractor ex = new XWPFWordExtractor(doc);
                String text = ex.getText();
                fulltext.add(text);
            } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOC")) {
                NPOIFSFileSystem fs = new NPOIFSFileSystem(new FileInputStream(tempFile));
                WordExtractor extractor = new WordExtractor(fs.getRoot());
                for (String rawText : extractor.getParagraphText()) {
                    String text = extractor.stripFields(rawText);
                    fulltext.add(text);
                }

            } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".XLS")) {
                POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(tempFile));
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
            } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".XLSX")) {
                XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(tempFile));
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
                            fulltext.add(cell.getStringCellValue());
                        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                                    fulltext.add( cell.getNumericCellValue());
                        }
                    }
                }
            } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".PDF")) {
                PDDocument pdDoc = PDDocument.load(new FileInputStream(tempFile));
                PDFTextStripper pdfStripper = new PDFTextStripper();
                String parsedText = pdfStripper.getText(pdDoc);
                fulltext.add(parsedText);
                pdDoc.close();
            }

            if (searchId == null) {
                DmsSearchModel temp = dmsSearchService.changDocumntToSearch(document);
                temp.setFileAttachName(attachName);
                temp.setFullText(fulltext);
                result = dmsSearchService.addData(temp);
                document.setDmsSearchId(result.getDmsSearchId());
                document = dmsDocumentService.update(document);
            } else {
                DmsSearchModel temp = dmsSearchService.changDocumntToSearch(document);
                temp.setFileAttachName(attachName);
                temp.setFullText(fulltext);
                result = dmsSearchService.updateData(searchId, temp);
            }
            //
            status = Response.Status.CREATED;
            responseData.put("data", true);
            responseData.put("success", true);
            responseData.put("message", "FileAttach created successfully.");
        } catch (IOException ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create FileAttach from ActiveX",
            notes = "สร้างข้อมูลเอกสารแนบจาก ActiveX",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "FileAttach created from ActiveX successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @POST
    @Path(value = "/activex")
    public Response createActiveX(
            //            @BeanParam VersionModel versionModel,
            //            @FormDataParam("linkType") String linkType,
            //            @FormDataParam("linkId") int linkId,
            //            @FormDataParam("referenceId") int referenceId,
            //            @FormDataParam("secrets") int secrets,
            FormDataMultiPart formData,
            //             @BeanParam VersionModel versionModel,
            //            @FormDataParam("file") InputStream uploadInputStream,
            //            @FormDataParam("file") FormDataContentDisposition fileDetail,

            @QueryParam("version") int version,
            @QueryParam("linkType") String linkType,
            @QueryParam("linkId") int linkId,
            @QueryParam("referenceId") int referenceId,
            @QueryParam("secrets") int secrets,
            @QueryParam("attachNameInput") String attachNameInput
    ) {
        LOG.debug("createActiveX...");
        Gson gs = new GsonBuilder()
                .setVersion(version)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("data", null);
        try {
            String str = new String(attachNameInput.getBytes(), StandardCharsets.UTF_8);
            String fileName = new String(attachNameInput.getBytes("iso-8859-1"), "UTF-8");
            fileName = attachNameInput;

            InputStream uploadInputStream = formData.getBodyParts().get(0).getEntityAs(InputStream.class);
            ParamService paramService = new ParamService();
            FileAttach fileAttach = new FileAttach();
            fileAttach.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            fileAttach.setFileAttachName(fileName);
            fileAttach.setFileAttachType(fileName.substring(fileName.lastIndexOf(".")).toUpperCase());
            fileAttach.setLinkType(linkType);
            fileAttach.setLinkId(linkId);
            fileAttach.setReferenceId(referenceId);
            fileAttach.setSecrets(secrets);

            FileAttachService fileAttachService = new FileAttachService();
            fileAttach = fileAttachService.create(fileAttach);
            if (fileAttach != null) {
                String pathDocumentTemp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
                String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_HTTP").getParamValue();
                LOG.debug("pathDocumentTemp = " + pathDocumentTemp);
                LOG.debug("pathDocumentHttp = " + pathDocumentHttp);
                String fileSave = pathDocumentTemp + fileAttach.getLinkType() + File.separator + fileAttachService.buildFilePathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                LOG.debug("fileSave = " + fileSave);
                fileAttachService.saveFile(uploadInputStream, fileSave);
                fileSave = fileAttachService.moveToRealPath(fileAttach.getId());
                File file2 = new File(fileSave);
                fileAttach.setFileAttachSize(file2.length());
                fileAttach = fileAttachService.update(fileAttach);

                FileAttachModel newFileAttachModel = fileAttachService.tranformToModel(fileAttach);
                String url2 = pathDocumentHttp + linkType + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                String thumbnailUrl = fileAttachService.buildThumbhunailUrl(fileAttach.getFileAttachType(), url2);
                String urlNoName = pathDocumentHttp + linkType + "/" + fileAttachService.buildHtmlPathExtNoName(fileAttach.getId());
                newFileAttachModel.setUrl(url2);
                newFileAttachModel.setThumbnailUrl(thumbnailUrl);
                newFileAttachModel.setUrlNoName(urlNoName);
                responseData.put("data", newFileAttachModel);

                DmsSearchModel result;
                int id = linkId;
                DmsSearchService dmsSearchService = new DmsSearchService();
//            ParamService paramService = new ParamService();
                DmsDocumentService dmsDocumentService = new DmsDocumentService();
                DmsDocument document = dmsDocumentService.getById(id);
                String searchId = document.getDmsSearchId();
//            FileAttachService fileAttachService = new FileAttachService();
//            List<FileAttach> listFileAttach = fileAttachService.listAllByLinkTypeLinkId("dms", id, "createdDate", "asc");
                List<String> attachName = new ArrayList<String>();
                List<String> fulltext = new ArrayList<String>();
                if (searchId != null) {
                    DmsSearchModel resultTemp = dmsSearchService.getData(searchId);
                    attachName = resultTemp.getFileAttachName();
                    fulltext = resultTemp.getFullText();
                }
//            
                String url = "";
                String pathDocumentHttp2 = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();

//            for (FileAttach fileAttach2 : listFileAttach) {
                url = pathDocumentHttp2 + fileAttach.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                attachName.add(fileAttach.getFileAttachName());
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
                }
                if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOC")) {
                    File file = new File(url);
                    NPOIFSFileSystem fs = new NPOIFSFileSystem(file);
                    WordExtractor extractor = new WordExtractor(fs.getRoot());

                    for (String rawText : extractor.getParagraphText()) {
                        String text = extractor.stripFields(rawText);
                        fulltext.add(text);
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
                                    // Your code here
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
                                fulltext.add(cell.getStringCellValue());
                            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            }

                        }

                    }

                }
//            }

                if (searchId == null) {
                    DmsSearchModel temp = dmsSearchService.changDocumntToSearch(document);
                    temp.setFileAttachName(attachName);
                    temp.setFullText(fulltext);
                    result = dmsSearchService.addData(temp);
                    document.setDmsSearchId(result.getDmsSearchId());
                    document = dmsDocumentService.update(document);
                } else {
                    DmsSearchModel temp = dmsSearchService.changDocumntToSearch(document);
                    temp.setFileAttachName(attachName);
                    temp.setFullText(fulltext);
                    result = dmsSearchService.updateData(searchId, temp);
                }

            }
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "FileAttach created successfully.");
        } catch (IOException ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create FileAttach from Base64String",
            notes = "สร้างข้อมูลเอกสารแนบ",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "FileAttach created from Base64String successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @Consumes({MediaType.APPLICATION_JSON})
    @POST
    @Path(value = "/base64")
    public Response createBase64(
            @BeanParam VersionModel versionModel,
            FileAttachBase64Model fileAttachBase64Model
    ) {
        LOG.debug("createBase64...");
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
        try {
            ParamService paramService = new ParamService();
            FileAttach fileAttach = new FileAttach();
            fileAttach.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
//            fileAttach.setFileAttachName(fileAttachBase64Model.getFileAttachName() + ".pdf");
            fileAttach.setFileAttachName(fileAttachBase64Model.getFileAttachName() + fileAttachBase64Model.getFileAttachType().toLowerCase());
            fileAttach.setFileAttachType(fileAttachBase64Model.getFileAttachType());
            fileAttach.setLinkType(fileAttachBase64Model.getLinkType());
            fileAttach.setLinkId(fileAttachBase64Model.getLinkId());
            fileAttach.setSecrets(fileAttachBase64Model.getSecrets());
            fileAttach.setReferenceId(fileAttachBase64Model.getReferenceId());
            FileAttachService fileAttachService = new FileAttachService();
            fileAttach = fileAttachService.create(fileAttach);
            if (fileAttach != null) {
                String pathDocumentTemp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
                String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_HTTP").getParamValue();
                LOG.debug("pathDocumentTemp = " + pathDocumentTemp);
                LOG.debug("pathDocumentHttp = " + pathDocumentHttp);
                String fileSave = pathDocumentTemp + fileAttach.getLinkType() + File.separator + fileAttachService.buildFilePathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                LOG.debug("fileSave = " + fileSave);
                fileAttachService.saveFileFromBase64(fileAttachBase64Model.getFileBase64(), fileSave);
                fileSave = fileAttachService.moveToRealPath(fileAttach.getId());
                File file = new File(fileSave);
                fileAttach.setFileAttachSize(file.length());
                fileAttach = fileAttachService.update(fileAttach);

                FileAttachModel fileAttachModel = fileAttachService.tranformToModel(fileAttach);
                String url = pathDocumentHttp + fileAttachModel.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                String thumbnailUrl = fileAttachService.buildThumbhunailUrl(fileAttach.getFileAttachType(), url);
                String urlNoName = pathDocumentHttp + fileAttachModel.getLinkType() + "/" + fileAttachService.buildHtmlPathExtNoName(fileAttach.getId());
                fileAttachModel.setUrl(url);
                fileAttachModel.setThumbnailUrl(thumbnailUrl);
                fileAttachModel.setUrlNoName(urlNoName);
                responseData.put("data", fileAttachModel);
            }
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "FileAttach created from Base64String successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get FileAttach by id",
            notes = "ขอข้อมูลเอกสารแนบ ด้วย รหัสเอกสารแนบ",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttach by id success."),
        @ApiResponse(code = 404, message = "FileAttach by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{fileAttachId}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "fileAttachId", value = "รหัสเอกสารแนบ", required = true)
            @PathParam("fileAttachId") int fileAttachId
    ) {
        LOG.debug("getById...");
        LOG.debug("fileAttachId = " + fileAttachId);
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
        responseData.put("message", "FileAttach by id not found in the database.");
        try {
            ParamService paramService = new ParamService();
            FileAttachService fileAttachService = new FileAttachService();
            FileAttach fileAttach = fileAttachService.getById(fileAttachId);
            if (fileAttach != null) {
                String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_HTTP").getParamValue();
                FileAttachModel fileAttachModel = fileAttachService.tranformToModel(fileAttach);
//                String fileSave = fileAttachService.moveToTempPath(fileAttach.getId());
                String url = pathDocumentHttp + "Temp/" + fileAttach.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                String thumbnailUrl = fileAttachService.buildThumbhunailUrl(fileAttach.getFileAttachType(), url);
                String urlNoName = pathDocumentHttp + "Temp/" + fileAttachModel.getLinkType() + "/" + fileAttachService.buildHtmlPathExtNoName2(fileAttach.getId());
                fileAttachModel.setUrl(url);
                fileAttachModel.setThumbnailUrl(thumbnailUrl);
                fileAttachModel.setUrlNoName(urlNoName);
                status = Response.Status.OK;
                responseData.put("data", fileAttachModel);
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
            value = "Method for update FileAttach.",
            notes = "แก้ไขข้อมูลเอกสารแนบ",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttach updeted by id success."),
        @ApiResponse(code = 404, message = "FileAttach by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Path(value = "/{id}")
    public Response update(
            @BeanParam FileAttachModel fileAttachModel,
            @FormDataParam("file") InputStream uploadInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @ApiParam(name = "id", value = "รหัสเอกสารแนบ", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("update...");
        LOG.debug("FileAttachName = " + fileDetail.getFileName());
        LOG.debug("FileAttachType = " + fileAttachModel.getFileAttachType());
        Gson gs = new GsonBuilder()
                .setVersion(fileAttachModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            ParamService paramService = new ParamService();
            FileAttachService fileAttachService = new FileAttachService();
            FileAttach fileAttach = fileAttachService.getById(id);
            if (fileAttach != null) {
//                fileAttach.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                fileAttach.setFileAttachName(fileDetail.getFileName());
                fileAttach.setFileAttachType(fileDetail.getFileName().substring(fileDetail.getFileName().lastIndexOf(".")).toUpperCase());
                fileAttach.setLinkType(fileAttachModel.getLinkType());
                fileAttach.setLinkId(fileAttachModel.getLinkId());
                fileAttach.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                fileAttach = fileAttachService.update(fileAttach);

                String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_HTTP").getParamValue();
                FileAttachModel newFileAttachModel = fileAttachService.tranformToModel(fileAttach);
//                String fileSave = fileAttachService.moveToTempPath(fileAttach.getId());
                String url = pathDocumentHttp + "Temp/" + fileAttachModel.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                String thumbnailUrl = fileAttachService.buildThumbhunailUrl(fileAttach.getFileAttachType(), url);
                String urlNoName = pathDocumentHttp + "Temp/" + fileAttachModel.getLinkType() + "/" + fileAttachService.buildHtmlPathExtNoName(fileAttach.getId());
//                String url = pathDocumentHttp+fileAttachService.buildHtmlPathExt(fileAttach.getId())+fileAttach.getFileAttachType();
                newFileAttachModel.setUrl(url);
                newFileAttachModel.setThumbnailUrl(thumbnailUrl);
                newFileAttachModel.setUrlNoName(urlNoName);
                status = Response.Status.OK;
                responseData.put("data", newFileAttachModel);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update FileAttach with version control.",
            notes = "แก้ไขข้อมูลเอกสารแนบแบบมี version control",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Version Control FileAttach updeted by id success."),
        @ApiResponse(code = 404, message = "Version Control FileAttach by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Path(value = "/versionControl/{id}")
    public Response updateWithVersionControl(
            @BeanParam FileAttachModel fileAttachModel,
            @FormDataParam("file") InputStream uploadInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @ApiParam(name = "id", value = "รหัสเอกสารแนบ", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("updateWithVersionControl...");
        LOG.debug("FileAttachName = " + fileDetail.getFileName());
        LOG.debug("FileAttachType = " + fileAttachModel.getFileAttachType());
        Gson gs = new GsonBuilder()
                .setVersion(fileAttachModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            ParamService paramService = new ParamService();
            FileAttachService fileAttachService = new FileAttachService();
            FileAttach fileAttach = fileAttachService.getByIdNotRemoved(id);
            if (fileAttach != null) {
//                fileAttach.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
//                fileAttach.setFileAttachName(fileDetail.getFileName());
//                fileAttach.setFileAttachType(fileDetail.getFileName().substring(fileDetail.getFileName().lastIndexOf(".")).toUpperCase());
//                fileAttach.setLinkType(fileAttachModel.getLinkType());
//                fileAttach.setLinkId(fileAttachModel.getLinkId());
                fileAttach.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                fileAttach = fileAttachService.update(fileAttach);

                FileAttach fileAttachVersionControl = new FileAttach();
                fileAttachVersionControl.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                fileAttachVersionControl.setFileAttachName(fileDetail.getFileName());
                fileAttachVersionControl.setFileAttachType(fileDetail.getFileName().substring(fileDetail.getFileName().lastIndexOf(".")).toUpperCase());
                fileAttachVersionControl.setLinkType(fileAttachModel.getLinkType());
                fileAttachVersionControl.setLinkId(fileAttachModel.getLinkId());
                fileAttachVersionControl.setReferenceId(id);
                fileAttachVersionControl = fileAttachService.create(fileAttachVersionControl);

                String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_HTTP").getParamValue();
                FileAttachModel newFileAttachModel = fileAttachService.tranformToModel(fileAttachVersionControl);
//                String fileSave = fileAttachService.moveToTempPath(fileAttachVersionControl.getId());
                String url = pathDocumentHttp + "Temp/" + fileAttachModel.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttachVersionControl.getId()) + fileAttachVersionControl.getFileAttachType();
                String thumbnailUrl = fileAttachService.buildThumbhunailUrl(fileAttach.getFileAttachType(), url);
                String urlNoName = pathDocumentHttp + "Temp/" + fileAttachModel.getLinkType() + "/" + fileAttachService.buildHtmlPathExtNoName(fileAttachVersionControl.getId());
//                String url = pathDocumentHttp+fileAttachService.buildHtmlPathExt(fileAttach.getId())+fileAttach.getFileAttachType();
                newFileAttachModel.setUrl(url);
                newFileAttachModel.setThumbnailUrl(thumbnailUrl);
                newFileAttachModel.setUrlNoName(urlNoName);
                status = Response.Status.OK;
                responseData.put("data", newFileAttachModel);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for delete FileAttach by id.",
            notes = "ลบข้อมูลเอกสารแนบ",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttach deleted by id success."),
        @ApiResponse(code = 404, message = "FileAttach by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสเอกสารแนบ", required = true)
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
        responseData.put("message", "FileAttach by id not found in the database.");
        try {
            FileAttachService fileAttachService = new FileAttachService();
            FileAttach fileAttach = fileAttachService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (fileAttach != null) {
                status = Response.Status.OK;
                responseData.put("data", fileAttachService.tranformToModel(fileAttach));
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
            value = "Method for list FileAttach.",
            notes = "รายการเอกสารประกอบ",
            responseContainer = "List",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttach list success."),
        @ApiResponse(code = 404, message = "FileAttach list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/linkType/{linkType}/linkId/{linkId}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listByLinkTypeLinkId(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "linkType", value = "module การทำงาน", required = true)
            @PathParam("linkType") String linkType,
            @ApiParam(name = "linkId", value = "รหัส record id ของการทำงาน", required = true)
            @PathParam("linkId") int linkId
    ) {
        LOG.debug("listByLinkTypeLinkId...");
        LOG.debug("linkType = " + linkType);
        LOG.debug("linkId = " + linkId);
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        ArrayList<FileAttachModel2> listFileAttachModel = new ArrayList<>();
        Response.Status status = Response.Status.OK;
        responseData.put("data", listFileAttachModel);
        responseData.put("success", false);
        responseData.put("message", "FileAttach list by LinkType LinkId not found in the database.");
        try {
            ParamService paramService = new ParamService();
            FileAttachService fileAttachService = new FileAttachService();
            List<FileAttach> listFileAttach = fileAttachService.listAllByLinkTypeLinkId(linkType, linkId, listOptionModel.getSort(), listOptionModel.getDir());
            if (!listFileAttach.isEmpty()) {
                String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_HTTP").getParamValue();
                FileAttachModel2 fileAttachModel = null;
                String url = "";
                String urlNoName = "";
                String thumbnailUrl = "";
//                ArrayList<FileAttachModel2> listFileAttachModel = new ArrayList<>();
                for (FileAttach fileAttach : listFileAttach) {
                    if (fileAttach.getFileAttachType().equalsIgnoreCase(".TTT")) {
                        fileAttachService.delete(fileAttach);
                    } else {
                        fileAttachModel = fileAttachService.tranformToModel2(fileAttach);
//                        String fileSave = fileAttachService.moveToTempPath(fileAttach.getId());
                        url = pathDocumentHttp + "Temp/" + fileAttach.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
//                    url = pathDocumentHttp+fileAttachService.buildHtmlPathExt(fileAttach.getId())+fileAttach.getFileAttachType();
                        thumbnailUrl = fileAttachService.buildThumbhunailUrl(fileAttach.getFileAttachType(), url);
                        urlNoName = pathDocumentHttp + "Temp/" + fileAttachModel.getLinkType() + "/" + fileAttachService.buildHtmlPathExtNoName2(fileAttach.getId());
                        fileAttachModel.setUrl(url);
                        fileAttachModel.setThumbnailUrl(thumbnailUrl);
                        fileAttachModel.setUrlNoName(urlNoName);
                        listFileAttachModel.add(fileAttachModel);
                    }
                }
                listFileAttachModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listFileAttachModel);
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
            value = "Method for get PlugIn",
            notes = "ขอข้อมูล PlugIn",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "PlugIn success."),
        @ApiResponse(code = 404, message = "PlugIn not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.TEXT_PLAIN})
    @Path(value = "/plugins")
    public Response getPlugIn(
            @BeanParam VersionModel versionModel
    ) {
        LOG.debug("getPlugIn...");
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
        responseData.put("message", "FileAttach by id not found in the database.");
        try {
            //PlugIn.EXE
            ParamService paramService = new ParamService();
            String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_HTTP").getParamValue();
            String filePlugIn = pathDocumentHttp + File.separator + "PlugIn.EXE";

            FileAttachService fileAttachService = new FileAttachService();
            FileAttach fileAttach = new FileAttach();
            FileAttachModel fileAttachModel = fileAttachService.tranformToModel(fileAttach);
            fileAttachModel.setUrl(filePlugIn);
            fileAttachModel.setThumbnailUrl(null);

            status = Response.Status.OK;
            responseData.put("data", fileAttachModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for Download File",
            notes = "Download File",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Download File success."),
        @ApiResponse(code = 404, message = "File not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{fileAttachId}/dl")
    public Response downloadFile(
            @ApiParam(name = "fileAttachId", value = "รหัสไฟล์เอกสารแนบ", required = true)
            @PathParam("fileAttachId") int fileAttachId
    ) {
        LOG.debug("downloadFile...");
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "FileAttach by id not found in the database.");
        StreamingOutput fileStream = null;
        String fileName = "";
        try {
            FileAttachService fileAttachService = new FileAttachService();
            FileAttach fileAttach = fileAttachService.getById(fileAttachId);
            if (fileAttach != null) {
                fileName = fileAttach.getFileAttachName();
                fileName = URLEncoder.encode(fileName, "UTF-8");
                String fileSave = fileAttachService.moveToTempPath(fileAttach.getId());
                fileStream = new StreamingOutput() {
                    @Override
                    public void write(java.io.OutputStream output) throws IOException, WebApplicationException {
                        try {
                            java.nio.file.Path path = Paths.get(fileSave);
                            byte[] data = Files.readAllBytes(path);
                            output.write(data);
                            output.flush();
                        } catch (IOException e) {
                            throw new WebApplicationException("File Not Found !!");
                        }
                    }
                };
            }
            responseData.put("success", true);
        } catch (UnsupportedEncodingException ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM).encoding("utf-8")
                .header("content-disposition", "attachment; filename*=UTF-8' '" + fileName)
                //                .setCharacterEncoding("utf-8")
                .build();
    }

    @ApiOperation(
            value = "Method for update FileAttach.",
            notes = "แก้ไขเอกสารแนบ",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttach updeted by id success."),
        @ApiResponse(code = 404, message = "FileAttach by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/update/{fileAttachId}")
    public Response update(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "fileAttachId", value = "fileAttachId", required = true)
            @PathParam("fileAttachId") int fileAttachId,
            FileAttachModel fileAttachModel
    ) {
        LOG.info("update sec...");
        LOG.info("fileAttachId = " + fileAttachId);
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
        responseData.put("message", "Folder by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            FileAttachService fileAttachService = new FileAttachService();
            FileAttach fileAttach = fileAttachService.getById(fileAttachId);
            if (fileAttach != null) {
                fileAttach.setSecrets(fileAttachModel.getSecrets());
//                fileAttach.setReferenceId(fileAttachModel.getReferenceId());
                fileAttach = fileAttachService.update(fileAttach);
                status = Response.Status.OK;
                responseData.put("data", fileAttachService.tranformToModel(fileAttach));
                responseData.put("message", "FileAttach updeted by id success.");
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
            value = "Method for create FullText from file attach",
            notes = "FullText File",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Create FullText success."),
        @ApiResponse(code = 404, message = "File not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/fts/{documentId}/{fileAttachId}")
    public Response ftsFile(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "documentId", value = "documentId", required = true)
            @PathParam("documentId") int documentId,
            @ApiParam(name = "fileAttachId", value = "รหัสไฟล์เอกสารแนบ", required = true)
            @PathParam("fileAttachId") int fileAttachId
    ) {
        LOG.debug("Create FullTextSearch...");
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
        responseData.put("message", "FileAttach by id not found in the database.");
        try {
            LOG.debug("fileAttachId = " + fileAttachId);
            List<String> fulltext = new ArrayList<String>();
            FileAttachService fileAttachService = new FileAttachService();
            FileAttach fileAttach = fileAttachService.getByIdNotRemoved(fileAttachId);
            if (fileAttach != null) {
                String tempFile = fileAttachService.moveToTempPath(fileAttach.getId());
                if (fileAttach.getFileAttachType().equalsIgnoreCase(".TXT")) {
                    File file = new File(tempFile);
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String sCurrentLine;
                        while ((sCurrentLine = br.readLine()) != null) {
                            LOG.debug("sCurrentLine = " + sCurrentLine);
                            fulltext.add(sCurrentLine);
                        }
                        br.close();
                    }
                } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOCX")) {
                    try (InputStream in = new FileInputStream(tempFile)) {
                        XWPFDocument doc = new XWPFDocument(in);
                        XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
                        String text = extractor.getText();
                        fulltext.add(text);
                        extractor.close();
                        //doc.close();//*****
                    }
                } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOC")) {
                    File file = new File(tempFile);
                    NPOIFSFileSystem fs = new NPOIFSFileSystem(file);
                    WordExtractor extractor = new WordExtractor(fs.getRoot());
                    for (String rawText : extractor.getParagraphText()) {
                        String text = extractor.stripFields(rawText);
                        fulltext.add(text);
                    }
                    extractor.close();
                    fs.close();
                } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".XLS")) {
                    File file = new File(tempFile);
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
                    fs.close();
                } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".XLSX")) {
                    File file = new File(tempFile);
                    InputStream excelFileToRead = new FileInputStream(file);
                    XSSFWorkbook wb = new XSSFWorkbook(excelFileToRead);
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
                            }
                        }
                    }
                    excelFileToRead.close();
                } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".PDF")) {
                    File file = new File(tempFile);
                    PDDocument pdDoc = PDDocument.load(file);
                    PDFTextStripper pdfStripper = new PDFTextStripper();
                    String parsedText = pdfStripper.getText(pdDoc);
                    fulltext.add(parsedText);
                    pdDoc.close();
                }
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
            value = "Method for get FileAttach by id",
            notes = "ขอข้อมูลเอกสารแนบ ด้วย รหัสเอกสารแนบ",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttach by id success."),
        @ApiResponse(code = 404, message = "FileAttach by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.TEXT_PLAIN})
    @Path(value = "/watermark/{fileAttachId}")
    public Response getWatermarkById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "fileAttachId", value = "รหัสเอกสารแนบ", required = true)
            @PathParam("fileAttachId") int fileAttachId
    ) {
        LOG.debug("getWatermarkById...");
        LOG.debug("fileAttachId = " + fileAttachId);
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
        responseData.put("message", "FileAttach by id not found in the database.");
        try {
            ParamService paramService = new ParamService();
            FileAttachService fileAttachService = new FileAttachService();
            FileAttach fileAttach = fileAttachService.getById(fileAttachId);
            if (fileAttach != null) {
                String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_HTTP").getParamValue();
                String pathFileWaterMark = paramService.getByParamName("PATH_FILE_WATERMARK").getParamValue();
                FileAttachModel fileAttachModel = fileAttachService.tranformToModel(fileAttach);
                String fileSave = fileAttachService.moveToTempPath(fileAttach.getId());
                if (!fileSave.equalsIgnoreCase("")) {
                    String pathDesWatermark = fileSave.substring(0, fileSave.indexOf(".")) + "_w" + fileSave.substring(fileSave.indexOf("."));
                    String waterMarkFile = fileAttachService.buildWaterMark(fileSave, pathDesWatermark, pathFileWaterMark);
                }
                String url = pathDocumentHttp + "Temp/" + fileAttach.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + "_w" + fileAttach.getFileAttachType();
//                String thumbnailUrl = fileAttachService.buildThumbhunailUrl(fileAttach.getFileAttachType(), url);
                String urlNoName = pathDocumentHttp + "Temp/" + fileAttachModel.getLinkType() + "/" + fileAttachService.buildHtmlPathExtNoName2(fileAttach.getId());
                fileAttachModel.setUrl(url);
//                fileAttachModel.setThumbnailUrl(thumbnailUrl);
                fileAttachModel.setUrlNoName(urlNoName);
                status = Response.Status.OK;
                responseData.put("data", fileAttachModel);
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

    /*//แคะ text
             DmsSearchModel result;
             int id = linkId;
                DmsSearchService dmsSearchService = new DmsSearchService();
//            ParamService paramService = new ParamService();
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            DmsDocument document = dmsDocumentService.getById(id);
            String searchId = document.getDmsSearchId();
//            FileAttachService fileAttachService = new FileAttachService();
            List<FileAttach> listFileAttach = fileAttachService.listAllByLinkTypeLinkId("dms", id, "createdDate", "asc");
            List<String> attachName = new ArrayList<String>();
            List<String> fulltext = new ArrayList<String>();
            String url = "";
            String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
            System.out.println(" listFileAttach = " + listFileAttach.size());

            for (FileAttach fileAttach2 : listFileAttach) {
                url = pathDocumentHttp + fileAttach2.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                System.out.println("url = " + url);
                attachName.add(fileAttach2.getFileAttachName());
                System.out.println("getFileAttachName = " + fileAttach2.getFileAttachName());
                if (fileAttach2.getFileAttachType().equalsIgnoreCase(".TXT")) {
//                    url = pathDocumentHttp + fileAttach2.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();

                    File file = new File(url);
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String sCurrentLine;

                    while ((sCurrentLine = br.readLine()) != null) {
                        fulltext.add(sCurrentLine);
                    }
                }
                if (fileAttach2.getFileAttachType().equalsIgnoreCase(".DOCX")) {
                    InputStream in = new FileInputStream(url);
                    XWPFDocument doc = new XWPFDocument(in);
                    XWPFWordExtractor ex = new XWPFWordExtractor(doc);
                    String text = ex.getText();
                    fulltext.add(text);
//                    System.out.println("text = " + text);
                }
                if (fileAttach2.getFileAttachType().equalsIgnoreCase(".DOC")) {
                    File file = new File(url);
                    NPOIFSFileSystem fs = new NPOIFSFileSystem(file);
                    WordExtractor extractor = new WordExtractor(fs.getRoot());

                    for (String rawText : extractor.getParagraphText()) {
                        String text = extractor.stripFields(rawText);
                        fulltext.add(text);
//                        System.out.println(text);
                    }

                }
                if (fileAttach2.getFileAttachType().equalsIgnoreCase(".XLS")) {
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
                if (fileAttach2.getFileAttachType().equalsIgnoreCase(".XLSX")) {
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
                            if (cell.getCellType()== Cell.CELL_TYPE_STRING) {
//                                System.out.print(cell.getStringCellValue() + "--");
                            fulltext.add(cell.getStringCellValue());
                            } else if (cell.getCellType()== Cell.CELL_TYPE_NUMERIC) {
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

     */
    //oat-add
    @ApiOperation(
            value = "Method for update FileAttach linkType by linkId",
            notes = "แก้ไข module การทำงานของเอกสารแนบทั้งหมดของเอกสาร"//finish content and keep
    //response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttach updeted by id success."),
        @ApiResponse(code = 404, message = "FileAttach by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateLinkType/")
    public Response updateLinkType(
            FileAttachModel fileAttachModel
    ) {
        LOG.info("update...");
        Gson gs = new GsonBuilder()
                .setVersion(fileAttachModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Folder by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            FileAttachService fileAttachService = new FileAttachService();
            List<FileAttach> listFileAttach = fileAttachService.listAllByLinkTypeLinkId("wf", fileAttachModel.getLinkId(), "createdDate", "asc");
            if (listFileAttach != null) {
                listFileAttach.forEach(fileAttach -> {
                    fileAttach.setLinkType("dms");
                    fileAttachService.update(fileAttach);
                });
                status = Response.Status.OK;
                responseData.put("data", null);
                responseData.put("message", "FileAttach updeted by id success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "Method for update FileAttach.",
            notes = "แก้ไขเอกสารแนบ",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttach updeted by id success."),
        @ApiResponse(code = 404, message = "FileAttach by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/update2/{ecms}")
    public Response update_saraban(
            @ApiParam(name = "ecms", value = "ลงรับ ecms", required = true)
            @PathParam("ecms") int ecms,
            FileAttachModel fileAttachModel
    ) {
        LOG.info("update...");
        LOG.info("id..." + fileAttachModel.getId());
        LOG.info("name..." + fileAttachModel.getFileAttachName());
        LOG.info("id..." + fileAttachModel.getId());
        Gson gs = new GsonBuilder()
                .setVersion(fileAttachModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Folder by id not found in the database.");
        responseData.put("errorMessage", "");
        try {

            FileAttachService fileAttachService = new FileAttachService();
            FileAttach fileAttach = fileAttachService.getById(fileAttachModel.getId());

            if (fileAttach != null) {
                if (ecms != 1) {
                    fileAttach.setSecrets(fileAttachModel.getSecrets());
                    fileAttach.setFileAttachName(fileAttachModel.getFileAttachName());
                } else {
                    fileAttach.setSecrets(fileAttachModel.getSecrets());
                    fileAttach.setFileAttachName(fileAttachModel.getFileAttachName());
                    fileAttach.setLinkType(fileAttachModel.getLinkType());
                    fileAttach.setLinkId(fileAttachModel.getLinkId());
                }
                fileAttach = fileAttachService.update(fileAttach);
                status = Response.Status.OK;
                responseData.put("data ", fileAttachService.tranformToModel(fileAttach));
                responseData.put("message", "FileAttach updeted by id success.");
            }

            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

//    //oat-add
//    @ApiOperation(
//            value = "Method for list FileAttach.",
//            notes = "รายการเอกสารประกอบ",
//            responseContainer = "List",
//            response = FileAttachModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "FileAttach list success.")
//        ,
//        @ApiResponse(code = 404, message = "FileAttach list not found in the database.")
//        ,
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @POST
//    @Path(value = "/afterAdd/linkType/{linkType}/linkId/{linkId}")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response listAfterAdd(
//            @BeanParam ListOptionModel listOptionModel,
//            @ApiParam(name = "linkType", value = "module การทำงาน", required = true)
//            @PathParam("linkType") String linkType,
//            @ApiParam(name = "linkId", value = "รหัส record id ของการทำงาน", required = true)
//            @PathParam("linkId") int linkId,
//            List<Integer> secrets
//    ) {
//        LOG.debug("listByLinkTypeLinkId...");
//        LOG.debug("linkType = " + linkType);
//        LOG.debug("linkId = " + linkId);
//        Gson gs = new GsonBuilder()
//                .setVersion(listOptionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        ArrayList<FileAttachModel2> listFileAttachModel = new ArrayList<>();
//        Response.Status status = Response.Status.OK;
//        responseData.put("data", listFileAttachModel);
//        responseData.put("success", false);
//        responseData.put("message", "FileAttach list by LinkType LinkId not found in the database.");
//        try {
//            ParamService paramService = new ParamService();
//            FileAttachService fileAttachService = new FileAttachService();
//
//            List<FileAttach> listTmp = fileAttachService.listAllAfterAdd(linkType, linkId, listOptionModel.getSort(), listOptionModel.getDir());
//            if (!listTmp.isEmpty()) {
//                for (int i = 0; i < listTmp.size(); i++) {
//                    listTmp.get(i).setSecrets(secrets.get(i));
//                    fileAttachService.update(listTmp.get(i));
//                }
//            }
//
//            List<FileAttach> listFileAttach = fileAttachService.listAllByLinkTypeLinkId(linkType, linkId, listOptionModel.getSort(), listOptionModel.getDir());
//            if (!listFileAttach.isEmpty()) {
//                String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_HTTP").getParamValue();
//                FileAttachModel2 fileAttachModel = null;
//                String url = "";
//                String urlNoName = "";
//                String thumbnailUrl = "";
////                ArrayList<FileAttachModel2> listFileAttachModel = new ArrayList<>();
//                for (FileAttach fileAttach : listFileAttach) {
//                    fileAttachModel = fileAttachService.tranformToModel2(fileAttach);
//                    String fileSave = fileAttachService.moveToTempPath(fileAttach.getId());
//                    url = pathDocumentHttp + "Temp/" + fileAttach.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
////                    url = pathDocumentHttp+fileAttachService.buildHtmlPathExt(fileAttach.getId())+fileAttach.getFileAttachType();
//                    thumbnailUrl = fileAttachService.buildThumbhunailUrl(fileAttach.getFileAttachType(), url);
//                    urlNoName = pathDocumentHttp + "Temp/" + fileAttachModel.getLinkType() + "/" + fileAttachService.buildHtmlPathExtNoName(fileAttach.getId());
//                    fileAttachModel.setUrl(url);
//                    fileAttachModel.setThumbnailUrl(thumbnailUrl);
//                    fileAttachModel.setUrlNoName(urlNoName);
//                    listFileAttachModel.add(fileAttachModel);
//                }
//                listFileAttachModel.trimToSize();
//                status = Response.Status.OK;
//                responseData.put("data", listFileAttachModel);
//                responseData.put("message", "");
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
    //oat-add
    @ApiOperation(
            value = "Method for create FileAttach",
            notes = "สร้างข้อมูลเอกสารแนบ",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "FileAttach created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @POST
    @Path(value = "/createList")
    public Response create_saraban(
            @FormDataParam("file") InputStream uploadInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @FormDataParam("version") int version,
            @FormDataParam("linkType") String linkType,
            @FormDataParam("linkId") int linkId,
            @FormDataParam("referenceId") int folderId,
            @FormDataParam("secrets") int contentId
    ) throws UnsupportedEncodingException {
        LOG.debug("create...");
        LOG.debug("fileName = " + fileDetail.getFileName());
        LOG.debug("linkId = " + linkId);
        LOG.debug("folderId = " + folderId);
        LOG.debug("contentId = " + contentId);
        Gson gs = new GsonBuilder()
                .setVersion(version)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("data", null);
        responseData.put("success", false);
        try {
            boolean isAdd = true;
            String tempFile = "";
            String name = new String(fileDetail.getFileName().getBytes("iso-8859-1"), "UTF-8");//newFileName
            int partPos = name.lastIndexOf(".");
            String nameAfterType[] = name.substring(partPos).split(",");// xxx.fileType,secret,refId (xxx.txt,1,144)
            name = name.substring(0, partPos);

            ParamService paramService = new ParamService();
            FileAttachService fileAttachService = new FileAttachService();

            FileAttach fileAttach = new FileAttach();
            fileAttach.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            fileAttach.setFileAttachName(name + nameAfterType[0]);
            fileAttach.setFileAttachType(nameAfterType[0].toUpperCase());
            fileAttach.setLinkType(linkType);
            fileAttach.setLinkId(linkId);
            fileAttach.setReferenceId(0);
            fileAttach.setSecrets(nameAfterType.length == 1 ? 1 : Integer.parseInt(nameAfterType[1]));
            fileAttach = fileAttachService.create(fileAttach);
            if (fileAttach != null) {
                String pathDocumentTemp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
                String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_HTTP").getParamValue();
                LOG.debug("pathDocumentTemp = " + pathDocumentTemp);
                LOG.debug("pathDocumentHttp = " + pathDocumentHttp);
                String fileSave = pathDocumentTemp + fileAttach.getLinkType() + File.separator + fileAttachService.buildFilePathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                LOG.debug("fileSave = " + fileSave);
                tempFile = fileSave;
                fileAttachService.saveFile(uploadInputStream, fileSave);

                fileSave = fileAttachService.moveToRealPath(fileAttach.getId());

                File file = new File(fileSave);
                fileAttach.setFileAttachSize(file.length());
                fileAttach = fileAttachService.update(fileAttach);
                LOG.debug("fileAttach Id = " + fileAttach.getId());

                if (nameAfterType.length == 2 && Integer.parseInt(nameAfterType[2]) > 0) {//upload edit file
                    isAdd = false;

                    FileAttach oldFileAttach = fileAttachService.getByIdNotRemoved(Integer.parseInt(nameAfterType[2]));
                    oldFileAttach.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    oldFileAttach.setReferenceId(fileAttach.getId());
                    oldFileAttach = fileAttachService.update(oldFileAttach);

                    fileAttach.setOrderNo(oldFileAttach.getOrderNo());
                    fileAttach = fileAttachService.update(fileAttach);
                }

                FileAttachModel newFileAttachModel = fileAttachService.tranformToModel(fileAttach);
                String url = pathDocumentHttp + linkType + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                String thumbnailUrl = fileAttachService.buildThumbhunailUrl(fileAttach.getFileAttachType(), url);
                //String resultUrlNoName = pathDocumentHttp + linkId + "/" + fileAttachService.buildHtmlPathExtNoName(fileAttach.getId());
                //LOG.debug("resultUrlNoName = " + resultUrlNoName);
                newFileAttachModel.setUrl(url);
                newFileAttachModel.setThumbnailUrl(thumbnailUrl);
                responseData.put("data", newFileAttachModel);
            }//GHB no ES
//            ///////แคะ text
//            System.out.println("แคะ text");
//            WfContentESModel result;
//            WfSearchService wfSearchService = new WfSearchService();
//            WfContentService contentService = new WfContentService();
//            WfContent content = contentService.getByIdNotRemoved(contentId);
//            String searchId = content.getWfContentStr01();
//            System.out.println("searchId = " + searchId);
//            String fulltext = "";
//
//            String url = "";
//            String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
//            url = pathDocumentHttp + fileAttach.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
//
//            if (fileAttach.getFileAttachType().equalsIgnoreCase(".TXT")) {
//                File file = new File(url);
//                BufferedReader br = new BufferedReader(new FileReader(file));
//                String sCurrentLine;
//
//                while ((sCurrentLine = br.readLine()) != null) {
//                    fulltext = sCurrentLine;
//                }
//            } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOCX")) {
//                System.out.println(" is docX ");
//                InputStream in = new FileInputStream(url);
//                XWPFDocument doc = new XWPFDocument(in);
//                XWPFWordExtractor ex = new XWPFWordExtractor(doc);
//                String text = ex.getText();
//                fulltext = text;
//            } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOC")) {
//                System.out.println(" is doc ");
//                File file = new File(url);
//                NPOIFSFileSystem fs = new NPOIFSFileSystem(file);
//                WordExtractor extractor = new WordExtractor(fs.getRoot());
//                for (String rawText : extractor.getParagraphText()) {
//                    String text = extractor.stripFields(rawText);
//                    fulltext = text;
//                }
//            } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".XLS")) {
//                File file = new File(url);
//                POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
//                HSSFWorkbook wb = new HSSFWorkbook(fs);
//                HSSFSheet sheet = wb.getSheetAt(0);
//                HSSFRow row;
//                HSSFCell cell;
//                int rows; // No of rows
//                rows = sheet.getPhysicalNumberOfRows();
//
//                int cols = 0; // No of columns
//                int tmp = 0;
//
//                for (int i = 0; i < 10 || i < rows; i++) {
//                    row = sheet.getRow(i);
//                    if (row != null) {
//                        tmp = sheet.getRow(i).getPhysicalNumberOfCells();
//                        if (tmp > cols) {
//                            cols = tmp;
//                        }
//                    }
//                }
//                for (int r = 0; r < rows; r++) {
//                    row = sheet.getRow(r);
//                    if (row != null) {
//                        for (int c = 0; c < cols; c++) {
//                            cell = row.getCell((short) c);
//                            if (cell != null) {
//
//                                fulltext = cell.toString();
//                            }
//                        }
//                    }
//                }
//            } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".XLSX")) {
//                //File file = new File(url);
//                InputStream ExcelFileToRead = new FileInputStream(url);
//                XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
//                //XSSFWorkbook test = new XSSFWorkbook();
//                XSSFSheet sheet = wb.getSheetAt(0);
//                XSSFRow row;
//                XSSFCell cell;
//                Iterator rows = sheet.rowIterator();
//
//                while (rows.hasNext()) {
//                    row = (XSSFRow) rows.next();
//                    Iterator cells = row.cellIterator();
//                    while (cells.hasNext()) {
//                        cell = (XSSFCell) cells.next();
//                        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
//                            fulltext = cell.getStringCellValue();
//                        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//
//                        }
//                    }
//                }
//            } else if (fileAttach.getFileAttachType().equalsIgnoreCase(".PDF")) {
//                PDDocument pdDoc = PDDocument.load(new FileInputStream(tempFile));
//                PDFTextStripper pdfStripper = new PDFTextStripper();
//                String parsedText = pdfStripper.getText(pdDoc);
//                fulltext = parsedText;
//                pdDoc.close();
//            }
//
//             if (searchId == null) {
//                System.out.println("create ES");
//                WfContentESModel searchModel = new WfContentESModel();
//                searchModel.setFolderId(folderId);
//                searchModel.setLinkId(linkId);
//
//                FileAttachWfSearchModel newESFileAttach = new FileAttachWfSearchModel();
//                newESFileAttach.setId(fileAttach.getId());
//                newESFileAttach.setFulltext(fulltext);
//                List<FileAttachWfSearchModel> listESFileAttach = new ArrayList();
//                listESFileAttach.add(newESFileAttach);
//                searchModel.setFileAttachs(listESFileAttach);
//
//                result = wfSearchService.addData(searchModel);
//                content.setWfContentStr01(result.getSearchId());
//                contentService.update(content);
//            } else {
//                System.out.println("update ES");
//                WfContentESModel searchModel = wfSearchService.getData(searchId);
//                System.out.println("num old ESFileAttachs = " + searchModel.getFileAttachs().size());
//                List<FileAttachWfSearchModel> listESFileAttach = new ArrayList();
//                for (FileAttachWfSearchModel ESFileAttach : searchModel.getFileAttachs()) {
//                    listESFileAttach.add(ESFileAttach);
//                }
//                if (isAdd) {
//                    System.out.println("add ESFileaAttach");
//                    FileAttachWfSearchModel newESFileAttach = new FileAttachWfSearchModel();
//                    newESFileAttach.setId(fileAttach.getId());
//                    newESFileAttach.setFulltext(fulltext);
//                    listESFileAttach.add(newESFileAttach);
//                } else {
//                    for (FileAttachWfSearchModel ESFileAttach : listESFileAttach) {
//                        if (ESFileAttach.getId() == fileAttach.getId()) {
//                            System.out.println("update ESFileaAttach id = " + ESFileAttach.getId());
//                            ESFileAttach.setFulltext(fulltext);
//                        }
//                    }
//                }
////                for (FileAttachWfSearchModel ESFileAttach : searchModel.getFileAttachs()) {
////                    if (ESFileAttach.getId() == fileAttach.getId()) {
////                        ESFileAttach.setId(fileAttach.getId());
////                        ESFileAttach.setFulltext(fulltext);
////                        listESFileAttach.add(ESFileAttach);
////                    } else {
////                        listESFileAttach.add(ESFileAttach);
////                    }
////                }
//                System.out.println("num new ESFileAttachs = " + listESFileAttach.size());
//                searchModel.setFileAttachs(listESFileAttach);
//                result = wfSearchService.updateData(searchId, searchModel);
//            }
            status = Response.Status.CREATED;
            responseData.put("data", true);
            responseData.put("success", true);
            responseData.put("message", "FileAttach created successfully.");
        } catch (IOException ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "Method for update FileAttach.",
            notes = "แก้ไขเอกสารแนบ",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttach updeted by id success."),
        @ApiResponse(code = 404, message = "FileAttach by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateESFolderId/{contentId}")
    public Response update_myWorkESFolderId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "contentId", value = "รหัสหนังสือ", required = true)
            @PathParam("contentId") int contentId
    ) {
        LOG.info("updateESFolderId...");
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
        responseData.put("message", "WfContent by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfContentService contentService = new WfContentService();
            WfContent content = contentService.getByIdNotRemoved(contentId);
            if (content != null) {
                WfSearchService wfSearchService = new WfSearchService();
                String searchId = content.getWfContentStr01();
                if (searchId != null && !searchId.equalsIgnoreCase("")) {//null is ok -> not upload FA yet
                    WfContentESModel searchModel = wfSearchService.getData(searchId);

                    searchModel.setFolderId(content.getWfContentFolderId());
                    wfSearchService.updateData(searchId, searchModel);
                }
                status = Response.Status.OK;
                responseData.put("data ", true);
                responseData.put("message", "success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            //ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update FileAttach referenceId.",
            notes = "แก้ไขเอกสารแนบ เฉพาะ referenceId",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttach updeted by id success."),
        @ApiResponse(code = 404, message = "FileAttach by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/referenceId/{fileAttachId}/{referenceId}")
    public Response referenceId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "fileAttachId", value = "fileAttachId", required = true)
            @PathParam("fileAttachId") int fileAttachId,
            @ApiParam(name = "referenceId", value = "referenceId", required = true)
            @PathParam("referenceId") int referenceId
    ) {
        LOG.info("update referenceId...");
        LOG.info("fileAttachId = " + fileAttachId);
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
        responseData.put("message", "Folder by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            FileAttachService fileAttachService = new FileAttachService();
            FileAttach fileAttach = fileAttachService.getById(fileAttachId);
            FileAttach fileAttachNew = fileAttachService.getById(referenceId);

            if (fileAttach != null) {
                if (fileAttachNew != null) {
                    fileAttachNew.setOrderNo(fileAttach.getOrderNo());
                    fileAttachService.update(fileAttachNew);
                }

                fileAttach.setReferenceId(referenceId);
//                fileAttach.setReferenceId(fileAttachModel.getReferenceId());
                fileAttach = fileAttachService.update(fileAttach);
                status = Response.Status.OK;
                responseData.put("data", fileAttachService.tranformToModel(fileAttach));
                responseData.put("message", "FileAttach updeted by id success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "Method for update wfe.",
            notes = "แก้ไขเอกสารแนบ (crop) หลังจากส่งหนังสือ",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttach updeted by id success."),
        @ApiResponse(code = 404, message = "FileAttach by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateWfe/{linkId}/{outboxId}")
    public Response update_wfe(
            @ApiParam(name = "linkId", value = "linkId", required = true)
            @PathParam("linkId") int linkId,
            @ApiParam(name = "outboxId", value = "outboxId(0=delete, outboxId=update linkId)", required = true)
            @PathParam("outboxId") int outboxId
    ) {
        LOG.info("updateWfe...");
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "FileAttach not found in the database.");
        responseData.put("errorMessage", "");
        try {

            FileAttachService fileAttachService = new FileAttachService();
            List<FileAttach> listFileAttach = fileAttachService.listAllByLinkTypeLinkId("wfe", linkId, "", "");

            if (!listFileAttach.isEmpty()) {
                if (outboxId == 0) {
                    for (FileAttach fileAttach : listFileAttach) {
//                        fileAttachService.delete(fileAttach);
                        fileAttachService.remove(fileAttach.getId(), -1);
                    }
                } else {
                    for (FileAttach fileAttach : listFileAttach) {
                        fileAttach.setLinkId(outboxId);
                        fileAttachService.update(fileAttach);
                    }
                }
                status = Response.Status.OK;
                responseData.put("data ", null);
                responseData.put("message", "FileAttach updeted by id success.");
            }
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
            value = "Method for check have file .",
            notes = "ตรวจสอบว่าเอกสารแนบเกิดหรือยัง",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttach check by id success."),
        @ApiResponse(code = 404, message = "FileAttach by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/checkHaveAttach/{attachId}")
    public Response checkHaveAttach(
            @ApiParam(name = "attachId", value = "attachId", required = true)
            @PathParam("attachId") int attachId
    ) {
        LOG.info("checkHaveAttach...");
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "FileAttach not found in the database.");
        responseData.put("errorMessage", "");
        try {
            FileAttachService fileAttachService = new FileAttachService();
            ParamService paramService = new ParamService();
            FileAttach fileAttach = fileAttachService.getById(attachId);
            String pathDocument = paramService.getByParamName("PATH_DOCUMENT").getParamValue();
            String filePath = fileAttach.getLinkType() + File.separator + fileAttachService.buildFilePathExt(attachId) + fileAttach.getFileAttachType();
            String pathFile = pathDocument + filePath;
            String data = "false";
            File f = new File(pathFile);

            if (f.exists() && !f.isDirectory()) {
                // found
                data = "true";
            } else {
                // not found
                data = "false";
            }

            status = Response.Status.OK;
            responseData.put("data", data);
            responseData.put("message", "FileAttach success.");

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
            value = "Method for create FileAttach from Base64String V2",
            notes = "สร้างข้อมูลเอกสารแนบ V2 รับ attach เพิ่ม",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "FileAttach created from Base64StringV2 successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @Consumes({MediaType.APPLICATION_JSON})
    @POST
    @Path(value = "/base64V2")
    public Response createBase64V2(
            @BeanParam VersionModel versionModel,
            FileAttachBase64Model fileAttachBase64Model
    ) {
        LOG.debug("createBase64...");
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
        try {
            ParamService paramService = new ParamService();
            FileAttachService fileAttachService = new FileAttachService();

            FileAttach fileAttach = fileAttachService.getById(fileAttachBase64Model.getFileAttachId());

//            System.out.println(" id - "+httpHeaders.getHeaderString("userID"));
//            fileAttach.setCreatedBy(1);
            fileAttach.setUpdatedBy(1);

            fileAttach.setFileAttachName(fileAttachBase64Model.getFileAttachName() + fileAttachBase64Model.getFileAttachType().toLowerCase());

            fileAttach.setFileAttachType(fileAttachBase64Model.getFileAttachType());
            fileAttach.setLinkType(fileAttachBase64Model.getLinkType());
            fileAttach.setLinkId(fileAttachBase64Model.getLinkId());
            fileAttach.setSecrets(fileAttachBase64Model.getSecrets());
            fileAttach.setReferenceId(fileAttachBase64Model.getReferenceId());

            fileAttach = fileAttachService.update(fileAttach);
            if (fileAttach != null) {
                String pathDocumentTemp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
                String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_HTTP").getParamValue();
                LOG.debug("pathDocumentTemp = " + pathDocumentTemp);
                LOG.debug("pathDocumentHttp = " + pathDocumentHttp);
                String fileSave = pathDocumentTemp + fileAttach.getLinkType() + File.separator + fileAttachService.buildFilePathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                LOG.debug("fileSave = " + fileSave);
                fileAttachService.saveFileFromBase64(fileAttachBase64Model.getFileBase64(), fileSave);
                fileSave = fileAttachService.moveToRealPath(fileAttach.getId());
                File file = new File(fileSave);
                fileAttach.setFileAttachSize(file.length());
                fileAttach = fileAttachService.update(fileAttach);

                FileAttachModel fileAttachModel = fileAttachService.tranformToModel(fileAttach);
                String url = pathDocumentHttp + fileAttachModel.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                String thumbnailUrl = fileAttachService.buildThumbhunailUrl(fileAttach.getFileAttachType(), url);
                String urlNoName = pathDocumentHttp + fileAttachModel.getLinkType() + "/" + fileAttachService.buildHtmlPathExtNoName(fileAttach.getId());
                fileAttachModel.setUrl(url);
                fileAttachModel.setThumbnailUrl(thumbnailUrl);
                fileAttachModel.setUrlNoName(urlNoName);
                responseData.put("data", fileAttachModel);
            }
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "FileAttach created from Base64StringV2 successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create FileAttach",
            notes = "สร้างข้อมูลเอกสารแนบ",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "FileAttach created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    @Path(value = "/createEmptyData/{linkType}/{linkId}/{referenceId}")
    public Response createEmptyData(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "linkType", value = "linkType", required = true)
            @PathParam("linkType") String linkType,
            @ApiParam(name = "linkId", value = "linkId", required = true)
            @PathParam("linkId") int linkId,
            @ApiParam(name = "referenceId", value = "referenceId", required = true)
            @PathParam("referenceId") int referenceId
    ) throws UnsupportedEncodingException {
        LOG.debug("createEmptyData...");

        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("data", null);
        responseData.put("success", false);
        FileAttachService fileAttachService = new FileAttachService();
        FileAttach fileAttach = new FileAttach();
        fileAttach.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
        fileAttach.setFileAttachName("TempAttach");
        fileAttach.setFileAttachType(".TTT");
        fileAttach.setLinkType(linkType);
        fileAttach.setLinkId(linkId);
        fileAttach.setReferenceId(referenceId);
        fileAttach.setSecrets(1);
        fileAttach = fileAttachService.create(fileAttach);
        FileAttachModel newFileAttachModel = fileAttachService.tranformToModel(fileAttach);
        responseData.put("data", newFileAttachModel);
        status = Response.Status.CREATED;
        responseData.put("success", true);
        responseData.put("message", "FileAttach created successfully.");
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "Method for replace FileAttach",
            notes = "แทนที่ข้อมูลเอกสารแนบ",
            response = FileAttachModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "FileAttach replaced successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @POST
    @Path(value = "/replaceFile")
    public Response replaceFile(
            @FormDataParam("file") InputStream uploadInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @FormDataParam("version") int version,
            @FormDataParam("id") int id
    ) throws UnsupportedEncodingException {
        LOG.debug("replaceFile...");
        Gson gs = new GsonBuilder()
                .setVersion(version)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("data", null);
        responseData.put("success", false);
        try {
            String fileName = new String(fileDetail.getFileName().getBytes("iso-8859-1"), "UTF-8");

            FileAttachService fileAttachService = new FileAttachService();
            FileAttach fileAttach = fileAttachService.getById(id);
            if (fileAttach != null) {
                fileAttach.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                final String errorMsg = fileAttachService.replaceFile(fileAttach, uploadInputStream, fileName);
                if (errorMsg == null) {
                    responseData.put("data", fileAttachService.tranformToModel2(fileAttach));
                    responseData.put("message", "FileAttach replaced successfully.");
                    status = Response.Status.OK;
                    responseData.put("success", true);
                } else {
                    responseData.put("message", errorMsg);
                    status = Response.Status.NOT_FOUND;
                    throw new Exception(errorMsg);
                }
            }

        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

}
