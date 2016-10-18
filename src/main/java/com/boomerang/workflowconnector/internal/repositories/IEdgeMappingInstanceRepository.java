package com.boomerang.workflowconnector.internal.repositories;

import com.boomerang.workflowconnector.internal.model.EdgeMappingInstance;
import com.boomerang.workflowconnector.internal.lib.IBaseRepository;

import java.util.List;

/**
 * Created by kanhaiya on 23/09/16.
 */
public interface IEdgeMappingInstanceRepository extends IBaseRepository<EdgeMappingInstance, Long>{
    List<EdgeMappingInstance> findAllByProjectIdandExecId(Long projectId, Long projectExecId);
    List<EdgeMappingInstance> findAllByExecId(Long projectExecId);
    List<Long> getParentIdListByChildIdandExecId(Long childId,Long projectExecId);
    List<Long> getChildIdListByParentIdandExecId(Long parentId,Long projectExecId);

}
