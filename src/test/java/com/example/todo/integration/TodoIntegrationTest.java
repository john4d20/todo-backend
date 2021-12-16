package com.example.todo.integration;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    public void should_return_todo_when_create_todo_given_todo() throws Exception {

        String todoAsJson = "{\n" +
                "    \"content\" : \"test\",\n" +
                "    \"done\": true\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.content").value("test"))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    public void should_return_updated_todo_when_update_todo_given_id() throws Exception {
        //given
        Todo todo = todoRepository.save(new Todo("test", true));
        String todoAsJson = "{\n" +
                "    \"content\" : \"test\",\n" +
                "    \"done\": true\n" +
                "}";
        System.out.println("odo.getId()"+todo.getId());
        mockMvc.perform(MockMvcRequestBuilders.put("/todos/" + todo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.content").value("test"))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void should_delete_todo_when_perform_delete_given_todo() throws Exception {


        Todo todo = todoRepository.save(new Todo("test", true));
        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/" + todo.getId()))
                .andExpect(status().isNoContent());
    }

}
