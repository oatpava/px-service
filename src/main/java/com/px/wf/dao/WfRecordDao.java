package com.px.wf.dao;

import com.px.share.dao.GenericTreeDao;
import com.px.wf.entity.WfRecord;
import java.util.List;

/**
 *
 * @author oat
 */
public interface WfRecordDao extends GenericTreeDao<WfRecord, Integer> {
    List<WfRecord> listByContentId(int contentId);
    List<WfRecord> listByDocumentId(int documentId);
}
