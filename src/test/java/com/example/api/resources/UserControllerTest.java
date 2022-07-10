package com.example.api.resources;

import com.example.api.domain.User;
import com.example.api.domain.dto.UserDTO;
import com.example.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @InjectMocks
    private UserController resource;

    @Mock
    private ModelMapper mapper;

    @Mock
    private UserServiceImpl service;

    private User user;

    private UserDTO userDTO;

    public static final Integer ID = 1;
    public static final String NAME = "werner";
    public static final String EMAIL = "werner@gmail.com";
    public static final String PASSWORD = "admin";
    public static final String EMAIL_JA_CADATRADO_NO_SISTEMA = "E-mail já cadastrado no sistema";
    public static final int INDEX = 0;
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void create() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void whenFindByIdThenReturnSuccess() {

        // fazendo o mock do service.findById
        Mockito.when(service.findById(Mockito.anyInt())).thenReturn(user);

        // fazendo o mock do mapeamento e a conversão de user para userDto
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        // teste: verifique a não nulidade da response
        Assertions.assertNotNull(response);

        // teste: verifique a não nulidade do corpo da response
        Assertions.assertNotNull(response.getBody());

        // teste: verifique se tipo da classe retornada na response seja do mesmo tipo ResponseEntity
        Assertions.assertEquals(ResponseEntity.class, response.getClass());

        // teste: verifique se a classe que virá no corpo da response seja do tipo UserDto
        Assertions.assertEquals(UserDTO.class, response.getBody().getClass());

        // teste: validação do atributo ID
        Assertions.assertEquals(ID, response.getBody().getId());

        // teste: validação do atributo NAME
        Assertions.assertEquals(NAME, response.getBody().getName());

        // teste: validação do atributo EMAIL
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());

        // teste: validação do atributo PASSWORD
        Assertions.assertEquals(PASSWORD, response.getBody().getPassword());

    }
}