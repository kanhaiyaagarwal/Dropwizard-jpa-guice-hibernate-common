package com.boomerang.workflowconnector.internal.repositories.impl;

import com.boomerang.workflowconnector.internal.model.EdgeMappingInstance;
import com.boomerang.workflowconnector.internal.repositories.IEdgeMappingInstanceRepository;
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
public class EdgeMappingInstanceRepository extends BaseRepositoryImpl<EdgeMappingInstance, Long>
        implements IEdgeMappingInstanceRepository {
    @Inject
    public EdgeMappingInstanceRepository(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

    @Override
    public List<EdgeMappingInstance> findAllByProjectIdandExecId(Long projectId, Long projectExecId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("projectId", projectId);
        params.put("projectExecId", projectExecId);
        return findALLByNamedQuery("EdgeMappingInstanceRepository.findAllByProjectIdandExecId",params);
    }

    @Override
    public List<EdgeMappingInstance> findAllByExecId(Long projectExecId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("projectExecId", projectExecId);
        return findALLByNamedQuery("EdgeMappingInstanceRepository.findAllByExecId",params);      }

    @Override
    public List<Long> getParentIdListByChildIdandExecId(Long childId, Long projectExecId) {
        List<Long> resultList = getEntityManager().createNamedQuery(
                "EdgeMappingInstanceRepository.getParentIdListByChildIdandExecId")
                .setParameter("childId", childId)
                .setParameter("projectExecId", projectExecId)
                .getResultList();
        return resultList;
    }

    @Override
    public List<Long> getChildIdListByParentIdandExecId(Long parentId, Long projectExecId) {
        List<Long> resultList = getEntityManager().createNamedQuery(
                "EdgeMappingInstanceRepository.getChildIdListByParentIdandExecId")
                .setParameter("parentId", parentId)
                .setParameter("projectExecId", projectExecId)
                .getResultList();
        return resultList;
    }

}
