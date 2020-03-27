package com.dream.content.service.impl;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiTreeNode;
import com.dream.content.service.TbContentCategoryService;
import com.dream.mapper.TbContentCategoryMapper;
import com.dream.pojo.TbContentCategory;
import com.dream.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

  @Autowired
  private TbContentCategoryMapper tbContentCategoryMapper;
    @Override
    public List<EasyUiTreeNode> getContentCategoryByParentId(Long parentId) {
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        //把饭会的集合转换成EasyUiTreeNode类型
        ArrayList<EasyUiTreeNode> list = new ArrayList<>();
        for (TbContentCategory tbContentCategory:tbContentCategories){
            EasyUiTreeNode node = new EasyUiTreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            list.add(node);
        }
        return list;
    }

    @Override
    public DreamResult createCategory(Long parentId, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        //设置其他的数据--新添加的肯定不是父节点
        tbContentCategory.setIsParent(false);
        //排列序号，表示统计类目的展现次序，如数值相等则按名称次序排列，取值范围：大于零的整数
        tbContentCategory.setSortOrder(1);
        //状态 可选值 1(正常) 2(删除)
        tbContentCategory.setStatus(1);
        //创建和修改时间，刚创建时，修改时间和创建时间一样
        Date date = new Date();
        tbContentCategory.setCreated(date);
        tbContentCategory.setUpdated(date);
        //插入
        tbContentCategoryMapper.insert(tbContentCategory);
        //自动增长的id会封装到参数对象中
        //判断id为parentId的那个分类在添加此节点时是否为父节点，如果不是，则改为父节点
        //先根据parentId来查到这个id对应的类别
        TbContentCategory parentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        //如果之前不是父节点，则把它修改为父节点
        if (!parentCategory.getIsParent()){
            parentCategory.setIsParent(true);
            //更新
            tbContentCategoryMapper.updateByPrimaryKey(parentCategory);
        }
        return DreamResult.ok(tbContentCategory);
    }

    @Override
    public DreamResult deleteContentCategory(Long parentId, Long id, Boolean isParentAfterDelete) {
        //1、先删除id的节点
        tbContentCategoryMapper.deleteByPrimaryKey(id);
        //2、根据parentId是否有父节点，如果有则不变，如果没有则做个修改
        if (!isParentAfterDelete){  //本来是true，如果是true就不用改
            TbContentCategory tbContentCategory = new TbContentCategory();
            tbContentCategory.setId(parentId);
            tbContentCategory.setIsParent(isParentAfterDelete);
            //只修改了一个属性，updateByPrimaryKeySelective方法，有没有设置的属性（null）的字段不修改
            tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        }
        return DreamResult.ok();
    }

    @Override
    public DreamResult updateContentCategory(Long id, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setId(id);
        tbContentCategory.setName(name);
        tbContentCategory.setUpdated(new Date());
        tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        return DreamResult.ok();
    }
}
