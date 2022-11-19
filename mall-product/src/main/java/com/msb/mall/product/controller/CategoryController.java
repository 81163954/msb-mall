package com.msb.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msb.mall.product.entity.CategoryEntity;
import com.msb.mall.product.service.CategoryService;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.common.utils.R;



/**
 * 商品三级分类
 *
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-03 13:00:18
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 批量修改 
     */
    @RequestMapping("/updateBatch")
    //@RequiresPermissions("product:category:update")
    public R updateBatch(@RequestBody CategoryEntity[] category){
        //categoryService.updateById(category);
        categoryService.updateBatchById(Arrays.asList(category));
        return R.ok();
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:category:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 查询所有的类别树结构，并按照tree的结构返回，方便前端使用
     * @param params
     * @return
     */
    @RequestMapping("/listTree")
    //@RequiresPermissions("product:category:list")
    public R listTree(@RequestParam Map<String, Object> params){
        List<CategoryEntity> list = categoryService.queryPageWithTree(params);

        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    //@RequiresPermissions("product:category:info")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:category:save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:category:update")
    public R update(@RequestBody CategoryEntity category){
//		categoryService.updateById(category);
        categoryService.updateDetail(category);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:category:delete")
    public R delete(@RequestBody Long[] catIds){
        //批量逻辑删除操作
		categoryService.removeCategoryByIds(Arrays.asList(catIds));
//		categoryService.removeByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
