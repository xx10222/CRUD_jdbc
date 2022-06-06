package com.example.demo.src.usr.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReqWritePost {
    private final String title;
    private final String content;
    private final int boardIdx;
}
