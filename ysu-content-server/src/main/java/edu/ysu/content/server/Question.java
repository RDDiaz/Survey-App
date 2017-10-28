package edu.ysu.content.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class Question {
    private UUID id;
    private String question;
    private UUID questionListId;
    private List<UUID> answersId = new ArrayList<UUID>();
    private UUID correctAnswer;
    private Date dateCreated;
    private Date lastUpdate;

    public void addAnswer(Answer answer) {
        answersId.add(answer.getId());
    }
}
