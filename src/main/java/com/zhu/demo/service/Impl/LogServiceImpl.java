package com.zhu.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhu.demo.dao.LogMapper;
import com.zhu.demo.entity.Log;
import com.zhu.demo.service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class LogServiceImpl implements LogService {
    @Resource
    private LogMapper logMapper;

    @Override
    @Transactional
    public IPage<Log> getAll(Log log) {
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>(log);
        Page<Log> logPage = new Page<>(log.getCurrent(), log.getSize());
        IPage<Log> list = logMapper.selectPage(logPage, queryWrapper);
        return list;
    }

    @Override
    @Transactional
    public void add(Log log) {
        logMapper.insert(log);
    }
}
