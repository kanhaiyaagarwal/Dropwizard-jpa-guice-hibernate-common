package com.boomerang.workflowconnector.internal.resource;

import com.boomerang.workflowconnector.internal.actions.defination.CreateProjectDagAction;
import com.boomerang.workflowconnector.internal.actions.execution.CompleteNodeExecutionAction;
import com.boomerang.workflowconnector.requestresponse.ProjectRequest;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.status;

/**
 * Created by kanhaiya on 13/10/16.
 */
@Slf4j
@Path("/callback")
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@com.google.inject.Inject))
@Api(value = "CallbackResource", description = "Callback Resource")
public class CallbackResource {

    private final Provider<CompleteNodeExecutionAction> completeNodeExecutionActionProvider;

    @POST
    @Path("/complete/node/{nodeId}/execid/{execId}")
    @ApiOperation(value = "Callback Api")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeNodeStatusToCompleted(@PathParam("nodeId") Long nodeId, @PathParam("execId") Long execId){
        completeNodeExecutionActionProvider.get().withParameters(execId, nodeId).invoke();
        return status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response testCallback(){
        log.info("this is working  >>>>>");
        return status(Response.Status.ACCEPTED).build();
    }
}
