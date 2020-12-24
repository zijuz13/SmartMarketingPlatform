package com.huajun123.biz;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huajun123.entity.Contact;
import com.huajun123.entity.Group;
import com.huajun123.mappers.ContactMapper;
import com.huajun123.utils.GroupRequest;
import com.huajun123.utils.SearchResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupBiz extends BaseBiz<Group> implements IGroupBiz{
    @Autowired
    private BeanFactory factory;
    @Autowired
    private ContactMapper mapper1;
    @PostConstruct
    public void construct(){
        this.setMapper((Mapper<Group>)factory.getBean("groupMapper"));
    }

    @Override
    public int createItem(Group group) {
        group.setImporttime(""+System.currentTimeMillis());
        group.setUpdatetime(null);
        group.setUpdator(null);
        return super.createItem(group);
    }
    //
    private String analyzeCorrespondingContacts(String con){
        //Get all the ocntacts
        List<Contact> contacts = mapper1.selectAll();
        //Select the contacters that are corresponding to the conditions.
        List<Long> collect = contacts.stream().filter(contact -> {
            boolean name;
            boolean
            for (String s : con.split(";")) {
                if(s.contains())
            }
        }).map(Contact::getGuid).collect(Collectors.toList());
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
        if(null!=request.getName()&&!StringUtils.isEmpty(request.getName())){
            criteria.andCondition("  name like  "+"'"+'%'+request.getName()+'%'+"'");
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
