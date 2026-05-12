package com.quiz_service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data

@JsonPropertyOrder({
        "id",
        "question",
        "option1",
        "option2",
        "option3",
        "option4",
})
public class QuestionAnswerDto
{
    private Integer id;
    private String question;
    private String option1,option2,option3,option4;
}