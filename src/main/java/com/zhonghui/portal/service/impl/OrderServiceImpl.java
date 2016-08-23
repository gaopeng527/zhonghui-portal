package com.zhonghui.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zhonghui.common.pojo.ZhonghuiResult;
import com.zhonghui.common.utils.HttpClientUtil;
import com.zhonghui.common.utils.JsonUtils;
import com.zhonghui.portal.pojo.Order;
import com.zhonghui.portal.service.OrderService;
/**
 * 订单处理Service
 * @author DELL
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	
	@Override
	public String createOrder(Order order) {
		// 调用taotao-order提供的服务提交订单
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		// 把json转换成ZhonghuiResult
		ZhonghuiResult zhonghuiResult = ZhonghuiResult.format(json);
		if(zhonghuiResult.getStatus() == 200){
			Object orderId = zhonghuiResult.getData();
			return orderId.toString();
		}
		return "";
	}

}
