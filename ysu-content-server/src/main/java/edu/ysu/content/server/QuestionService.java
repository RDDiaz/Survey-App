package edu.ysu.content.server;

import org.apache.commons.lang3.Validate;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

import java.util.UUID;
import java.util.List;

import static javafx.scene.input.KeyCode.T;

@Service
public class QuestionService {
    @Autowired
    private DSLContext jooq;

    //CREATE QUESTION
    public UUID create(String question, UUID questionListId, UUID[] answersId)
            throws RuntimeException {
        Validate.notBlank(question);
        List<UUID> id = jooq.fetch(
                "INSERT INTO question" +
                        "(question, question_list_id, answers_id) " +
                        "VALUES( " +
                        "?,?,?) " +
                        "RETURNING id"
                , question, questionListId, answersId).into(UUID.class);

        if (id.isEmpty()) {
            throw new RuntimeException("Database Error");
        }

        return id.get(0);
    }


    //GET QUESTION
    public Question get(UUID id) throws DataAccessException {
        //Check for valid ID. Throw exception if incorrect

        try {
             return jooq.fetchOne(
                    "SELECT * FROM question " +
                            "WHERE id = ?",
                    id
            ).into(Question.class);
        } catch (DataAccessException ex) {
            throw new DataAccessException("Unknown UUID");
        }

    }

    //DELETE QUESTION
    public String deleteQuestion(String id)
    {
        int response = jooq.execute
                (
                        "DELETE FROM QUESTION WHERE id = ?",
                        UUID.fromString(id)
                );
        if (response == 0) { return "HTTP:400"; }
        else { return "HTTP:200"; }
    }

    //UPDATE QUESTION
    public UUID updateQuestion(UUID id, String questionText)
        throws RuntimeException {
        Validate.notBlank(questionText);
        UUID ids = jooq.fetchOne(

                        "UPDATE question SET question = ? WHERE id = ?" +
                        "RETURNING id"
                , questionText, id).into(UUID.class);

            if (ids == null) {
                throw new RuntimeException("Database Error");
            }

        return ids;
    }



}
