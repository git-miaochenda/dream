package com.dream.item.pojo;

import com.dream.pojo.TbItem;

public class Item extends TbItem {
    //提供getImages
    public String[] getImages(){
        String img=this.getImage();
        if (img!=null && "".equals(img)&&img.indexOf(",")!=-1){
            return img.split(",");
        }
        return new String[]{img};
    }
    public Item(){

    }
    public Item(TbItem tbItem){
        //把查询到的TbItem传到该对象中，封装到自己的属性中
        this.setId(tbItem.getId());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setNum(tbItem.getNum());
        this.setPrice(tbItem.getPrice());
        this.setSellPoint(tbItem.getSellPoint());
        this.setTitle(tbItem.getTitle());
    }
}
