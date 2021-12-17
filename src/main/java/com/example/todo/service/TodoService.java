package com.example.todo.service;
import com.example.todo.exception.ContentNotFoundException;
import com.example.todo.exception.DoneNotFoundException;
import com.example.todo.exception.TodoNotFoundException;
import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public List<Todo> getAll(){
        return todoRepository.findAll();
    }

    public Todo add(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo get(String id) {
        return todoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
    }

    public Todo update(String id, Todo todoUpdate) throws DoneNotFoundException {
        Todo originTodo = todoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
        if (todoUpdate.getContent() != null) {
            originTodo.setContent(todoUpdate.getContent());
        }
        else {
            throw new ContentNotFoundException();
        }
        if(todoUpdate.isDone() != null){
            originTodo.setDone(todoUpdate.isDone());
        }
        else {
            throw new DoneNotFoundException();
        }
        return todoRepository.save(originTodo);

    }

    public void remove(String id) {
        todoRepository.deleteById(id);
    }
}
