package com.ceuma.connectfono.handlers;

import com.ceuma.connectfono.core.patient.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.sqlite.SQLiteException;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException methodArgumentNotValidException,
            HttpHeaders headers,
            WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error. Check 'errors' field for details.");
        for(FieldError fieldError: methodArgumentNotValidException.getBindingResult().getFieldErrors()){
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }
    //Exception para pegar todas que não forem mapeadas
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request){
        final String errorMessage = "Unknow error ocurred";
        log.error(errorMessage, exception);
        return buildErrorResponse(
                exception,
                errorMessage,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );

    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> HandleAllRuntimeException(Exception exception, WebRequest request){
        final String errorMessage = "Erro desconhecido";
        log.error(errorMessage, exception);
        return buildErrorResponse(
                exception,
                errorMessage,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );

    }

    @ExceptionHandler(SQLiteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> HandleSqliteException(SQLiteException exception, WebRequest request){
        log.error("ERRO AO INSERIR NO BANCO DE DADOS" + exception.getMessage());
        log.error("error code " + exception.getErrorCode());
        return buildErrorResponse(
                exception,
                getSqliteErrorMessage(exception.getErrorCode()),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(JpaSystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> HandleJpaExceptions(JpaSystemException exception, WebRequest request){
        final String errorMessage = "Erro interno do banco de dados";
        if(exception.getRootCause() instanceof SQLiteException) return HandleSqliteException((SQLiteException) exception.getRootCause(), request);
        log.error("ERRO AO INSERIR NO BANCO DE DADOS" + exception.getMostSpecificCause());
        return buildErrorResponse(
                exception,
                errorMessage + " " +  exception.getMostSpecificCause(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    // Excepetion para pegar violação de dados
    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleConstraintException(org.hibernate.exception.ConstraintViolationException constraintViolationException, WebRequest request){
        log.error("Falha ao salvar paciente, campo já cadastrado", constraintViolationException);
        return buildErrorResponse(
                constraintViolationException,
                HttpStatus.UNPROCESSABLE_ENTITY,
                request
        );
    }

    @ExceptionHandler(JDBCException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleIntegrityException(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException, WebRequest request){
        final String message = "Não é possível deletar uma entidade com relações ativas. Por favor delete suas relações primeiro ";
        log.error(message);
        return buildErrorResponse(
                sqlIntegrityConstraintViolationException,
                message,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleDataIntegrityException(DataIntegrityViolationException dataIntegrityViolationException, WebRequest request){

        final String errorMessage =
                dataIntegrityViolationException.getMessage().contains("foreign key constraint fails") ?
                        "Não é possível deletar uma entidade com relações ativas no banco de dados. Verifique as subrelações primeiro." :
                        "Campos duplicados";
        log.error(errorMessage);
        return buildErrorResponse(
                dataIntegrityViolationException,
                errorMessage,
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException badRequestException, WebRequest request){
        final String errorMessage = badRequestException.getMessage();
        log.error("[PATIENT]  BAD REQUEST ");
        return buildErrorResponse(
                badRequestException,
                errorMessage,
                HttpStatus.BAD_REQUEST,
                request
        );
    }


    private ResponseEntity<Object> buildErrorResponse(Exception exception, String message, HttpStatus httpStatus, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception, HttpStatus httpStatus, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), exception.getMessage());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    public static String getSqliteErrorMessage(int errorCode){
        switch (errorCode){
            case 19:
                return "UM CAMPO JÁ EXISTENTE FOI INSERIDO";
            default:
                return "ERRO DESCONHECIDO";
        }
    }

}
