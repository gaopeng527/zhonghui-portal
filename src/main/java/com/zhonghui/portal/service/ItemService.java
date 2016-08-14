package com.zhonghui.portal.service;

import com.zhonghui.portal.pojo.ItemInfo;

public interface ItemService {
	
	ItemInfo getItemById(Long itemId);
	String getItemDescById(Long itemId);
	String getItemParam(Long itemId);
	
}
