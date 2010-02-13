$(function() {
    $('#btnInitData').click(function() {
        if (confirm("确认清空已有书签并设置为牛推荐的书签?")) {
            $("#spanInitData").html("正在设置为牛推荐的书签...");
            $.ajax({
                type: "POST",
                url: routes.init_mysite_datas,
                dataType: "json",
                success: function(result) {
                    alert(result.message)
                    $("#spanInitData").html("设置为牛推荐的书签成功！");
                }
            });
        }
    });

    $("#dialog").dialog({
        bgiframe: true,
        height: 200,
        autoOpen: false,
        modal: true,
        buttons: {
            "Ok": function() {
                $.ajax({
                    url: routes.add_bookmarker,
                    data: {
                        name:$("#iName").val(),
                        url:$("#iUrl").val(),
                        key:$('#iKey').val()
                    },
                    type: "POST",
                    dataType: "json",
                    success: function(result) {
                        alert(result.message)
                    }
                })
                $(this).dialog("close");
            },
            "Cancel": function() {
                $(this).dialog("close");
            }
        }
    });

    $("#btnAddBookmarker").click(function() {
        $("#dialog").dialog('open');
    });

    $("#btnSetHomepage").click(function() {
        var homepage = $("#txtHomepage").val();
        if (confirm("确认设置" + homepage + "为主页?")) {
            $.ajax({
                url: routes.set_homepage,
                data: {
                    "homepage":homepage
                },
                type: "POST",
                dataType: "json",
                success: function(result) {
                    alert(result.message)
                }
            });
        }
    });

});
