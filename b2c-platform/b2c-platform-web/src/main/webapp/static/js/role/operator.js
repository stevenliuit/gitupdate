function checkform(){
	var realName = $("#realName").val(); 
	if(realName == "" ){
		layer.msg('用户姓名不能为空.');
		return false;
	}
	var userErp = $("#userErp").val();
	if(userErp == ""){
		layer.msg('用户ERP不能为空');
		return false;
	}
	return true;
}

var oper = {
	operatorId:-1,
	loading:false,
	updateRoleLoading:false,
	getParamsForm:function(){
		var that = this;
		return {
			operatorId:that.operatorId,
			realName:$("#realName").val(),
			userErp:$("#userErp").val()
		};
	},
	addOrUpdate:function(){
		var that = this;
		if(that.loading){return false;}
		var check = checkform();
		if(!check){return false;}
		var params = that.getParamsForm();
		console.log(params);
		that.loading = true;
		var btn = $("#addOperatorBtn").html('提交中...');
		$.post('/operator/addOrUpdate',params,function(data){
			that.loading = false;
			btn.html('确定提交');
			if(data.code == "0"){
				window.location.reload();
			}else{
				layer.msg(data.msg);
			}
		});
	},
	updateOperatorRole:function(){
		if(oper.updateRoleLoading){return false;}
		var ids = "";
		$(".allRoleCheckbox").each(function(){
			if(this.checked){
				ids += $(this).val()+",";
			}
		}); 
		if(ids.length == ""){
			layer.msg("请至少选中一个角色");
			return;
		}
		oper.updateRoleLoading = true;
		var params = {operatorId:oper.operatorId,roleIds:ids};
		$.post('/operator/updateOperatorRole',params,function(data){
			oper.updateRoleLoading = false;
			if(data.code != "0"){
				layer.msg(data.msg);
				return;
			}
			window.location.reload();
		});
	}
}

$(function(){
	// 打开增加操作员面板
	$("#addOperBtn").click(function(){
		oper.operatorId = -1;
		layer.open({
            type: 1,title:'添加操作员',
            closeBtn:1,area: '516px',
            content: $('#editOperPage')
        });
	});
	// 修改或增加操作员
	$("#addOperatorBtn").click(function(){
		oper.addOrUpdate();
	});
	// 打开修改操作员面板
	$(".updateOperatorBtn").click(function(){
		oper.operatorId = $(this).attr("operatorId");
		$("#realName").val($("#name"+oper.operatorId).html());
		$("#userErp").val($("#userErp"+oper.operatorId).html());
		layer.open({
            type: 1,title:'修改操作员',
            closeBtn:1,area: '516px',
            content: $('#editOperPage')
        });
	});
	// 打开授权操作员面板
	$(".toupdateOperatorRoleBtn").click(function(){
		oper.operatorId = $(this).attr("operatorId");
		var ids = [];
		$("#roles"+oper.operatorId).find("input").each(function(){
			ids.push(this.value);
		});
		
		$("#updateOperatorRole").find("input:checkbox").each(function(){
			var id = this.value;
			var ok = false;
			for(var i = 0 ; i < ids.length; i ++){
				if(id == ids[i]){
					$(this).attr("checked",true);
					ok = true;
					break;
				}
			}
			if(!ok){
				$(this).attr("checked",false);
			}
		});
		layer.open({
            type: 1,title:'操作员授权',
            closeBtn:1,area: '516px',
            content: $('#updateOperatorRole')
        });
	});
	// 确定修改操作员角色
	$("#updateOperatroRoleContfrimBtn").click(function(){
		oper.updateOperatorRole();
	});
	
});