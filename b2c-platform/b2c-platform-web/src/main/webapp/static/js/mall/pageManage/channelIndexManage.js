/**
 * Created by YueZheng on 2017/2/28.
 */
/**
 * Created by YueZheng on 2017/2/24.
 */
$(function(){
    //收缩展开效果
    $(".list_tit h2").click(function() {
        $(this).next(".list_menu").slideToggle(300);
    });
    $('.list_tit h2').click(function() {
        $(this).children('.angle-down').toggleClass('hidden');
        $(this).children('.angle-up').toggleClass('hidden');
    });
    $(".popnameclose").click(function(){
        $(".bgpop").css("display","none");
        $(".pop").css("display","none");
    });
    $(".popcancle").click(function(){
        $(".bgpop").css("display","none");
        $(".pop").css("display","none");
    });
    //pop显示隐藏
    $(".dr_btn").click(function(){
        $(".bgpop").css("display","block");
    });
    window.pop2 = $(".carousel").css("margin-top");
    $(".btn2").click(function(){
        openPop($(".carousel"),"新建轮播图",bannerVali, pop2);
    });
    window.pop3 = $(".recommend").css("margin-top");
    $(".btn3").click(function(){
        openPop($(".recommend"),"新建推荐位",adBannerVali, pop3);
    });
    window.pop4 = $(".floor").css("margin-top");
    $(".btn4").click(function(){
        openPop($(".floor"),"新建楼层",floorVali, pop4);
    });

    //新建banner
    var bannerVali = $("#bannerForm").validate({
        rules:{
            name:{required:true,isEmpty:true,maxlength:50},
            sortNum:{required:true,digits:true,min:1},
            imgSrc:{required:true},
            link:{required:true,isUrl:true},
        },
        messages:{
            name:{required:"标题不能为空",isEmpty:"请填写正确的字符",maxlength:"不能超过50个中文"},
            sortNum:{required:"序号不能为空",digits:"请输入大于0的数字",min:"请输入大于0的数字"},
            imgSrc:{required:"请上传图片"},
            link:{required:"图片链接链接不能为空",isUrl:"请输入正确的URL地址"},
        },
        submitHandler:function (form){
            $("[type='submit']").attr("disabled",true);
            var id = $(form).find("[name='id']").val();
            if (id==""||id==null){
                if($("[name='bannerNum']").length>=6){
                    layer.alert("只能添加6条轮播图！");
                } else {
                    addAndUpd("/mall/mallBanner/addBanner",$(form));
                }
            } else {
                addAndUpd("/mall/mallBanner/updateBanner",$(form));
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.next());
        },
        ignore: [],
    });
    //新建adBanner
    var adBannerVali = $("#adBannerForm").validate({
        rules:{
            name:{required:true,isEmpty:true,maxlength:50},
            sortNum:{required:true,digits:true,min:1},
            imgSrc:{required:true},
            link:{required:true,isUrl:true},
        },
        messages:{
            name:{required:"标题不能为空",maxlength:"不能超过50个中文",isEmpty:"请填写正确的字符"},
            sortNum:{required:"序号不能为空",digits:"请输入大于0的数字",min:"请输入大于0的数字"},
            imgSrc:{required:"请上传图片"},
            link:{required:"图片链接链接不能为空",isUrl:"请输入正确的URL地址"},
        },
        submitHandler:function (form){
            $("[type='submit']").attr("disabled",true);
            var id = $(form).find("[name='id']").val();
            if (id==""||id==null){
                if($("[name='adBannerNum']").length>=7){
                    layer.alert("只能添加7个推荐位！");
                } else {
                    addAndUpd("/mall/mallBanner/addBanner",$(form));
                }
            } else {
                addAndUpd("/mall/mallBanner/updateBanner",$(form));
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.next());
        },
        ignore: [],
    });

    //新建楼层
    var floorVali = $("#floorForm").validate({
        rules:{
            name:{required:true,maxlength:8,isEmpty:true},
            shortName:{required:true,maxlength:2,isEmpty:true},
            sortNum:{required:true,digits:true,min:1},
            subName:{isEmpty:true,maxlength:16},
        },
        messages:{
            name:{required:"标题不能为空",maxlength:"不能超过8个中文",isEmpty:"请填写正确的字符"},
            shortName:{required:"楼层短标题不能为空",maxlength:"不能超过2个中文",isEmpty:"请填写正确的字符"},
            sortNum:{required:"序号不能为空",digits:"请输入大于0的数字",min:"请输入大于0的数字"},
            subName:{maxlength:"不能超过16个中文",isEmpty:"请填写正确的字符"},
        },
        submitHandler:function (form){
            $("[type='submit']").attr("disabled",true);
            var id = $(form).find("[name='id']").val();
            if (id==""||id==null) {
                addAndUpd("/mall/mallFloor/addFloor",$(form));
            }else{
                addAndUpd("/mall/mallFloor/updateFloor",$(form));
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.next());
        },
        ignore: [],
    });

    //上传轮播图，广告推荐位，吸顶广告图片
    $("div.fileaddv").on("click",function(){
        $("input[name=fileImage]").trigger("click");
    });
    $("input[name=fileImage]").imgUpload(function(obj){
        var parent = undefined;
        $(".popend").each(function () {
            if($(this).css("display")=="block"){
                parent = $(this);
            }
        });
        parent.find("[name='imgSrc']").val(obj.fileUrl);
        parent.find("[name='imgView']").attr("src",obj.fileUrl);
        parent.find("[name='imgView']").css("display","block");
    });
});
function openPop(parent, title, validate, marginTop){
    var scrollhei = $(document).scrollTop();
    var martop = parseInt(marginTop.substring(0,marginTop.indexOf("p")));
    parent.css("margin-top",martop+scrollhei);
    parent.find(".popname").text(title);
    //parent.find("form")[0].reset();
    validate.resetForm();
    var imgView = parent.find("[name='imgView']");
    if(imgView!=undefined) {imgView.attr("src","");}
    parent.css("display","block");
}

function addAndUpd(url,form){
    $.post(url,form.serializeArray(),function (obj) {
        if (!obj){
            layer.alert("dev:操作失败");
            location.reload(true);
        } else {
            location.reload(true);
        }
    },"json");
}

function del(url,id, tips) {
    layer.confirm(tips || '确定删除？', {
        btn: ['确定','取消']
    }, function(){
        $.post(url,{id:id},function(obj){
            if (obj){
                location.reload(true);
            } else {
                layer.alert("dev:删除失败");
                location.reload(true);
            }
        },"json");
    }, function(){
    });
}

function deleteBanner(id){
    if($("[name='bannerNum']").length == 1){
        layer.alert("最少有1条轮播图！");
    }else {
        del("/mall/mallBanner/deleteBanner",id)
    }
}

function deleteFloor(id){
    del("/mall/mallFloor/deleteFloor",id, "该楼层下设置的推荐商品和广告将与该楼层解除关联，确认删除？")
}

function openUpdPop(parent, title, marginTop){
    var scrollhei = $(document).scrollTop();
    var martop = parseInt(marginTop.substring(0,marginTop.indexOf("p")));
    parent.css("margin-top",martop+scrollhei);
    parent.find(".popname").text(title);
    parent.css("display","block");
    parent.find(".errorMessage").text("");
    $(".bgpop").css("display","block");
}

function toUpdateBanner(id,bannerType) {
    $.post("/mall/mallBanner/getBanner",{id:id},function(obj){
        if (obj!=null){
            var parent = undefined;
            var pop = undefined;
            var title = undefined;
            if (obj.bannerType==1){
                parent = $("#bannerForm");
                pop = $(".carousel");
                title = "编辑轮播图";
            } else if (obj.bannerType==2){
                parent = $("#adBannerForm");
                pop = $(".recommend");
                title = "编辑推荐位";
            }
            parent.find("[name='id']").val(obj.id);
            parent.find("[name='name']").val(obj.name);
            parent.find("[name='sortNum']").val(obj.sortNum);
            parent.find("[name='link']").val(obj.link);
            parent.find("[name='imgSrc']").val(obj.imgSrc);
            parent.find("[name='imgView']").css("display","block");
            parent.find("[name='imgView']").attr("src",obj.imgSrc);
            openUpdPop(pop,title,pop2);
        } else {
            layer.alert("获取失败");
        }
    },"json");
}

function toUpdateFloor(id) {
    $.post("/mall/mallFloor/getFloor",{id:id},function (obj) {
        if (obj!=null){
            var parent = $("#floorForm");
            var pop = $(".floor");
            parent.find("[name='id']").val(id);
            parent.find("[name='name']").val(obj.name);
            parent.find("[name='shortName']").val(obj.shortName);
            parent.find("[name='subName']").val(obj.subName);
            parent.find("[name='sortNum']").val(obj.sortNum);
            openUpdPop(pop,"编辑楼层",pop4);
        } else {
            layer.alert("获取失败");
        }
    },"json");
}


function viewChannelIndexPage(obj) {
    $(obj).data("flag", "1");
    var code = $(obj).data("code");
    window.open('/mall/mallChannel/viewIndexPage?code=' + code);
    $("button[name='createIndex']").removeAttr("disabled");
    $("button[name='createIndex']").css("cursor", "");
    $("button[name='createIndex']").unbind("click").on("click", function () {
        var code = $(this).data("code");
        createChannelIndexPage(code);
    });
}

function createChannelIndexPage(code) {
    var flag = $("button[name='viewIndex']").data("flag");
    if (flag == "0") {
        layer.alert("请先预览首页，确保无误后发布");
        return;
    }
    $("button[name='createIndex']").attr("disabled", true);
    $.ajax({
        url: "/mall/mallChannel/createIndexPage" + "?time=" + (new Date()).getTime(),
        cache: false,
        dataType: "json",
        type: "POST",
        data:{code: code},
        success: function(data){
            if (data.success == 1) {
                layer.alert("发布成功");
            } else {
                layer.alert("发布失败");
            }
            $("button[name='createIndex']").attr("disabled", false);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            layer.alert("服务端异常:" + textStatus);
            $("button[name='createIndex']").attr("disabled", false);
        }
    });
}


