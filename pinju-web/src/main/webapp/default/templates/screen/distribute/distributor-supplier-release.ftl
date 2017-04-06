<@load.js src="/modules.js"/>
<title>申请商品分销</title>
	<!--分销页面样式-->
	<link href="http://static.pinju.com/css/jin/sns-base.css" rel="stylesheet" />
	<link href="http://static.pinju.com/css/jin/management.css" rel="stylesheet" />
	<link href="http://static.pinju.com/css/shopDecoration/base.css?t=20111203.css" rel="stylesheet" /> 
<div class="management_page02"><!--外部大框架-->
	<div class="box_width_700px floatleft lable_bg_style margin_bottom">
		<div id="release" class="lable_present" style="cursor: default"> 招募书 </div>
        <div id="items" class="lable_after" style="cursor: pointer"> 产品目录 </div>
    </div>

  <div id="html_id" class="box_width_700px floatleft margin_bottom">
  		<input type="hidden" value="${supplierResult.memberId!''}" name="memberId"/>
  		<input type="hidden" value="${(supplierResult.nickName!'')?html}" name="nickName"/>
    	<ul class="bg_gray01 bottom_1px_gray"><strong>${(supplierResult.applyTitle!'')?html}</strong></ul>
    	<ul class="fontgray_03">
        	<li>${supplierResult.applyContent!''}</li>
        </ul>
      
        <ul class="bottom_1px_gray">
        <strong>供应商联系信息：</strong>
        	<li>联系人：
        	<a href="http://sns.pinju.com/dashboard/index?id=${(supplierResult.memberId)!''}"  target="_blank">
	        	${(supplierResult.nickName)!''}
	        </a>
             <input id="privateMail" name="button" type="button" class="gray_button_004 margin_left20" value="发私信">
             <!--<input id="concern" name="button" type="button" class="gray_button_004 margin_left20" value="关注他">-->
            </li>
        </ul>
        
        <ul style="text-align:center">
            <input id="confirm" name="button" type="button" class="orange_button_002" value="确认">
        </ul>
    </div>
	</div><!--底结束-->
</div><!--外部大框架end-->

        <div id="DialogMask" style="display:none">
        </div>
        <div id="Dialog">
            <div id="DialogShadow"></div>
            <div id="DialogLayout">
               <div id="DialogCaption">
                    <span>发送私信</span>
                    <a id="closeDiv" href="###"></a>
                </div>
					      <div id="mailDiv" class="sns_sendletter_flipbox">
							<div class="sns_sendletter_flipbox_main">
						           <div  class="sns_sendletter_flipbox_body">
						           	  <div class="line">
						           		<div class="textbox01">发给：</div>
						           	 	<div class="textbox02"><input name="text" type="text" class="sns_sendletter_flipbox_textarea01" value="${(supplierResult.nickName!'')?html}" disabled="true" readOnly="true"/></div>
						              </div>
						              
						              <div class="line2 fontgray_01">（请输入对方昵称）</div>
						              
						              <div class="line">
						                <div class="textbox01">内容：</div>
						             	<div class="textbox02"> <textarea id="content" max="200" name="text" class="sns_sendletter_flipbox_textarea02" style="overflow:hidden"></textarea></div>
						              </div>
									  
						              <div class="line2">
						             	<div class="textbox01"> <span class="text-align_center fontgray_01">说明：长度不能超过二百字</span><span id="caution" class="fontgray_01" ></span></div>
						                <div class=" textbox05"><input id="send" name="button" type="button" class="red_button_01"  value="发送" /></div>
						              </div>
						             </div>
						          </div> 
						     </div><!--main 结束-->
            </div>
        </div>
<script type="text/javascript">
var dialog;
	$(function(){
		$('#applyDistributor').addClass('count');
		
		dialog = new Dialog();
		jQuery.fn.extend({ 
	        showWordCount: function() { 
	            var _max = $(this).attr('max');
	            var _length = $(this).val().length;
	            var lengthHtml = _length > _max ?("<font color='red'>"+_length+"</font>"):_length;
	            $('#caution').html(lengthHtml + '/' + _max);
	        } 
	    });
		//$('#release').click(
			//function(){window.location.href = "/distributor/supplierRelease.htm?supplierParam.id=${(supplierParam.id!'')?js_string}";}
		//);
		$('#items').click(
			function(){window.location.href = "/distributor/supplierItems.htm?supplierItemParam.supplierId=${(supplierParam.id!'')?js_string}";}
		);
		$('#confirm').click(
			function(){window.location.href = "/distributor/applyDistributor.htm";}
		);
		$('#content').bind('propertychange input change', function() {
	        $(this).showWordCount();
	    });
	    
	    $('#content').focus(function(){    
	        $(this).showWordCount();
	        $('#caution').fadeIn('slow');
	    });
	    
	    $('#content').blur(function(){
	           $('#caution').fadeOut('slow');
	    });
		$('#concern').click(
			function(){
				$.ajax({
					data:"memberId="+$('input[name="memberId"]').val(),
					url:"/distributor/sendConcern.htm",
					type:"POST",
					success:function(data){
						if(data.status){
							alert(data.message);
						}else{
							alert(data.message);
						}
					},
					error:function(){}
				});
			}
		);
		$('#privateMail').click(
			function(){dialog.open('',432,295);}
		);
		$('#closeDiv').click(closeDiv);
		$('#send').click(
			function(){
				var _max = $('#content').attr('max');
	            var _length = $.trim($('#content').val()).length;
	            if(_length <= 0){
	            	alert('请输入信息内容');
	            }else if(_length <= _max){
					$(this).attr('disabled',true);
					$.ajax({
						data:"memberId="+$('input[name="memberId"]').val()+"&content="+encodeURIComponent($('#content').val())+"&nickName="+$('input[name="nickName"]').val(),
						url:"/distributor/sendMail.htm",
						type:"POST",
						success:function(data){
							if(data.status){
								alert(data.message);
								closeDiv();
							}else{
								alert(data.message);
							}
								$('#send').attr('disabled',false);
						},
						error:function(){$('#send').attr('disabled',false);}
					});
				}else{
					alert("输入的内容过长")
				}
			}
		);
	});
	
	var closeDiv = function(){$('#DialogMask').hide();$('#Dialog').hide();}
</script>