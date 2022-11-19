package com.msb.mall.order.dao;

import com.msb.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-04 12:14:15
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
