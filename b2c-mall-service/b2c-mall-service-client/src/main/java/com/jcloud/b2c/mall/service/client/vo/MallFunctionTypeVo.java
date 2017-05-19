package com.jcloud.b2c.mall.service.client.vo;

import java.io.Serializable;
import java.util.List;

public class MallFunctionTypeVo implements Serializable {
	
	private int functionTypeId;
	
	private String functionTypeName;
	
	private String icon;
	
	private List<MallFunctionVo> functions;

	public int getFunctionTypeId() {
		return functionTypeId;
	}

	public void setFunctionTypeId(int functionTypeId) {
		this.functionTypeId = functionTypeId;
	}

	public String getFunctionTypeName() {
		return functionTypeName;
	}

	public void setFunctionTypeName(String functionTypeName) {
		this.functionTypeName = functionTypeName;
	}

	public List<MallFunctionVo> getFunctions() {
		return functions;
	}

	public void setFunctions(List<MallFunctionVo> functions) {
		this.functions = functions;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof MallFunctionTypeVo)){
			return false;
		}
		MallFunctionTypeVo target = (MallFunctionTypeVo) obj;
		return target.functionTypeId == this.functionTypeId;
	}
	
	@Override
	public int hashCode() {
		return this.functionTypeId;
	}

}
