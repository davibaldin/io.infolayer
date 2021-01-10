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
package io.infolayer;

import java.util.List;
import java.util.Map;

import io.infolayer.exception.PluginException;
import io.infolayer.exception.PluginNotFoundException;
import io.infolayer.exception.PluginServiceProviderException;
import io.infolayer.plugin.IPluginServiceProvider;
import io.infolayer.plugin.IPluginSubmission;
import io.infolayer.plugin.IRunnableListener;
import io.infolayer.plugin.OutputFlow;
import io.infolayer.plugin.PluginMetadata;
import io.infolayer.entity.PluginCall;
import io.infolayer.plugin.PluginSubmissionResponse;

public interface IPluginService extends IPluginSubmission {
	
	/**
	 * Return a reference to the IPluginServiceProvider witch provides the plugin FQDN (context + name) name.
	 * @param pluginFqdnName
	 * @return
	 * @throws PluginNotFoundException
	 */
	public IPluginServiceProvider getServiceProviderByPluginName(String pluginFqdnName) throws PluginNotFoundException;
	
	/**
	 * Return a reference to the IPluginServiceProvider witch provides the plugin name.
	 * @param pluginName
	 * @return
	 * @throws PluginNotFoundException
	 */
	public IPluginServiceProvider getServiceProviderByName(String serviceProviderName) throws PluginNotFoundException;
	
	/**
	 * Return the list of IPluginServiceProvider registered.
	 * @return
	 */
	public List<String> getIPluginServiceProviderNames();

	/**
	 * Convenient proxy to {@link IPluginSubmission} with a single listener instance.
	 */
	public PluginSubmissionResponse submitSingleListener(PluginCall call,  Map<String, String> environment,  IRunnableListener statusListener,  OutputFlow outputFlow) throws PluginException;
	
	/**
	 * Convenient proxy to {@link IPluginServiceProvider#cancel(String)}.
	 * @param executionEventID
	 * @return
	 */
	public boolean cancel(String pluginName, String executionEventID);
	
	/**
	 * Return Plugin from service.
	 * @param pluginFqdnName
	 * @return
	 * @throws PluginNotFoundException 
	 */
	public PluginMetadata getPlugin(String pluginFqdnName) throws PluginNotFoundException;
	
	/**
	 * Return list of on-line plugins name.
	 * @return
	 */
	public List<String> getPluginNames();
	
	/**
	 * Return list of on-line plugins.
	 * @return
	 */
	public int getPluginsCount();

}
