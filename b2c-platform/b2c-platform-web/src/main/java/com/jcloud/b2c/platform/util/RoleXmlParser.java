package com.jcloud.b2c.platform.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.jcloud.b2c.mall.service.client.vo.MallFunctionTypeVo;
import com.jcloud.b2c.mall.service.client.vo.MallFunctionVo;
import com.jcloud.b2c.mall.service.client.vo.MallOperatorVo;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.mall.service.client.enums.FunctionTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.YesNoEnum;
import com.jcloud.b2c.platform.service.feign.CacheFeignClient;
import com.jcloud.b2c.platform.service.role.MallOperatorService;
import com.jcloud.b2c.platform.service.role.MallRoleService;
/**
 * 权限 控制 xml 解析类 
 * 在  spring-servlet.xml 中 配置 bean 并且为单例模式
 * @author cyy
 * @date 2017-05-15
 */
public class RoleXmlParser {
	private static Logger logger = LoggerFactory.getLogger(RoleXmlParser.class);
	
	private static Map<String,String> ALL_ROLES = new HashMap<String,String>();
	
	private static Cache<String, String> CACHE = CacheBuilder.newBuilder().maximumSize(10).expireAfterWrite(60, TimeUnit.SECONDS).build();
	
	private static final String ROLE_KEY = "key";
	private static final String ROLE_VALUE = "value";
	public static final String DELIMITER = ","; // 权限 code 之间的分隔符，需要和xml中一致
	private static final String CHARSET = "UTF-8";
	
	@Resource
	private CacheFeignClient cacheFeignClient;
	@Resource
	private MallOperatorService operatorService;
	@Resource
	private MallRoleService roleService;
	
	public void init(){
		SAXReader rader = new SAXReader();
		// 获取 classpath 根目录
		String filepath = RoleXmlParser.class.getResource("/").getPath();
		
		String xmlFilePath = filepath + "role.xml";
		
		if(logger.isDebugEnabled()){
			logger.debug("role.xml 的绝对路径为：{0}",xmlFilePath);
		}
		
		Document document = null;
		try{
			document = rader.read(new File(xmlFilePath));
		}catch (Exception e) {
			logger.error("role.xml 解析出错",e);
			throw new RuntimeException(e);
		}
		
		Element root = document.getRootElement();
		
		@SuppressWarnings("unchecked")
		Iterator<Element> els  = root.elementIterator();
		while(els.hasNext()){
			Element el = els.next();
			ALL_ROLES.put(el.attributeValue(ROLE_KEY),el.attributeValue(ROLE_VALUE));
		}
		if(logger.isDebugEnabled()){
			logger.debug("role.xml 解析完成");
		}
	}
	/**
	 * 更具key获取 权限
	 * @param key
	 * @return
	 */
	public String [] getRolesByKey(String key){
		if(StringUtils.isBlank(key)){
			return null;
		}
		String value = ALL_ROLES.get(key); 
		if(StringUtils.isBlank(value)){
			return null;
		}
		String [] result = value.split(DELIMITER);
		return result;
	}
	
	
	public String getUserFunctionCacheKey(Long tenantId,String userErp){
		return "userFunctionCache:tenantId:"+tenantId+":userErp:"+userErp;
	}
	
	public List<MallFunctionTypeVo> getUserFunctions(String userErp, boolean updateCache){
		
		Long tenantId = ControllerContext.getTenantId();
		
		String cacheKey = this.getUserFunctionCacheKey(tenantId, userErp);
		
		if(updateCache){
			this.clearCache(tenantId, userErp);
		}
		
		String functionJson = this.getLocalUserFunctionCache(cacheKey,new Callable<String>() {
			@Override
			public String call() throws Exception {
				byte [] cacheByte = cacheFeignClient.getObjectFromCache(tenantId, cacheKey);
				if(cacheByte == null){
					return "";
				}
				return new String(cacheByte,CHARSET); //cacheFeignClient.get(tenantId, cacheKey);
			}
		});
		if(StringUtils.isBlank(functionJson)){
			BaseResponseVo<MallOperatorVo> opData = operatorService.queryOperatorByErp(tenantId,userErp);
			if(opData.getData() == null || opData.getData().getState() == YesNoEnum.NO.getValue()){
				functionJson = ""; // 没有权限
			}else{
				BaseResponseVo<List<MallFunctionVo>> roles = operatorService.queryFunction(tenantId,userErp,1,1);
				functionJson = this.functionListToJsonString(opData.getData(),roles.getData()); 
			}
			if(StringUtils.isNotBlank(functionJson)){
				// 存  redis  过期时间为 1天 
				CACHE.put(cacheKey,functionJson);
				try{
					byte [] cacheByte = functionJson.getBytes(CHARSET);
					cacheFeignClient.saveBytes2Cache(tenantId,cacheKey,cacheByte,60 * 60 * 24);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		MallOperatorVo result = this.jsonStringToFunctionList(functionJson);
		// List<MallFunctionTypeVo> result = this.jsonStringToFunctionList(functionJson);
		return result == null ? null : result.getFunctionList();
	}
	
	public String getLocalUserFunctionCache(String key,Callable<String> valueLoader){
		try{
			return CACHE.get(key, valueLoader);
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	
	public void clearCache(Long tenantId,String userErp){
		String key = this.getUserFunctionCacheKey(tenantId, userErp);
		CACHE.invalidate(key);
		cacheFeignClient.del(tenantId, key);
	}
	/**
	 * 角色权限，和状态发生变化时 清除缓存
	 * @author cyy
	 * @date 2017年5月18日
	 */
	public void clearCache(Long tenantId,Long roleId){
		BaseResponseVo<List<MallOperatorVo>> opData = roleService.queryRoleOperator(tenantId,roleId);
		if(opData.getData() != null && opData.getData().size() > 0){
			for(MallOperatorVo item : opData.getData()){
				this.clearCache(tenantId,item.getUserErp());
			}
		}
	}
	
	/**
	 * 对象 转 json
	 * @author cyy
	 * @date 2017年5月18日
	 * @param funcs
	 * @return
	 */
	private String functionListToJsonString(MallOperatorVo operator,List<MallFunctionVo> funcs){
		if(funcs == null || funcs.size() == 0){
			return "[]";
		}
		Map<Integer,List<MallFunctionVo>> data = new HashMap<Integer,List<MallFunctionVo>>();
		for(MallFunctionVo item : funcs){
			if(item.getState() == YesNoEnum.NO.getValue()){
				continue;
			}
			if(StringUtils.isEmpty(item.getFuncUrl())){
				item.setFuncUrl("javascript:void(0);");
			}
			if(!data.containsKey(item.getFuncTypeID().intValue())){
				data.put(item.getFuncTypeID().intValue(),new ArrayList<MallFunctionVo>());
			}
			data.get(item.getFuncTypeID().intValue()).add(item);
		}
		List<MallFunctionTypeVo> ftl = new ArrayList<MallFunctionTypeVo>();
		for(Map.Entry<Integer,List<MallFunctionVo>> item : data.entrySet()){
			if(item.getValue().size() == 0){continue;}
			FunctionTypeEnum typeEnum = FunctionTypeEnum.getFunctionType(item.getKey());
			
			MallFunctionTypeVo tvo = new MallFunctionTypeVo();
			tvo.setFunctions(item.getValue());
			tvo.setFunctionTypeId(item.getKey());
			
			tvo.setFunctionTypeName(typeEnum == null ? "" : typeEnum.getName());
			tvo.setIcon(typeEnum == null ? "" : typeEnum.getIcon());
			ftl.add(tvo);
		}
		
		operator.setFunctionList(ftl);
		
		return JSON.toJSONString(operator);
	}
	
	/**
	 * json 转对象
	 * @author cyy
	 * @date 2017年5月18日
	 */
	private MallOperatorVo jsonStringToFunctionList(String str){
		if(StringUtils.isEmpty(str)){
			return null;
		}
		JSONObject json = JSONObject.parseObject(str);
		
		MallOperatorVo data =  json.toJavaObject(MallOperatorVo.class);
		// JSONArray list = JSON.parseArray(str);
		// List<MallFunctionTypeVo> data = list.toJavaList(MallFunctionTypeVo.class);
		return data;
	}
}
