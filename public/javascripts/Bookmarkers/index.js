var curr_window_status = "min";

function _isMaximized() {
    return curr_window_status == "max"
}

$(function() {

    //refresh click事件 -> iframe reload
    $('#refresh').click(function() {
        var iframe = $("#frameWindow");
        iframe.attr("src", iframe.attr("src"));
    });

    $('#btnMaximize').click(function() {
        $("#navbar").toggle("fast");
        if (!_isMaximized()) {
            curr_window_status = "max";
            this.value = "显示书签栏";
            $("#frame_container").width(g.getViewportInfo().w - 30);
        } else {
            curr_window_status = "min";
            this.value = "隐藏书签栏";
            $("#frame_container").width(g.getViewportInfo().w - 250);
        }
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

    $("#frame_container").width(g.getViewportInfo().w - 250);
});
