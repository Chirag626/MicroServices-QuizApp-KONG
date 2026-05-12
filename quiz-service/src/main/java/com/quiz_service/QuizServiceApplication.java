package com.quiz_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class QuizServiceApplication  // QuizService
{

	public static void main(String[] args)
	{
		SpringApplication.run(QuizServiceApplication.class, args);
	}

}
