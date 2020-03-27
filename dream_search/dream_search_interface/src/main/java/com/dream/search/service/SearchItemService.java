package com.dream.search.service;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.SearchResult;

public interface SearchItemService {
    /**
     * 把商品导入到索引库中
     * @return
     * @throws Exception
     */
    DreamResult importAllItems() throws Exception;
    /**
     * 条件查询
     * */
    SearchResult search(String queryString,Integer page,Integer rows) throws Exception;

    /**
     * 更新索引库
     * @param id
     * @return
     * @throws Exception
     */
    DreamResult updateSearchItemById(Long id) throws Exception;


}
