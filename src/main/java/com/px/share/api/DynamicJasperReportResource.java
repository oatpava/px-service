package com.px.share.api;

import com.px.share.service.DynamicJasperReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.apache.log4j.Logger;

/**
 *
 * @author Peach
 */
@Api(value = "DynamicJasperReport ตัวอย่างรายงาน")
@Path("v1/dynamicReports")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class DynamicJasperReportResource {
    private static final Logger LOG = Logger.getLogger(DynamicJasperReportResource.class);
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Example for DynamicJasper Report", 
        notes = "ตัวอย่างรายงานการออกรายงานด้วย DynamicJasper", 
        response = ByteArrayOutputStream.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Download File success." ),
        @ApiResponse( code = 404, message = "File not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/runReport/{exportType}")
    public Response runReport(
        //@BeanParam VersionModel versionModel,
        @ApiParam(name = "exportType", value = "ประเภทของไฟล์รายงาน", required = false) 
        @PathParam("exportType") String exportType
    ) {
        LOG.debug("testRunReport...");
        LOG.debug("exportType = "+exportType);
//        Gson gs = new GsonBuilder()
//                .setVersion(versionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();       
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "FileAttach by id not found in the database.");
        StreamingOutput fileStream = null;
        String fileName = "report.pdf";
   
        if(exportType.equals("XLS")) {
            fileName = "report.xls";
        } else if(exportType.equals("XLSX")) {
            fileName = "report.xlsx";
        } else if(exportType.equals("DOCX")) {
            fileName = "report.docx";
        } else {
            fileName = "report.pdf";
        }

        try{
            DynamicJasperReportService dynamicJasperReportService = new DynamicJasperReportService();
            fileStream =  new StreamingOutput(){
                @Override
                public void write(java.io.OutputStream output) throws IOException, WebApplicationException{
                    try
                    {
                        ByteArrayOutputStream baos = dynamicJasperReportService.example(exportType);
                        baos.writeTo(output);
                        output.flush();
                    } 
                    catch (Exception e) 
                    {
                        throw new WebApplicationException("Report Not Found !!");
                    }
                }
            };

            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }      
        return Response.ok(fileStream,MediaType.APPLICATION_OCTET_STREAM).encoding("utf-8")
                .header("Content-Disposition","attachment; filename*=UTF-8' '"+fileName)
//                .setCharacterEncoding("utf-8")
                .build();
//        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
}
