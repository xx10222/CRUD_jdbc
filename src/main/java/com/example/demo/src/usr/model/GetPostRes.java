package com.example.demo.src.usr.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetPostRes {
    private final int postIdx;
    private final String createAt;
    private final String updateAt;
    private final String writer;
    private final String title;
}
