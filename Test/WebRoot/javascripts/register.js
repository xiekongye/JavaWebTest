/**
 * 
 */
$(document).ready(function(){
	//使用jquery.validate.js插件完成验证
	var validation = $("#RegisterForm").validate({
		rules:{
			"userName":{
				required:true,
				minlength:2
			},
			"password":{
				required:true,
				minlength:6
			},
			"userEmail":{
				required:true,
				email:true
			}
		},
		messages: {
			"userName":{
				required:"用户名不能为空",
				minlength:"用户名长度至少为2位"
			},
			"password":{
				required:"密码不能为空",
				minlength:"密码长度至少为6位"
			},
			"userEmail":{
				required:"邮箱不能为空",
				email:"邮箱格式不正确"
			}
		}
	});
	//alert("欢迎光临!");
	var userNameText = $("#userName");
	var passwordText = $("#password");
	var userEmailText = $("#userEmail");
	var cancelButton = $("#cancelButton");
	var submitButton = $("#submitButton");

	//验证有效性类
	var Validation = {
		validateUsername:function(){
			//验证用户名
			var userName = userNameText.val();
			if(!userName){
				//Console.log("用户名不合法";)
				alert("用户名不合法");
				return false;
			}
			return true;
		},
		validatePassword:function(){
			//验证密码
			var password = passwordText.val();
			if(!password){
				alert("密码不合法");
				return false;
			}
			return true;
		},
		validateRegisterResult:function(){
			var req = jQuery.ajax({
				type:"post",
				url:"./servlet/GetRegisterResult",
				async:false,
				data:{
					'userName':userNameText.val(),
					'password':passwordText.val(),
					'userEmail':userEmailText.val()
				},
				success: function(data){
					//concole.log("AJAX请求返回了数据");
					/*
					var jsonResult = jQuery.parseJSON(data);
					var userStr = "";
					jQuery.each(jsonResult,function(key,value){
						jQuery.each(value,function(key,value){
							userStr += '"' + key + '":"' + value + '",'
							});
					});
					alert(userStr);
					*/
					if(data){
						var result = jQuery.parseJSON(data);
						if(result.IsSuccess){
							$("#PromptInfo").html("用户注册成功!");
							$("#PromptInfo").show();
						}else{
							$("#PromptInfo").html("该用户名已经被注册，请选择其他用户名!");
							$("#PromptInfo").show();
						}
						
					}
				},
				complete:function(){
					//alert('AJAX请求完成');
				},
				error:function(){
					//console.log("AJAX请求失败");
					var temp = req;
				}
			});
		}
	}
	/*
	userNameText.on("blur",Validation.validateUsername);
	passwordText.on("blur",Validation.validatePassword);
	*/
	submitButton.on("click",Validation.validateRegisterResult);
})
