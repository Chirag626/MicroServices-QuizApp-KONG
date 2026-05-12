package com.question_service.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.question_service.dto.AnswerUpdateRequestDto;
import com.question_service.dto.QuestionAnswerDto;
import com.question_service.dto.Response;
import com.question_service.dto.UpdateAnyDataDto;
import com.question_service.entity.Question;
import com.question_service.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class QuestionService 
{
    private final QuestionRepository questionRepository;

    private final ModelMapper modelMapper;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();

    }

    public Question getById(Integer id) 
    {    
        return questionRepository.findById(id).orElse(null);
   
    }

    public List<Question> findByCategory(String category) {
        return questionRepository.findByCategory(category);
    }

    public Question addQuestion(Question que) {
        return questionRepository.save(que);
    }

    public Question updateAnswer(Integer id, AnswerUpdateRequestDto correctAnswer) {

        // Step 1: Fetch existing question
        Question que = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        // Step 2: Update only answer(here, we take the new answer from "correctAnswer"(Dto ka object) and set it into the existing "que" object (Entity ka object))
        que.setAnswer(correctAnswer.getCorrectAnswer());

        // Step 3: Save back to DB
        return questionRepository.save(que);
    }

    public Question updateAnyExisting(Integer id, UpdateAnyDataDto updatedData) {
        Question question = questionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Id doesn't exist : " + id));
        
        // Map only non-null fields from DTO to entity and converts DTO to Entity
        modelMapper.map(updatedData, question);
        
        
        return questionRepository.save(question);

    }

    public void saveAll(List<Question> questions) {
        questionRepository.saveAll(questions);
    }

    public void deleteById(Integer id) 
    {
       questionRepository.deleteById(id);
    }

    public boolean existsById(Integer id)
    {
        return (questionRepository.existsById(id));
    }

    public List<Integer> getQuestionForQuiz(String categoryName, Integer numQue)
    {
        return questionRepository.findRandomQuestionByCategory(categoryName, numQue);
    }


    public List<QuestionAnswerDto> getQuestionsFromIds(List<Integer> questionIds)
    {
        // step 1 : Fetch question from DB
        List<Question> questionsList = questionRepository.findAllById(questionIds);

        // step 2 : convert Question -> QuestionAnswerDto and return
        return questionsList.stream()
                .map(q -> modelMapper.map(q, QuestionAnswerDto.class))
                .toList();

    }

    public Integer calculateScore(List<Response>  responses)
    {
        // Step 1: Extract IDs from responses
        List<Integer> ids = responses.stream()
                .map(Response::getId)
                .toList();

        // Step 2: Fetch all questions in ONE DB call
        List<Question> questions = questionRepository.findAllById(ids);

        // Step 3: Convert to Map (id → correct answer)
        Map<Integer, String> answerMap = new HashMap<>();
        for (Question q : questions)
        {
            answerMap.put(q.getId(), q.getAnswer());
        }

        int score = 0;

        // Step 4: Compare responses
        for (Response res : responses)
        {
            if (answerMap.containsKey(res.getId()) &&
                    res.getResponse() != null &&
                    res.getResponse().equalsIgnoreCase(answerMap.get(res.getId()))) {
                score++;
            }
        }

        return score;
    }
}
