package com.dream.service;

import com.dream.common.pojo.EasyUiTreeNode;

import java.util.List;

public interface ItemCatService {
    /**
     * 查找商品类名，返回树形节点的集合
     * @param parentId
     * @return
     */
    List<EasyUiTreeNode> getItemCatListByParentId(Long parentId);
}
