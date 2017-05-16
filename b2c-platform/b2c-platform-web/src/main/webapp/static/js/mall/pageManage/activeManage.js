
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
    window.pop1 = $(".active").css("margin-top");
    $(".btn_4").on("click",function(){
        openPop($(".active"),"新建活动页",activeVali, pop1);
    });

    var activeVali = $("#editActiveForm").validate({
        rules:{
            code:{required:true,isEmpty:true},
            htmlStr:{required:true},
        },
        messages:{
            code:{required:"编码不能为空",isEmpty:"请填写正确的字符"},
            htmlStr:{required:"不能为空"},
        },
        submitHandler:function (form){
            $("[type='submit']").attr("disabled",true);
            var id = $(form).find("[name='id']").val();
            if (id==""||id==null){
                addAndUpd("/mall/mallActive/add",$(form));
            } else {
                addAndUpd("/mall/mallActive/update",$(form));
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.next());
        },
    });

});
function openPop(parent,title,validate, marginTop){
    $(".poptrue").removeAttr("disabled");
    var scrollhei = $(document).scrollTop();
    var martop = parseInt(marginTop.substring(0,marginTop.indexOf("p")));
    parent.css("margin-top",martop+scrollhei);
    parent.find(".popname").text(title);
    // parent.find("form")[0].reset();
    validate.resetForm();
    // var imgView = parent.find("[name='imgView']");
    // if(imgView!=undefined) {imgView.attr("src","");}
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

function deleteActive(id) {
    if (confirm("确定要删除吗？")) {
        $.ajax({
            url: "/mall/mallActive/delete",
            type: "post",
            data: {
                id: id,
            },
            dataType: "json",
            success: function (data) {
                if(data.isSuccess){
                    layer.msg("删除成功。", {skin: 'layer-ext-b2b layer-b2b-hui'});
                    window.location.reload(true);
                }else {
                    layer.msg("删除失败，请稍后重试", {skin: 'layer-ext-b2b layer-b2b-hui'});
                }
            },
            error: function (data) {
                layer.msg("服务器打盹，请稍后再试。", {skin: 'layer-ext-b2b layer-b2b-hui'});
            }
        });
    }
}

function openUpdPop(parent, title, marginTop){
    $(".poptrue").removeAttr("disabled");
    var scrollhei = $(document).scrollTop();
    var martop = parseInt(marginTop.substring(0,marginTop.indexOf("p")));
    parent.css("margin-top",martop+scrollhei);
    parent.find(".popname").text(title);
    parent.css("display","block");
    parent.find(".errorMessage").text("");
    $(".bgpop").css("display","block");
}
function toUpdateActive(id,code) {
    $.post("/mall/mallActive/showUpdateActive",{id:id,code:code},function(obj){
        if (obj!=null){
            if(obj.isSuccess){
            var parent = $(".active");
            parent.find("[name='id']").val(obj.data.id);
            parent.find("[name='code']").val(obj.data.code);
            var tObj = document.getElementById('htmlStr');
            tObj.value = obj.data.htmlStr;
            if (obj.data.isHead == 1){
                parent.find("[name='isHead']").attr("checked","checked");
            }else {
                parent.find("[name='isHead']").attr("checked", false);
            }
            openUpdPop(parent,"编辑活动页",pop1);
            }else {
                layer.alert("获取失败");
            }
        } else {
            layer.alert("获取失败");
        }
    },"json");
}




