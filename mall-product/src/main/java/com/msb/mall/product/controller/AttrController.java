package com.msb.mall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.msb.mall.product.vo.AttrResponseVO;
import com.msb.mall.product.vo.AttrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msb.mall.product.entity.AttrEntity;
import com.msb.mall.product.service.AttrService;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.common.utils.R;



/**
 * 商品属性
 *
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-03 13:00:18
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 列表
     */
    @RequestMapping("/{attrType}/list/{catelogId}")
    //@RequiresPermissions("product:attr:list")
    public R baseList(@RequestParam Map<String, Object> params
            ,@PathVariable("catelogId") Long catelogId
            ,@PathVariable("attrType") String attrType){

        PageUtils page = attrService.queryBasePage(params,catelogId,attrType);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
//		AttrEntity attr = attrService.getById(attrId);

        AttrResponseVO attrResponseVO = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", attrResponseVO);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVO attr){
//		attrService.save(attr);
        attrService.saveAttr(attr);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVO attr){
//		attrService.updateById(attr);
        attrService.updateBaseAttr(attr);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
//		attrService.removeByIds(Arrays.asList(attrIds));

        attrService.removeByIdsDetails(attrIds);
        return R.ok();
    }

}
