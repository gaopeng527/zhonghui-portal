package com.zhonghui.portal.service;

import com.huizhong.pojo.TbUser;

public interface UserService {
	
	TbUser getUserByToken(String token);
}
