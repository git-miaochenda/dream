package com.dream.content.service.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiDataGridResult;
import com.dream.content.redis.JedisClient;
import com.dream.content.service.TbContentService;
import com.dream.mapper.TbContentMapper;
import com.dream.pojo.TbContent;
import com.dream.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class TbContentServiceImpl implements TbContentService {

    @Autowired
    private TbContentMapper tbContentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_KEY}")
    private String CONTENT_KEY;

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param categoryId
     * @return
     */
    @Override
    public EasyUiDataGridResult list(int page, int rows, long categoryId) {
       //1、先调用分页插件
        PageHelper.startPage(page,rows);
        //2、执行查询
        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContents = tbContentMapper.selectByExample(tbContentExample);
        //获取分页信息，封装到指定对象
        PageInfo<TbContent> tbContentPageInfo = new PageInfo<>(tbContents);
        //通过pageInfo来得到所有的查询结果--总数，页码，每页条数
        return new EasyUiDataGridResult(tbContentPageInfo.getTotal(),tbContents);
    }

    /**
     * 增加
     * @param tbContent
     * @return
     */
    @Override
    public DreamResult save(TbContent tbContent) {
        //设置增加时间
        tbContent.setCreated(new Date());
        tbContent.setUpdated(tbContent.getCreated());
        //执行增加
        tbContentMapper.insert(tbContent);
        //缓存同步
        jedisClient.hdel(CONTENT_KEY,tbContent.getCategoryId().toString());
        return DreamResult.ok();
    }

    @Override
    public DreamResult delete(Long [] ids) {
        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        //id in (x,y,z..)
        criteria.andIdIn(Arrays.asList(ids));
        tbContentMapper.deleteByExample(tbContentExample);
        return DreamResult.ok();
    }

    @Override
    public DreamResult edit(TbContent tbContent) {
        //设置修改时间
        tbContent.setUpdated(new Date());
        //执行修改
        tbContentMapper.updateByPrimaryKeySelective(tbContent);

        jedisClient.hdel(CONTENT_KEY,tbContent.getCategoryId().toString());
        return DreamResult.ok();
    }
/** 门户网站用的是这一个，在这里添加缓存*/
    @Override
    public List<TbContent> selectContentByCategoryId(Long ad1_category_id) {
        try {
            //先查询缓存--value值是JSON格式的
            String jsonValue = jedisClient.hget(CONTENT_KEY, ad1_category_id + "");
            //判断value是否不为空
            if (StringUtils.isNotBlank(jsonValue)){
                //如果不是空，说明缓存中有数据，则转换成集合并返回
                TbContent[] parse = JSON.parse(jsonValue, TbContent[].class);
                return Arrays.asList(parse);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(ad1_category_id);
        List<TbContent> tbContents = tbContentMapper.selectByExample(tbContentExample);

        //如果上面没有缓存，则这里查询完之后加入缓存中
        try {
            jedisClient.hset(CONTENT_KEY,ad1_category_id+"",JSON.json(tbContents));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tbContents;
    }
}
