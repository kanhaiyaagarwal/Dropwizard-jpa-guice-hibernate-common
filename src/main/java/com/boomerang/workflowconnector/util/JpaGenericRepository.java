package com.boomerang.workflowconnector.util;

/**
 * Created by kanhaiya on 21/09/16.
 */
import java.util.Optional;

        import java.io.Serializable;
        import java.util.List;
        import java.util.Map;

public interface JpaGenericRepository<T, ID extends Serializable> {

    public void persist(T entity);

    public void persist(Iterable<? extends T> entities);

    public <S extends T> S merge(S entity);

    public <S extends T> Iterable<S> merge(Iterable<S> entities);

    public List<T> findAll();

    public Optional<T> findOneByNamedQuery(String queryName,
                                           Map<String, Object> parameters);

    public Optional<T> findOne(ID id);

    public void delete(T t);

    public void delete(Iterable<? extends T> entities);

    @Deprecated
    /**
     * Use entity manager instead.
     *
     * Will be removed in the 2.0 release
     */
    public void flushAndClear();

}


