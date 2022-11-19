package com.msb.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-03 12:26:44
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> queryPageWithTree(Map<String, Object> params);

    void removeCategoryByIds(List<Long> ids);

    Long[] findCatelogPath(Long catelogId);

    void updateDetail(CategoryEntity category);
}

