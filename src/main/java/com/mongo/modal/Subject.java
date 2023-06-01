package com.mongo.modal;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "subjects")
public class Subject {



    @Transient
    public static final String SEQUENCE_NAME = "subject_sequence";

    @Id
    private String id= UUID.randomUUID().toString();;

    private String subName;

    public Subject() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubName() { return subName; }

    public void setSubName(String subName) { this.subName = subName; }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", subName='" + subName + '\'' +
                '}';
    }
}
