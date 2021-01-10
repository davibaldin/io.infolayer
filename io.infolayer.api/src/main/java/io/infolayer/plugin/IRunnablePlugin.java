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

import io.infolayer.exception.PluginException;

/**
 * IRunnablePlugin is the commom interface for each Plugin runnable code.
 * 
 * @author davi@infolayer.io
 *
 */
public interface IRunnablePlugin extends Runnable, IPluginSubmission {
	
	public static final int STATUS_NEW 			= 0;
	public static final int STATUS_RUNNING 		= 3;
	public static final int STATUS_SUCCESS 		= 4;
	public static final int STATUS_EXCEPTION    = 5;
	public static final int STATUS_INTERRUPTED 	= 6; //Either timeout or cancel
	public static final String ENV_DRYRUN 	= "dryRun";
	
	/**
	 * Return Plugin instance.
	 * @return
	 */
	public PluginMetadata getPlugin();
	
	/**
	 * Get execution ID.
	 * @return
	 */
	public String getInstanceID();
	
	/**
	 * Return execution instance status.
	 * @return
	 */
	public int getStatus();
	
	/**
	 * Execute the Plugin and return an arbitrary object if desired.
	 * @throws PluginException
	 */
	public Object execute() throws Exception;
	
	/**
	 * Set timeout in seconds.
	 * @param seconds
	 */
	public void setTimeout(int seconds);
	
	/**
	 * Get timeout in seconds.
	 * @return
	 */
	public int getTimeout();
	
	/**
	 * Configure IRunnablePlugin instance. 
	 * @param runanbleMetada
	 * @throws PluginException 
	 */
	public void configure(PluginMetadata plugin, int timeoutSeconds, IPluginInputHandler[] input, IPluginOutputHandler[] output) throws PluginException;
	
	/**
	 * Configure IRunnablePlugin instance. 
	 * @param runanbleMetada
	 * @throws PluginException 
	 */
	public void configure(int timeoutSeconds, IPluginInputHandler[] input, IPluginOutputHandler[] output) throws PluginException;
	
	
	// /**
	//  * Configure IRunnablePlugin instance. 
	//  * @param runanbleMetada
	//  * @throws PluginException 
	//  */
	// public void configure(Object runConfigurationMetada) throws PluginException;
	
	

}
