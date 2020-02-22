/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.dms.entity.ElasticSearch;
import com.px.dms.model.ElasticSearchModel;
import com.px.dms.service.ElasticSearchService;
import com.px.share.model.VersionModel;
import com.px.share.util.Elasticsearch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.search.SearchHit;

/**
 *
 * @author TOP
 */
@Api(value = "search ค้นหา")
@Path("v1/dms/elasticSearch")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ElasticSearchResource {
    private static final Logger LOG = Logger.getLogger(ElasticSearchResource.class.getName());

    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
            value = "Method for add data search",
            notes = "สร้างข้อมูล search",
            response = ElasticSearchModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "add data successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML})
    public Response addDataSearch(
            //            @ApiParam(name = "listField", value = "listField,listField,listField ", required = true)
            //            @QueryParam("listField") String listField,
            //            
            //            @ApiParam(name = "listFieldData", value = "listFieldData,listFieldData,listFieldData ", required = true)
            //            @QueryParam("listFieldData") String listFieldData,

            ElasticSearchModel elasticSearchModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(elasticSearchModel.getVersion())
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

            ElasticSearchService elasticSearchService = new ElasticSearchService();
            ArrayList<ElasticSearch> data = new ArrayList<>();
            String[] field = elasticSearchModel.getField().split(",");
            String[] fieldData = elasticSearchModel.getFieldData().split(",");

            for (int i = 0; i < field.length; i++) {
                data.add(new ElasticSearch(field[i], fieldData[i]));
            }
            IndexResponse result = elasticSearchService.addData(elasticSearchModel.getIndexName(), elasticSearchModel.getIndexType(), elasticSearchModel.getId(), data);
            System.out.println("result = " + result);
            System.out.println("result getShardInfo= " + result.getShardInfo());
            System.out.println("result getResult= " + result.getResult());
            responseData.put("data", result.getResult());
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "add data successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update data search",
            notes = "update search data",
            response = ElasticSearchModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "update data successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response updateDataSearch(
            //            @ApiParam(name = "listField", value = "listField,listField,listField ", required = true)
            //            @QueryParam("listField") String listField,
            //            
            //            @ApiParam(name = "listFieldData", value = "listFieldData,listFieldData,listFieldData ", required = true)
            //            @QueryParam("listFieldData") String listFieldData,

            ElasticSearchModel elasticSearchModel
    ) {
        LOG.info("update...");
        Gson gs = new GsonBuilder()
                .setVersion(elasticSearchModel.getVersion())
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

            ElasticSearchService elasticSearchService = new ElasticSearchService();
            ArrayList<ElasticSearch> data = new ArrayList<>();
            String[] field = elasticSearchModel.getField().split(",");
            String[] fieldData = elasticSearchModel.getFieldData().split(",");

            for (int i = 0; i < field.length; i++) {
                data.add(new ElasticSearch(field[i], fieldData[i]));
            }
            UpdateResponse result = elasticSearchService.updateData(elasticSearchModel.getIndexName(), elasticSearchModel.getIndexType(), elasticSearchModel.getId(), data);
            System.out.println("result = " + result);
            responseData.put("data", result.getResult());
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "update data successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for delete data search",
            notes = "ลบข้อมูล search",
            response = ElasticSearchModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "delete data successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteDataSearch(
            @BeanParam VersionModel versionModel,
            //            ElasticSearchModel elasticSearchModel

            @ApiParam(name = "indexName", value = "indexName", required = true)
            @QueryParam("indexName") String indexName,
            @ApiParam(name = "indexType", value = "indexType", required = true)
            @QueryParam("indexType") String indexType,
            @ApiParam(name = "id", value = "id", required = true)
            @QueryParam("id") String id
    ) {
        LOG.info("del...");
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
            
            ElasticSearchService elasticSearchService = new ElasticSearchService();
            System.out.println("-----------1");
            DeleteResponse result = elasticSearchService.deleteData(indexName, indexType, id);
            System.out.println("----------2");
            System.out.println("result = " + result);
            responseData.put("data", result.getResult());
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "delete data successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for  search",
            notes = "ค้นหาข้อมูล",
            response = ElasticSearchModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "search data successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
//    @Path(value = "/aa")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response Search(
            //            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            //            ElasticSearchModel elasticSearchModel

            @ApiParam(name = "indexName", value = "indexName", required = true)
            @QueryParam("indexName") String indexName,
            @ApiParam(name = "indexType", value = "indexType", required = true)
            @QueryParam("indexType") String indexType,
            @ApiParam(name = "isFullText", value = "Y=fulltext,N=no fulltest| fulltext คือ สามารถค้นหาแบบ 'mo กิมจิ' ได้ใน Field เดียวแต่จะหาแบบ wildcard ไม่ได้ ", required = true)
            @QueryParam("isFullText") String isFullText,
            @ApiParam(name = "from", value = "ลำดับเริ่มต้น", required = false)
            @QueryParam("from") int from,
            @ApiParam(name = "size", value = "จำนวนที่จะออกมา", required = false)
            @QueryParam("size") int size,
            @ApiParam(name = "sortByField", value = "sortByField", required = false)
            @QueryParam("sortByField") String sortByField,
            @ApiParam(name = "orderBy", value = "DESC,ASC", required = false)
            @QueryParam("orderBy") String orderBy,
            @ApiParam(name = "listField", value = "field1,field2,field3", required = true)
            @QueryParam("listField") String listField,
            @ApiParam(name = "listFieldData", value = "fieldData1,fieldData2,fieldData3", required = true)
            @QueryParam("listFieldData") String listFieldData
    ) {
        LOG.info("search...");
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

//            System.out.println("-----------1");
            ElasticSearchService elasticSearchService = new ElasticSearchService();
//            System.out.println("-----------2");
            ArrayList<ElasticSearch> data = new ArrayList<ElasticSearch>();
//            ArrayList<String> dataReturn = new ArrayList<>();
            String[] field = listField.split(",");
//            System.out.println("-----------3");
            String[] fieldData = listFieldData.split(",");
//            System.out.println("-----------4");
            for (int i = 0; i < field.length; i++) {
                System.out.println("field[i] = " + field[i]);
                System.out.println("fieldData[i] = " + fieldData[i]);
                data.add(new ElasticSearch(field[i], fieldData[i]));
            }

//             data.add(new ElasticSearch("field1", "top top1"));
//             isFullText="Y";
//            System.out.println("-----------5");
            SearchResponse response = elasticSearchService.searchdata(indexName, indexType, isFullText, data, from, size, sortByField, orderBy);
//            System.out.println("response = "+response);
//            System.out.println("-----------6");
            Elasticsearch elasticsearch = new Elasticsearch();
            boolean useScroll = false;
            if (useScroll) {
                do {
                    for (SearchHit hit : response.getHits().getHits()) {
                        //Handle the hit...
                        System.out.println("hit = " + hit.getSourceAsString());

                    }

//                    System.out.println("-----nextList-----");
                    response = elasticsearch.searchNextDataFromScroll(response.getScrollId());
//                    System.out.println("-----------7");
//                System.out.println("response = " + response.toString());
                } while (response.getHits().getHits().length != 0);
            }
//            for (SearchHit hit : response.getHits().getHits()) {
//                        //Handle the hit...
//                        System.out.println("hit = " + hit.getSourceAsString());
////                         dataReturn.add(hit.getSourceAsString());
//                    }

            Map<String, Object> dataretuen = new HashMap<String, Object>();
            List<Map<String, Object>> rivers = new ArrayList<Map<String, Object>>();
            int i = 0;
            for (SearchHit hit : response.getHits().hits()) {

//            System.out.println("hit = " + hit.getSourceAsString());
//            System.out.println("hit = " + hit.getSortValues().toString());
                Map<String, Object> source = new HashMap<String, Object>();
                String riverName = hit.getType();

                source.put("name", riverName);

                source.put("fieldAndData", hit.getSource());

                rivers.add(source);
                i++;

            }
            dataretuen.put("results", rivers);
            System.out.println("data = " + data);

            System.out.println("result = " + response);
            responseData.put("data", dataretuen);
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "search data successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for add data search map for sort",
            notes = "สร้างข้อมูล map เพื่อ เรียงลำดับ จะเพิ่มเฉพาะ string ถ้าไม่ add map จะทำการ sortไม่ได้",
            response = ElasticSearchModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "add data successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Path(value = "/mapSortSearch")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML})
    public Response addDataMapSortSearch(
            @BeanParam VersionModel versionModel,
            
            @ApiParam(name = "indexName", value = "indexName", required = true)
            @QueryParam("indexName") String indexName,
            @ApiParam(name = "indexType", value = "indexType", required = true)
            @QueryParam("indexType") String indexType,
            @ApiParam(name = "fieldName", value = "fieldName ", required = true)
            @QueryParam("fieldName") String fieldName
    ) {
        LOG.info("create...");
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

            ElasticSearchService elasticSearchService = new ElasticSearchService();
            
            boolean result =elasticSearchService.putMapping(indexName, indexType, fieldName);
            
          
            responseData.put("data", result);
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "add data successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

}
