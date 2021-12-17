package com.example.todo.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "todos")
public class Todo {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String content;
    private Boolean done;

    public Todo(String content, boolean done) {
        this.content = content;
        this.done = done;
    }

    public Todo() {
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Boolean isDone() {
        return done;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
