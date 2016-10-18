package com.boomerang.workflowconnector.internal.actions.execution;

import com.boomerang.workflowconnector.internal.repositories.impl.EdgeMappingInstanceRepository;
import com.boomerang.workflowconnector.internal.repositories.impl.NodeExecutionRepository;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by kanhaiya on 13/10/16.
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CheckParentNodeDependenciesAction {
    private final EdgeMappingInstanceRepository repository;
    private final NodeExecutionRepository nodeExecutionRepository;
    private Long execId;
    private Long nodeId;

    public CheckParentNodeDependenciesAction withParameters(Long execId, Long nodeId){
        this.nodeId = nodeId;
        this.execId = execId;
        return this;
    }

    public Boolean invoke(){
        List<Long> parentNodeList = repository.getParentIdListByChildIdandExecId(nodeId, execId);
        int count = nodeExecutionRepository.findCountOfNotCompletedNodeFromList(parentNodeList,execId);
        return count>0?false:true;
    }

}
