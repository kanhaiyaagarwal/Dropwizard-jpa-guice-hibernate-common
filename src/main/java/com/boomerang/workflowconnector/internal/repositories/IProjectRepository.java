package com.boomerang.workflowconnector.internal.repositories;

import com.boomerang.workflowconnector.internal.model.Project;
import com.boomerang.workflowconnector.internal.lib.IBaseRepository;

import java.util.Optional;

/**
 * Created by kanhaiya on 23/09/16.
 */
public interface IProjectRepository extends IBaseRepository<Project, Long> {

    Optional<Project> findByName(String name);
}
