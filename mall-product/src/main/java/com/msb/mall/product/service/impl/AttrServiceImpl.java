package com.msb.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.msb.mall.common.constant.ProductConstant;
import com.msb.mall.product.dao.AttrAttrgroupRelationDao;
import com.msb.mall.product.entity.AttrAttrgroupRelationEntity;
import com.msb.mall.product.entity.AttrGroupEntity;
import com.msb.mall.product.entity.CategoryEntity;
import com.msb.mall.product.service.AttrAttrgroupRelationService;
import com.msb.mall.product.service.AttrGroupService;
import com.msb.mall.product.service.CategoryService;
import com.msb.mall.product.vo.AttrResponseVO;
import com.msb.mall.product.vo.AttrVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.mall.common.utils.PageUtils;
import com.msb.mall.common.utils.Query;

import com.msb.mall.product.dao.AttrDao;
import com.msb.mall.product.entity.AttrEntity;
import com.msb.mall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrGroupService attrGroupService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveAttr(AttrVO vo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(vo,attrEntity);

        this.save(attrEntity);

        //attr-attrgroup表操作
        if(vo.getAttrGroupId() != null && vo.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(vo.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());

            attrAttrgroupRelationService.save(attrAttrgroupRelationEntity);
        }
    }

    @Override
    public PageUtils queryBasePage(Map<String, Object> params, Long catelogId, String attrType) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();

        wrapper.eq("attr_type","base".equalsIgnoreCase(attrType)?1:0);
        // 1.根据类别编号查询
        if(catelogId != 0 ){
            wrapper.eq("catelog_id",catelogId);
        }
        // 2.根据key 模糊查询
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            wrapper.and((w)->{
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        // 3.分页查询
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        // 4. 关联的我们需要查询出类别名称和属性组的名称
        List<AttrEntity> records = page.getRecords();
        List<AttrResponseVO> list = records.stream().map((attrEntity) -> {
            AttrResponseVO responseVo = new AttrResponseVO();
            BeanUtils.copyProperties(attrEntity, responseVo);
            // 查询每一条结果对应的 类别名称和属性组的名称
            CategoryEntity categoryEntity = categoryService.getById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                responseVo.setCatelogName(categoryEntity.getName());
            }
            //在attrtype为1时(为base属性)，才做这些
            if("base".equalsIgnoreCase(attrType)){
                // 设置属性组的名称
                AttrAttrgroupRelationEntity entity = new AttrAttrgroupRelationEntity();
                entity.setAttrId(attrEntity.getAttrId());
                // 去关联表中找到对应的属性组ID
                //attrAttrgroupRelationService.query(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrEntity.getAttrId()));
                AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationDao
                        .selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
                if (attrAttrgroupRelationEntity != null && attrAttrgroupRelationEntity.getAttrGroupId() != null) {
                    // 获取到属性组的ID，然后根据属性组的ID我们来查询属性组的名称
                    AttrGroupEntity attrGroupEntity = attrGroupService.getById(attrAttrgroupRelationEntity.getAttrGroupId());
                    responseVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            return responseVo;
        }).collect(Collectors.toList());
        pageUtils.setList(list);
        return pageUtils;
    }

    /**
     * 根据规格参数ID查询对应的详细信息
     * 1.规格参数的具体信息
     * 2.关联的属性组信息
     * 3.关联的类别信息
     * @param attrId
     * @return
     */
    @Override
    public AttrResponseVO getAttrInfo(Long attrId) {
        // 声明返回的对象
        AttrResponseVO responseVo = new AttrResponseVO();
        // 1.根据ID查询规格参数的基本信息
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity,responseVo);
        // 2.查询关联的属性组信息 中间表
        if(attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
            if(relationEntity != null){
                AttrGroupEntity attrGroupEntity = attrGroupService.getById(relationEntity.getAttrGroupId());
                responseVo.setAttrGroupId(attrGroupEntity.getAttrGroupId());
                if(attrGroupEntity != null){
                    responseVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
        }
        // 3.查询关联的类别信息
        Long catelogId = attrEntity.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        responseVo.setCatelogPath(catelogPath);

        CategoryEntity categoryEntity = categoryService.getById(catelogId);
        if(categoryEntity!=null){
            responseVo.setCatelogName(categoryEntity.getName());
        }
        return responseVo;
    }

    @Transactional
    @Override
    public void updateBaseAttr(AttrVO attr) {
        AttrEntity entity = new AttrEntity();
        BeanUtils.copyProperties(attr,entity);
        // 1.更新基本数据
        this.updateById(entity);
        // 2.修改分组关联的关系
        if(entity.getAttrType()==ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrId(entity.getAttrId());
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            // 判断是否存在对应的数据
            Integer count = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attr.getAttrId()));
            if(count > 0){
                // 说明有记录，直接更新
                attrAttrgroupRelationDao.update(relationEntity,new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attr.getAttrId()));
            }else{
                // 说明没有记录，直接插入
                attrAttrgroupRelationDao.insert(relationEntity);
            }
        }
    }

    @Override
    @Transactional
    public void removeByIdsDetails(Long[] attrIds) {
        for (Long attrId : attrIds) {
            AttrEntity byId = this.getById(attrId);
            if(byId != null && byId.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
                //删除中间表的信息
                attrAttrgroupRelationDao.delete(
                        new UpdateWrapper<AttrAttrgroupRelationEntity>()
                                .eq("attr_id",byId.getAttrId()));
            }
        }
        this.removeByIds(Arrays.asList(attrIds));
    }

    @Override
    public List<AttrEntity> getRelationAttr(Long attrGroupId) {
        //根据属性组id查到属性信息
        List<AttrAttrgroupRelationEntity> list = attrAttrgroupRelationDao
                .selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId));

        //根据查到的属性id数组获取对应的详细信息
        List<AttrEntity> attrEntities = list.stream()
                .map(entity -> this.getById(entity.getAttrId()))
                .filter(entity->entity!=null)
                .collect(Collectors.toList());
        return attrEntities;
    }


}