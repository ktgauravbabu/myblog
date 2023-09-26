package com.myblog5.myblog5.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MainClass {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("test"));
    }
}
