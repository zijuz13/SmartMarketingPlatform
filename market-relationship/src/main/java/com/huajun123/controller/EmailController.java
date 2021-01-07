package com.huajun123.controller;

import com.huajun123.biz.IEmailBiz;
import com.huajun123.entity.Email;
import com.huajun123.utils.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("email")
public class EmailController {
    @Autowired
    private IEmailBiz biz;
    @GetMapping
    public ResponseEntity<SearchResult<Email>> getEmailsByCriteria(){
      return null;
    }
}
