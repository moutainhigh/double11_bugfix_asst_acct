<title>维权列表</title>
<LINK href="http://static.pinju.com/css/rights/xiaobao.css" rel="stylesheet" />
<div class=" boxh0 top10 dajin0629-juzhong"><!--右侧 -->	   
	<div class="positionbanner t4">你的位置：<a href="#">我是卖家 <i></i></a>><a href="#">维权管理<i></i></a></div><!--顶部引导条结束 --> 
	<script src="${base}/default/js/datePicker/WdatePicker.js"></script>
	<form action="http://www.pinju.com/rights/sellerRightsList.htm" id="pageForm" name="pageForm" method="post">
	<div>
		<ul class="t2 paddingbot5">
		  <li style="padding:12px 0 0 8px">
		  		订单编号：
		  		<input type="text" maxlength="100" name="orderId" value="<#if orderId??>${orderId}</#if>"/>
		  		从
				<input type="text" name="beginDate" value="<#if beginDate??>${beginDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="Wdate" id="beginDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
			 	到
			    <input type="text" name="endDate" id="endDate" class="Wdate" value="<#if endDate??>${endDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		  </li>
		  <li style="padding:12px 0 0 8px">
				维权编号：
				<input type="text" maxlength="100" name="rightsIdString" value="<#if rightsIdString??>${rightsIdString}</#if>"/>　
				维权状态：
			        <select name="status" id="status">
			           <option value="-1">全部</option>
			           <option value="0">等待卖家处理</option>
			           <option value="1">等待买家退还商品</option>
			           <option value="2">拒绝的维权申请</option>
			           <option value="3">等待卖家确认收货</option>
			           <option value="4">客服介入中</option>
			           <option value="5">卖家同意,维权成功</option>
			           <option value="6">维权关闭</option>
			           <option value="7">客服仲裁维权成功</option>
			           <option value="8">客服仲裁维权关闭</option>
			           <option value="9">客服仲裁成功,等待打款</option>
			           <option value="10">卖家同意,等待打款</option>
			      	</select>
			      	<span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="搜索" onclick="$('#pageForm').submit();" /></span>
			      	<span style="color:red"><#if errorMessage??>${errorMessage}</#if></span>
		  </li></br>
		</ul>
	</div>
	<div class=" boxh0 top10 floatleft t2"><!--大框架线 -->  
		<!--1、title-->	
		<div class="boxpadding floatleft t3"><strong class=" zi_14size">我的维权记录</strong></div>
		<div class="boxh2 floatleft t5"><div class="dajin0628-boxred120 floatleft"></div></div>
        <div class=" dajin0628-boxw700-01"><!--2、表-->
        	<div class="dajin0628-boxw700-02"><!--2、1标签-->            
        		<div class="dajin0628-biaoqian001">我收到的维权申请<#if query.items??>(${query.items})<#else>(0)</#if></div>
                <div class="dajin0628-biaoqiankongbai"></div>
                <!--<div class="dajin0628-biaoqian002">我收到的维权申请</div>-->
                <div class="fudongceng03">
                    <!--
                    <div class="dajin0628-boxfloatright">
                        <select name="select">
                        <option>维权状态</option>
                        </select>
                        </div>
                    </div>
                    -->
                </div><!--2、1结束--> 
                <!--2、2表格-->
                <div class=" dajin0628-boxw700-03">
					
                   		<table width="700" class=" dajin0630-tablejianju">
                          	<tr align="center" class="dajin0628-zi-jiacu">
                            	<td>维权编号</td>
                            	<td>订单编号</td>
                            	<td>买家</td>
                            	<td>交易金额(元)</td>
                            	<td>维权金额(元)</td>
                            	<td>维权要求</td>
                            	<td>维权状态</td>
                            	<td>发起时间</td>
                            	<td>操作</td>
                          	</tr>
                         <#if query??>
                          	<#if query.rightsDOs??>
                            <#assign rightsDOs = query.rightsDOs>
                            <#list rightsDOs as rightsDO>
	                        <tr>
	                     		<td>${(rightsDO.id)!}</td>
	                            <td>${(rightsDO.orderId)!}</td>
	                            <td>${(rightsDO.buyerNick)!}</td>
	                            <#assign price=rightsDO.price/100>
	                            <#assign applyPrice=rightsDO.buyerApplyPrice/100>
	                            <td>${price?string("0.00")}</td>
	                            <td>${applyPrice?string("0.00")}</td>
	                            <td><#if rightsDO.buyerRequire=0>退款<#elseif rightsDO.buyerRequire=1>退款退货</#if></td>
	                            <td>
	                               <#if rightsDO.state=0>等待卖家处理
		                               <#elseif rightsDO.state=1>
		                               		<#if rightsDO.buyerRequire=1>
		                               			等待买家退还商品
		                               		<#else>
		                               			卖家同意,等待打款
		                               		</#if>
		                               <#elseif rightsDO.state=2>卖家已拒绝，等待买家处理
		                               <#elseif rightsDO.state=3 ||rightsDO.state=11>等待卖家确认收货
		                               <#elseif rightsDO.state=4>等待客服处理
		                               <#elseif rightsDO.state=5>卖家同意,维权成功
		                               <#elseif rightsDO.state=6>维权关闭
		                               <#elseif rightsDO.state=7>客服仲裁维权成功
		                               <#elseif rightsDO.state=8>客服仲裁维权关闭
		                               <#elseif rightsDO.state=9>客服仲裁成功,等待打款
		                               <#elseif rightsDO.state=10>卖家同意,等待打款
		                               <#elseif rightsDO.state=12>等待买家发货
	                               </#if>
	                            </td>
	                            <td>${rightsDO.applyDate?string("yyyy-MM-dd HH:mm:ss")}</td>
	                            <td><a href="${base}/rights/sellerRightsDetail.htm?rightsId=${(rightsDO.id)!}">查看</a></td>
	                        </tr>
	                        </#list>
	                   		</#if>
	                    	<tr><td colspan="8">
	                    		<#if query.items = 0><font color="red">没有记录<font>
	                    		<#else><#include "/default/templates/control/pagination.ftl"></#if>
	                    	</td></tr>
                  		</#if>
              		</table>
              		<input type="hidden" value="sellerRightsList" id="my-page" />
            	</form>
            	 <script type="text/javascript">
				 	$('#status').val(${status});
				 </script>
            	
			</div><!--2、2表格结束-->
		</div><!--2、表-->    
	</div><!--大框架线 -->                  
</div><!--右侧结束 -->