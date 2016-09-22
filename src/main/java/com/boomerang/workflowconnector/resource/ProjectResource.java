package com.boomerang.workflowconnector.resource;

import com.boomerang.workflowconnector.dao.ProjectDao;
import com.boomerang.workflowconnector.model.Project;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by kanhaiya on 21/09/16.
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectResource {
    private final ProjectDao projectDao;

    @Inject
    public ProjectResource(final ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @GET
    @Path("/first")
    @Produces(MediaType.APPLICATION_JSON)
    public Project createUserResource(@PathParam("name") String name,
                                                @PathParam("password") String password){

        return projectDao.findById((long) 1).get() ;

    }

}
