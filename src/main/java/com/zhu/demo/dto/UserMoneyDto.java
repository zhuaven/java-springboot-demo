package com.zhu.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserMoneyDto {

    private String userId;

    @JsonProperty("username")
    private String name;

    private Integer money;

    private Integer status;

    private String createBy;

    private Date createDate;

    private Long current = 1L;

    private Long size = 8L;
}
