package com.dream.protal.pojo;

public class Ad1Node {
    //pic
    private String src;
    //pic2
    private String src2;
    //url
    private String href;
    private String width;
    private String widthB;
    private String height;
    private String heightB;
    private String alt;

    @Override
    public String toString() {
        return "Ad1Node{" +
                "src='" + src + '\'' +
                ", src2='" + src2 + '\'' +
                ", href='" + href + '\'' +
                ", width='" + width + '\'' +
                ", widthB='" + widthB + '\'' +
                ", height='" + height + '\'' +
                ", heightB='" + heightB + '\'' +
                ", alt='" + alt + '\'' +
                '}';
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrc2() {
        return src2;
    }

    public void setSrc2(String src2) {
        this.src2 = src2;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getwidthB() {
        return widthB;
    }

    public void setwidthB(String widthB) {
        this.widthB = widthB;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeightB() {
        return heightB;
    }

    public void setHeightB(String heightB) {
        this.heightB = heightB;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
