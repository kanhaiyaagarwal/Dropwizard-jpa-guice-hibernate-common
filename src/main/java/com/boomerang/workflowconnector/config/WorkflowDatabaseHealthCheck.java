package com.boomerang.workflowconnector.config;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.hubspot.dropwizard.guice.InjectableHealthCheck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by kanhaiya on 04/10/16.
 */
public class WorkflowDatabaseHealthCheck extends InjectableHealthCheck {
    private final Provider<EntityManagerFactory> entityManagerFactoryProvider;

    @Inject
    public WorkflowDatabaseHealthCheck(Provider<EntityManagerFactory> entityManagerFactoryProvider) {
        this.entityManagerFactoryProvider = entityManagerFactoryProvider;
    }

    @Override
    protected Result check() throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactoryProvider.get().createEntityManager();

            entityManager.createNativeQuery("select 1").getFirstResult();

        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return Result.healthy();
    }

    @Override
    public String getName() {
        return "database";
    }
}
