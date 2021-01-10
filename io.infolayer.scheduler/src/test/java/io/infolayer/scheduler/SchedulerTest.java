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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;

import io.infolayer.entity.SchedulerEntry;
import io.infolayer.utils.PlatformUtils;

//@TestInstance(Lifecycle.PER_CLASS)
public class SchedulerTest {

    private SchedulerService service = null;

    public SchedulerTest() throws SchedulerException {
        service = new SchedulerService();
        service.start();
    }

    // @BeforeAll
    // public void testStart() {
    //     assertDoesNotThrow(() -> service.start());
    // }

    // @AfterAll
    // public void testStop() {
    //     assertDoesNotThrow(() -> service.stop());
    // }

    @Test
    public void testDump() {
        assertDoesNotThrow(() -> service.dump());
    }

    @Test
    public void testRemoveAll() {
        assertDoesNotThrow(() -> service.removeAllJobs());
    }

    @Test
    public void testPauseResume() {
        assertDoesNotThrow(() -> service.pause());
        assertDoesNotThrow(() -> service.resume());
    }

    @Test
    public void testAddJob() {
        SchedulerEntry entry = new SchedulerEntry();
        entry.setCronExpresssion("* 0 0 ? * * *");
        entry.setOid(PlatformUtils.getAlphaNumericString(5));
        entry.setMethod("method");
        entry.setType(SchedulerEntry.TYPE_PLAYBOOK_RUN);
        entry.setInstance("instance");

        assertDoesNotThrow(() -> service.addJob(entry));
    }

    @Test
    public void testRemoveJob() {

        String randonJobId = PlatformUtils.getAlphaNumericString(5);

        SchedulerEntry entry = new SchedulerEntry();
        entry.setCronExpresssion("0 0 * ? * * *");
        entry.setOid(randonJobId);
        entry.setMethod("method");
        entry.setType(SchedulerEntry.TYPE_PLAYBOOK_RUN);
        entry.setInstance("instance");

        assertDoesNotThrow(() -> service.addJob(entry));
        assertDoesNotThrow(() -> service.removeJob(randonJobId));
    }

}
