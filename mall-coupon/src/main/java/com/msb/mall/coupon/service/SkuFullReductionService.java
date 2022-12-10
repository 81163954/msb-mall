package com.msb.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.mall.common.dto.SkuReductionDTO;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-04 12:14:58
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionDTO dto);
}

