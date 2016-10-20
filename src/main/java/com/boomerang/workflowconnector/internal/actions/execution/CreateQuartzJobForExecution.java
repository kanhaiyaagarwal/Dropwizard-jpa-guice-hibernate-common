package com.boomerang.workflowconnector.internal.actions.execution;

import com.boomerang.workflowconnector.jobs.CreateAndExecuteRootNodeJob;
import com.boomerang.workflowconnector.requestresponse.CreateQuartzScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.quartz.*;

import javax.ws.rs.BadRequestException;

import static org.quartz.CronScheduleBuilder.cronSchedule;
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

    public CreateQuartzJobForExecution withRequest(CreateQuartzScheduleRequest request){
        this.projectName = request.getProjectName();
        this.schedule = request.getSchedule();
        return this;
    }
    //    todo: handle exceptions for cron format and already exisiting triggers
    public void invoke()  {
        try {
            JobDetail job = newJob(CreateAndExecuteRootNodeJob.class)
                    .withIdentity(projectName + schedule, "group1")
                    .build();
            JobDataMap jobDataMap=  job.getJobDataMap();
            jobDataMap.put("projectName", projectName);
            Trigger trigger = newTrigger()
                    .withIdentity(projectName + schedule, "group1")
                    .startNow()
                    .withSchedule(cronSchedule(schedule))
                    .build();


            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new BadRequestException(e);
        }
    }
}
