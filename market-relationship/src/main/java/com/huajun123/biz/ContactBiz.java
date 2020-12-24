package com.huajun123.biz;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huajun123.entity.Contact;
import com.huajun123.entity.Group;
import com.huajun123.mappers.GroupMapper;
import com.huajun123.utils.LoadUtils;
import com.huajun123.utils.SearchRequest;
import com.huajun123.utils.SearchResult;
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
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactBiz extends BaseBiz<Contact> implements IContactBiz {
    @Autowired
    private BeanFactory factory;
    @Autowired
    private GroupMapper mapper1;
    @PostConstruct
    public void construct(){
        this.setMapper((Mapper<Contact>) factory.getBean("contactMapper"));
    }
    //Use reflection to implement the query of multiple conditions(fields)
    @Override
    public SearchResult<Contact> getContactsByCriteria(SearchRequest contact) {
        Group group=null;
        if(null!=contact.getGroupId()&&0!=contact.getGroupId()){
            group=mapper1.selectByPrimaryKey(contact.getGroupId());
        }
        PageHelper.startPage(contact.getPage(),contact.getLimit());
        Mapper<Contact> mapper = this.getMapper();
        Example example=new Example(Contact.class);
        Example.Criteria criteria = example.createCriteria();
        try{
            Field[] declaredFields = Contact.class.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Object o = declaredField.get(contact);
                if(null!=o&&!StringUtils.isEmpty(o.toString())){
                    criteria.andCondition("  "+declaredField.getName()+" like  "+"'"+'%'+o+'%'+"'");
                }
            }
            if(null!=group) {
                String contacts = group.getContacts();
//                Arrays.asList(contacts.split(",")).stream().map(Long::parseLong).collect(Collectors.toList()).forEach(id->{
//                    criteria.andCondition(" guid in ")
//                });
                criteria.andCondition("  guid in (" + contacts + ")");
            }
            LOGGER.info("sort{}",contact.getSort());
            if(contact.getSort().equalsIgnoreCase("+id")){
                example.setOrderByClause("guid asc");
            }else{
                example.setOrderByClause("guid desc");
            }
        }catch (Exception e){
            LOGGER.error("somehing went wrong {}",e.getMessage());
        }
        List<Contact> contacts = mapper.selectByExample(example);
        PageInfo<Contact> info=new PageInfo<>(contacts);
        return new SearchResult<>(contacts,Integer.parseInt(""+info.getTotal()),Integer.parseInt(info.getPages()+""));
    }
    @Autowired
    private LoadUtils utils;
    private static final Logger LOGGER= LoggerFactory.getLogger(ContactBiz.class);
    // Import several contact records at the same time through the CSV file
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

    @Override
    public int createItem(Contact contact) {
        contact.setUpdator(null);
        contact.setImporttime(""+System.currentTimeMillis());
        contact.setUpdatetime(null);
        return super.createItem(contact);
    }

    @Override
    public int updateItem(Contact contact) {
        contact.setCreator(null);
        contact.setImporttime(null);
        contact.setUpdatetime(System.currentTimeMillis()+"");
        return super.updateItem(contact);
    }
}
