package com.msb.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.ware.entity.PurchaseEntity;
import com.msb.mall.ware.vo.MergeVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 采购信息
 *
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-04 12:08:52
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceive(Map<String, Object> params);

    @Transactional
    Integer merge(MergeVO mergeVO);
}

