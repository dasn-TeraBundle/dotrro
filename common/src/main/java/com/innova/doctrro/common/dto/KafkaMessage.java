package com.innova.doctrro.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class KafkaMessage implements Serializable {

    private Operation operation;
    private String collectionName;
    private String id;
    private List<Update<?>> updates;

    public KafkaMessage(String id, Operation operation, String collectionName) {
        this.id = id;
        this.operation = operation;
        this.collectionName = collectionName;
        this.updates = new ArrayList<>();
    }

    public KafkaMessage(String id, String operation, String collectionName) {
        this(id, Operation.valueOf(operation), collectionName);
    }

    public <T extends Serializable> void addUpdate(Update<T> update) {
        if (updates == null)
            updates = new ArrayList<>();
        updates.add(update);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KafkaMessage that = (KafkaMessage) o;

        if (operation != that.operation) return false;
        if (!collectionName.equals(that.collectionName)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = operation.hashCode();
        result = 31 * result + collectionName.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    public enum Operation {
        C, R, U, D
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update<T extends Serializable> implements Serializable {
        private String field;
        private T oldValue;
        private T newValue;
    }
}
