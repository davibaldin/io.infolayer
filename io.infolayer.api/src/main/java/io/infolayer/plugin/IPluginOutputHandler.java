package io.infolayer.plugin;

import io.infolayer.exception.PluginException;

/**
 * Interface for generic Plugin's output handling, if required.
 * 
 * @author davi@infolayer.io
 *
 */
public interface IPluginOutputHandler {

	public void proccess(Object result, OutputFlow flow) throws PluginException;
	
}
