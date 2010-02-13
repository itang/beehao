function _resizeFrame() {
    var height = g.getViewportInfo().h - $("#frameWindow").position().top;
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

            $("#frameWindow").hide();
        }

        $("#globalnav").slideToggle("fast", function() {
            $("#frameWindow").show();
            _resizeFrame();
        });
    });

    //点击tab链接，在iframe显示网页
    $('ul li a').click(function(event) {
        var t = this;

        //增加点击率
        (function updateHit() {
            $.ajax({
                url:"/bookmarkers/update_hit",
                data: "id=" + t.id,
                type:"POST",
                dataType:"json",
                success: function(result) {
                    if (result.success)
                        $("#" + t.id + "hit").html(result.data.currHit);
                    else alert("update hit error:" + result.message);
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
