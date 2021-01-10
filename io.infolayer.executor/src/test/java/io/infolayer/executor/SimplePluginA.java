package io.infolayer.executor;

import io.infolayer.annotation.Plugin;
import io.infolayer.annotation.PluginParameter;
import io.infolayer.executor.runnable.AbstractRunnablePlugin;

@Plugin(name = "simple-a", description = "Sample plugin for tests")
public class SimplePluginA extends AbstractRunnablePlugin {

    @PluginParameter
    private String param1;

    @PluginParameter(defaultValue = "param2 with default value")
    private String param2;

    @Override
    public Object execute() throws Exception {
        logInfo("SimplePluginA execute >>>>>>>>>>>>>>>>>>>>>");
        logInfo("I have two params {} {}", param1, param2);
        logInfo("SimplePluginA execute <<<<<<<<<<<<<<<<<<<<<");
        return null;
    }
    
}
