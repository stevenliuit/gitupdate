package com.jcloud.b2c.platform.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jcloud.b2c.mall.service.client.vo.MallFunctionTypeVo;
import com.jcloud.b2c.mall.service.client.vo.MallFunctionVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jcloud.b2c.common.common.util.JacksonUtil;
import com.jcloud.b2c.common.common.web.interceptor.LoginInterceptor;
import com.jcloud.b2c.platform.service.role.MallOperatorService;
import com.jcloud.b2c.platform.util.RoleXmlParser;


/**
 * 后台权限控制拦截器
 * @author cyy
 */
public class PlatformRoleInterceptor extends HandlerInterceptorAdapter{
	
	
	@Resource
	private RoleXmlParser roleXml;
	@Resource
	private MallOperatorService operatorService;
	
	private static final String REQUEST_KEY = "userFunctions";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(!(handler instanceof HandlerMethod)){
			return true;
		}
		String userErp = String.valueOf(request.getAttribute("userErp"));
		if(StringUtils.isBlank(userErp)){
			returnError(request, response,"您的登录信息错误，请重新登录");
		    return false;
		}
		String cln = request.getParameter("cln"); // 清空所有缓存
		List<MallFunctionTypeVo> funcs = roleXml.getUserFunctions(userErp,StringUtils.isNotEmpty(cln)); //this.getUserRoles(userErp,cln);
		// 请求中保存 用户的权限
		request.setAttribute(REQUEST_KEY, funcs);
		
		HandlerMethod method = (HandlerMethod) handler;
		
        String roleKey = method.getBean().getClass().getName()+"."+method.getMethod().getName();
		String [] roles = roleXml.getRolesByKey(roleKey);
        boolean exist = roleExist(roles,funcs);
        if(exist){ //  有权限，放行
        	return true; 
        }
        returnError(request, response,"您没有权限");
    	return false;
	}
	/**
	 * 校验 登录用户是否有改函数的访问权限
	 * @param values 该函数 的权限 code 吗
	 * @param list 权限分割符号
	 * @return 
	 * @throws ExecutionException 
	 */
	private boolean roleExist(String [] values,List<MallFunctionTypeVo> list) throws ExecutionException{
		if(list == null || list.size() == 0){
			return false; // 用户没有任何权限
		}
		if(null == values || values.length == 0 ){
			// 没有权限控制，直接放行
			return true;
		}
		for(MallFunctionTypeVo  t : list){
			if(CollectionUtils.isEmpty(t.getFunctions())){
				continue;
			}
			for(MallFunctionVo item : t.getFunctions()){
				for(String temp : values){
					if(StringUtils.isBlank(temp)){continue;}
					if(temp.equals(item.getCode())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/*private List<MallFunctionTypeVo> getUserRoles(final String userErp,String cln) throws ExecutionException{
		final Long tenantId = ControllerContext.getTenantId();
		
		final String cacheKey = roleXml.getUserFunctionCacheKey(tenantId, userErp);
		
		if(StringUtils.isNotBlank(cln)){ // 清空
			roleXml.invalidate(cacheKey);
			cacheFeignClient.del(tenantId, cacheKey);
		}
		
		String  cacheRoles = roleXml.getLocalUserFunctionCache(cacheKey,new Callable<String>() {
			@Override
			public String call() throws Exception {
				return cacheFeignClient.get(tenantId,cacheKey);
			}
		});
		
		// 缓存中不存在 就去 数据库取
		if(StringUtils.isBlank(cacheRoles)){
			BaseResponseVo<List<MallFunctionVo>> roles = operatorService.queryFunction(tenantId,userErp,1,1);
			cacheRoles = this.functionListToJsonString(roles.getData()); 
			// 存  redis  过期时间为 1天 
			// cacheFeignClient.save2Cache(tenantId,cacheKey,cacheRoles,60 * 60 * 24);
		}
		List<MallFunctionTypeVo> result = this.jsonStringToFunctionList(cacheRoles);
		return result;
	}*/
	
	
	/**
	 * 返回错误信息
	 * @author cyy
	 * @date 2017年5月18日
	 * @param request
	 * @param response
	 * @param msg
	 * @throws IOException
	 */
	private void returnError(HttpServletRequest request,HttpServletResponse response,String msg) throws IOException{
		 // 无权限，返回错误信息
    	response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
    	// 跳转到 无权限页面
    	if (LoginInterceptor.isAjaxRequest(request)) {
            Map<String, String> result = new HashMap<String,String>();
            result.put("errorCode", "-1");
            String callback = request.getParameter("callback");
            String writeVal = JacksonUtil.convert(result);
            if (callback != null) {
                writeVal = callback + "(" + writeVal + ")";
            }
            response.getWriter().write(writeVal);
        }else{
        	response.getWriter().write(msg);
        }
	}
}
