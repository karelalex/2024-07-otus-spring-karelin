package ru.otus.hw.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestRunnerServiceImplTest {
    private TestRunnerServiceImpl subj;
    private TestService testService;

    @BeforeEach
    void initialize() {
        testService = mock(TestService.class);
        subj = new TestRunnerServiceImpl(testService);
    }
    @Test
    public void RunnerCallsTestService() {
        subj.run();
        verify(testService, times(1)).executeTest();
    }
}
