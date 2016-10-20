package com.boomerang.workflowconnector.internal.actions.execution;

import com.boomerang.workflowconnector.internal.model.NodeExecutionJob;
import com.boomerang.workflowconnector.internal.repositories.INodeExecutionRepository;
import com.google.inject.Inject;
import com.google.inject.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by kanhaiya on 18/10/16.
 */

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ExecuteRootNodeAction {

    private final INodeExecutionRepository repository;
    private Long execId;
    private final Provider<ExecuteNodeAction> executeNodeActionProvider;

    public ExecuteRootNodeAction withParameters(Long execId){
        this.execId = execId;
        return this;
    }

    public void invoke(){
        List<NodeExecutionJob> rootJobList = repository.findRootsByProjectExecId(execId);
        for(NodeExecutionJob rootJob : rootJobList){

            executeNodeActionProvider.get().withParameters(execId,rootJob.getNodeId()).invoke();
        }
    }

}
