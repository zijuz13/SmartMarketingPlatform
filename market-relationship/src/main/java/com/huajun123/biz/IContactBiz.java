package com.huajun123.biz;

import com.huajun123.entity.Contact;
import com.huajun123.utils.SearchRequest;
import com.huajun123.utils.SearchResult;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

public interface IContactBiz extends IBaseBiz<Contact> {
    SearchResult<Contact> getContactsByCriteria(SearchRequest contact);
    Map<String,Object> listCreateContacts(MultipartFile file);
}
