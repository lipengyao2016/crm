//package com.crm.core;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.TypeReference;
//import com.by.crm.dto.BaseDto;
//import com.by.crm.feign.administration.AdministrationFeignCommon;
//import com.by.crm.vo.FactoryVo;
//
//@Component
//public class HttpRequestHelper {
//	private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestHelper.class);
//	@Autowired
//	private AdministrationFeignCommon administrationFeign;
//
//	public FactoryVo findFactoryVo(String factoryId) {
//		FactoryVo factory = administrationFeign.findFactoryDetail(factoryId);
//		if (factory == null) {
//			throw new MessageException("未查询到工厂信息！");
//		}
//		if (factory.getServiceUrl() == null || "".equals(factory.getServiceUrl())) {
//			throw new MessageException("未维护该工厂对接系统的url");
//		}
//
//		return factory;
//	}
//
//	public String postFactoryService(String serviceUrl, String majorIdKey, BaseDto dto) {
//		JSONObject paramJson = JSONObject.parseObject(JSONObject.toJSONString(dto));
//		FactoryVo factory = this.findFactoryVo(paramJson.getString("factoryId"));
//		paramJson.put("factoryId", factory.getRelevanceFactoryId());
//		paramJson = HttpClientUtil.getLoginUserInfo(paramJson);
//
//		String resultString = HttpClientUtil.doPost(factory.getServiceUrl() + serviceUrl, paramJson.toJSONString());
//		JSONObject resultJson = JSONObject.parseObject(resultString);
//		RestfulResponse<Object> restful = JSONObject.toJavaObject(resultJson, RestfulResponse.class);
//		if (restful != null) {
//			int code = resultJson.getInteger("code");
//			if (code != ECode.SUCCESS.getCode()) {
//				throw new MessageException(restful.getMessage());
//			}
//
//			JSONObject dataJson = resultJson.getJSONObject("data");
//			if (dataJson != null) {
//				return dataJson.getString(majorIdKey);
//			}
//		}
//
//		return "0";
//	}
//
//	public NewPageInfo<? extends BaseDto> getFactoryServicePage(String serviceUrl, SearchCondition cond) {
//		NewPageInfo<BaseDto> result = new NewPageInfo<BaseDto>();
//		JSONObject resultJson = getFactoryServiceResult(serviceUrl, cond);
//		RestfulResponse<Object> restful = JSONObject.toJavaObject(resultJson, RestfulResponse.class);
//		if (restful != null) {
//			int code = resultJson.getInteger("code");
//			if (code == ECode.NO_DATA_RESULT.getCode()) {
//				return result;
//			}
//			if (code != ECode.SUCCESS.getCode()) {
//				throw new MessageException(restful.getMessage());
//			}
//
//			JSONObject dataJson = resultJson.getJSONObject("data");
//
//			// 列表查询结果
//			NewPageInfo<? extends BaseDto> pageInfo = JSONObject.toJavaObject(dataJson, NewPageInfo.class);
//
//			return pageInfo;
//		}
//
//		return result;
//	}
//
//	public List<Object> getFactoryServiceList(String serviceUrl, SearchCondition cond) {
//		List<Object> result = new ArrayList();
//		JSONObject resultJson = getFactoryServiceResult(serviceUrl, cond);
//		RestfulResponse<Object> restful = JSONObject.toJavaObject(resultJson, RestfulResponse.class);
//		if (restful != null) {
//			int code = resultJson.getInteger("code");
//			if (code == ECode.NO_DATA_RESULT.getCode()) {
//				return result;
//			}
//			if (code != ECode.SUCCESS.getCode()) {
//				throw new MessageException(restful.getMessage());
//			}
//
//			JSONArray dataJson = resultJson.getJSONArray("data");
//
//			// 列表查询结果
//			result = JSONArray.parseArray(dataJson.toJSONString(), Object.class);
//		}
//
//		return result;
//	}
//
//	public JSONObject getFactoryServiceDetail(String serviceUrl, SearchCondition cond) {
//		JSONObject resultJson = getFactoryServiceResult(serviceUrl, cond);
//		if (resultJson != null) {
//			JSONObject dataJson = resultJson.getJSONObject("data");
//			return dataJson;
//		}
//		return null;
//	}
//
//	public String getFactoryServiceString(String serviceUrl, SearchCondition cond) {
//		JSONObject resultJson = getFactoryServiceResult(serviceUrl, cond);
//		if (resultJson != null) {
//			String result = resultJson.getString("data");
//			return result;
//		}
//		return null;
//	}
//
//	private JSONObject getFactoryServiceResult(String serviceUrl, SearchCondition cond) {
//		// 先获取工厂端url
//		JSONObject condJson = JSONObject.parseObject(JSONObject.toJSONString(cond));
//		FactoryVo factory = this.findFactoryVo(condJson.getString("factoryId"));
//		condJson.put("factoryId", factory.getRelevanceFactoryId());
//		condJson.put("status", "Y");
//		condJson = HttpClientUtil.getLoginUserInfo(condJson);
//
//		Map<String, Object> params = JSONObject.parseObject(condJson.toJSONString(),
//				new TypeReference<Map<String, Object>>() {
//				});
//		String resultString = null;
//		try {
//			resultString = HttpClientUtil.doGet(factory.getServiceUrl() + serviceUrl, params, false);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		JSONObject resultJson = JSONObject.parseObject(resultString);
//		RestfulResponse<Object> restful = JSONObject.toJavaObject(resultJson, RestfulResponse.class);
//		if (restful != null) {
//			int code = resultJson.getInteger("code");
//			if (code == ECode.NO_DATA_RESULT.getCode()) {
//				return null;
//			}
//			if (code != ECode.SUCCESS.getCode()) {
//				throw new MessageException(restful.getMessage());
//			}
//			return resultJson;
//		}
//
//		return null;
//	}
//
//}
