package com.question_service.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response
{
    private Integer id;
    private String response;
}
