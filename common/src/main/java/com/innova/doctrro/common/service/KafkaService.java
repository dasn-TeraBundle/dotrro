package com.innova.doctrro.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.innova.doctrro.common.dto.Event;
import com.innova.doctrro.common.dto.KafkaMessage;

import java.util.List;

public interface KafkaService {

    void send(String topic, KafkaMessage message) throws JsonProcessingException;
    default void send(String topic, List<? extends Event> event) throws JsonProcessingException {}

}
