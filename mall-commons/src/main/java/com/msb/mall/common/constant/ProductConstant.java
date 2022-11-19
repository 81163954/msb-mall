package com.msb.mall.common.constant;

public class ProductConstant {
    //    public static final Integer ATTR_TYPE_BASE = 1;
//    public static final Integer ATTR_TYPE_SALE = 0;
    public enum AttrEnum {
        ATTR_TYPE_BASE(1, "base"), ATTR_TYPE_SALE(0, "sale");
        private Integer code;
        private String message;

        public Integer getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        AttrEnum(Integer code, String message) {
            this.code = code;
            this.message = message;

        }
    }
}
