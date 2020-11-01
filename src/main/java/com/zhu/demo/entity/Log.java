package com.zhu.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("log")
public class Log implements Serializable {

    private Integer id;

    private String type;

    private String title;

    private String method;

    private String params;

    private String remark;

    private String createBy;

    private Date createDate;

    @TableField(exist = false)
    private Long current = 1L;

    @TableField(exist = false)
    private Long size = 8L;
}

