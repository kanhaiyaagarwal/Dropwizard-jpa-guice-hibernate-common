package com.boomerang.workflowconnector.internal.actions.defination;

import com.boomerang.workflowconnector.internal.actions.execution.CheckParentNodeDependenciesAction;
import com.boomerang.workflowconnector.internal.repositories.IEdgeMappingInstanceRepository;
import com.boomerang.workflowconnector.internal.repositories.INodeExecutionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import lombok.SneakyThrows;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by kanhaiya on 19/10/16.
 */
@RunWith(JukitoRunner.class)
public class CheckParentNodeDependenciesActionTest {

    @Mock
    private IEdgeMappingInstanceRepository edgeMappingInstanceRepository;
    @Mock
    private INodeExecutionRepository nodeExecutionRepository;
    @Inject
    ObjectMapper objectMapper;

    private String FIXTURE = "fixtures/project/";
    private CheckParentNodeDependenciesAction checkParentNodeDependenciesAction;
    private Long childId = 1l;
    private Long execId = 1l;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        checkParentNodeDependenciesAction =
                new CheckParentNodeDependenciesAction(edgeMappingInstanceRepository, nodeExecutionRepository);
    }

    @Test
    public void shouldReturnTrueIfRootNode(){

        when(edgeMappingInstanceRepository
                .getParentIdListByChildIdandExecId(childId,execId))
                .thenReturn(new ArrayList<Long>());
        when(nodeExecutionRepository
                .findCountOfNotCompletedNodeFromList(new ArrayList<>(),execId))
                .thenReturn(0);
        assertTrue(checkParentNodeDependenciesAction.withParameters(execId,childId).invoke());
    }

    @Test
    public void shouldReturnTrueIfDependentNodeExecuted(){

        when(edgeMappingInstanceRepository
                .getParentIdListByChildIdandExecId(childId,execId))
                .thenReturn(getparentNodeList());
        when(nodeExecutionRepository
                .findCountOfNotCompletedNodeFromList(getparentNodeList(),execId))
                .thenReturn(0);
        assertTrue(checkParentNodeDependenciesAction.withParameters(execId,childId).invoke());
    }

    @Test
    public void shouldReturnFalseIfDependentNodeNotExecuted(){

        when(edgeMappingInstanceRepository
                .getParentIdListByChildIdandExecId(childId,execId))
                .thenReturn(getparentNodeList());
        when(nodeExecutionRepository
                .findCountOfNotCompletedNodeFromList(getparentNodeList(),execId))
                .thenReturn(1);
        assertFalse(checkParentNodeDependenciesAction.withParameters(execId,childId).invoke());
    }

    @SneakyThrows
    private List<Long> getparentNodeList() {
        return Arrays.asList(1l,2l,3l);
    }
}
