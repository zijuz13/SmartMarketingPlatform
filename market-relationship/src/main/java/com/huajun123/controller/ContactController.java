package com.huajun123.controller;
import com.huajun123.biz.IContactBiz;
import com.huajun123.entity.Contact;
import com.huajun123.utils.SearchRequest;
import com.huajun123.utils.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RequestMapping("contact")
@Controller
public class ContactController {
    @Autowired
    private IContactBiz biz;
    @GetMapping
            public ResponseEntity<SearchResult> getContactsByCriteria(SearchRequest contact){
        SearchResult contactsByCriteria = biz.getContactsByCriteria(contact);
        return ResponseEntity.status(HttpStatus.OK).body(contactsByCriteria);
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
    public ResponseEntity<Void> createContact(@RequestBody Contact contact){
        biz.createItem(contact);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("list/create")
    public ResponseEntity<Map<String,Object>> listCreate(MultipartFile file){
        Map<String, Object> stringObjectMap = this.biz.listCreateContacts(file);
        return ResponseEntity.status(HttpStatus.OK).body(stringObjectMap);
    }
    @PutMapping
    public ResponseEntity<Void> updateContact(@RequestBody Contact contact){
        biz.updateItem(contact);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
