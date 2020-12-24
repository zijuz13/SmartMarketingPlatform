package com.huajun123.utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult<T> {
    private List<T> items;
    private int total;
    private int totalPages;
}
