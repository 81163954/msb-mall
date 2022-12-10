package com.msb.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.product.entity.AttrAttrgroupRelationEntity;
import com.msb.mall.product.vo.AttrGroupRelationVO;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-03 12:26:44
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(List<AttrGroupRelationVO> vos);
}

