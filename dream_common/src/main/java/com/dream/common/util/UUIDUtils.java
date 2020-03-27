package com.dream.common.util;

import java.util.Random;

public class UUIDUtils {
    /**
     * 生成商品id
     */
    public static long getItemId(){
        //1、先得到当前的时间戳
        long millis = System.currentTimeMillis();
        //2、加上一个两位的随机数0~100的
        int ran = new Random().nextInt(99);//Math.random()*99
        //3、有可能生成的是1位数  7   9  则给他们前面补一个0
        String id=millis+String.format("%02d",ran);//把ran的数值变为两位数的字符串，如果不足2位，前面补0
        return new Long(id);
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            System.out.println(getItemId());
        }
    }

}
