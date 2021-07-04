package com.example.goalkeeper;

public class Subject {
    private int id;
    private String name;
    private String description;

    public Subject(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
