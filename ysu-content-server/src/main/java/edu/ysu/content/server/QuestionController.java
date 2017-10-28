package edu.ysu.content.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    // CREATE QUESTION
    @PostMapping("/question")
    public ResponseEntity<UUID> create(@RequestParam(value = "question")       String question,
                                       @RequestParam(value = "questionListId") UUID questionListId,
                                       @RequestParam(value = "answersId", required = false)      UUID[] answersId) {


        try {
            UUID id = questionService.create(question, questionListId, answersId);
            return new ResponseEntity<UUID>(id, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<UUID>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/question")
    public Question get(@RequestParam(value = "id") UUID id){
        Question q = questionService.get(id);
        // TODO: Once AnswerService is finished, get all answers for this Question and add them to it.
        return q;
    }

    @DeleteMapping("/question")
    public String delete(@RequestParam(value="id") String id)
    {
        return questionService.deleteQuestion(id);
    }

    @PutMapping("/question")
    public UUID update(@RequestParam(value="id") UUID questionId,
                       @RequestParam(value = "questionText") String questionText) {

        return questionService.updateQuestion(questionId, questionText);
    }

}
