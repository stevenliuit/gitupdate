/**
 * Created by YueZheng on 2017/3/1.
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
    window.pop1 = $(".pindao").css("margin-top");
    $(".btn1").click(function(){
        openPop($(".pindao"),"新建频道",channelVali, pop1);
    });
    window.pop2 = $(".carousel").css("margin-top");
    $(".btn2").click(function(){
        openPop($(".carousel"),"新建轮播图",bannerlVali, pop2);
    });
    window.pop3 = $(".special").css("margin-top");
    $(".btn3").click(function(){
        openPop($(".special"),"新建专题",specialVali, pop3);
    });
    window.pop4 = $(".recommend").css("margin-top");
    $(".btn4").click(function(){
        $("#adBannerForm [name='bannerType']").val(4);
        openPop($(".recommend"),"新建快捷入口",adBannerVali, pop4);
    });
    window.pop5 = $(".topnews").css("margin-top");
    $(".btn5").click(function(){
        //$("#adBannerForm [name='bannerType']").val(4);
        openPop($(".topnews"),"新建头条",topnewsVali, pop5);
    });
    window.pop6 = $(".recommend").css("margin-top");
    $(".btn6").click(function(){
        if($("[name='tuijianweiNum']").length>=8){
            $(".bgpop").css("display","none");
            layer.alert("只能添加8个推荐位！");
        }else {
        $("#adBannerForm [name='bannerType']").val(2);
        openPop($(".recommend"),"新建推荐位",adBannerVali, pop6);
        }
    });
    window.pop7 = $(".recommend").css("margin-top");
    $(".btn7").click(function(){
        if($("[name='adPositionIdNum']").length>=4){
            $(".bgpop").css("display","none");
            layer.alert("只能添加4个广告位！");
        }else {
            $("#adBannerForm [name='bannerType']").val(6);
            openPop($(".recommend"),"新建广告位",adBannerVali, pop7);
        }
    });



    var channelVali = $("#channelForm").validate({
        rules:{
            name:{required:true,maxlength:6,isEmpty:true},
            sortNum:{required:true,digits:true,min:1},
            link:{required:true,isUrl:true},
            homeCode:{isUrl:true},
        },
        messages:{
            name:{required:"频道标题不能为空",maxlength:"不能超过6个中文",isEmpty:"请填写正确的字符"},
            sortNum:{required:"频道序号不能为空",digits:"请输入大于0的数字",min:"请输入大于0的数字"},
            link:{required:"频道链接不能为空",isUrl:"请输入正确的URL地址"},
            homeCode:{isUrl:"请输入正确的URL地址"},
        },
        submitHandler:function (form){
            $("[type='submit']").attr("disabled",true);
            var id = $(form).find("[name='id']").val();
            if (id==""||id==null){
                addAndUpd("/mall/mallChannel/addChannel",$(form));
            } else {
                addAndUpd("/mall/mallChannel/updateChannel",$(form));
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.next());
        },
    });

    var bannerlVali = $("#bannerForm").validate({
        rules:{
            name:{required:true,maxlength:50,isEmpty:true},
            imgSrc:{required:true},
            sortNum:{required:true,digits:true,min:1},
            link:{required:true,isUrl:true},
        },
        messages:{
            name:{required:"请填入轮播图标题",maxlength:"不能超过50个中文",isEmpty:"请填写正确的字符"},
            imgSrc:{required:"请上传图片"},
            sortNum:{required:"轮播图序号不能为空",digits:"请输入大于0的数字",min:"请输入大于0的数字"},
            link:{required:"图片链接不能为空",isUrl:"请输入正确的链接地址"},
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
    //快捷入口等验证 推荐位 广告位
    var adBannerVali = $("#adBannerForm").validate({
        rules:{
            name:{required:true,maxlength:50,isEmpty:true},
            imgSrc:{required:true},
            sortNum:{required:true,digits:true,min:1},
            link:{required:true,isUrl:true},
        },
        messages:{
            name:{required:"请填入标题",maxlength:"不能超过50个中文",isEmpty:"请填写正确的字符"},
            imgSrc:{required:"请上传图片"},
            sortNum:{required:"序号不能为空",digits:"请输入大于0的数字",min:"请输入大于0的数字"},
            link:{required:"链接不能为空",isUrl:"请输入正确的链接地址"},
        },
        submitHandler:function (form){
            $("[type='submit']").attr("disabled",true);
            var id = $(form).find("[name='id']").val();
            if (id==""||id==null){
                addAndUpd("/mall/mallBanner/addBanner",$(form));
            } else {
                addAndUpd("/mall/mallBanner/updateBanner",$(form));
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.next());
        },
        ignore: [],
    });
    //创建专题验证
    var specialVali = $("#specialForm").validate({
        rules:{
            "mallFloor.name":{required:true,maxlength:50,isEmpty:true},
            "mallAd.imgSrc":{required:true},
            sortNum:{required:true,digits:true},
            //"mallAd.link":{required:true,isUrl:true}
            "mallAd.link":{isUrl:true},
        },
        messages:{
            "mallFloor.name":{required:"请填入标题",maxlength:"不能超过50个中文",isEmpty:"请填写正确的字符"},
            "mallAd.imgSrc":{required:"请上传图片"},
            sortNum:{required:"序号不能为空",digits:"请输入数字"},
            //"mallAd.link":{required:"链接不能为空",isUrl:"请输入正确的链接地址"}
            "mallAd.link":{isUrl:"请输入正确的链接地址"}
        },
        submitHandler:function (form){
            $("[type='submit']").attr("disabled",true);
            var id = $(form).find("[name='id']").val();
            if (id==""||id==null){
                addAndUpd("/mall/mallSpecial/add",$(form));
            } else {
                addAndUpd("/mall/mallSpecial/update",$(form));
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.next());
        },
        ignore: [],
    });

    //头条验证
    var topnewsVali = $("#topnewsForm").validate({
        rules:{
            title:{required:true,maxlength:50,isEmpty:true},
            sortNum:{required:true,digits:true,min:1},
            articleId:{required:true,digits:true},
            articleUrl:{required:true,isUrl:true},
        },
        messages:{
            title:{required:"请填入标题",maxlength:"不能超过50个中文",isEmpty:"请填写正确的字符"},
            sortNum:{required:"序号不能为空",digits:"请输入大于0的数字",min:"请输入大于0的数字"},
            articleId:{required:"不能为空",digits:"请输入数字"},
            articleUrl:{required:"链接不能为空",isUrl:"请输入正确的链接地址"},
        },
        submitHandler:function (form){
            $("[type='submit']").attr("disabled",true);
            var id = $(form).find("[name='id']").val();
            if (id==""||id==null){
                addAndUpd("/mall/mallTopnewsArticle/addTopnews",$(form));
            } else {
                addAndUpd("/mall/mallTopnewsArticle/updateTopnews",$(form));
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.next());
        },
        ignore: [],
    });
    //上传轮播图，广告推荐位图片
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
        parent.find("[name='mallAd.imgSrc']").val(obj.fileUrl);
        parent.find("[name='imgView']").attr("src",obj.fileUrl);
        parent.find("[name='imgView']").css("display","block");
    });

    $("[name='articleType']").change(function(){
        var redioVal = $("[name='articleType']:checked").val()
        if (redioVal==1){
            showArticleInput("articleId","请输入文章ID");
        } else if(redioVal==2){
            showArticleInput("articleUrl","请输入文章URL");
        }
    });
});
function openPop(parent,title,validate, marginTop){
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
function deleteChannel(id) {
    del("/mall/mallChannel/deleteChannel",id, "该频道下设置的推荐商品将与该频道解除关联，确认删除？");
}

function deleteBanner(id){
    if($("[name='bannerNum']").length == 1){
        layer.alert("最少有1条轮播图！");
    }else {
    del("/mall/mallBanner/deleteBanner",id)
    }
}
function deleteTuijianwei(id) {
    if($("[name='tuijianweiNum']").length == 1){
        layer.alert("最少有1个推荐位！");
    }else {
        del("/mall/mallBanner/deleteBanner",id)
    }
}

function deleteAdPositionId(id) {
    //if($("[name='adPositionIdNum']").length == 1){
        //layer.alert("最少有1个广告位！");
    //}else {
        del("/mall/mallBanner/deleteBanner",id)
    //}
}

function deleteAdBanner(id){
    if($("[name='speedyEntranceNum']").length == 1){
        layer.alert("最少有1个快捷入口！");
    }else {
        del("/mall/mallBanner/deleteBanner",id)
    }
}

function deleteTopnews(id){
    del("/mall/mallTopnewsArticle/deleteTopnews",id)
}

function deleteSpecial(id,adId,floorId){
    layer.confirm('确定删除？', {
        btn: ['确定','取消']
    }, function(){
        $.post("/mall/mallSpecial/delete",{id:id,adId:adId,floorId:floorId},function(obj){
            if(obj){
                location.reload(true);
            }else{
                layer.alert("dev:删除失败");
                location.reload(true);
            }
        },"json");
    }, function(){
    });
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

function toUpdateSpecial(id){
    $.post("/mall/mallSpecial/get",{id:id,adType:4,floorType:4,clientType:3,channelId:0},function(obj){
        if (obj!=null){
            var parent = $("#specialForm");
            var pop = $(".special");
            parent.find("[name='id']").val(obj.id);
            parent.find("[name='adId']").val(obj.adId);
            parent.find("[name='floorId']").val(obj.floorId);
            parent.find("[name='mallFloor.name']").val(obj.mallFloor.name);
            parent.find("[name='mallAd.link']").val(obj.mallAd.link);
            parent.find("[name='mallAd.imgSrc']").val(obj.mallAd.imgSrc);
            parent.find("[name='imgView']").attr("src",obj.mallAd.imgSrc);
            parent.find("[name='imgView']").css("display","block");
            parent.find("[name='sortNum']").val(obj.sortNum);
            openUpdPop(pop,"编辑专题",pop3);
        } else {
            layer.alert("获取失败");
        }
    },"json");
}

function toUpdateBanner(id,bannerType) {
    $.post("/mall/mallBanner/getBanner",{id:id},function(obj){
        if (obj!=null){
            var parent = undefined;
            var pop = undefined;
            var bType = obj.bannerType;
            var title = undefined;
            if (bType==1){
                parent = $("#bannerForm");
                pop = $(".carousel");
                pop.find(".popname").text("编辑轮播图");
            } else if (bType==2||bType==4||bType==5||bType==6){
                parent = $("#adBannerForm");
                pop = $(".recommend");
                if (bType==2){
                    title = "编辑推荐位";
                } else if (bType==4){
                    title = "编辑快捷入口";
                } else if (bType==6){
                    title = "编辑广告位";
                }
            }
            parent.find("[name='id']").val(obj.id);
            parent.find("[name='bannerType']").val(obj.bannerType);
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
function toUpdateChannel(id) {
    $.post("/mall/mallChannel/getChannel",{id:id},function(obj){
        if (obj!=null){
            var parent = $("#channelForm");
            parent.find("[name='id']").val(obj.id);
            parent.find("[name='name']").val(obj.name);
            parent.find("[name='sortNum']").val(obj.sortNum);
            parent.find("[name='link']").val(obj.link);
            if(obj.homeCode!="-1"){
                parent.find("[name='homeCode']").val(obj.homeCode);
            }
            openUpdPop($(".pindao"),"编辑频道",pop1);
        } else {
            layer.alert("获取失败");
        }
    },"json");
}
//头条回显
function toUpdateTopnews(id) {
    $.post("/mall/mallTopnewsArticle/getTopnews",{id:id},function(obj){
        if (obj!=null){
            var parent = $("#topnewsForm");
            parent.find("[name='id']").val(obj.id);
            parent.find("[name='title']").val(obj.title);
            parent.find("[name='sortNum']").val(obj.sortNum);
            if (obj.articleId==null||obj.articleId==0){
                parent.find("[name='articleType']:eq(1)").attr("checked","checked");
                showArticleInput("articleUrl","请输入文章URL");
                parent.find("[name='articleUrl']").val(obj.articleUrl);
            } else if (obj.articleUrl==null||obj.articleUrl==""){
                parent.find("[name='articleType']:eq(0)").attr("checked","checked");
                showArticleInput("articleId","请输入文章ID");
                parent.find("[name='articleId']").val(obj.articleId);
            }
            openUpdPop($(".topnews"),"编辑头条",pop5);
        } else {
            layer.alert("dev:获取失败");
            location.reload(true);
        }
    },"json");
}

function showArticleInput(name, placeholder){
    var input = $("#articleFrom");
    input.val("");
    input.attr("name",name);
    input.attr("placeholder",placeholder);
    input.removeAttr("readonly");
}



