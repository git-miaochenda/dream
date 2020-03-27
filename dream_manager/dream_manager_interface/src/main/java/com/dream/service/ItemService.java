package com.dream.service;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiDataGridResult;
import com.dream.pojo.TbItem;
import com.dream.pojo.TbItemDesc;

import java.util.List;

public interface ItemService {
    /**
     * 根据商品id查询商品描述
     * @param itemId
     * @return
     */
    TbItemDesc getItemDescById(Long itemId);
    /**
     * 根据商品id查找商品
     * @param itemId
     * @return
     */
    TbItem selectByKey(long itemId);

    /**
     * 分页查询
     * @param pageNum 当前页码
     * @param pageSize 每页行数
     * @return EasyUi数据网格的数据格式的对象
     */
    EasyUiDataGridResult list(int pageNum, int pageSize);

    /**
     *
     * @param ids
     * @return
     */
    DreamResult delete(List<Long> ids);

    /**
     * 增加商品，和描述
     * @param tbItem
     * @param desc
     * @return
     */
    DreamResult saveItem(TbItem tbItem, String desc);
}
