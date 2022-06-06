package com.example.demo.src.usr.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRes {
    private final int userIdx;
    private final String id;
    private final String name;
    private final String email;
}
