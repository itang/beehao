    $(function() {
        $('#btnInitData').click(function() {
            if (confirm("ȷ�����������ǩ������Ϊţ�Ƽ�����ǩ?")) {
                $("#spanInitData").html("��������Ϊţ�Ƽ�����ǩ...");
                $.ajax({
                    type: "POST",
                    url: "@{Tool.init_mysite_datas()}",
                    dataType: "json",
                    success: function(data) {
                        alert(data.msg)
                        $("#spanInitData").html("����Ϊţ�Ƽ�����ǩ�ɹ���");
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
                        url: "@{Tool.add_bookmarker()}",
                        data: {
                            name:$("#iName").val(),
                            url:$("#iUrl").val(),
                            key:$('#iKey').val()
                        },
                        type: "POST",
                        dataType: "json",
                        success:function(data) {
                            alert(data.msg)
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
            if (confirm("ȷ������" + homepage + "Ϊ��ҳ?")) {
                $.ajax({
                    url: "@{Tool.set_homepage()}",
                    data: {
                        "homepage":homepage
                    },
                    type: "POST",
                    dataType: "json",
                    success:function(data) {
                        alert(data.msg);
                    }
                });
            }
        });

    });
