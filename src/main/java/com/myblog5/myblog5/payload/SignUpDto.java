package com.myblog5.myblog5.payload;

import lombok.Data;

@Data
public class SignUpDto {
    private String name;
    private String username;
    private String email;
    private String password;
}
