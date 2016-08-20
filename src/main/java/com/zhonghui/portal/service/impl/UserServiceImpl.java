package com.zhonghui.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.huizhong.pojo.TbUser;
import com.zhonghui.common.pojo.ZhonghuiResult;
import com.zhonghui.common.utils.HttpClientUtil;
import com.zhonghui.portal.service.UserService;
/**
 * 用户管理Service
 * @author DELL
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN}")
	private String SSO_USER_TOKEN;
	@Value("${SSO_PAGE_LOGIN}")
	public String SSO_PAGE_LOGIN;
	
	@Override
	public TbUser getUserByToken(String token) {
		try {
			// 调用sso系统的服务，根据token取用户信息
			String json = HttpClientUtil.doGet(SSO_BASE_URL+SSO_USER_TOKEN+token);
			// 把json转换成ZhonghuiResult对象
			ZhonghuiResult zhonghuiResult = ZhonghuiResult.formatToPojo(json, ZhonghuiResult.class);
			if(zhonghuiResult.getStatus() == 20){
				TbUser user = (TbUser) zhonghuiResult.getData();
				return user;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
