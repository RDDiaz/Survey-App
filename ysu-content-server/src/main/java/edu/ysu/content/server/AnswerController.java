package edu.ysu.content.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @PostMapping("/answer")
    public ResponseEntity<UUID> create(@RequestParam(value = "answerText") String answerText,
                                 @RequestParam(value = "questionId") UUID questionId) {
        if (questionId == null || answerText == null)
            return new ResponseEntity<UUID>(HttpStatus.BAD_REQUEST);
        try {
            UUID id = answerService.create(answerText, questionId);
            return new ResponseEntity<UUID>(id, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<UUID>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/answer")
    public String update(UUID id, String answerText){
    	return answerService.updateAnswer(id, answerText);
    }

    @DeleteMapping("/answer")
    public String delete(@RequestParam(value="id") String id){
        return answerService.deleteAnswer(id);
    }

    @GetMapping("/answer")
    public Answer get(@RequestParam(value = "id") UUID id){
        Answer a  = answerService.get(id);

        return a;
    }
}
