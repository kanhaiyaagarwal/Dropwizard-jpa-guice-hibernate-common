package com.boomerang.workflowconnector.jobs;

import com.boomerang.workflowconnector.internal.actions.execution.CreateExecutionProjectDag;
import com.boomerang.workflowconnector.internal.actions.execution.ExecuteRootNodeAction;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by kanhaiya on 03/10/16.
 */
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@com.google.inject.Inject))
public class CreateAndExecuteRootNodeJob implements Job {
    private final Provider<CreateExecutionProjectDag> createExecutionProjectDagProvider;
    private final Provider<ExecuteRootNodeAction> executeRootNodeActionProvider;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String projectName = dataMap.getString("projectName");

        log.info("executing project and its root node" + projectName);

        Long execId = createExecutionProjectDagProvider.get().withName(projectName).invoke();
        executeRootNodeActionProvider.get().withParameters(execId).invoke();
    }
}