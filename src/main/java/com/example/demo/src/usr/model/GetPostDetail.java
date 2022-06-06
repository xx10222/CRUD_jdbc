package com.example.demo.src.usr.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetPostDetail {
    private final int postIdx;
    private final String writer;
    private final String title;
    private final String content;
    private final String createAt;
    private final String updateAt;
}
