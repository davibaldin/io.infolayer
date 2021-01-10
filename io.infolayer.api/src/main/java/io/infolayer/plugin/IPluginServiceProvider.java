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

import java.util.List;
import java.util.Set;

/**
 * Interface utilizada para executar plugins do SiteView implementaados no formato OSGI.
 * Cada OSGI Bundle que implementar e registrar essa interface como um serviços disponibiliza plugins para o framework.
 * 
 * @author davi
 *
 */
public interface IPluginServiceProvider extends IPluginSubmission {
	
	/**
	 * Default execution timeout for plugins. Unit is seconds.
	 */
	public static final int DEFAULT_EXECUTION_TIMEOUT = 60;
	
	/**
	 * Return known plugins by this service provider.
	 * @return
	 */
	public List<PluginMetadata> listKnownPlugins();
	
	/**
	 * Attempt to abort an execution event ID.
	 * @param executionEventID
	 * @return
	 */
	public boolean cancel(String executionEventID);
	
	/**
	 * Shutdown Service provider and do not accept new plugin submissions. 
	 */
	public void shutdown();
	
	/**
	 * Return current execution queue.
	 * @return
	 */
	public int getQueueCount();
	
	/**
	 * Return current execution queue.
	 * @return
	 */
	public Set<String> getActiveQueue();
	
}
