package edu.ysu.content.server;

import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jooq.DSLContext;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionListService
{
    @Autowired
    private DSLContext jooq;

    public UUID create(UUID userID, String name)
    {
        List<UUID> ids = jooq.fetch
                (
                        "INSERT INTO question_list" +
                                "(userID,name) " +
                                "VALUES( " +
                                "?,?) " +
                                "RETURNING id",
                        userID, name).into(UUID.class);
        UUID newQuestionList = ids.get(0);
        return newQuestionList;
    }

    //GetQuestionList
    public QuestionList get(UUID id)
    {
        return jooq.fetch(
                "SELECT * FROM question_list WHERE id = ?", id

        ).into(QuestionList.class).get(0);
    }


    public String updateList(UUID id, String name){
    	jooq.execute
    		(
    				"UPDATE question_list SET name = ? WHERE id = ?",
    				name, id
    		);
    	//TODO if statements for failure and success
    	return "HTTP:400";
    }

    public String deleteList(UUID id) throws DataAccessException {
        try{
            jooq.execute(
                        "DELETE FROM question_list" +
                        " WHERE id= "+
                        "?",
                        id
                        );
            return "HTTP: 200";
        } catch (DataAccessException ex){
            throw new DataAccessException("Unknown UUID");
        }
    }
}
