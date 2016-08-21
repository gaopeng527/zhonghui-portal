package com.zhonghui.portal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhonghui.common.pojo.ZhonghuiResult;
/**
 * 购物车服务
 * @author DELL
 *
 */
public interface CartService {
	
	ZhonghuiResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response);
}
