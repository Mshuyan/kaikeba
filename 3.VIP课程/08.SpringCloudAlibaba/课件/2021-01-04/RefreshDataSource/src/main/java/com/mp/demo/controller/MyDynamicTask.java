package com.mp.demo.controller;

import com.mp.demo.cron.ScheduledUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class MyDynamicTask
{

    private static Logger log = LoggerFactory.getLogger(MyDynamicTask.class);

    @Autowired
    @Qualifier("test1")
    private ScheduledUtil scheduledUtil1;

    @Autowired
    @Qualifier("test2")
    private ScheduledUtil scheduledUtil2;

    @Autowired
    @Qualifier("test3")
    private ScheduledUtil scheduledUtil3;

    @GetMapping
    public void getCron(String time, String name, Integer task)
    {
        if (task == 1)
        {
            scheduledUtil1.setCron(time);
            scheduledUtil1.setName(name);
        }
        else if (task == 2)
        {
            scheduledUtil2.setCron(time);
            scheduledUtil2.setName(name);
        }
        else if (task == 3)
        {
            scheduledUtil3.setCron(time);
            scheduledUtil3.setName(name);
        }
    }
}