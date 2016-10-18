package com.boomerang.workflowconnector.internal.repositories.impl;

import com.boomerang.workflowconnector.internal.enums.Status;
import com.boomerang.workflowconnector.internal.model.ProjectExecutionJob;
import com.boomerang.workflowconnector.internal.repositories.IProjectExecutionRepository;
import com.boomerang.workflowconnector.internal.lib.BaseRepositoryImpl;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

/**
 * Created by kanhaiya on 23/09/16.
 */
public class ProjectExecutionRepository extends BaseRepositoryImpl<ProjectExecutionJob, Long>
        implements IProjectExecutionRepository {

    @Inject
    public ProjectExecutionRepository(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

    @Override
    public List<ProjectExecutionJob> findAllByProjectIdAndStatus(Long projectId, Status status) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("projectId", projectId);
        params.put("status", status.toString());
        return findALLByNamedQuery("ProjectExecutionRepository.findAllByProjectIdAndStatus",params );
    }
}
