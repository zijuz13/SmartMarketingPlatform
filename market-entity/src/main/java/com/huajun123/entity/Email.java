package com.huajun123.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private Long guid;
    private String category;
    private String content;
    private String name;
    private Boolean type;
    private String creator;
    private String updatetime;
    private String createtime;
    private String updator;
}
