package com.zhonghui.portal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.huizhong.pojo.TbItemDesc;
import com.zhonghui.common.pojo.ZhonghuiResult;
import com.zhonghui.common.utils.HttpClientUtil;
import com.zhonghui.portal.pojo.ItemInfo;
import com.zhonghui.portal.service.ItemService;
/**
 * 商品信息管理Service
 * @author DELL
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	
	/**
	 * 取商品基本信息
	 */
	@Override
	public ItemInfo getItemById(Long itemId) {
		try {
			// 调用rest的服务查询商品基本信息
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			if(!StringUtils.isBlank(json)){
				ZhonghuiResult zhonghuiResult = ZhonghuiResult.formatToPojo(json, ItemInfo.class);
				if(zhonghuiResult.getStatus() == 200){
					ItemInfo item = (ItemInfo) zhonghuiResult.getData();
					return item;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取商品描述信息
	 */
	@Override
	public String getItemDescById(Long itemId) {
		try {
			// 调用rest的服务查询商品描述信息
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
			if(!StringUtils.isBlank(json)){
				ZhonghuiResult zhonghuiResult = ZhonghuiResult.formatToPojo(json, TbItemDesc.class);
				if(zhonghuiResult.getStatus() == 200){
					TbItemDesc item = (TbItemDesc) zhonghuiResult.getData();
					return item.getItemDesc();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

}
