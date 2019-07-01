package com.crm.administration.service;


import com.crm.administration.model.User;
import com.crm.administration.model.dto.UserDto;
import com.crm.administration.model.pojo.UserSearchCondition;
import com.crm.common.NewPageInfo;
import com.crm.vo.UserVo;

import java.util.List;


public interface IUserService {
    public User create(UserDto dto) throws Exception;

    public User update(UserDto dto);

    public int batchInsertCustom(List<UserDto> dtoList);

    public NewPageInfo<UserVo> list(UserSearchCondition condition);

    public UserVo getUserById(Long id);

    public void delete(long id);

}
