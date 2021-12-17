package com.example.todo.service;

import com.example.todo.exception.DoneNotFoundException;
import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @InjectMocks
    private TodoService todoService;
    @Mock
    private TodoRepository todoRepository;

//    post create
    @Test
    void should_return_todo_when_perform_add_given_no_todo() {
        //given
        Todo todo = new Todo("test", true);
        when(todoRepository.save(todo)).thenReturn(todo);

        //when
        final Todo actual = todoService.add(todo);

        //then
        assertEquals(todo, actual);
    }

//    get
    @Test
    void should_return_todo_when_perform_get_given_id() {
        Todo todo = new Todo("test", true);
        String id = todo.getId();
        when(todoRepository.findById(id)).thenReturn(java.util.Optional.of(todo));

        final Todo actual = todoService.get(id);
        assertEquals(todo, actual);
    }

    @Test
    void should_return_todo_list_when_perform_get_nothing() {
        Todo todo = new Todo("test", true);
        todoService.add(new Todo("test", true));
        todoService.add(new Todo("test", true));
        List<Todo> todoList = Arrays.asList(todo,todo);
        when(todoRepository.findAll()).thenReturn(todoList);

        final List<Todo> actual = todoService.getAll();
        assertEquals(todoList, actual);
    }

//    put
    @Test
    void should_return_todo_when_perform_update_given_todo() throws DoneNotFoundException {
        //given
        Todo toDoItem = new Todo( "Do Homework", false);
        Todo updatedToDoItem = new Todo( "Do CSS", true);
        //when
        when(todoRepository.findById(toDoItem.getId()))
                .thenReturn(java.util.Optional.of(toDoItem));
        when(todoRepository.save(toDoItem))
                .thenReturn(updatedToDoItem);
        //then
        Todo actual = todoService.update(toDoItem.getId(), updatedToDoItem);
        assertEquals(updatedToDoItem, actual);
    }

    @Test
    void should_confirm_function_run_one_times_when_delete_todo_given_id() {
        //given
        Todo todo = new Todo("test", true);
        todoService.add(todo);

        //when
        todoService.remove(todo.getId());
        final ArgumentCaptor<Todo> employeeArgumentCaptor = ArgumentCaptor.forClass(Todo.class);

        //then
        verify(todoRepository, times(1)).deleteById(todo.getId());

    }


}
