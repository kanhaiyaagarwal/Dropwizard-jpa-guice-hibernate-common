package com.boomerang.workflowconnector.internal.lib;

/**
 * Created by kanhaiya on 21/09/16.
 */


import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@SuppressWarnings("unchecked")
public class BaseRepositoryImpl<T, ID extends Serializable> implements IBaseRepository<T, ID> {

    private final Provider<EntityManager> entityManagerProvider;
    private final Class<T> entityClass;

    @Inject
    public BaseRepositoryImpl(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
        entityClass = getEntityClass();
    }

    protected EntityManager getEntityManager() {
        return entityManagerProvider.get();
    }

    Class<T> getEntityClass() {
        ParameterizedType genericSuperclass = (ParameterizedType) getGenericSuperClass();
        return (Class<T>) genericSuperclass
                .getActualTypeArguments()[0];
    }

    private Type getGenericSuperClass() {
        // Handle case where the class gets extended (due to proxying)
        Class klass = getClass();
        // Get the immediate subclass of BaseRepositoryImpl
        while (klass != null
                && klass.getSuperclass() != null
                && !klass.getSuperclass().isAssignableFrom(BaseRepositoryImpl.class)) {
            klass = klass.getSuperclass();
        }
        Preconditions.checkNotNull(klass);
        return klass.getGenericSuperclass();
    }

    @Override
    public Optional<T> findOne(ID id) {
        return Optional.ofNullable(getEntityManager().find(entityClass, id));
    }

    @Override
    public List<T> findAll() {
        return getEntityManager()
                .createQuery("select x from " + entityClass.getSimpleName() + " x")
                .getResultList();
    }

    @Override
    public Optional<T> findOneByNamedQuery(String queryName, Map<String, Object> parameters) {
        Query query = getEntityManager().createNamedQuery(queryName);

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        try {
            return Optional.of((T) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    @Override
    //To Add Pagination
    public List<T> findALLByNamedQuery(String queryName, Map<String, Object> parameters) {
        Query query = getEntityManager().createNamedQuery(queryName);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    @Override
    public void persist(T t) {
        getEntityManager().persist(t);
    }

    @Override
    public void delete(T t) {
        getEntityManager().remove(t);
    }


}
