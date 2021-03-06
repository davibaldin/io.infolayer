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

import org.slf4j.Logger;
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
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import io.infolayer.Heartbeat;

@Component
public class HeartbeatMessageProducer {

    private static final Logger log = LoggerFactory.getLogger(HeartbeatMessageProducer.class);

    @Autowired
    private KafkaTemplate<String, Heartbeat> heartbeatKafkaTemplate;

    @Value(value = "Executors.Heartbeat")
    private String heartbeatTopicName;

    public void sendHeartbeatMessage(Heartbeat heartbeat) {

        //ControlChannel control = new ControlChannel();

        ListenableFuture<SendResult<String, Heartbeat>> future = heartbeatKafkaTemplate.send(heartbeatTopicName, "control", heartbeat);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Heartbeat>>() {

            @Override
            public void onSuccess(SendResult<String, Heartbeat> result) {
                log.info("Sent message=[" + heartbeat.getMessage() + "] with offset=[" + result.getRecordMetadata()
                    .offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[" + heartbeat.getMessage() + "] due to : " + ex.getMessage());
            }
        });
    }

}