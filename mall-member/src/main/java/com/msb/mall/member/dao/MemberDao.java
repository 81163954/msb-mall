package com.msb.mall.member.dao;

import com.msb.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-04 12:13:20
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
