package com.zhu.demo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDto {

    private Integer type;//产品类型

    private String name;

    private Integer money;

    private Integer number;

    private Integer status;

    private String remark;

    private String createBy;

    private Date createDate;

    private Long current = 1L;

    private Long size = 8L;
}
