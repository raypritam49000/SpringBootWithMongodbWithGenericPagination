package com.mongo.modal;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Document(collection = "students")
public class Student {


    @Transient
    public static final String SEQUENCE_NAME = "student_sequence";


    @Id
    private String id= UUID.randomUUID().toString();

    private String name;

    private String city;

    private String collage;

    private Address address;

    private List<Subject> subjects;


    public Student() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public Address getAddress() { return address; }

    public void setAddress(Address address) { this.address = address; }

    public List<Subject> getSubjects() { return subjects; }

    public void setSubjects(List<Subject> subjects) { this.subjects = subjects; }




    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", collage='" + collage + '\'' +
                '}';
    }
}
