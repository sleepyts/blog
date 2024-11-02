package com.ts.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.Mapper.TaskLogMapper;
import com.ts.Model.Entity.TaskLog;
import com.ts.Service.TaskLogService;
import org.springframework.stereotype.Service;

@Service
public class TaskLogServiceImpl extends ServiceImpl<TaskLogMapper, TaskLog> implements TaskLogService {
}
