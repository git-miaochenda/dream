package com.dream.protal.controller;

import com.alibaba.fastjson.JSON;
import com.dream.content.service.TbContentService;
import com.dream.pojo.TbContent;
import com.dream.protal.pojo.Ad1Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Value("${AD1_CATEGORY_ID}")
    private Long AD1_CATEGORY_ID;

    @Value("${AD1_WIDTH}")
    private String AD1_WIDTH;
    @Value("${AD1_WIDTH_B}")
    private String AD1_WIDTH_B;

    @Value("${AD1_HEIGHT}")
    private String AD1_HEIGHT;
    @Value("${AD1_HEIGHT_B}")
    private String AD1_HEIGHT_B;

    @Autowired
    private TbContentService tbContentService;

    /**
     * 展示是首页
     * 访问从web.xml中找index.html,没有找到，最后会默认访问/index
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        //查找大广告位的内容进行图片轮播
        List<TbContent> tbContents = tbContentService.selectContentByCategoryId(AD1_CATEGORY_ID);
        //把此集合转换为Ad1Node泛型集合
        ArrayList<Ad1Node> nodes = new ArrayList<>();
        for (TbContent tbContent:tbContents){
            Ad1Node node = new Ad1Node();
            //设置轮番标题
            node.setAlt(tbContent.getTitle());
            //设置连接
            node.setHref(tbContent.getUrl());
            node.setSrc(tbContent.getPic());
            node.setSrc2(tbContent.getPic2());
            //宽高
            node.setWidth(AD1_WIDTH);
            node.setwidthB(AD1_WIDTH_B);
            node.setHref(AD1_HEIGHT);
            node.setHeightB(AD1_HEIGHT_B);
            //把转换过得对象加入集合中
            nodes.add(node);
        }
        //把数据转换为json转发给index.jsp
        String jsonString = JSON.toJSONString(nodes);
        model.addAttribute("ad1",jsonString);
        return "index";
    }
}
