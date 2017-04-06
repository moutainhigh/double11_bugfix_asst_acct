<#setting classic_compatible=true>
<LINK href="http://static.pinju.com/css/rights/xiaobao.css" rel="stylesheet" />

<title>维权列表</title>

<!-- 右侧  -->
<div class="boxh0 top10 dajin0629-juzhong">

	<!-- 顶部引导条-Start --> 
	<div class="positionbanner t4">
		你的位置：<a href="${base}/orderBuyer/myBuyer.htm">我是买家<i></i></a>&gt;
		  <span class="bd red">维权管理</span>
	</div>
	<!-- 顶部引导条-End --> 
	
	<!-- 大框架线   -->
	<div class="boxh0 top10 floatleft t2">  
		<!-- 1、title Start -->
		<div class="boxpadding floatleft t3"><strong class="zi_14size">我的维权记录</strong></div>
		<div class="boxh2 floatleft t5"><div class="dajin0628-boxred120 floatleft"></div></div>
		<!-- 1、title End -->
		
		<!-- 2、表维权信息-Start -->
        <div class=" dajin0628-boxw700-01">
        	<!-- 2、1-维权列表标签-Start --> 
        	<div class="dajin0628-boxw700-02">           
        		<div class="dajin0628-biaoqian001">我的维权申请（${(query.items)!}）</div>
                <div class="dajin0628-biaoqiankongbai"></div>
                <div class="fudongceng03"></div>
            </div>
            <!-- 2、1-维权列表标签-End --> 
            
            <!-- 2、2-维权列表-Start -->
            <div class=" dajin0628-boxw700-03">
			<form action="" method="post" name="pageForm" id="pageForm">
               	<table width="700" class=" dajin0630-tablejianju">
                  	<tr align="center" class="dajin0628-zi-jiacu">
                    	<td>维权编号</td>
                    	<td>订单编号</td>
                    	<td>卖家</td>
                    	<td>交易金额(元)</td>
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
                        <td>${(rightsDO.sellerNick)!}</td>
                        <td>${(rightsDO.price/100)?string.currency}</td>
                        <td><#if rightsDO.buyerRequire=0>退款
                        	<#elseif rightsDO.buyerRequire=1>退款退货</#if>
                        </td>
                        <td>
                           	<#if rightsDO.state=0>等待卖家处理
                           	<#elseif rightsDO.state=1>
                           		<#if rightsDO.buyerRequire=0>
                           			维权协议已达成,等待打款
                           		<#elseif rightsDO.buyerRequire=1>
                           			卖家已同意,等待买家退还商品
                           		</#if>
                           	<#elseif rightsDO.state=2>卖家已拒绝,等待买家处理
                           	<#elseif rightsDO.state=3>等待卖家确认收货
                           	<#elseif rightsDO.state=4>等待客服处理
                           	<#elseif rightsDO.state=5 || rightsDO.state=7>维权成功
                           	<#elseif rightsDO.state=6 || rightsDO.state=8>维权关闭
                           	<#elseif rightsDO.state=9>维权协议已达成,等待打款
                           	<#elseif rightsDO.state=10>维权协议已达成，等待打款
                           	<#elseif rightsDO.state=11>等待卖家确认收货
                           	<#elseif rightsDO.state=12>客服同意,等待买家退还商品</#if>
                        </td>
                        <td>${rightsDO.applyDate?string("yyyy-MM-dd HH:mm:ss")}</td>
                        <td><a href="${base}/rights/rightsDetail.htm?rightsId=${(rightsDO.id)!}" target="_blank">查看</a></td>
                    </tr>
                    </#list>
               		</#if>
                	<tr>
                		<td colspan="8">
                		<#if query.items = 0><font color="red">没有记录<font>
                		<#else><#include "/default/templates/control/pagination.ftl"></#if>
                		</td>
                	</tr>
              		</#if>
          		</table>
          		<input type="hidden" value="rightsList" id="my-page" />
        	</form>
        	</div>
        	<!-- 维权列表-End  -->  
		</div>
		<!-- 2、表维权信息-End -->  
	</div>
	<!-- 大框架线   -->                  
</div>
<!-- 右侧结束  -->

