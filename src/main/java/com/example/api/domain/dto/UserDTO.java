package com.example.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String name;
    private String email;

    // inibindo a senha na saída do json para o cliente
    // evita o acesso pelo get ao parâmetro
    @JsonIgnore
    private String passwod;
}
