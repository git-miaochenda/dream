package com.dream.item.controller;

import com.dream.item.pojo.Item;
import com.dream.pojo.TbItem;
import com.dream.pojo.TbItemDesc;
import com.dream.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model){
        //根据商品id查询商品信息和详情信息
        TbItem tbItem = itemService.selectByKey(itemId);
        TbItemDesc itemDescById = itemService.getItemDescById(itemId);
        //把tbItem封装到item对象中
        Item item = new Item(tbItem);

        //把item和描述绑定
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDescById);
        return "item";
    }

}
