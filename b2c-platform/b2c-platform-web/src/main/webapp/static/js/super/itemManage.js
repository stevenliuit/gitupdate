$(function(){
    //ItemList.searchItemList(1);
    $("#searchItemList").on("click",function(){
        ItemList.searchItemList(1);
    });
    $(".poptrue").on("click",function(){
        $(".poploading").hide();
        $(".popend").hide();
        $("#importExcel").show();
    });
    $(".addfileb").on("click",function(){
        $(this).siblings("input[name=itemTemplate]").trigger("click");
    });
    $("#J_first_category").on("change", function(){
        ItemList.queryCategorys($(this).val(),"#J_second_category");
    });
    $("#J_second_category").on("change", function(){
        ItemList.queryCategorys($(this).val(),"#J_third_category");
    });
    $("a[name='resetSearchKey']").on("click", function(){
        $("#skuName").val("");
        $("#skuId").val("");
        $("#jdSkuId").val("");
        $("#brand").val("");
        $("#itemStatus").val("");
        $("#J_first_category").val("");
        $("#J_second_category").val("");
    });
    $("#skuId").on("keyup", function(){
        var val = $(this).val();
        if(val != null && val.length > 0 && !ItemList.checkSkuId(val)){
            layer.msg("商品ID只能为1-25位的数字", {skin: 'layer-ext-b2b layer-b2b-hui'});
            var n_val = val.substring(0, val.length - 1);
            $(this).val(n_val);
        }
    });
    $("#jdSkuId").on("keyup", function(){
        var val = $(this).val();
        if(val != null && val.length > 0 && !ItemList.checkSkuId(val)){
            layer.msg("京东SKU只能为1-25位的数字", {skin: 'layer-ext-b2b layer-b2b-hui'});
            var n_val = val.substring(0, val.length - 1);
            $(this).val(n_val);
        }
    });
    $("#checkAll").click(function(){
        $("[name=items]:checkbox").attr("checked",this.checked);
    });
    $("#delItems").on("click",function(){
        var count =0;
        var value = new Array();
        $("[name=items]:checkbox").each(function(){
            if(this.checked){
                count++;
                var param={
                    itemId :$(this).attr("itemId"),
                    jdSkuId : $(this).attr("jdSkuId")
                };
                value.push(param);
            }
        });
        if(count==0){
            layer.msg("请先选择商品",{skin: 'layer-ext-b2b layer-b2b-hui'});
            return;
        }
        layer.confirm("您确认要删除"+count+"个商品吗？", {
            title: '删除提示',
            icon: 0,
            btn: ['确  定', '取  消']
        }, function (index, layero) {
            ItemList.delItemLists(value);
        }, function (index) {
        });
    });
    $("a[name='delete']").on("click", function(){

    });
});
window.onload = function(){
    ItemList.searchItemList(1);
    ItemList.queryCategorys(-1,"#J_first_category");
    ItemList.queryBrandList();
};
var ItemList = {
    /**
     * 条件查询商品列表
     * @param page
     */
    searchItemList: function (page) {
        page = page < 1 ? 1 : page;
        pageSize = 10;
        var brandId = $.trim($("#brand").val()); //品牌
        var skuName = $.trim($("#skuName").val()); //商品名称
        var skuId = $.trim($("#skuId").val()); //商品id
        var jdSkuId = $.trim($("#jdSkuId").val()); //商品id
        var itemStatus = $.trim($("#itemStatus").val()); //商品状态
        var firstCategory = $.trim($("#J_first_category").val()); //一级类目
        var secondCategory = $.trim($("#J_second_category").val()); //二级类目
        var thirdCategory = $.trim($("#J_third_category").val()); //三级类目
        var category = "";
        if (ItemList.checkStrIsNull(firstCategory)) {
            category = category + firstCategory;
        }
        if (ItemList.checkStrIsNull(secondCategory)) {
            if (ItemList.checkStrIsNull(category)) {
                category = category + "," + secondCategory;
            } else {
                category = category + secondCategory;
            }
        }
        if (ItemList.checkStrIsNull(thirdCategory)) {
            if (ItemList.checkStrIsNull(category)) {
                category = category + "," + thirdCategory;
            } else {
                category = category + thirdCategory;
            }
        }
        var data = {
            page: page,
            skuId: skuId,
            jdSkuId: jdSkuId,
            skuName: skuName,
            category: category,
            brandid: brandId,
            status: itemStatus,
            size: pageSize
        };
        tool.ajax({
            url: "/item/searchItemList",
            type: "post",
            data: data,
            dataType: "json",
            success: function (data) {
                if (data && data.isSuccess) {
                    $("#itemListTable tbody").html("");
                    var tr = "<tr><th width=\"30%\" align=\"left\" class=\"p_20\">商品信息</th><th width=\"13%\" align=\"left\">品牌</th><th width=\"13%\" align=\"left\">京东后台价</th><th width=\"13%\" align=\"left\">创建时间</th>";
                    tr += "<th width=\"13%\" align=\"left\">状态</th> <th align=\"left\">操作</th></tr>";
                    $("#itemListTable tbody").append(tr);
                    $("#itemListTable tbody")
                    if (data.list && data.list.length > 0) {
                        var list = data.list;
                        for (var o in list) {
                            var skuId = list[o].skuId;
                            var jdSkuId = list[o].jdSkuId;
                            var skuName = list[o].skuName;
                            var category = list[o].category;
                            var status = list[o].status;
                            var brandName = list[o].brandName;
                            if (status == 0) {
                                status = "待审核";
                            } else if (status == 1) {
                                status = "待上架";
                            } else if (status == 2) {
                                status = "在售";
                            } else if (status == 3) {
                                status = "已下架";
                            } else if (status == 4) {
                                status = "未发布";
                            } else if (status == 5) {
                                status = "审核未通过";
                            }

                            var price ;
                            var marketPrice ;
                            var create = list[o].created;
                            var createdMilliseconds = parseInt(create);
                            var createDate = new Date(createdMilliseconds);
                            var mainPictureUrl = list[o].mainPictureUrl;
                            var html = "";
                            html += "<tr><td colspan=\"6\" class=\"spid p_20\"><div><input type='checkbox' name='items' itemId='" + skuId + "' jdSkuId='" + jdSkuId + "'onclick='ItemList.check()' />商品ID：" + skuId + "&nbsp;&nbsp;京东skuId：" + jdSkuId+ "&nbsp;&nbsp;商品类目:" + category + "</div></td></tr>";
                            var img_url = "";
                            if(mainPictureUrl != null && mainPictureUrl.length > 0){
                                img_url = imageFormat_sys(mainPictureUrl,59,59);
                            }
                            html += "<tr><td width='30%'><img src='" + img_url + "' height='59' width='59'>" + skuName + "</td>";
                            html += "<td width='15%'>" + brandName + "</td>";
                            if(list[o].price != null){
                                price = list[o].price;
                                var n_price = Number(price);
                                if(n_price <= 0){
                                    price = "--";
                                }
                            }else{
                                price = "--";
                            }
                            if(list[o].marketPrice != null){
                                marketPrice = list[o].marketPrice;
                                var n_mprice = Number(marketPrice);
                                if(n_mprice <= 0){
                                    marketPrice = "--";
                                }
                            }else{
                                marketPrice = "--";
                            }

                            html += "<td width='13%'><span>销售价:</span><i>￥" + price + "</i><br /><span>市场价:</span><i>￥" + marketPrice + "</i></td>";
                            html += "<td width='13%'>" + DateFormat.prototype.format(createDate, "yyyy-MM-dd HH:mm:ss") + "</td><td width='10%' class='yellow'>" + status + "</td><td ><a href='JavaScript:void(0)' name='delete' onclick='ItemList.delete("+skuId+","+jdSkuId+")'>删除</a> | <a href=\"JavaScript:void(0)\" name='unBindSku' onclick='ItemList.unBindItem("+jdSkuId+")'>解除关联</a></td>";
                            //html += "<td></td>";
                            html += "</tr>";
                            $("#itemListTable tbody").append(html);
                        }
                        var count = data.totalCount;
                        var totalPage = data.totalPages;
                        pageUtil.paginateSet(page, totalPage, "#itemListPage", function (currentPage) {
                            ItemList.searchItemList(currentPage);
                        }, []);
                        pageUtil.show("#itemListPage");
                    }else{
                        var empty_html = "<tr><td colspan='5' align='center'>没有符合条件的查询记录</td></tr>";
                        $("#itemListTable tbody").append(empty_html);
                        $("#itemListPage").html("");
                    }
                } else {
                    layer.msg("查询失败", {skin: 'layer-ext-b2b layer-b2b-hui'});
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                // 请求未发送就离开本页面
                if (XMLHttpRequest.readyState == 0 && textStatus == "error") {
                    return;
                }
                layer.msg("查询失败",{skin: 'layer-ext-b2b layer-b2b-hui'});
            }
        });
    },
    checkStrIsNull: function (str) {
        if (str != null && str != "") {
            return true;
        } else {
            return false;
        }
    },
    queryCategorys: function (parentId, showId) {
        if(parentId == null || parentId == ""){
            if(showId == "#J_second_category"){
                $(showId).html("");
                $(showId).html("<option value =''>二级类目</option>");
            }
            return;
        }
        tool.ajax({
            type: "post",
            url: "/platcategory/queryCategoryList",
            data: {
                parentId: parentId
            },
            dataType: "json",
            success: function (data) {
                if (data && data.isSuccess) {
                    var categorys = data.categorys;
                    if (categorys && categorys.length > 0) {
                        $(showId).html("");
                        if(showId == "#J_first_category"){
                            $(showId).append("<option value =''>一级类目</option>");
                        }else if(showId == "#J_second_category"){
                            $(showId).append("<option value =''>二级类目</option>");
                        }else if(showId == "#J_third_category"){
                            $(showId).append("<option value =''>三级类目</option>");
                        }
                        for(var i=0;i<categorys.length;i++){
                            var category = categorys[i];
                            var html = "<option value =" + category.cid + ">" + category.categoryName + "</option>";
                            $(showId).append(html);
                        }
                    }
                }
            }
        });
    },
    queryBrandList:function(){
        tool.ajax({
            type: "post",
            url: "/item/searchBrandList",
            data: {
            },
            dataType: "json",
            success: function (data) {
                if (data && data.isSuccess) {
                    var brandVos = data.brandVos;
                    if (brandVos && brandVos.length > 0) {
                        $("#brand").html("");
                        $("#brand").append("<option value=''>请选择</option>");
                        for(var o in  brandVos){
                            var brandVo = brandVos[o];
                            var html = "<option value =" + brandVo.brandId + ">" + brandVo.brandName + "</option>";
                            $("#brand").append(html);
                        }
                    }
                }
            }
        });
    },
    /**
     * 验证sku格式
     * @param val
     * @returns {boolean}
     */
    checkSkuId:function(val){
        var reg = /^\d{1,25}$/;
        var ret = reg.test(val);
        if(!ret){
            return false;
        }else{
            return true;
        }
    },
    //删除商品
    delItemLists :function(value){
        tool.ajax({
            type: "post",
            url: "/item/deleteItemLists",
            data: {
                itemList:JSON.stringify(value)
            },
            dataType: "json",
            success: function (data) {
                if (data && data.isSuccess) {
                    layer.msg('删除商品成功。', {skin: 'layer-ext-b2b layer-b2b-hui'});
                    window.location.reload(true);
                }else{
                    layer.msg(data.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                }
            },
            error: function(data){
                layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
            }
        });
    },
    check:function(){
        var flag=true;
        $("[name=items]:checkbox").each(function(){
            if(!this.checked){
                flag=false;
            }
        });
        $("#checkAll").attr("checked",flag);
    },
    delete:function(itemId,jdSkuId){
        layer.confirm("您确认要删除该商品吗？", {
            title: '删除提示',
            icon: 0,
            btn: ['确  定', '取  消']
        }, function (index, layero) {
            var value = new Array();
            var param={
                itemId :itemId,
                jdSkuId : jdSkuId
            };
            value.push(param);
            ItemList.delItemLists(value);
        }, function (index) {
        });
    },
    //解除关联经营类目
    unBindItem:function(jdSkuId){
        tool.ajax({
            type: "post",
            url: "/item/unBindSku",
            data: {
                skuIds:jdSkuId
            },
            dataType: "json",
            success: function (data) {
                if (data && data.isSuccess) {
                    layer.msg('解除关联成功', {skin: 'layer-ext-b2b layer-b2b-hui'});
                    window.location.reload(true);
                }else{
                    layer.msg(data.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                }
            },
            error: function(data){
                layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
            }
        });
    }

};
Date.prototype.aFormat = function(fmt) { //author: meizz
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
};
