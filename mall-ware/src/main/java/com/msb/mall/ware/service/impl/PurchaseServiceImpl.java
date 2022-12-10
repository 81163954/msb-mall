package com.msb.mall.ware.service.impl;

import com.msb.mall.common.constant.WareConstant;
import com.msb.mall.ware.entity.PurchaseDetailEntity;
import com.msb.mall.ware.service.PurchaseDetailService;
import com.msb.mall.ware.vo.MergeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.common.utils.Query;

import com.msb.mall.ware.dao.PurchaseDao;
import com.msb.mall.ware.entity.PurchaseEntity;
import com.msb.mall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    PurchaseDetailService detailService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询采购单的状态为 新建 或者 已分配 的采购单信息
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPageUnreceive(Map<String, Object> params) {
        QueryWrapper<PurchaseEntity> queryWrapper = new QueryWrapper<>();
        // 添加查询的条件
        queryWrapper.eq("status",0).or().eq("status",1);
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    /**
     * 完成采购需求的合单操作
     * @param mergeVO
     * @return
     */
    @Transactional
    @Override
    public Integer merge(MergeVO mergeVO) {
        Long purchaseId = mergeVO.getPurchaseId();
        if(purchaseId == null){
            // 新建采购单
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }
        // 判断采购单的状态 只能是新建或者已分配的才能合单 如果是已领取 就不能再合单了
        PurchaseEntity purchaseEntity = this.getById(purchaseId);
        if(purchaseEntity.getStatus() > WareConstant.PurchaseStatusEnum.RECEIVE.getCode()){
            // 该菜单不能合单
            return -1;
        }

        // 整合菜单需求单
        List<Long> items = mergeVO.getItems();
        final long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> list = items.stream().map(i -> {
            PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
            // 更新每一条 需求单的 采购单编号
            detailEntity.setId(i);
            detailEntity.setPurchaseId(finalPurchaseId);
            detailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGED.getCode());
            return detailEntity;
        }).filter(id ->{
            PurchaseDetailEntity item = detailService.getById(id);
            if(item.getStatus() == WareConstant.PurchaseDetailStatusEnum.CREATED.getCode() ||
                    item.getStatus() == WareConstant.PurchaseDetailStatusEnum.ASSIGED.getCode()){
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        if(list != null && list.size() > 0){
            detailService.updateBatchById(list);
        }
        // 更新对应的采购单的更新时间
        PurchaseEntity entity = new PurchaseEntity();
        entity.setId(purchaseId);
        entity.setUpdateTime(new Date());
        this.updateById(entity);
        return 1;
    }

}