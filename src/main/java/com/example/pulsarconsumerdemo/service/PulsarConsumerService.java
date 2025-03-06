package com.example.pulsarconsumerdemo.service;

import com.example.pulsarconsumerdemo.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Slf4j
@Service
public class PulsarConsumerService {

    private final PulsarClient pulsarClient;
    private final ObjectMapper objectMapper;
    private Consumer<byte[]> consumer;

    @Value("${pulsar.topic}")
    private String topic;

    @Value("${pulsar.subscription}")
    private String subscription;

    public PulsarConsumerService(PulsarClient pulsarClient) {
        this.pulsarClient = pulsarClient;
        this.objectMapper = new ObjectMapper();
    }

    @PostConstruct
    public void init() throws PulsarClientException {
        consumer = pulsarClient.newConsumer()
                .topic(topic)
                .subscriptionName(subscription)
                .subscriptionType(SubscriptionType.Exclusive)
                .messageListener((consumer, msg) -> {
                    try {
                        String messageContent = new String(msg.getData());
                        Message message = objectMapper.readValue(messageContent, Message.class);
                        log.info("Received message: {}", message);
                        consumer.acknowledge(msg);
                    } catch (Exception e) {
                        log.error("Error processing message: ", e);
                        consumer.negativeAcknowledge(msg);
                    }
                })
                .subscribe();
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (consumer != null) {
                consumer.close();
            }
        } catch (PulsarClientException e) {
            log.error("Error closing consumer: ", e);
        }
    }
} 