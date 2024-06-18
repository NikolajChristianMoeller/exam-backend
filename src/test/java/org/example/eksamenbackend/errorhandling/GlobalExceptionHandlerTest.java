package org.example.eksamenbackend.errorhandling;

import org.example.eksamenbackend.errorhandling.exception.NotFoundException;
import org.example.eksamenbackend.errorhandling.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    @Test
    void testHandleNotFoundException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        NotFoundException exception = new NotFoundException("Test not found exception");
        ResponseEntity<?> responseEntity = globalExceptionHandler.handleNotFoundException(exception);

        assertEquals(404, responseEntity.getStatusCode().value());
        assertEquals("NotFound error: Test not found exception", responseEntity.getBody());
    }

    @Test
    void testHandleValidationException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ValidationException exception = new ValidationException("Test validation exception");
        ResponseEntity<?> responseEntity = globalExceptionHandler.handleValidationException(exception);

        assertEquals(400, responseEntity.getStatusCode().value());
        assertEquals("Validation error: Test validation exception", responseEntity.getBody());
    }
}