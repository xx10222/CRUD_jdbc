package com.example.demo.src.usr.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReqPost {
    private final int postIdx;
    private final String title;
    private final String content;
    private final int boardIdx;
}
