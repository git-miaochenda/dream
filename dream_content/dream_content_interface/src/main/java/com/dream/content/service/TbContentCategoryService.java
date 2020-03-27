package com.dream.content.service;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiTreeNode;

import java.util.List;

public interface TbContentCategoryService {
    /**
     * 根据父节点查询内容分类
     * @param parentId
     * @return
     */
    List<EasyUiTreeNode> getContentCategoryByParentId(Long parentId);

    /**
     * 增加
     * @param parentId
     * @param name
     * @return
     */
    DreamResult createCategory(Long parentId, String name);

    /**
     * 删除
     * @param parentId
     * @param id
     * @param isParentAfterDelete
     * @return
     */
    DreamResult deleteContentCategory(Long parentId, Long id, Boolean isParentAfterDelete);

    /**
     * 修改
     * @param id
     * @param name
     * @return
     */
    DreamResult updateContentCategory(Long id, String name);
}
