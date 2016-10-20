package com.boomerang.workflowconnector.internal.actions.execution;

import com.boomerang.workflowconnector.internal.actions.defination.GetAllEdgeMappingAction;
import com.boomerang.workflowconnector.internal.actions.defination.GetProjectAction;
import com.boomerang.workflowconnector.internal.enums.Status;
import com.boomerang.workflowconnector.internal.model.*;
import com.boomerang.workflowconnector.internal.repositories.IEdgeMappingInstanceRepository;
import com.boomerang.workflowconnector.internal.repositories.INodeExecutionRepository;
import com.boomerang.workflowconnector.internal.repositories.IProjectExecutionRepository;
import com.google.inject.Inject;
import com.google.inject.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.util.List;

/**
 * Created by kanhaiya on 28/09/16.
 */
//todo: Creation to be abstracted out more with each repository having its own module.

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CreateExecutionProjectDag {
    private final IProjectExecutionRepository projectExecutionRepository;
    private final IEdgeMappingInstanceRepository edgeMappingInstanceRepository;
    private final INodeExecutionRepository nodeExecutionRepository;
    private final Provider<GetProjectAction> getProjectActionProvider;
    private final Provider<GetAllEdgeMappingAction> getAllEdgeMappingActionProvider;
    private Project project;
    private final ModelMapper modelMapper;

    public CreateExecutionProjectDag withProjectId(Long projectId){
        this.project = getProjectActionProvider.get().withId(projectId).invoke();
        return this;
    }

    public CreateExecutionProjectDag withName(String name){
        this.project = getProjectActionProvider.get().withName(name).invoke();
        return this;
    }

    public Long invoke(){
        log.info("the project we got" + project.toString());
        Long execId = createProjectExecutionJob();
        CreateNodeExecutionJobs(execId);
        CreateAllEdgeMappingInstance(execId);
        return execId;
    }

    private Long createProjectExecutionJob() {
        ProjectExecutionJob projectExecutionJob = new ProjectExecutionJob();
        projectExecutionJob.setName(project.getName());
        projectExecutionJob.setProjectId(project.getId());
        projectExecutionJob.setStatus(Status.CREATED);

        projectExecutionRepository.persist(projectExecutionJob);
        return projectExecutionJob.getId();
    }


    private void CreateNodeExecutionJobs(Long execId) {
        for(ProjectFlowNode projectFlowNode: project.getProjectFlowNodes()){
            NodeExecutionJob nodeExecutionJob = mapNodeExecutionJob(projectFlowNode);
            nodeExecutionJob.setProjectExecId(execId);
            nodeExecutionRepository.persist(nodeExecutionJob);
        }
    }

    private NodeExecutionJob mapNodeExecutionJob(ProjectFlowNode projectFlowNode) {
        NodeExecutionJob nodeExecutionJob = new NodeExecutionJob();
        nodeExecutionJob.setAttributes(projectFlowNode.getAttributes());
        nodeExecutionJob.setName(projectFlowNode.getName());
        nodeExecutionJob.setProjectId(projectFlowNode.getProjectId());
        nodeExecutionJob.setNodeId(projectFlowNode.getId());
        nodeExecutionJob.setUrl(projectFlowNode.getUrl());
        nodeExecutionJob.setIsRoot(projectFlowNode.getIsRoot());

        return nodeExecutionJob;
    }

    private void CreateAllEdgeMappingInstance(Long execId) {
        List<EdgeMapping> edgeMappingList = getAllEdgeMappingActionProvider
                .get()
                .withprojectId(project.getId())
                .invoke();
        for (EdgeMapping edgeMapping : edgeMappingList){
            createEdgeMappingInstance(edgeMapping, execId);
        }
    }

    private void createEdgeMappingInstance(EdgeMapping edgeMapping, Long execId) {
        EdgeMappingInstance edgeMappingInstance = modelMapper.map(edgeMapping, EdgeMappingInstance.class);
        edgeMappingInstance.setProjectExecId(execId);
        edgeMappingInstance.setId(null);
        edgeMappingInstanceRepository.persist(edgeMappingInstance);
    }


}
