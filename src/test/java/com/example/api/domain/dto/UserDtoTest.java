package com.example.api.domain.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.*;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
class UserDtoTest {

    public static final String CAMPO_NOME_OBRIGATORIO = "O campo nome é obrigatório";
    public static final String EMAIL_OBRIGATORIO = "O campo email é obrigatório";
    public static final String PASSWORD_OBRIGATORIO = "O campo password é obrigatório";
    private Validator validator;

    @BeforeEach void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenUserDtoNotEmpty() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("werner@gmail.com");
        userDTO.setName("werner");
        userDTO.setPassword("admin");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void whenUserDtoNameIsNull() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("werner@gmail.com");
        userDTO.setName("");
        userDTO.setPassword("admin");
        Optional<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO).stream().findFirst();
        Assertions.assertEquals(CAMPO_NOME_OBRIGATORIO, violations.get().getMessage());
    }

    @Test
    public void whenUserDtoEmailIsNull() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("");
        userDTO.setName("werner");
        userDTO.setPassword("admin");
        Optional<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO).stream().findFirst();
        Assertions.assertEquals(EMAIL_OBRIGATORIO, violations.get().getMessage());
    }

    @Test
    public void whenUserDtoPasswordIsNull() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("werner@gmail.com");
        userDTO.setName("werner");
        userDTO.setPassword("");
        Optional<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO).stream().findFirst();
        Assertions.assertEquals(PASSWORD_OBRIGATORIO, violations.get().getMessage());
    }

}