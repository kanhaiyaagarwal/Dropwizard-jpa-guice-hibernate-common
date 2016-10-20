package com.boomerang.workflowconnector.internal.actions.defination;

import com.boomerang.workflowconnector.internal.model.ProjectFlowNode;
import com.boomerang.workflowconnector.internal.repositories.IProjectFlowNodeRepository;
import com.boomerang.workflowconnector.requestresponse.ProjectFlowNodeRequest;
import com.boomerang.workflowconnector.util.MapToStringConvertor;
import com.google.inject.Inject;
import com.google.inject.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.Optional;

/**
 * Created by kanhaiya on 27/09/16.
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CreateProjectFLowNodesAction {

    private final IProjectFlowNodeRepository repository;
    private List<ProjectFlowNodeRequest> requestList;
    private final Provider<CreateEdgeMappingsAction> createEdgeMappingsActionProvider;
    private Long projectId;
    private final ModelMapper modelMapper;

    public CreateProjectFLowNodesAction withRequest(List<ProjectFlowNodeRequest> requestList, Long projectId) {
        this.requestList =requestList;
        this.projectId = projectId;
        return this;
    }

    public void invoke(){

        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        createIndividualNodes();
        createEdgeMapping();
    }

    private void createIndividualNodes() {
        for(ProjectFlowNodeRequest request: requestList){
            ProjectFlowNode projectFlowNode = modelMapper.map(request, ProjectFlowNode.class);
            if(request.getParentNodeNameList() ==null || request.getParentNodeNameList().isEmpty())
            {
                projectFlowNode.setIsRoot(Boolean.TRUE);
            }
            projectFlowNode.setProjectId(projectId);
            projectFlowNode.setAttributes(
                    MapToStringConvertor.convert(request.getAttributes()));
            repository.persist(projectFlowNode);
            log.info("The node got created with id " + projectFlowNode.getId());
        }
    }

    private void createEdgeMapping() {
        for(ProjectFlowNodeRequest request: requestList){
            if(!(request.getParentNodeNameList() ==null || request.getParentNodeNameList().isEmpty())){
                Optional<ProjectFlowNode> projectFlowNodeOptional = repository.findByNameAndProjectId(request.getName(), projectId);
                if(!projectFlowNodeOptional.isPresent()){
                    throw new BadRequestException("FlowNode not found with name" + request.getName());
                }

                Long childId = projectFlowNodeOptional.get().getId();

                List<Long> parentIdList = repository.getIdListByNameListAndProjectId(request.getParentNodeNameList(),projectId);

                //create edgemappingaction call from here
                createEdgeMappingsActionProvider.get().withParameters(projectId,childId,parentIdList).invoke();
            }
        }

    }
}
