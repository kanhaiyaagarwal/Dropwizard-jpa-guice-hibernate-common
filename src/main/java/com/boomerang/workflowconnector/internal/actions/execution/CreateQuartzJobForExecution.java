package com.boomerang.workflowconnector.internal.actions.execution;

import com.boomerang.workflowconnector.jobs.CreateAndExecuteRootNodeJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by kanhaiya on 18/10/16.
 */
@RequiredArgsConstructor(onConstructor = @__(@com.google.inject.Inject))
public class CreateQuartzJobForExecution {
    private final Scheduler scheduler;
    private String projectName;
    private String schedule;

    public CreateQuartzJobForExecution withParameters(String projectName, String schedule){
        this.projectName = projectName;
        this.schedule = schedule;
        return this;
    }
    public void invoke() throws SchedulerException {
        scheduler.start();
        JobDetail job = newJob(CreateAndExecuteRootNodeJob.class)
                .withIdentity(projectName + schedule, "group1")
                .build();
        JobDataMap jobDataMap=  job.getJobDataMap();
        jobDataMap.put("projectName", projectName);
//todo: put in schedule in the api
        Trigger trigger = newTrigger()
                .withIdentity(projectName + schedule, "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(4)
                        .withRepeatCount(2))
                .build();

        scheduler.scheduleJob(job, trigger);
    }
}
