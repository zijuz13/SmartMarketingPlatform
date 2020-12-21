package com.huajun123.biz;

import com.huajun123.entity.Contact;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.PostConstruct;
@Service
@Transactional
public class ContactBiz extends BaseBiz<Contact> implements IContactBiz {
    @Autowired
    private BeanFactory factory;
    @PostConstruct
    public void construct(){
        this.setMapper((Mapper<Contact>) factory.getBean("contactMapper"));
    }
}
