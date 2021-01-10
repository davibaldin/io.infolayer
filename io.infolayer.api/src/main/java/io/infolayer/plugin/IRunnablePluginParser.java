package io.infolayer.plugin;

import java.io.File;

import org.w3c.dom.Node;

import io.infolayer.exception.MalformedPluginException;

public interface IRunnablePluginParser {
	
	public Object parseConfiguration(Node xmlNode, File pluginXmlFile) throws MalformedPluginException;

}
