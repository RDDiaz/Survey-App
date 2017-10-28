package edu.ysu.content.server;

import lombok.Data;
import java.sql.Time;
import java.util.UUID;

@Data
public class Answer {
    private String answerText;

    private UUID id;

    private Time dateCreated;

    private Time lastUpdated;

}
