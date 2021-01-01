package com.huajun123.biz;

import com.huajun123.entity.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmailBiz extends BaseBiz<Email> implements IEmailBiz {
}
