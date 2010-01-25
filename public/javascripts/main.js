//global object
var g = {
  getViewportInfo: function(){
    var w = (window.innerWidth) ? window.innerWidth : (document.documentElement && document.documentElement.clientWidth) ? document.documentElement.clientWidth : document.body.offsetWidth;
    var h = (window.innerHeight) ? window.innerHeight : (document.documentElement && document.documentElement.clientHeight) ? document.documentElement.clientHeight : document.body.offsetHeight;
    return {w:w, h:h};
  }

};

var proxyUrl = {
    "http://googlegroups" : "http://groups.google.com"
};


$(function() {

    function openSearchPage() {
        var searchKey = $("#searchkey").attr("value");
        var isproxy = function() {
            return searchKey && searchKey.indexOf("http") == 0;
        }

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
    (function highlightChannel(){
      var currHref = window.location.href;
      $("div#quicklinks a").each(function(){
      	if(currHref.indexOf( this.href ) != - 1){
          $(this).addClass("selected");
          return false;
        }
  		});
    })();
 
})
