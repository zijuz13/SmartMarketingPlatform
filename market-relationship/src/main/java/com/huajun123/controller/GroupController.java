package com.huajun123.controller;
import com.huajun123.biz.IGroupBiz;
import com.huajun123.entity.Group;
import com.huajun123.utils.GroupRequest;
import com.huajun123.utils.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("group")
public class GroupController {
    @Autowired
    private IGroupBiz biz;
    private static final Logger LOGGER= LoggerFactory.getLogger(GroupController.class);
    @GetMapping
    public ResponseEntity<SearchResult<Group>> searchResults(GroupRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(biz.getGroupsByCriteria(request));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id")Long id){
        biz.deleteItem(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping
    public ResponseEntity<Void> updateGroup(@RequestBody Group group){
        biz.updateItem(group);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping
    public ResponseEntity<Void> createGroup(@RequestBody Group group){
        biz.createItem(group);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
