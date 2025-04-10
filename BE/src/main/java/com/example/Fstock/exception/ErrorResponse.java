package com.example.Fstock.exception;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ErrorResponse {
    private HttpStatus status;
    private String message;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();


}
