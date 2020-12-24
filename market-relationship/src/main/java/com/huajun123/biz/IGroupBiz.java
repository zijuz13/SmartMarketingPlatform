package com.huajun123.biz;

import com.huajun123.entity.Group;
import com.huajun123.utils.GroupRequest;
import com.huajun123.utils.SearchResult;

public interface IGroupBiz extends IBaseBiz<Group> {
    SearchResult<Group> getGroupsByCriteria(GroupRequest request);
}
