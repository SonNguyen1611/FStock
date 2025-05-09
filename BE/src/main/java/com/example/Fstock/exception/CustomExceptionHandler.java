package com.example.Fstock.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {
    // khi một exception kiểu NotFound xảy ra trong heej thống sẽ đươợc xử lí tại đây
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e, WebRequest request) {
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .build();

    }
    // không có quyền truy cập
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(ForbiddenException e, WebRequest request) {
        return ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN)
                .message(e.getMessage())
                .build();
    }

    // xung đột tài nguyên ( người dùng đã tồn taại ..)
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(ConflictException e, WebRequest request) {
        return ErrorResponse.builder()
                .status(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .build();
    }
    // không có quyền truy cập
    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUnAuthorizedException(UnAuthorizedException e, WebRequest request) {
        return ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .build();
    }
    // lỗi từ phía server
    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerException(InternalServerErrorException e, WebRequest request) {
        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
    }
    // lỗi từ phía client
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException e, WebRequest request) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
    }

    // lỗi dữ liệu không ph hợp
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorResponse> handleValidationgException(MethodArgumentNotValidException e, WebRequest request) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
           errorResponses.add(ErrorResponse.builder().status(HttpStatus.BAD_REQUEST).
                   message( fieldError.getDefaultMessage())
                   .build());
        });

        return errorResponses;
    }


}