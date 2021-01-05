package com.mp.demo.cron;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

// @Component
@Slf4j
@Data
public class ScheduledUtil implements SchedulingConfigurer
{

    private String cron = "0/10 * * * * ?";

    private String name = "测试";

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar)
    {
        scheduledTaskRegistrar.addTriggerTask(doTask(), getTrigger());
    }

    /**
     * 业务执行方法
     * @return
     */
    private Runnable doTask()
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // 业务逻辑
                log.info(name + ",时间为:" + simpleDateFormat.format(new Date()));
            }
        };
    }

    /**
     * 业务触发器
     * @return
     */
    private Trigger getTrigger()
    {
        return new Trigger()
        {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext)
            {
                // 触发器
                CronTrigger trigger = new CronTrigger(cron);
                return trigger.nextExecutionTime(triggerContext);
            }
        };
    }
}