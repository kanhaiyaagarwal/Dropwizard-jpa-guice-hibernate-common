package com.boomerang.workflowconnector.internal.repositories.impl;

import com.boomerang.workflowconnector.internal.model.ProjectFlowNode;
import com.boomerang.workflowconnector.internal.repositories.IProjectFlowNodeRepository;
import com.boomerang.workflowconnector.internal.lib.BaseRepositoryImpl;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by kanhaiya on 23/09/16.
 */
public class ProjectFlowNodeRepository
        extends BaseRepositoryImpl<ProjectFlowNode, Long>
        implements IProjectFlowNodeRepository {

    @Inject
    public ProjectFlowNodeRepository(Provider<EntityManager> entityManagerProvider){
        super(entityManagerProvider);
    }

    @Override
    public Optional<ProjectFlowNode> findByName(String name) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("name", name);

        return findOneByNamedQuery("ProjectFlowNodeRepository.findByName",params);
    }

    @Override
    public Optional<ProjectFlowNode> findByNameAndProjectId(String name, Long projectId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("name", name);
        params.put("projectId", projectId);
        return findOneByNamedQuery("ProjectFlowNodeRepository.findByNameAndProjectId",params);
    }

    @Override
    public List<Long> getIdListByNameListAndProjectId(List<String> names, Long projectId) {
        List<Long> resultList = getEntityManager().createNamedQuery(
                "ProjectFlowNodeRepository.getIdListByNameListAndProjectId")
                .setParameter("names", names)
                .setParameter("projectId", projectId)
                .getResultList();
        ;
        return resultList;
    }
}
