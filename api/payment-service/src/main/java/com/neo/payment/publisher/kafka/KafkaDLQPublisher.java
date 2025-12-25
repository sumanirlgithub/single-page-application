package com.neo.payment.publisher.kafka;

import com.neo.api.common.event.dlq.DlqEvent;
import com.neo.api.common.order.event.OrderEvent;
import com.neo.payment.config.KafkaDlqTopicConfig;
import com.neo.payment.service.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaDLQPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaDlqTopicConfig kafkaDlqTopicConfig;
    private final JsonUtil jsonUtil;

    public void sendToDLQ(OrderEvent event, Exception ex) {
        DlqEvent dlqEvent = new DlqEvent(event, ex.getMessage(), LocalDateTime.now());

        kafkaTemplate.send(kafkaDlqTopicConfig.dLqTopic().name(), String.valueOf(event.eventId()), jsonUtil.toJson(dlqEvent));
    }
}
