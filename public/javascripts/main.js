//global object
var TANG_QIONG_JS = {
    version : "1.0"
};
TANG_QIONG_JS.util = {
    toString: function(obj) {
        if (obj == null) return "null";
        if (typeof obj == "string") return obj;
        if (jQuery.isArray(obj)) {
            var array = [];
            for (var i = 0; i < obj.length; i++) {
                array.push(this.toString(obj[i]));
            }
            return "[" + array.join(", ") + "]";
        }
        if (typeof obj == "object") {
            var array = [];
            for (var p in obj) {
                array.push(p + ":" + this.toString(obj[p]));
            }
            return  "{" + array.join(", ") + "}";
        }

        return obj.toString();
    },
    log: function(obj) {
        if (console) {
            console.log(this.toString(obj));
        }
        else alert(this.toString(obj));
    } ,

    ajax: function(action, data, successFunc) {
        $.ajax({
            type: "POST",
            url: action,
            data: data,
            dataType: "json",
            success: function(result) {
                if (result.success) {
                    successFunc(result);
                }
                else {
                    alert(result.message);
                }
            },
            failure:function(result) {
                alert(result.message);
            }
        });
    },
    getViewportInfo: function() {
        var w = (window.innerWidth) ? window.innerWidth : (document.documentElement && document.documentElement.clientWidth) ? document.documentElement.clientWidth : document.body.offsetWidth;
        var h = (window.innerHeight) ? window.innerHeight : (document.documentElement && document.documentElement.clientHeight) ? document.documentElement.clientHeight : document.body.offsetHeight;
        return {w:w, h:h};
    }

};
var tutil = TANG_QIONG_JS.util;
var ajax = ajax || tutil.ajax;

var g = {
    getViewportInfo : tutil.getViewportInfo
};

var proxyUrl = {
    "http://googlegroups" : "http://groups.google.com"
};


$(function() {

    function openSearchPage() {
        var searchKey = $("#searchkey").attr("value");
        var isproxy = function() {
            return searchKey && searchKey.indexOf("http") == 0;
        };

        function real() {
            if (isproxy()) {
                for (p in proxyUrl) {
                    if (searchKey.indexOf(proxyUrl[p]) == 0) {
                        return p + searchKey.substring(proxyUrl[p].length);
                    }
                }
            }
            return searchKey;
        }

        window.open("/tool/search?key=" + real(), "My Search");
    }

    $("#btnSearch").click(openSearchPage);
    $("#searchkey").keypress(function(e) {
        if (e.which == 13) {
            openSearchPage()
        }
    });

    //选中当前频道
    (function highlightChannel() {
        var currHref = window.location.href;
        $("div#quicklinks a").each(function() {
            if (currHref.indexOf(this.href) != - 1) {
                $(this).addClass("selected");
                return false;
            }
        });
    })();


    /**
     * 重置footer位置.
     */
//    ( function rePositionFooter() {
//        var f = $("#footer");
//        if (f.offset().top < 400) {
//            $("#footer").css("margin-top", g.getViewportInfo().h - 300);
//        }
//    })();


});
