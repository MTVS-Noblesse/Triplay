package com.noblesse.backend.post.command.application.handler;

import com.noblesse.backend.global.handler.GlobalExceptionHandler;
import com.noblesse.backend.global.util.ErrorResponse;
import com.noblesse.backend.post.common.exception.PostCoCommentNotFoundException;
import com.noblesse.backend.post.common.exception.PostCommentNotFoundException;
import com.noblesse.backend.post.common.exception.PostNotFoundException;
import com.noblesse.backend.post.common.exception.PostReportNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "com.noblesse.backend.post")
public class PostExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException ex) {
        log.error("# Handling PostNotFoundException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse("POST_NOT_FOUND", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostCommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostCommentNotFoundException(PostCommentNotFoundException ex) {
        log.error("# Handling PostCommentNotFoundException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse("POST_COMMENT_NOT_FOUND", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostCoCommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostCoCommentNotFoundException(PostCoCommentNotFoundException ex) {
        log.error("# Handling PostCoCommentNotFoundException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse("POST_CO_COMMENT_NOT_FOUND", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostReportNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostReportNotFoundException(PostReportNotFoundException ex) {
        log.error("# Handling PostReportNotFoundException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse("POST_REPORT_NOT_FOUND", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
