package com.zhu.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhu.demo.entity.Log;

public interface LogService {
    IPage<Log> getAll(Log log);

    void add(Log log);
}
