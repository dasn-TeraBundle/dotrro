package com.innova.doctrro.usrs.service.impl;//package com.innova.doctrro.usrs.service.impl;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.innova.doctrro.common.dto.KafkaMessage;
//import com.innova.doctrro.usrs.service.KafkaService;
//import com.innova.doctrro.usrs.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaServiceImpl implements KafkaService {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//    private  final UserService userService;
//    private final static ObjectMapper MAPPER = new ObjectMapper();
//
//    @Autowired
//    public KafkaServiceImpl(KafkaTemplate<String, String> kafkaTemplate, UserService userService) {
//        this.kafkaTemplate = kafkaTemplate;
//        this.userService = userService;
//    }
//
//    @Override
//    public void send(String topic, KafkaMessage message) throws JsonProcessingException {
//        kafkaTemplate.send(topic, MAPPER.writeValueAsString(message));
//    }
//
//    @Override
//    @KafkaListener(topics = "patients", groupId = "pt_lsnr_grp")
//    public void listenPatients(String message) {
//        System.out.println("Message : " + message);
//        try {
//            KafkaMessage kafkaMessage = MAPPER.readValue(message, KafkaMessage.class);
//            switch (kafkaMessage.getCollectionName()) {
//                case "patients":
//                    switch (kafkaMessage.getOperation()) {
//                        case C :
//                            userService.addRole(kafkaMessage.getId(), "ROLE_PATIENT");
//                            break;
//                        default:
//                            System.out.println(kafkaMessage.getOperation() + " not supported");
//                    }
//                    break;
//                default:
//                    System.out.println(kafkaMessage.getCollectionName() + " collection not supported");
//            }
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
//}
