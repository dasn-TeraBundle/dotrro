package com.innova.doctrro.usrs.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innova.doctrro.common.dto.KafkaMessage;
import com.innova.doctrro.common.dto.UserDto;
import com.innova.doctrro.usrs.service.KafkaService;
import com.innova.doctrro.usrs.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class KafkaServiceImpl implements KafkaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaServiceImpl.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserService userService;

    @Autowired
    public KafkaServiceImpl(KafkaTemplate<String, String> kafkaTemplate, UserService userService) {
        this.kafkaTemplate = kafkaTemplate;
        this.userService = userService;
    }

    @Override
    public void send(String topic, KafkaMessage message) throws JsonProcessingException {
        kafkaTemplate.send(topic, MAPPER.writeValueAsString(message));
    }

    @Override
    @KafkaListener(topics = "users_create", groupId = "us_usc_grp")
    public void listenPatients(String message) {
        LOGGER.info("Message : {}", message);
        try {
            KafkaMessage kafkaMessage = MAPPER.readValue(message, KafkaMessage.class);
            KafkaMessage.Operation operation = kafkaMessage.getOperation();
            String collectionName = kafkaMessage.getCollectionName();

            switch (collectionName) {
                case "patients":
                    if (operation == KafkaMessage.Operation.C) {
                        userService.addRole(kafkaMessage.getId(), "ROLE_PATIENT");
                    } else {
                        LOGGER.warn("Operation {} not supported for collection {}", operation, collectionName);
                    }
                    break;
                case "doctors":
                    switch (operation) {
                        case C:
                            userService.addRole(kafkaMessage.getId(), "ROLE_DOCTOR");
                            break;
                        case U:
                            var updates = kafkaMessage.getUpdates();
                            boolean isNewEmail = updates.stream()
                                .filter(upd -> upd.getField().equals("email") && upd.getOldValue() == null)
                                .findFirst()
                                .isPresent();
                            if (isNewEmail) {
                                var item = new UserDto.UserDtoRequest();
                                updates.forEach(upd -> {
                                    switch (upd.getField()) {
                                        case "email":
                                            item.setEmail(upd.getNewValue().toString());
                                            break;
                                        case "name":
                                            item.setName(upd.getNewValue().toString());
                                            break;
                                        default:
                                            break;
                                    }
                                });
                                var roles = new HashSet<>(Arrays.asList("ROLE_USER", "ROLE_DOCTOR"));
                                item.setRoles(roles);
                                item.setActive(true);
                                item.setEnabled(true);

                                userService.create(item);
                            }
                            break;
                        default:
                            LOGGER.warn("Operation {} not supported for collection {}", operation, collectionName);
                            break;
                    }
                    break;
                default:
                    LOGGER.warn("Collection {} not supported", collectionName);
                    break;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
