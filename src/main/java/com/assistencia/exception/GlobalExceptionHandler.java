package com.assistencia.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ProblemDetail;
import java.net.URI;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail tratarValidacao(MethodArgumentNotValidException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Erro de validação");
        pd.setDetail("Um ou mais campos são inválidos.");
        pd.setType(URI.create("https://api.assistencia.com/erros/validacao"));
        Map<String, String> erros = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> erros.putIfAbsent(e.getField(), e.getDefaultMessage()));
        pd.setProperty("campos", erros);
        return pd;
    }
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ProblemDetail tratarNaoEncontrado(RecursoNaoEncontradoException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Recurso não encontrado");
        return pd;
    }
    @ExceptionHandler(RegraNegocioException.class)
    public ProblemDetail tratarRegra(RegraNegocioException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        pd.setTitle("Regra de negócio violada");
        return pd;
    }
}
