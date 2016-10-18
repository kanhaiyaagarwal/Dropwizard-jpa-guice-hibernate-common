package com.boomerang.workflowconnector.internal.actions.execution;

import com.boomerang.workflowconnector.config.AzkabanClientConfiguration;
import com.boomerang.workflowconnector.internal.enums.Status;
import com.boomerang.workflowconnector.internal.model.NodeExecutionJob;
import com.boomerang.workflowconnector.internal.repositories.impl.NodeExecutionRepository;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import javax.ws.rs.BadRequestException;
import java.util.Optional;

/**
 * Created by kanhaiya on 13/10/16.
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Transactional
public class CompleteNodeExecutionAction {

//    todo: check why transaction not working on resource level
    private final NodeExecutionRepository repository;
    private Long execId;
    private Long nodeId;
    private final Provider<ExecuteAllChildNodeAction> executeAllChildNodeActionProvider;

    public CompleteNodeExecutionAction withParameters(Long execId, Long nodeId){
        this.execId = execId;
        this.nodeId = nodeId;
        return this;
    }

    public void invoke(){
        Optional<NodeExecutionJob> nodeExecutionJob = repository.findByProjectExecIdAndNodeId(execId,nodeId);
        if(!nodeExecutionJob.isPresent()){
            throw new BadRequestException("No node with execId and node ID found");
        }
        if(nodeExecutionJob.get().getStatus() != Status.RUNNING){
            throw new BadRequestException("Node not is RUNNING state");
        }
        executeAllChildNodeActionProvider.get().withParameters(execId, nodeId).invoke();

        nodeExecutionJob.get().setStatus(Status.COMPLETED);


    }


}
