package com.boomerang.workflowconnector.internal.actions.defination;

import com.boomerang.workflowconnector.internal.model.Project;
import com.boomerang.workflowconnector.internal.model.ProjectFlowNode;
import com.boomerang.workflowconnector.internal.repositories.impl.EdgeMappingRepository;
import com.boomerang.workflowconnector.requestresponse.ProjectDagResponse;
import com.boomerang.workflowconnector.requestresponse.ProjectFlowNodeResponse;
import com.boomerang.workflowconnector.util.StringToHashMapConvertor;
import com.google.inject.Inject;
import com.google.inject.Provider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanhaiya on 28/09/16.
 */
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GetCompleteProjectDagResponseAction {

    private final Provider<GetProjectAction> getProjectActionProvider;
    private final EdgeMappingRepository edgeMappingRepository;
    private final ModelMapper modelMapper;
    private ProjectDagResponse response;
//    private final Provider<>

    private String name;

    public GetCompleteProjectDagResponseAction withName(String name){
        this.name = name;
        return this;
    }

    public ProjectDagResponse invoke(){
        Project project = getProjectActionProvider.get().withName(name).invoke();
        response = modelMapper.map(project, ProjectDagResponse.class);
        addFlowNodeToResponse(project);
        return response;
    }

    private void addFlowNodeToResponse(Project project) {
        List<ProjectFlowNode> projectFlowNodeList = project.getProjectFlowNodes();
        List<ProjectFlowNodeResponse> projectFlowNodeResponses = new ArrayList<>();
        modelMapper.addConverter(StringToHashMapConvertor.toHashMap);
        for(ProjectFlowNode projectFlowNode : projectFlowNodeList){
            ProjectFlowNodeResponse projectFlowNodeResponse =
                    modelMapper.map(projectFlowNode, ProjectFlowNodeResponse.class);

            //setting edgeMapping relationship
            projectFlowNodeResponse.setParentNodeIdList(getParentNodeList(projectFlowNode.getId()));

            projectFlowNodeResponses.add(projectFlowNodeResponse);
        }
        response.setProjectFlowNodeRequestList(projectFlowNodeResponses);
    }

    private List<Long> getParentNodeList(Long id) {
        return edgeMappingRepository.getParentIdListByChildId(id);
    }
}
