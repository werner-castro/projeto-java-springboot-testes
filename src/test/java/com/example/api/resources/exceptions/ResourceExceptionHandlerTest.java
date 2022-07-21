package com.example.api.resources.exceptions;

import com.example.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

class ResourceExceptionHandlerTest {

    private static final String MESSAGE_ERROR = "Objeto não encontrado";
    private static final Integer STATUS_404 = 404;

    // método que será testado
    @InjectMocks
    private ResourceExceptionHandler resourceExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void objectNotFoundExceptionThenReturnAResponseEntity(){
        ResponseEntity<StandardError> response = resourceExceptionHandler.
                objectNotFound(new ObjectNotFoundException("Objeto não encontrado"), new MockHttpServletRequest());

        // teste: verificando se a response não vem nula
        Assertions.assertNotNull(response);

        // teste: verificando se o corpo da response não vem vazia
        Assertions.assertNotNull(response.getBody());

        // teste: verificando se o status da requisição é do tipo NOT_FOUND
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // teste: verificando se a classe da response é a mesma da ResponseEntity
        Assertions.assertEquals(ResponseEntity.class, response.getClass());

        // teste: verificando se a classe que vem no corpo da response é do tipo StandardError
        Assertions.assertEquals(StandardError.class, response.getBody().getClass());

        // teste: verificando se a mensagem retornada na response é mesma definida na exception
        Assertions.assertEquals(MESSAGE_ERROR, response.getBody().getError());

        // teste: verificando se o status no corpo da response é um 404
        Assertions.assertEquals(STATUS_404, response.getBody().getStatus());
    }

    @Test
    void dataIntegratyViolationException() {
    }

    @Test
    void methodArgumentNotValidException() {
    }
}