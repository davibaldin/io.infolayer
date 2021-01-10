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
package io.infolayer.executor;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.infolayer.executor.PoolExecutorService.PoolEntry;

/**
 * Watch for running plugins and cancel an instance due to timeout.
 * @author davi@infolayer.io
 *
 */
public class PluginExecutorTimeout implements Runnable {
	
	private static Logger log = LoggerFactory.getLogger(PluginExecutorTimeout.class);
	private final ConcurrentHashMap<String, PoolEntry> submittedTasks;
	
	protected PluginExecutorTimeout(ConcurrentHashMap<String, PoolEntry> submittedTasks) {
		this.submittedTasks = submittedTasks;
	}

	@Override
	public void run() {
		try {
			
			do {
				
				long now = System.currentTimeMillis();
				
				if (submittedTasks != null) {
					Iterator<Entry<String, PoolEntry>> iterator = 
							submittedTasks.entrySet().iterator();
					
					while (iterator.hasNext()) {
						PoolEntry entry = iterator.next().getValue();
						
						if (entry.getDue() > 0 && now > entry.getDue()) {
							if (entry.getPlugin() != null && entry.getFuture() != null) {
								log.info("IRunnablePlugin instance {} cancelled due timeout {}sec.", entry.getPlugin().getInstanceID(), entry.getPlugin().getTimeout());
								entry.getFuture().cancel(true);
							}
						}
						
						if (entry.getFuture().isCancelled() || entry.getFuture().isDone()) {
							try {
								log.debug("IRunnablePlugin instance {} cleanup.", entry.getPlugin().getInstanceID());
								entry.setPlugin(null);
								entry.setFuture(null);
								iterator.remove();
							}catch (Exception e) {
								if (log.isDebugEnabled()) {
									e.printStackTrace();
								}
								log.warn("Unexpected exception while cleaning up: {}", e.getMessage());
							}
							

						}
						
					}
					
				}
				
				//FIXME Hardcode
				Thread.sleep(1000);
				
			}while (true);
			
		} catch (InterruptedException e) {
			log.info("PluginExecutorTimeout interrupted."); 
		}
	}

}