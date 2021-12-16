package com.example.todo.integration;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @AfterEach
    void deleteAfterEach() {
        todoRepository.deleteAll();
    }

    @BeforeEach
    void deleteBeforeEach() {
        todoRepository.deleteAll();
    }

    @Test
    public void should_return_todo_list_when_get_all_given_nothing() throws Exception {

        Todo todo = new Todo("test",true);
        todoRepository.insert(todo);
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].content").value("test"))
                .andExpect(jsonPath("$[0].done").value(true));
    }

}
