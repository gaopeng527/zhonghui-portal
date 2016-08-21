package com.zhonghui.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhonghui.common.pojo.ZhonghuiResult;
import com.zhonghui.portal.service.CartService;
/**
 * 购物车Controller
 * @author DELL
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId, @RequestParam(defaultValue="1") Integer num, 
			HttpServletRequest request, HttpServletResponse response){
		ZhonghuiResult result = cartService.addCartItem(itemId, num, request, response);
		return "cartSuccess";
	}
}
