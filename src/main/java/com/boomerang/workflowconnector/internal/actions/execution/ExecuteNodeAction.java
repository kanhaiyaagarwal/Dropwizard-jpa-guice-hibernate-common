package com.boomerang.workflowconnector.internal.actions.execution;

import com.boomerang.workflowconnector.config.AzkabanClientConfiguration;
import com.boomerang.workflowconnector.internal.enums.Status;
import com.boomerang.workflowconnector.internal.model.NodeExecutionJob;
import com.boomerang.workflowconnector.internal.repositories.impl.NodeExecutionRepository;
import com.boomerang.workflowconnector.util.StringToHashMapConvertor;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;

import javax.ws.rs.BadRequestException;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by kanhaiya on 29/09/16.
 */
//todo: change error statement

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ExecuteNodeAction {

    private final NodeExecutionRepository repository;
    private Long execId;
    private Long nodeId;
    private final AzkabanClientConfiguration azkabanClientConfiguration;
    private final Provider<CheckParentNodeDependenciesAction> checkParentNodeDependenciesActionProvider;

    public ExecuteNodeAction withParameters(Long execId, Long nodeId){
        this.execId = execId;
        this.nodeId = nodeId;
        return this;
    }

    public void invoke(){

        if(checkParentNodeDependenciesActionProvider.get().withParameters(execId,nodeId).invoke())
        {
            try{
                log.info("before json response" + azkabanClientConfiguration.toString() );
                azkabanClientConfiguration.setSessionId();
                triggerJob();

                log.info("the session id is " +  azkabanClientConfiguration.getSessionId());
            }catch (UnirestException e){
                log.info("caught exception " + e.getMessage());
            }
        }
    }

    private void triggerJob() throws UnirestException {
        Optional<NodeExecutionJob> nodeExecutionJob = repository.findByProjectExecIdAndNodeId(execId,nodeId);
        if(!nodeExecutionJob.isPresent()){
            throw new BadRequestException("No node with execId and node ID found");
        }
        if(nodeExecutionJob.get().getStatus() != Status.CREATED){
            throw new BadRequestException("Node not is CREATED state");
        }
        HttpResponse<JsonNode> jsonResponse = Unirest.post(nodeExecutionJob.get().getUrl())
                .queryString("session.id", azkabanClientConfiguration.getSessionId())
                .queryString("ajax", "executeFlow")
                .queryString("project", nodeExecutionJob.get().getName() )
                .queryString("job.notification.started.1.url","http://abc.com/api/v2/message?text=wow!!&job=?{job}&status=?{status}")
                .queryString(StringToHashMapConvertor.convert(nodeExecutionJob.get().getAttributes()))
                .asJson();

        nodeExecutionJob.get().setExternalExecId(jsonResponse.getBody().getObject().getLong("execid"));
        nodeExecutionJob.get().setStatus(Status.RUNNING);
    }


}
