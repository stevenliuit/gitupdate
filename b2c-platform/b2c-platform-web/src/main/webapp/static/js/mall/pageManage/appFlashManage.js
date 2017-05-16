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
    $(".btn1").click(function(){
        openPop($(".carousel"),"新建轮播图",bannerlVali);
    });

    var bannerlVali = $("#bannerForm").validate({
        rules:{
            name:{required:true,maxlength:50,isEmpty:true},
            imgSrc:{required:true},
            sortNum:{required:true,digits:true,min:1},
            link:{required:true,isUrl:true},
            beginTime:{required:true,remote:{
                type:"POST",
                url:"/mall/mallBanner/checkByBeginTime",
                data:{
                    clientType:function(){return $("[name='clientType']").val();},
                    channelId:function(){return $("[name='channelId']").val();},
                    bannerType:function(){return $("[name='bannerType']").val();},
                    type:1,
                }
            }},
            endTime:{required:true,remote:{
                type:"POST",
                url:"/mall/mallBanner/checkByBeginTime",
                data:{
                    clientType:function(){return $("[name='clientType']").val();},
                    channelId:function(){return $("[name='channelId']").val();},
                    bannerType:function(){return $("[name='bannerType']").val();},
                    beginTime:function(){return $("[name='beginTime']").val();},
                    type:2,
                }
            }},
        },
        messages:{
            name:{required:"请填入轮播图标题",maxlength:"不能超过50个中文",isEmpty:"请输入正确的字符"},
            imgSrc:{required:"请上传图片"},
            sortNum:{required:"轮播图序号不能为空",digits:"请输入大于0的数字",min:"请输入大于0的数字"},
            link:{required:"图片链接不能为空",isUrl:"请输入正确的链接地址"},
            beginTime:{required:"开始时间不能为空",remote:"必须晚于当前时间"},
            endTime:{required:"结束时间不能为空",remote:"必须晚于开始时间"},
        },
        submitHandler:function (form){
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
});
function openPop(parent,title,validate){
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
    del("/mall/mallBanner/deleteBanner",id);
}

function toUpdateBanner(id,bannerType) {
    $.post("/mall/mallBanner/getBanner",{id:id},function(obj){
        if (obj!=null){
            var parent = $("#bannerForm");
            var pop = $(".carousel");
            pop.find(".popname").text("编辑APP闪图");
            parent.find("[name='id']").val(obj.id);
            parent.find("[name='bannerType']").val(obj.bannerType);
            parent.find("[name='name']").val(obj.name);
            parent.find("[name='sortNum']").val(obj.sortNum);
            parent.find("[name='link']").val(obj.link);
            parent.find("[name='imgSrc']").val(obj.imgSrc);
            parent.find("[name='imgView']").css("display","block");
            parent.find("[name='imgView']").attr("src",obj.imgSrc);
            parent.find("[name='beginTime']").val(moment(obj.beginTime).format("YYYY-MM-DD HH:mm:ss"));
            parent.find("[name='endTime']").val(moment(obj.endTime).format("YYYY-MM-DD HH:mm:ss"));
            parent.find(".errorMessage").text("");
            pop.css("display","block");
            $(".bgpop").css("display","block");
        } else {
            layer.alert("dev:获取失败");
            location.reload(true);
        }
    },"json");
}




