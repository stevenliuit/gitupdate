// iframe操作常见方法
var iframeUtil = {
	/**
	 * iframe高度自适应
	 * @param iframeId
	 */
	reinitIframe: function(iframeId){
		var browserVersion = window.navigator.userAgent.toUpperCase();
		var isOpera = false, isFireFox = false, isChrome = false, isSafari = false, isIE = false;

		isOpera = browserVersion.indexOf("OPERA") > -1 ? true : false;
		isFireFox = browserVersion.indexOf("FIREFOX") > -1 ? true : false;
		isChrome = browserVersion.indexOf("CHROME") > -1 ? true : false;
		isSafari = browserVersion.indexOf("SAFARI") > -1 ? true : false;
		if (!!window.ActiveXObject || "ActiveXObject" in window)
			isIE = true;

		var iframe = document.getElementById(iframeId);
		try {
			var subWeb = document.frames ? document.frames[iframeId].document : iframe.contentDocument;
			if (iframe != null && subWeb != null) {
				var realHeight = 0;
				if(isChrome || isSafari || isOpera) {
					realHeight = iframe.contentWindow.document.documentElement.scrollHeight;
				}else if(isFireFox) {
					realHeight = subWeb.body.scrollHeight;
				}else if(isIE) {
					realHeight = iframe.contentWindow.document.body.scrollHeight;
				}else{
					realHeight = subWeb.body.scrollHeight;
				}
				if(realHeight<600){
					iframe.height=600;
				}else{
					iframe.height = realHeight;
				}
				iframe.width = 994;
			}
		}catch (ex){
		}
	}
};

// checkbox操作常见方法
var checkboxUtil = {
	/**
	 * 全选
	 * @param allCheckbox
	 * @param eachCheckbox
	 */
	checkAll: function(allCheckbox, eachCheckbox){
		var _isAllChecked = $(allCheckbox).attr("checked");
		if( _isAllChecked == "true" || _isAllChecked == "checked"){// 全部选择
			$(eachCheckbox).attr("checked", true);
		}else{// 全部取消
			$(eachCheckbox).attr("checked", false);
		}
	},
	/**
	 * 选中一个
	 * @param allCheckbox
	 * @param eachCheckbox
	 */
	checkOne: function(allCheckbox, eachCheckbox){
		// 如果选择了所有的，则选中全选按钮
		if($(eachCheckbox).not("input:checked").length <= 0){
			$(allCheckbox).attr("checked", true);
		}else{
			var _isAllChecked = $(allCheckbox).attr("checked");
			// 当前处于全选状态，则应取消全选
			if( _isAllChecked == "true" || _isAllChecked == "checked"){
				$(allCheckbox).attr("checked", false);
			}
		}

	},
	/**
	 * 获取选中项的某一类信息
	 * @param eachCheckbox
	 * @param infoFlag
	 * @param splitFlag
	 * @returns {String}
	 */
	getCheckedInfo: function(eachCheckbox, infoFlag, splitFlag){
		var infos = "";
		// 选中的项
		var _checkedInfo = $(eachCheckbox + ":checked");
		if(_checkedInfo.length <= 0){
			return infos;
		}
		_checkedInfo.each(function(i, n){
			var _info = $(n).attr(infoFlag);
			infos += _info + splitFlag;
		});
		return infos;
	},
	/**
	 * 判断是否选中
	 * @param $checkBox
	 * @returns {Boolean}
	 */
	isChecked: function($checkBox){
		if($checkBox == null){
			return false;
		}
		var _checked = $checkBox.attr("checked");
		if(_checked == "true" || _checked == "checked"){
			return true;
		}
		return false;
	},
	/**
	 *
	 */
	resetCheck: function(){
		$("input:checkbox").attr("checked", false);
	}
};

//页码工具类
var pageUtil = {
	/**
	 * <div class="pagination clr" style="margin:0;margin-bottom:10px;height:30px;">
	 * 	<div class="fn-right">
	 * 		<span class="disabled">上一页</span>
	 * 		<a class="cur" href="#">1</a>
	 * 		<a href="#">2</a>
	 * 		<a href="#">3</a>
	 * 		<a href="#">4</a>
	 * 		<a href="#">5</a>
	 * 		<span>...</span>
	 * 		<a href="#">10</a>
	 * 		<a class="unable" href="#">下一页</a>
	 * 	</div>
	 * </div>
	 * 分页
	 * 调用示例：
	 * var convertPageParams = [];
	 * convertPageParams.push("#J_item_search_form"); --对应formArea
	 * convertPageParams.push(searchItemOptions);     --对应submitOptions
	 * pageUtil.paginateSet(1, 1, "#J_page_area", item.handleFormSubmit, convertPageParams);
	 * handleFormSubmit方法定义如下
	 * handleFormSubmit: function(formArea, submitOptions, currentPage){
		 * 	// 提交表单
		 * 	$(formArea).ajaxSubmit(submitOptions);
		 * 页码html动态地根据当前条数生成
	 * @param currentPage 当前页
	 * @param totalPage 总页数
	 * @param pageArea 页码区域
	 * @param callback 回调函数
	 * @param args 回调函数的参数
	 */
	paginateSet : function(currentPage, totalPage, pageArea, callback, args) {
		pageUtil.show(pageArea);

		//当传递的currentPage为字符串时，无法进行与数字相加
		if(currentPage){
			currentPage = parseInt(currentPage);
		}
		// 拼接分页字符串
		var splitPage = "<input type='hidden' name='total_page' value=" + totalPage + " /><input type='hidden' name='current_page' value=" + currentPage + " /><p><a ";
		// 不处于第一页时，给第一页绑定点击事件
		if(currentPage > 1) {
			splitPage += "class='page-prev'><</a><a name='pageForSerch' ";
		}else{
			splitPage += "class='no_click page-prev'><</a><a name='pageForSerch'";
		}
		if(currentPage == 1) {
			splitPage += "class='act'";
		}

		splitPage += ">1</a>";

		if(currentPage > 4) {
			splitPage += "<span class='page-more'>...</span>";
		}
		if(currentPage > 3) {
			splitPage += "<a name='pageForSerch'>" + (currentPage - 2) + "</a>";
		}

		if(currentPage > 2) {
			splitPage += "<a name='pageForSerch'>" + (currentPage - 1) + "</a>";
		}

		if(currentPage != 1 && currentPage != totalPage) {
			splitPage += "<a href='javascript:void(0);' name='pageForSerch' class='act' >" + currentPage + "</a>";
		}

		if((totalPage - currentPage) > 1) {
			splitPage += "<a name='pageForSerch'>" + (currentPage + 1) + "</a>";
		}

		if((totalPage - currentPage) > 2) {
			splitPage += "<a name='pageForSerch'>" + (currentPage + 2) + "</a>";
		}

		if((totalPage - currentPage) > 3) {
			splitPage += "<span  class='page-more'>...</span>";
		}

		if(totalPage != 1 && totalPage > 0) {
			splitPage += "<a name='pageForSerch'";
			if(currentPage != totalPage) {
			} else {
				splitPage += "class='act'";
			}
			splitPage += ">" + totalPage + "</a>";
		}
		splitPage += "<a ";
		if(currentPage < totalPage) {
			splitPage += "class='page-next'>> </a>";
		}else{
			splitPage += "class='page-next no_click'>> </a>";
		}
		splitPage += "</p>";
		$(pageArea).html(splitPage);
		$(pageArea).find("a[name=pageForSerch]").on("click", function(){
			var _curClickPage = $(this).text();
			$(pageArea).find("input[name=current_page]").val(_curClickPage);
			pageUtil.choosePage(_curClickPage, callback, args);
		});
		$(pageArea).find(".page-prev").on("click", function(){
			if($(this).hasClass("no_click")){
				//layer.msg('已经是第一页', {skin: 'layer-ext-b2b layer-b2b-hui'});
				return;
			}
			$(pageArea).find("input[name=current_page]").val(currentPage - 1);
			pageUtil.choosePage(currentPage - 1, callback, args);
		});
		$(pageArea).find(".page-next").on("click", function(){
			if($(this).hasClass("no_click")){
				//layer.msg('已经是最后一页', {skin: 'layer-ext-b2b layer-b2b-hui'});
				return;
			}
			$(pageArea).find("input[name=current_page]").val(currentPage + 1);
			pageUtil.choosePage(currentPage + 1, callback, args);
		});
	},
	paginateSetForPop : function(currentPage, totalPage, pageArea, callback, args) {
		pageUtil.show(pageArea);

		//当传递的currentPage为字符串时，无法进行与数字相加
		if(currentPage){
			currentPage = parseInt(currentPage);
		}
		// 拼接分页字符串
		var splitPage = "<input type='hidden' name='total_page' value=" + totalPage + " /><input type='hidden' name='current_page' value=" + currentPage + " /> <a ";
		// 不处于第一页时，给第一页绑定点击事件
		if(currentPage > 1) {
			splitPage += "class='unable page-prev'>上一页</a><a name='pageForSerch' ";
		}else{
			splitPage += "class='disabled page-prev'>上一页</a><a name='pageForSerch'";
		}
		if(currentPage == 1) {
			splitPage += "class='on'";
		}

		splitPage += ">1</a>";

		if(currentPage > 4) {
			splitPage += "<span class='page-more'>...</span>";
		}
		if(currentPage > 3) {
			splitPage += "<a name='pageForSerch'>" + (currentPage - 2) + "</a>";
		}

		if(currentPage > 2) {
			splitPage += "<a name='pageForSerch'>" + (currentPage - 1) + "</a>";
		}

		if(currentPage != 1 && currentPage != totalPage) {
			splitPage += "<a href='javascript:void(0);' name='pageForSerch' class='on' >" + currentPage + "</a>";
		}

		if((totalPage - currentPage) > 1) {
			splitPage += "<a name='pageForSerch'>" + (currentPage + 1) + "</a>";
		}

		if((totalPage - currentPage) > 2) {
			splitPage += "<a name='pageForSerch'>" + (currentPage + 2) + "</a>";
		}

		if((totalPage - currentPage) > 3) {
			splitPage += "<span  class='page-more'>...</span>";
		}

		if(totalPage != 1 && totalPage > 0) {
			splitPage += "<a name='pageForSerch'";
			if(currentPage != totalPage) {
			} else {
				splitPage += "class='on'";
			}
			splitPage += ">" + totalPage + "</a>";
		}
		splitPage += "<a ";
		if(currentPage < totalPage) {
			splitPage += "class='page-next unable'>下一页 </a>";
		}else{
			splitPage += "class='page-next disabled'>下一页 </a>";
		}
		splitPage += "</div>";
		$(pageArea).html(splitPage);
		$(document).delegate(pageArea+" a[name=pageForSerch]","click",function(){
			var _curClickPage = $(this).text();
			$(pageArea).find("input[name=current_page]").val(_curClickPage);
			pageUtil.choosePage(_curClickPage, callback, args);
		});
		$(document).delegate(pageArea+" .page-prev","click",function(){
			if($(this).hasClass("disabled")){
				return;
			}
			$(pageArea).find("input[name=current_page]").val(currentPage - 1);
			pageUtil.choosePage(currentPage - 1, callback, args);
		});
		$(document).delegate(pageArea+" .page-next","click",function(){
			if($(this).hasClass("disabled")){
				return;
			}
			$(pageArea).find("input[name=current_page]").val(currentPage + 1);
			pageUtil.choosePage(currentPage + 1, callback, args);
		});
	},
	/**
	 * 跳转至某一页
	 * @param goPage 页码
	 * @param callback 回调函数
	 * @param args 回调函数的参数
	 */
	choosePage : function(goPage, callback, args) {
		if(!objCheckUtil.isValid(args)){
			args = [];
		}
		args.push(goPage);
		callback.apply(null, args);
	},
	/**
	 * 显示页码
	 * @param pageArea
	 */
	show: function(pageArea) {
		if ($(pageArea)) {
			$(pageArea).show();
		}
	},
	/**
	 * 隐藏页码
	 * @param pageArea
	 */
	hide: function(pageArea) {
		if ($(pageArea)) {
			$(pageArea).hide();
		}
	},
	/**
	 * 获取当前页码
	 * @param pageArea
	 * @returns
	 */
	getCurrentPage: function(pageArea){
		if($(pageArea).is(":visible")){
			var _currentPage = $(pageArea).find("input[name=current_page]").val();
			if(objCheckUtil.isNotEmpty(_currentPage) && !isNaN(_currentPage)){
				return parseInt(_currentPage);
			}
		}
		return 1;
	}
};

// 时间相关操作
var dateUtil = {
	/**
	 * 格式化时间:2014-04-29 00:00:00
	 * @param time
	 * @returns {String}
	 */
	dateFormat: function(time){
		if(null == time || time == ""){
			return "";
		}
		var date = new Date(time);
		var year = date.getFullYear();
		var month = dateUtil.replace(date.getMonth() + 1);
		var date_ = dateUtil.replace(date.getDate());
		var hour = dateUtil.replace(date.getHours());
		var minis = dateUtil.replace(date.getMinutes());
		var seconds = dateUtil.replace(date.getSeconds());
		return year + "-" + month + "-" + date_ + "  " + hour + ":" + minis + ":" + seconds;
	},
	/**
	 * 格式化时间:2014年4月29日
	 * @param time
	 * @returns {String}
	 */
	dateFormat2: function(time){
		var date = new Date(time);
		var year = date.getFullYear();
		var month = dateUtil.replace(date.getMonth() + 1);
		var date_ = dateUtil.replace(date.getDate());
		return year + "年" + month + "月" + date_ + "日";
	},

	/**
	 * 格式化时间:01月01日
	 */
	dataFormat3: function(time) {
		var date = new Date(time);
		var month = dateUtil.replace(date.getMonth() + 1);
		var date_ = dateUtil.replace(date.getDate());
		return month + "月" + date_ + "日";
	},

	/**
	 * 格式化时间时分:13:59
	 */
	dataFormat4: function(time) {
		var date = new Date(time);
		var hour = dateUtil.replace(date.getHours());
		var minis = dateUtil.replace(date.getMinutes());
		return hour + ":" + minis;
	},
	/**格式化时间:2014/10/02*/
	dateFormat5: function(time){
		var date = new Date(time);
		var year = date.getFullYear();
		var month = dateUtil.replace(date.getMonth() + 1);
		var date_ = dateUtil.replace(date.getDate());
		return year + "/" + month + "/" + date_ ;
	},
	/**格式化时间:2014.10.02*/
	dateFormat6: function(time){
		var date = new Date(time);
		var year = date.getFullYear();
		var month = dateUtil.replace(date.getMonth() + 1);
		var date_ = dateUtil.replace(date.getDate());
		return year + "." + month + "." + date_ ;
	},
	/**格式化时间:2014.10.02*/
	dateFormat7: function(time){
		var date = new Date(time);
		var year = date.getFullYear();
		var month = dateUtil.replace(date.getMonth() + 1);
		var date_ = dateUtil.replace(date.getDate());
		return year + "-" + month + "-" + date_ ;
	},
	/**
	 * 获取time与当前时间的间隔
	 * @param time
	 * @param currentTime 当前时间，用服务器端更准确
	 * @returns {String}
	 */
	getTimeInterval: function(time, currentTime){
		var _intervalTime = time - currentTime;
		var _dayTime =  _intervalTime / (24 * 60 * 60 * 1000);
		var _hourTime = (_intervalTime % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
		var _minuteTime = (_intervalTime % (60 * 60 * 1000)) / (60 * 1000);
		var _day =  parseInt(_dayTime);
		var _hour = parseInt(_hourTime);
		var _minute = parseInt(_minuteTime);
		return _day + "天" + _hour + "小时" + _minute + "分";
	},
	/**
	 * 小于10则在前面补0
	 * @param str 需要处理的数字
	 * @returns
	 */
	replace: function(str){
		if(str < 10){
			return "0" + str;
		}else{
			return str;
		}
	}
};

var objCheckUtil = {
	/**
	 * 判断变量是否有效
	 * @param toCheckedObj
	 * @returns {Boolean}
	 */
	isValid: function(toCheckedObj){
		if(typeof(toCheckedObj) == "undefined" || toCheckedObj == null){
			// jQuery对象
			if(toCheckedObj instanceof jQuery){
				if(toCheckedObj.length <= 0){
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		}else{
			// jQuery对象
			if(toCheckedObj instanceof jQuery){
				if(toCheckedObj.length <= 0){
					return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
		}
	},
	/**
	 * 判断是否为空串
	 * @param toCheckedObj
	 * @returns {Boolean}
	 */
	isNotEmpty:  function(toCheckedVal){
		if(typeof(toCheckedVal) == "undefined" || toCheckedVal == null
			|| $.trim(toCheckedVal).length <= 0 || $.trim(toCheckedVal) == ""){
			return false;
		}else{
			return true;
		}
	},
	/**
	 * 给input赋值并触发change事件
	 * @param $input
	 * @param value
	 */
	assignAndHandleChange4Input: function($inputs, value){
		if(objCheckUtil.isValid($inputs)){
			$inputs.each(function(i, n){
				var $input = $(n);
				if($input.val() != value){
					$input.val(value);
					$input.trigger("change");
				}
			});
		}
	}

};
var moneyUtil={
	/**
	 * 格式化金额 xxx,xx0.00
	 */
	fmoney : function (s, n) {
		if(s == null || typeof(s) == "undefined"){
			s=0;
		}
		n = n > 0 && n <= 20 ? n : 2;
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
		var t = "";
		for (var i = 0; i < l.length; i++) {
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
		}
		return t.split("").reverse().join("") + "." + r;
	}
};

//event
var eventUtil = {
	/**
	 * 阻止冒泡
	 */
	stopPropagation: function(e){
		e = e || window.event;
		if(e.stopPropagation){ // W3C阻止冒泡方法
			e.stopPropagation();
		}else{
			e.cancelBubble = true; // IE阻止冒泡方法
		}
	},
	/**
	 * 阻止浏览器的默认行为
	 * @param e
	 * @returns
	 */
	stopDefault: function(e){
		//如果提供了事件对象，则这是一个非IE浏览器
		if(e && e.preventDefault){
			// 阻止默认浏览器动作(W3C)
			e.preventDefault();
		}else{
			//IE中阻止函数器默认动作的方式
			window.event.returnValue = false;
		}
		return false;
	}
};

var selectUtil = {

	resetSelectOption:function(_id, _value, _strVal){
		$(_id).find("option").remove();
		$(_id).prepend("<option value='"+_value +"'>" + _strVal + "</option>");
	}
};

// 图片处理
var imgUtil = {
	/**
	 * 获取指定尺寸的图片
	 * 测试环境：
	 * http://img30.360buyimg.com/test/jfs-test/t4/108/95429239/222714/ba691a1b/53ffd51eN7fb2f449.jpg
	 * http://img30.360buyimg.com/test/s80x80_jfs-test/t4/108/95429239/222714/ba691a1b/53ffd51eN7fb2f449.jpg
	 * 线上环境：
	 * http://img30.360buyimg.com/ecc-b2b/jfs/t556/285/17384013/198736/aa8a2daa/54467e09N7d1f7bb6.png
	 * http://img30.360buyimg.com/ecc-b2b/s80x80_jfs/t556/285/17384013/198736/aa8a2daa/54467e09N7d1f7bb6.png
	 * @param imgFullPath 原图片全路径
	 * @param width 需要获取图片的指定宽度
	 * @param height 需要获取图片的指定高度
	 */
	getSpecifySizeImg: function(imgFullPath, width, height){
		if(!objCheckUtil.isNotEmpty(imgFullPath)){
			return "";
		}
		var _imgFullPathNoHttp = imgFullPath.substring(7);
		var imgPathArray = _imgFullPathNoHttp.split("/");
		imgPathArray[2] = "s" + width + "x" + height + "_" + imgPathArray[2];
		return "http://" + imgPathArray.join("/");
	}
};


var strUtil ={
	substr1:function(str,start,length,strTail){
		if(!str){
			return "";
		}
		var strLen = str.length;
		if(strLen<length){
			return str;
		}
		return str.substr(start,length)+strTail;
	},
	substr:function(str,length,strTail){
		return strUtil.substr1(str,0,length,strTail);
	}
};