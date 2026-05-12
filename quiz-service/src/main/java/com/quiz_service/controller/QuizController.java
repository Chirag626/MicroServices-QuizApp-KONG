package com.quiz_service.controller;


import com.quiz_service.dto.QuestionAnswerDto;
import com.quiz_service.dto.QuizDto;
import com.quiz_service.dto.Response;
import com.quiz_service.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(quizService.createQuiz(quizDto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionAnswerDto>> getQuizQuestions(@PathVariable Integer id) {
        return ResponseEntity.ok(quizService.getQuizQuestions(id));
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz( @PathVariable("id") Integer quizId,
            @RequestBody List<Response> responses)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(quizService.submitQuiz(quizId, responses));
    }
}