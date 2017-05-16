$(function() {
    /**绑定select的change事件*/
    feedback.searchList(1);
    $("#search").on("click", function () {
        feedback.searchList(1);
    });
    $("#reset").on("click", function () {
        $("#mobile").val('');
    });
});

var feedback = {

    /**
     * 查询列表 - in use
     */
    searchList : function(page){
        page = page < 1 ? 1 : page;
        pageSize = 10;
        var mobile = $("#mobile").val();
        // 检测手机号格式是否合法
        //if (!feedback.checkMobile(mobile)) {
        //    return;
        //}
        $.ajax({
            url : "/mall/feedback/query",
            type : "post",
            data : {
                contact:mobile,
                pageIndex: page,
                pageSize: pageSize
            },
            dataType: "json",
            success: function(data){
                if(data && data.isSuccess){
                    $("#feedbackListTable tbody").html("");
                    if (data.mallFeedbackList && data.mallFeedbackList.length > 0) {
                        var list = data.mallFeedbackList;
                        var index = 1;
                        for (var i in list) {
                            var createdMilliseconds = parseInt(list[i].created);
                            var createDate = new Date(createdMilliseconds);
                            var html = "";
                            html +="<tr><td width='5%'>"+ index +"</td>";
                            html +="<td width='20%'>"+ list[i].contact +"</td>";
                            html +="<td width='45%'>"+ list[i].content +"</td>";
                            html +="<td width='20%'>"+ DateFormat.prototype.format(createDate, "yyyy-MM-dd HH:mm:ss") +"</td>";
                            html +="<td width='10%'>"+ "" +"</td>";
                            html +="</tr>";
                            $("#feedbackListTable tbody").append(html);
                            index++;
                        }
                        var count = data.totalCount;
                        var totalPage = data.totalPages;
                        pageUtil.paginateSet(page, totalPage, "#feedbackListPage", function (currentPage) {
                            feedback.searchList(currentPage);
                        }, []);
                        pageUtil.show("#feedbackListPage");
                    }else{
                        var empty_html = "<tr><td colspan='7' align='center'>没有符合条件的查询记录</td></tr>";
                        $("#tbodyList").html(empty_html);
                        $("#feedbackListPage").html("");
                    }
                }
            },
            error: function(data){
                layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
            }
        });
    },

    // 手机号码验证
    checkMobile: function(value) {
        if(!objCheckUtil.isNotEmpty(value)){
            return false;
        }
        var length = value.length;
        var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
        return  (length == 11 && mobile.test(value));
    }
};