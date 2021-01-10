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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;

import io.infolayer.ControlChannel;
import io.infolayer.Heartbeat;
import io.infolayer.entity.SchedulerEntry;

@SpringBootTest
@DirtiesContext
//@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://kafka:9092", "port=9092" })
class KafkaIntegrationTest {

    // @Autowired
    // public KafkaTemplate<String, String> template;

    @Autowired
    private KafkaTemplate<String, Heartbeat> heartbeatKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, SchedulerEntry> schedulerKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, ControlChannel> controlKafkaTemplate;

    // @Autowired
    // private KafkaSchedulerListener listener;

    @Test
    public void testSendHeartbeat() throws Exception {
        heartbeatKafkaTemplate.send("Executors.Heartbeat",
            "control", new Heartbeat("Hello test"));
        assert(true);
    }

    @Test
    public void testControlReset() throws Exception {
        controlKafkaTemplate.send("Scheduler",
            "PUT", new ControlChannel(ControlChannel.CMD_RESET_CONFIG));
        assert(true);
    }

    @Test
    public void testControlShutdown() throws Exception {
        controlKafkaTemplate.send("Scheduler",
            "PUT", new ControlChannel(ControlChannel.CMD_SHUTDOWN));
        assert(true);
    }

    @Test
    public void testScheduleTask() throws Exception {

        SchedulerEntry entry = new SchedulerEntry();
        entry.setCronExpresssion("0 * 0 ? * * *");
        entry.setMethod("method");
        entry.setInstance("instance");
        entry.setOid("oid");
        entry.setType("type");

        schedulerKafkaTemplate.send("Scheduler", 
            "PUT", entry);
    }

    @Test
    public void testRemoveScheduledTask() throws Exception {
        SchedulerEntry entry = new SchedulerEntry();
        entry.setOid("oid");

        schedulerKafkaTemplate.send("Scheduler", 
            "DELETE", entry);
    }

}
