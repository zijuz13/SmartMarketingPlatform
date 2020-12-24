package com.huajun123.biz;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huajun123.entity.Group;
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

@Service
@Transactional
public class GroupBiz extends BaseBiz<Group> implements IGroupBiz{
    @Autowired
    private BeanFactory factory;
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
