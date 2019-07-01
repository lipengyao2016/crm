package com.crm.administration.service;

import com.crm.administration.model.login.AccountVo;
import com.crm.administration.model.login.UserLoginLog;
import com.crm.vo.UserLoginInfoVo;

public interface IUserLoginService {
	/**
	 * 账号密码验证
	 * 
	 * @param userLoginId
	 * @param pwd
	 * @return
	 */
	public String canUserLogin(String userLoginId, String pwd);

	/**
	 * 查询登录用户基本信息
	 * 
	 * @param userLoginId
	 * @return
	 * @throws Exception
	 */
	public UserLoginInfoVo findUserLoginInfo(String userLoginId) throws Exception;

	/**
	 * 修改最近一次登录信息
	 * 
	 * @param log
	 */
	public void updateUserLoginInfo(UserLoginLog log);

	/**
	 * App用户注册
	 * 
	 * @param vo
	 * @return
	 */
//	public void registerUserInfo(UserRegisterVo vo);

	/**
	 * 修改用户密码
	 * 
	 * @param vo
	 */
	public void updateUserPwd(AccountVo vo);

	/**
	 * 添加登录失败次数记录缓存
	 * 
	 * @param sessionId
	 * @return
	 */
	public Integer recordLoginCount(String sessionId);

	/**
	 * 更新登录失败次数记录缓存
	 * 
	 * @param sessionId
	 * @param count
	 * @return
	 */
	public Integer updateLoginCount(String sessionId, Integer count);

	/**
	 * 删除登录失败次数记录缓存
	 * 
	 * @param sessionId
	 */
	public void deleteLoginCount(String sessionId);

	/**
	 * 添加登录时间缓存
	 * 
	 * @param sessionId
	 * @return
	 */
	public Long recordLoginTime(String sessionId);

	/**
	 * 更新登录时间缓存
	 * 
	 * @param sessionId
	 * @param sysTime
	 * @return
	 */
	public Long updateLoginTime(String sessionId, Long sysTime);

	/**
	 * 删除登录时间缓存
	 * 
	 * @param sessionId
	 */
	public void deleteLoginTime(String sessionId);

	/**
	 * 忘记密码
	 * 
	 * @param userLoginId
	 * @return
	 */
	public String getVerifyCode(String userLoginId);
}
