package com.example.api.services.impl;

import com.example.api.domain.User;
import com.example.api.domain.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.api.repositories.UserRepository;

import java.util.Optional;

@SpringBootTest
class UserServiceImplTest {

    // criando um User para teste
    public static final Integer ID      = 1;
    public static final String NAME     = "werner";
    public static final String EMAIL    = "werner@gmail.com";
    public static final String PASSWORD = "admin";

    // adicionando as dependências da classe UserService que vai ser testada

    // como a classe que vai ser testada é a UserServiceImpl, a anotation @InjectMocks cria uma instância real para testes
    @InjectMocks
    private UserServiceImpl service;

    // como não vamos precisar utilizar o banco de dados de fato, vamos mokar, criar uma instância de mentira com @Mock para testar
    // a classe de UserServiceImpl.
    @Mock
    private UserRepository repository;

    // classe mokacada para testes
    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    // método que inicia os mocks antes do teste
    @BeforeEach
    void setUp() {
        // nesse método é passado todos os mocks que precisam ser iniciados antes de o método ser testado
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {

        // mockando a resposta do repository.findById

        // quando o repository.findbyId for chamado, passamos um Id do tipo inteiro, então retornamos uma instância do OptionalUser
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);

        // depois das dependências mockadas, fazemos o teste no método selecionado
        User response = service.findById(ID);

        // teste que a response não seja nula
        Assertions.assertNotNull(response);

        // teste para verificar se o ID passado na requisição sejá igual ao ID retornado na response
        Assertions.assertEquals(ID, response.getId());

        // teste para verificar se o NAME passado na requisição sejá igual ao NAME retornado na response
        Assertions.assertEquals(NAME, response.getName());

        // teste para verificar se o EMAIL passado na equisição sejá igual ao EMAIL retornado na response
        Assertions.assertEquals(EMAIL, response.getEmail());

        // teste: verificando se a classe restornada pelo teste é do tipo User
        // param 1 da validação: resposta esperada
        // param 2 da validação: reposta retornada pelo teste
        Assertions.assertEquals(User.class, response.getClass());
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void findByEmail() {
    }

    // criando um método para startar os mocks, que serão repassados na classe setUp
    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}