$(function(){
    // 动态绑定事件
    $(".addr-item").on('mouseover mouseout', '.address', function (enent) {
        if(event.type === "mouseover"){
        	$(this).addClass("address-hover");
        }else if(event.type === "mouseout"){
        	$(this).removeClass("address-hover");
        }
    });
});

$(function(){
	$(".addr-item").on("click", ".name", function () {
        $(this).addClass("selected");
        $(this).parent().siblings().children(".name").removeClass("selected");
    });
	$(".payType li").click(function(){
        $(this).addClass("selected");
        $(this).siblings().removeClass("selected");
	});
});
