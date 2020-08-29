package com.innova.doctrro.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class KafkaMessage implements Serializable {

    private Operation operation;
    private String collectionName;
    private String id;
    private List<Update> updates;
//    private LocalDateTime operationTime;

    public KafkaMessage(String id, Operation operation, String collectionName) {
        this.id = id;
        this.operation = operation;
        this.collectionName = collectionName;
        this.updates = new ArrayList<>();
//        this.operationTime = LocalDateTime.now();
    }

    public KafkaMessage(String id, String operation, String collectionName) {
        this(id, Operation.valueOf(operation), collectionName);
    }


    @Getter
    @Setter
    @NoArgsConstructor
    private static class Update implements Serializable {
        private String field;
        private Object oldValue;
        private Object newValue;
    }

    public enum Operation {
        C, R, U, D
    }
}
