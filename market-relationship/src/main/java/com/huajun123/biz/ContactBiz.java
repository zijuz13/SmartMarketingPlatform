package com.huajun123.biz;

import com.huajun123.entity.Contact;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Transactional
public class ContactBiz extends BaseBiz<Contact> implements IContactBiz {
    @Autowired
    private BeanFactory factory;
    @PostConstruct
    public void construct(){
        this.setMapper((Mapper<Contact>) factory.getBean("contactMapper"));
    }

    @Override
    public List<Contact> getContactsByCriteria(Contact contact) {
        Mapper<Contact> mapper = this.getMapper();
        Example example=new Example(Contact.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(contact.getName())){
            criteria.andCondition("  name like  "+"'"+'%'+contact.getName()+'%'+"'");
        }
        return mapper.selectByExample(example);
    }
}
