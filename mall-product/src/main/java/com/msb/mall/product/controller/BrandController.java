package com.msb.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.msb.mall.common.valid.groups.AddGroupsInterface;
import com.msb.mall.common.valid.groups.UpdateGroupsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.msb.mall.product.entity.BrandEntity;
import com.msb.mall.product.service.BrandService;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.common.utils.R;


/**
 * 品牌
 *
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-03 13:00:18
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;


    @GetMapping("/all")
    public R queryAllBrand(){

        BrandEntity entity = new BrandEntity();
        entity.setName("理想");
        return R.ok().put("brand",entity);
    }

    //save


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:brand:save")
    public R save(@Validated({AddGroupsInterface.class}) @RequestBody BrandEntity brand){
		brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@Validated({UpdateGroupsInterface.class}) @RequestBody BrandEntity brand){
//		brandService.updateById(brand);

        //更新品牌信息，以及其他表的品牌相关数据，比如品牌-分类表中的品牌名称
        brandService.updateDetailById(brand);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
