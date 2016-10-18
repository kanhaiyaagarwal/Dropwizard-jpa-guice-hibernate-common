package com.boomerang.workflowconnector.internal.repositories.impl;

import com.boomerang.workflowconnector.internal.enums.Status;
import com.boomerang.workflowconnector.internal.model.NodeExecutionJob;
import com.boomerang.workflowconnector.internal.repositories.INodeExecutionRepository;
import com.boomerang.workflowconnector.internal.lib.BaseRepositoryImpl;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by kanhaiya on 23/09/16.
 */
public class NodeExecutionRepository extends BaseRepositoryImpl<NodeExecutionJob, Long>
        implements INodeExecutionRepository {

    @Inject
    public NodeExecutionRepository(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

    @Override
    public List<NodeExecutionJob> findAllByProjectIdAndStatus(Long projectId, Status status) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("projectId", projectId);
        params.put("status", status.toString());
        return findALLByNamedQuery("NodeExecutionRepository.findAllByProjectIdAndStatus",params );
    }

    @Override
    public List<NodeExecutionJob> findAllByProjectExecIdAndStatus(Long projectExecId, Status status) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("projectExecId", projectExecId);
        params.put("status", status.toString());
        return findALLByNamedQuery("NodeExecutionRepository.findAllByProjectExecIdAndStatus",params );
    }

    @Override
    public Optional<NodeExecutionJob> findByProjectExecIdAndNodeId(Long projectExecId, Long nodeId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("projectExecId", projectExecId);
        params.put("nodeId", nodeId);
        return findOneByNamedQuery("NodeExecutionRepository.findByProjectExecIdAndNodeId",params );
    }

    @Override
    public Optional<NodeExecutionJob> findByExternalExecId(Long externalExecid) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("externalExecid", externalExecid);
        return findOneByNamedQuery("NodeExecutionRepository.findByExternalExecId",params );
    }

    @Override
    public List<NodeExecutionJob> findRootsByProjectExecId(Long projectExecId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("projectExecId", projectExecId);
        params.put("isRoot", true);
        return findALLByNamedQuery("NodeExecutionRepository.findRootsByProjectExecId",params );
    }

    @Override
    public int findCountOfNotCompletedNodeFromList(List<Long> nodeList, Long projectExecId) {

        int count = getEntityManager().createNamedQuery(
                "NodeExecutionRepository.findCountOfNotCompletedNodeFromList")
                .setParameter("projectExecId", projectExecId)
                .setParameter("nodeId", nodeList)
                .setParameter("status", Status.COMPLETED)
                .getFirstResult();
        return count;
    }
}
