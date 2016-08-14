package com.zhonghui.portal.pojo;

import com.huizhong.pojo.TbItem;
/**
 * 商品pojo
 * @author DELL
 *
 */
public class ItemInfo extends TbItem {
	
	public String[] getImages(){
		String image = getImage();
		if(image != null){
			String[] images = image.split(",");
			return images;
		}
		return null;
	}
}
