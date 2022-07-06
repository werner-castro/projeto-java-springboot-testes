package com.example.api.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    private String passwod;

}
