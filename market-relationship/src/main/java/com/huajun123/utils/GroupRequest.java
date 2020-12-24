package com.huajun123.utils;

import com.huajun123.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequest extends Group {
    private int page;
    private int limit;
    private String sort;
}
