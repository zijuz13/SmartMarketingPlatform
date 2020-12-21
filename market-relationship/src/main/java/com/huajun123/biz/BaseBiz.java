package com.huajun123.biz;

import tk.mybatis.mapper.common.Mapper;
//业务层基类减少业务层重复的crud代码
public class BaseBiz<T> implements IBaseBiz<T> {
    private Mapper<T> mapper;
    protected void setMapper(Mapper<T> mapper){
        this.mapper=mapper;
    }
    @Override
    public int createItem(T t) {
        return mapper.insertSelective(t);
    }

    @Override
    public int deleteItem(Long guid) {
        return mapper.deleteByPrimaryKey(guid);
    }

    @Override
    public int updateItem(T t) {
        return mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public T getItem(Long guid) {
        return mapper.selectByPrimaryKey(guid);
    }
}
