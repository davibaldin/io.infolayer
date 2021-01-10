/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.infolayer.plugin;

import java.util.HashMap;
import java.util.Map;

public class PluginPrototype {
	
	private PluginMetadata plugin;
	private Map<String, String> environment;
	private String[] inputHandlerClasses;
	private String[] outputHandlerClasses;
	private int timeout = 0;
	private String pluginClassName = null;
	
	public PluginPrototype(PluginMetadata plugin, Map<String, String> environment, String[] inputHandlerClasses, String[] outputHandlerClasses, int timeout,
			String pluginClassName) {
		this.plugin = plugin;
		this.environment = environment;
		this.inputHandlerClasses = inputHandlerClasses;
		this.outputHandlerClasses = outputHandlerClasses;
		this.timeout = timeout;
		this.pluginClassName = pluginClassName;
	}

	public Map<String, String> cloneEnvironment() {
		return new HashMap<String, String>(environment);
	}
	
	public PluginMetadata clonePlugin() {
		PluginMetadata p = null;
		try {
			p = plugin.clone();
		} catch (CloneNotSupportedException e) { }
		return p;
	}
	
	public int getTimeout() {
		return timeout;
	}
	
	public String getPluginClassName() {
		return pluginClassName;
	}

	
	
}
