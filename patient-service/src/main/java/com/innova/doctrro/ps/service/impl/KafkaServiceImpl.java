package com.innova.doctrro.ps.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innova.doctrro.common.dto.KafkaMessage;
import com.innova.doctrro.common.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    public KafkaServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topic, KafkaMessage message) throws JsonProcessingException {
        kafkaTemplate.send(topic, MAPPER.writeValueAsString(message));
    }
}
