package com.msb.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-04 12:14:15
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

