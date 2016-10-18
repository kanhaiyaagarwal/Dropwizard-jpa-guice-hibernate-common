package com.boomerang.workflowconnector.internal.lib;

/**
 * Created by kanhaiya on 21/09/16.
 */
import java.util.Optional;

        import java.io.Serializable;
        import java.util.List;
        import java.util.Map;

public interface IBaseRepository<T, ID extends Serializable> {

    public Optional<T> findOne(ID id);

    public List<T> findAll();

    public Optional<T> findOneByNamedQuery(String queryName,
                                           Map<String, Object> parameters);
    public void persist(T entity);

    public void delete(T t);

    //To Add Pagination
    public List<T> findALLByNamedQuery(String queryName,
                                       Map<String, Object> parameters);

}


