/**
 * Created by YueZheng on 2017/3/6.
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
    window.pop1 = $(".carousel").css("margin-top");
    $(".btn1").click(function(){
        openPop($(".carousel"),"新建轮播图",bannerlVali, pop1);
    });
    window.pop2 = $(".recommend").css("margin-top");
    $(".btn2").click(function(){
        openPopTwo($(".recommend"),"新建推荐位",recommendVali, pop2);
    });
    window.pop3 = $(".fullRecommend").css("margin-top");
    $(".btn3").click(function(){
        openPop($(".fullRecommend"),"新建通栏推荐",fullRecommendVali, pop3);
    });
    window.pop4 = $(".floor").css("margin-top");
    $(".btn4").click(function(){
        openPop($(".floor"),"新建楼层",floorVali, pop4);
    });
    window.pop5 = $(".shortcut").css("margin-top");
    $(".btn5").click(function(){
        openPop($(".shortcut"),"新建快捷入口",shortcutVali, pop5);
    });
    window.pop6 = $(".topnews").css("margin-top");
    $(".btn6").click(function(){
        $("#topnewsForm [name='articleType']").eq(0).attr("checked","checked");
        $("#topnewsForm [name='articleId']").attr("placeholder","请输入文章ID")
        openPop($(".topnews"),"新建头条",topnewsVali, pop6);
    });

    $("a[name='getSkuInfo']").on("click", function () {
        var skuId = $("input[name='mallAd.itemId']").val().trim();
        if(skuId == "" || isNaN(skuId)){
            layer.alert("请输入SKU，不可输入字母或汉字")
        }else {
            $.ajax({
                url: "/mall/mallAd/getSkuInfo/" + skuId + "?time=" + (new Date()).getTime(),
                cache: false,
                dataType: "json",
                type: "POST",
                success: function (data) {
                    if (data.success == 1) {
                        $("input[name='firstname']").val(data.itemDetailVo.skuName);
                    } else if (data.success == 2) {
                        layer.alert("不存在该sku");
                    } else {
                        layer.alert("服务端异常");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    layer.alert("服务端异常:" + textStatus);
                }
            });
        }
    });
    //创建轮播图验证
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

    //编辑快捷入口验证
    var shortcutVali = $("#shortcutForm").validate({
        rules:{
            name:{required:true,maxlength:50,isEmpty:true},
            sortNum:{required:true,digits:true,min:1},
            imgSrc:{required:true},
            link:{required:true,isUrl:true},
        },
        messages:{
            name:{required:"请填入快捷入口标题",maxlength:"不能超过50个中文",isEmpty:"请填写正确的字符"},
            sortNum:{required:"快捷入口序号不能为空",digits:"请输入大于0的数字",min:"请输入大于0的数字"},
            imgSrc:{required:"请上传图片"},
            link:{required:"图片链接不能为空",isUrl:"请输入正确的链接地址"},
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

    //编辑头条验证
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
            articleId:{required:"不能为空",digits:"请填入数字"},
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

    //创建推荐位验证
    var recommendVali = $("#recommendForm").validate({
        rules:{
            firstname:{required:true,maxlength:12,isEmpty:true},
            lastname:{required:true,maxlength:10,isEmpty:true},
            "mallAd.itemId":{required:true,maxlength:50,digits:true,isEmpty:true},
            sortNum: {required: true, digits: true,min:1},
        },
        messages:{
            firstname:{required:"请填入推荐商品标题",maxlength:"不能超过12个中文",isEmpty:"请填写正确的字符"},
            lastname:{required:"请填入推荐商品副标题",maxlength:"不能超过10个中文",isEmpty:"请填写正确的字符"},
            "mallAd.itemId":{required:"请填入SKU",maxlength:"不能超过50个字符",digits:"请输入数字",isEmpty:"请填写正确的字符"},
            sortNum: {required: "序号不能为空", digits: "请输入大于0的数字",min:"请输入大于0的数字"},
        },
        submitHandler:function (form){
            $("[type='submit']").attr("disabled",true);
            var id = $("#recommendForm [name='id']").val();
            var firstname =  $("#recommendForm [name='firstname']").val();
            var lastname =  $("#recommendForm [name='lastname']").val();
            $("#recommendForm [name='mallAd.adWords']").val(firstname+";"+lastname);
            if (id==""||id==null){
                addAndUpdTwo("/mall/MallAdRecommend/add",$(form));
            } else {
                addAndUpdTwo("/mall/MallAdRecommend/update",$(form));
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.next());
        },
    });

    //创建通栏推荐位验证
    var fullRecommendVali = $("#fullRecommendForm").validate({
        rules:{
            "mallAd.itemId":{required:true,maxlength:50,digits:true,isEmpty:true},
            "mallAd.imgSrc":{required:true},
            sortNum: {required: true, digits: true,min:1},
        },
        messages:{
            "mallAd.itemId":{required:"请填入关联商品SKU",maxlength:"不能超过50个字符",digits:"请输入数字",isEmpty:"请填写正确的字符"},
            "mallAd.imgSrc":{required:"请上传图片"},
            sortNum: {required: "序号不能为空", digits: "请输入大于0的数字",min:"请输入大于0的数字"},
        },
        submitHandler:function (form){
            $("[type='submit']").attr("disabled",true);
            var id = $(form).find("[name='id']").val();
            if (id==""||id==null){
                addAndUpd("/mall/MallAdRecommend/add",$(form));
            } else {
                addAndUpd("/mall/MallAdRecommend/update",$(form));
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.next());
        },
        ignore: [],
    });

    //创建楼层验证
    var floorVali = $("#floorForm").validate({
        rules:{
            "mallFloor.name":{required:true,maxlength:6,isEmpty:true},
            "mallFloor.subName":{maxlength:50,isEmpty:true},
            "mallAd.imgSrc":{required:true},
            "mallAd.link":{required:true,isUrl:true},
            sortNum:{required:true,digits:true,min:1}
        },
        messages:{
            "mallFloor.name":{required:"请填入楼层标题",maxlength:"不能超过6个中文",isEmpty:"请填写正确的字符"},
            "mallFloor.subName":{maxlength:"不能超过50个中文",isEmpty:"请填写正确的字符"},
            "mallAd.imgSrc":{required:"请上传图片"},
            "mallAd.link":{required:"图片链接不能为空",isUrl:"请输入正确的链接地址"},
            sortNum:{required:"请填入楼层序号",digits:"请输入大于0的数字",min:"请输入大于0的数字"}
        },
        submitHandler:function (form){
            $("[type='submit']").attr("disabled",true);
            var id = $(form).find("[name='id']").val();
            if (id==""||id==null) {

                addAndUpd("/mall/mallSpecial/add",$(form));
            }else{
                addAndUpd("/mall/mallSpecial/update",$(form));
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
        parent.find("[name$='imgSrc']").val(obj.fileUrl);
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
    parent.find("form")[0].reset();
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
function openPopTwo(parent,title,validate, marginTop){
    var scrollhei = $(document).scrollTop();
    var martop = parseInt(marginTop.substring(0,marginTop.indexOf("p")));
    parent.css("margin-top",martop+scrollhei);
    parent.find(".popname").text(title);
    parent.find("form")[0].reset();
    validate.resetForm();
    var imgView = parent.find("[name='imgView']");
    if(imgView!=undefined) {imgView.attr("src","");}
    parent.css("display","block");
}
function addAndUpdTwo(url,formId){
    $.post(url,$(formId).serializeArray(),function (obj) {
        if (!obj){
            layer.alert("dev:操作失败");
            location.reload(true);
        } else {
            location.reload(true);
        }
    },"json");
}

function del(url,id) {
    layer.confirm('确定删除？', {
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
        del("/mall/mallBanner/deleteBanner", id);
    }
}
function deleteAdBanner(id){
        del("/mall/mallBanner/deleteBanner", id);
}

function deleteTopnews(id){
    del("/mall/mallTopnewsArticle/deleteTopnews",id);
}

function deleteFloor(id){
    del("/mall/mallFloor/deleteFloor",id);
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

function deleteMallAd(id) {
    if($("[name='recommendNum']").length == 1){
        layer.alert("最少有1个推荐位！");
    }else {
        del("/mall/MallAdRecommend/delete", id);
    }
}

function deleteMallAdFull(id) {
    if($("[name='fullNum']").length == 1){
        layer.alert("最少有1个通栏推荐位！");
    }else {
        del("/mall/MallAdRecommend/delete", id);
    }
}

function deleteMallAd(id) {
    if($("[name='recommendNum']").length == 1){
        layer.alert("最少有1个推荐位！");
    }else {
        del("/mall/MallAdRecommend/delete", id);
    }
}

function deleteMallAdFull(id) {
    if($("[name='fullNum']").length == 1){
        layer.alert("最少有1个通栏推荐位！");
    }else {
        del("/mall/MallAdRecommend/delete", id);
    }
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
                title = "编辑轮播图";
            } else if (bType==4){
                parent = $("#shortcutForm");
                pop = $(".shortcut");
                title = "编辑快捷入口";
            }
            parent.find("[name='id']").val(obj.id);
            parent.find("[name='bannerType']").val(obj.bannerType);
            parent.find("[name='name']").val(obj.name);
            parent.find("[name='sortNum']").val(obj.sortNum);
            parent.find("[name='link']").val(obj.link);
            parent.find("[name='imgSrc']").val(obj.imgSrc);
            parent.find("[name='imgView']").css("display","block");
            parent.find("[name='imgView']").attr("src",obj.imgSrc);
            openUpdPop(pop,title,pop1);
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
            openUpdPop($(".topnews"),"编辑头条",pop6);
        } else {
            layer.alert("获取失败");
        }
    },"json");
}
function toUpdateSpecial(id) {
    $.post("/mall/mallSpecial/get",{id:id,adType:1,floorType:1,clientType:2,channelId:0},function (obj) {
        if (obj!=null){
            var parent = $("#floorForm");
            var pop = $(".floor");
            parent.find("[name='id']").val(obj.id);
            parent.find("[name='adId']").val(obj.adId);
            parent.find("[name='floorId']").val(obj.floorId);
            parent.find("[name='mallFloor.name']").val(obj.mallFloor.name);
            parent.find("[name='mallFloor.subName']").val(obj.mallFloor.subName);
            parent.find("[name='mallAd.link']").val(obj.mallAd.link);
            parent.find("[name='mallAd.imgSrc']").val(obj.mallAd.imgSrc);
            parent.find("[name='imgView']").attr("src",obj.mallAd.imgSrc);
            parent.find("[name='imgView']").css("display","block");
            parent.find("[name='sortNum']").val(obj.sortNum);
            parent.find(".errorMessage").text("");
            pop.css("display","block");
            $(".bgpop").css("display","block");
            openUpdPop(pop,"编辑楼层",pop4);
        } else {
            layer.alert("获取失败");
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

function toUpdateMallAd(id,type){
    $.post("/mall/MallAdRecommend/get",{id:id},function (obj) {
        if (obj!=null){
            var parent = undefined;
            var pop = undefined;
            var title = undefined;
            if (type==1){
                parent = $("#recommendForm");
                pop = $(".recommend");
                title = "编辑推荐位";
                parent.find("[name='name']").val(obj.name);
                var words = obj.mallAd.adWords.split(";");
                parent.find("[name='firstname']").val(words[0]);
                parent.find("[name='lastname']").val(words[1]);
            } else if (type==2){
                parent = $("#fullRecommendForm");
                pop = $(".fullRecommend");
                title = "编辑通栏推荐";
                parent.find("[name='mallAd.imgSrc']").val(obj.mallAd.imgSrc);
                parent.find("[name='imgView']").attr("src",obj.mallAd.imgSrc);
                parent.find("[name='imgView']").css("display","block");
            }
            parent.find("[name='id']").val(obj.id);
            parent.find("[name='adId']").val(obj.adId);
            parent.find("[name='mallAd.itemId']").val(obj.mallAd.itemId);
            parent.find("[name='sortNum']").val(obj.sortNum);
            openUpdPop(pop,title,pop2);
        } else {
            layer.alert("获取失败");
        }
    },"json");
}



function MIndexPageManager(obj) {
    var self =this;
    var viewMindexPage=function () {
        $(obj).data("flag", "1");
        window.open('/mall/mallPcIndex/viewMIndexPage');
        $("button[name='createMIndex']").removeAttr("disabled");
        $("button[name='createMIndex']").css("cursor", "");
        $("button[name='createMIndex']").unbind("click").on("click", function () {
            mindex.create();
        });
    }
    var createMindexpage=function () {
        var flag = $("button[name='viewMIndex']").data("flag");
        if (flag == "0") {
            layer.alert("请先预览首页，确保无误后发布");
            return;
        }
        $("button[name='createMIndex']").attr("disabled", true);
        $.ajax({
            url: "/mall/mallPcIndex/createMIndexPage" + "?time=" + (new Date()).getTime(),
            cache: false,
            dataType: "json",
            type: "POST",
            success: function(data){
                if (data.success == 1) {
                    layer.alert("发布成功");
                } else {
                    layer.alert("发布失败");
                }
                $("button[name='createMIndex']").attr("disabled", false);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                layer.alert("服务端异常:" + textStatus);
                $("button[name='createMIndex']").attr("disabled", false);
            }
        });
    }
    return {view:viewMindexPage,create:createMindexpage};
}
var mindex=MIndexPageManager();