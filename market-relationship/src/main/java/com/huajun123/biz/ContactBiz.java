package com.huajun123.biz;
import com.huajun123.entity.Contact;
import com.huajun123.utils.LoadUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ContactBiz extends BaseBiz<Contact> implements IContactBiz {
    @Autowired
    private BeanFactory factory;

    @PostConstruct
    public void construct(){
        this.setMapper((Mapper<Contact>) factory.getBean("contactMapper"));
    }

    @Override
    public List<Contact> getContactsByCriteria(Contact contact) {
        Mapper<Contact> mapper = this.getMapper();
        Example example=new Example(Contact.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(contact.getName())){
            criteria.andCondition("  name like  "+"'"+'%'+contact.getName()+'%'+"'");
        }
        return mapper.selectByExample(example);
    }
    @Autowired
    private LoadUtils utils;
    private static final Logger LOGGER= LoggerFactory.getLogger(ContactBiz.class);
    @Override
    public Map<String,Object> listCreateContacts(MultipartFile file) {
        List<Contact> contacts = utils.loadContacts(file);
        Map<String,Object> resultSet=new HashMap<>();
        contacts.forEach(contact -> {
            boolean isEdit=false;
            //Verify whether the contact information has already been created
            if(null!=this.getItem(contact.getGuid())){
                isEdit=true;
                contact.setCreator(null);
                contact.setImporttime(null);
                contact.setUpdatetime(System.currentTimeMillis()+"");
            }else{
                contact.setUpdator(null);
                contact.setImporttime(""+System.currentTimeMillis());
                contact.setUpdatetime(null);
            }
            DateFormat format=new SimpleDateFormat("yyyy/MM/dd");
            Date parse = null;
            boolean continue1=true;
            try {
                parse = format.parse(contact.getBirthday());
            } catch (ParseException e) {
                continue1=false;
                LOGGER.error("The format of the birthday of contact<{}> in csv file is wrong:{}",contact,contact.getBirthday());
                resultSet.put("status","failed");
                resultSet.put("message","Date Format Error");
            }
            if(continue1) {
                contact.setBirthday("" + parse.getTime());
                if(isEdit)
                this.updateItem(contact);
                else this.createItem(contact);
            }
        });if(null!=resultSet.get("status")){
            return resultSet;
        }
            resultSet.put("status","success");
            return resultSet;
    }
}
