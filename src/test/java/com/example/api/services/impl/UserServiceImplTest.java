package com.example.api.services.impl;

import com.example.api.domain.User;
import com.example.api.domain.dto.UserDTO;
import com.example.api.services.exceptions.DataIntegralityViolationException;
import com.example.api.services.exceptions.ObjectNotFoundException;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserServiceImplTest {

    // criando um User para teste
    public static final Integer ID = 1;
    public static final String NAME = "werner";
    public static final String EMAIL = "werner@gmail.com";
    public static final String PASSWORD = "admin";

    public static final String EMAIL_JA_CADATRADO_NO_SISTEMA = "E-mail já cadastrado no sistema";
    public static final int INDEX = 0;
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";

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

    // criando um método para startar os mocks, que serão repassados na classe setUp
    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }

    // método que inicia os mocks antes do teste
    @BeforeEach
    void setUp() {
        // nesse método é passado todos os mocks que precisam ser iniciados antes de o método ser testado
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenCreateThenReturnSuccess() {

        // criando o mock do método a ser testado: repository.save()
        Mockito.when(repository.save(Mockito.any())).thenReturn(user);

        // fazendo a requsição no método a ser testado
        User response = service.create(userDTO);

        // teste: verificação de não nulidade
        Assertions.assertNotNull(response);

        // teste: verifica a classe restornada é do tipo User
        Assertions.assertEquals(User.class, response.getClass());

        // teste: verifica se ID enviado na requisição é igual ao ID do response
        Assertions.assertEquals(ID, response.getId());

        // teste: verifica se NAME enviado na requisição é igual ao NAME do response
        Assertions.assertEquals(NAME, response.getName());

        // teste: verifica se EMAIL enviado na requisição é igual ao EMAIL do response
        Assertions.assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenCreateThenReturnDataIntegrityViolationException() {

        // cenário com ID diferente:
        // quando o utilizador enviar uma requisição para salvar um novo User com um email já existente, o método
        // findByEmail retorna uma excessão informando que este email já está cadastrado.

        // cenário com ID igual:
        // nesse caso o método findByEmail entende como uma atualização de User e faz o salvamento do User normalmente,
        // sem gerar uma excessão.

        // criando o mock do método a ser testado: repository.save()
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);

        try {
            // alterando o ID do User para forçar uma excessão
            optionalUser.get().setId(777);

            // fazendo a requsição no método a ser testado
            service.create(userDTO);

        } catch (Exception ex) {

            // teste: verifique que a excessão da response seja do tipo DataIntegralityViolationException.
            Assertions.assertEquals(DataIntegralityViolationException.class, ex.getClass());

            // teste: verifique se a mensagem da excessão seja igual à mensagem do DataIntegralityViolationException:
            // E-mail já cadrastrado no sistema.
            Assertions.assertEquals(EMAIL_JA_CADATRADO_NO_SISTEMA, ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {

        // criando o mock do método a ser testado: repository.save()
        Mockito.when(repository.save(Mockito.any())).thenReturn(user);

        // fazendo a requsição no método a ser testado
        User response = service.update(userDTO);

        // teste: verificação de não nulidade
        Assertions.assertNotNull(response);

        // teste: verifica se a classe restornada é do tipo User
        Assertions.assertEquals(User.class, response.getClass());

        // teste: verifica se ID enviado na requisição é igual ao ID do response
        Assertions.assertEquals(ID, response.getId());

        // teste: verifica se NAME enviado na requisição é igual ao NAME do response
        Assertions.assertEquals(NAME, response.getName());

        // teste: verifica se EMAIL enviado na requisição é igual ao EMAIL do response
        Assertions.assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenUpdateThenReturnDataIntegrityViolationException() {

        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(777);
            service.update(userDTO);
        } catch (Exception ex) {
            Assertions.assertEquals(DataIntegralityViolationException.class, ex.getClass());
            Assertions.assertEquals(EMAIL_JA_CADATRADO_NO_SISTEMA, ex.getMessage());
        }
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
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        // quando o repository.findbyId for chamado, passamos um Id do tipo inteiro,
        // então retornamos uma excessão informando objeto não encontrado.
        Mockito.when(repository.findById(Mockito.anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try {
            service.findById(ID);
        } catch (Exception ex) {
            Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
            Assertions.assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {

        // gerando o mock
        // quando o repository.findAll for chamado, devemos retornar uma lista de users
        Mockito.when(repository.findAll()).thenReturn(List.of(user));

        //acessando o mock
        List<User> response = service.findAll();

        // teste de verificação de valores nulos
        Assertions.assertNotNull(response);

        // teste de verificação do tamanho da lista de users:
        // nesse caso, tamanho da lista é igual á um
        Assertions.assertEquals(1, response.size());

        // teste: verificar se o primento elemento da lista de users seja do tipo User
        Assertions.assertEquals(User.class, response.get(INDEX).getClass());

        // teste: verificar se o ID passado na requisição seja igual ao ID do primeiro elemento da lista retornada
        Assertions.assertEquals(ID, response.get(INDEX).getId());

        // teste: verificar se o NAME passado na requisição seja igual ao NAME do primeiro elemento da lista retornada
        Assertions.assertEquals(NAME, response.get(INDEX).getName());

        // teste: verificar se o EMAIL passado na requisição seja igual ao EMAIL do primeiro elemento da lista retornada
        Assertions.assertEquals(EMAIL, response.get(INDEX).getEmail());

        // teste: verificar se o PASSWORD passado na requisição seja igual ao PASSWORD do primeiro elemento da lista retornada
        Assertions.assertEquals(PASSWORD, response.get(INDEX).getPassword());
    }

    @Test
    void whenDeleteSuccess() {

        // criando o mock:
        // quando o método findById for chamado, retorne um user do tipo Optional<User>
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);

        // não faça nada, quando for chamado o repository.deleteById passando um ID do tipo inteiro.
        Mockito.doNothing().when(repository).deleteById(Mockito.anyInt());

        // fazendo a requisição
        service.delete(ID);

        // teste: verifica se o número de vezes que o método foi chamado é igual à 1.
        Mockito.verify(repository, Mockito.times(1)).deleteById(Mockito.anyInt());
    }

    @Test
    void DeleteWithObjectNotFoundExpception() {

        // criando o mock:
        // quando o método findById for chamado, retorne um user uma exceção do tipo ObjectNotFoundException
        Mockito.when(repository.findById(Mockito.anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try {
            // fazendo a requisição
            service.delete(ID);
        } catch (Exception ex) {

            // teste: verifique se a classe da exceção é do tipo ObjectNotFoundException
            Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());

            // teste: verifique se a mensagem retornada da exceção é: Objeto não encotrado
            Assertions.assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }
}