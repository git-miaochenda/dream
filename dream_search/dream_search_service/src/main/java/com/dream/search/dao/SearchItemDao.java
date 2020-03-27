package com.dream.search.dao;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

public interface SearchItemDao{
    /**
     * 更新索引库
     * @param itemId
     * @return
     * @throws Exception
     */
    public DreamResult updateSearchItemById(Long itemId) throws Exception;


    /**
     * 搜索功能
     * @param query
     * @return
     * @throws Exception
     */
    public SearchResult search(SolrQuery query) throws Exception;
}
