function valication(inputIds){
	for(var item in inputIds){
		var input = $("#"+inputIds[item]);
		if(input.is('input')){
			if(input.val() == ""){
				layer.msg(input.attr('placeholder'));
				return false;
			}
		}
		if(input.is('select')){
			if(input.val() == "-1" || input.val() == ""){
				layer.msg(input.attr('placeholder'));
				return false;
			}
		}
	}
	return true;
}

var update = {
	loading:false,
	updateStateLoading:false,
	functionId:-1,
	getFormParams:function(){
		var that = this;
		return {
			functionId:that.functionId,
			typeId:$("#funcTypeId").val(),
			name:$("#name").val(),
			description:$("#funcDescription").val(),
		 	code:$("#funcCode").val(),
		 	functionUrl:$("#funcUrl").val()
		 };
	},
	addOrUpdate:function(){
		if(this.loading){return false;}
		 var check = valication(["name","funcCode","funcTypeId","funcUrl"]);
		 if(!check){return false;}
		 this.loading = true;
		 var addBtn = $("#addFunctionBtn");
		 addBtn.html('提交中...');
		 var params = this.getFormParams();
		 $.post('/function/addOrUpdate',params,function(data){
			 addBtn.html('确定提交');
			 update.loading = false;
			 console.log(data);
		 	 if(data.code == "0"){
		 		window.location.reload();
		 	 }else{
		 		layer.msg(data.msg);
		 	 }
		 });
	}
}

$(function(){
	$("#addBtn").click(function(){
		update.functionId =-1;
		layer.open({type: 1,title:'添加权限功能',
		closeBtn:1,
		  area: '516px',
		  content: $('#addModelPage')
		});
	});
	// 修改或增加
	$("#addFunctionBtn").click(function(){
		update.addOrUpdate();
	});
	
	$(".toupdate").click(function(){
		update.functionId = $(this).attr("functionId");
		// 设置 值
		$("#name").val($("#fname"+update.functionId).html());
		$("#funcCode").val($("#fcode"+update.functionId).html());
		$("#funcTypeId").val($(this).attr("typeId"));
		$("#funcUrl").val($("#funcUrl"+update.functionId).html());
		$("#funcDescription").val($("#fdesc"+update.functionId).html());
		layer.open({
			type: 1,title:'修改权限功能',
			closeBtn:1,area: '516px',
		  	content: $('#addModelPage')
		});
	});
	

	$(".stateBtn").click(function(){
		if(update.updateStateLoading){return false;}
		var that = $(this);
		var _state = that.attr('state');
		that.html('修改中...');
		update.updateStateLoading = true;
		var functionId = that.attr('functionId');
		$.getJSON('/function/updateStatus',{functionId:functionId},function(data){
			update.updateStateLoading = false;
			if(data.code == 0){
				$("#state"+functionId).html(_state == "1" ? "否":"是");
				that.html(_state == "1"?"启用":"停用");
				that.attr("state",_state == "1" ?0:1);
			}else{
				that.html(_state == "1"?"停用":"启用");
				layer.msg('修改失败：'+data.msg);
			}
		});
	});
});