(function ($) {
    $.fn.extend({
        //插件名称 - followPop
        followPop: function (options) {

            //参数和默认值
            var defaults = {
                popHeight: 310,
                popWidth: 310
            };

            var options = $.extend(defaults, options);

            return this.each(function () {
                var o = options;
				var obj = $(this);

                //添加事件
                obj.hover(function () {
					var winHeight = $(window).height();
					var pScTop = $(window).scrollTop();
					var plargepath = obj.attr("rel");
					if(!plargepath){
						return;
					}
					var plink = obj.attr("href");
					var phPosition = obj.offset().left + 90;
					var pvPosition = obj.offset().top;
					var pSpace = winHeight - (pvPosition - pScTop);
					var positionPopWrap = '<div class="popup-position"><div class="img-large" style="width:'+o.popWidth+';height:'+o.popHeight+'"><a href="'+plink+'"><img src="'+plargepath+'" /></a></div></div>';

					if($(".popup-position").length > 0){
						$(".popup-position a").attr("href",plink);
						$(".popup-position img").attr("src",plargepath);
					}else{
						$("body").prepend(positionPopWrap);
					}
					if(pSpace < o.popHeight ){
						$(".popup-position").css({
							left:phPosition,
							top:pvPosition -(o.popHeight-pSpace)-20
						});
					}else{
						$(".popup-position").css({
							left:phPosition,
							top:pvPosition
						});
					}
					obj.parent().addClass("hover");
				},
				function () {
					$(".popup-position").css("left","-9999px");
					obj.parent().removeClass("hover");
				});

            });
        }
    });
})(jQuery);