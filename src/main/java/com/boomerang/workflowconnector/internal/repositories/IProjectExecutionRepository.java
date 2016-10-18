package com.boomerang.workflowconnector.internal.repositories;

import com.boomerang.workflowconnector.internal.enums.Status;
import com.boomerang.workflowconnector.internal.model.ProjectExecutionJob;
import com.boomerang.workflowconnector.internal.lib.IBaseRepository;

import java.util.List;

/**
 * Created by kanhaiya on 23/09/16.
 */
public interface IProjectExecutionRepository extends IBaseRepository<ProjectExecutionJob, Long> {
    List<ProjectExecutionJob> findAllByProjectIdAndStatus(Long projectId, Status status);
}
