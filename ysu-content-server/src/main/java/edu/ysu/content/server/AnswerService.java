package edu.ysu.content.server;

import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.UUID;

@Service
public class AnswerService {
    @Autowired
    private DSLContext jooq;

    public UUID create(String answerText, UUID questionId)
            throws RuntimeException {
        Validate.notBlank(answerText);
        List<UUID> ids = jooq.fetch("INSERT INTO ANSWER" +
                "(ANSWER_TEXT, QUESTION_ID) VALUES (?, ?)" +
                        "RETURNING ID;"
        , answerText, questionId).into(UUID.class);

        if (ids.isEmpty()) {
            throw new RuntimeException("Database Error");
        }

        return ids.get(0);
    }
    
    public String updateAnswer(UUID id, String answerText){
    	jooq.execute
    		(
    				"UPDATE answer SET answer_text = ? WHERE id = ?",
    				answerText, id		
    		);
    	//TODO if statements for failure and success
    	return "HTTP:400";
    }

    public String deleteAnswer(String id)
    {
        int response = jooq.execute
                (
                        "DELETE FROM ANSWER WHERE id = ?",
                        UUID.fromString(id)
                );
        if (response == 0) { return "HTTP:400"; }
        else { return "HTTP:200"; }
    }

    //GET ANSWER
    public Answer get(UUID id) throws DataAccessException {
        //Check for valid ID. Throw exception if incorrect

        try {
            return jooq.fetchOne(
                    "SELECT * FROM answer " +
                            "WHERE id = ?",
                    id
            ).into(Answer.class);
        } catch (DataAccessException ex) {
            throw new DataAccessException("Unknown UUID");
        }

    }
}
