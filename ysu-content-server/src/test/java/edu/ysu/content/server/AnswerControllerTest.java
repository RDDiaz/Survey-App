package edu.ysu.content.server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest (classes = ContentServerApplication.class)
@WebAppConfiguration
public class AnswerControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private Answer testAnswer;
    private UUID testQuestionUUID = UUID.fromString("e0c243e2-ed82-4dbb-a8b4-dbeeaa2f6ec8");

    private ResultActions testCreateAnswerAPI(Answer answer, UUID questionId) throws Exception {
        String requestAnswerText = "", requestQuestionId = "";

        if (answer.getAnswerText() != null)
            requestAnswerText = answer.getAnswerText();

        if (questionId != null)
            requestQuestionId = questionId.toString();

        return mockMvc.perform(post("/answer")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("answerText", requestAnswerText)
                .param("questionId", requestQuestionId));
    }

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        testAnswer = new Answer();
    }

    @Test
    public void testCreateNoAnswer() throws Exception {
        //empty answerText should return HTTP 400
        testAnswer.setAnswerText(null);
        testCreateAnswerAPI(testAnswer, testQuestionUUID)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateNoQuestionId() throws Exception {
        //empty questionId should return HTTP 400
        testAnswer.setAnswerText("sample");
        testCreateAnswerAPI(testAnswer, null)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateOk() throws Exception {
        //valid requests should return HTTP 200
        testAnswer.setAnswerText("sample");
        testCreateAnswerAPI(testAnswer, testQuestionUUID)
                .andExpect(status().isOk());
    }
}