﻿/**
 * 放大图片
 * 用法：在img标签中加class="showBigImage"的属性
 **/
$(document).ready(function() {  
	$('.showBigImage').mouseover(function(){
		var Img = new Image();  
		Img.src = this.src;  
		if(Img.width == 0 && Img.height == 0){
			Img.onload = function (){
				var w = Img.width; 
				var h = Img.height;
				w = w >= 500 ? 500:w;
				h = h >= 350 ? 350:h;
				var $tip=$('<div id="showBigImage"><div class="showBigImage_box"><div><s><i></i></s><img height="'+h+'" width="'+w+'" src="'+this.src+'" /></div></div></div>');
				$('body').append($tip);
				$('#showBigImage').show('fast');	
			}
		}else{
			var w = Img.width; 
			var h = Img.height;
			w = w >= 500 ? 500:w;
			h = h >= 350 ? 350:h;
			var $tip=$('<div id="showBigImage"><div class="showBigImage_box"><div><s><i></i></s><img height="'+h+'" width="'+w+'" src="'+this.src+'" /></div></div></div>');
			$('body').append($tip);
			$('#showBigImage').show('fast');
		}
   	}).mouseout(function(){
      	$('#showBigImage').remove();
   	}).mousemove(function(e){
      	$('#showBigImage').css({"top":(e.pageY-60)+"px","left":(e.pageX+30)+"px"})
   	})
   
   	$('.showBigImage').click(function(){
		var dialog = art.dialog({id: '123456789',title: '图片预览',width: 500,height: 400,lock: true});
		dialog.content('<img src="http://static.pinju.com/images/ajax/loadding.gif" />'); 
		var Img = new Image();  
		Img.src = this.src;  
      	if(Img.width == 0 && Img.height == 0){
			Img.onload = function (){
				var w =  Img.width;  
				var h =  Img.height;
				var w1 = document.body.clientWidth;
				w = w > w1 ? parseInt(w1-410):w;
				h = h >= 950 ? 900:h;
				dialog.content('<img src="'+this.src+'" width="'+w+'" height="'+h+'" />');	
			}
		}else{
			var w =  Img.width; 
			var h =  Img.height;
			var w1 = document.body.clientWidth;
			w = w > w1 ? parseInt(w1-410):w;
			h = h >= 950 ? 900:h;
			dialog.content('<img src="'+this.src+'" width="'+w+'" height="'+h+'" />');
		}
   	});

});

/**
 * 图片放大镜
 */
var $E = function(opts){
	function _(objId){return (typeof objId === "string")?document.getElementById(objId):objId;}
	var beImg,expand,clip,cover,warp,expImg;
	var clipWidth,clipHeight;
	var closeTimeId = null;
	var mult = 1; //图片放大倍数,根据裁剪框和放大后图片容器的大小自动调整
	//init
	(function(){
		beImg = _(opts.berviary);
		warp = beImg.parentNode;
		cover = document.createElement("div");
		warp.appendChild(cover);
		cover.style.position = "absolute";
		cover.style.top = "0px";
		cover.style.left = "0px";
		cover.style.backgroundColor = "#ccc";
		var opac = parseFloat(opts.opacity) || 0.3;
		cover.style.opacity = opac;
		cover.style.filter = "Alpha(Opacity=" + opac * 100 + ")";
		cover.style.width = "100%";
		cover.style.height = "100%";
		cover.style.zIndex = 2;
		cover.style.visibility = "hidden";
		clip = document.createElement("img");
		warp.appendChild(clip);
		clipWidth = (opts.clip && opts.clip.width) || "60px";
		clipHeight = (opts.clip && opts.clip.height) || "60px";
		clip.src = beImg.src;
		clip.className = beImg.className;
		clip.style.position = "absolute";
		clip.style.top = "0px";
		clip.style.left = "0px";
		clip.style.clip = "rect(0px," + clipWidth + "," + clipHeight + ",0px)";
		clip.style.backgroundColor = "#ccc";
		clip.style.zIndex = 3;
		//ie里面会变态的自动设置宽度和高度
		clip.removeAttribute("width");
		clip.removeAttribute("height");
	})();
	function layerPos(e){
		if(e.layerX && e.layerY){
			return {x:e.layerX,y:e.layerY};
		} else {
			return {x:e.x,y:e.y};
		}
	}
	function absolutePos(e){
		if(e.pageX && e.pageY){
			return {x:e.pageX,y:e.pageY};
		} else {
			var x = e.clientX + (document.documentElement.scrollLeft?document.documentElement.scrollLeft : document.body.scrollLeft);
			var y = e.clientY + (document.documentElement.scrollTop?document.documentElement.scrollTop : document.body.scrollTop);
			return {x:x,y:y};
		}
	}
	var showExpand = function(x,y,e){
		if(!expand){
			initExpand();
		}
		expImg.style.left = (-1 * x) * mult + "px";
		expImg.style.top = (-1 * y ) * mult + "px";
		if((!opts.expand) || (!opts.expand.id)){
			var aPos = absolutePos(e);
			expand.style.left = aPos.x + parseFloat(clipWidth) / 2 + 20 + "px";
			expand.style.top = aPos.y + "px";
		}
		//初始化放大的div
		function initExpand(){
			if(opts.expand && opts.expand.id) {
				expand = _(opts.expand.id);
			} else {
				expand = document.createElement("div");
				if(opts.expand && opts.expand.style){
					for(var p in opts.expand.style){
						expand.style[p] = opts.expand.style[p];
					}
				}
				expand.style.border = "1px solid #000";
				expand.style.position = "absolute";
				expand.style.left ="0";
				expand.style.right = "0";
				expand.style.display = "block";
				document.body.appendChild(expand);
				if(clip.clientWidth>clip.clientHeight){
					expand.style.width = clip.clientWidth + "px";
					expand.style.height = clip.clientWidth * parseFloat(clipHeight) / parseFloat(clipWidth) + "px";
				} else {
					expand.style.height = clip.clientHeight + "px";
					expand.style.width = clip.clientHeight * parseFloat(clipWidth) / parseFloat(clipHeight) + "px";
				}
				expand.style.zIndex = 4;
			}
			expand.style.overflow = "hidden";
			if((expand.style.position != "relative") && (expand.style.position != "absolute")){
				//变态的ie6和ie7的img如果为relative,需要设置其父节点也为relative,否则overflow:hidden无效
				expand.style.position = "relative";
				expand.style.left = "0";
				expand.style.top = "0";
			}
			expImg = document.createElement("img");
			expImg.src = beImg.src;
			expImg.style.position = "relative";
			expImg.style.left = "0px";
			expImg.style.top = "0px";
			expand.appendChild(expImg);
			expImg.removeAttribute("width");
			expImg.removeAttribute("height");
			//计算图片放大的倍数
			var ew = expand.clientWidth;
			var eh = expand.clientHeight;
			var cw = parseFloat(clipWidth);
			var ch = parseFloat(clipHeight);
			mult = (ew/cw < eh/ch) ? ew/cw : eh/ch;
			//调整放大图片的大小
			expImg.style.width = beImg.clientWidth * mult + "px";
			expImg.style.height = beImg.clientHeight * mult + "px";
		}
	}
	cover.onmousemove = clip.onmousemove = function(e){
		var e = e || event;
		var pos = layerPos(e);
		var x = pos.x;
		var y = pos.y;
		//判断x和y坐标有没有超出范围
		var w = parseFloat(clipWidth) / 2,h = parseFloat(clipHeight) / 2;
		x = (x < w)?w:x;
		y = (y < h)?h:y;
		x = (x > warp.clientWidth - w) ? (warp.clientWidth - w) : x;
		y = (y > warp.clientHeight - h) ? (warp.clientHeight - h) : y;
		clip.style.clip = "rect(" + (y - h) + "px," + (x + w) + "px," + (y + h) + "px," + (x - w) + "px)";
		showExpand(x - w,y - h,e);
	}
	warp.onmouseover = cover.onmouseover = clip.onmouseover = function(){
		//如果清除的定时器存在,则删除.
		if(closeTimeId){
			clearTimeout(closeTimeId);
			closeTimeId = null;
		}
		if(cover.style.visibility === "hidden"){
			cover.style.visibility = "visible";
		}
		if(expand && expand.style.display === "none"){
			expand.style.display = "block";
		}
	}
	warp.onmouseout = function(){
		//延迟一定时间后清除
		closeTimeId = setTimeout(function(){
			cover.style.visibility = "hidden";
			if((!opts.expand) || (!opts.expand.id)){
				expand.style.display = "none";
			}
		},130);
	}
	return {
	clear:function(){
		//在这里清除放大镜效果
		warp.removeChild(clip);
		warp.removeChild(cover);
		warp.onmouseout = null;
		if((!opts.expand) || (!opts.expand.id)){
			expand.style.display = "none";
		}
	}
}
}
 