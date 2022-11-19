package com.msb.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.product.entity.AttrEntity;
import com.msb.mall.product.vo.AttrResponseVO;
import com.msb.mall.product.vo.AttrVO;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-03 12:26:44
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVO attr);

    PageUtils queryBasePage(Map<String, Object> params, Long catelogId, String attrType);

    AttrResponseVO getAttrInfo(Long attrId);

    void updateBaseAttr(AttrVO attr);

    void removeByIdsDetails(Long[] attrIds);

    List<AttrEntity> getRelationAttr(Long attrGroupId);
}

