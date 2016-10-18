package com.boomerang.workflowconnector.internal.actions.defination;

import com.boomerang.workflowconnector.internal.model.EdgeMapping;
import com.boomerang.workflowconnector.internal.repositories.impl.EdgeMappingRepository;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by kanhaiya on 29/09/16.
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GetAllEdgeMappingAction {

    private final EdgeMappingRepository edgeMappingRepository;
    private Long projectId;

    public GetAllEdgeMappingAction withprojectId(Long projectId){
        this.projectId = projectId;
        return this;
    }

    public List<EdgeMapping> invoke(){
        return edgeMappingRepository.findAllByProjectId(projectId);
    }
}
