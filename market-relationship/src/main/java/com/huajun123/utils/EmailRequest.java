package com.huajun123.utils;
import com.huajun123.entity.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest extends Email {
    private Integer page;
    private Integer limit;
    private String sort;
}
