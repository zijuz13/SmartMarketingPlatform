package com.huajun123.tests;

import com.huajun123.RelationshipApplication;
import com.huajun123.biz.GroupBiz;
import com.huajun123.biz.IContactBiz;
import com.huajun123.biz.IGroupBiz;
import com.huajun123.entity.Contact;
import com.huajun123.mappers.ContactMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RelationshipApplication.class)
public class HuajunTests {
    @Autowired
    private ContactMapper mapper1;
    @Autowired
    private IContactBiz biz;
    @Autowired
    private GroupBiz biz1;
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
    static class Decider{
        private String name;
        private String operator;
        private String statement;
        public Decider(String name, String operator, String statement) {
            this.name = name;
            this.operator = operator;
            this.statement = statement;
        }
        public boolean ifMeeting(Contact contact) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
            PropertyDescriptor descriptor=new PropertyDescriptor(name,Contact.class);
            Method readMethod = descriptor.getReadMethod();
            Object invoke = readMethod.invoke(contact);
            //对象包含相应的条件
            if("包含".equalsIgnoreCase(operator)){
                return null!=invoke&&invoke.toString().contains(statement);
            }else{
                //没有包含条件说明是数字类型才可以lt gt,但这里是birthday是字符串需要转换
                if("birthday".equalsIgnoreCase(name)){
                    long var1=Long.parseLong(statement);
                    long var2=Long.parseLong(invoke.toString());
                    return "大于".equalsIgnoreCase(operator)?var2>var1:var2<var1;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            return "Decider{" +
                    "name='" + name + '\'' +
                    ", operator='" + operator + '\'' +
                    ", statement='" + statement + '\'' +
                    '}';
        }
    }
    @Test
    public void testCondition(){
        String con="birthday大于752577600000;source包含a";
        List<Decider> list=new ArrayList<>();
         for (String subCondition : con.split(";")) {
            if(subCondition.contains("包含")){
                int var1=subCondition.indexOf("包");
                int var2=subCondition.indexOf("含");
                list.add(new Decider(subCondition.substring(0,var1),"包含",subCondition.substring(var2+1)));
            }else if(subCondition.contains("大于")){
                int var1=subCondition.indexOf("大");
                int var2=subCondition.indexOf("于");
                list.add(new Decider(subCondition.substring(0,var1),"大于",subCondition.substring(var2+1)));
            }else if(subCondition.contains("小于")){
                int var1=subCondition.indexOf("小");
                int var2=subCondition.indexOf("于");
                list.add(new Decider(subCondition.substring(0,var1),"小于",subCondition.substring(var2+1)));
            }
        }
        List<Contact> contacts = mapper1.selectAll();
        contacts.forEach(contact -> {
            System.out.println(contact);
            boolean flag=true;
            for(int i=0;i<list.size();i++){
                boolean b = false;
                try {
                    b = list.get(i).ifMeeting(contact);
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                flag=b&&flag;
            }
            System.out.println(flag);
        });
    }
    @Test
    public void demo100(){
//        System.out.println(biz1.analyzeCorrespondingContacts("birthday大于752577600000;source包含a"));
    }
}
