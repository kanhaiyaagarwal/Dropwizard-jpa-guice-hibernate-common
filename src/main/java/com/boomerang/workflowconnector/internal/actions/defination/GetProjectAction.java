package com.boomerang.workflowconnector.internal.actions.defination;

import com.boomerang.workflowconnector.internal.model.Project;
import com.boomerang.workflowconnector.internal.repositories.impl.ProjectRepository;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.BadRequestException;
import java.util.Optional;

/**
 * Created by kanhaiya on 28/09/16.
 */
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GetProjectAction {

    private final ProjectRepository repository;
    private Optional<Project> projectOptional;

    public GetProjectAction withName(String name){
        this.projectOptional = repository.findByName(name);
        return this;
    }

    public GetProjectAction withId(Long id){
        this.projectOptional = repository.findOne(id);
        return this;
    }

    public Project invoke(){
        if(!projectOptional.isPresent()){
            throw new BadRequestException("Not Found : Entity");
        }
        return projectOptional.get();
    }
}
