function _getViewportInfo() {
    var w = (window.innerWidth) ? window.innerWidth : (document.documentElement && document.documentElement.clientWidth) ? document.documentElement.clientWidth : document.body.offsetWidth;
    var h = (window.innerHeight) ? window.innerHeight : (document.documentElement && document.documentElement.clientHeight) ? document.documentElement.clientHeight : document.body.offsetHeight;
    return {w:w, h:h};
}

function _resizeFrame() {
    var height = _getViewportInfo().h - $("#frameWindow").position().top;
    height = height - $("#footer").height();
    $("#frameWindow").height(height);
}

//
var curr_window_status = "max"

function _isMaximized() {
    return curr_window_status == "max"
}

$(function() {
    //resize event
    $(window).resize(_resizeFrame);

    //refresh click事件 -> iframe reload
    $('#refresh').click(function() {
        var iframe = $("#frameWindow");
        iframe.attr("src", iframe.attr("src"));
    });

    $('#btnMaximize').click(function() {
        if (!_isMaximized()) {
            curr_window_status = "max";
            this.value = "显示书签栏";
        } else {
            curr_window_status = "min";
            this.value = "隐藏书签栏";
        }

        $("#globalnav").slideToggle("fast", function() {
            _resizeFrame();
        });
    });

    //点击tab链接，在iframe显示网页
    $('ul li a').click(function(event) {
        var t = this;

        //增加点击率
        (function updateHit() {
            $.ajax({
                url:"/site/update_hit",
                data: "id=" + t.id,
                type:"POST",
                dataType:"json",
                success: function(data) {
                    if (data.success)
                        $("#" + t.id + "hit").html(data.currHit);
                    else alert("update hit error:" + data.msg);
                }
            })
        })();

        //刷新
        $("#frameWindow").attr("src", this.href);

        event.preventDefault();
        event.stopPropagation();
    });
});

$(function() {
    _resizeFrame();
});
