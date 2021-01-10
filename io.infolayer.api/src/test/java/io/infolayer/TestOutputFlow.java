package io.infolayer;

import org.junit.jupiter.api.Test;

import io.infolayer.exception.OutputFlowException;
import io.infolayer.plugin.OutputFlow;

public class TestOutputFlow {

    @Test
    public void testOutputFlow() throws OutputFlowException {
        OutputFlow flow = OutputFlow.newInstance("target/test");
    }
    
}
