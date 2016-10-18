package com.boomerang.workflowconnector.internal.repositories;

import com.boomerang.workflowconnector.internal.model.ProjectFlowNode;
import com.boomerang.workflowconnector.internal.lib.IBaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by kanhaiya on 23/09/16.
 */
public interface IProjectFlowNodeRepository extends IBaseRepository<ProjectFlowNode, Long> {

    Optional<ProjectFlowNode> findByName(String name);
    Optional<ProjectFlowNode> findByNameAndProjectId(String name, Long projectId) ;
    List<Long> getIdListByNameListAndProjectId(List<String> names, Long projectId);

}
