package com.quiz_service.service;

import com.quiz_service.dto.QuestionAnswerDto;
import com.quiz_service.dto.QuizDto;
import com.quiz_service.dto.Response;
import com.quiz_service.entity.Quiz;
import com.quiz_service.feign.QuizFeignClient;
import com.quiz_service.repository.QuizRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizFeignClient quizFeignClient;

    // CREATE QUIZ
    @SuppressWarnings("null") // to ignore compiler warnings
    public String createQuiz(QuizDto quizDto)
    {
        if (quizDto.getCategory() == null || quizDto.getNumOfQuestions() == null
                || quizDto.getNumOfQuestions() <= 0)
        {
            throw new RuntimeException("Invalid quiz data");
        }

        Quiz quiz = new Quiz();
        quiz.setCategory(quizDto.getCategory());
        quiz.setTitle(quizDto.getTitle());
        quiz.setNumOfQuestions(quizDto.getNumOfQuestions());

        // FEIGN CALL
        ResponseEntity<List<Integer>> response = quizFeignClient
                .getQuestionIdsForQuiz(quizDto.getCategory(), quizDto.getNumOfQuestions());

        //  IMPORTANT: Feign returns ResponseEntity, so we MUST use .getBody()
        if (response.getBody() == null || response.getBody().isEmpty()) {
            throw new RuntimeException("Failed to fetch questions from Question Service");
        }

        List<Integer> questionIds = response.getBody();
        quiz.setQuestionIds(questionIds);
        quizRepository.save(quiz);

        return "Quiz Created Successfully!";
    }

    // GET QUIZ QUESTIONS
    public List<QuestionAnswerDto> getQuizQuestions(Integer id) {

        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));

        // FEIGN CALL
        ResponseEntity<List<QuestionAnswerDto>> response =
                quizFeignClient.getQuestionFromIds(quiz.getQuestionIds());


        //  IMPORTANT: Feign returns ResponseEntity, so we MUST use .getBody()
        if (response.getBody() == null)
        {
            throw new RuntimeException("Failed to fetch questions");
        }
        else
        {
            return response.getBody();
        }

    }

    // SUBMIT QUIZ
    public Integer submitQuiz(Integer quizId, List<Response> responses) {

        quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));

        if (responses == null || responses.isEmpty())
        {
            throw new RuntimeException("Responses cannot be empty");
        }

        //  FEIGN CALL
        ResponseEntity<Integer> response =
                quizFeignClient.calculateScore(responses);

        //  IMPORTANT: Feign returns ResponseEntity, so we MUST use .getBody()
        if (response.getBody() == null)
        {
            throw new RuntimeException("Failed to calculate score");
        }
        else
        {
            return response.getBody();
        }
    }
}