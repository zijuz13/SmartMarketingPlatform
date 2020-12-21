package com.huajun123.biz;

import com.huajun123.entity.Contact;

import java.util.List;

public interface IContactBiz extends IBaseBiz<Contact> {
    List<Contact> getContactsByCriteria(Contact contact);
}
