package io.infolayer.executor;

import org.junit.jupiter.api.Test;

import io.infolayer.entity.PluginCall;
import io.infolayer.plugin.OutputFlow;

public class TestSimplePlugin {

    @Test
    public void testCreateAndRun() throws Exception {

        /**
         * The simpliest plugin execution requires:
         * 1. OutputFlow.
         * 2. Instantiate and configure with timeout and null handlers.
         * 3. Create a PluginCall with plugin's name.
         * 4. Submit itself.
         * 5. Execute run.
         */
        OutputFlow flow = OutputFlow.newInstance("temp");

        SimplePluginA plugin = new SimplePluginA();
        plugin.configure(10, null, null);

        PluginCall call = new PluginCall(plugin.getPlugin().getName());
        call.addPluginParam("param1", "value from call");

        plugin.submit(call, null, null, flow);
        plugin.run();

    }
    
}