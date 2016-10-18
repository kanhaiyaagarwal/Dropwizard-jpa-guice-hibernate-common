package com.boomerang.workflowconnector.internal.repositories.impl;

import com.boomerang.workflowconnector.internal.model.EdgeMapping;
import com.boomerang.workflowconnector.internal.repositories.IEdgeMappingRepository;
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
public class EdgeMappingRepository extends BaseRepositoryImpl<EdgeMapping, Long>
        implements IEdgeMappingRepository {

    @Inject
    public EdgeMappingRepository(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }


    @Override
    public List<EdgeMapping> findAllByParentId(Long parentId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("parentId", parentId);

        return findALLByNamedQuery("EdgeMappingRepository.findAllByParentId",params);
    }

    @Override
    public List<EdgeMapping> findAllByChildId(Long childId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("childId", childId);

        return findALLByNamedQuery("EdgeMappingRepository.findAllByChildId",params);
    }

    @Override
    public List<EdgeMapping> findAllByProjectId(Long projectId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("projectId", projectId);

        return findALLByNamedQuery("EdgeMappingRepository.findAllByProjectId",params);
    }

    @Override
    public List<Long> getParentIdListByChildId(Long childId) {
        List<Long> resultList = getEntityManager().createNamedQuery(
                "EdgeMappingRepository.getParentIdListByChildId")
                .setParameter("childId", childId)
                .getResultList();
        ;
        return resultList;
    }
}
