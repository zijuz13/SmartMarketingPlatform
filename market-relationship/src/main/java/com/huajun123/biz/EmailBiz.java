package com.huajun123.biz;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huajun123.entity.Email;
import com.huajun123.mappers.EmailMapper;
import com.huajun123.utils.EmailRequest;
import com.huajun123.utils.GenericCriteriaUtils;
import com.huajun123.utils.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Transactional
public class EmailBiz extends BaseBiz<Email> implements IEmailBiz {
    @Autowired
    private EmailMapper mapper;
    @PostConstruct
    public void init(){
        this.setMapper(mapper);
    }
    @Override
    public int createItem(Email email) {
            email.setUpdator(null);
            email.setCreatetime(""+System.currentTimeMillis());
            email.setUpdatetime(null);
        return super.createItem(email);
    }

    @Override
    public int updateItem(Email email) {
        email.setCreatetime(null);
        email.setCreator(null);
        email.setUpdatetime(""+System.currentTimeMillis());
        return super.updateItem(email);
    }

    @Autowired
    private GenericCriteriaUtils utils;
    private static final Logger LOGGER= LoggerFactory.getLogger(EmailBiz.class);
    @Override
    public SearchResult<Email> getEmailsByCriteria(EmailRequest request) {
        PageHelper.startPage(request.getPage(),request.getLimit());
        Example example=new Example(Email.class);
        Example.Criteria criteria = example.createCriteria();
        try {
            utils.addGenericCriteria(criteria, Email.class, request);
        }catch (Exception e){
            LOGGER.error("error:{}",e.getMessage());
        }
        if(request.getSort().equalsIgnoreCase("+id")){
            example.setOrderByClause("guid asc");
        }else{
            example.setOrderByClause("guid desc");
        }
        List<Email> emails = this.mapper.selectByExample(example);
        PageInfo<Email> info=new PageInfo<>(emails);
        return new SearchResult<>(emails,Integer.parseInt(""+info.getTotal()),Integer.parseInt(""+info.getPages()));
    }
}
