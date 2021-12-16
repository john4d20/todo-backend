package com.example.todo.exception;

public class TodoNotFoundException extends RuntimeException{
    public TodoNotFoundException() {
        super("Todo Not Found");
    }
}
