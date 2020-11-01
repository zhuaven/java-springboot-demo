package com.zhu.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("product")
public class Product implements Serializable {
    private Integer id;

    private Integer type;//产品类型

    @NotBlank(message = "产品名称必须填写")
    private String name;

    @NotBlank(message = "产品金额必须填写")
    private Double money;

    @NotBlank(message = "产品数量必须填写")
    private Integer number;

    private Integer status;

    private String remark;

    private String createBy;

    private Date createDate;
}
