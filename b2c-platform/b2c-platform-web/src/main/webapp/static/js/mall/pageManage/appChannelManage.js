/**
 * Created by YueZheng on 2017/3/1.
 */
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
    window.pop1 = $(".carousel").css("margin-top");
    $(".btn1").click(function(){
        openPop($(".carousel"),"新建轮播图",bannerlVali,pop1);
    });
    window.pop2 = $(".hotclass").css("margin-top");
    $(".btn2").click(function(){
        openPop($(".hotclass"),"新建热门分类",hotclassVali,pop2);
    });
    window.pop3 = $(".special").css("margin-top");
    $(".btn3").click(function(){
        openPop($(".special"),"新建专题",specialVali,pop3);
    });
    window.pop4 = $(".brand").css("margin-top");
    $(".btn4").click(function(){
        openPop($(".brand"),"新建品牌",brandVali,pop4);
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

    //新建hotclass验证
    var hotclassVali = $("#hotclassForm").validate({
        rules:{
            name:{required:true,maxlength:50,isEmpty:true},
            imgSrc:{required:true},
            sortNum:{required:true,digits:true,min:1},
            link:{required:true,isUrl:true},
        },
        messages:{
            name:{required:"请填入分类标题",maxlength:"不能超过50个中文",isEmpty:"请填写正确的字符"},
            imgSrc:{required:"请上传图片"},
            sortNum:{required:"排序序号不能为空",digits:"请输入大于0的数字",min:"请输入大于0的数字"},
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

    //新建special验证
    var specialVali = $("#specialForm").validate({
        rules:{
            "mallFloor.name":{required:true,maxlength:50,isEmpty:true},
            "mallAd.imgSrc":{required:true},
            sortNum:{required:true,digits:true},
            "mallAd.link":{required:true,isUrl:true},
        },
        messages:{
            "mallFloor.name":{required:"请填入标题",maxlength:"不能超过50个中文",isEmpty:"请填写正确的字符"},
            "mallAd.imgSrc":{required:"请上传图片"},
            sortNum:{required:"序号不能为空",digits:"请输入数字"},
            "mallAd.link":{required:"链接不能为空",isUrl:"请输入正确的链接地址"},
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

    //新建brand验证
    var brandVali = $("#brandForm").validate({
        rules:{
            sortNum:{required:true,digits:true,min:1},
            name:{required:true,maxlength:50,isEmpty:true},
            imgSrc:{required:true},

        },
        messages:{
            sortNum:{required:"品牌序号不能为空",digits:"请输入大于0的数字",min:"请输入大于0的数字"},
            name:{required:"请填入品牌名称",maxlength:"不能超过50个中文",isEmpty:"请填写正确的字符"},
            imgSrc:{required:"请上传品牌图片"},
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
});
function openPop(parent,title,validate, marginTop){
    var scrollhei = $(document).scrollTop();
    var martop = parseInt(marginTop.substring(0,marginTop.indexOf("p")));
    parent.css("margin-top",martop+scrollhei);
    parent.find(".popname").text(title);
   // parent.find("form")[0].reset();
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
    del("/mall/mallBanner/deleteBanner",id)
    }
}
function deleteHotclass(id){
        del("/mall/mallBanner/deleteBanner",id)
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
            } else if (bType==7){
                parent = $("#hotclassForm");
                pop = $(".hotclass");
                title = "编辑热门分类";
            } else if (bType==8){
                parent = $("#brandForm");
                pop = $(".brand");
                title = "编辑品牌";
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

function toUpdateSpecial(id,channelId){
    $.post("/mall/mallSpecial/get",{id:id,adType:4,floorType:4,clientType:3,channelId:channelId},function(obj){
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
