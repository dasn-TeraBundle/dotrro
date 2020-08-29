package com.innova.doctrro.usrs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.innova.doctrro.common.dto.KafkaMessage;

public interface KafkaService {

    void send(String topic, KafkaMessage message) throws JsonProcessingException;
    void listenPatients(String message);
}
