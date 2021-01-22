//下拉列表
$(function(){
	$(".tag_body_1").hide();
	$(".tag_body_2").hide();
	$(".tag_body_3").hide();
})

function fadeIn(p){
	$(".tag_body_"+p).fadeToggle("fast");
}
