package com.boomerang.workflowconnector.internal.actions.defination;

import com.boomerang.workflowconnector.internal.model.Project;
import com.boomerang.workflowconnector.internal.repositories.IProjectRepository;
import com.boomerang.workflowconnector.requestresponse.ProjectFlowNodeRequest;
import com.boomerang.workflowconnector.requestresponse.ProjectRequest;
import com.google.inject.Inject;
import com.google.inject.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.Optional;

/**
 * Created by kanhaiya on 26/09/16.
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CreateProjectDagAction {

    private final IProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private ProjectRequest request;
    private final Provider<CreateProjectFLowNodesAction> createProjectFLowNodeActionProvider;

    public CreateProjectDagAction withRequest(ProjectRequest request){
        this.request = request;
        return this;
    }

    public void invoke(){
        Optional<Project> projectOptional = projectRepository.findByName(request.getName());

        if (projectOptional.isPresent()){
            throw new BadRequestException("Project Name Already Exists");
        }
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Project project = modelMapper.map(this.request, Project.class);
        projectRepository.persist(project);

        log.info("the project got created with " + project.getId());

        createProjectFlowNodes(project.getId());
    }

    private void createProjectFlowNodes(Long projectId) {
        List<ProjectFlowNodeRequest> requestList = request.getProjectFlowNodeRequestList();
        if(requestList!=null)
            createProjectFLowNodeActionProvider.get().withRequest(requestList, projectId).invoke();

    }

}
