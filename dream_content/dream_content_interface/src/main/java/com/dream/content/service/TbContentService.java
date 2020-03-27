package com.dream.content.service;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiDataGridResult;
import com.dream.pojo.TbContent;

import java.util.List;

public interface TbContentService {
    /**
     * 分页查询
     * @param page
     * @param rows
     * @param categoryId
     * @return
     */
    EasyUiDataGridResult list(int page, int rows, long categoryId);

    /**
     * 增加
     * @param tbContent
     * @return
     */
    DreamResult save(TbContent tbContent);

    /**
     * 删除
     * @param ids
     * @return
     */
    DreamResult delete(Long [] ids);

    /**
     * 编辑修改
     * @param tbContent
     * @return
     */
    DreamResult edit(TbContent tbContent);

    /**
     * 图片轮播
     * @param ad1_category_id
     * @return
     */
    List<TbContent> selectContentByCategoryId(Long ad1_category_id);
}
