/**
 * 
 */
$(document).ready(function(){
	var mailFrom = $("#mailFrom");
	var mailTo = $("#mailTo");
	var mailSubject = $("#mailSubject");
	var mailContent = $("#mailContent");
	var btnSendSimlpeEmail = $("#btnSendSimlpeEmail");
	
	btnSendSimlpeEmail.on('click',function(){
		$.ajax({
			url:'/Test/servlet/SendEmail',
			type:'post',
			async:'true',
			data:{
				//'mailFrom':mailFrom.val(),
				'mailTo':mailTo.val(),
				'mailSubject':mailSubject.val(),
				'mailContent':mailContent.val()
			},
			datatype:'json',
			success: function(data){
				if(data == 'true'){
					alert("发送邮件成功!");
				}else{
					alert("发送邮件失败!");
				}
			},
			error:function(){
				alert("发送邮件AJAX请求出错!");
			},
			complete:function(){
				alert("发送邮件请求完成!");
			}
		});
	});
})