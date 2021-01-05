package com.mp.demo.serivce.impl;

import com.mp.demo.cron.ScheduledUtil;
import com.mp.demo.serivce.TaskService;
import org.springframework.stereotype.Service;

@Service("test1")
public class TaskServiceImpl extends ScheduledUtil implements TaskService
{

}