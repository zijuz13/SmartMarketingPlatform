package com.huajun123.biz;

import com.huajun123.entity.Email;
import com.huajun123.mappers.EmailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Transactional
public class EmailBiz extends BaseBiz<Email> implements IEmailBiz {
    @Autowired
    private EmailMapper mapper;
    @PostConstruct
    public void init(){
        this.setMapper(mapper);
    }

}
