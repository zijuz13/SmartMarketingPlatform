package com.huajun123.utils;
import com.huajun123.entity.Contact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest extends Contact {
    private int page;
    private int limit;
    private String sort;
    private Long groupId;
}
