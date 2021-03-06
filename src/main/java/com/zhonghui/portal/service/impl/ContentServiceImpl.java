package com.zhonghui.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.huizhong.pojo.TbContent;
import com.zhonghui.common.pojo.ZhonghuiResult;
import com.zhonghui.common.utils.HttpClientUtil;
import com.zhonghui.common.utils.JsonUtils;
import com.zhonghui.portal.service.ContentService;
/**
 * 调用服务层服务查询内容列表
 * @author DELL
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;
	
	@Override
	public String getContentList() {
		// 调用服务层的服务
		String result = HttpClientUtil.doGet(REST_BASE_URL+REST_INDEX_AD_URL);
		// 把字符串装换成ZhonghuiResult对象
		try {
			ZhonghuiResult zhonghuiResult = ZhonghuiResult.formatToList(result, TbContent.class);
			// 取内容列表
			List<TbContent> list = (List<TbContent>) zhonghuiResult.getData();
			List<Map> resultList = new ArrayList<>();
			// 创建一个jsp页面要求的pojo列表
			for(TbContent tbContent : list) {
				Map map = new HashMap<>();
				map.put("src", tbContent.getPic());
				map.put("height", 240);
				map.put("width", 670);
				map.put("srcB", tbContent.getPic2());
				map.put("width", 550);
				map.put("height", 240);
				map.put("href", tbContent.getUrl());
				map.put("alt", tbContent.getSubTitle());
				resultList.add(map);
			}
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}

}
