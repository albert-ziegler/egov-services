package org.egov.pg.models.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CollectionType {
    COUNTER("COUNTER"),
    FIELD("FIELD"),
    ONLINE("ONLINE");


    private String value;

    CollectionType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static CollectionType fromValue(String text) {
        for (CollectionType b : CollectionType.values()) {
            if (0 == b.value.compareTo(text)) {
                return b;
            }
            System.out.println("Mismatch: " + String.valueOf(b.value).compareTo(text.trim()));
        }
        System.out.println(CollectionType.FIELD.toString().equals("FIELD"));
        System.out.println("textLength:" + text.length());
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    public String getValue() {
        return value;
    }
}
