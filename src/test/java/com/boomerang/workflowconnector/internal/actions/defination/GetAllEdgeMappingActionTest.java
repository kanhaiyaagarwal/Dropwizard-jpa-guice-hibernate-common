package com.boomerang.workflowconnector.internal.actions.defination;

import com.boomerang.workflowconnector.internal.model.EdgeMapping;
import com.boomerang.workflowconnector.internal.model.Project;
import com.boomerang.workflowconnector.internal.repositories.IEdgeMappingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import io.dropwizard.jackson.Jackson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by kanhaiya on 19/10/16.
 */

@Slf4j
@RunWith(JukitoRunner.class)
public class GetAllEdgeMappingActionTest {

    @Mock
    private IEdgeMappingRepository repository;
    @Inject
    ObjectMapper objectMapper ;

    private GetAllEdgeMappingAction getAllEdgeMappingAction;
    private String FIXTURE = "fixtures/project/";
    private Long projectId = 1l;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        getAllEdgeMappingAction = new GetAllEdgeMappingAction(repository);
    }

    @Test
    public void shouldGetProjectFromId() throws IOException {
        when(repository.findAllByProjectId(projectId)).thenReturn(getEdgeMappingList());
        List<EdgeMapping> edgeMappingList = getAllEdgeMappingAction.withprojectId(projectId).invoke();
        assertEquals(1, edgeMappingList.size()) ;
        assertEquals(projectId, edgeMappingList.get(0).getId()) ;
        assertEquals(Long.valueOf(2), edgeMappingList.get(0).getChildId()) ;
        assertEquals(Long.valueOf(3), edgeMappingList.get(0).getParentId()) ;
        assertEquals(Long.valueOf(4), edgeMappingList.get(0).getProjectId()) ;

    }

    @Test
    public void shouldReturnEmptyList() throws IOException {
        when(repository.findAllByProjectId(projectId)).thenReturn(new ArrayList<EdgeMapping>());
        List<EdgeMapping> edgeMappingList = getAllEdgeMappingAction.withprojectId(projectId).invoke();
        assertEquals(0, edgeMappingList.size()) ;
    }

    @SneakyThrows
    private List<EdgeMapping> getEdgeMappingList() {
        return Arrays.asList(objectMapper.readValue(fixture(FIXTURE + "edge_mapping_list.json"), EdgeMapping.class));
    }

}
