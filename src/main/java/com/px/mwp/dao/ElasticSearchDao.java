
package com.px.mwp.dao;

import com.px.mwp.entity.ElasticSearch;
import com.px.share.dao.GenericTreeDao;
import java.util.List;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

/**
 *
 * @author TOP
 */
public interface ElasticSearchDao extends GenericTreeDao<ElasticSearch, Integer>{
    IndexResponse addData(String indexName,String indexType,String id,List<ElasticSearch> data);
    UpdateResponse updateData(String moduleId,String subModuleId,String linkId ,List<ElasticSearch> data);
    DeleteResponse deleteData(String moduleId,String subModuleId,String linkId);
    SearchResponse searchdata(String indexName,String indexType,String isFullText,List<ElasticSearch> data,int from , int size ,String sortByField ,String orderBy);
    QueryBuilder advanceSearchSubTextQuery(String indexName,String indexType,List<ElasticSearch> data);
    BoolQueryBuilder advanceSearchFullTextQuery(String indexName,String indexType, List<ElasticSearch> data);
    
}
