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
	loading:false,roleId:-1,
	getFormParams:function(){
		var that = this;
		return {
			roleId:that.roleId,
			roleName:$("#name").val(),
			descirption:$("#rdescription").val()
		 };
	},
	addOrUpdate:function(){
		if(this.loading){return false;}
		 var check = valication(["name"]);
		 if(!check){return false;}
		 this.loading = true;
		 var addBtn = $("#addRoleBtn");
		 addBtn.html('提交中...');
		 var params = this.getFormParams();
		 $.post('/role/addOrUpdateRole',params,function(data){
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
		update.roleId =-1;
		layer.open({type: 1,title:'添加角色',
		closeBtn:1,
		  area: '516px',
		  content: $('#addModelPage')
		});
	});
	
	$("#addRoleBtn").click(function(){
		update.addOrUpdate();
	});
	
	$(".toupdate").click(function(){
		update.roleId = $(this).attr("roleId");
		// 设置 值
		$("#name").val($("#rname"+update.roleId).html());
		$("#rdescription").val($("#rdesc"+update.roleId).html());
		layer.open({
			type: 1,title:'修改角色',
			closeBtn:1,area: '516px',
		  	content: $('#addModelPage')
		});
	});
	

	$(".stateBtn").click(function(){
		var that = $(this);
		if(that.html() == '修改中...'){return false;}
		var _state = that.attr('state');
		that.html('修改中...');
		var roleId = that.attr('roleId');
		$.getJSON('/role/updateStatus',{roleId:roleId},function(data){
			if(data.code == 0){
				$("#state"+roleId).html(_state == "1" ? "否":"是");
				that.html(_state == "1"?"启用":"停用");
				that.attr("state",_state == "1" ?0:1);
			}else{
				that.html(_state == "1"?"停用":"启用");
				layer.msg('修改失败：'+data.msg);
			}
		});
	});
});