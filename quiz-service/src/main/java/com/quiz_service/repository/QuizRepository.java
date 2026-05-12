    package com.quiz_service.repository;

    import org.springframework.data.jpa.repository.JpaRepository;

    import com.quiz_service.entity.Quiz;

    public interface QuizRepository extends  JpaRepository<Quiz, Integer>
    {

    }
