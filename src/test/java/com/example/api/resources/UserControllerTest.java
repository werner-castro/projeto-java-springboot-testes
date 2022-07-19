package com.example.api.resources;
import com.beust.ah.A;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    void whenCreateReturnCreated() {
        Mockito.when(service.create(Mockito.any())).thenReturn(user);

        ResponseEntity<UserDTO> response = resource.create(userDTO);

        // teste: verifique se o status da response é do tipo 201 CREATED
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // teste: no header da response tem que ter a chave location
        // quando o user é criado é retorno no headers da response um location com uri de acesso do novo objeto
        Assertions.assertNotNull(response.getHeaders().get("Location"));

        // teste: verifique se a classe da response é do tipo ResponseEntity
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    void whenfindAllReturnAListofUserDTO() {

        // quando o método find all for chamado, retorne uma lista de users
        Mockito.when(service.findAll()).thenReturn(List.of(user));

        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = resource.findAll();

        // teste: verifique se a response vem vazia
        Assertions.assertNotNull(service);

        // teste: verifique se a corpo da response vem nula
        Assertions.assertNotNull(response.getBody());

        // teste: verifique se o status da responde é do tipo 200 OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // teste: verifique se o tipo da response é do tipo ResponseEntity
        Assertions.assertEquals(ResponseEntity.class, response.getClass());

        // teste: verifique se na response vem um ArrayList
        Assertions.assertEquals(ArrayList.class, response.getBody().getClass());

        // teste: verifique se o ArrayList quem vem no corpo da responde seja do tipo UserDTO
        // pegando o primento elemento do corpo da response e acessando a sua classe
        Assertions.assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());

        // teste: verifique se o ID do primeiro elemento no corpo da response seja igual ao ID de teste
        Assertions.assertEquals(ID, response.getBody().get(INDEX).getId());

        // teste: verifique se o EMAIL do primeiro elemento no corpo da response seja igual ao EMAIL de teste
        Assertions.assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());

        // teste: verifique se o NAME do primeiro elemento no corpo da response seja igual ao NAME de teste
        Assertions.assertEquals(NAME, response.getBody().get(INDEX).getName());

        // teste: verifique se o PASSWORD do primeiro elemento no corpo da response seja igual ao PASSWORD de teste
        Assertions.assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
    }

    @Test
    void whenReturnThenReturnSuccess() {
        Mockito.when(service.update(userDTO)).thenReturn(user);
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.update(ID, userDTO);

        // teste: verificando se a response é nula
        Assertions.assertNotNull(response);

        // teste: verificando se o corpo da resquisição está nula
        Assertions.assertNotNull(response.getBody());

        // teste: verificando se o status da requisição é sucesso 200
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // teste: verifique se tipo da classe retornada na response seja do mesmo tipo ResponseEntity
        Assertions.assertEquals(ResponseEntity.class, response.getClass());

        // teste: verificando se a classe da response é do tipo UserDTO
        Assertions.assertEquals(UserDTO.class, response.getBody().getClass());

        // teste: validação do atributo ID
        Assertions.assertEquals(ID, response.getBody().getId());

        // teste: validação do atributo NAME
        Assertions.assertEquals(NAME, response.getBody().getName());

        // teste: validação do atributo EMAIL
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());
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