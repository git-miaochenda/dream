package com.dream.common.pojo;

import java.io.Serializable;

public class DreamResult implements Serializable {
    //状态码  定义为跟http响应的状态码的含义一样 200代表成功
    private Integer status;
    //响应之后的提示
    private String msg;
    //响应如果需要带数据，封装到data
    private Object data;

    /*
    * 提供几个静态工厂方法，用来返回结果对象同时设置响应结果
    * */
    //3、如果成功了 需要返回对象
    public static DreamResult ok(Object data){
        return new DreamResult(data);
    }
    //2、如果失败了，则自定义DreamResult对象
    public static DreamResult build(Integer status,String name,Object data){
       return new DreamResult(status,name,data);
    }
    //1、如果成功了但是没有数据要返回，直接调用ok方法代表成功
    public static DreamResult ok(){
        return new DreamResult(null);
    }

    //登录操作
    public static DreamResult build(Integer status,String name){
        return new DreamResult(status,name);
    }
    //一旦返回有数据了，则代表肯定成功
    public DreamResult(Integer status,String name){
        this.data=null;
        this.status=status;
        this.msg=name;
    }

    //一旦返回有数据了，则代表肯定成功
    public DreamResult(Object data) {
        this.data = data;
        this.status=200;
        this.msg="ok";
    }

    public DreamResult() {
    }

    public DreamResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
