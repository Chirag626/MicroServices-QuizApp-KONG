package com.quiz_service.feign;

import com.quiz_service.dto.QuestionAnswerDto;
import com.quiz_service.dto.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "QUESTION-SERVICE",path = "/question")
public interface QuizFeignClient
{
    // Get random questions for quiz creation
    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionIdsForQuiz
    (@RequestParam ("categoryName")String categoryName, @RequestParam("numQue") Integer numQue);

    // Get questions by IDs
    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionAnswerDto>> getQuestionFromIds
            (@RequestBody List<Integer> questionIds);

    // getScore for calculating total score.
    @PostMapping("/getScore")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> responses);
}

