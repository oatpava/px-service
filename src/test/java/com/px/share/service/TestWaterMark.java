/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.share.service;

import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.docx4j.TextUtils;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.ImagePngPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart.AddPartBehaviour;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.Hdr;
import org.docx4j.wml.SectPr;
import static org.elasticsearch.common.io.FileSystemUtils.files;

/**
 *
 * @author OPAS
 */
public class TestWaterMark {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InvalidFormatException, Docx4JException, Exception {
//        String pathSourceFile = "C:\\px8\\Data\\Document\\Temp\\dms\\EXT0\\2609.PDF";
//        String pathDestFile = "C:\\px8\\Data\\Document\\Temp\\dms\\EXT0\\2609_w.PDF";
//        String pathWaterMarkFile = "C:\\px8\\Data\\Document\\watermark\\watermark-cc.png";
//        String resultWaterMarkPath = "";
//        File f = new File(pathSourceFile);
//        if(f.exists()){
//            try {
//                PdfReader reader = new PdfReader(pathSourceFile);
//                int pages = reader.getNumberOfPages();
//                PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pathDestFile));
//                Image img = Image.getInstance(pathWaterMarkFile);
//                float w = img.getScaledWidth();
//                float h = img.getScaledHeight();
//                PdfGState gs1 = new PdfGState();
//                gs1.setFillOpacity(0.5f);
//                PdfContentByte over;
//                Rectangle pagesize;
//                float x, y;
//                for (int i = 1; i <= pages; i++) {
//                    pagesize = reader.getPageSizeWithRotation(i);
//                    x = (pagesize.getLeft() + pagesize.getRight()) / 2;
//                    y = (pagesize.getTop() + pagesize.getBottom()) / 2;
//                    over = stamper.getOverContent(i);
//                    over.saveState();
//                    over.setGState(gs1);
//                    over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
//                    over.restoreState();
//                }
//                stamper.close();
//                reader.close();                
//            } catch (Exception ex) {
//                System.out.println(ex);
//            }
////               LOG.error("Source File not found.");
//            }

        String pathSourceFile = "C:\\px8\\Data\\Document\\Temp\\dms\\EXT0\\1201.DOCX";
//        String pathSourceFile2 = "C:\\px8\\Data\\Document\\Temp\\dms\\EXT0\\1201.DOCX";
        String pathDestFile = "C:\\px8\\Data\\Document\\Temp\\dms\\EXT0\\1200_w.DOCX";
        String pathWaterMarkFile = "C:\\px8\\Data\\Document\\watermark\\watermark-cc.png";
        String resultWaterMarkPath = "";
        
        
        WordprocessingMLPackage wordMLPackage;
        wordMLPackage = WordprocessingMLPackage.createPackage();
        wordMLPackage.getMainDocumentPart().getContents().getBody().setSectPr(createSectPr(wordMLPackage));
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
			
		   	// Pretty print the main document part
			System.out.println(
					XmlUtils.marshaltoString(documentPart.getJaxbElement(), true, true) );
        File f = new File(pathDestFile);
        wordMLPackage.save(f);

    }
    private static SectPr createSectPr(WordprocessingMLPackage wordMLPackage) throws Exception {
		
		String openXML = "<w:sectPr xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\">"
                    // Word adds the background image in each of 3 header parts
	            + "<w:headerReference r:id=\"" + createHeaderPart("even",wordMLPackage).getId() + "\" w:type=\"even\"/>"
	            + "<w:headerReference r:id=\"" + createHeaderPart("default",wordMLPackage).getId() + "\" w:type=\"default\"/>"
	            + "<w:headerReference r:id=\"" + createHeaderPart("first",wordMLPackage).getId() + "\" w:type=\"first\"/>"	            
	            // Word adds empty footer parts when you create a watermark, but its not necessary	            
	            + "<w:pgSz w:h=\"15840\" w:w=\"12240\"/>"
	            + "<w:pgMar w:bottom=\"1440\" w:footer=\"708\" w:gutter=\"0\" w:header=\"708\" w:left=\"1440\" w:right=\"1440\" w:top=\"1440\"/>"
	            + "<w:cols w:space=\"708\"/>"
	            + "<w:docGrid w:linePitch=\"360\"/>"
	        +"</w:sectPr>";	 
		return (SectPr)XmlUtils.unmarshalString(openXML);	
	}

	private static Relationship createHeaderPart(String nameSuffix,WordprocessingMLPackage wordMLPackage) throws Exception {
		HeaderPart headerPart = new HeaderPart(new PartName("/word/header-" + nameSuffix + ".xml"));
		Relationship rel =  wordMLPackage.getMainDocumentPart().addTargetPart(headerPart);
		setHdr( headerPart);
		return rel;
	}
	
	private static void setHdr(HeaderPart headerPart) throws Exception {
        byte[] image = getImage();
        ImagePngPart imagePart = new ImagePngPart(new PartName("/media/background.png"));
        imagePart.setBinaryData(image);
        Relationship rel = headerPart.addTargetPart(imagePart, AddPartBehaviour.REUSE_EXISTING); // the one image is shared by the 3 header parts
        String openXML = "<w:hdr mc:Ignorable=\"w14 wp14\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\">"
                + "<w:p>"
                + "<w:pPr>"
                + "<w:pStyle w:val=\"Header\"/>"
                + "</w:pPr>"
                + "<w:r>"
                + "<w:rPr>"
                + "<w:noProof/>"
                + "</w:rPr>"
                + "<w:pict>"
                + "<v:shapetype coordsize=\"21600,21600\" filled=\"f\" id=\"_x0000_t75\" o:preferrelative=\"t\" o:spt=\"75\" path=\"m@4@5l@4@11@9@11@9@5xe\" stroked=\"f\">"
                + "<v:stroke joinstyle=\"miter\"/>"
                + "<v:formulas>"
                + "<v:f eqn=\"if lineDrawn pixelLineWidth 0\"/>"
                + "<v:f eqn=\"sum @0 1 0\"/>"
                + "<v:f eqn=\"sum 0 0 @1\"/>"
                + "<v:f eqn=\"prod @2 1 2\"/>"
                + "<v:f eqn=\"prod @3 21600 pixelWidth\"/>"
                + "<v:f eqn=\"prod @3 21600 pixelHeight\"/>"
                + "<v:f eqn=\"sum @0 0 1\"/>"
                + "<v:f eqn=\"prod @6 1 2\"/>"
                + "<v:f eqn=\"prod @7 21600 pixelWidth\"/>"
                + "<v:f eqn=\"sum @8 21600 0\"/>"
                + "<v:f eqn=\"prod @7 21600 pixelHeight\"/>"
                + "<v:f eqn=\"sum @10 21600 0\"/>"
                + "</v:formulas>"
                + "<v:path gradientshapeok=\"t\" o:connecttype=\"rect\" o:extrusionok=\"f\"/>"
                + "<o:lock aspectratio=\"t\" v:ext=\"edit\"/>"
                + "</v:shapetype>"
                + "<v:shape id=\"WordPictureWatermark835936646\" o:allowincell=\"f\" o:spid=\"_x0000_s2050\" style=\"position:absolute;margin-left:0;margin-top:0;width:306pt;height:362pt;z-index:-251657216;mso-position-horizontal:center;mso-position-horizontal-relative:margin;mso-position-vertical:center;mso-position-vertical-relative:margin\" type=\"#_x0000_t75\">"
                + "<v:imagedata blacklevel=\"22938f\" gain=\"19661f\" o:title=\"docx4j-logo\" r:id=\"" + rel.getId() + "\"/>"
                + "</v:shape>"
                + "</w:pict>"
                + "</w:r>"
                + "</w:p>"
                + "</w:hdr>";
        Hdr hdr = (Hdr) XmlUtils.unmarshalString(openXML);
        headerPart.setJaxbElement(hdr);

    }
    
    private static byte[] getImage() throws IOException {
        String pathWaterMarkFile = "C:\\px8\\Data\\Document\\watermark\\watermark-cc.png";
        File imageFile = new File(pathWaterMarkFile);
        // Our utility method wants that as a byte array
        java.io.InputStream is = new java.io.FileInputStream(imageFile);
        long length = imageFile.length();
        // You cannot create an array using a long type.
        // It needs to be an int type.
        if (length > Integer.MAX_VALUE) {
            System.out.println("File too large!!");
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            System.out.println("Could not completely read file " + imageFile.getName());
        }
        is.close();

        return bytes;
    }
    
}
