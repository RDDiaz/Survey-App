package edu.ysu.content.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@RestController
public class QuestionListController {

    @Autowired
    QuestionListService questionListService;

    @PostMapping("/questionList")
    public ResponseEntity<UUID> create(@RequestParam(value = "userID") UUID userID,
                                       @RequestParam(value = "name") String name)
    {
        try
        {
            UUID id = questionListService.create(userID, name);
            return new ResponseEntity<UUID>(id, HttpStatus.OK);
        }
        catch (RuntimeException ex) {
            return new ResponseEntity<UUID>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/questionList")
    public QuestionList get(@RequestParam(value = "id") UUID id)

    {

        return questionListService.get(id);
    }

    @PutMapping("/questionList")
    public String update(UUID id, String name){return questionListService.updateList(id, name);}

    @DeleteMapping("/questionList/{id}")
    public void delete(UUID id) {questionListService.deleteList(id);}

}
