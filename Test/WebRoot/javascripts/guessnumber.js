/**
 * 猜数字
 */
$(document).ready(function(){
	var promptInfo = $("#PromptInfo");//提示信息
	promptInfo.html("我设置了一个0-100之间的随机数，你能猜到吗?");
	
	var guessCount = $("#GuessCount");//用户猜的次数
	guessCount.html("0");
	
	var guessNumber = $("#GuessNumber");//用户猜的数字
	var btnSubmit = $("#Submit");//提交按钮
	btnSubmit.on('click',function(){
		var getResultAjax = $.ajax('/Test/servlet/CheckGuessResult',{
			method:'post',
			data:{
				'guessNumber':guessNumber.val()
			},
			async:true,
			success:function(data){
				if(data){
					var result = JSON.parse(data);
					guessCount.html(result.GuessCount);
					switch(result.ResultDesc){
					case 'RIGHT':
						promptInfo.html("恭喜你，猜对了！页面将在5秒后重新刷新，生成一个新的随机数...");
						setTimeout(function(){
							window.location = "/Test/GuessNumber.jsp";
						},5000);
						break;
					case 'SMALL':
						promptInfo.html("猜小了，请重新输入你猜的数字!");
						break;
					case 'BIG':
						promptInfo.html("猜大了，请重新输入你猜的数字!");
						break;
					}
					//promptInfo.html(result.ResultDesc);
					//if(result.ResultDesc == "RIGHT"){
					//	window.location = "/Test/GuessNumber.jsp";
					//}
					//ShowPromptInfo.showResultInfo(promptInfo);
					//ShowPromptInfo.showGuessCountInfo(guessCount);
				}
			},
			error:function(){
				alert("请求CheckGuessResult失败！");
			}
		});
	});
	//显示提示信息的类
	var ShowPromptInfo = {
			showResultInfo:function(elem){
				
			},
			showGuessCountInfo:function(elem){
				
			}
	};
})