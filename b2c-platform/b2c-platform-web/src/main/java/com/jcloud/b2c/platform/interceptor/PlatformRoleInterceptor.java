package com.jcloud.b2c.platform.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jcloud.b2c.common.common.util.JacksonUtil;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.interceptor.LoginInterceptor;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.platform.domain.vo.MallFunctionVo;
import com.jcloud.b2c.platform.service.feign.CacheFeignClient;
import com.jcloud.b2c.platform.service.role.MallOperatorService;
import com.jcloud.b2c.platform.util.RoleXmlParser;


/**
 * 后台权限控制拦截器
 * @author cyy
 */
public class PlatformRoleInterceptor extends HandlerInterceptorAdapter{
	
	@Resource
	private CacheFeignClient cacheFeignClient;
	@Resource
	private RoleXmlParser roleXml;
	@Resource
	private MallOperatorService operatorService;
	
	
	private static Cache<String, String> CACHE = CacheBuilder.newBuilder().maximumSize(10).expireAfterWrite(60, TimeUnit.SECONDS).build();

	private static final String ROLE_CACHE_KEY = "userRolesCache";
	
	// private static final String DELIMITDER = ","; // 多个权限之间的分隔符
	
	private static final String NO_FUNCTION = "NOFUNCTION"; // 没有权限标识
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(!(handler instanceof HandlerMethod)){
			return true;
		}
		
		HandlerMethod method = (HandlerMethod) handler;
		
        String roleKey = method.getBean().getClass().getName()+"."+method.getMethod().getName();
		
		String [] roles = roleXml.getRolesByKey(roleKey);
		
        boolean exist = roleExist(roles);
        
        if(exist){ //  有权限，放行
        	return true;
        }
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
        	response.getWriter().write("您没有权限");
        }
    	
    	return false;
    	
	}
	/**
	 * 校验 登录用户是否有改函数的访问权限
	 * @param values 该函数 的权限 code 吗
	 * @param decollator 权限分割符号
	 * @return 
	 * @throws ExecutionException 
	 */
	private boolean roleExist(String [] values) throws ExecutionException{
		
		if(null == values || values.length == 0 ){
			// 没有权限控制，直接放行
			return true;
		}
		// 获取登录用户权限
		String [] roles = this.getUserRoles();
		if(null == roles || roles.length == 0 ){
			return false;
		}
		
		for(String item : roles){
			for(String temp : values){
				if(item.equals(temp)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	private String [] getUserRoles() throws ExecutionException{
		Long tenantId = ControllerContext.getTenantId();
		String userErp = ControllerContext.getUserName();
		
		String cacheKey = ROLE_CACHE_KEY+":tenantId:"+tenantId+":userErp:"+userErp;
		
		String  cacheRoles = CACHE.get(cacheKey,new Callable<String>() {
			@Override
			public String call() throws Exception {
				String result = cacheFeignClient.get(ControllerContext.getTenantId(),cacheKey);
				if(StringUtils.isBlank(result)){
					BaseResponseVo<List<MallFunctionVo>> roles = operatorService.queryFunction(tenantId.toString(), userErp);
					// 如果请求错误，则 getDate 返回为 null 在 getFunctionStr 中返回没有权限标识
					result = PlatformRoleInterceptor.this.getFunctionStr(roles.getData());
					// 存  redis  过期时间为 1天 
					cacheFeignClient.save2Cache(tenantId,cacheKey,result,60 * 60 * 24);
				}
				return result;
			}
		});
		
		if(StringUtils.isBlank(cacheRoles)){
			return null;
		}
		String [] result = cacheRoles.split(RoleXmlParser.DELIMITER);
		return result;
	}
	
	
	private String getFunctionStr(List<MallFunctionVo> funcs){
		if(funcs == null || funcs.size() == 0){
			return NO_FUNCTION;// 没有权限
		}
		StringBuilder bstr = new StringBuilder();
		for(MallFunctionVo func : funcs){
			bstr.append(func.getCode()).append(RoleXmlParser.DELIMITER);
		}
		if(bstr.length() > RoleXmlParser.DELIMITER.length()){
			bstr.delete(bstr.length() - RoleXmlParser.DELIMITER.length(),bstr.length());
		}
		return bstr.toString();
	}
	
}
