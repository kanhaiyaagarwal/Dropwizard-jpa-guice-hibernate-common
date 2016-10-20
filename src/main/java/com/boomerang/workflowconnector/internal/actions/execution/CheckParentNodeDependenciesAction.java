package com.boomerang.workflowconnector.internal.actions.execution;

import com.boomerang.workflowconnector.internal.repositories.IEdgeMappingInstanceRepository;
import com.boomerang.workflowconnector.internal.repositories.INodeExecutionRepository;
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
    private final IEdgeMappingInstanceRepository edgeMappingInstanceRepository;
    private final INodeExecutionRepository nodeExecutionRepository;
    private Long execId;
    private Long nodeId;

    public CheckParentNodeDependenciesAction withParameters(Long execId, Long nodeId){
        this.nodeId = nodeId;
        this.execId = execId;
        return this;
    }

    public Boolean invoke(){
        List<Long> parentNodeList = edgeMappingInstanceRepository.getParentIdListByChildIdandExecId(nodeId, execId);
        int count = nodeExecutionRepository.findCountOfNotCompletedNodeFromList(parentNodeList,execId);
        return count>0?false:true;
    }

}
