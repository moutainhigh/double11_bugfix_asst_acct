<LINK href="${staticServer}/css/goods/stint_code.css" rel="stylesheet" />	
	<div class="wrap cf" style="min-height:400px;">
		<div class="map_bar">
			您的位置：
			<a href="http://www.pinju.com" target="_blank">品聚网</a> > <span id="navMsg">领取限购码 </span>
		</div>
		<#-- 第一页验证码校验-->
		<div id = "codeH">
			<div id ="valCode" class="get_stint_code">
				<span class="blue">输入验证码正确后即可领取限购验证码</span>
				<div class="get_bar">
					<p>
						<input type="text" name="captcha" maxlength="4" id="captcha" />
						<input type="hidden" name="sidValue" id ="sidValue" value="${login.sid?html}" />
						<img class="code_pic" width="80" height="30" src="${base}/e/captcha.htm?sid=${login.sid?html}" 
							basesrc="${base}/e/captcha.htm?sid=${login.sid?html}" 
							style="border:1px solid #C4C4C4;margin:0 5px;cursor:pointer;" 
							title="看不清，点击图片换一张" id="captcha-img" />
						<a href="javascript:void(0);" id="captcha-change">看不清 ,换一张</a>
					</p>
					<a class="btn1" href="javascript:void(0);">确　定</a>
				</div>
				
			</div>
			
			<div  id="code_txt" class="code_txt">
					<p>限购码使用说明：</p>
					<p>1）	在限购特供活动中的商品必须使用限购码才能购买；</p>
					<p>2）	一个限购码只能下一次订单；</p>
					<p>3）	由于关闭页面后您可能会找不到您已领取的限购码，所以建议您成功获得一个限购码后立刻使用各种方式记录下来；</p>
					<p>4）	限购码并非实名制，您可以把您领取的限购码分享给您的朋友们！</p>
			</div>
			
			<div class="stint_pop" id = "stint_pop" style="display:none">
				<div class="stint_main">
					<a class="stint_pop_close" href="javascript:void(0);"></a>
					
					<div class="stint_pop_tit">信息提示</div>
					<div class="stint_cont">
						<p class="m30" id="result"></p>
						<a class="btn2 m25" href="javascript:void(0);">确　定</a>
					</div>
				</div>
			</div>
		</div>
		<#-- 第二页限购码领取-->
		<div class="stint_box cf" id = "XianGouGet" style="display:none">
			<div class="stint_box_l cf">
				<div class="stint_code" id = "redSucc">
					<p class="red" >限购码领取成功！</p>
					<div>请妥善保存您的限购码：<span class="red bd" id="redCode"></span></div>
				</div>
			
				<div class="stint_code" id="redFail">
					<p class="red">限购码领取失败！</p>
					<div class="bd" id="bdMsg"></div>
				</div>

			</div>
			<div class="stint_box_r cf" id="stint_box_r">
				<h2>适用商品:</h2>
				<ul class="itemwrap">
						<li class="item" id="item1">
							<div class="img">
								<img src="#" width="108px" height="108px" id="imgSrc1"/>
							</div>
							<div class="title">
								<a class="items_name" href="javascript:void(0);" id="items_name1"></a>
							</div>
							<div class="btn">
								<a class="buy_btn" id="buy_btn1" href="javascript:void(0);">快去抢购</a>
							</div>
						</li>
						<li class="item" id="item2">
							<div class="img">
								<img src="#" width="108px" height="108px" id="imgSrc2"/>
							</div>
							<div class="title">
								<a class="items_name" href="javascript:void(0);" id="items_name2"></a>
							</div>
							<div class="btn">
								<a class="buy_btn" id="buy_btn2" href="javascript:void(0);">快去抢购</a>
							</div>
						</li>			
						<li class="item" id="item3">
							<div class="img">
								<img src="#" width="108px" height="108px" id="imgSrc3"/>
							</div>
							<div class="title">
								<a class="items_name" href="javascript:void(0);" id="items_name3"></a>
							</div>
							<div class="btn">
								<a class="buy_btn" id="buy_btn3" href="javascript:void(0);">快去抢购</a>
							</div>
						</li>			
						<li class="item" id="item4">
							<div class="img">
								<img src="#" width="108px" height="108px" id="imgSrc4"/>
							</div>
							<div class="title">
								<a class="items_name" href="javascript:void(0);" id="items_name4"></a>
							</div>
							<div class="btn">
								<a class="buy_btn" id="buy_btn4" href="javascript:void(0);">快去抢购</a>
							</div>
						</li>			
						<li class="item" id="item5">
							<div class="img">
								<img src="#" width="108px" height="108px" id="imgSrc5"/>
							</div>
							<div class="title">
								<a class="items_name" href="javascript:void(0);" id="items_name5"></a>
							</div>
							<div class="btn">
								<a class="buy_btn" id="buy_btn5" href="javascript:void(0);">快去抢购</a>
							</div>
						</li>			
				</ul>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	
		//提交验证码，领取
		function getCode(){
			var url = "${base}/sales/code.htm";
			var valCode = $('#captcha').val();
			var valSid = $('#sidValue').val();
			$.ajax({
					url:url,
	        		type: "post",
			        cache: false,
			        dataType: 'json',
			        async : true,
	    			data: {captcha:valCode,sid:valSid},
	    			success: function(date) {
	    				if(date.flag=="error"){
							if(date.resFlag=="0"){
								$('#result').html("该地址无效，请重新检查该链接来源！");
								$('#stint_pop').show();
							}else if(date.resFlag=="1" || date.resFlag=="3" ||date.resFlag=="5" ||date.resFlag=="8"){
								$('#result').html("系统繁忙，请稍后再试！");
								$('#stint_pop').show();
							}else if(date.resFlag=="7"){
								$('#result').html("验证码输入错误！");
								$('#stint_pop').show();
							}else if(date.resFlag=="2"){
								$('#result').html("该商品已下架或删除！");
								$('#stint_pop').show();
							}else if(date.resFlag=="10"){
								$('#result').html("该商品不存在！");
								$('#stint_pop').show();
							}else if(date.resFlag=="4"){
								$('#bdMsg').html("限购活动已经结束！");	
								$('#navMsg').html("信息提示");
								$('#code_txt').hide();	
								$('#codeH').hide();
								$('#stint_box_r').hide();
								$('#XianGouGet').show();
								$('#redFail').show();
							}else if(date.resFlag=="6"){
								$('#bdMsg').html("此活动的限购码已被领完！");	
								$('#navMsg').html("信息提示");	
								$('#codeH').hide();
								$('#stint_box_r').hide();
								$('#XianGouGet').show();
								$('#redFail').show();
							}else if(date.resFlag=="11"){
								$('#bdMsg').html("该商品暂未参加活动！！！");	
								$('#navMsg').html("信息提示");	
								$('#codeH').hide();
								$('#stint_box_r').hide();
								$('#XianGouGet').show();
								$('#redFail').show();
							}else{
								$('#result').html("系统繁忙，请稍后再试！");
								$('#stint_pop').show();
							}
							changeCaptcha();
	    				}else if(date.flag=="success"){
	    					$('#navMsg').html("信息提示");
							$('#redCode').html(date.code);
							$('#codeH').hide();
							$('#code_txt').hide();
							$('#XianGouGet').show();
							$('#redSucc').show();
							if(date.login=="false"){
								$('#stint_login').css('display','block');
							}
							if(date.size==1){
								$('#items_name1').text(date.itemDO1.title);	
								$('#buy_btn1').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO1.id+".htm",'target':'_blank'});
								});
								$('#items_name1').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO1.id+".htm",'target':'_blank'});
								});
								$('#imgSrc1').attr({'src': "${imageServer}"+date.itemDO1.picUrl});
								$('#item2').hide();
								$('#item3').hide();
								$('#item4').hide();
								$('#item5').hide();
							}
							if(date.size==2){
								$('#items_name1').text(date.itemDO1.title);	
								$('#buy_btn1').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO1.id+".htm",'target':'_blank'});
								});
								$('#items_name1').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO1.id+".htm",'target':'_blank'});
								});
								$('#imgSrc1').attr({'src': "${imageServer}"+date.itemDO1.picUrl});
								
								$('#items_name2').text(date.itemDO2.title);	
								$('#buy_btn2').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO2.id+".htm",'target':'_blank'});
								});
								$('#items_name2').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO2.id+".htm",'target':'_blank'});
								});
								$('#imgSrc2').attr({'src': "${imageServer}"+date.itemDO2.picUrl});
								
								$('#item3').hide();
								$('#item4').hide();
								$('#item5').hide();
							}
							if(date.size==3){
								$('#items_name1').text(date.itemDO1.title);	
								$('#buy_btn1').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO1.id+".htm",'target':'_blank'});
								});
								$('#items_name1').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO1.id+".htm",'target':'_blank'});
								});
								$('#imgSrc1').attr({'src': "${imageServer}"+date.itemDO1.picUrl});
								
								$('#items_name2').text(date.itemDO2.title);	
								$('#buy_btn2').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO2.id+".htm",'target':'_blank'});
								});
								$('#items_name2').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO2.id+".htm",'target':'_blank'});
								});
								$('#imgSrc2').attr({'src': "${imageServer}"+date.itemDO2.picUrl});
								
								$('#items_name3').text(date.itemDO3.title);	
								$('#buy_btn3').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO3.id+".htm",'target':'_blank'});
								});
								$('#items_name3').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO3.id+".htm",'target':'_blank'});
								});
								$('#imgSrc3').attr({'src': "${imageServer}"+date.itemDO3.picUrl});
								
								$('#item4').hide();
								$('#item5').hide();
							}
							if(date.size==4){
								$('#items_name1').text(date.itemDO1.title);	
								$('#buy_btn1').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO1.id+".htm",'target':'_blank'});
								});
								$('#items_name1').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO1.id+".htm",'target':'_blank'});
								});
								$('#imgSrc1').attr({'src': "${imageServer}"+date.itemDO1.picUrl});
								
								$('#items_name2').text(date.itemDO2.title);	
								$('#buy_btn2').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO2.id+".htm",'target':'_blank'});
								});
								$('#items_name2').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO2.id+".htm",'target':'_blank'});
								});
								$('#imgSrc2').attr({'src': "${imageServer}"+date.itemDO2.picUrl});
								
								$('#items_name3').text(date.itemDO3.title);	
								$('#buy_btn3').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO3.id+".htm",'target':'_blank'});
								});
								$('#items_name3').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO3.id+".htm",'target':'_blank'});
								});
								$('#imgSrc3').attr({'src': "${imageServer}"+date.itemDO3.picUrl});
								
								$('#items_name4').text(date.itemDO4.title);	
								$('#buy_btn4').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO4.id+".htm",'target':'_blank'});
								});
								$('#items_name1').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO4.id+".htm",'target':'_blank'});
								});
								$('#imgSrc4').attr({'src': "${imageServer}"+date.itemDO4.picUrl});
								
								$('#item5').hide();
							}
							if(date.size==5){
							
								$('#items_name1').text(date.itemDO1.title);	
								$('#buy_btn1').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO1.id+".htm",'target':'_blank'});
								});
								$('#items_name1').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO1.id+".htm",'target':'_blank'});
								});
								$('#imgSrc1').attr({'src': "${imageServer}"+date.itemDO1.picUrl});
								
								$('#items_name2').text(date.itemDO2.title);	
								$('#buy_btn2').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO2.id+".htm",'target':'_blank'});
								});
								$('#items_name2').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO2.id+".htm",'target':'_blank'});
								});
								$('#imgSrc2').attr({'src': "${imageServer}"+date.itemDO2.picUrl});
								
								$('#items_name3').text(date.itemDO3.title);	
								$('#buy_btn3').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO3.id+".htm",'target':'_blank'});
								});
								$('#items_name3').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO3.id+".htm",'target':'_blank'});
								});
								$('#imgSrc3').attr({'src': "${imageServer}"+date.itemDO3.picUrl});
								
								$('#items_name4').text(date.itemDO4.title);	
								$('#buy_btn4').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO4.id+".htm",'target':'_blank'});
								});
								$('#items_name1').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO4.id+".htm",'target':'_blank'});
								});
								$('#imgSrc4').attr({'src': "${imageServer}"+date.itemDO4.picUrl});
							
								$('#items_name5').text(date.itemDO5.title);	
								$('#buy_btn5').click(function(){ 	
									$(this).attr({'href': "${base}/detail/"+date.itemDO5.id+".htm",'target':'_blank'});
								});
								$('#items_name5').click(function(){
									$(this).attr({'href': "${base}/detail/"+date.itemDO5.id+".htm",'target':'_blank'});
								});
								$('#imgSrc5').attr({'src': "${imageServer}"+date.itemDO5.picUrl});
							}
							
							
	    					changeCaptcha();    				
	    				}else {
							$('#result').html("系统繁忙，请稍后再试！");
							$('#stint_pop').show();
						}
	    			}
	        })
		}
		var commit = function() {
				var valCode = $('#captcha').val();
				var valSid = $('#sidValue').val();
				if(valCode=="" ){
					$('#result').html("请输入验证码！");
					$('.stint_pop').show();
					return;
				}else if(valCode.length<4){
					$('#result').html("请输入四位验证码！！");
					$('.stint_pop').show();
					return;
				}else{
					getCode();
				}
		}
		
		$(function(){
			document.onkeydown=function(e){
				var ev = document.all?window.event:e;
				if(ev.keyCode==13){
					commit();
				}
			}
		})
		
		$(document).ready(function() {
			$('#stint_pop').hide();
			$('#XianGouGet').hide();
			$('#redFail').hide();
			$('#redSucc').hide();
			$('.stint_pop_close').click(function(){
				$('.stint_pop').hide();
			});
			$('.get_bar .btn1').click(function(){
				commit();
			});
			$('.stint_cont .btn2').click(function() {
				$('.stint_pop').hide();
			});
		});
		
	</script>
<script type="text/javascript" src="http://static.pinju.com/js/member/captcha-min.js"></script>