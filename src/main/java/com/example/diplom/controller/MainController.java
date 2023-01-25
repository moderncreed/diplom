package com.example.diplom.controller;

import com.example.diplom.exceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("main")
public class MainController {
    private int counter = 4;
    private List<Map<String, String>> mess = new ArrayList<Map<String,String>>() {{
        add(new HashMap<String,String>() {{put("id","1"); put("text", "dsadsadsa");
        }});
    }};

    @GetMapping
    public List list() {
        return mess;
    }

    @GetMapping("{id}")
    public Map<String,String> getMessage(@PathVariable String id) {

        return mess.stream()
                .filter(mess -> mess.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String,String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));
        mess.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String,String> update(@PathVariable String id,@RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessage(message.get("id"));
        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        return messageFromDb;
    }
    @DeleteMapping("{id}")
    public  void delete(@PathVariable String id) {
        Map<String,String> message = getMessage(id);

        mess.remove(message);
    }
}
