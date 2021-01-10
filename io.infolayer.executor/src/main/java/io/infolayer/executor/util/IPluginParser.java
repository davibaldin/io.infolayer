package io.infolayer.executor.util;

import io.infolayer.exception.MalformedPluginException;
import io.infolayer.plugin.PluginMetadata;
//import io.infolayer.siteview.plugin.PluginPrototype;
import io.infolayer.plugin.PluginPrototype;

public interface IPluginParser {
	
	public PluginPrototype getPluginPrototype() throws Exception;

	public PluginMetadata getPlugin() throws MalformedPluginException;

}
