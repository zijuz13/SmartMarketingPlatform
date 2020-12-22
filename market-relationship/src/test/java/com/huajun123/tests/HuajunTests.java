package com.huajun123.tests;

import com.huajun123.RelationshipApplication;
import com.huajun123.biz.IContactBiz;
import com.huajun123.entity.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RelationshipApplication.class)
public class HuajunTests {
    @Autowired
    private IContactBiz biz;
    @Test
    public void testContactBiz(){
        Contact item = biz.getItem(3L);
        System.out.println(item);
        item.setEmail("hujkc123@vip.qq.com");
        item.setGuid(9L);
        biz.createItem(item);
    }
    @Test
    public void testSelectiveUpdate(){
        Contact contact=new Contact();
        contact.setGuid(3L);
        contact.setEmail("200822041@qq.com");
        biz.updateItem(contact);
    }
    @Test
    public void deleteTest(){
        biz.deleteItem(3L);
    }
    private static final String filePath="/Library/test/test02.xlsx";
    @Test
    public void fileCSVPrintTest(){
        BufferedReader reader=null;
        try{
            reader=new BufferedReader(new FileReader(filePath));
            String line=reader.readLine();
            while(null!=line){
                String[] split = line.split("\\s+");
                System.out.println(Arrays.toString(split));
                line=reader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
