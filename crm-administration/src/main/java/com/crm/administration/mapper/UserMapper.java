package com.crm.administration.mapper;

import com.crm.administration.model.User;
import com.crm.administration.model.UserCriteria;
import com.crm.administration.model.login.AccountVo;
import com.crm.administration.model.login.UserLoginLog;
import com.crm.common.SearchCondition;
import java.util.List;

import com.crm.vo.UserLoginInfoVo;
import com.crm.vo.UserVo;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int countByExample(UserCriteria example);

    int deleteByExample(UserCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserCriteria example);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserCriteria example);

    int updateByExample(@Param("record") User record, @Param("example") UserCriteria example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<UserVo> list(SearchCondition cond);

    int batchInsert(@Param("recordList")List<User> recordList);

    UserLoginInfoVo findUserInfoById(String userLoginId);

    void updateUserLoginInfo(UserLoginLog log);

    void updateUserPwd(AccountVo vo);
}