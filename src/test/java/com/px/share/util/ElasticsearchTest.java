package com.px.share.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.xcontent.XContentBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Peach
 */
public class ElasticsearchTest {
    public ElasticsearchTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Ignore
    @Test
    public void testGetTransportClientNodes() {
        System.out.println("start...");
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        System.out.println("TransportClient = "+elasticsearch.getClient());
        
        List<DiscoveryNode> nodeList = elasticsearch.getClient().listedNodes();
        for(DiscoveryNode node:nodeList){
            System.out.println("------------------");
            System.out.println("node = "+node.toString());
            System.out.println("name = "+node.getName());
            System.out.println("id = "+node.getId());
        }
        
        elasticsearch.closeTransportClient();
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testGetTransportClientIndices() {
        System.out.println("start...");
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        System.out.println("TransportClient = "+elasticsearch.getClient());
        
        String[] indices = elasticsearch.getClient().admin().cluster().prepareState().get().getState().getMetaData().getConcreteAllIndices();
        System.out.println("------------------");
        for(int i = 0;i<indices.length;i++){
            System.out.println("indices = "+indices[i]);
        }

        elasticsearch.closeTransportClient();
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testCreateIndex() {
        System.out.println("start...");
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        System.out.println("TransportClient = "+elasticsearch.getClient());
        
        elasticsearch.createIndex("twitter");
        String[] indices = elasticsearch.getClient().admin().cluster().prepareState().get().getState().getMetaData().getConcreteAllIndices();
        System.out.println("------------------");
        for(int i = 0;i<indices.length;i++){
            System.out.println("indices = "+indices[i]);
        }

        elasticsearch.closeTransportClient();
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testDeleteIndex() {
        System.out.println("start...");
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        System.out.println("TransportClient = "+elasticsearch.getClient());
        
        elasticsearch.deleteIndex("twitter");
        String[] indices = elasticsearch.getClient().admin().cluster().prepareState().get().getState().getMetaData().getConcreteAllIndices();
        System.out.println("------------------");
        for(int i = 0;i<indices.length;i++){
            System.out.println("indices = "+indices[i]);
        }

        elasticsearch.closeTransportClient();
        System.out.println("end...");
    }

    @Ignore
    @Test
    public void testSortTextField() {
        System.out.println("start...");
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        System.out.println("TransportClient = "+elasticsearch.getClient());
        
        try {
            String indexName = "twitter";
            String indexType = "tweet";
            String fieldName = "user_name";
            PutMappingResponse response = elasticsearch.setTextFieldSortEnable(indexName, indexType, fieldName);
            System.out.println("response = "+response.toString());
            
        } catch(Exception e){
            System.out.println("Exception:testAddData:"+e);
        }


        elasticsearch.closeTransportClient();
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testSetThaiAnalyzer() {
        System.out.println("start...");
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        System.out.println("TransportClient = "+elasticsearch.getClient());
        
        try {
            String indexName = "twitter";
            String indexType = "tweet";
            String fieldName = "full_text_search";
            PutMappingResponse response = elasticsearch.setTextFieldThaiAnalyzer(indexName, indexType, fieldName);
            System.out.println("response = "+response.toString());
            
        } catch(Exception e){
            System.out.println("Exception:testAddData:"+e);
        }


        elasticsearch.closeTransportClient();
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testAddData() {
        System.out.println("start...");
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        System.out.println("TransportClient = "+elasticsearch.getClient());
        
        try {
            String indexName = "twitter";
            String indexType = "tweet";
            String id = "1";
            XContentBuilder builder = jsonBuilder()
                                        .startObject()
                                            .field("user", "kimchi")
                                            .field("postDate", new Date())
                                            .field("message", "trying out Elasticsearch")
                                        .endObject();
            System.out.println("builder = "+builder.string());
            IndexResponse response = elasticsearch.addData(indexName, indexType, id, builder.string());
            System.out.println("index = "+response.getIndex());
            System.out.println("type = "+response.getType());
            System.out.println("id = "+response.getId());
            System.out.println("version = "+response.getVersion());
        } catch(Exception e){
            System.out.println("Exception:testAddData:"+e);
        }


        elasticsearch.closeTransportClient();
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testGetData() {
        System.out.println("start...");
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        System.out.println("TransportClient = "+elasticsearch.getClient());
        
        String indexName = "twitter";
        String indexType = "tweet";
        String id = "1";
        GetResponse response = elasticsearch.getData(indexName, indexType, id);
        System.out.println("response = "+response.toString());

        elasticsearch.closeTransportClient();
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testUpdateData() {
        System.out.println("start...");
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        System.out.println("TransportClient = "+elasticsearch.getClient());
        
        try {
            String indexName = "twitter";
            String indexType = "tweet";
            String id = "1";
            XContentBuilder builder = jsonBuilder()
                                        .startObject()
                                            .field("user_name", "abcd")
                                            .field("gender", "male")
                                            .field("detail", "ทดสอบการใช้งานภาษาไทย ฉันกินข้าวกับปลาทูแล้วเธอกินข้าวกับอะไรบ้าง lemons")
                                            .field("full_text_search", "ทดสอบการใช้งานภาษาไทย ฉันกินข้าวกับปลาทูแล้วเธอกินข้าวกับอะไรบ้าง lemons")
                                            .field("age",22)
                                            .startArray("color")
                                                .value("red")
                                                .value("blue")
                                                .value("green")
                                            .endArray()
                                            .startArray("school")
                                                .startObject()
                                                    .field("schoolName", "assumption")
                                                    .field("year",1900)
                                                .endObject()
                                                .startObject()
                                                    .field("schoolName", "thammasat")
                                                    .field("year",1912)
                                                .endObject()
                                            .endArray()
                                        .endObject();
            System.out.println("builder = "+builder.string());
            UpdateResponse response = elasticsearch.updateData(indexName, indexType, id, builder.string());
            System.out.println("response = "+response.toString());
        } catch(Exception e){
            System.out.println("Exception:testUpdateData:"+e);
        }


        elasticsearch.closeTransportClient();
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testDeleteData() {
        System.out.println("start...");
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        System.out.println("TransportClient = "+elasticsearch.getClient());
        
        String indexName = "twitter";
        String indexType = "tweet";
        String id = "1";
        DeleteResponse response = elasticsearch.deleteData(indexName, indexType, id);
        System.out.println("response = "+response.toString());

        elasticsearch.closeTransportClient();
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testSearchData() {
        System.out.println("start...");
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        System.out.println("TransportClient = "+elasticsearch.getClient());
        
        String indexName = "twitter";
        String indexType = "tweet";
        /*QueryBuilder queryBuilder = boolQuery()
                                    .must(termQuery("user", "kimchy"))
                                    .must(termQuery("message", "out"));*/
        BoolQueryBuilder booleanQueryBuilder = boolQuery();
        booleanQueryBuilder.must(termQuery("user", "kimchy")); //CaseSensitive
        booleanQueryBuilder.must(matchQuery("message", "trying"));//Case Insensitive
        booleanQueryBuilder.must(wildcardQuery("detail", "*กินข้าว*"));
        booleanQueryBuilder.must(matchQuery("age", 22));
        //booleanQueryBuilder.must(rangeQuery("age").from(5).to(22).includeLower(true).includeUpper(true));
        booleanQueryBuilder.must(rangeQuery("age").gt(5).lte(22)); //gte, gt, lt or lte
        booleanQueryBuilder.must(boolQuery()
                                        .should(matchQuery("age", 22))
                                        .should(wildcardQuery("user", "*ji")));
        //Search from Array
        booleanQueryBuilder.must(termQuery("color", "red")); 
        booleanQueryBuilder.must(termQuery("color", "green"));
        
        booleanQueryBuilder.must(matchQuery("school.year", 1900));
        
        //Search Full Text
        booleanQueryBuilder.must(matchQuery("full_text_search", "กินภาษาlemon")); //analyzer thai
        //booleanQueryBuilder.must(matchQuery("detail", "ภาษา กิน"));
        
        ArrayList<SortBuilder> arrSortBuilder = new ArrayList();
        arrSortBuilder.add(SortBuilders.fieldSort("postDate").order(SortOrder.DESC));
        arrSortBuilder.add(SortBuilders.fieldSort("user_name").order(SortOrder.ASC));
        
        String[] fetchSource = null;
        HighlightBuilder highlightBuilder = null;
        
        int from = 0;
        int size = 0;//0 = Not Specific list size when return
        SearchResponse response = elasticsearch.searchData(indexName, indexType, booleanQueryBuilder,arrSortBuilder,fetchSource,highlightBuilder,from,size);
        System.out.println("response = "+response.toString());
        
        if(elasticsearch.isUseScroll()){//get Next Data From Scroll
            do {
                for (SearchHit hit : response.getHits().getHits()) {
                    //Handle the hit...
                    System.out.println("hit = "+hit.getSourceAsString());
                }

                System.out.println("-----nextList-----");
                response = elasticsearch.searchNextDataFromScroll(response.getScrollId());
                System.out.println("response = "+response.toString());
            } while(response.getHits().getHits().length != 0); // Zero hits mark the end of the scroll and the while loop.
        }

        elasticsearch.closeTransportClient();
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testAdvanceSearchData() {
        System.out.println("start...");
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        System.out.println("TransportClient = "+elasticsearch.getClient());
        
        String indexName = "twitter";
        String indexType = "tweet";
        
        String field = "user";
        String value = "mo กิมจิ";
        String defaultSymbolForSpace = "OR";
        String symbolAnd = "AND";
        String symbolOr = "OR";
        String symbolNot = "NOT";
        String symbolWith = "WITH";
        String[] wordBriefList = new String[1];
        wordBriefList[0] = "฿กิมจิ฿kimchy฿";
        QueryBuilder queryBuilder = elasticsearch.advanceSearchSubTextQuery(field,value,defaultSymbolForSpace,symbolAnd,symbolOr,symbolNot,symbolWith,wordBriefList);
        //QueryBuilder queryBuilder = elasticsearch.advanceSearchFullTextQuery(field,value,defaultSymbolForSpace,symbolAnd,symbolOr,symbolNot,symbolWith,wordBriefList);
        
        System.out.println("queryBuilder = "+queryBuilder.toString());
        
        ArrayList<SortBuilder> arrSortBuilder = new ArrayList();
        arrSortBuilder.add(SortBuilders.fieldSort("postDate").order(SortOrder.DESC));
        arrSortBuilder.add(SortBuilders.fieldSort("user_name").order(SortOrder.ASC));
        
        String[] fetchSource = null;
        HighlightBuilder highlightBuilder = null;
        
        int from = 0;
        int size = 0;//0 = Not Specific list size when return
        SearchResponse response = elasticsearch.searchData(indexName, indexType, queryBuilder,arrSortBuilder,fetchSource,highlightBuilder,from,size);
        System.out.println("response = "+response.toString());
        
        if(elasticsearch.isUseScroll()){//get Next Data From Scroll
            do {
                for (SearchHit hit : response.getHits().getHits()) {
                    //Handle the hit...
                    System.out.println("hit = "+hit.getSourceAsString());
                }

                System.out.println("-----nextList-----");
                response = elasticsearch.searchNextDataFromScroll(response.getScrollId());
                System.out.println("response = "+response.toString());
            } while(response.getHits().getHits().length != 0); // Zero hits mark the end of the scroll and the while loop.
        }

        elasticsearch.closeTransportClient();
        System.out.println("end...");
    }
    
    @Ignore
    @Test
    public void testScore() {
        System.out.println("start...");
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        System.out.println("TransportClient = "+elasticsearch.getClient());
        
        String indexName = "twitter";
        String indexType = "tweet";
        
        BoolQueryBuilder booleanQueryBuilder = boolQuery();
        booleanQueryBuilder.should(termQuery("user", "kimchy"));
        booleanQueryBuilder.should(termQuery("user", "moji"));
        booleanQueryBuilder.should(termQuery("age", "22"));
        //booleanQueryBuilder.should(termQuery("user_name", "12345"));
        
        System.out.println("queryBuilder = "+booleanQueryBuilder.toString());
        
        ArrayList<SortBuilder> arrSortBuilder = new ArrayList();
        //arrSortBuilder.add(SortBuilders.fieldSort("user_name").order(SortOrder.ASC));
        //elasticsearch.setShowScoreWhenSort(true);
        
        String[] fetchSource = null;
        HighlightBuilder highlightBuilder = null;
        
        int from = 0;
        int size = 0;//0 = Not Specific list size when return
        SearchResponse response = elasticsearch.searchData(indexName, indexType, booleanQueryBuilder,arrSortBuilder,fetchSource,highlightBuilder,from,size);
        System.out.println("response = "+response.toString());
        
        if(elasticsearch.isUseScroll()){//get Next Data From Scroll
            do {
                for (SearchHit hit : response.getHits().getHits()) {
                    //Handle the hit...
                    System.out.println("hit = "+hit.getSourceAsString());
                }

                System.out.println("-----nextList-----");
                response = elasticsearch.searchNextDataFromScroll(response.getScrollId());
                System.out.println("response = "+response.toString());
            } while(response.getHits().getHits().length != 0); // Zero hits mark the end of the scroll and the while loop.
        }

        elasticsearch.closeTransportClient();
        System.out.println("end...");
    }
}
