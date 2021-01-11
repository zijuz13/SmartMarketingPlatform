package com.huajun123.controller;
import com.huajun123.biz.IEmailBiz;
import com.huajun123.entity.Email;
import com.huajun123.utils.EmailRequest;
import com.huajun123.utils.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("email")
public class EmailController {
    @Autowired
    private IEmailBiz biz;
    @GetMapping
    public ResponseEntity<SearchResult<Email>> getEmailsByCriteria(EmailRequest request){
      return ResponseEntity.status(HttpStatus.OK).body(biz.getEmailsByCriteria(request));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEmail(@PathVariable("id")Long id){
        biz.deleteItem(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping
    public ResponseEntity<Void> updateEmail(@RequestBody Email email){
        biz.updateItem(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping
    public ResponseEntity<Void> createEmail(@RequestBody Email email){
        biz.createItem(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("{id}")
    public ResponseEntity<Email> getEmailById(@PathVariable("id")Long id){
        return ResponseEntity.status(HttpStatus.OK).body(biz.getItem(id));
    }
}
