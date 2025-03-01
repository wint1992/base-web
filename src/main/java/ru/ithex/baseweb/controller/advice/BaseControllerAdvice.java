package ru.ithex.baseweb.controller.advice;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ithex.baseweb.exception.*;
import ru.ithex.baseweb.model.dto.response.ResponseWrapperDTO;
import ru.ithex.baseweb.model.dto.response.error.AuthenticationError;
import ru.ithex.baseweb.model.dto.response.error.BadRequestError;
import ru.ithex.baseweb.model.dto.response.error.InternalServerError;
import ru.ithex.baseweb.model.dto.response.error.ValidationError;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseControllerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapperDTO baseHandle(Exception e, HttpServletRequest request) {
        return ResponseWrapperDTO.error(new InternalServerError("Системная ошибка."));
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseWrapperDTO handle(CustomAuthenticationException e, HttpServletRequest request){
        return ResponseWrapperDTO.error(new AuthenticationError(e.getMessage()));
    }

    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapperDTO handle(CustomBadRequestException e, HttpServletRequest request){
        return ResponseWrapperDTO.error(new BadRequestError(e.getMessage()));
    }

    @ExceptionHandler(CustomInternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapperDTO handle(CustomInternalServerException e, HttpServletRequest request){
        return ResponseWrapperDTO.error(new InternalServerError(e.getMessage()));
    }

    @ExceptionHandler(CustomValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapperDTO handle(CustomValidationException e, HttpServletRequest request){
        return ResponseWrapperDTO.error(new ValidationError(e.getMessage()));
    }

    @ExceptionHandler(NestedRuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapperDTO handle(NestedRuntimeException e, HttpServletRequest request){
        return ResponseWrapperDTO.error(new InternalServerError(e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage()));
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapperDTO handle(HttpMessageConversionException e, HttpServletRequest request){
        return ResponseWrapperDTO.error(new InternalServerError("Внутренняя ошибка преобразования входных данных"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapperDTO handle(HttpRequestMethodNotSupportedException e, HttpServletRequest request){
        return ResponseWrapperDTO.error(new BadRequestError(e.getMessage()));
    }

    @ExceptionHandler(RestException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapperDTO handle(RestException e, HttpServletRequest request){
        return ResponseWrapperDTO.error(new InternalServerError("Ошибка удаленного вызова"));
    }

    @ExceptionHandler(RestServiceSystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapperDTO handle(RestServiceSystemException e, HttpServletRequest request){
        return ResponseWrapperDTO.error(new InternalServerError("Системная ошибка удаленного вызова"));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapperDTO handle(MissingServletRequestParameterException e, HttpServletRequest request){
        return ResponseWrapperDTO.error(new BadRequestError(String.format("Отсутствуют необходимые параметры запроса: %s", e.getMessage())));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseWrapperDTO handle(HttpMessageNotReadableException e, HttpServletRequest request){
        return ResponseWrapperDTO.error(new BadRequestError("Ошибочный формат передаваемых данных"));
    }
}