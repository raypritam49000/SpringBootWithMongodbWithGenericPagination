package com.mongo.modal;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("address")
public class Address {


    @Transient
    public static final String SEQUENCE_NAME = "address_sequence";

    @Id
    private String id= UUID.randomUUID().toString();;

    private String house;

    public Address() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }


    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", house='" + house + '\'' +
                '}';
    }
}
