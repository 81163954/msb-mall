package com.msb.mall.product.service.impl;

import com.msb.mall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.common.utils.Query;

import com.msb.mall.product.dao.CategoryDao;
import com.msb.mall.product.entity.CategoryEntity;
import com.msb.mall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询所有类别数据，并将数据封装成树型结构，方便前端使用
     * @param params
     * @return
     */
    @Override
    public List<CategoryEntity> queryPageWithTree(Map<String, Object> params) {
        // 1.查询所有的商品类别信息
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        // 2.将商品类别拆解为树型结构【父子关系】
        List<CategoryEntity> list = categoryEntities.stream().filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .map(categoryEntity -> {
                    //根据大类找小类
                    categoryEntity.setChildren(getCategoryChildren(categoryEntity, categoryEntities));
                    return categoryEntity;
                }).sorted((entity1, entity2) -> {
                    return (entity1.getSort() == null ? 0 : entity1.getSort()) - (entity2.getSort() == null ? 0 : entity2.getSort());
                }).collect(Collectors.toList());


        return list;
    }

    /**
     * 逻辑批量删除
     * @param ids
     */
    @Override
    public void removeCategoryByIds(List<Long> ids) {
        //todo 1.检查类别数据在其他业务中是否使用了

        // 2.进行批量逻辑删除
        baseMapper.deleteBatchIds(ids);

    }

    private List<CategoryEntity> getCategoryChildren(CategoryEntity categoryEntity, List<CategoryEntity> categoryEntities) {
        List<CategoryEntity> collect = categoryEntities.stream().filter(entity -> {
            //此处long类型如果没在-128 127之间的话是对象，所以不要用==去比较
            //            return entity.getParentCid() == categoryEntity.getCatId();
            return entity.getParentCid().equals(categoryEntity.getCatId());
        }).map(entity -> {
            entity.setChildren(getCategoryChildren(entity, categoryEntities));
            return entity;
        }).sorted((entity1, entity2) -> {
            return (entity1.getSort() == null ? 0 : entity1.getSort()) - (entity2.getSort() == null ? 0 : entity2.getSort());
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);
        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[parentPath.size()]);
    }

    @Override
    @Transactional
    public void updateDetail(CategoryEntity category) {
        this.updateById(category);
        if(!StringUtils.isEmpty(category.getName())){
            //同步更新品牌分类表中的数据
            categoryBrandRelationService.updateCategoryName(category.getCatId(),category.getName());
            //todo 同步更新其他表的数据
        }
    }

    /**
     * 225,22,2
     * @param catelogId
     * @param paths
     * @return
     */
    private List<Long> findParentPath(Long catelogId,List<Long> paths){
        paths.add(catelogId);
        CategoryEntity entity = this.getById(catelogId);
        if(entity.getParentCid() != 0){
            findParentPath(entity.getParentCid(),paths);
        }
        return paths;
    }
}