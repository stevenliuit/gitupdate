$(function(){
    var clickObj = $('#categoryListTable tr td .caret');
    clickObj.each(function () {
        $(this).addClass('develop');
        var subTr = "tr[data-class=" + $(this).attr('data') + "_1]";
        $(subTr).each(function () {
            $(subTr).show();
        });
    });
    $("#categoryListTable").delegate(".caret","click",function(){
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
        layer.confirm('确认要删除所有类目？', {
            title: '删除提示',
            icon: 0,
            btn: ['确  定', '取  消']
        }, function (index, layero) {
            interface.delAllCategories();
        });
    });
    //查询类目信息
    interface.searchCateList();
    /**保存添加的类目*/
    $("a[name='save_category_data']").bind("click", function(){
        interface.saveCategoryAdd();
    });

    /**单个分类删除 - in use*/
    $("#categoryList").delegate("a[name='del_current_category']", "click", function(){
        var _that = $(this);
        var _id = _that.parent().parent().attr("id");
        var _level = _that.parent().parent().attr("level");
        layer.confirm('确认要删除该类目？', {
            title: '删除提示',
            icon: 0,
            btn: ['确  定', '取  消']
        }, function (index, layero) {
            interface.delCategoryByCid(_id, _level);
        }, function (index) {

        });
    });

    /**添加一级分类tr*/
    $("button[name='add_new_category']").bind("click", function(){
        var _level = 1;
        var _parentCid = -1;
        var _flag = "new";
        var $lastTr = " <tr class='table-btnbox' level="+_level+" parentcid="+_parentCid+" flag="+_flag+">"
            +"<td> </td>"
            +"<td> <div class='col-1 control-line'>"
                    +"<a href='javascript:void(0);'>"
                    +"   <span class='caret caret-down'></span> </a> </div>"
                +"<div class='col-8'>"
                +"<input type='text' class='form-control categoryname'name='newCategory'  placeholder='类目名称' maxlength='20'></div> </td>"
            +"<td></td><td></td>"
            +"<td class='text-center'><a href='javascript:void(0);' class='text-blue pop-ask'  name='del_current_category_new'>删除</a></td>"
       +"</tr>";
        $("#j_category_body").append($lastTr);
        $("input[name=newCategory]:last").focus();
    });

    /**添加子分类tr - in use*/
    $("#j_category_body").delegate("button[name='add_category_level2']", "click", function(){
        var _that = $(this);
        var _id = _that.parent().parent().attr("id");
        var faDataSubId = _that.parent().parent().attr("faDataSubId");
        var _level = 2;
        var _flag = "new";
        var $insertTr = " <tr class='table-btnbox' level="+_level+" parentcid="+_id+" flag="+_flag+" data-class="+faDataSubId+" subtr='subtr'>"
        +"<td> </td>"
        +"<td> <div class='col-3 control-line text-right'>"
        +"   <span class='classify-line'></span>  </div>"
        +"<div class='col-8'>"
        +"<input type='text' class='form-control categoryname' placeholder='子类目名称' maxlength='20'></div> </td><td> </td>"
        +"<td class='text-center'></td>"
        +"<td class='text-center'><a href='javascript:void(0);' class='text-blue pop-ask'  name='del_current_category_new'>删除</a></td>"
        +"</tr>";
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
            window.parent.$("#J_item_manager_item_list").attr("href", "item/toItemList.html?shopCid=" + _shopCid);
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
     * 查询类目
     */
    searchCateList:function(){
        $.ajax({
            url : "/platcategory/searchCategoryData",
            type : "post",
            data : { },
            dataType: "json",
            success: function(data){
                if(data){
                    var html_txt = '<tr class="table-btnbox"><td colspan="6" class="text-center">查询失败</td></tr>';
                    if(data.isSuccess == true){
                        var cateList = data.list;
                        if(cateList && cateList.length > 0){
                            //生成数据
                            html_txt = "";
                            for(var i = 0; i < cateList.length; i++){
                                var faDataParent = "fa"+cateList.length;
                                var faDataSub = faDataParent +"_" +1;
                                var category = cateList[i];
                                var iconfont1 = "";
                                if(cateList.length == 1){
                                    iconfont1 = '<a href="javascript:void(0);"><i class="iconfont text-grey icon-jiantouzhiding"></i></a>' +
                                        '<a href="javascript:void(0);"><i class="iconfont text-grey icon-xiangshangjiantou"></i></a>';
                                }else {
                                    iconfont1 = '<a href="javascript:void(0);" id="j_category_top"><i class="iconfont text-blue icon-jiantouzhiding"></i></a>' +
                                        '<a href="javascript:void(0);" id="j_category_up"><i class="iconfont text-blue icon-xiangshangjiantou"></i></a>';
                                }
                                var iconfont2 = "";
                                if(i < cateList.length -1 ) {
                                    iconfont2 = '<a href="javascript:void(0);" id="j_category_down"><i class="iconfont text-blue icon-jiantouxiangxia"></i></a>' +
                                        '<a href="javascript:void(0);" id="j_category_bottom"><i class="iconfont text-blue icon-jiantouzhidi"></i></a>';
                                }else {
                                    iconfont2 = '<a href="javascript:void(0);" id="j_category_down"><i class="iconfont text-grey icon-jiantouxiangxia"></i></a>' +
                                        '<a href="javascript:void(0);" id="j_category_bottom"><i class="iconfont text-grey icon-jiantouzhidi"></i></a>';
                                }
                                var ltObjects = category.ltObjects;
                                var html_sub = "";
                                for(var j =0; j<ltObjects.length; j++){
                                    var cs = ltObjects[j];
                                    var iconfontSub1 = "";
                                    if(cateList.length == 1){
                                        iconfontSub1 = '<a href="javascript:void(0);"><i class="iconfont text-grey  icon-xiangshangjiantou"></i></a>';
                                    }else {
                                        iconfontSub1 = '<a href="javascript:void(0);" id="j_category_up"><i class="iconfont text-blue  icon-xiangshangjiantou"></i></a>';
                                    }
                                    var iconfontSub2 = "";
                                    if(i < cateList.length -1 ) {
                                        iconfontSub2 = '<a href="javascript:void(0);" id="j_category_down"><i class="iconfont text-blue  icon-jiantouxiangxia"></i></a>';
                                    }else {
                                        iconfontSub2 = '<a href="javascript:void(0);" id="j_category_down"><i class="iconfont text-grey  icon-jiantouxiangxia"></i></a>';
                                    }
                                    html_sub = html_sub + '<tr class="table-btnbox"  id='+ cs.cid +' level=' + cs.lev +' data-class= '+ faDataSub +' subtr="subtr"><td></td>' +
                                        '<td><div class="col-3 control-line text-right"><span class="classify-line"></span></div>' +
                                        '<div class="col-8"><input class="form-control" placeholder="子类目名称" type="text" value='+ cs.categoryName +' id="j_category_name" maxlength="20"></div></td>' +
                                        '<td class="text-center">'+ cs.cid+'</td>' +
                                        '<td class="text-center">'+ iconfontSub1 + iconfontSub2+'</td>' +
                                        '<td class="text-center"><a href="javascript:void(0);" class="text-blue pop-ask" id="j_category_delete" name="del_current_category">删除</a></td>' +
                                        '</tr>';
                                }
                                html_txt = html_txt + '<tr class="table-btnbox" id='+category.cid+' level=' +category.lev +'><td></td>' +
                                    '<td><div class="col-1 control-line"><a href="javascript:void(0);"><span class="caret" data='+ faDataParent +'></span></a></div>' +
                                    '<div class="col-8"><input class="form-control" value='+ category.categoryName + ' placeholder="类目名称" type="text" id="j_category_name" maxlength="20"></div></td>' +
                                    '<td class="text-center">'+ category.cid + '</td>' +
                                    '<td class="text-center">'+ iconfont1 + iconfont2 + '</td>' +
                                    '<td class="text-center"><a href="javascript:void(0);" class="text-blue pop-ask" id="j_category_delete" name="del_current_category">删除</a></td>' +
                                    html_sub + '<tr class="table-btnbox" id="' + category.cid + '" faDataSubId="' + faDataSub + '"><td></td>' +
                                    '<td><div class="col-3 control-line text-right"><span class="classify-line"></span></div>' +
                                    '<button type="button" class="btn btn-default btn-sm" id="j_add_level2" name="add_category_level2"><i class="iconfont">&#xe617;</i>添加子类目</button></td>' +
                                    '<td></td><td></td><td></td><tr>'
                                    '</tr>';
                            }
                        }else{
                            html_txt = '<tr class="table-btnbox"><td colspan="6" class="text-center">未找到已添加的商品类目</td></tr>';
                        }
                    }else{
                        layer.msg("查询失败请稍后重试...", {skin: 'layer-ext-b2b layer-b2b-hui'});
                    }
                    $("#j_category_body").html(html_txt);
                }
            },
            error: function(data){
                layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
            }
        });
    },

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
            if(level == 2){
                var hasLeaf = 1;
            }
            var parentCid = $(n).attr("parentcid");
            var categoryAddVo = new Object();
            if(categoryName.length!=0){
                categoryAddVo.categoryName = categoryName;
                categoryAddVo.lev = level;
                categoryAddVo.hasLeaf= hasLeaf;
                categoryAddVo.status =1;
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
            url : "/platcategory/addNewCategory",
            type : "post",
            data : {
                categoryData : categoryData
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
            layer.msg("类目名不能为空，请正确填写。", {skin: 'layer-ext-b2b layer-b2b-hui'});
            return;
        }
        if(categoryName.length > 20) {
            layer.msg("类目名称长度需小于20个字符，请检查确认。", {skin: 'layer-ext-b2b layer-b2b-hui'});
            return;
        }
        $.ajax({
            url : "/platcategory/modifyCategoryName",
            type : "post",
            data : {
                cid : cid,	//类目ID
                categoryName : categoryName	//修改后的类目名称
            },
            dataType: "json",
            success: function(data){
                if(data){
                    if(data.result.msg){
                        layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                    }
                    if(data.result.success == true){
                        layer.msg("修改类目名成功。", {skin: 'layer-ext-b2b layer-b2b-hui'});
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
            url : "/platcategory/deleteAllCategories",
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
                        layer.msg("删除所有类目成功。", {skin: 'layer-ext-b2b layer-b2b-hui'});
                        window.location.reload(true);
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
    delCategoryByCid:function(cid, lev){
        $.ajax({
            url : "/platcategory/delCategoryByCid",
            type : "post",
            data : {
                cid : cid,	//类目ID
                lev : lev	//类目级别
            },
            dataType: "json",
            success: function(data){
                if(data){
                    if(data.result.msg){
                        layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                    }
                    if(data.result.isSuccess == true){
                        layer.msg('删除类目成功。', {skin: 'layer-ext-b2b layer-b2b-hui'});
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
            url : "/platcategory/markExpandAll",
            type : "post",
            data : {
                markExpand : markFlag
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
            url : "/platcategory/markExpandByCid",
            type : "post",
            data : {
                cid : cid,
                markExpand : markExpand
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
            url : "/platcategory/markHomeShowByCid",
            type : "post",
            data : {
                cid : cid,
                markHomeShow : markHomeShow
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
            url : "/platcategory/categorySort",
            type : "post",
            data : {
                cid : cid,
                flag : flag
            },
            dataType: "json",
            success: function(data){
                if(data){
                    if(data.result.msg){
                        layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                    }
                    if(data.result.isSuccess == true){
                        layer.msg('移动成功。', {skin: 'layer-ext-b2b layer-b2b-hui'});
                        window.location.reload(true);
                    }
                }
            },
            error: function(data){
                layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
            }
        });
    }
};
