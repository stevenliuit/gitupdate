$(function(){
	var clickObj = $('#categoryListTable tr td .caret');
	clickObj.each(function () {
		$(this).addClass('develop');
		var subTr = "tr[data-class=" + $(this).attr('data') + "_1]";
		$(subTr).each(function () {
			$(subTr).show();
		});
	});
	clickObj.click(function () {
		var _this=$(this);
		$(this).toggleClass('develop');
		var subTr = "tr[data-class=" + $(this).attr('data') + "_1]";
		$(subTr).each(function () {
			if($(_this).hasClass('develop')){
				$(this).show();
			}else{
				$(this).hide();
			}
			//$(this).toggle();
		});
	});

	/**全部展开 - in use*/
	$("button[name='expand_category_all']").bind("click", function(){
		var clickObj = $('#categoryListTable tr td .caret');
		clickObj.each(function () {
			if (!$(this).hasClass("develop")) {
				$(this).toggleClass('develop');
			}
		});

		var objects = $("tr[subtr=subtr]");
		objects.each(function () {
			$(this).show();
		});
	});
	
	/**全部收起 - in use*/
	$("button[name='unexpand_category_all']").bind("click", function(){
		var clickObj = $('#categoryListTable tr td .caret');
		clickObj.each(function () {
			if ($(this).hasClass("develop")) {
				$(this).toggleClass('develop');
			}
		});

		var objects = $("tr[subtr=subtr]");
		objects.each(function () {
			$(this).hide();
		});
	});
	/**删除全部 - in use*/
	$("#deleteAllCategories").bind("click", function(){
//		if(confirm("确认要删除所有分类？")){
//			interface.delAllCategories();
//		}
        layer.confirm('确认要删除所有分类？', {
            title: '删除提示',
            icon: 0,
            btn: ['确  定', '取  消']
        }, function (index, layero) {
            interface.delAllCategories();
        });
	});
	
	/**保存添加的类目*/
	$("a[name='save_category_data']").bind("click", function(){
		interface.saveCategoryAdd();
	});
	
	/**单个分类删除 - in use*/
	$("#categoryList").delegate("a[name='del_current_category']", "click", function(){
		var _that = $(this);
		var _id = _that.parent().parent().attr("id");
		var _level = _that.parent().parent().attr("level");
		layer.confirm('确认要删除该分类？', {
			title: '删除提示',
			icon: 0,
			btn: ['确  定', '取  消'],
		}, function (index, layero) {
			interface.delCategoryByCid(_id, _level);
		}, function (index) {

		});
	});
	
	/**添加一级分类tr*/
	$("button[name='add_new_category']").bind("click", function(){
		var _level = 1;
		var _parentCid = 0;
		var _flag = "new";
		var $lastTr = getDataByTempl({"level":_level,"parentCid":_parentCid,"flag":_flag}, "tmpl-lastTr");
		$("#j_category_body").append($lastTr);
	});
	
	/**添加子分类tr - in use*/
	$("#j_category_body").delegate("button[name='add_category_level2']", "click", function(){
		var _that = $(this);
		var _id = _that.parent().parent().attr("id");
		var faDataSubId = _that.parent().parent().attr("faDataSubId");
		var _level = 2;
		var _flag = "new";
		var $insertTr = getDataByTempl({"level":_level,"parentCid":_id,"flag":_flag,"faDataSubId":faDataSubId}, "tmpl-insertTr");
		_that.parent().parent().before($insertTr);
	});
	$("#j_category_body").delegate("a[name='del_current_category_new']", "click", function(){
		$(this).parents(".table-btnbox").remove();
	});
	
	/**绑定categoryName的change事件 - in use*/
	$("#categoryList").delegate("#j_category_name", "change", function(){
		var _that = $(this);
		var _id = _that.parent().parent().parent().attr("id");
		var _categoryName = _that.val().trim();
		interface.updateCategoryName(_id, _categoryName);
	});
	
	/**单个标记展开/收起 - in use*/
	$(document).on("ifChanged", "#j_category_expand", function(event){
		var cid = $(this).parent().parent().parent().parent().attr("id");
		if($(this).attr("checked")){
			interface.markExpandByCid(cid, 1);
		} else {
			interface.markExpandByCid(cid, 2);
		}
	});
	
	/**单个标记首页是否展示 - in use*/
	$(document).on("ifChanged", "#j_category_homeshow", function(event){
		var cid = $(this).parent().parent().parent().parent().attr("id");
		if($(this).attr("checked")){
			interface.markHomeShowByCid(cid, 1);
		} else {
			interface.markHomeShowByCid(cid, 2);
		}
	});
	
	/**top操作 - in use*/
	$("#categoryList").delegate("#j_category_top", "click", function(){
		var cid = $(this).parent().parent().attr("id");
		interface.moveCategory(cid, "top");
	});
	
	/**up操作 - in use*/
	$("#categoryList").delegate("#j_category_up", "click", function(){
		var cid = $(this).parent().parent().attr("id");
		interface.moveCategory(cid, "up");
	});
	
	/**bottom操作 - in use*/
	$("#categoryList").delegate("#j_category_bottom", "click", function(){
		var cid = $(this).parent().parent().attr("id");
		interface.moveCategory(cid, "bottom");
	});
	
	/**down操作 - in use*/
	$("#categoryList").delegate("#j_category_down", "click", function(){
		var cid = $(this).parent().parent().attr("id");
		interface.moveCategory(cid, "down");
	});
	
	$("#j_category_body").on("click", "a[name='view-item']", function(){
		var url = "item/toItemList.html";
		if(checkPermission(url)){
			var _shopCid = $(this).parent().parent().attr("id");
			window.parent.$("#J_item_manager_item_list").attr("href", domainUrl + "item/toItemList.html?shopCid=" + _shopCid);
			if(!$(window.parent.$("#J_item_manager_item_list").parents(".s-sortList")[0]).hasClass("cur")){
				$(window.parent.$("#J_item_manager_item_list").parents("ul")[0]).prev("span").trigger("click");
			}
			window.parent.$("#J_item_manager_item_list")[0].click();
		}else{
			layer.alert('<i class="pop-tips error"></i>抱歉！当前账号无权访问该内容，请联系店铺主账号帮您开通权限', {skin: 'layer-ext-b2b layer-b2b-hui'});
		}
	});
});

/**
 * 类目接口
 * @return
 */
var interface = {
	
	/**
	 * 保存添加类目 - in use
	 */
	saveCategoryAdd:function(){
		var array = [];
		var objects = $("tr[flag=new]");
		var nameLength = true;
		objects.each(function(i,n){
			var categoryName = $(n).find(".categoryname").val().trim();
			if(categoryName.length > 20){
				nameLength = false;
				return;
			}
			var level = $(n).attr("level");
			var parentCid = $(n).attr("parentcid");
			var categoryAddVo = new Object();
			if(categoryName.length!=0){
				categoryAddVo.categoryName = categoryName;
				categoryAddVo.level = level;
				categoryAddVo.parentCid = parentCid;
				array.push(categoryAddVo);
			}
		});
		if(nameLength == false) {
			layer.msg('类目名称长度需小于20个字符，请检查确认。', {skin: 'layer-ext-b2b layer-b2b-hui'});
			return;
		}
		if(array.length == 0){
			layer.msg('本次没有新增分类操作。', {skin: 'layer-ext-b2b layer-b2b-hui'});
			return;
		}
		var categoryData = JSON.stringify(array);
		$.ajax({
			url : domainUrl+"shopcategory/addNewCategory.html",
			type : "post",
			data : {
				categoryData : categoryData,
			},
			dataType: "json",
			success: function(data){
				if(data){
					if(data.result.msg){
						layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
					}
					if(data.result.success == true){
						layer.msg("保存成功。", {skin: 'layer-ext-b2b layer-b2b-hui'});
						window.location.reload(true);
					}
				}
			},
			error: function(data){
				layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
			}
		});
	},
	
	/**
	 * 修改类目名称 - in use
	 */
	updateCategoryName:function(cid, categoryName){
		if(categoryName.length==0){
			layer.msg("分类名不能为空，请正确填写。", {skin: 'layer-ext-b2b layer-b2b-hui'});
			return;
		}
		if(categoryName.length > 20) {
			layer.msg("类目名称长度需小于20个字符，请检查确认。", {skin: 'layer-ext-b2b layer-b2b-hui'});
			return;
		}
		$.ajax({
			url : domainUrl+"shopcategory/modifyCategoryName.html",
			type : "post",
			data : {
				cid : cid,	//类目ID
				categoryName : categoryName,	//修改后的类目名称
			},
			dataType: "json",
			success: function(data){
				if(data){
					if(data.result.msg){
						layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
					}
					if(data.result.success == true){
						layer.msg("修改分类名成功。", {skin: 'layer-ext-b2b layer-b2b-hui'});
					}
				}
			},
			error: function(data){
				layer.msg("服务器打盹，请稍后再试。", {skin: 'layer-ext-b2b layer-b2b-hui'});
			}
		});
	},
	
	/**
	 * 删除全部分类 - in use
	 */
	delAllCategories:function(){
		$.ajax({
			url : domainUrl+"shopcategory/deleteAllCategories.html",
			type : "post",
			data : {
			},
			dataType: "json",
			success: function(data){
				if(data){
					if(data.result.msg){
						layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
					}
					if(data.result.success == true){
						layer.msg("删除所有分类成功。", {skin: 'layer-ext-b2b layer-b2b-hui'});
						window.location.reload(true);;
					}
				}
			},
			error: function(data){
				layer.msg("服务器打盹，请稍后再试。", {skin: 'layer-ext-b2b layer-b2b-hui'});
			}
		});
	},
	
	/**
	 * 根据cid删除分类 - in use
	 */
	delCategoryByCid:function(cid, level){
		$.ajax({
			url : domainUrl+"shopcategory/delCategoryByCid.html",
			type : "post",
			data : {
				cid : cid,	//类目ID
				level : level,	//类目级别
			},
			dataType: "json",
			success: function(data){
				if(data){
					if(data.result.msg){
						layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
					}
					if(data.result.success == true){
						layer.msg('删除分类成功。', {skin: 'layer-ext-b2b layer-b2b-hui'});
						window.location.reload(true);
					}
				}
			},
			error: function(data){
				layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
			}
		});
	},
	
	/**
	 * 标记全部展开/全部收起 - in use - 暂未使用
	 */
	markExpandAllByFlag:function(flag){
		var markFlag;
		if(flag){
			markFlag = 1;
		} else {
			markFlag = 2;
		}
		$.ajax({
			url : domainUrl+"shopcategory/markExpandAll.html",
			type : "post",
			data : {
				markExpand : markFlag,
			},
			dataType: "json",
			success: function(data){
				if(data){
					if(data.result.msg){
						layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
					}
					if(data.result.success == true){
						layer.msg('设置成功。', {skin: 'layer-ext-b2b layer-b2b-hui'});
						window.location.reload(true);
					}
				}
			},
			error: function(data){
				layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
			}
		});
	},
	
	/**
	 * 单个标记展开/收起 - in use
	 */
	markExpandByCid:function(cid, markExpand){
		$.ajax({
			url : domainUrl+"shopcategory/markExpandByCid.html",
			type : "post",
			data : {
				cid : cid,
				markExpand : markExpand,
			},
			dataType: "json",
			success: function(data){
				if(data){
					if(data.result.msg){
						layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
					}
					if(data.result.success == true){
						layer.msg('设置成功。', {skin: 'layer-ext-b2b layer-b2b-hui'});
					}
				}
			},
			error: function(data){
				layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
			}
		});
	},
	
	/**
	 * 单个标记首页显示/不显示 - in use
	 */
	markHomeShowByCid:function(cid, markHomeShow){
		$.ajax({
			url : domainUrl+"shopcategory/markHomeShowByCid.html",
			type : "post",
			data : {
				cid : cid,
				markHomeShow : markHomeShow,
			},
			dataType: "json",
			success: function(data){
				if(data){
					if(data.result.msg){
						layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
					}
					if(data.result.success == true){
						layer.msg('设置成功。', {skin: 'layer-ext-b2b layer-b2b-hui'});
					}
				}
			},
			error: function(data){
				layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
			}
		});
	},
	
	/**
	 * 类目移动/排序 - in use
	 */
	moveCategory:function(cid, flag){
		$.ajax({
			url : domainUrl+"shopcategory/categorySort.html",
			type : "post",
			data : {
				cid : cid,
				flag : flag,
			},
			dataType: "json",
			success: function(data){
				if(data){
					if(data.result.msg){
						layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
					}
					if(data.result.success == true){
						var ref = window.open(window.location.href, "_self");
						if(ref) {
							layer.msg('移动成功。', {skin: 'layer-ext-b2b layer-b2b-hui'});
						}

					}
				}
			},
			error: function(data){
				layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
			}
		});
	}
};