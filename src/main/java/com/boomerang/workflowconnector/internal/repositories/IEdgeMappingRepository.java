package com.boomerang.workflowconnector.internal.repositories;

import com.boomerang.workflowconnector.internal.model.EdgeMapping;
import com.boomerang.workflowconnector.internal.lib.IBaseRepository;

import java.util.List;

/**
 * Created by kanhaiya on 23/09/16.
 */
public interface IEdgeMappingRepository extends IBaseRepository<EdgeMapping, Long>{
    List<EdgeMapping> findAllByParentId(Long parentId);
    List<EdgeMapping> findAllByChildId(Long childId);
    List<EdgeMapping> findAllByProjectId(Long projectId);
    List<Long> getParentIdListByChildId(Long childId);

}
