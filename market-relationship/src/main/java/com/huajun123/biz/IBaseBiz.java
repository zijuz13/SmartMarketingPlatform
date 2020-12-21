package com.huajun123.biz;

public interface IBaseBiz<T> {
    int createItem(T t);
    int deleteItem(Long guid);
    int updateItem(T t);
    T getItem(Long guid);
}
