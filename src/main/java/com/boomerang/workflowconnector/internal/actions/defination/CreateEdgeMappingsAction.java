package com.boomerang.workflowconnector.internal.actions.defination;

import com.boomerang.workflowconnector.internal.model.EdgeMapping;
import com.boomerang.workflowconnector.internal.repositories.impl.EdgeMappingRepository;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by kanhaiya on 27/09/16.
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CreateEdgeMappingsAction {
    private final EdgeMappingRepository repository;
    private Long childId;
    private List<Long> parentIdList;
    private Long projectId;

    public CreateEdgeMappingsAction withParameters(Long projectId, Long childId , List<Long> parentIdList){
        this.projectId = projectId;
        this.childId = childId;
        this.parentIdList = parentIdList;
        return this;
    }

    public void invoke(){
        for(Long parentId : parentIdList){
            EdgeMapping edgeMapping = new EdgeMapping();
            edgeMapping.setProjectId(projectId);
            edgeMapping.setChildId(childId);
            edgeMapping.setParentId(parentId);
            repository.persist(edgeMapping);
        }
    }
}
