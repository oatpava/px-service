package com.px.share.util; 

import com.px.share.daoimpl.GenericTreeDaoImpl;
import com.px.share.entity.BaseTreeEntity;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Peach
 */
public class TreeUtil {
    private static final Logger LOG = Logger.getLogger(TreeUtil.class);
    private static final String SEPARATOR = "฿";
    
    public static String getSeparator(){
        return SEPARATOR;
    }
    
    public static String generateParentKey(BaseTreeEntity node) throws Exception{
        String result = "";
        if(node==null){
            throw new NullPointerException("node can't be null");
        } else  if(node.getId()==null){
            throw new NullPointerException("Id can't be null");
        } else {
            String separator = SEPARATOR;
            String newParentKey = separator;
            if(node.getParentId()!=null){
                GenericTreeDaoImpl genericTreeDaoImpl = new GenericTreeDaoImpl(node.getClass());
                BaseTreeEntity parent = (BaseTreeEntity)genericTreeDaoImpl.findParent(node.getParentId());
                
                if(parent!=null){
                    newParentKey = Common.noNull(parent.getParentKey(),"");
                    if(newParentKey.equals("")){ newParentKey = separator;}
                }
            }
            
            newParentKey += node.getId()+separator;
            
            result = newParentKey;
        }
        
        return result;
    }
    
    public static List<BaseTreeEntity> getListFromParentKey(BaseTreeEntity node) throws Exception{
        ArrayList<BaseTreeEntity> result = new ArrayList();
        
        String parentKey = node.getParentKey();
        if(!parentKey.isEmpty()){
            GenericTreeDaoImpl genericTreeDaoImpl = new GenericTreeDaoImpl(node.getClass());
            for (String tmp: parentKey.split(SEPARATOR)){
                if(!tmp.isEmpty()){
                    BaseTreeEntity obj = (BaseTreeEntity)genericTreeDaoImpl.getById(Integer.parseInt(tmp));
                    if(obj!=null){
                        result.add(obj);
                    }
                }
            }
        } 
        
        return result;

    }
    
    
    
    
    
    public static Integer generateNodeLevel(BaseTreeEntity node){
        Integer nodeLevel = 0;
        String parentKey = node.getParentKey();
        if(!(parentKey==null || parentKey.equals(""))) {
            String[] parentList = parentKey.split(SEPARATOR);
            nodeLevel = parentList.length-2;
        }
        return nodeLevel;
    }
    
}
