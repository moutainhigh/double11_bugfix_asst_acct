<script src="http://static.pinju.com/js/rights/popup_layer.js"></script>
<script src="http://static.pinju.com/js/rights/validateNum.js"></script>

<title>维权详情页面</title>

<!-- 内容-Start -->
<div class="main20110627">
	<!-- 顶部引导条-Start -->
	<div class="rights_map"> 
		您的位置：<a href="${base}/orderBuyer/myBuyer.htm">我是买家 <i></i></a>&gt;
		<a href="${base}/rights/rightsList.htm">维权管理<i></i></a>&gt;
		<span class="bd red">维权详情</span>
	</div>
	<!-- 顶部引导条-End --> 	    
<#if query??> 

	<!-- 左侧订单信息-Start -->
	<#include "/default/templates/control/rights/orderInfo.ftl">
	<!-- 左侧订单信息-End --> 	

	<!-- 右侧维权信息-Start -->
	<div class="right_layout">
	<#if query.rightsDO??>
		<#assign rightsDO=query.rightsDO>
   		
   		<!-- 1.申请消保。。。进程图;2.维权状态及操作信息;3.小提示-Start -->
		<#include "/default/templates/control/rights/rightsStatus.ftl">
		<!-- 1.申请消保。。。进程图;2.维权状态及操作信息;3.小提示-End -->
		
		<!-- 商品信息-Start -->
		<#include "/default/templates/control/rights/itemInfo.ftl">
		<!-- 商品信息-End -->		
		
		<!-- 维权申请协议-Start -->
		<#include "/default/templates/control/rights/rightsInfo.ftl">
		<!-- 维权申请协议-End -->
		
		<!-- 留言/凭证记录-Start -->
		<div class="rights_box cf">
			<div class="tit"><span class="txt">留言/凭证记录</span></div>
			<!-- 留言列表-Start -->
			<#include "/default/templates/control/rights/messageList.ftl">
			<!-- 留言列表-End -->
			
			<!-- 发送留言/记录凭证模块-Start -->
			<#if rightsDO.buyerRequire=1>
				<#if rightsDO.state=0 || rightsDO.state=1 || rightsDO.state=2 || rightsDO.state=3 || rightsDO.state=4 || rightsDO.state=11 || rightsDO.state=12>
					<#include "/default/templates/screen/rights/sendMessage.ftl">
				</#if>
			<#elseif rightsDO.buyerRequire=0>
				<#if rightsDO.state=0 || rightsDO.state=2 || rightsDO.state=3 || rightsDO.state=4 || rightsDO.state=11 || rightsDO.state=12>
					<#include "/default/templates/screen/rights/sendMessage.ftl">
				</#if>
			</#if>
			<!-- 发送留言/记录凭证模块-End -->
		</div>
		<!-- 留言/凭证记录-End -->
		
		<!-- 维权协议状态信息-Start -->
		<div class="rights_box cf">
			<div class="tit"><span class="txt">维权协议状态</span></div>
			<div class="rights_status" style="padding-bottom:5px">
			<#if rightsDO.state == 0>
				等待卖家(<font color="blue">${((rightsDO.sellerNick)?html)!}</font>)处理中
			<#elseif rightsDO.state == 1>
				<#if rightsDO.buyerRequire=1>
					卖家(<font color="blue">${((rightsDO.sellerNick)?html)!}</font>)已经同意了您的维权申请，维权协议达成.<br/>
					请您在5天内将商品发出退还卖家(<font color="blue">${rightsDO.sellerNick!}</font>)，否则系统默认关闭维权，请及时操作。
				<#elseif rightsDO.buyerRequire=0>	
					卖家(<font color="blue">${rightsDO.sellerNick!}</font>)已经同意了维权申请，维权协议达成，买家(${rightsDO.buyerNick})将收到退款
				</#if>	
			<#elseif rightsDO.state=2>
				卖家(<font color="blue">${rightsDO.sellerNick!}</font>)已经拒绝了维权申请
			<#elseif rightsDO.state=3>
				买家已退还商品，等待卖家(<font color="blue">${rightsDO.sellerNick!}</font>)确认收货
			<#elseif rightsDO.state=4>
				卖家(<font color="blue">${rightsDO.sellerNick}</font>)已拒绝维权申请，等待客服处理
			<#elseif rightsDO.state=5 || rightsDO.state=7>
				维权成功，请检查您的支付账户
			<#elseif rightsDO.state=6 || rightsDO.state=8>
				维权关闭
	        <#elseif rightsDO.state=9>
				客服已经同意了维权申请，维权协议达成，您将收到退款
	        	<#--
				<#if rightsDO.buyerRequire=0>
					客服已经同意了维权申请，维权协议达成，您将收到退款
				<#elseif rightsDO.buyerRequire=1>	
					客服已经同意了您的维权申请，维权协议达成.<br/>
					请您在5天内将商品发出退还卖家，否则系统默认关闭维权，请及时操作。
				</#if>
				-->		        
	        <#elseif rightsDO.state=10>
	           	卖家(<font color="blue">${rightsDO.sellerNick!}</font>)已确认收货，等待打款
	        <#elseif rightsDO.state=11>
	           	客服已同意，等待卖家(<font color="blue">${rightsDO.sellerNick!}</font>)确认收货
	        <#elseif rightsDO.state=12>
				客服已经同意了您的维权申请，维权协议达成.<br/>
				请您在5天内将商品发出退还卖家，否则系统默认关闭维权，请及时操作。
			</#if>
			</div>
		</div>
		<!-- 维权协议状态信息-End -->
		
		<#if rightsDO.buyerRequire=1 && (rightsDO.state=1 || rightsDO.state=3 || rightsDO.state=11 || rightsDO.state=12)>
		<!-- 退货物流信息-Start -->
		<#include "/default/templates/control/rights/logisticsInfo.ftl">
		<!-- 退货物流信息-End -->
		</#if>
	</#if>
	</div>
	<!-- 左侧维权信息-End -->
</#if>      
</div>
<!-- 内容-End -->   
<script type="text/javascript">
//取得字符串长度，一个中文字符长度为2（参数s为字符串的value）
function getLength(s){
	if(s == ""){
		return 0;
	}
	var len = 0;
	for(i = 0; i < s.length; i++){
		var c = s.substr(i, 1);
		var ts = escape(c);
		if(ts.substring(0, 2) == "%u"){
			len += 2;
		} else {
			if(ts.substring(0, 3) == "%D7"){
				len += 2;
			} else {
				len += 1;
			}
		}
	}
	return len;
}
</script>
