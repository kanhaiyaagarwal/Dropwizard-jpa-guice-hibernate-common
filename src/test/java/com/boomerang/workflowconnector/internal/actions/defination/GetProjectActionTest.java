package com.boomerang.workflowconnector.internal.actions.defination;

import com.boomerang.workflowconnector.internal.model.Project;
import com.boomerang.workflowconnector.internal.repositories.IProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Provides;
import io.dropwizard.jackson.Jackson;
import lombok.SneakyThrows;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Optional;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by kanhaiya on 19/10/16.
 */
@RunWith(JukitoRunner.class)
public class GetProjectActionTest {

    private String FIXTURE = "fixtures/project/";
    private GetProjectAction getProjectAction;
    @Mock
    private IProjectRepository repository;

    @Inject
    ObjectMapper objectMapper;
    private Long projectId = 1L;
    private String name = "sampleProject";


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        getProjectAction = new GetProjectAction(repository);
    }

    @Test
    public void shouldGetProjectFromId() throws IOException {
        when(repository.findOne(projectId)).thenReturn(Optional.of(getProject()));
        Project project = getProjectAction.withId(projectId).invoke();
        assertEquals("sampleProject", project.getName());
    }

    @Test
    public void shouldGetProjectFromName() throws IOException {
        when(repository.findByName(name)).thenReturn(Optional.of(getProject()));
        Project project = getProjectAction.withName(name).invoke();
        assertEquals("sampleProject", project.getName());
        assertEquals("anything", project.getDescription());
    }
    @SneakyThrows
    private Project getProject() {
        return objectMapper.readValue(fixture(FIXTURE + "sample_project_model_data.json"), Project.class);
    }

}
