package com.px.share.service;

import com.aspose.pdf.Document;
import com.aspose.pdf.Font;
import com.aspose.pdf.FontRepository;
import com.aspose.pdf.License;
import com.aspose.pdf.PKCS1;
import com.aspose.pdf.Page;
import com.aspose.pdf.Signature;
import com.aspose.pdf.TextStamp;
import com.aspose.pdf.facades.PdfContentEditor;
import com.aspose.pdf.facades.PdfFileSignature;
import static com.google.common.base.Preconditions.checkNotNull;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.UserProfileService;
import com.px.share.daoimpl.FileAttachDaoImpl;
import com.px.share.entity.FileAttach;
import com.px.share.entity.FileAttachApprove;
import com.px.share.model.FileAttachModel;
import com.px.share.model.FileAttachModel2;
import com.px.share.util.Common;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class FileAttachService implements GenericService<FileAttach, FileAttachModel> {

    private static final Logger LOG = Logger.getLogger(FileAttachService.class.getName());
    private final FileAttachDaoImpl fileAttachDaoImpl;

    public FileAttachService() {
        this.fileAttachDaoImpl = new FileAttachDaoImpl();
    }

    @Override
    public FileAttach create(FileAttach fileAttach) {
        checkNotNull(fileAttach, "fileAttach entity must not be null");
        checkNotNull(fileAttach.getFileAttachName(), "fileAttach name must not be null");
        checkNotNull(fileAttach.getFileAttachType(), "fileAttach type must not be null");
        checkNotNull(fileAttach.getLinkType(), "fileAttach link type must not be null");
        checkNotNull(fileAttach.getLinkId(), "fileAttach link id must not be null");
        checkNotNull(fileAttach.getCreatedBy(), "create by must not be null");
        fileAttach = fileAttachDaoImpl.create(fileAttach);
        if (fileAttach.getOrderNo() == 0) {
            fileAttach.setOrderNo(fileAttach.getId());
            fileAttach = update(fileAttach);
        }
        return fileAttach;
    }

    @Override
    public FileAttach getById(int id) {
        checkNotNull(id, "fileAttach id entity must not be null");
        return fileAttachDaoImpl.getById(id);
    }

    //oat-add
    public FileAttach getByReferenceId(int referenceId) {
        checkNotNull(referenceId, "fileAttach referenceId entity must not be null");
        return fileAttachDaoImpl.getByReferenceId(referenceId);
    }

    @Override
    public FileAttach update(FileAttach fileAttach) {
        checkNotNull(fileAttach, "fileAttach entity must not be null");
        checkNotNull(fileAttach.getFileAttachName(), "fileAttach name must not be null");
        checkNotNull(fileAttach.getFileAttachType(), "fileAttach type must not be null");
        checkNotNull(fileAttach.getLinkType(), "fileAttach link type must not be null");
        checkNotNull(fileAttach.getLinkId(), "fileAttach link id must not be null");
        checkNotNull(fileAttach.getUpdatedBy(), "update by must not be null");
        fileAttach.setUpdatedDate(LocalDateTime.now());
        return fileAttachDaoImpl.update(fileAttach);
    }

    @Override
    public FileAttach remove(int id, int userId) {
        checkNotNull(id, "fileAttach id must not be null");
        FileAttach fileAttach = getById(id);
        checkNotNull(fileAttach, "fileAttach entity not found in database.");
        fileAttach.setRemovedBy(userId);
        fileAttach.setRemovedDate(LocalDateTime.now());
        return fileAttachDaoImpl.update(fileAttach);
    }

    //0at-add
    public void delete(FileAttach fileAttach) {
        checkNotNull(fileAttach, "fileAttach must not be null");
        fileAttachDaoImpl.delete(fileAttach);
    }

    @Override
    public List<FileAttach> listAll(String sort, String dir) {
        return fileAttachDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<FileAttach> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return fileAttachDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public FileAttachModel tranformToModel(FileAttach fileAttach) {
        FileAttachModel fileAttachModel = null;
        if (fileAttach != null) {
//            ParamService paramService = new ParamService();
//            FileAttachService fileAttachService = new FileAttachService();
            fileAttachModel = new FileAttachModel();
            fileAttachModel.setId(fileAttach.getId());
            fileAttachModel.setFileAttachName(fileAttach.getFileAttachName());
            fileAttachModel.setFileAttachSize(fileAttach.getFileAttachSize());
            fileAttachModel.setFileAttachType(fileAttach.getFileAttachType());
            fileAttachModel.setLinkType(fileAttach.getLinkType());
            fileAttachModel.setLinkId(fileAttach.getLinkId());
            fileAttachModel.setReferenceId(fileAttach.getReferenceId());
            fileAttachModel.setSecrets(fileAttach.getSecrets());

//            String url = paramService.getByParamName("PATH_DOCUMENT_HTTP").getParamValue()+fileAttachService.buildHtmlPathExt(fileAttach.getId())+fileAttach.getFileAttachType();
//            fileAttachModel.setUrl(url);
//            fileAttachModel.setThumbnailUrl(null);
        }
        return fileAttachModel;
    }

    public FileAttachModel2 tranformToModel2(FileAttach fileAttach) {
        FileAttachModel2 fileAttachModel = null;
        if (fileAttach != null) {
            UserProfile userProfile = new UserProfile();
            UserProfileService userProfileService = new UserProfileService();
//            ParamService paramService = new ParamService();
//            FileAttachService fileAttachService = new FileAttachService();
            fileAttachModel = new FileAttachModel2();
            fileAttachModel.setId(fileAttach.getId());
            fileAttachModel.setFileAttachName(fileAttach.getFileAttachName());
            fileAttachModel.setFileAttachSize(fileAttach.getFileAttachSize());
            fileAttachModel.setFileAttachType(fileAttach.getFileAttachType());
            fileAttachModel.setLinkType(fileAttach.getLinkType());
            fileAttachModel.setLinkId(fileAttach.getLinkId());
            fileAttachModel.setReferenceId(fileAttach.getReferenceId());
            fileAttachModel.setSecrets(fileAttach.getSecrets());
//            fileAttachModel.setCreatedName(fileAttach.getCreatedBy());
            fileAttachModel.setCreatedDate(Common.localDateTimeToString(fileAttach.getCreatedDate()));

            if (fileAttach.getCreatedBy() != 0 && fileAttach.getCreatedBy() != -1) {
                userProfile = userProfileService.getById(fileAttach.getCreatedBy());
                fileAttachModel.setCreatedName(userProfile.getUserProfileFullName());
                //oat-add
                fileAttachModel.setCreatedBy(fileAttach.getCreatedBy());
            }
        }
        return fileAttachModel;
    }

    @Override
    public int countAll() {
        return fileAttachDaoImpl.countAll();
    }

    public List<FileAttach> listAllByLinkTypeLinkId(String linkType, int linkId, String sort, String dir) {
        return fileAttachDaoImpl.listAllByLinkTypeLinkId(linkType, linkId, sort, dir);
    }

    public List<FileAttach> listAllAfterAdd(String linkType, int linkId, String sort, String dir) {
        return fileAttachDaoImpl.listAllAfterAdd(linkType, linkId, sort, dir);
    }

    public List<FileAttach> listByLinkTypeLinkId(String linkType, int linkId, int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return fileAttachDaoImpl.listByLinkTypeLinkId(linkType, linkId, offset, limit, sort, dir);
    }

    public void saveFile(InputStream uploadedInputStream, String serverPathSaveFile) throws IOException {
        File file = new File(serverPathSaveFile);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        try (OutputStream outpuStream = new FileOutputStream(new File(serverPathSaveFile))) {
            int read = 0;
            byte[] bytes = new byte[2048];
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
            throw new IOException("Failed to save file: " + e.getMessage());
        }
    }

    public void saveFileFromBase64(String base64data, String serverPathSaveFile) throws IOException {
        File file = new File(serverPathSaveFile);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        byte[] dataByteArray = decodeImage(base64data);
        try (OutputStream outpuStream = new FileOutputStream(new File(serverPathSaveFile))) {
            outpuStream.write(dataByteArray);
            outpuStream.flush();
            outpuStream.close();
        } catch (Exception e) {
            throw new IOException("Failed to save file: " + e.getMessage());
        }
    }

    public String buildFilePathExt(int id) {
        checkNotNull(id, "fileAttach id must not be null");
        String result = "" + id;
        if (id < 1000) {
            result = "EXT0" + File.separator + result;
        }
        if (id >= 1000 && id < 1000000) {
            result = "EXT" + result.substring(0, result.length() - 3) + File.separator + result;
        }
        if (id >= 1000000) {
            result = "PXG" + result.substring(0, result.length() - 6) + File.separator + "EXT" + result.substring(0, result.length() - 3) + File.separator + result;
        }
        return result;
    }

    public String buildHtmlPathExt(int id) {
        checkNotNull(id, "fileAttach id must not be null");
        String result = "" + id;
        if (id < 1000) {
            result = "EXT0" + "/" + result;
        }
        if (id >= 1000 && id < 1000000) {
            result = "EXT" + result.substring(0, result.length() - 3) + "/" + result;
        }
        if (id >= 1000000) {
            result = "PXG" + result.substring(0, result.length() - 6) + "/" + "EXT" + result.substring(0, result.length() - 3) + "/" + result;
        }
        return result;
    }

    public String buildHtmlPathExtNoName(int id) {
        checkNotNull(id, "fileAttach id must not be null");
        String result = "" + id;
        if (id < 1000) {
            result = "EXT0/";
        }
        if (id >= 1000 && id < 1000000) {
            result = "EXT" + result.substring(0, result.length() - 3) + "/";
        }
        if (id >= 1000000) {
            result = "PXG" + result.substring(0, result.length() - 6) + "/" + "EXT" + result.substring(0, result.length() - 3) + "/";
        }
        return result;
    }

    public String buildHtmlPathExtNoName2(int id) {
        checkNotNull(id, "fileAttach id must not be null");
        String result = "" + id;
        if (id < 1000) {
            result = "EXT0";
        }
        if (id >= 1000 && id < 1000000) {
            result = "EXT" + result.substring(0, result.length() - 3);
        }
        if (id >= 1000000) {
            result = "PXG" + result.substring(0, result.length() - 6) + "/" + "EXT" + result.substring(0, result.length() - 3);
        }
        return result;
    }
// assets/filetype/

    public String buildThumbhunailUrl(String fileAttachType, String fileAttachUrl) {
        String thumbhunailUrl = fileAttachUrl;
        if (fileAttachType.equalsIgnoreCase(".DOCX")) {
            thumbhunailUrl = "assets/filetype/doc.svg";
        } else if (fileAttachType.equalsIgnoreCase(".DOC")) {
            thumbhunailUrl = "assets/filetype/docx.svg";
        } else if (fileAttachType.equalsIgnoreCase(".XLSX")) {
            thumbhunailUrl = "assets/filetype/xlsx.svg";
        } else if (fileAttachType.equalsIgnoreCase(".XLS")) {
            thumbhunailUrl = "assets/filetype/xls.svg";
        } else if (fileAttachType.equalsIgnoreCase(".PPTX")) {
            thumbhunailUrl = "assets/filetype/pptx.svg";
        } else if (fileAttachType.equalsIgnoreCase(".PPT")) {
            thumbhunailUrl = "assets/filetype/ppt.svg";
        } else if (fileAttachType.equalsIgnoreCase(".PDF")) {
            thumbhunailUrl = "assets/filetype/pdf.svg";
        } else if (fileAttachType.equalsIgnoreCase(".JPEG")) {
            thumbhunailUrl = "assets/filetype/jpeg.svg";
        } else if (fileAttachType.equalsIgnoreCase(".JPG") || fileAttachType.equalsIgnoreCase(".BMP")) {
            thumbhunailUrl = "assets/filetype/jpg.svg";
        } else if (fileAttachType.equalsIgnoreCase(".PNG")) {
            thumbhunailUrl = "assets/filetype/png.svg";
        } else if (fileAttachType.equalsIgnoreCase(".SVG")) {
            thumbhunailUrl = "assets/icons/icon-image.svg";
        } else if (fileAttachType.equalsIgnoreCase(".TIF")) {
            thumbhunailUrl = "assets/filetype/tif.svg";
        } else if (fileAttachType.equalsIgnoreCase(".TIFF")) {
            thumbhunailUrl = "assets/filetype/tiff.svg";
        } else {
            thumbhunailUrl = "assets/icons/icon-unknown.svg";
        }
        return thumbhunailUrl;

//        else if (fileAttachType.equalsIgnoreCase(".XLSX") || fileAttachType.equalsIgnoreCase(".XLS")) {
//            thumbhunailUrl = "assets/icons/icon-excel.svg";
//        } else if (fileAttachType.equalsIgnoreCase(".PPTX") || fileAttachType.equalsIgnoreCase(".PPT")) {
//            thumbhunailUrl = "assets/icons/icon-powerpoint.svg";
//        } else if (fileAttachType.equalsIgnoreCase(".PDF")) {
//            thumbhunailUrl = "assets/icons/icon-text.svg";
//        } else if (fileAttachType.equalsIgnoreCase(".JPG") || fileAttachType.equalsIgnoreCase(".JPEG") || fileAttachType.equalsIgnoreCase(".PNG") || fileAttachType.equalsIgnoreCase(".SVG")) {
//            thumbhunailUrl = "assets/icons/icon-image.svg";
//        } else if (fileAttachType.equalsIgnoreCase(".TIF") || fileAttachType.equalsIgnoreCase(".TIFF")) {
//            thumbhunailUrl = "assets/icons/icon-tif.svg";
//        } else {
//            thumbhunailUrl = "assets/icons/icon-unknown.svg";
//        }
//        return thumbhunailUrl;
    }

    @Override
    public List<FileAttach> search(MultivaluedMap<String, String> queryFileAttachs, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryFileAttachs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String moveToRealPath(int fileAttachId) {
        checkNotNull(fileAttachId, "fileAttach id must not be null");
        FileAttach fileAttach = fileAttachDaoImpl.getById(fileAttachId);
        checkNotNull(fileAttach, "Get fileAttach by id not found in the database.");

        ParamService paramService = new ParamService();
        String encodeFile = paramService.getByParamName("ENCODE_FILE").getParamValue();
        String pathDocumentTemp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
        String pathDocument = paramService.getByParamName("PATH_DOCUMENT").getParamValue();
        String filePath = fileAttach.getLinkType() + File.separator + buildFilePathExt(fileAttachId) + fileAttach.getFileAttachType();
        String scrFile = pathDocumentTemp + filePath;
        String desFile = pathDocument + filePath;

        if (encodeFile.equalsIgnoreCase("Y")) {
            try {
                Common.encodeFile(scrFile, desFile);
            } catch (IOException ex) {
                LOG.error("encodeFile error.");
            }
        } else {
            String tempPath = desFile.substring(0, desFile.lastIndexOf(File.separator));
            File f = new File(tempPath);
            if (!f.exists()) {
                f.mkdirs();
            }
            File fileA = new File(scrFile);
            File fileB = new File(desFile);
            try {
                Files.move(fileA.toPath(), fileB.toPath(), REPLACE_EXISTING);
            } catch (IOException ex) {
                LOG.error("Move file error.");
            }
        }
        return desFile;
    }

    public String moveToTempPath(int fileAttachId) {
        checkNotNull(fileAttachId, "fileAttach id must not be null");
        FileAttach fileAttach = fileAttachDaoImpl.getById(fileAttachId);
        checkNotNull(fileAttach, "Get fileAttach by id not found in the database.");

        ParamService paramService = new ParamService();
        String encodeFile = paramService.getByParamName("ENCODE_FILE").getParamValue();
        String pathDocumentTemp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
        String pathDocument = paramService.getByParamName("PATH_DOCUMENT").getParamValue();
        String filePath = fileAttach.getLinkType() + File.separator + buildFilePathExt(fileAttachId) + fileAttach.getFileAttachType();
        String scrFile = pathDocument + filePath;
        String desFile = pathDocumentTemp + filePath;
        File f = new File(scrFile);
        if (!f.exists()) {
            desFile = "";
        }
        final boolean flagCa = (fileAttach.getFlagCa() != null && fileAttach.getFlagCa().equalsIgnoreCase("Y"));

        if (encodeFile.equalsIgnoreCase("Y") && !flagCa) {
            try {
                Common.decodeFile(scrFile, desFile);
            } catch (Exception ex) {
                LOG.error("encodeFile error.");
            }
        } else {
            String tempPath = desFile.substring(0, desFile.lastIndexOf(File.separator));
            f = new File(tempPath);
            if (!f.exists()) {
                f.mkdirs();
            }
            File fileA = new File(scrFile);
            File fileB = new File(desFile);
            try {
                Files.copy(fileA.toPath(), fileB.toPath(), REPLACE_EXISTING);
            } catch (IOException ex) {
                LOG.error("Move file error.");
            }
        }
        return desFile;
    }

    public String getRealPathFile(int fileAttachId) {
//        String result = "";
        checkNotNull(fileAttachId, "fileAttach id must not be null");
        FileAttach fileAttach = fileAttachDaoImpl.getById(fileAttachId);
        checkNotNull(fileAttach, "Get fileAttach by id not found in the database.");
        ParamService paramService = new ParamService();
        String pathDocument = paramService.getByParamName("PATH_DOCUMENT").getParamValue();
        String filePath = fileAttach.getLinkType() + File.separator + buildFilePathExt(fileAttachId) + fileAttach.getFileAttachType();
        return pathDocument + filePath;
    }

    @Override
    public FileAttach getByIdNotRemoved(int id) {
        checkNotNull(id, "FileAttach id entity must not be null");
        return fileAttachDaoImpl.getByIdNotRemoved(id);
    }

    public String buildWaterMark(String pathSourceFile, String pathDestFile, String pathWaterMarkFile) {
        String resultWaterMarkPath = "";
        File f = new File(pathSourceFile);
        if (f.exists()) {
            try {
                PdfReader reader = new PdfReader(pathSourceFile);
                int pages = reader.getNumberOfPages();
                PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pathDestFile));
                Image img = Image.getInstance(pathWaterMarkFile);
                float w = img.getScaledWidth();
                float h = img.getScaledHeight();
                PdfGState gs1 = new PdfGState();
                gs1.setFillOpacity(0.4f);
                PdfContentByte over;
                Rectangle pagesize;
                float x, y;
                for (int i = 1; i <= pages; i++) {
                    pagesize = reader.getPageSizeWithRotation(i);
                    x = (pagesize.getLeft() + pagesize.getRight()) / 2;
                    y = (pagesize.getTop() + pagesize.getBottom()) / 2;
                    over = stamper.getOverContent(i);
                    over.saveState();
                    over.setGState(gs1);
//                    if (i % 2 == 1)
//                        ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 0);
//                    else
                    over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
                    over.restoreState();
                }
                stamper.close();
                reader.close();
//                PDDocument realDoc = PDDocument.load(f);
//                HashMap<Integer, String> overlayGuide = new HashMap<>();
//        for(int i=0; i<realDoc.getNumberOfPages(); i++){
//            overlayGuide.put(i+1, "watermark.pdf");
//            //watermark.pdf is the document which is a one page PDF with your watermark image in it. 
//            //Notice here, you can skip pages from being watermarked.
//        }
//        Overlay overlay = new Overlay();
//        overlay.setInputPDF(realDoc);
//        overlay.setOutputFile("final.pdf");
//        overlay.setOverlayPosition(Overlay.Position.BACKGROUND);
//        overlay.overlay(overlayGuide);

            } catch (Exception ex) {
                LOG.error("Source File not found.");
            }
        }
        return resultWaterMarkPath;
    }

    /**
     * Encodes the byte array into base64 string
     *
     * @param imageByteArray - byte array
     * @return String a {@link java.lang.String}
     */
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }

    /**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }

    public int countAllByLinkTypeLinkId(String linkType, int linkId) {
        return fileAttachDaoImpl.countAllByLinkTypeLinkId(linkType, linkId);
    }

    public boolean checkHaveFile(int id) {
        return true;
    }

    public String cert(FileAttachApprove fielAttachApprove, String caPassword, String issuedBy, String signedBy) {
        final FileAttach fileAttach = fielAttachApprove.getFileAttach();
        final String encodeFile;
        final String pathDocumentTemp;
        final String pathDocument;
        final String pathCa;
        final String srcFilePath;
        final String tmpFilePath;
        final String pfxPath;
        final boolean flagCa = (fileAttach.getFlagCa() != null && fileAttach.getFlagCa().equalsIgnoreCase("Y"));
        try {
            ParamService paramService = new ParamService();
            encodeFile = paramService.getByParamName("ENCODE_FILE").getParamValue();
            pathDocumentTemp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
            pathDocument = paramService.getByParamName("PATH_DOCUMENT").getParamValue();
            pathCa = paramService.getByParamName("PATH_CA").getParamValue();

            final String fileAttachType = fileAttach.getFileAttachType();
            final String filePath = fileAttach.getLinkType() + File.separator + buildFilePathExt(fileAttach.getId()) + fileAttachType;
            final String filePathCa = "ca" + File.separator + fileAttach.getId() + "_" + fielAttachApprove.getUserProfile().getId() + fileAttachType;
            srcFilePath = pathDocument + filePath;//Document/dms/EXTxxx/xxx.PDF
            tmpFilePath = pathDocumentTemp + filePathCa;//Document/Temp/ca/xxx_1.PDF
            pfxPath = pathCa + fielAttachApprove.getUserProfile().getId() + ".pfx";
//            System.out.println("xxxxxxxxxx srcFilePath: " + srcFilePath);
//            System.out.println("xxxxxxxxxx tmpFilePath: " + tmpFilePath);
//            System.out.println("xxxxxxxxxx dstFilePath: " + dstFilePath);
//            System.out.println("xxxxxxxxxx pfx: " + pfxPath);
            LOG.info("xxxxxxxxxx srcFilePath: " + srcFilePath);
            LOG.info("xxxxxxxxxx tmpFilePath: " + tmpFilePath);
            LOG.info("xxxxxxxxxx pfx: " + pfxPath);
        } catch (Exception ex) {
            LOG.error("cert().getParam()", ex);
            return ex.getMessage();
        }

        File srcFile = new File(srcFilePath);
        if (!srcFile.exists()) {
            return "file not found!!!";
        }
        File tmpFile = new File(tmpFilePath);
        if (tmpFile.exists()) {
            tmpFile.delete();
        }

        if (encodeFile.equalsIgnoreCase("Y") && !flagCa) {
            try {
                Common.decodeFile(srcFilePath, tmpFilePath);
//                System.out.println("xxxxxxxxxx decode done.");
                LOG.info("xxxxxxxxxx decode done.");
            } catch (Exception ex) {
                LOG.error("cert().decodeFile()", ex);
                return "file decode error!!!";
            }
        } else {
            String p = tmpFilePath.substring(0, tmpFilePath.lastIndexOf(File.separator));
            File f = new File(p);
            if (!f.exists()) {
                f.mkdirs();
            }

            try {
                Files.copy(srcFile.toPath(), tmpFile.toPath(), REPLACE_EXISTING);
//                System.out.println("xxxxxxxxxx copy done.");
                LOG.info("xxxxxxxxxx copy done.");
            } catch (IOException ex) {
                LOG.error("cert().copy()", ex);
                return "file copy error!!!";
            }
        }

//        Document document;
//        try {
//            document = new Document(tmpFilePath);
////            System.out.println("xxxxxxxxxx document: " + document.getFileName());
//            LOG.info("xxxxxxxxxx document: " + document.getFileName());
//        } catch (Exception ex) {
//            LOG.error("cert().new Document()", ex);
//            return "new Document error!!!";
//        }
        License license = new License();
        try {
            license.setLicense(pathCa + "Aspose.Total.Java.lic");
//            System.out.println("xxxxxxxxxx license: " + license.toString());
        } catch (Exception ex) {
            LOG.error("cert().setLicense()", ex);
            return "license not found!!!";
        }

//        Font font;
//        try {
//            font = FontRepository.openFont(pathCa + "THSarabun.ttf");
////            System.out.println("xxxxxxxxxx font: " + font.getFontName());
//        } catch (Exception ex) {
//            LOG.error("cert().openFont()", ex);
//            return "font not found!!!";
//        }
        Signature pkcs = null;
        PdfFileSignature signature = null;
        try {
            pkcs = new PKCS1(pfxPath, caPassword);//486185
            signature = new PdfFileSignature();
            signature.bindPdf(tmpFilePath);
            signature.sign(1, false, new java.awt.Rectangle(300, 100, 400, 200), pkcs);
            signature.save(tmpFilePath);
            signature.close();
            pkcs.close();
//            System.out.println("xxxxxxxxxx sign done.");
            LOG.info("xxxxxxxxxx sign done.");
        } catch (Exception ex) {
            LOG.error("cert().sign", ex);
            signature.close();
            pkcs.close();
            tmpFile.delete();
            return "sign document error!!!";
        }

        TextStamp tsIssuedBy = null;
        if (flagCa) {
            PdfContentEditor contentEditor = null;
            try {
                contentEditor = new PdfContentEditor();
                contentEditor.bindPdf(tmpFilePath);
                for (int i = 1; i <= contentEditor.getDocument().getPages().size(); i++) {
                    contentEditor.deleteStamp(i, new int[]{1});
                }
                contentEditor.save(tmpFilePath);
                contentEditor.close();
//                System.out.println("xxxxxxxxxx deleteStamp done.");
                LOG.info("xxxxxxxxxx deleteStamp done.");
            } catch (Exception ex) {
                LOG.error("cert().deleteStampById()", ex);
                contentEditor.close();
                tmpFile.delete();
                return "delete stamp erro!!!.";
            }
        } else {
            tsIssuedBy = new TextStamp(issuedBy);
//            tsIssuedBy.getTextState().setFont(font);
            tsIssuedBy.getTextState().setFontSize(12.0F);
            tsIssuedBy.setLeftMargin(32);
            tsIssuedBy.setBottomMargin(29);
            tsIssuedBy.setHorizontalAlignment(com.aspose.pdf.HorizontalAlignment.Left);
            tsIssuedBy.setVerticalAlignment(com.aspose.pdf.VerticalAlignment.Bottom);
            tsIssuedBy.getTextState().setFontStyle(com.aspose.pdf.FontStyles.Regular);
            tsIssuedBy.getTextState().setForegroundColor(com.aspose.pdf.Color.getBlue());
//            System.out.println("xxxxxxxxxx issuedBy: " + issuedBy);
        }

        TextStamp tsSignedBy = new TextStamp(signedBy);
//        tsSignedBy.getTextState().setFont(font);
        tsSignedBy.getTextState().setFontSize(12.0F);
        tsSignedBy.setLeftMargin(32);
        tsSignedBy.setBottomMargin(15);
        tsSignedBy.setHorizontalAlignment(com.aspose.pdf.HorizontalAlignment.Left);
        tsSignedBy.setVerticalAlignment(com.aspose.pdf.VerticalAlignment.Bottom);
        tsSignedBy.getTextState().setFontStyle(com.aspose.pdf.FontStyles.Regular);
        tsSignedBy.getTextState().setForegroundColor(com.aspose.pdf.Color.getBlue());
//        System.out.println("xxxxxxxxxx signedBy: " + signedBy);

        Document document = null;
        try {
            document = new Document(tmpFilePath);
            for (int i = 1; i <= document.getPages().size(); i++) {
                Page page = document.getPages().get_Item(i);
                if (!flagCa) {
                    page.addStamp(tsIssuedBy);
                }
                page.addStamp(tsSignedBy);
            }
            document.save(tmpFilePath);
            document.close();
//            System.out.println("xxxxxxxxxx addStamp done.");
            LOG.info("xxxxxxxxxx addStamp done.");
        } catch (Exception ex) {
            LOG.error("cert().addStamp()", ex);
            document.close();
            tmpFile.delete();
            return "add stamp error!!!";
        }

        try {
            Files.move(tmpFile.toPath(), srcFile.toPath(), REPLACE_EXISTING);
        } catch (IOException ex) {
            LOG.error("cert().move()", ex);
            tmpFile.delete();
            return "file move error!!!";
        }

        try {
            fileAttach.setFileAttachSize(srcFile.length());
            fileAttach.setUpdatedBy(fielAttachApprove.getUserProfile().getId());
            fileAttach.setFlagCa("Y");
            update(fileAttach);
        } catch (Exception ex) {
            LOG.error("cert().sign", ex);
            tmpFile.delete();
            return "update FileAttach error!!!";
        }

        tmpFile.delete();
//        System.out.println("xxxxxxxxxx done.");
        LOG.info("xxxxxxxxxx done.");
        return null;
    }

}
