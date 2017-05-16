$(function() {
    var upLoadImage = {
        url: "/item/import_item",
        dataType: 'json',
        acceptFileTypes: /(\.|\/)(xlsx|xls)$/i,
        maxFileSize:  5 * 1024 * 1024,
        done: function (e, data) {
            var result = data.result;
            if (result.success == true) {
                var importDate = result.importDate;
                var total = result.total;
                var succeCount = result.succeCount;
                var faileCount = result.faileCount;
                var importItemFails = result.importItemFails;
                $(".list_time").text(importDate);
                $(".list_num").text(total);
                $(".list_suc").text(succeCount);
                $(".list_fail").text(faileCount);
                $("#failSkuList").html("");
                if(importItemFails && importItemFails.length > 0){

                    var html = "";
                    for(var o in importItemFails){
                        var importItemFail = importItemFails[o];
                        html += "<tr><td style='width:33%'>" + importItemFail.rowNum + "</td>";
                        html += "<td style='width:36%'>" + importItemFail.skuId + "</td>";
                        html += "<td>" + importItemFail.resultMsg + "</td></tr>";
                    }
                    $("#failSkuList").html(html);
                    $(".list_table").show();
                    $(".list_table_prompt").show();
                }else{
                    $(".list_table").hide();
                    $(".list_table_prompt").hide();
                }
                $(".popend").show();
                //var msg = "导入完成,成功:<span>" + result.successCount + "</span>条, 失败:<span>" + result.failCount + "</span>条, <a href='" + result.resultFileUrl + "' style='color:red;font-size:18px;text-decoration:underline;'><span class='text-blue'>点击下载结果</span></a>"
                //alert(msg);
            } else {
                layer.msg("导入失败," + result.msg,{skin: 'layer-ext-b2b layer-b2b-hui'});
                $(".poploading").hide();
                $("#importExcel").show();
            }
        },
        fail: function (e, data) {
            layer.msg("导入失败",{skin: 'layer-ext-b2b layer-b2b-hui'});
            $(".poploading").hide();
            $("#importExcel").show();
        }
    };
    $("input[name=itemTemplate]").fileupload(upLoadImage).on('fileuploadprocessalways', function (e, data) {
        if(data.files.error){
            var msg = data.files[0].error;
            if (msg == 'File is too large') {
                layer.msg("上传文件大于5M",{skin: 'layer-ext-b2b layer-b2b-hui'});
            } else if (msg == 'File type not allowed') {
                layer.msg("只允许上传xlsx、xls文件类型",{skin: 'layer-ext-b2b layer-b2b-hui'});
            } else {
                layer.msg(msg,{skin: 'layer-ext-b2b layer-b2b-hui'});
            }
        }
    }).on('fileuploadprogressall', function (e, data) {
        $(".poploading").show();
        var $target = $(e.target);
        var _progress = parseInt(data.loaded / data.total, 10);
        var px = parseInt(365 * _progress);
        $(".loadingbg").next().css("width",  px + "px");
        //$target.parent().siblings(".text-center").find("em").css("width",  _progress + "%");
        //alert('加载中');
        //layer.msg('加载中', {skin: 'layer-b2b-loading', icon: 16, shade: [0.2, '#000'], time: 0});
    });
})