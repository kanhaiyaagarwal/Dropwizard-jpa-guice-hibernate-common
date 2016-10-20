package com.boomerang.workflowconnector.internal.resource;

import com.boomerang.workflowconnector.internal.actions.execution.CreateExecutionProjectDag;
import com.boomerang.workflowconnector.internal.actions.execution.CreateQuartzJobForExecution;
import com.boomerang.workflowconnector.internal.actions.execution.ExecuteNodeAction;
import com.boomerang.workflowconnector.internal.repositories.impl.ProjectFlowNodeRepository;
import com.boomerang.workflowconnector.requestresponse.CreateQuartzScheduleRequest;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.status;

/**
 * Created by kanhaiya on 18/10/16.
 */
@Slf4j
@Path("/schedular")
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@com.google.inject.Inject))
@Api(value = "SchedularResource", description = "Schedular Resource")
public class SchedularResource {
    private final ProjectFlowNodeRepository projectFlowNodeRepository;
    private final Provider<ExecuteNodeAction> executeNodeActionProvider;
    private final Provider<CreateExecutionProjectDag> createExecutionProjectDagProvider;
    private final Provider<CreateQuartzJobForExecution> createQuartzJobForExecutionProvider;

    @POST
    @Path("/execute/{name}")
    @ApiOperation(value = "Create Execution DAG ")
    public Response executeProjectDag(@PathParam("name") String name){
        createExecutionProjectDagProvider.get().withName(name).invoke();
        return status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/executeNode/{execId}/node/{nodeId}")
    @ApiOperation(value = "Run Execution DAG Root ")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTest(@PathParam("execId") Long execId , @PathParam("nodeId") Long nodeId ) {
        executeNodeActionProvider.get().withParameters(execId, nodeId).invoke();
        return status(Response.Status.CREATED).build();
    }

    @POST
    @ApiOperation(value = "Schedule DAG Execution")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response schedule(@ApiParam("projectName and cron scheduler")
                                 @Valid CreateQuartzScheduleRequest request)
            throws SchedulerException {
        createQuartzJobForExecutionProvider.get().withRequest(request).invoke();
        return status(Response.Status.ACCEPTED).build();
    }
}
