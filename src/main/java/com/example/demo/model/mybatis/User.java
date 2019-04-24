package com.example.demo.model.mybatis;

public class User {
    private int id;
    private String name;
    private String description;
    private String[] models;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getModels() {
        return models;
    }

    public void setModels(String[] models) {
        this.models = models;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name : ");
        sb.append(getName());
        sb.append("\n");

        sb.append("id : ");
        sb.append(getId());
        sb.append("\n");

        sb.append("description : ");
        sb.append(getDescription());
        sb.append("\n");

        sb.append("models :");
        sb.append(String.join(", ", getModels()));

        return sb.toString();
    }
}
