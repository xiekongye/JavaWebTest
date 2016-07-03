/**
 * 
 */
$(document).ready(function(){
	//alert("欢迎光临!");
	var userNameText = $("#userName");
	var passwordText = $("#password");
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
					'password':passwordText.val()
				},
				success: function(data){
					alert("AJAX请求返回了数据");
					var jsonResult = jQuery.parseJSON(data);
					var userStr = "";
					jQuery.each(jsonResult,function(key,value){
						jQuery.each(value,function(key,value){
							userStr += '"' + key + '":"' + value + '",'
							});
					});
					alert(userStr);
					
				},
				complete:function(){
					//alert('AJAX请求完成');
				},
				error:function(){
					alert("AJAX请求失败");
					var temp = req;
				}
			});
		}
	}
	userNameText.on("blur",Validation.validateUsername);
	passwordText.on("blur",Validation.validatePassword);
	submitButton.on("click",Validation.validateRegisterResult);
	//submitButton.click = Validation.validateRegisterResult;

})
