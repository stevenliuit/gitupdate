/**
 * Created by YueZheng on 2017/3/3.
 */
$(document).ready(function() {
    $(".list_tit h2").click(function() {
        $(this).next(".list_menu").slideToggle(300);
    });
    $('.list_tit h2').click(function(ev){
        $(this).children('.angle-down').toggleClass('hidden');
        $(this).children('.angle-up').toggleClass('hidden');
    });
    $("#lanmu").click(function(){
        $(".bgpop").css("display","block");
        $(".pop").css("display","block");
        $(".poploading").css("display","none");
        $(".popend").css("display","none");
    });
    $(".popnameclose").click(function(){
        $(".bgpop").css("display","none");
        $(".pop").css("display","none");
    });

    /*$('#datetimepicker').datetimepicker('show');
    $('#datetimepicker').datetimepicker('update');
    $('#datetimepicker').datetimepicker('setStartDate', '2012-01-01');
    /!*$('.form_date').datetimepicker({
        format: 'yyyy',
        startView:4,
        minView:4,
        language: 'zh-CN' ,
        forceParse: false,
        autoclose:true,
        pickerPosition: "bottom-left"
    });*!/
    //可选择年月日
    $('.form_date').datetimepicker({
        language:  'zh-CN',
        minView: 'month',
        format: 'yyyy-mm-dd',
        autoclose: true,
        startView: 2,
        showMeridian: 1,
        pickerPosition: "bottom-left"
    });*/
    //类目级联初始化
    var oneId = $("[name='parentId']").val();
    var twoId = $("[name='childId']").val();
    if (oneId!=""&&oneId!=null){
        getChildList($("#two"),oneId);
        $("#two").val(twoId);
    }
    //类目动作数据加载
    $("#one").on("change",function(){
        if ($(this).val()!=""){
            getChildList($("#two"),$(this).val());
            $("#two").val("");
        } else {
            $("#two").empty();
            $("#two").append("<option value=''>--请选择--</option>");
        }
    });
    //增加框 选择类目级别动作
    $("[name='categoryType']").on("change",function(){
        var type = $(this).val();
        var addsel = $("#addSel");
        if (type==1){
            addsel.empty();
            addsel.val(0);
            addsel.append("<option value='0'>--请选择--</option>");
            $(".selectone").css("display","none");
        } else if (type==2){
            getChildList(addsel,0);
            $(".selectone").css("display","block");
        }
    });
    //增加框 确认保存
    $(".poptrue").click(function () {
        var form = $("#categoryForm").serializeArray();
        $.post("/mall/mallArticleCategory/add",form,function(obj){
            if(obj){
                location.reload(true);
            } else {
                layer.alert("操作失败!");
                location.reload(true);
            }
        },"json");
    });
});

//获取各级类目列表
function getChildList(dom, parentId){
    $.post("/mall/mallArticleCategory/queryCategory",{parentId:parentId},function(obj){
        dom.empty();
        dom.append("<option value=''>--请选择--</option>");
        for(var i in obj){
            dom.append("<option value='"+obj[i].id+"'>"+obj[i].name+"</option>");
        }
    },"json");
}
//弹出文章编辑iframe
function editArticle(id){
    layer.open({
        type: 2,
        title:"编辑文章",
        area: ['780px', '615px'],
        fixed: false, //不固定
        maxmin: true,
        content: '/mall/mallArticle/toEdit?id='+id,
        scrollbar: false,
        btn: ['保存编辑', '关闭'],
        yes: function(index, layero){
            var iframeWin = window[layero.find('iframe')[0]['name']];
            iframeWin.addArticle();
        },
        btn2: function(){
            layer.closeAll();
        }
    });
}

function publishArticle(id,state){
    var message = null;
    if (state==2){
        message = "确认发布？";
    } else if (state==3){
        message = "确认下架？";
    }
    layer.confirm(message, {
        btn: ['确定','取消']
    }, function(){
        $.post("/mall/mallArticle/updateState",{id:id,state:state},function(obj){
            if(obj){
                location.reload(true);
            } else {
                layer.alert("操作失败！");
            }
        });
    }, function(){
    });
}

function reloadForm(){
    $("#selectForm")[0].reset();
    $("#two").empty();
    $("#two").append("<option value=''>--请选择--</option>");
}
