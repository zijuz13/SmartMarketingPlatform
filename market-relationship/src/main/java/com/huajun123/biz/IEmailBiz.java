package com.huajun123.biz;

import com.huajun123.entity.Email;
import com.huajun123.utils.EmailRequest;
import com.huajun123.utils.SearchResult;

public interface IEmailBiz extends IBaseBiz<Email> {
  SearchResult<Email> getEmailsByCriteria(EmailRequest request);
}
