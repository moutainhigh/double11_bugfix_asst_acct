<title>分销商品管理</title>
	<!--分销页面样式-->
	<link href="http://static.pinju.com/css/jin/sns-base.css" rel="stylesheet" />
	<link href="http://static.pinju.com/css/jin/management.css" rel="stylesheet" />
	<link rel="stylesheet" href="http://static.pinju.com/css/shareBuying/shareBuying.css"/>
	<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css"/>
	<@load.js src="../artdialog/jquery.artDialog.js?skin=simple"/>
	<@load.js src="jquery.form.js"/>
	<@load.js src="../image-preview/jquery.image-preview.js"/>
<!--right -->
	<div class="right_850px floatright"><!--右侧开始 -->
  	<div class="right_content02"><!--分销大框架开始 -->
        
      <div class="box_806 padding_10px fontgray_01 margin_bottom">您好，${nickName!''}，欢迎进入云营销</div>
        
      <div class="box_806 padding_10px border_1px_gray bg_white margin_bottom"><span class="fontgray_03">我是买家 > 云营销 ></span> <span class="fontred">分销商品管理</span></div>
      
      <div class="box_806 padding_10px ltr_1px_gray bg_white fontgray_0 fontbold">基本信息：</div>
      <div class="box_806 padding_10px border_1px_orange bg_yellow01 margin_bottom">
      	<div class="floatleft">合作中的供应商<span class="fontred">（${itemQuery.applyStatusCount!0}家） </span></div>
        <div class="floatleft margin_left20">已分销商品 <span class="fontred">（${itemQuery.soldItemCount!0}件） </span></div>
      </div>
        
      <div class="box_806 padding_10px ltr_1px_gray bg_white fontgray_0 fontbold">商品管理：</div> 
             
<div class="box_806 lable_bg_style padding_lr_10px left_1px_gray right_1px_gray">
		   <div id="unsold" class="lable_present" style="cursor: default"> 未分销 </div>
            <div id="sold" class="lable_after" style="cursor: pointer">已分销</div>
		</div><!--标签 end --> 
        
        <!--<div class="box_806 lable_bg_style padding_10px ">
		    <#if (distrbuteSupplierItemDOList)?? && ( distrbuteSupplierItemDOList?size >0)>
	        	<input id="startSold" name="button" type="button" class="orange_button_005" value="批量分销">
	        <#else>
	        	<input name="button" type="button" class="orange_button_005" value="批量分销">
	        </#if>
        </div>-->
        
        <div class="box_826">
      	<form id="pageForm" name="pageForm" action="/" method="post">
        <table width="826" border="0">
          <tr class="bottom_1px_gray bg_gray01 fontbold">
            <td width="220">商品名 </td>
            <td width="100">零售价</td>
            <td width="126">分成比例</td>
            <td width="80">库存</td>
            <!--<td width="100">分销状态</td>-->
            <td width="100">&nbsp;</td>
          </tr>
<#if (distrbuteSupplierItemDOList)?? && (distrbuteSupplierItemDOList?size >0)>
		<#list distrbuteSupplierItemDOList as distrbuteSupplierItemDO>
          <tr class="bottom_1px_gray_dashed fontgray_03">
          	<input id="price_${distrbuteSupplierItemDO.itemId}" name="price" disabled="disabled" type="hidden" value="${(distrbuteSupplierItemDO.itemDO.priceByYuan)!0.00}">
	        <input id="curStock_${distrbuteSupplierItemDO.itemId}" name="curStock" type="hidden" disabled="disabled" value="${(distrbuteSupplierItemDO.itemDO.curStock)!''}">
	  		<input id="reward_${distrbuteSupplierItemDO.itemId}" name="reward" type="hidden" disabled="disabled" value="${(distrbuteSupplierItemDO.reward)!0}">
	  		<input id="picUrl_${distrbuteSupplierItemDO.itemId}" name="picUrl" type="hidden" disabled="disabled" value="${(distrbuteSupplierItemDO.itemDO.picUrl)!''}">
	  		<input id="title_${distrbuteSupplierItemDO.itemId}" name="title" type="hidden" disabled="disabled" value="${(distrbuteSupplierItemDO.itemDO.title!'')?html}">
            <td width="220">
		      	<a href="/detail/${(distrbuteSupplierItemDO.itemDO.id)!0}.htm" target="_blank">
			      <#if distrbuteSupplierItemDO.itemDO??>
		          	${(distrbuteSupplierItemDO.itemDO.title!'')?html}
		          </#if>
	          	</a>
			</td>
            <td width="100">
				<#if distrbuteSupplierItemDO.itemDO??>
	            	${distrbuteSupplierItemDO.itemDO.priceByYuan!0.00}
	            </#if>
			</td>
            <td width="126">${distrbuteSupplierItemDO.reward!0}%</td>
            <td width="80">
				<#if distrbuteSupplierItemDO.itemDO??>
	            	${distrbuteSupplierItemDO.itemDO.curStock!''}
	            </#if>
			</td>
            <td width="100">
            	<input supplierId="${distrbuteSupplierItemDO.supplierId}" itemId="${distrbuteSupplierItemDO.itemId}" name="startSold" name="button" type="button" class="orange_button_005" value="上架">  
			</td>
          </tr>
       </#list>
<#else>
<tr><td colspan="7" align='center'><label class="hong">查不到任何未分销商品信息！</label></td></tr>
</#if>   
       </table>
<#if (distrbuteSupplierItemDOList)?? && ( distrbuteSupplierItemDOList?size >0)>
	<div class="cbottom imgbtn">
	  <#include "/default/templates/control/bottomPage.ftl">
	</div>
</#if>
	  </form>
		<!--<div class="box_806 padding_10px fontgray_01" align="center">
        	<input name="button" type="button" class="orange_button_002" value="确定">
            <input name="button" type="button" class="gray_button_002 margin_left20" value="取消">	
        </div>-->

        </div>      
        
  	</div><!--分销大框架结束 -->

</div><!--右侧结束 -->
<!-- 弹出框 start -->
<div id="market-pop-div" class="market-pop-div cf">
	<div class="pop-head">
		<span>编辑分销商品</span>
	</div>
	<div class="pop-body">
		<div class="pop-item">
			<p class="item-name" id="itemName"></p>
			<ul class="pop-item-list cf">
				<li>零售价：<span id="price"></span></li>
				<li>返点比例：<span id="reward"></span></li>
				<li>返利方式：<span>统一返点</span></li>
				<li>商品数量：<span id="curStock"></span></li>
			</ul>
		</div>
		<div class="item-pic-box">
			<ul class="item-pic-tab cf">
				<li id="default" class="focus"><span>店铺商品图片</span></li>
				<li id="custom"><span>上传自定义图片</span>
					<div class="point"><b></b>(不如来点真人秀,细节图，更出彩哦!) </div>
				</li>
			</ul>
			<span class="upload-error" style="display: none;"></span>
			<div class="pic-box" id="shop-pic-box">
				<div class="shop-pic-box cf">
					<div class="img-box"><img name="defaultPicUrl" src="" alt="" title="" originalSrc="" /></div>
					<div class="textarea-box">
						<p>请输入您的推荐理由：</p>
						<textarea id="defaultTextArea" name="defaultTextArea">您最多可以输入100个字</textarea>
					</div>
				</div>
				<p class="btn-box cf"><a class="submit" name="default" href="javascript:;">确认</a><a class="cancel" href="javascript:;">取消</a> </p>
			</div>
			
			<div class="pic-box" id="upload-box">
				<div id="uploading" class="loading cf" style="display: none;">
					<p><img src="http://static.pinju.com/images/ajax/loadding.gif" /></p>
					<p class="txt">正在上传中.....</p>
				</div>
				<div id="data" style="display: none;">
				</div>
				<div id="afterUpload" class="complete cf">
					<div class="img-box">
						<span><img name="customPicUrl" src="../images/it1_10.gif" originalSrc="" /></span>
					</div>
					<div class="again-uplad">
					  	<form id="soldForm" name="soldForm" method="post" action="" submitType="">
					  		<input id="supplierId" name="supplierId" type="hidden" value="">
					  		<input id="itemId" name="itemId" type="hidden" value=""/>
					  		<input id="imageUrl" name="imageUrl" type="hidden" value=""/>
					  		<input id="submitType" name="submitType" type="hidden" value=""/>
							<p><span class="point">自定义图片为gif/jpg/png格式，宽度为194px 长度为122px至292，图片大小控制在50k以内</span></p>
							<a class="btn" href="javascript:;">选择上传图片</a>
							<input id="picFile" type="file" name="picFile" class="file"/>
							<textarea id="recommendReason" name="recommendReason" style="display: none;"></textarea>
  						</form>
					</div>
				</div>
				
				<div class="upload-pic cf">
					<div class="textarea-box">
						<p>请输入您的推荐理由：</p>
						<textarea id="customTextArea" name="customTextArea">您最多可以输入100个字</textarea>
					</div>
				</div>
				<p class="btn-box cf"><a class="submit" name="custom" href="javascript:;">确认</a><a class="cancel" href="javascript:;">取消</a> </p>
			</div>
		</div>
	</div>
</div>
<!-- 弹出框   end  -->
<script type="text/javascript">
var dialog = null;
	$(function(){
		$('#itemManagerDistributor').addClass('count');
	
		/** tab 跳转 */
		$('#sold').click(
			function(){window.location.href = "/distributor/soldManager.htm";}
		);
		$('.submit').each(
			function(){
				$(this).click(
					function(){
						var type = $('.focus').attr('id');
						if(type){
							var reason = $('#'+type+'TextArea').val();
							var file = $.trim($('#picFile').val());
							if(file == '' && type == 'custom'){
								$('.upload-error').html("请选择自定义图片");
							}
							if($.trim(reason) == '' || reason == "您最多可以输入100个字" || reason.length > 100){
								$('.upload-error').show();
								if(reason.length > 100){
									$('.upload-error').html("推荐理由不能大于100字");
								}else{
									$('.upload-error').html("请填写推荐理由");
								}
								return false;
							}else{
								$('.upload-error').hide();
							}
						}
						$("form[name='soldForm']").attr('submitType','submit').attr('action',"/distributor/sold.htm").submit();
					}
				);
			}
		);
		
		$('#picFile').each(
			function(){
				$(this).change(
					function(){
						$(this).parent("form[name='soldForm']").attr('submitType','check').attr('action',"/distributor/checkPic.htm").submit();
					}
				);
			}
		);
		
		$('.btn').each(function(){
			$(this).click(function(){
				$('#picFile').click();
			})
		});
		
		$('form[name="soldForm"]').each(
			function(){
				$(this).bind('submit', function(e) {
					/** picture type [custom or default type] */
					var type = $('.focus').attr('id');
					$('#submitType').val(type);
					if(type){
						/** reason set */
						$('#recommendReason').val($('#'+type+'TextArea').val());
					}
					/** picture url set [custom or default type]  */
					$('#imageUrl').val("");
					if(type == 'default'){
						$('#imageUrl').val($('img[name="defaultPicUrl"]').attr("originalSrc"));
					}
					
					type = $(this).attr('submitType');
					e.preventDefault();
					$(this).ajaxSubmit({
							dataType:'json',
							type:"post",
							success:function(callback){
								if(callback.status){
									ajaxSuccess(type,callback.message);
								}else{
									ajaxFailure(type,callback.message);
								}
							},beforeSubmit:function(data){
								uploading(data);
							}
					});
					return false;
				});
		});
		
		$('input[name="startSold"]').click(function(){
			/** init form */
			dialogInit();
		
			/** itemID、supplierID */
			var itemId = $(this).attr('itemId');
			$('#supplierId').val($(this).attr('supplierId'));
			$('#itemId').val(itemId);
			
			/** price、curStock、reward set */
			$('#price').html('￥'+$('#price_'+itemId).val());
			$('#curStock').html($('#curStock_'+itemId).val());
			$('#reward').html($('#reward_'+itemId).val()+'%');
			
			/** default pic、title set */
			var title = $('#title_'+itemId).val();
			$('#itemName').html(title);
			$('img[name="defaultPicUrl"]').attr("alt",title);
			$('img[name="defaultPicUrl"]').attr("title",title);
			var prefixPicUrl = '${(imageServer!'')?js_string}';
			var originalPicUrl = $('#picUrl_'+itemId).val();
			$('img[name="defaultPicUrl"]').attr("src",prefixPicUrl + originalPicUrl);
			$('img[name="defaultPicUrl"]').attr("originalSrc",originalPicUrl);
			
			/** show dialogue  start */
			dialog = art.dialog({
				resize: false,
				lock:true,
				padding:0,
				content: document.getElementById('market-pop-div')
				
		 	});
			$(".pic-box").hide();
			$("#shop-pic-box").show();
			
			$(".cancel").click(function(){
				dialogInit();
				dialog.close();
			}); 
			$(".item-pic-tab>li").removeClass("focus").eq(0).addClass("focus");
			/** show dialogue end */
		});		   
		var $tabs=$(".item-pic-tab>li");
		$tabs.click(function(){
			$tabs.removeClass("focus");
			$(this).addClass("focus");
			var tabIndex=$(this).index()
			$(".pic-box").hide();
			$(".pic-box").eq(tabIndex).show();
			$('.upload-error').hide();
		})
		
		$(".textarea-box textarea").focus(function(){
			if($(this).val()=="您最多可以输入100个字"){
				$(this).val("").addClass("c");
			}
		});
		
		$(".textarea-box textarea").blur(function(){
			if($(this).val()==""){
				$(this).val("您最多可以输入100个字").removeClass("c");
			}
		});	
		
		$.imagePreview({
			file : $('#picFile'),
			img : $('img[name="customPicUrl"]'),
			maxWidth : 130,
			maxHeight : 140
		});
	});
	
	function resetFormFun(){
		$('form[name="soldForm"]').each(
			function(){
		  		$('#supplierId').val('');
		  		$('#itemId').val('');
		  		$('#imageUrl').val('');
		  		$('#recommendReason').val('');
		  		$('#submitType').val('');
		  		resetFileDOM();
			}
		);
	}
	
	function resetFileDOM(){
		$('#picFile').each(function(){
			var tempForm = document.createElement('form');
			$(this).before(tempForm);
			$(tempForm).append($(this));
			tempForm.reset();
			$(tempForm).after($(this));
			$(tempForm).remove();
			setDefaultImage();
		});
	}
	
	function ajaxSuccess(type,message){
		$('.upload-error').hide();
		if(type == 'check'){
			$('#afterUpload').show();
			$('#uploading').hide();
		}else if(type == 'submit'){
			$(".cancel").click();
			window.location.href = "/distributor/unsoldManager.htm";
		}
	}
	
	function ajaxFailure(type,message){
		resetFileDOM();
		if(type == 'check'){
			$('#afterUpload').show();
			$('#uploading').hide();
			$('.upload-error').show();
			$('.upload-error').html(message);
		}else if(type == 'submit'){
			$('.upload-error').html(message);
		}
	}
	
	function uploading(data){
		$('#afterUpload').hide();
		$('#uploading').show();
	}
	
	function dialogInit(){
		$('#afterUpload').show();
		$('#uploading').hide();
		$('.upload-error').hide();
		resetFormFun();
	}

	function setDefaultImage(){
		$('img[name="customPicUrl"]').attr("src","http://static.pinju.com/images/upload_Default.gif").css({'width': '130px', 'height': '140px'});
	}

</script>