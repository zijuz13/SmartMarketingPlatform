package com.huajun123.biz;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huajun123.entity.Contact;
import com.huajun123.entity.Group;
import com.huajun123.mappers.ContactMapper;
import com.huajun123.utils.GenericCriteriaUtils;
import com.huajun123.utils.GroupRequest;
import com.huajun123.utils.SearchResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.PostConstruct;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupBiz extends BaseBiz<Group> implements IGroupBiz{
    @Autowired
    private BeanFactory factory;
    @Autowired
    private ContactMapper mapper1;
    @Autowired
    private GenericCriteriaUtils utils;
    @PostConstruct
    public void construct(){
        this.setMapper((Mapper<Group>)factory.getBean("groupMapper"));
    }

    @Override
    public int createItem(Group group) {
        group.setImporttime(""+System.currentTimeMillis());
        group.setUpdatetime(null);
        group.setUpdator(null);
        group.setContacts(this.analyzeCorrespondingContacts(group.getCon()));
        return super.createItem(group);
    }
    //用来封装解析条件
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
    }
    private static final Logger LOGGER= LoggerFactory.getLogger(GroupBiz.class);
    private String analyzeCorrespondingContacts(String con){
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
        List<Long> collect = mapper1.selectAll().stream().filter(contact -> {
            boolean flag = true;
            for (int i = 0; i < list.size(); i++) {
                boolean b = false;
                try {
                    b = list.get(i).ifMeeting(contact);
                } catch (Exception e){
                    LOGGER.error("decider{}",list.get(i));
                }
                flag = b && flag;
            }
            return flag;
        }).map(Contact::getGuid).collect(Collectors.toList());
        return StringUtils.join(collect,",");
    }
    @Override
    public int updateItem(Group group) {
        group.setImporttime(null);
        group.setCreator(null);
        group.setUpdatetime(""+System.currentTimeMillis());
        return super.updateItem(group);
    }

    @Override
    public SearchResult<Group> getGroupsByCriteria(GroupRequest request) {
        PageHelper.startPage(request.getPage(),request.getLimit());
        Example example=new Example(Group.class);
        Example.Criteria criteria = example.createCriteria();
//        if(null!=request.getName()&&!StringUtils.isEmpty(request.getName())){
//            criteria.andCondition("  name like  "+"'"+'%'+request.getName()+'%'+"'");
//        }
        try {
            utils.addGenericCriteria(criteria,Group.class,request);
        } catch (IllegalAccessException e) {
            LOGGER.error("Criteria setting failed {}",e.getMessage());
        }
        if(request.getSort().equalsIgnoreCase("+id")){
            example.setOrderByClause("guid asc");
        }else{
            example.setOrderByClause("guid desc");
        }
        List<Group> groups = this.getMapper().selectByExample(example);
        PageInfo<Group> info = new PageInfo<>(groups);
        return new SearchResult<>(groups,Integer.parseInt(""+info.getTotal()),Integer.parseInt(info.getPages()+""));
    }
}
