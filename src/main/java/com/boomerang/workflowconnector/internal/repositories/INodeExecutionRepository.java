package com.boomerang.workflowconnector.internal.repositories;

import com.boomerang.workflowconnector.internal.enums.Status;
import com.boomerang.workflowconnector.internal.model.NodeExecutionJob;
import com.boomerang.workflowconnector.internal.lib.IBaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by kanhaiya on 23/09/16.
 */
public interface INodeExecutionRepository extends IBaseRepository<NodeExecutionJob, Long> {
    List<NodeExecutionJob>findAllByProjectIdAndStatus(Long projectId, Status status);
    List<NodeExecutionJob>findAllByProjectExecIdAndStatus(Long projectExecId, Status status);
    Optional<NodeExecutionJob>findByProjectExecIdAndNodeId(Long projectExecId, Long nodeId);
    Optional<NodeExecutionJob>findByExternalExecId(Long externalExecId);
    List<NodeExecutionJob>findRootsByProjectExecId(Long externalExecId);
    int findCountOfNotCompletedNodeFromList(List<Long> nodeList , Long projectExecId);
}
