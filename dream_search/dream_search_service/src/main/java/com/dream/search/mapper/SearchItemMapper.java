package com.dream.search.mapper;

import com.dream.common.pojo.SearchItem;

import java.util.List;

public interface SearchItemMapper {
    /**
     *
     * @return
     */
    List<SearchItem> getSearchItemList();

    /**
     * 根据商品ID来查找SearchItem
     * @param itemId
     * @return
     */
    SearchItem getItemById(Long itemId);
}
