package com.boomerang.workflowconnector.internal.repositories.impl;

import com.boomerang.workflowconnector.internal.model.Project;
import com.boomerang.workflowconnector.internal.repositories.IProjectRepository;
import com.boomerang.workflowconnector.internal.lib.BaseRepositoryImpl;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.Optional;

/**
 * Created by kanhaiya on 21/09/16.
 */
public class ProjectRepository extends BaseRepositoryImpl<Project, Long> implements IProjectRepository {


    @Inject
    public ProjectRepository(
            Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

    @Override
    @Transactional
    public Optional<Project> findByName(String name) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("name", name);

        return findOneByNamedQuery("ProjectRepository.findByName",params);
    }
}
