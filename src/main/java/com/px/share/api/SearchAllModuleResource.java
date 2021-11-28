//package com.px.share.api;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.px.share.model.SearchAllModuleModel;
//import com.px.share.model.VersionModel;
//import com.px.share.service.SearchAllModuleService;
//import static com.px.share.util.Common.dateThaiToEng;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import javax.ws.rs.BeanParam;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.DefaultValue;
//import javax.ws.rs.FormParam;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
//import org.apache.log4j.Logger;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.update.UpdateResponse;
//
///**
// *
// * @author Peach
// */
//@Api(value = "SearchAllModule ค้นหาจากทุก module")
//@Path("v1/searchAll")
//@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
//public class SearchAllModuleResource {
//    private static final Logger LOG = Logger.getLogger(SearchAllModuleResource.class.getName());
//    
//    @ApiOperation(
//        value = "สร้างข้อมูลการค้นหา", 
//        notes = "",
//        response = IndexResponse.class
//    )
//    @ApiResponses( {
//        @ApiResponse( code = 201, message = "SearchAllModule created successfully.", response = SearchAllModuleModel.class ),
//        @ApiResponse( code = 500, message = "Internal Server Error!" )
//    } )
//    @POST
//    @Consumes({ MediaType.APPLICATION_JSON })
//    public IndexResponse create(
//        SearchAllModuleModel searchAllModuleModel
//    ) {
//        LOG.debug("create...");
//        Gson gs = new GsonBuilder()
//                .setVersion(searchAllModuleModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();        
//        IndexResponse result;
//        try{
//            SearchAllModuleService searchAllModuleService = new SearchAllModuleService();
//            result = searchAllModuleService.addData(searchAllModuleModel.getModuleId()
//                                                    , searchAllModuleModel.getLinkType()
//                                                    , searchAllModuleModel.getLinkId()
//                                                    , searchAllModuleModel.getTitle()
//                                                    , searchAllModuleModel.getContent()
//                                                    , dateThaiToEng(searchAllModuleModel.getContentDate())
//                                                    , searchAllModuleModel.getSearchContent());
//            
//        }catch(Exception ex){
//            LOG.error("Exception = "+ex.getMessage());
//            result = null;
//        }
//        return result;
//    }
//    
//    @ApiOperation(
//        value = "Method for get SearchAllModule by id", 
//        notes = "ขอข้อมูลการค้นหา ด้วย รหัสการค้นหา", 
//        response = GetResponse.class
//    )
//    @ApiResponses( {
//        @ApiResponse( code = 200, message = "SearchAllModule by id success." ),
//        @ApiResponse( code = 404, message = "SearchAllModule by id not found in the database." ),
//        @ApiResponse( code = 500, message = "Internal Server Error!" )
//    } )
//    @GET
//    @Consumes({ MediaType.APPLICATION_JSON })
//    @Path(value = "/{id}")
//    public GetResponse getById(
//        @BeanParam VersionModel versionModel,
//        @ApiParam(name = "id", value = "รหัสการค้นหา", required = true) 
//        @PathParam("id") String id
//    ) {
//        LOG.debug("getById...");
//        LOG.debug("id = "+id);
//        Gson gs = new GsonBuilder()
//                .setVersion(versionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();        
//        GetResponse result;
//        try{
//            SearchAllModuleService searchAllModuleService = new SearchAllModuleService();
//            result = searchAllModuleService.getData(id);
//        }catch(Exception ex){
//            LOG.error("Exception = "+ex.getMessage());
//            result = null;
//        }
//        return result;
//    }
//    
//    @ApiOperation(
//        value = "Method for update SearchAllModule.", 
//        notes = "แก้ไขข้อมูลการค้นหา", 
//        response = UpdateResponse.class
//    )
//    @ApiResponses( {
//        @ApiResponse( code = 200, message = "SearchAllModule updeted by id success." )
//        ,@ApiResponse( code = 404, message = "SearchAllModule by id not found in the database." )
//        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
//    } )
//    @PUT
//    @Consumes({ MediaType.APPLICATION_JSON })
//    @Path(value = "/{id}")
//    public UpdateResponse update(
//        @ApiParam(name = "id", value = "รหัสการค้นหา", required = true) 
//        @PathParam("id") int id, 
//        SearchAllModuleModel searchAllModuleModel
//    ) {
//        LOG.debug("update...");
//        LOG.debug("id = "+id);
//        Gson gs = new GsonBuilder()
//                .setVersion(searchAllModuleModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();        
//        UpdateResponse result;
//        try{
//            SearchAllModuleService searchAllModuleService = new SearchAllModuleService();
//            result = searchAllModuleService.updateData(searchAllModuleModel.getId()
//                                                    , searchAllModuleModel.getModuleId()
//                                                    , searchAllModuleModel.getLinkType()
//                                                    , searchAllModuleModel.getLinkId()
//                                                    , searchAllModuleModel.getTitle()
//                                                    , searchAllModuleModel.getContent()
//                                                    , dateThaiToEng(searchAllModuleModel.getContentDate())
//                                                    , searchAllModuleModel.getSearchContent());
//            
//        }catch(Exception ex){
//            LOG.error("Exception = "+ex.getMessage());
//            result = null;
//        }
//        return result;
//    }
//    
//    @ApiOperation(
//        value = "Method for delete SearchAllModule by id.", 
//        notes = "ลบข้อมูลการค้นหา", 
//        response = DeleteResponse.class
//    )
//    @ApiResponses( {
//        @ApiResponse( code = 200, message = "SearchAllModule deleted by id success." )
//        ,@ApiResponse( code = 404, message = "SearchAllModule by id not found in the database." )
//        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
//    } )
//    @DELETE
//    @Consumes({ MediaType.APPLICATION_JSON })
//    @Path(value = "/{id}")
//    public DeleteResponse remove(
//        @BeanParam VersionModel versionModel,
//        @ApiParam(name = "id", value = "รหัสการค้นหา", required = true) 
//        @PathParam("id") String id
//    ) {
//        LOG.debug("remove...");
//        LOG.debug("id = "+id);
//        Gson gs = new GsonBuilder()
//                .setVersion(versionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();        
//        DeleteResponse result;
//        try{
//            SearchAllModuleService searchAllModuleService = new SearchAllModuleService();
//            result = searchAllModuleService.deleteData(id);
//            
//        }catch(Exception ex){
//            LOG.error("Exception = "+ex.getMessage());
//            result = null;
//        }
//        return result;
//    }
//    
//    @ApiOperation(
//        value = "Method for search from SearchAllModule", 
//        notes = "ค้นหาข้อมูล", 
//        response = SearchResponse.class
//    )
//    @ApiResponses( {
//        @ApiResponse( code = 200, message = "search success." ),
//        @ApiResponse( code = 404, message = "search not found in the database." ),
//        @ApiResponse( code = 500, message = "Internal Server Error!" )
//    } )
//    @POST
//    @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
//    @Path(value = "/search")
//    public SearchResponse search(
//        @BeanParam VersionModel versionModel,
//        @ApiParam(name = "searchText", value = "คำค้นหา", required = true) 
//        @FormParam("searchText") String searchText,
//        @ApiParam(name = "from", value = "ตำแหน่งเริ่มต้น", required = true) 
//        @DefaultValue("0") @QueryParam("from") int from,
//        @ApiParam(name = "size", value = "จำนวน", required = true) 
//        @DefaultValue("0") @QueryParam("size") int size,
//        @ApiParam(name = "defaultSymbolForSpace", value = "ตรรกะสำหรับเว้นวรรค", required = true) 
//        @FormParam("defaultSymbolForSpace") String defaultSymbolForSpace,
//        @ApiParam(name = "symbolAnd", value = "สัญลักษณ์แทนตรรกะและ", required = true) 
//        @FormParam("symbolAnd") String symbolAnd,
//        @ApiParam(name = "symbolOr", value = "สัญลักษณ์แทนตรรกะหรือ", required = true) 
//        @FormParam("symbolOr") String symbolOr,
//        @ApiParam(name = "symbolNot", value = "สัญลักษณ์แทนตรรกะไม่", required = true) 
//        @FormParam("symbolNot") String symbolNot,
//        @ApiParam(name = "symbolWith", value = "สัญลักษณ์แทนตรรกะตามด้วย", required = true) 
//        @FormParam("symbolWith") String symbolWith
//    ) {
//        LOG.debug("search...");
//        Gson gs = new GsonBuilder()
//                .setVersion(versionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();        
//        SearchResponse result;
//        try{
//            SearchAllModuleService searchAllModuleService = new SearchAllModuleService();
//            result = searchAllModuleService.search(searchText, from, size, defaultSymbolForSpace, symbolAnd, symbolOr, symbolNot, symbolWith);
//        }catch(Exception ex){
//            LOG.error("Exception = "+ex.getMessage());
//            result = null;
//        }
//        return result;
//    }
//}
