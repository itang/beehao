/**
 * fix: 
 */
function _resizeFrame(){
	$(".frame").each( function(){
		var size = _getViewportInfo()
		this.width = size.w - 10;
		this.height = size.h - ( _isMaximized() ?  60 :  156 )
	});
}
var curr_window_status = "max"
function _isMaximized(){
	return curr_window_status == "max"
}

$(function(){
	//resize event
	$(window).resize(_resizeFrame);

	//refresh click事件 -> iframe reload
	$('#refresh').click( function(){
		$('.frame').each( function(){this.src = this.src;} )
	});

	$('#btnMaximize').click(function(){
		if(!_isMaximized()){
			curr_window_status = "max"
			this.value = "显示书签栏";
		}else{
			curr_window_status = "min"
			this.value = "隐藏书签栏";
		}

		$("#globalnav").slideToggle("slow"); //.toggle();
		_resizeFrame();
	});
	
	//点击tab链接，在iframe显示网页
	$('ul li a').click( function(event){
		var t  = this
		function updateHit(){
			$.ajax({
					url:"/site/update_hit",
					data: "id=" + t.id,  
					type:"POST",
					dataType:"json",
					success: function(data){
						if(data.success)
							$("#" + t.id + "hit").html(data.currHit)
						else alert("update hit error:" + data.msg)
					}
				})
			};
		updateHit();

    //刷新
		$('.frame').each(function(){ this.src = t.href;})
		event.preventDefault();
		event.stopPropagation(); // do something
	});
});

$(function(){
	//隐藏导航栏
	$("#globalnav").hide();
	
	//iframe 大小自适应窗口
	_resizeFrame()
});
