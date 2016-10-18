package com.boomerang.workflowconnector.internal.resource;

import com.boomerang.workflowconnector.internal.actions.defination.CreateProjectDagAction;
import com.boomerang.workflowconnector.internal.actions.defination.GetCompleteProjectDagResponseAction;
import com.boomerang.workflowconnector.internal.actions.execution.CreateExecutionProjectDag;
import com.boomerang.workflowconnector.internal.actions.execution.CreateQuartzJobForExecution;
import com.boomerang.workflowconnector.internal.actions.execution.ExecuteNodeAction;
import com.boomerang.workflowconnector.internal.repositories.impl.ProjectFlowNodeRepository;
import com.boomerang.workflowconnector.requestresponse.ProjectDagResponse;
import com.boomerang.workflowconnector.requestresponse.ProjectRequest;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.status;

/**
 * Created by kanhaiya on 21/09/16.
 */

@Path("/")
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@com.google.inject.Inject))
@Api(value = "ProjectResource", description = "Project Defination")
public class ProjectResource {
    private final Provider<CreateProjectDagAction> createProjectDagActionProvider;
    private final Provider<GetCompleteProjectDagResponseAction> getProjectDagActionProvider;


    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "get api")
    public ProjectDagResponse createUserResource(@PathParam("name") String name){

        return getProjectDagActionProvider.get().withName(name).invoke() ;

    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "create DAG", consumes="application/json")
    public Response createProjectDag(@ApiParam("description") @Valid ProjectRequest request){
        createProjectDagActionProvider.get().withRequest(request).invoke();
        return status(Response.Status.CREATED).build();
    }



}
