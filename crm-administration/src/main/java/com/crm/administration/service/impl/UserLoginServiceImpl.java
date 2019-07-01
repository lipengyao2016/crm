package com.crm.administration.service.impl;

import java.util.List;
import java.util.Random;

import com.crm.administration.mapper.RoleMapper;
import com.crm.administration.mapper.UserMapper;
import com.crm.administration.model.login.AccountVo;
import com.crm.administration.model.login.UserLoginLog;
import com.crm.administration.service.IUserLoginService;
import com.crm.common.MessageException;
import com.crm.vo.RoleVo;
import com.crm.vo.UserLoginInfoVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLoginServiceImpl implements IUserLoginService {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

//	private AssistantFeignCommon assistantFeign;

//	@Value("${aliyun.wyPrefix}")
//	private String prefix;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class })
	public void updateUserLoginInfo(UserLoginLog log) {
		userMapper.updateUserLoginInfo(log);
	}
	@Override
	public String canUserLogin(String userLoginId, String pwd) {
		UserLoginInfoVo userInfo = userMapper.findUserInfoById(userLoginId);
		if (userInfo == null) {
			return "用户" + userLoginId + "不存在！";
		}

		if (!"Y".equals(userInfo.getStatus())) {
			return "用户" + userLoginId + "已被停用！";
		}

		String password = userInfo.getCurrentPassword();
		if (pwd.equals(password)) {
			return null;
		}
		return "用户名或密码错误";
	}

	@Override
	public UserLoginInfoVo findUserLoginInfo(String userLoginId) throws Exception {
		UserLoginInfoVo vo = userMapper.findUserInfoById(userLoginId);
		if (vo == null) {
			throw new MessageException("用户不存在或已禁用！");
		}

		String url = "";
//		String picUrl = vo.getPicUrl();
//		if (picUrl != null && !"".equals(picUrl)) {
//			List<AttachmentVo> list = (List<AttachmentVo>) assistantFeign.listData(picUrl);
//			if (list != null && list.size() > 0) {
//				AttachmentVo attachment = list.get(0);
//				url = attachment.getFileUrl();
//			}
//		}

		// 如果用户是超级管理员,则取其中一个该用户代理的经销商
		if (vo.getAgencyId() == null || 0L==vo.getAgencyId()) {

				vo.setIfSuperAdmin("Y");
		}

//		vo.setPicUrl(url);

		// 返回用户角色列表

		List<RoleVo> roleList = roleMapper.getRolesByUserId(vo.getId());
		vo.setRoleList(roleList);

		//查询商户是否被禁用
//		if (!"Y".equals(vo.getIfSuperAdmin())) {
//			int count = userMapper.ifAgencySealed(vo.getUserLoginId());
//			if (count == 0) {
//				throw new MessageException("你所在的商户已被禁用!", ECode.NOT_LOGGED_IN);
//			}
//		}
		return vo;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class })
	@Override
	public void updateUserPwd(AccountVo vo) {
		// 修改密码
		String newPwd = DigestUtils.sha256Hex(vo.getNewPwd());

//		WyChatUtil.changePassword(vo.getUserLoginId(), newPwd, prefix);
		vo.setNewPwd(newPwd);
		userMapper.updateUserPwd(vo);
	}

	@Cacheable(value = "login", key = "#sessionId")
	public Integer recordLoginCount(String sessionId) {
		return 0;
	}

	@CachePut(value = "login", key = "#sessionId")
	public Integer updateLoginCount(String sessionId, Integer count) {
		count += 1;
		return count;
	}

	@CacheEvict(value = "login", key = "#sessionId")
	public void deleteLoginCount(String sessionId) {
	}

	@Cacheable(value = "login", key = "'systime' + #sessionId")
	public Long recordLoginTime(String sessionId) {
		return System.currentTimeMillis();
	}

	@CachePut(value = "login", key = "'systime' + #sessionId")
	public Long updateLoginTime(String sessionId, Long sysTime) {
		return sysTime;
	}

	@CacheEvict(value = "login", key = "'systime' + #sessionId")
	public void deleteLoginTime(String sessionId) {
	}

	@Override
	public String getVerifyCode(String userLoginId) {
		// 查询用户信息
		UserLoginInfoVo findUserInfoById = userMapper.findUserInfoById(userLoginId);
		if (findUserInfoById == null) {
			throw new MessageException("用户名不存在或已禁用！");
		}

		String phone = findUserInfoById.getPhone();
		if (phone == null || "".equals(phone)) {
			throw new MessageException("用户名未绑定手机号，无法发送手机验证码！");
		}

		// 随机生成四位长度的数字
		Random rand = new Random(System.currentTimeMillis());
		StringBuilder verifyCode = new StringBuilder(4);
		for (int i = 0; i < 4; i++) {
			verifyCode.append(rand.nextInt(9));
		}

		try {
//			MessageUtil.sendMessageForValCode(phone, verifyCode.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessageException("发送手机验证码出错！");
		}

		return verifyCode.toString();
	}
}
