package com.zhonghui.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zhonghui.common.pojo.ZhonghuiResult;
import com.zhonghui.common.utils.HttpClientUtil;
import com.zhonghui.portal.pojo.SearchResult;
import com.zhonghui.portal.service.SearchService;
/**
 * 商品搜索Service
 * @author DELL
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	@Override
	public SearchResult search(String queryString, int page) {
		// 调用zhonghui-search的服务
		// 查询参数
		Map<String, String> param = new HashMap<>();
		param.put("q", queryString);
		param.put("page", page+"");
		try {
			// 调用服务
			String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
			// 将字符串转换成java对象
			ZhonghuiResult zhonghuiResult = ZhonghuiResult.formatToPojo(json, SearchResult.class);
			if(zhonghuiResult.getStatus() == 200){
				SearchResult result = (SearchResult) zhonghuiResult.getData();
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}		
		return null;
	}

}
