package com.zhonghui.portal.service;

import com.zhonghui.portal.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String queryString, int page);
}
