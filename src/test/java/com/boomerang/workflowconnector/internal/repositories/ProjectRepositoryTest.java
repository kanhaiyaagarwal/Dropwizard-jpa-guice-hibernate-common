package com.boomerang.workflowconnector.internal.repositories;

import com.boomerang.workflowconnector.config.TestModule;
import com.boomerang.workflowconnector.internal.model.Project;
import com.boomerang.workflowconnector.util.TransactionalJpaRepositoryTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Optional;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertTrue;

/**
 * Created by kanhaiya on 19/10/16.
 */
@Slf4j
@UseModules({TestModule.class})
@RunWith(JukitoRunner.class)
public class ProjectRepositoryTest  extends TransactionalJpaRepositoryTest {

    @Inject
    IProjectRepository projectRepository;

    @Inject
    ObjectMapper objectMapper;

    private String FIXTURE = "fixtures/project/";

    @Before
    public void setUp() throws Exception {
        createSampleData();
    }

    @Test
    public void shouldGetByBrandName() {
        Optional<Project> projectOptional = projectRepository.findByName("sampleProject");
        assertTrue(projectOptional.isPresent());
    }


    private void createSampleData() throws IOException {
        persist(getProjectRow());
    }

    private Project getProjectRow() throws IOException {
        return objectMapper.readValue(fixture(FIXTURE + "sample_project_model_data.json"), Project.class);
    }

}
