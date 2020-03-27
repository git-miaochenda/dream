package com.dream.search.controller;

import com.dream.common.pojo.SearchResult;
import com.dream.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchItemController {
    @Autowired
    private SearchItemService searchItemService;
    //注入rows 给bean使用setter注入 注入值 ${spring表达式}
    @Value("${ITEM_ROWS}")
    private Integer ITEM_ROWS;

    @RequestMapping("/search")
    public String search(@RequestParam("q")String queryString, @RequestParam(defaultValue = "1") Integer page,
                         Model model) throws Exception{

        //模拟异常
//        int i=1/0;

        //发出该请求的是门户系统，请求方式是GET方式 ?q=搜索内容
        //基本上搜索内容肯定会有中文，但是web.xml中配置的知识解决了POST请求的中文乱码
        //解决GET方式乱码--把字符串进行转码
        queryString = new String(queryString.getBytes("ISO8859-1"), "UTF-8");
        SearchResult search= searchItemService.search(queryString,page,ITEM_ROWS);
        //绑定数据
        model.addAttribute("query",queryString);
        model.addAttribute("totalPages",search.getTotalPages());
        model.addAttribute("itemList",search.getItemList());
        model.addAttribute("page",page);
        return "search";
    }
}
