package com.zhonghui.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhonghui.common.utils.ExceptionUtil;
import com.zhonghui.portal.pojo.CartItem;
import com.zhonghui.portal.pojo.Order;
import com.zhonghui.portal.service.CartService;
import com.zhonghui.portal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
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
	
	/**
	 * 创建订单存入数据库
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("/create")
	public String createOrder(Order order, Model model){
		try {
			String orderId = orderService.createOrder(order);
			model.addAttribute("orderId", orderId);
			model.addAttribute("payment", order.getPayment());
			model.addAttribute("data", new DateTime().plusDays(3).toString("yyyy-MM-dd")); // 预计到达日期
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("message", "创建订单出错，请稍后重试！");
			return "error/exception";
		}
	}
}
