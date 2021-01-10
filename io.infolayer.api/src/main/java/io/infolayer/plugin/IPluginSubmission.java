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

import java.util.Map;
import java.util.Set;

import io.infolayer.entity.PluginCall;
import io.infolayer.exception.PluginException;

/**
 * Commom interface for plugin submission. This interface is implemented by: 
 * 		{@link IPluginService},
 * 		{@link IPluginServiceProvider} and 
 * 		{@link IRunnablePlugin} instances.
 * @author davi@infolayer.io
 *
 */
public interface IPluginSubmission {
	
	/**
	 * Submit for execution
	 * @param call Chained plugin call for execution.
	 * @param environment Environment variables required for this submission.
	 * @param statusListeners Listeners to be called.
	 * @param outputFlow OutputFlow for output processing.
	 * @throws PluginException
	 */
	public PluginSubmissionResponse submit(PluginCall call,  Map<String, String> environment,  Set<IRunnableListener> listeners,  OutputFlow outputFlow) throws PluginException;

}
