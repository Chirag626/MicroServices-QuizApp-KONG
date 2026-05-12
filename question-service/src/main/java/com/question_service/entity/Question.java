package com.question_service.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity

@Data // getters, setters, toString

// this annotation will give the JSON response in this pattern.
@JsonPropertyOrder({ 
    "id",
    "category",
    "question",
    "option1",
    "option2",
    "option3",
    "option4",
    "answer"
})
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String category;

    @Column(name = "difficulty_level")
    private String difficulty;

    @Column(nullable = false)
    private  String option1,option2,option3,option4;

    @Column(name = "correct_answer")
    private String answer;

    @NotBlank(message = "Question can't be empty") // Validation and add @Valid on controller method.but to use this we have to add validation dependency in pom.xml.
    private String question;
}
