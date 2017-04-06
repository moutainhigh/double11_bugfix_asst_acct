$(document).ready(function() {  
   $('.showBigImage').mouseover(function(){
		var Img = new Image();  
		Img.src = this.src;  
		if(Img.width == 0 && Img.height == 0){
			Img.onload = function (){
				var w = Img.width; 
				var h = Img.height;
				var w1 = document.body.clientWidth;
				var h1 = document.body.clientHeight;
				w = w >= w1 ? w1/2-100:w;
				h = h >= 600 ? 500:h;
				var $tip=$('<div id="showBigImage"><div class="showBigImage_box"><div><s><i></i></s><img height="'+h+'" width="'+w+'" src="'+this.src+'" /></div></div></div>');
				$('body').append($tip);
				$('#showBigImage').show('fast');	
			}
		}else{
			var w = Img.width; 
			var h = Img.height;
			var w1 = document.body.clientWidth;
			var h1 = document.body.clientHeight;
			w = w >= w1 ? w1/2-100:w;
			h = h >= 600 ? 500:h;
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
		var dialog = art.dialog({id: '123456789',title: '图片预览',background: '#F7F7F7',opacity: 0.3,padding: 0,width:500,height:400, lock: true});
		var Img = new Image();  
		Img.src = this.src;  
      if(Img.width == 0 && Img.height == 0){
			Img.onload = function (){
				var w =  Img.width;  
				var h =  Img.height;
				var w1 = document.body.clientWidth;
				w = w > w1 ? w1-410:w;
				h = h >= 950 ? 900:h;
				dialog.content('<img src="'+this.src+'" width="'+w+'" height="'+h+'" />');	
			}
		}else{
			var w =  Img.width; 
			var h =  Img.height;
			var w1 = document.body.clientWidth;
			w = w > w1 ? w1-410:w;
			h = h >= 950 ? 900:h;
			dialog.content('<img src="'+this.src+'" width="'+w+'" height="'+h+'" />');
		}
   });
});