/**
 * validate 自定义校验
 */
//url自定义校验
jQuery.validator.addMethod("isUrl", function(value, element) {
    var url = /^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/|[qQ][iI][hH][oO]{2}[mM][aA][lL]{2}:\/\/|.*)+/;
    return this.optional(element) || (url.test(value));
}, "请正填写正确的URL");

//全拼自定义校验  字母下划线“/”
jQuery.validator.addMethod("isPinyin", function(value, element) {
    var pinyin = /^(\/+|\w+)+$/;
    return this.optional(element) || (pinyin.test(value));
}, "请正填写正确全拼");


//验证不能为空字符串
jQuery.validator.addMethod("isEmpty", function(value, element) {
    var pinyin = /\S/;
    return this.optional(element) || (pinyin.test(value));
}, "请填写正确的字符");