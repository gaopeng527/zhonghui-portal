package com.zhonghui.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhonghui.portal.pojo.CartItem;
import com.zhonghui.portal.service.CartService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;
	
	/**
	 * 展示订单页面
	 * @return
	 */
	@RequestMapping("/order-cart")
	String showOrderCart(HttpServletRequest request, HttpServletResponse response, Model model){
		// 取购物车商品列表
		List<CartItem> cartList = cartService.getCartItemList(request, response);
		// 传递给页面
		model.addAttribute("cartList", cartList);
		return "order-cart";
	}
}
