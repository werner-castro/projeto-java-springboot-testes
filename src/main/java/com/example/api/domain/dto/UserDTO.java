package com.example.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    @NotEmpty(message = "O campo nome é obrigatório")
    private String name;

    @NotEmpty(message = "O campo email é obrigatório")
    private String email;

    // inibindo a senha na saída do json para o cliente
    // evita o acesso password pelo get ao parâmetro
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "O campo password é obrigatório")
    private String password;
}
