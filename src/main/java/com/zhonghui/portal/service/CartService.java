package com.zhonghui.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhonghui.common.pojo.ZhonghuiResult;
/**
 * 购物车服务
 * @author DELL
 *
 */
import com.zhonghui.portal.pojo.CartItem;
public interface CartService {
	
	ZhonghuiResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response);
	List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);
	ZhonghuiResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response);
	
}
