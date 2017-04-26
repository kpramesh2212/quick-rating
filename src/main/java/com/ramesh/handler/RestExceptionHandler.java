package com.ramesh.handler;

import com.ramesh.error.ErrorDetail;
import com.ramesh.error.ValidationError;
import com.ramesh.exception.ResourceNotFoundException;
import com.ramesh.exception.UnAuthorizedException;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Inject
    private MessageSource messageSource;

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException
                                                                          ex,
                                                             HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Message Not Readable");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass().getName());

        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe,
                                                          HttpServletRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());

        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<?> handleUnAuthorizedException(UnAuthorizedException rnfe,
                                                             HttpServletRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTitle("Unauthorized Exception");
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());

        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException manve,
             HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTitle("Validation Failed");
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setDetail("Input Field validation Failed");
        errorDetail.setDeveloperMessage(manve.getClass().getName());

        List<FieldError> fieldErrors = manve.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            final String field = fieldError.getField();
            List<ValidationError> validationErrorList = errorDetail.getErrors().get(field);
            if (validationErrorList == null) {
                validationErrorList = new ArrayList<>();
                errorDetail.getErrors().put(field, validationErrorList);
            }
            ValidationError ve = new ValidationError();
            ve.setCode(fieldError.getCode());
            ve.setMessage(messageSource.getMessage(fieldError, null));
            validationErrorList.add(ve);
        }
        return handleExceptionInternal(manve, errorDetail, headers, status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException
            (ConstraintViolationException cve, HttpServletRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTitle("Validation Failed");
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setDetail("Input Field validation Failed");
        errorDetail.setDeveloperMessage(cve.getClass().getName());

        Set<ConstraintViolation<?>> constraintViolations = cve.getConstraintViolations();
        for (ConstraintViolation<?> cv : constraintViolations) {
            String propertyClassName = cv.getRootBeanClass().getSimpleName();
            String constraintName = cv.getConstraintDescriptor().getAnnotation().annotationType()
                                  .getSimpleName();
            String property = cv.getPropertyPath().toString();
            final String code = constraintName + "." + propertyClassName.toLowerCase() + "." + property;

            List<ValidationError> validationErrorList = errorDetail.getErrors().get(propertyClassName);
            if (validationErrorList == null) {
                validationErrorList = new ArrayList<>();
                errorDetail.getErrors().put(propertyClassName, validationErrorList);
            }
            ValidationError ve = new ValidationError();
            ve.setCode(cv.getMessage());
            ve.setMessage(messageSource.getMessage(code, new Object[]{}, cv.getMessage(), null));
            validationErrorList.add(ve);
        }

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

}
