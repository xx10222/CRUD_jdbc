package com.example.demo.src.usr.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@RequiredArgsConstructor
public class Post {
    private final int postIdx;
    private final int userIdx;
    private final String title;
    private final String content;
    private final Timestamp createAt;
    private final Timestamp updateAt;
}
