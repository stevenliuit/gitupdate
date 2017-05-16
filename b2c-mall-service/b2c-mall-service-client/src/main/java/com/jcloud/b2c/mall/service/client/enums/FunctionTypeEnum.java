package com.jcloud.b2c.mall.service.client.enums;

/**
 * 权限分类  
 * @author cyy
 * @dete 2017-05-16
 */
public enum FunctionTypeEnum {
	PRODUCT(1,"商品管理"),
	PAGE(2,"页面管理"),
	CATEGORY(3,"类目管理"),
	SEARCH(4,"搜索管理"),
	ARTICLE(5,"文章管理"),
	ORDER(6,"订单管理"),
	ROLE(7,"权限管理")
	;
	private FunctionTypeEnum(int value,String name){
		this.value = value;
		this.name = name;
	}
	/**
	 * 判断 是否包含
	 * @author cyy
	 * @date 2017年5月16日
	 * @param value
	 */
	public static boolean include(int value){
		for(FunctionTypeEnum item : values()){
			if(item.getValue() == value){
				return true;
			}
		}
		return false;
	}
	
	private int value;
	private String name;
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
