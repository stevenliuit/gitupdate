$(document).ready(function() {
    $(".list_tit h2").click(function() {
        $(this).next(".list_menu").slideToggle(300);
    });
    $('.list_tit h2').click(function(ev){
        $(this).children('.angle-down').toggleClass('hidden');
        $(this).children('.angle-up').toggleClass('hidden');
    });
    $(".dr_btn").click(function(){
        $(".bgpop").css("display","block");
        $(".pop").css("display","block");
        $(".poploading").css("display","none");
        $(".popend").css("display","none");
    });
    $(".popnameclose").click(function(){
        $(".bgpop").css("display","none");
        $(".pop").css("display","none");
    });
    $(".popcancle").click(function(){
        $(".bgpop").css("display","none");
        $(".pop").css("display","none");
    });
    //$("#logout").click(function(){
    //    tool.ajax({
    //        type: "post",
    //        url: "logout",
    //        data: {
    //        },
    //        dataType: "json",
    //        success: function (data) {
    //            if (data && data.isSuccess) {
    //                location.reload(true);
    //            }
    //        }
    //    });
    //})
});
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