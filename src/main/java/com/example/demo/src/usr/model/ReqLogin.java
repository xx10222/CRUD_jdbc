package com.example.demo.src.usr.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReqLogin {
    private final String loginId;
    private final String loginPw;
}
