<title>维权详情页面</title>
	<div class="rights_map">
		您的位置： <span><a href="/my/sell.htm">我是卖家</a></span>> <span><a href="/rights/sellerRightsList.htm">维权管理</a></span> > <span class="bd red">维权详情</span>
	</div>
	<#if query?? && query.rightsDO??>
	     	<#assign rightsDO = query.rightsDO>	
	     	<#assign id=rightsDO.id>
	     	<#assign rightsId=rightsDO.id>
		<!-- 左侧订单信息 -->
		<div class="tuikuanleft">   
			<#include "/default/templates/control/rights/rightsLeftOrder.ftl">
		</div>
		<!-- 左侧订单信息  end-->
		
		<div class="right_layout">
            <div class="rights_course">
				<#if rightsDO.state == 0 ||rightsDO.state == 2||rightsDO.state == 4||rightsDO.state == 3||rightsDO.state == 11>
					<span class="course1_b"></span>
					<span class="course2_a"></span>
					<span class="course3_b"></span>
				<#elseif rightsDO.state == 1>
					<#if rightsDO.buyerRequire=1>
						<p class="refund_status">
							<span class="course1_b"></span>
							<span class="course2_a"></span>
							<span class="course3_b"></span>
						</p>
					<#elseif rightsDO.buyerRequire=0>	
						<span class="course1_b"></span>
						<span class="course2_b"></span>
						<span class="course3_a"></span>
					</#if>	
				<#elseif rightsDO.state == 5 || rightsDO.state == 6 || rightsDO.state == 7 || rightsDO.state == 8 || rightsDO.state == 9 || rightsDO.state == 10>
					<span class="course1_b"></span>
					<span class="course2_b"></span>
					<span class="course3_a"></span>
				</#if>
				
            </div>
			<!-- 右上部分导航信息  -->
				<#include "/default/templates/control/rights/rightsRightUp.ftl">
			<!-- 右上部分导航信息  end-->
			
			<!-- 维权部分  -->
				<#include "/default/templates/control/rights/rightsRightMiddle.ftl">
			<!-- 维权部分  end-->
			
			<!-- 留言部分 -->
			<div class="rights_box cf">
			<div class="tit">				
					<span class="txt">留言/凭证记录<a name="upload"></a></span>
			</div>
				<!-- 留言内容列表 -->
					<#include "/default/templates/control/rights/rightsMsgList.ftl">							
				<!-- 留言框 -->
				<#if rightsDO.state !=5 && rightsDO.state !=6 && rightsDO.state !=7 && rightsDO.state !=8>
					<#include "/default/templates/screen/rights/sellerSendMessage.ftl">
				</#if>
			</div>				
			<!--留言部分  end-->
			
			<div class="rights_box cf">
				<div class="tit">
					<span class="txt">维权协议状态</span>
				</div>
				<#if rightsDO.state == 0>
					<p class="refund_status">
							买家&nbsp;(<span>${rightsDO.buyerNick}</span>)申请维权，等待卖家处理。
					</p>
				<#elseif rightsDO.state == 1>
					<#if rightsDO.buyerRequire=1>
						<p class="refund_status" style="color: #666;">
							卖家&nbsp;(<span>${rightsDO.sellerNick}</span>)已经同意了维权申请，维权协议达成<br /><br />
							等待买家&nbsp;(<span>${rightsDO.buyerNick}</span>)退还商品。
						</p>
					<#elseif rightsDO.buyerRequire=0>	
						<p class="refund_status">
							卖家&nbsp;(<span>${rightsDO.sellerNick}</span>)已经同意了维权申请，维权协议达成，买家(${rightsDO.buyerNick})将收到退款。
						</p>
					</#if>	
				<#elseif rightsDO.state == 12>
					<p class="refund_status" style="color: #666;">
							等待买家&nbsp;(<span>${rightsDO.buyerNick}</span>)退还商品。
					</p>
				<#elseif rightsDO.state == 2>
					<p class="refund_status">
						卖家&nbsp;(<span>${rightsDO.sellerNick}</span>)已经拒绝了维权申请。
					</p>
				<#elseif rightsDO.state == 3 || rightsDO.state == 11>
					<p class="refund_status">
						卖家&nbsp;(<span>${rightsDO.sellerNick}</span>)已经同意了维权申请。<br /><br />
						买家已退回商品，等待卖家确认收获。
					</p>
				<#elseif rightsDO.state == 4>
					<p class="refund_status">
						客服介入中。
					</p>
				<#elseif rightsDO.state == 5 || rightsDO.state == 7>
					<p class="refund_status">
						卖家&nbsp;(<span>${rightsDO.sellerNick}</span>)已经同意了维权申请，维权协议达成，请检查您的支付账户。
					</p>
				<#elseif rightsDO.state == 6 || rightsDO.state == 8>
					<p class="refund_status">
						维权关闭
					</p>
				<#elseif rightsDO.state == 9>
					<p class="refund_status">
						客服裁决成功，等待打款
					</p>
				<#elseif rightsDO.state == 10>
					<p class="refund_status">
						维权协议已达成，等待打款
					</p>
				</#if>
			</div>
			<#if rightsDO.state == 3 || rightsDO.state == 4  || (rightsDO.state == 5 && rightsDO.buyerRequire=1)|| (rightsDO.state == 6 && rightsDO.buyerRequire=1)|| (rightsDO.state == 7 && rightsDO.buyerRequire=1)|| (rightsDO.state == 8 && rightsDO.buyerRequire=1)|| (rightsDO.state == 9 && rightsDO.buyerRequire=1)||rightsDO.state == 10||rightsDO.state == 11>
                  <#if query.rightsLogisticsDO??>
					<#assign rightsLogisticsDO = query.rightsLogisticsDO>	                                                                                                                                                                                          
						<div class="rights_box cf">
							<div class="tit">
								<span class="txt">退还商品物流信息</span>
							</div>
								<table width="730">                                                                                                                                                                                        
		                     		<tbody>
		                              <tr align="center">                                                                                                                                                                     
		                                <th class="bla2">物流名称</th>
										<th class="bla3">运单号码</th>
										<th class="bla1">备注</th>
										<th class="bla4">操作</th>                                                                                                                                                  
		                              </tr>
			                              <tr align="center">                                                                                                                                                                            
			                                <td valign="middle">${rightsLogisticsDO.logisticsName}</td>                                                                                                                                                                                        
			                                <td valign="middle">${rightsLogisticsDO.billNo}</td>                                                                                                                                
			                                <td valign="middle"><#if rightsLogisticsDO.comments??>${rightsLogisticsDO.comments}</#if></td>                                                                           
			                                <td valign="middle">--</td>
			                              </tr>
		      						</tbody>
		      					</table>      
						</div>
                  </#if>                                                                                                                                                                                   
			</#if>
		</div>
		<script language="JavaScript" type="text/javascript">
		 	 
			//返回卖家维权列表
			function returnrightlist(){
				document.location.href="${base}/rights/sellerRightsList.htm";	
			}
			//卖家发表信息
			function sendMessage(){
				document.location.href="${base}/rights/showSellerSendMessage.htm?rightsId=${id?js_string}";
			}
			
			
		</script>  
	</#if>