package com.example.api.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StandardApiError {

    private LocalDateTime timestamp;
    private Integer status;
    private List<String> errors;
    private String path;

}
