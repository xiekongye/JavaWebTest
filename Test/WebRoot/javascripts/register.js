/**
 * 
 */
$(document).ready(function(){
	alert("欢迎光临!");
	var userName = $("#userName");
	var password = $("#password");
	var cancelButton = $("#cancelButton");
	var submitButton = $("#submitButton");
	
	userName.bind("blur",function(){
		if(userName.val() == undefined || userName.val() == null || userName.val() == ""){
			alert("UserName不能为空");
		}
	});
})