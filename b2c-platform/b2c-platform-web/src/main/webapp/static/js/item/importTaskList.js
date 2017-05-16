
$(function() {
    /**绑定select的change事件 - in use*/
    importTaskList.searchList(1);
    $("#searchItemTaskList").on("click",function(){
        importTaskList.searchList(1);
    });
    $("#resetItemTaskList").on("click",function(){
        $("#select").val(2);
        $("#batchCode").val('');
        $("#skuId").val('');
        $("#calendar1").val('');
        $("#calendar2").val('');
        $("#select").val("1");
    });
    $('#calendar1').calendar();
    $('#calendar2').calendar();

    $(".down_btn").on("click",function(){
        importTaskList.reImportAllFailedTask();
    });
    /**重新导入 - in use*/
    $("#importTaskListTable").delegate("a[name='reImport']", "click", function(){
        var _that = $(this);
        layer.confirm('确认要重新导入吗？', {
            title: '导入提示',
            icon: 0,
            btn: ['确  定', '取  消']
        }, function (index, layero) {
            var vlue = _that.attr("taskId");
            importTaskList.reImportTask(vlue);
        }, function (index) {

        });
    });
    $("#skuId").on("keyup", function(){
        var val = $(this).val();
        if(val != null && val.length > 0 && !importTaskList.checkSkuId(val)){
            layer.msg("京东SKU只能为1-25位的数字", {skin: 'layer-ext-b2b layer-b2b-hui'});
            var n_val = val.substring(0, val.length - 1);
            $(this).val(n_val);
        }
    });
});
var importTaskList = {

    /**
     * 查询列表 - in use
     */
    searchList : function(page){
        page = page < 1 ? 1 : page;
        pageSize = 10;
        var status = $("#select").val();
        var batchCode = $("#batchCode").val();
        var skuId =  $("#skuId").val();
        var beginDate = $("#calendar1").val();
        var endDate = $("#calendar2").val();
        // 检测时间格式是否合法
        if (!importTaskList.checkDateTimeFormat(beginDate) || !importTaskList.checkDateTimeFormat(endDate)) {
            return;
        }

        // 补充查询时间，起始时间为：yyyy-MM-dd 00:00:00
        if (objCheckUtil.isNotEmpty(beginDate)) {
            beginDate = beginDate + " 00:00:00";
        }

        // 补充查询时间，结束时间为：yyyy-MM-dd 23:59:59
        if (objCheckUtil.isNotEmpty(endDate)) {
            endDate = endDate + " 23:59:59";
        }
        if (beginDate&&endDate&&beginDate>endDate) {
            layer.msg("结束时间不能小于开始时间", {skin: 'layer-ext-b2b layer-b2b-hui'});
            return false;
        }
        $.ajax({
            url : "/importTask/searchImportTaskList",
            type : "post",
            data : {
                skuId:skuId,
                batchCode:batchCode,
                beginDate:beginDate,
                endDate:endDate,
                status : status,
                page: page,
                size: pageSize
            },
            dataType: "json",
            success: function(data){
                if(data && data.isSuccess){
                    $("#importTaskListTable tbody").html("");
                    if (data.list && data.list.length > 0) {
                        var list = data.list;
                        var nuanber = 1;
                        for (var i in list) {
                            var taskId = list[i].id;
                            var skuId = list[i].skuId;
                            var status = list[i].status;
                            var reason = list[i].reason;
                            var batchCode = list[i].batchCode;
                            var beginDate = list[i].beginDate;
                            var unixTime = new Date(beginDate);
                            var flag_css = "fail_zt";
                            if (status == 1) {
                                status = "成功";
                                flag_css = "Success_zt";
                            } else if(status == 2){
                                status = "失败";
                            }else{
                                status = "未执行"
                            }
                            var html = "";
                            html +="<tr><td width='5%'>"+ nuanber +"</td>";
                            html +="<td width='10%'>"+ skuId +"</td>";
                            html +="<td width='15%'>"+ batchCode +"</td>";
                            html +="<td width='10%' class='" + flag_css + "'>"+ status +"</td>";
                            html +="<td width='20%'>"+ unixTime.toLocaleString() +"</td>";
                            html +="<td width='30%'>"+ reason +"</td>";
                                if (status == "成功") {
                                    html +="<td width='10%'> </td>";
                                } else {
                                    html +="<td width='10%'> <a href='javascript:void(0);' name='reImport' taskId='"+taskId+"'>重新导入</a> </td>";
                                }
                            html +="</tr>";
                            $("#importTaskListTable tbody").append(html);
                            nuanber++;
                        }
                        var count = data.totalCount;
                        var totalPage = data.totalPages;
                        pageUtil.paginateSet(page, totalPage, "#importTaskListPage", function (currentPage) {
                            importTaskList.searchList(currentPage);
                        }, []);
                        pageUtil.show("#importTaskListPage");
                    }else{
                        var empty_html = "<tr><td colspan='7' align='center'>没有符合条件的查询记录</td></tr>";
                        $("#thodyList").html(empty_html);
                        $("#importTaskListPage").html("");
                    }
                }
            },
            error: function(data){
                layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
            }
        });
    },
    checkDateTimeFormat:function(_val) {
        if(!objCheckUtil.isNotEmpty(_val)){
            return true;
        }
        var _reg = /^\d{4}-\d{2}-\d{2}$/;
        if (!_reg.test(_val))
        {
            layer.msg("时间格式错误，请按格式：2000-01-01，进行填写", {skin: 'layer-ext-b2b layer-b2b-hui'});
            return false;
        }
        return true;
    },
    reImportTask :function (val){
        $.ajax({
            url : "/importTask/reImportTask",
            type : "post",
            data : {
                id: val
            },
            dataType: "json",
            success: function(data){
                if(data) {
                    if (data.msg) {
                        layer.msg(data.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                    }
                    if (data && data.isSuccess) {
                        layer.msg('重新导入任务成功。', {skin: 'layer-ext-b2b layer-b2b-hui'});
                        window.location.reload(true);
                    }
                }
            },
            error: function(data){
                layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
            }
        });
    },
    reImportAllFailedTask : function(){
        $.ajax({
            url : "/importTask/reImportAllFailedTask",
            type : "post",
            data : {
            },
            dataType: "json",
            success: function(data){
                if(data) {
                    if (data.msg) {
                        layer.msg(data.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                    }
                    if (data && data.isSuccess) {
                        layer.msg('重新导入任务成功。', {skin: 'layer-ext-b2b layer-b2b-hui'});
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
    }
};
Date.prototype.toLocaleString = function() {
    return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate() + " " + this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds();
};