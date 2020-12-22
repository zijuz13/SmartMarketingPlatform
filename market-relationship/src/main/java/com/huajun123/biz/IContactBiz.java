package com.huajun123.biz;

import com.huajun123.entity.Contact;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IContactBiz extends IBaseBiz<Contact> {
    List<Contact> getContactsByCriteria(Contact contact);
    Map<String,Object> listCreateContacts(MultipartFile file);
}
