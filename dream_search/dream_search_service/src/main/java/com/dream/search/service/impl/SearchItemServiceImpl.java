package com.dream.search.service.impl;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.SearchItem;
import com.dream.common.pojo.SearchResult;
import com.dream.search.dao.SearchItemDao;
import com.dream.search.mapper.SearchItemMapper;
import com.dream.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;

    //在spring管理的solrServer
    @Autowired
    private SolrServer solrServer;

    @Autowired
    private SearchItemDao searchItemDao;

    @Override
    public DreamResult importAllItems() throws Exception {
        //1、查询所有的商品数据
        List<SearchItem> itemList = searchItemMapper.getSearchItemList();

        for(SearchItem item:itemList){
            //2、为每一个商品创建一个SolrInputDocument
            SolrInputDocument document = new SolrInputDocument();
            //3、为文档添加业务域
            document.addField("id",item.getId());
            document.addField("item_title",item.getTitle());
            document.addField("item_sell_point",item.getSellPoint());
            document.addField("item_price",item.getPrice());
            document.addField("item_image",item.getImage());
            document.addField("item_category_name",item.getCategoryName());
            document.addField("item_desc",item.getItemDesc());

            //4、向索引库中添加文档
            solrServer.add(document);
        }
        //5、提交
        solrServer.commit();
        return DreamResult.ok();
    }

    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) throws Exception {
        //1、先创建一个SolrQuery对象
        SolrQuery solrQuery = new SolrQuery();
        //2、设置搜索条件
        solrQuery.setQuery(queryString);
        //3、设置分页条件 查询索引库跟查询数据库概念上是一样的start跟limit一样起始的下标
        solrQuery.setStart((page-1)*rows);
        solrQuery.setRows(rows);
        //4、指定默认的搜索域
        solrQuery.set("df","item_title");
        //5、设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        //中间就是高亮的内容
        solrQuery.setHighlightSimplePost("</em>");
        //6、执行查询
        SearchResult result = searchItemDao.search(solrQuery);
        //7、设置总页数 总记录数/每页显示的条数 5.1->6.0 5.9-6.0
        //Math.ceil(result.getTotalNum()*1.0 / rows*1.0);
        Long totalNum = result.getTotalNum();
        long totalPages = totalNum / rows;
        if (totalPages % rows>0){
            totalPages++;
        }
        result.setTotalPages(totalPages);
        return result;
    }

    @Override
    public DreamResult updateSearchItemById(Long id) throws Exception {
        return searchItemDao.updateSearchItemById(id);
    }


}
