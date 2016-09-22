package com.boomerang.workflowconnector.dao;

import com.boomerang.workflowconnector.model.Project;
import com.boomerang.workflowconnector.util.SimpleJpaGenericRepository;
import com.google.inject.Provider;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Created by kanhaiya on 21/09/16.
 */
public class ProjectDao  extends SimpleJpaGenericRepository<Project, Long> {


    @Inject
    public ProjectDao(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }


//    public String findByExternalRefId( String externalRefId) {
//        Map<String, Object> params = Maps.newHashMap();
//        params.put("externalRefId", externalRefId);
//        params.put("type", "asdas");
//        Query listQuery = getEntityManager().createNamedQuery("TransactionRepository.findOpenExpiredTransactions");
//
//        return findOneByNamedQuery("TransactionRepository.findByExternalRefId", params);
//    }

    public Optional<Project> findById(Long id) {
        return (findOne(id));

    }
}
