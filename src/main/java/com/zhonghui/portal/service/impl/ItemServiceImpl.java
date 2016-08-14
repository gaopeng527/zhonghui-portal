package com.zhonghui.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.huizhong.pojo.TbItemDesc;
import com.huizhong.pojo.TbItemParamItem;
import com.zhonghui.common.pojo.ZhonghuiResult;
import com.zhonghui.common.utils.HttpClientUtil;
import com.zhonghui.common.utils.JsonUtils;
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
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;
	
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
					TbItemDesc itemDesc = (TbItemDesc) zhonghuiResult.getData();
					return itemDesc.getItemDesc();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

	/**
	 * 取商品规格参数信息
	 */
	@Override
	public String getItemParam(Long itemId) {
		try {
			// 调用rest的服务查询商品规格参数信息
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
			if(!StringUtils.isBlank(json)){
				ZhonghuiResult zhonghuiResult = ZhonghuiResult.formatToPojo(json, TbItemParamItem.class);
				if(zhonghuiResult.getStatus() == 200){
					TbItemParamItem itemParamItem = (TbItemParamItem) zhonghuiResult.getData();
					String paramData = itemParamItem.getParamData();
					// 把规格参数json数据转换成java对象
					List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
					// 生成html
					StringBuffer sb = new StringBuffer();
					sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\"\n");
					sb.append("	class=\"Ptable\">\n");
					sb.append("	<tbody>\n");
					for(Map m1 : jsonList) {
						sb.append("		<tr>\n");
						sb.append("			<th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
						sb.append("		</tr>\n");
						List<Map> list2 = (List<Map>) m1.get("params");
						for(Map m2 : list2) {
							sb.append("		<tr>\n");
							sb.append("			<td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
							sb.append("			<td>"+m2.get("v")+"</td>\n");
							sb.append("		</tr>\n");
						}	
					}
					sb.append("	</tbody>\n");
					sb.append("</table>");
					return sb.toString();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

}
