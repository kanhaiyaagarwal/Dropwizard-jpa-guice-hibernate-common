package com.boomerang.workflowconnector.internal.actions.execution;

import com.boomerang.workflowconnector.internal.repositories.impl.EdgeMappingInstanceRepository;
import com.boomerang.workflowconnector.internal.repositories.impl.NodeExecutionRepository;
import com.google.inject.Inject;
import com.google.inject.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.util.List;

/**
 * Created by kanhaiya on 13/10/16.
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ExecuteAllChildNodeAction {
//    private final NodeExecutionRepository repository;
    private final EdgeMappingInstanceRepository repository;
    private Long execId;
    private Long nodeId;
    private final Provider<ExecuteNodeAction> executeNodeActionProvider;

    public ExecuteAllChildNodeAction withParameters(Long execId, Long nodeId){
        this.execId = execId;
        this.nodeId = nodeId;
        return this;
    }

    public void invoke(){
        List<Long> childNodeList = repository.getChildIdListByParentIdandExecId(nodeId,execId);
        for(Long nodeId : childNodeList){
            executeNodeActionProvider.get().withParameters(execId,nodeId).invoke();
        }
    }
}
