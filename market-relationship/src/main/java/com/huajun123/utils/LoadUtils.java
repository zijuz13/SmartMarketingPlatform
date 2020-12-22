package com.huajun123.utils;
import com.huajun123.entity.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
@Service
public class LoadUtils {
    private static final String filePath="D:\\test1.csv";
    private static Logger LOGGER= LoggerFactory.getLogger(LoadUtils.class);
    public List<Contact> loadContacts(MultipartFile file){
        BufferedReader reader=null;
        try{
            reader=new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));
            String line=reader.readLine();
            String[] lookup = line.split(",");
            line=reader.readLine();
            List<Contact> users=new ArrayList<>();
            while(null!=line){
                Contact user = this.convertLineToEntity(lookup, line);
                users.add(user);
                line=reader.readLine();
            }
            LOGGER.info("contacts:{}",users);
            return users;
        }catch (Exception e){
            LOGGER.error("error{}",e.getMessage());
        }
        return null;
    }
    // Convert Line Into User
    private Contact convertLineToEntity(String[] lookUp, String line) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Contact user=new Contact();
        String[] split = line.split(",");
        for(int i=0;i< split.length;i++){
            String s = lookUp[i];
            PropertyDescriptor descriptor=new PropertyDescriptor(s,Contact.class);
            Method writeMethod = descriptor.getWriteMethod();
            if(!s.equalsIgnoreCase("guid"))
                writeMethod.invoke(user,split[i]);
            else
                writeMethod.invoke(user,Long.parseLong(split[i]));
        }
        return user;
    }
}
