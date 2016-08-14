package com.zhonghui.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
/**
 * 商品搜索Controller
 * @author DELL
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhonghui.portal.pojo.SearchResult;
import com.zhonghui.portal.service.SearchService;
@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q") String queryString, @RequestParam(defaultValue="1") Integer page, Model model){
		// 解决乱码
		try {
			queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SearchResult searchResult = searchService.search(queryString, page);
		// 向页面传递参数
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", page);
		return "search";
	}
}
