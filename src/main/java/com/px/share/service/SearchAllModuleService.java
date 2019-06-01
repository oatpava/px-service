package com.px.share.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.entity.WordBriefDetail;
import com.px.admin.service.WordBriefDetailService;
import com.px.share.util.Elasticsearch;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;

/**
 *
 * @author Peach
 */
public class SearchAllModuleService {
    private static final Logger LOG = Logger.getLogger(SearchAllModuleService.class.getName());
    
    final private String IndexName = "pxsearch";
    final private String IndexType = "allModule";
    final private String searchField = "searchContent";

    public SearchAllModuleService() {
    }
    
    public boolean setup(){
        boolean result = false;
        
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if(elasticsearch.getClient()!=null){
            elasticsearch.createIndex(this.IndexName);
            elasticsearch.setTextFieldThaiAnalyzer(this.IndexName, this.IndexType, this.searchField);
            
            result = true;
            
            elasticsearch.closeTransportClient();
        }
        
        return result;
    }
    
    public IndexResponse addData(int moduleId, String linkType, int linkId, String title, String content, Date contentDate, String searchContent){
        checkNotNull(moduleId, "moduleId must not be null");
        checkNotNull(linkType, "linkType must not be null");
        checkNotNull(linkId, "linkId must not be null");
        checkNotNull(title, "title must not be null");
        checkNotNull(content, "content must not be null");
        checkNotNull(contentDate, "contentDate must not be null");
        checkNotNull(searchContent, "searchContent must not be null");
        checkArgument(searchContent.length() > 0,"searchContent must not be empty");
        
        IndexResponse result = null;
        
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if(elasticsearch.getClient()!=null){
            try {
                XContentBuilder builder = jsonBuilder()
                                                .startObject()
                                                    .field("moduleId", moduleId)
                                                    .field("linkType", linkType)
                                                    .field("linkId", linkId)
                                                    .field("title", title)
                                                    .field("content", content)
                                                    .field("contentDate", contentDate)
                                                    .field(this.searchField, searchContent)
                                                    .field("createdDate", new Date())
                                                    .field("updatedDate", new Date())
                                                .endObject();
                
                String id = null;
                result = elasticsearch.addData(this.IndexName, this.IndexType, id, builder.string());
            } catch(Exception e){
            }
            
            elasticsearch.closeTransportClient();
        }
        
        return result;
    }
    
    public UpdateResponse updateData(String id,int moduleId, String linkType, int linkId, String title, String content, Date contentDate, String searchContent){
        checkNotNull(id, "id must not be null");
        checkArgument(id.length() > 0,"id must not be empty");
        checkNotNull(moduleId, "moduleId must not be null");
        checkNotNull(linkType, "linkType must not be null");
        checkNotNull(linkId, "linkId must not be null");
        checkNotNull(title, "title must not be null");
        checkNotNull(content, "content must not be null");
        checkNotNull(contentDate, "contentDate must not be null");
        checkNotNull(searchContent, "searchContent must not be null");
        checkArgument(searchContent.length() > 0,"searchContent must not be empty");
        
        UpdateResponse result = null;
        
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if(elasticsearch.getClient()!=null){
            try {
                XContentBuilder builder = jsonBuilder()
                                                .startObject()
                                                    .field("moduleId", moduleId)
                                                    .field("linkType", linkType)
                                                    .field("linkId", linkId)
                                                    .field("title", title)
                                                    .field("content", content)
                                                    .field("contentDate", contentDate)
                                                    .field(this.searchField, searchContent)
                                                    .field("updatedDate", new Date())
                                                .endObject();
                
                result = elasticsearch.updateData(this.IndexName, this.IndexType, id, builder.string());
            } catch(Exception e){
            }
            
            elasticsearch.closeTransportClient();
        }
        
        return result;
    }
    
    public DeleteResponse deleteData(String id){
        checkNotNull(id, "id must not be null");
        checkArgument(id.length() > 0,"id must not be empty");
        
        DeleteResponse result = null;
        
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if(elasticsearch.getClient()!=null){
            result = elasticsearch.deleteData(this.IndexName, this.IndexType, id);
            
            elasticsearch.closeTransportClient();
        }
        
        return result;
    }
    
    public GetResponse getData(String id){
        checkNotNull(id, "id must not be null");
        checkArgument(id.length() > 0,"id must not be empty");
        
        GetResponse result = null;
        
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if(elasticsearch.getClient()!=null){
            result = elasticsearch.getData(this.IndexName, this.IndexType, id);
            
            elasticsearch.closeTransportClient();
        }
        
        return result;
        
    }
    
    public SearchResponse search(String searchText, int from, int size, String defaultSymbolForSpace, String symbolAnd, String symbolOr, String symbolNot, String symbolWith){
        SearchResponse result = null;
        
        //get WordBrief List
        WordBriefDetailService wordBriefDetailService = new WordBriefDetailService();
        List<WordBriefDetail> wordBriefDetailList = wordBriefDetailService.listAll("", "");
        String[] wordBriefList = new String[wordBriefDetailList.size()];
        int i =0;
        for(WordBriefDetail wordBriefDetail: wordBriefDetailList){
            wordBriefList[i] = wordBriefDetail.getWordBriefDetailName();
            i++;
        }
        
        String[] fetchSource = null;
        HighlightBuilder highlightBuilder = null;

        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.createTransportClient();
        if(elasticsearch.getClient()!=null){
            QueryBuilder queryBuilder = elasticsearch.advanceSearchFullTextQuery(this.searchField,searchText,defaultSymbolForSpace,symbolAnd,symbolOr,symbolNot,symbolWith,wordBriefList);
            ArrayList<SortBuilder> arrSortBuilder = new ArrayList();
            
            result = elasticsearch.searchData(this.IndexName, this.IndexType, queryBuilder,arrSortBuilder,fetchSource,highlightBuilder,from,size);
            
            elasticsearch.closeTransportClient();
        }
        
        return result;
        
    }
    
}
