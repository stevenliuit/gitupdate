package com.jcloud.b2c.mall.service.client.enums;

/**
 * 权限分类  
 * @author cyy
 * @dete 2017-05-16
 */
public enum FunctionTypeEnum {
	PRODUCT(1,"商品管理","spgl"),
	PAGE(2,"页面管理","ymgl"),
	CATEGORY(3,"类目管理","xmgl"),
	SEARCH(4,"搜索管理","ssgl"),
	ARTICLE(5,"文章管理","wzgl"),
	ORDER(6,"订单管理","odgl"),
	ROLE(7,"权限管理","wzgl")
	;
	private FunctionTypeEnum(int value,String name,String icon){
		this.value = value;
		this.name = name;
		this.icon = icon;
	}
	
	public static FunctionTypeEnum getFunctionType(int value){
		for(FunctionTypeEnum item : values()){
			if(item.getValue() == value){
				return item;
			}
		}
		return null;
	}
	
	/*public static String [][] getValues(){
		FunctionTypeEnum [] enums = values();
		String [] [] result = new String[enums.length][2];
		int i = 0;
		for(FunctionTypeEnum item : enums){
			String [] itemValue = {String.valueOf(item.getValue()),item.getName()};
			result[i] = itemValue;
			i ++;
		}
		return result;
	}*/
	
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
	private String icon; 
	
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
