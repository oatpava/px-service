//
//package com.px.mwp.service;
//
//import com.px.mwp.daoimpl.ElasticSearchDaoImpl;
//import com.px.mwp.entity.ElasticSearch;
//import com.px.mwp.model.ElasticSearchModel;
//import com.px.share.service.GenericService;
//import java.util.List;
//import javax.ws.rs.core.MultivaluedMap;
//import org.apache.log4j.Logger;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.update.UpdateResponse;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilder;
//
///**
// *
// * @author TOP
// */
//public class ElasticSearchService implements GenericService<ElasticSearch, ElasticSearchModel> {
//
//    private static final Logger log = Logger.getLogger(ElasticSearchService.class.getName());
//    private final ElasticSearchDaoImpl elasticSearchDaoImpl;
//
//    public ElasticSearchService() {
//        this.elasticSearchDaoImpl = new ElasticSearchDaoImpl();
//    }
//
//    @Override
//    public ElasticSearch create(ElasticSearch t) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public ElasticSearch getById(int id) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public ElasticSearch getByIdNotRemoved(int id) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public ElasticSearch update(ElasticSearch t) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public ElasticSearch remove(int id, int userId) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public List<ElasticSearch> list(int offset, int limit, String sort, String dir) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public List<ElasticSearch> listAll(String sort, String dir) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public int countAll() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public List<ElasticSearch> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public int countSearch(MultivaluedMap<String, String> queryParams) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public ElasticSearchModel tranformToModel(ElasticSearch t) {
//       ElasticSearchModel elasticSearchModel = new ElasticSearchModel();
//       
//       if(t != null){
//           elasticSearchModel.setField(t.getFieldName());
//           elasticSearchModel.setFieldData(t.getFieldData());
//       
//       }
//       return elasticSearchModel;
//    }
//
//    public IndexResponse addData(String indexName, String indexType, String id, List<ElasticSearch> data) {
//        return elasticSearchDaoImpl.addData(indexName, indexType, id, data);
//
//    }
//
//    public UpdateResponse updateData(String moduleId, String subModuleId, String linkId, List<ElasticSearch> data) {
//        return elasticSearchDaoImpl.updateData(moduleId, subModuleId, linkId, data);
//
//    }
//
//    public DeleteResponse deleteData(String moduleId, String subModuleId, String linkId) {
//        return elasticSearchDaoImpl.deleteData(moduleId, subModuleId, linkId);
//    }
//    
//    public SearchResponse searchdata(String indexName, String indexType, String isFullText,List<ElasticSearch> data,int from , int size ,String sortByField ,String orderBy) {
//        return elasticSearchDaoImpl.searchdata(indexName, indexType, isFullText, data,from,size,sortByField,orderBy);
//    }
//    
//    public BoolQueryBuilder advanceSearchFullTextQuery(String indexName, String indexType, List<ElasticSearch> data) {
//        return elasticSearchDaoImpl.advanceSearchFullTextQuery(indexName, indexType, data);
//    }
//    
//    public QueryBuilder advanceSearchSubTextQuery(String indexName, String indexType, List<ElasticSearch> data) {
//    return elasticSearchDaoImpl.advanceSearchSubTextQuery(indexName, indexType, data);
//    
//    }
//    
//    public boolean putMapping(String indexName,String indexType,String fieldName){
//     return elasticSearchDaoImpl.putMapping(indexName, indexType, fieldName);
//    
//    }
//
//}
