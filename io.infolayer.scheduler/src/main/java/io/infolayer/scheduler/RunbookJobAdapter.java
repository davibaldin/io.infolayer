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
package io.infolayer.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunbookJobAdapter implements Job {
	
	private final Logger log = LoggerFactory.getLogger(RunbookJobAdapter.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		log.debug("RunbookJobAdapter execute()");
		
		String oid = context.getJobDetail().getJobDataMap().get("instance").toString();
		
		if (oid == null) {
			log.error("Runbook oid is null. Execution will return now.");
			return;
		}
		
		// try {
			
		// 	IPlaybookService service = OsgiUtils.getOSGIService(IPlaybookService.class, true);
		// 	service.runPlaybook(oid, false, null, null);
			
		// } catch (Exception e) {
		// 	log.error("PlaybookRunJobAdapter execute Exception: {}", e.getMessage());
		// }
		
	}

}