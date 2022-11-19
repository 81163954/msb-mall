package com.msb.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author legu
 * @email 937583499@qq.com
 * @date 2022-08-04 12:13:20
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

