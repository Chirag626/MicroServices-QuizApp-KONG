package com.question_service.controller;

import com.question_service.dto.AnswerUpdateRequestDto;
import com.question_service.dto.QuestionAnswerDto;
import com.question_service.dto.Response;
import com.question_service.dto.UpdateAnyDataDto;
import com.question_service.entity.Question;
import com.question_service.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;


    @GetMapping("/allQuestions")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/id/{id}")
    public Question getById(@PathVariable Integer id)
    {
        return questionService.getById(id);
    }

    @GetMapping("/category/{category}")
    public List<Question> getByCategory(@PathVariable String category) {
        return questionService.findByCategory(category);
    }

    // Add One By One
    @PostMapping("/add")
    public ResponseEntity<Question> addQuestion(@Valid @RequestBody Question que) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(questionService.addQuestion(que));
    }

    @PostMapping("/addBatch")
    public ResponseEntity<?> addQuestions(@RequestBody List<Question> questions) {
        questionService.saveAll(questions);
        return ResponseEntity.ok(questions.size() + " questions added!");
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Question> updateAnswer(@PathVariable Integer id,
                                                 @RequestBody AnswerUpdateRequestDto updatedAnswer) {
        return ResponseEntity.ok(questionService.updateAnswer(id, updatedAnswer));
    }

    @PatchMapping("/updateAnyExisting/{id}")
    public ResponseEntity<Question> updateAnyExistingData(@PathVariable Integer id,
                                                          @RequestBody UpdateAnyDataDto updatedData) {
        return ResponseEntity.ok(questionService.updateAnyExisting(id, updatedData));
    }

    @PutMapping("/updateall/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Integer id,
                                                   @RequestBody Question updatedQuestion) {

        // Replace all fields
        updatedQuestion.setId(id); // ensure ID stays same

        // ❌ ERROR FIX: direct repository access hata diya
        // ✅ Correct way → service ke through save karo
        Question saved = questionService.addQuestion(updatedQuestion);

        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        if (!questionService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        questionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // generate questions for Quiz , input : category or no. of questions, O/P : [1, 5, 8, 10]  returning only id's..
    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionIdsForQuiz(
            @RequestParam ("categoryName") String categoryName,
            @RequestParam ("numQue") Integer numQue) {

        List<Integer> questions = questionService.getQuestionForQuiz(categoryName, numQue);
        return ResponseEntity.ok(questions);
    }

    // get the questions by giving Ids to Quiz using getQuestions(questionId)
    // {for-eg: in json body if we put [1,2,3] it'll fetch the questions associate with these id's.}

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionAnswerDto>> getQuestionFromIds(
            @RequestBody List<Integer> questionIds) {

        List<QuestionAnswerDto> que = questionService.getQuestionsFromIds(questionIds);
        return ResponseEntity.ok(que);
    }

    // getScore  for calculating score coz we have the questions and answers so it's our job to calculate
    @PostMapping("/getScore")
    public ResponseEntity<Integer> calculateScore(@RequestBody List<Response> responses) {
        return ResponseEntity.ok(questionService.calculateScore(responses));
    }
}