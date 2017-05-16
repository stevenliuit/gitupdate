/**
 * Created by YueZheng on 2017/2/28.
 */

$.fn.imgUpload = function (func) {
    $(this).fileupload({
        url: "/mall/mallAd/uploadImg",
        dataType: 'json',
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png|webp)$/i,
        maxFileSize: 3 * 1024 * 1024,
        done: function (e, data) {
            var jsonResult = data.result;
            try {
                var jsobObj = jsonResult;
                if (jsobObj.success == 1) {
                    func(jsobObj);
                } else {
                    alert("上传失败");
                }
            } catch (e) {
                alert("图片上传失败" + e);
            }
        },
        fail: function (e, data) {
            alert("图片上传失败！");
        }
    }).on('fileuploadprocessalways', function (e, data) {
        if (data.files.error) {
//                alert(data.files[0].error);
            if (data.files[0].error == "File is too large") {
                alert("文件过大");
            } else if (data.files[0].error == "File type not allowed") {
                alert("文件类型有误");
            } else {
                alert(data.files[0].error);
            }
        }
    }).on('fileuploadprogressall', function (e, data) {

    });
}


