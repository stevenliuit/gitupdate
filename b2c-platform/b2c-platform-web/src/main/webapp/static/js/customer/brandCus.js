$(function(){
    brandCus.selectBrandCusList(1);
    $("#searchRelList").on("click",function(){
        brandCus.selectBrandCusList(1);
    });
    $("#brandCusListTable").delegate("a[name=updateRelState]","click",function(){
        var relId = $(this).parent().siblings("input").val();
        var status = $(this).attr("relstatus");
        var relType = 1;
        if(status == 1){
            relType = 0;
        }
        brandCus.updateRelState(relId,relType);
    })

})
var brandCus = {
    /**
     * 条件查询客服状态
     * @param page
     */
    selectBrandCusList:function(page){
        page = page < 1 ? 1 : page;
        pageSize = 10;
        var brandName = $.trim($("#brandName").val()); //品牌名称
        var relType = $.trim($("#status").val()); //商品状态
        var data = {
            page: page,
            brandName: brandName,
            relType: relType,
            size: pageSize
        };
        tool.ajax({
            url: "/item/selectRrandCusRelList",
            type: "post",
            data: data,
            dataType: "json",
            success: function (data) {
                if (data && data.isSuccess) {
                    $("#brandCusListTable tbody").html("");
                    var tr = "<tr><th width=\"40%\" align=\"left\" class=\"p_20\">品牌名称</th><th width=\"30%\" align=\"left\">状态</th><th align=\"left\">操作</th>";
                    $("#brandCusListTable tbody").append(tr);
                    if (data.list && data.list.length > 0) {
                        var list = data.list;
                        for (var o in list) {
                            var brandId = list[o].brandId;
                            var brandName = list[o].brandName;
                            var relStatus = list[o].relStatus;
                            var status = "";
                            var option = "";
                            if(relStatus == 1){
                                status = "启用";
                                option = "停用";
                            }else{
                                status = "停用";
                                option = "启用";
                            }
                            var html = "";
                            html += "<tr><input type='hidden' value='"+ brandId +"'><td width=\"40%\">" + brandName + "</td><td width=\"30%\">" + status + "</td>";
                            html += "<td><a href='JavaScript:void(0)'relStatus='"+ relStatus +"' name='updateRelState'>"+ option +"</a></td></tr>"
                            $("#brandCusListTable tbody").append(html);
                        }
                        var count = data.totalCount;
                        var totalPage = data.totalPages;
                        pageUtil.paginateSet(page, totalPage, "#bramdCusListPage", function (currentPage) {
                            brandCus.selectBrandCusList(currentPage);
                        }, []);
                        pageUtil.show("#bramdCusListPage");
                    }else{
                        var empty_html = "<tr><td colspan='5' align='center'>没有符合条件的查询记录</td></tr>";
                        $("#brandCusListTable tbody").append(empty_html);
                        $("#bramdCusListPage").html("");
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
    /**
     * 开启关闭客服
     * @param page
     */
    updateRelState:function(relId,relType){
        var data = {
            relId: relId,
            relType: relType,
            type: 1
        };
        tool.ajax({
            url: "/item/updateCusState",
            type: "post",
            data: data,
            dataType: "json",
            success: function (data) {
                if (data && data.isSuccess) {
                    layer.msg("操作成功", {skin: 'layer-ext-b2b layer-b2b-hui'});
                    var currentPage = $("input[name=current_page]").val();
                    brandCus.selectBrandCusList(currentPage);
                } else {
                    layer.msg("操作失败", {skin: 'layer-ext-b2b layer-b2b-hui'});
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                // 请求未发送就离开本页面
                if (XMLHttpRequest.readyState == 0 && textStatus == "error") {
                    return;
                }
                layer.msg("操作失败",{skin: 'layer-ext-b2b layer-b2b-hui'});
            }
        });
    }
}