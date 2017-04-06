
		<#if rightsDO.state == 0>
				<div class="rights_code_box cf">
					<div class="tit">
						<span class="rights_code">维权编号：<span>${id}</span></span>
					</div>
					<div class="rights_status cf">
			  				<p class="red bd">当前维权状态：
								维权申请中
							</p>
				  			<div id="time">
				  				您还有<span id = "stime" class="red bd"></span>处理本次维权协议
				  			</div>
				  			<p>
				  				如果未在该期限内与买家(${rightsDO.buyerNick})就维权协议达成一致或未拒绝本次维权申请，本次维权将会自动达成协议，买家将会受到维权或进入商品退还状态。
				  				请您在同意或拒绝操作前，尽快充分与买家沟通达成一致，避免误解。
				  			</p>
				  			<div id = "operateBtn">
					  			<a class="location_btn" href="javascript:;" onclick='sellerAgreeRights();' >同意维权申请</a>
					  			<a class="location_btn" id = "refuse" href="javascript:;" onclick='sellerRefuseRights();'>拒绝维权申请</a>
					  			<a class="location_btn" id = "msgBtn" href="javascript:;" >发表留言/上传凭证</a>
				  			</div>
				  			<form id='refuseForm' action="${base}/rights/sellerRefuseRights.htm" method="post">
					  			<div  id="refuse_reason" class="code_txt">
									<span style="vertical-align:top;color:red" >请填写拒绝理由：</span>
									<textarea  style="width:480px;" id="refuseReason" name="refuseReason" cols="40" rows="4">${refuseReason!}</textarea>
									<p style="padding-left:65px color:red">请控制在150字以内</p>
									<a class="location_btn" id = "refuseCommit" href="javascript:;" onclick="refuseCommit();">提交</a>
									<a class="location_btn" id = "refuseCancel" href="javascript:;" onclick="refuseCancel();">取消</a>
									<input type="hidden" name="rightsId" value="${rightsId}"/>
								</div>
							</form>
				  	</div>
				</div>
				<div class="rights_map">
					<span class="fl red">小提示：</span>
					<p class="red">1.请您注意维权处理时限，避免超时导致损失产生。</p>
					<p class="red">2.如果您对维权有异议，建议您在期限内积极与买家进行沟通协商，可以让买家修改维权申请。</p>
				</div>
	  	<#elseif rightsDO.state == 1>
						<#if rightsDO.buyerRequire=1>
							<div class="rights_code_box cf">
								<div class="tit">
									<span class="rights_code">维权编号：<span>${id}</span></span>
								</div>
								<div class="rights_status cf">
						  				<p class="red bd">当前维权状态：
											等待买家退还商品
										</p>
							  			<p>
							  				您同意了买家的维权申请，买家将在5天内将商品发出退还您，否则系统默认关闭本次维权。
							  			</p>
							  			<a class="location_btn" id = "msgBtn" href="javascript:;" >发表留言/上传凭证</a>
							  	</div>
							</div>
							<div class="rights_map">
								<span class="fl red">小提示：</span>
								<p class="red">1.请您注意维权处理时限，避免超时导致损失产生。</p>
								<p class="red">2.如果您对维权有异议，建议您在期限内积极与买家进行沟通协商，可以让买家修改维权申请。</p>
							</div>
						<#elseif rightsDO.buyerRequire=0>
								<div class="rights_code_box cf">
									<div class="tit">
										<span class="rights_code">维权编号：<span>${id}</span></span>
									</div>
									<div class="rights_status cf">
						  				<p class="red bd">当前维权状态：
											维权协议已达成，等待打款
										</p>
								  	</div>
								</div>
								<div class="rights_map">
									<span class="fl red">小提示：</span>
									<p class="red">1.该订单中针对此商品仅能申请一次维权，维权申请成功后将无法再次发起维权申请。</p>
								</div>
						</#if>	
		<#elseif rightsDO.state == 12>
					<div class="rights_code_box cf">
								<div class="tit">
									<span class="rights_code">维权编号：<span>${id}</span></span>
								</div>
								<div class="rights_status cf">
						  				<p class="red bd">当前维权状态：
											等待买家退还商品
										</p>
							  			<p>
							  				买家将在5天内将商品发出退还您，否则系统默认关闭本次维权。
							  			</p>
							  			<#if rightsDO.sellerRefuseReason??>
											<div class='tips-text'><p><strong>卖家拒绝理由：</strong>${rightsDO.sellerRefuseReason?html}！</p></div>
										</#if>
							  			<a class="location_btn" id = "msgBtn" href="javascript:;" >发表留言/上传凭证</a>
							  	</div>
					</div>
					<div class="rights_map">
						<span class="fl red">小提示：</span>
						<p class="red">1.请您注意维权处理时限，避免超时导致损失产生。</p>
						<p class="red">2.如果您对维权有异议，建议您在期限内积极与买家进行沟通协商，可以让买家修改维权申请。</p>
					</div>
		<#elseif rightsDO.state == 2>
					<div class="rights_code_box cf">
						<div class="tit">
							<span class="rights_code">维权编号：<span>${id}</span></span>
						</div>
						<div class="rights_status cf">
				  				<p class="red bd">当前维权状态：
									维权申请已拒绝
								</p>
					  			<p>
					  				如果买家未在您拒绝维权申请后的5天内申请客服介入处理，那么系统将自动默认为买家同意了您的拒绝维权申请操作，此维权申请将被关闭，并且无法再次申请。
					  			</p>
					  			<#if rightsDO.sellerRefuseReason??>
									<div class='tips-text'><p><strong>卖家拒绝理由：</strong>${rightsDO.sellerRefuseReason?html}！</p></div>
								</#if>
					  			<a class="location_btn" id = "msgBtn" href="javascript:;" >发表留言/上传凭证</a>
					  	</div>
					</div>
					<div class="rights_map">
						<span class="fl red">小提示：</span>
						<p class="red">1.您应该保管好您的证据，买家可能会在被您拒绝维权后申请客服介入，届时需要您提供相应的凭证。</p>
					</div>
		<#elseif rightsDO.state == 3 || rightsDO.state == 11>
					<div class="rights_code_box cf">
						<div class="tit">
							<span class="rights_code">维权编号：<span>${id}</span></span>
						</div>
						<div class="rights_status cf">
				  				<p class="red bd">当前维权状态：
									等待卖家确认收货
								</p>
					  			<div id="st">
					  				您还有<span id = "stime" class="red bd"></span>处理本次维权协议
					  			</div>
					  			<p>
					  				买家&nbsp;(${rightsDO.buyerNick})&nbsp;已经将退还的商品发出，等待您确认收货完成维权，如果您未在规定时间内收到退货物或您收到的货物有问题，可以点击申请客服介入，客服将在5个工作日内介入该笔维权协议。
					  			</p>
					  			<#if rightsDO.sellerRefuseReason??>
									<div class='tips-text'><p><strong>卖家拒绝理由：</strong>${rightsDO.sellerRefuseReason?html}！</p></div>
								</#if>
					  			<a class="location_btn" href="javascript:;" onclick='sellerConfirmReceipt()' >确认收到退货</a>
					  			<a class="location_btn" href="javascript:;" onclick='sellerApply()'>申请客服介入</a>
					  			<a class="location_btn" id = "msgBtn" href="javascript:;" >发表留言/上传凭证</a>
					  	</div>
					</div>
					<div class="rights_map">
						<span class="fl red">小提示：</span>
						<p class="red">1.请确保您的物流凭证保持完好，如果进入客服介入，可能需要您提供相关证据。</p>
					</div>
		<#elseif rightsDO.state == 4>
					<div class="rights_code_box cf">
						<div class="tit">
							<span class="rights_code">维权编号：<span>${id}</span></span>
						</div>
						<div class="rights_status cf">
				  				<p class="red bd">当前维权状态：
									客服介入中
								</p>
					  			<p>
					  				客服将会根据买卖双方提交的凭证及留言记录对本维权进行裁决，裁决结果为最终结果，如果买卖双方对于客服裁决结果有异议，可以拨打客服电话(4008-211-588)对客服人员的裁决结果进行申诉。
					  			</p>
								<#if rightsDO.sellerRefuseReason??>
									<div class='tips-text'><p><strong>卖家拒绝理由：</strong>${rightsDO.sellerRefuseReason?html}！</p></div>
								</#if>
					  			<a class="location_btn" id = "msgBtn" href="javascript:;" >发表留言/上传凭证</a>
					  	</div>
					</div>
					<div class="rights_map">
						<span class="fl red">小提示：</span>
						<p class="red">1.客服会在5个工作日内进入本维权申请，请耐心等待。</p>
						<p class="red">2.客服会在买卖双方都同意的情况下修改维权金额，所以买家卖家可以在本页面进行留言沟通，确定金额后通知客服修改维权金额。
						</p>
					</div>
		<#elseif rightsDO.state == 5 || rightsDO.state == 7>
					<div class="rights_code_box cf">
						<div class="tit">
							<span class="rights_code">维权编号：<span>${id}</span></span>
						</div>
						<div class="rights_status cf">
				  				<p class="red bd">当前维权状态：
									维权成功
								</p>
					  	</div>
					</div>
					<div class="rights_map">
						<span class="fl red">小提示：</span>
						<p class="red">1.该订单中针对此商品仅能发起一次维权，维权申请成功后将无法再次发起维权申请。</p>
					</div>
		<#elseif rightsDO.state == 6 || rightsDO.state == 8>
					<div class="rights_code_box cf">
						<div class="tit">
							<span class="rights_code">维权编号：<span>${id}</span></span>
						</div>
						<div class="rights_status cf">
				  				<p class="red bd">当前维权状态：
									维权关闭
								</p>
					  	</div>
					</div>
					<div class="rights_map">
						<span class="fl red">小提示：</span>
						<p class="red">1.该订单中针对此商品仅能发起一次维权，维权申请关闭后将无法再次发起维权申请。</p>
					</div>
		<#elseif rightsDO.state == 9>
					<div class="rights_code_box cf">
						<div class="tit">
							<span class="rights_code">维权编号：<span>${id}</span></span>
						</div>
						<div class="rights_status cf">
				  				<p class="red bd">当前维权状态：
									客服裁决成功，等待打款
								</p>
					  	</div>
					</div>
					<div class="rights_map">
						<span class="fl red">小提示：</span>
						<p class="red">1.客服裁决成功，等待打款。</p>
					</div>
		<#elseif rightsDO.state == 10>
					<div class="rights_code_box cf">
						<div class="tit">
							<span class="rights_code">维权编号：<span>${id}</span></span>
						</div>
						<div class="rights_status cf">
				  				<p class="red bd">当前维权状态：
									维权协议已达成，等待打款
								</p>
					  	</div>
					</div>
					<div class="rights_map">
						<span class="fl red">小提示：</span>
						<p class="red">1.卖家已确认收货，等待打款。</p>
					</div>
		</#if>
	<script type="text/javascript">
		
		var t;
		$('#refuse_reason').hide();
		$(document).ready(function(){
			$("#msgBtn").click(function(){
	    		$("#content").focus();
	    		$('html,body').animate({scrollTop:$('#content').offset().top + 'px'},800);
	  		});  
		});
		<#if rightsTime ?? && outOfDate?? && outOfDate=="false">
			var delay = ${rightsTime}/1000;
			showTimeout();
			function showTimeout(){
					delay = delay-1;
					if(delay>0){
						var date = Math.floor(delay/60/60/24);
						var hour = Math.floor((delay/60/60)%24);
						var minute = Math.floor((delay/60)%60);
						var second = Math.floor(delay%60);
						$('#stime').html(date+"天"+hour+"小时"+minute+"分"+second+"秒");
						setTimeout(showTimeout,1000);
					}else{
						//超时,更新维权状态;
						<#if rightsDO.state == 0 >
							document.location.href="${base}/rights/sellerAgreeRights.htm?rightsId=${rightsDO.id?js_string}";
						<#elseif rightsDO.state == 3 ||rightsDO.state == 11>
							document.location.href="${base}/rights/sellerApply.htm?rightsId=${rightsDO.id?js_string}";
						</#if>
						return;
					}
		 	 }
		 </#if>
		//卖家同意维权
		function sellerAgreeRights(){
			if(!confirm("您确认要同意买家的维权申请吗？")) {
				return;
			}
			document.location.href="${base}/rights/sellerAgreeRights.htm?rightsId=${rightsDO.id?js_string}";
		}
		//卖家拒绝维权,显示填写理由
		function sellerRefuseRights(){
			if(!confirm("您确认要拒绝买家维权申请吗？建议您谨慎操作,请输入拒绝理由后确认.")) {
				return;
			}
			$('#refuse_reason').show();
			$('#operateBtn').hide();
		}
		//卖家拒绝维权
		function refuseCommit(){
			var reason= $.trim($('#refuseReason').val());
			if(reason.length==0 ){
				alert('请输入拒绝理由！');
				return;
			}
			if(reason.length>150){
				alert('拒绝理由请控制在150字以内！');
				return;			
			}
			$('#refuseForm').submit();
			//document.location.href="${base}/rights/sellerRefuseRights.htm?rightsId=${rightsDO.id?js_string}";	
		   	
		}
		//卖家拒绝维权填写理由时,点击取消
		function refuseCancel(){
			$('#content').val('');
			$('#refuse_reason').hide();
			$('#operateBtn').show();
		}
		
		//卖家确认收货	
		function sellerConfirmReceipt(){
			document.location.href="${base}/rights/sellerConfirmReceipt.htm?rightsId=${rightsDO.id?js_string}";
		}
			
		//卖家申请客服介入
		function sellerApply(){
			document.location.href="${base}/rights/sellerApply.htm?rightsId=${rightsDO.id?js_string}";
		}
			
		// 限制文本域的输入
		function refusedReason(obj,a_limit){
			if (obj.value.length > a_limit) {
				obj.value = obj.value.substring(0,a_limit);
			}
		}
	</script>
					
				
			