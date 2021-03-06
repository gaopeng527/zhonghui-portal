package com.zhonghui.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.huizhong.pojo.TbItem;
import com.zhonghui.common.pojo.ZhonghuiResult;
import com.zhonghui.common.utils.CookieUtils;
import com.zhonghui.common.utils.HttpClientUtil;
import com.zhonghui.common.utils.JsonUtils;
import com.zhonghui.portal.pojo.CartItem;
import com.zhonghui.portal.service.CartService;
/**
 * 购物车Service
 * @author DELL
 *
 */
@Service
public class CartServiceImpl implements CartService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	
	/**
	 * 添加购物车商品
	 */
	@Override
	public ZhonghuiResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		// 取商品信息
		CartItem cartItem = null;
		// 取购物车商品列表
		List<CartItem> cartItems = getCartItemList(request);
		// 判断购物车商品列表中是否存在此商品
		for(CartItem cItem : cartItems){
			// 如果存在此商品
			if(cItem.getId() == itemId){
				// 增加商品数量
				cItem.setNum(cItem.getNum() + num);
				cartItem = cItem;
				break;
			}
		}
		if(cartItem == null){
			cartItem = new CartItem();
			// 根据商品id查询商品基本信息
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			// 把json转换成java对象
			ZhonghuiResult result = ZhonghuiResult.formatToPojo(json, TbItem.class);
			if(result.getStatus() == 200){
				TbItem item = (TbItem) result.getData();
				cartItem.setId(item.getId());
				cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
				cartItem.setPrice(item.getPrice());
				cartItem.setTitle(item.getTitle());
				cartItem.setNum(num);
			}
			// 添加到购物车列表
			cartItems.add(cartItem);
		}
		// 把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "ZH_CART", JsonUtils.objectToJson(cartItems), true);
		return ZhonghuiResult.ok();
	}
	
	/**
	 * 从cookie中取商品列表
	 * @return
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request){
		// 从cookie中取商品列表
		String cartJson = CookieUtils.getCookieValue(request, "ZH_CART", true);
		if(cartJson == null){
			return new ArrayList<>();
		}
		try {
			// 把json转换成商品列表
			List<CartItem> result = JsonUtils.jsonToList(cartJson, CartItem.class);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	/**
	 * 获取购物车商品信息
	 */
	@Override
	public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> itemList = getCartItemList(request);
		return itemList;
	}

	/**
	 * 删除购物车商品
	 */
	@Override
	public ZhonghuiResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 从cookie中取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		// 从列表中找到此商品
		for(CartItem cartItem : itemList){
			if(cartItem.getId() == itemId){
				itemList.remove(cartItem);
				break;
			}
		}
		// 把购物车列表重新写入cookie
		CookieUtils.setCookie(request, response, "ZH_CART", JsonUtils.objectToJson(itemList), true);
		return ZhonghuiResult.ok();
	}

}
