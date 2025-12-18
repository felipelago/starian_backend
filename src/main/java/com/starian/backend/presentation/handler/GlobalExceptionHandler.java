package com.starian.backend.presentation.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.starian.backend.domain.exception.BusinessException;
import com.starian.backend.presentation.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ApiErrorResponse build(int status, String message, HttpServletRequest request) {
        String path = request != null ? request.getRequestURI() : "N/A";
        return new ApiErrorResponse(status, path, message, LocalDateTime.now());
    }

    /**
     * Handler para exceção de serviço indisponível.
     *
     * @param ex A exceção lançada quando o serviço está indisponível.
     * @return Uma resposta HTTP com status 503 e detalhes do erro.
     */
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ApiErrorResponse> handleServiceUnavailableException(ServiceUnavailableException ex, HttpServletRequest request) {
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(build(HttpStatus.SERVICE_UNAVAILABLE.value(), ex.getMessage(), request));
    }

    /**
     * Handler para exceção de mapeamento JSON.
     *
     * @param ex A exceção lançada durante o mapeamento JSON.
     * @return Uma resposta HTTP com status 400 e detalhes do erro.
     */
    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ApiErrorResponse> jsonMappingException(JsonMappingException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(build(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request));
    }

    /**
     * Handler para exceção de validação de argumentos.
     *
     * @param ex A exceção lançada quando a validação dos argumentos falha.
     * @return Uma resposta HTTP com status 400 e detalhes do erro.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(build(HttpStatus.BAD_REQUEST.value(), "Dados inválidos: " + message, request));
    }

    /**
     * Handler para exceção de violação de restrição de bean validation em parâmetros simples (@RequestParam, @PathVariable, etc.).
     *
     * @param ex A exceção lançada quando uma restrição de bean validation é violada.
     * @return Uma resposta HTTP com status 400 e detalhes do erro.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        String message = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(build(HttpStatus.BAD_REQUEST.value(), "Dados inválidos: " + message, request));
    }

    /**
     * Handler para exceção de requisição mal formada.
     *
     * @param ex A exceção lançada quando a requisição é mal formada.
     * @return Uma resposta HTTP com status 400 e detalhes do erro.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(build(HttpStatus.BAD_REQUEST.value(), "Requisição mal formada", request));
    }

    /**
     * Handler para exceção de parâmetro de requisição ausente. Ex: @RequestParam obrigatório não fornecido.
     *
     * @param ex A exceção lançada quando um parâmetro de requisição obrigatório está ausente.
     * @return Uma resposta HTTP com status 400 e detalhes do erro.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String message = "Parâmetro de requisição ausente: " + ex.getParameterName();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(build(HttpStatus.BAD_REQUEST.value(), message, request));
    }

    /**
     * Handler para exceção de violação de integridade de dados. Quando tenta salvar um campo nulo em uma coluna NOT NULL, por exemplo.
     *
     * @param ex A exceção lançada quando há uma violação de integridade de dados.
     * @return Uma resposta HTTP com status 400 e detalhes do erro.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                         HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(build(HttpStatus.BAD_REQUEST.value(),
                        "Dados inválidos: violação de integridade ao salvar",
                        request));
    }

    /**
     * Handler para exceção de negócio personalizada.
     *
     * @param ex A exceção de negócio lançada.
     * @return Uma resposta HTTP com o status e mensagem definidos na exceção.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        HttpStatus status = ex.getStatus() != null ? ex.getStatus() : HttpStatus.BAD_REQUEST;
        String clientMessage = ex.getClientMessage();
        return ResponseEntity.status(status)
                .body(build(status.value(), clientMessage, request));
    }
}