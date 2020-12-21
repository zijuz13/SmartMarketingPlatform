package com.huajun123.controller;
import com.huajun123.biz.IContactBiz;
import com.huajun123.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("contact")
@Controller
public class ContactController {
    @Autowired
    private IContactBiz biz;
    @GetMapping
    public ResponseEntity<List<Contact>> getContactsByCriteria(Contact contact){
        return ResponseEntity.status(HttpStatus.OK).body(biz.getContactsByCriteria(contact));
    }
    @GetMapping("{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable("id")Long id){
        return ResponseEntity.status(HttpStatus.OK).body(biz.getItem(id));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteContactById(@PathVariable("id")Long id){
        biz.deleteItem(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping
    public ResponseEntity<Void> createContact(Contact contact){
        biz.createItem(contact);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping
    public ResponseEntity<Void> updateContact(Contact contact){
        biz.updateItem(contact);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
