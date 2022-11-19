package com.msb.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.msb.mall.product.entity.AttrEntity;
import com.msb.mall.product.service.AttrService;
import com.msb.mall.product.service.CategoryService;
import com.msb.mall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.msb.mall.product.entity.AttrGroupEntity;
import com.msb.mall.product.service.AttrGroupService;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.common.utils.R;



/**
 * 属性分组
 *
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-03 13:00:18
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrService attrService;

    //product/attrgroup/6/attr/relation?t=1668841858469
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable Long attrgroupId){
        List<AttrEntity> list = attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data",list);
    }

    /**
     * 列表
     *       {
     *           page:1, // 当前页
     *           limit:10, // 每页显示的条数
     *           sidx:"id", // 排序的字段
     *           order:"asc/desc", // 排序的方式
     *           key:"小米" // 查询的关键字
     *       }
     */

    @GetMapping("/{catelogId}/withattr")
    public R getAttrgroupWithAttrs(@PathVariable("catelogId") Long catelogId){
        //根据三级分类编号来获取对应的属性组和属性组的属性信息
        List<AttrGroupWithAttrsVo> list =  attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return R.ok().put("data",list);
    }

    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params
            ,@PathVariable("catelogId") Long catelogId){
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params,catelogId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

		//再加上属性中分类信息对应的路径 【2,25,225】
        Long catelogId = attrGroup.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        attrGroup.setCatelogPath(catelogPath);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
