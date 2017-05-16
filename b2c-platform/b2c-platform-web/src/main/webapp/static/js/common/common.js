$.ajaxSetup({
    error : function(jqXHR, textStatus, errorThrown) {
        alert("服务器处理异常！");
    }
});
;(function(){
    var tool={
        ajax:function(params){
            var _success = params.success;
            params.success = function(data){
                _success(data);
            };
            var _error = params.error;
            if(_error){
                params.error = function(){
                    //console.log(arguments);
                    _error(arguments);
                };
            }
            $.ajax(params);
        }
    };

    window.tool = tool;
})();

/**
 * 根据数据显示模板
 * @param data 查询到的数据
 * @param templateId 模板ID
 * @param parentId 模板加载完成显示的位置
 */
function showDataByTempl(data, templateId, parentId){
    //加载模板数据
    var _html = tmpl(templateId, data);
    //显示到相应位置
    $(parentId).html(_html);

    $.getScript(staticModule + "b2b-seller/widget/common/common.js");
}

/**
 * 替换图片服务器图片的URL
 * url="jfs/t1/45/52430153/298522/f1faa01a/538429bdN260d118a.jpg"
 * 替换为 url="http://img20.360buyimg.com/test/s100x100_jfs/t1/45/52430153/298522/f1faa01a/538429bdN260d118a.jpg"
 * @param url
 * @param width
 * @param height
 * @returns {*}
 */
function imageFormat_sys(url, width, height){
    if(!url){
        console.error("参数异常！");
        return "";
    }
    if(!width || !height){
        console.error("未传入图片分辨率参数");
        return "";
    }
    var index = url.lastIndexOf(".");
    var type = url.substring(index*1 + 1, url.length);
    if(type == "gif" || type == "GIF"){
        console.error("图片格式错误");
        return "";
    }
    var protocol_header = "//";
    var JSS_SERVER_DOMAIN = "img11.360buyimg.com";
    var SPLITTER  = "/";
    var REGEX = "jfs";
    var FIRST = "s";
    var DEL = "x";
    var LAST = "_jfs";
    var ret_url = protocol_header + JSS_SERVER_DOMAIN + SPLITTER + "n1" + SPLITTER + url.replace(REGEX, FIRST + width + DEL + height + LAST);
    console.info("ret_url=" + ret_url);
    return ret_url;
}

/**
 * 获取js模版拼出来的html
 * @param data 查询到的数据
 * @param templateId 模板ID
 */
function getDataByTempl(data, templateId){
    //加载模板数据
    var _html = tmpl(templateId, data);
    return _html;
}

var arr = [];
/**
 * 显示加载提示
 */
function showDisplay(){
    //当请求超过1s时，在1s后显示遮罩层
    var _time = setTimeout(function(){
        $("#J_Mask_v5").show();
        // 解决有滚动条时遮罩层显示不完
//		$("#J_Mask_v5").css("height", browserUtil.getAreaVal().scrollHeight);
        $("#J_Move_div_warning").show();
    },1000);
    arr.push(_time);
}

/**
 * 关闭加载提示
 */
function closeDisplay(){
    //关闭加载时，需要将定时器清空，
    $.each(arr,function(i,n){
        clearTimeout(n);
    });
    arr = [];
    $("#J_Mask_v5").hide();
    $("#J_Move_div_warning").hide();
}

var opition = {
    currentPage: 1,
    totalPages: 1,
    alignment:'right',
    itemTexts: function (type, page, current) {
        switch (type) {
            case "first":
                return "首页";
            case "prev":
                return "上一页";
            case "next":
                return "下一页";
            case "last":
                return "末页";
            case "page":
                return page;
        }
    },
    tooltipTitles: function (type, page, current) {
        return "";
    },
    onPageChanged: function (e, oldPage,newPage) {
        //parentAlert("type:" + oldPage + ",Page:" + newPage);
    }
};

function hidePop() {
    $("#mask,#maskTop").fadeOut(function() {
        $(this).remove();
    });
}

/**根据code 获得下级城市*/
function queryCityInfo(areaCode,domId,template){
    top.showDisplay();
    tool.ajax({
        type : "post",
        async:false,
        url : domainUrl + "/shopinfo/queryCityInfo.html",
        data : {
            areaCode:areaCode
        },
        dataType: "json",
        success: function(data){
            top.closeDisplay();
            if(!data.data ){
                return;
            }
            //模板格式化
            showDataByTempl(data.data, template, domId);
        },
        error:function(data){
            top.closeDisplay();
            parentAlert("加载地区出错");
        }
    });
}

/**
 * 跳转到安全设置页面
 * @param _type
 * @return
 */
function toSecurityPage(_type){
    var fsId = $("#topMenuAccount").attr("funcid");
    var _url = domainUrl + "seller/bindCheck?type=" + _type + "&m=" + fsId;
    /*if (m != undefined && m != "") {
     _url = _url + "&m=" + m;
     }*/
    //parent.menu.toMenu("#J_securitySettingPage",_url,from_domId);
    location.href = _url;
}

function parentAlert(msg){
    layer.msg(msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
}

//tab标签效果
function setTab(name,cursel,n){
    for(i=1;i<=n;i++){
        if(i==cursel){
            $("#"+name+i).addClass("hover");
            $("#con-"+name+"-"+i).show();
        }else{
            $("#"+name+i).removeClass("hover");
            $("#con-"+name+"-"+i).hide();
        }
    }
}

function checkPermission(url){
    var flag = false;
    $.ajax({
        async: false,
        type : "POST",
        url : domainUrl + "subAccount/checkPermission",
        dataType : 'json',
        data :{
            url : url
        },
        success : function(data) {
            if(data && data.isSuccess){
                flag = true;
            }
        },
        error : function(){
        }
    });
    return flag;
}

var menu = {
    /**跳转到菜单页面*/
    toMenu:function(id,customUrl,from_domId){
        var menu = $(id);
        var toSortList = menu.parents(".s-sortList");
        toSortList.addClass("cur");
        menu.parent().addClass("hover");
        toSortList.siblings().find("li").removeClass("hover");
        if(customUrl != null && customUrl != "") {
            //$(".s-rightMain iframe").attr("src", customUrl);
            location.href = customUrl;
        }else {
            menu[0].click();
        }
    }
};