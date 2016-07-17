/**
 * 
 */
$(document).ready(function(){
	var timeSpan = $("#timespan");
	var interId = window.setInterval(function(){
		timeSpan.html(new Date());
	},100);
})