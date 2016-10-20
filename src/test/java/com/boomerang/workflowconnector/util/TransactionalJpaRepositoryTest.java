package com.boomerang.workflowconnector.util;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.PersistService;
import org.junit.After;
import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by kanhaiya on 19/10/16.
 */
public class TransactionalJpaRepositoryTest {

    @Inject
    protected  Provider<PersistService> persistServiceProvider;

    @Inject
    protected Provider<EntityManager> entityManagerProvider;

    protected EntityTransaction transaction;

    @Before
    public final void setUpTransaction() throws Exception {
        persistServiceProvider.get().start();
        transaction = entityManagerProvider.get().getTransaction();
        transaction.begin();
    }

    @After
    public final void tearDownTransaction() throws Exception {
        transaction.rollback();
    }

    /**
     * Saves the passed object.
     *
     * @param object T object to be persisted.
     */
    protected <T> T persist(T object){
        entityManagerProvider.get().persist(object);
        return object;
    }
}
