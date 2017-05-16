$(function () {
    $(".popnameclose").click(function () {
        $(".bgpop").css("display", "none");
        $(".pop").css("display", "none");
    });
    $(".btn_4").click(function () {
        addHotWordsForm.resetForm();
        $("#addHotWordswin").css("display","block");
    });
    $("[name='notAddMsg']").click(function () {
        $(".bgpop").css("display", "none");
        layer.msg("只能添加5个热搜词", {skin: 'layer-ext-b2b layer-b2b-hui'});
    });
    $("[name='editHotWords']").click(function () {
        $(".bgpop").css("display", "block");
    });
    var addHotWordsForm = $("#addHotWordsForm").validate({
        rules: {
            hotWords_name: {required: true,maxlength:10,isEmpty:true}
        },
        messages: {
            hotWords_name: {required: "请输入热词",maxlength:"不能超过10个中文",isEmpty:"请填写正确的字符"}
        },
        submitHandler: function (form) {
            $("[type='submit']").attr("disabled",true);
            interface.saveHotWordsAdd();
        },
        errorPlacement: function (error, element) {
            error.appendTo(element.next());
        },
    });



});

/**单个热词删除 - in use
 *
 */
function deleteHotWords(id, sortNum) {
    layer.confirm('确认要删除热词吗？', {
        title: '删除提示',
        icon: 0,
        btn: ['确  定', '取  消']
    }, function (index, layero) {
        interface.delHotWordsById(id, sortNum);
    }, function (index) {

    });
};


function popnameclose() {
    layer.closeAll()

};

function updateHotWords(id, name, wordsType) {
    var updateHotWordsForm = $("#updateHotWordsForm").validate({
        rules: {
            update_hotWords_name: {required: true,maxlength:10,isEmpty:true}
        },
        messages: {
            update_hotWords_name: {required: "请输入热词",maxlength:"不能超过10个中文",isEmpty:"请填写正确的字符"}
        },
        submitHandler: function (form) {
            $("[type='submit']").attr("disabled",true);
            interface.updateCommit();
        },
        errorPlacement: function (error, element) {
            error.appendTo(element.next());
        },
    });

    updateHotWordsForm.resetForm();
    $("#hotWordsId").attr("value", id);
    $("#update_hotWords_name").attr("value", name);
    if (wordsType == 2) {
        $("#updateCheckbox").attr("checked", "checked");
    } else {
        $("#updateCheckbox").attr("checked", false);
    }
    $("#updateHotWordswin").css("display", "block");
    // $(".bgpop").css("display","none");
    // $(".pop2").css("display","none");

};

/**
 * 排序置顶
 * @param id
 * @param sortNun
 */
function toStick(id, sortNum) {
    $.ajax({
        url: "/mall/hotWords/toStick",
        type: "post",
        data: {
            id: id,
            sortNum: sortNum
        },
        dataType: "json",
        success: function (data) {
            if (data) {
                if (data.result.msg) {
                    layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                }
                if (data.result.success == true) {
                    layer.msg("置顶成功。", {skin: 'layer-ext-b2b layer-b2b-hui'});
                    window.location.reload(true);
                }
            }
        },
        error: function (data) {
            layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
        }
    });

};
/**
 * 排序置尾
 * @param id
 * @param sortNun
 */
function toFinally(id, sortNum) {
    $.ajax({
        url: "/mall/hotWords/toFinally",
        type: "post",
        data: {
            id: id,
            sortNum: sortNum
        },
        dataType: "json",
        success: function (data) {
            if (data) {
                if (data.result.msg) {
                    layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                }
                if (data.result.success == true) {
                    layer.msg("排序置尾成功。", {skin: 'layer-ext-b2b layer-b2b-hui'});
                    window.location.reload(true);
                }
            }
        },
        error: function (data) {
            layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
        }
    });

};
/**
 * 排序上移
 * @param id
 * @param sortNun
 */
function moveUp(id, sortNum) {
    $.ajax({
        url: "/mall/hotWords/moveUp",
        type: "post",
        data: {
            id: id,
            sortNum: sortNum
        },
        dataType: "json",
        success: function (data) {
            if (data) {
                if (data.result.msg) {
                    layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                }
                if (data.result.success == true) {
                    layer.msg("上移成功。", {skin: 'layer-ext-b2b layer-b2b-hui'});
                    window.location.reload(true);
                }
            }
        },
        error: function (data) {
            layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
        }
    });

};
/**
 * 排序下移
 * @param id
 * @param sortNun
 */
function moveDown(id, sortNum) {
    $.ajax({
        url: "/mall/hotWords/moveDown",
        type: "post",
        data: {
            id: id,
            sortNum: sortNum
        },
        dataType: "json",
        success: function (data) {
            if (data) {
                if (data.result.msg) {
                    layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                }
                if (data.result.success == true) {
                    layer.msg("下移成功。", {skin: 'layer-ext-b2b layer-b2b-hui'});
                    window.location.reload(true);
                }
            }
        },
        error: function (data) {
            layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
        }
    });

};


/**
 * 热词接口
 *
 */
var interface = {
    /**
     * 根据id删除热词
     */
    delHotWordsById: function (id, sortNum) {

        $.ajax({
            url: "/mall/hotWords/delhotWordsById",
            type: "post",
            data: {
                id: id,
                sortNum: sortNum
            },
            dataType: "json",
            success: function (data) {
                if (data) {
                    if (data.result.success == true) {
                        layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                        window.location.reload(true);
                    }
                    else if (data.result.msg) {
                        layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                    }

                }
            },
            error: function (data) {
                layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
            }
        });
    },
    /**
     * 添加热词
     */
    saveHotWordsAdd: function () {
        var hotWordsName = $("#hotWords_name").val();
        if ($("[name='number']").length >= 5) {
            layer.msg('<td class="rc_set">热词设置已达上限，最多设置5个</td>', {skin: 'layer-ext-b2b layer-b2b-hui'});
            window.location.reload(true);
            return;
        }
        if ($("#addHotWordsCheckbox:checked").val() == 2) {
            var wordsType = 2;
        } else {
            var wordsType = 1;
        }
        $.ajax({
            url: "/mall/hotWords/addNewHotWords",
            type: "post",
            data: {
                hotWordsName: hotWordsName,
                wordsType: wordsType,
                clientType: 1
            },
            dataType: "json",
            success: function (data) {
                if (data) {
                    if (data.result.msg) {
                        layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                    }
                    if (data.result.success == true) {
                        layer.msg("添加成功。", {skin: 'layer-ext-b2b layer-b2b-hui'});
                        window.location.reload(true);
                    }
                }
            },
            error: function (data) {
                layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
            }
        });
    },
    /**
     * 保存修改热词
     */
    updateCommit: function () {
        var id = $("#hotWordsId").val();
        var hotWordsName = $("#update_hotWords_name").val();
        if ($("#updateCheckbox:checked").val() == 2) {
            var wordsType = 2;
        } else {
            var wordsType = 1;
        }
        $.ajax({
            url: "/mall/hotWords/updateHotWords",
            type: "post",
            data: {
                id: id,
                name: hotWordsName,
                wordsType: wordsType,
                clientType: 1
            },
            dataType: "json",
            success: function (data) {
                if (data) {
                    if (data.result.msg) {
                        layer.msg(data.result.msg, {skin: 'layer-ext-b2b layer-b2b-hui'});
                    }
                    if (data.result.success == true) {
                        layer.msg("修改成功。", {skin: 'layer-ext-b2b layer-b2b-hui'});
                        window.location.reload(true);
                    }
                }
            },
            error: function (data) {
                layer.msg('服务器打盹，请稍后再试。', {skin: 'layer-ext-b2b layer-b2b-hui'});
            }
        });
    }


}


