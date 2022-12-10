package com.msb.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.product.entity.SpuInfoEntity;
import com.msb.mall.product.vo.SpuInfo.SpuInfoVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-03 12:26:44
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuInfoVo vo);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

