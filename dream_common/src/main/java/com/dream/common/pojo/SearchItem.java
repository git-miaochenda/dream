package com.dream.common.pojo;

import java.io.Serializable;

public class SearchItem implements Serializable {

    /**
     * id : 商品id
     * title : 商品的标题
     * sellPoint : 商品的卖点
     * image : 图片路径
     * price : 价格
     * categoryName : 分类名称
     * itemDesc : 商品描述
     */

    private String id;
    private String title;
    private String sellPoint;
    private String image;
    private Long price;
    private String categoryName;
    private String itemDesc;

    //这里处理一个图片地址，返回一个图片数组
    public String[] getImages(){
        if(this.image!=null && !"".equals(this.image) && this.image.indexOf(",")!=-1){
            return this.image.split(",");
        }
        return new String[]{this.image};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
}
