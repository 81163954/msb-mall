/**
  * Copyright 2022 bejson.com 
  */
package com.msb.mall.product.vo.SpuInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2022-12-07 15:28:4
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class SpuInfoVo {

    private Long id;
    private String spuName;
    private String spuDescription;
    private long catalogId;
    private String catalogName;
    private long brandId;
    private String brandName;
    private BigDecimal weight;
    private Integer publishStatus;
    private List<String> decript;
    private List<String> images;
    private Bounds bounds;
    private List<BaseAttrs> baseAttrs;
    private List<Skus> skus;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    private Date updateTime;
}