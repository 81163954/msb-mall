package com.msb.mall.order.dao;

import com.msb.mall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-04 12:14:15
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
