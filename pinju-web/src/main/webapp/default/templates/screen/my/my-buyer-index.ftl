<#setting classic_compatible=true>

<title>我是买家</title>
<link rel="stylesheet" href="http://static.pinju.com/css/page/pagination.css" type="text/css" media="screen" />
<link media="screen" type="text/css" href="http://static.pinju.com/css/buyer.css" rel="stylesheet">

<div class="main">
	<div class="ma_ts">请注意您相关进行中的交易、退款、维权的超时时间，以保障您的权益。</div>
			<div class="userpannel cf">
				<div class="avatar">
					<div class="img"><img src="<@load.avatars userMemberId '50' version/>" alt="" /></div>
				</div>
				<div class="text">
					<div class="title">
						<div class="sayhi"><p>欢迎光临，${userName!"品聚会员"}</p></div>
						<div class="eye">
							<ul>
								<li><span class="icon-edit"></span><a href="${base}/my/info.htm" title="点击此处去修改个人信息">修改个人信息</a></li>
								<li><span class="icon-mlist"></span><a href="${base}/my/address.htm" title="点击此处去管理收货地址">管理收货地址</a></li>
							</ul>
						</div>
					</div>
					<div class="tips">
						<ul>
							<input type="hidden" id="userMemberId" value="${userMemberId}">
						    <#if items[0] != 0 >
								<li class="hasnews"><a href="${base}/orderBuyer/orderManager.htm?page=1&orderItemState=1&searchTy=1" title="待付款">待付款(<strong>${items[0]}</strong>)</a></li>
							<#elseif items[0] == 0 >
								<li>待付款(0)</li>
							</#if>
							
							<#if items[1] != 0 >
								<li class="hasnews"><a href="${base}/orderBuyer/orderManager.htm?page=1&orderItemState=3&searchTy=1" title="等待收货">等待收货(<strong>${items[1]}</strong>)</a></li>
							<#elseif items[1] == 0 >
								<li>等待收货(0)</li>
							</#if>
							
							<#if items[2] != 0 >
								<li class="hasnews"><a href="${base}/orderBuyer/orderManager.htm?isBuyerRate=0&orderItemState=5&searchTy=1" title="待评价">待评价(<strong>${items[2]}</strong>)</a></li>
                            <#elseif items[2] == 0 >
                            	<li>待评价(0)</li>
							</#if>
							
							
							<#if items[3] != 0 >
								<li class="hasnews"><a href="${base}/refund/buyerRefundList.htm" title="退款">退款(<strong>${items[3]}</strong>)</a></li>
                            <#elseif items[3] == 0 >
                            	<li>退款(0)</li>
							</#if>
							
							<#if items[4] != 0 >
								<li class="hasnews"><a href="${base}/rights/rightsList.htm" title="维权">维权(<strong>${items[4]}</strong>)</a></li>
                            <#elseif items[4] == 0 >
                            	<li>维权(0)</li>
							</#if>
							
						</ul>
						<ul class="last">
							<li id ="TDWD">提到我的(0)</li>
							<li id ="XPL">新评论(0)</li>
							<li id ="XSX">新私信(0)</li>
							<li id ="XTXX">系统消息(0)</li>
							<li></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="title">
					<ul class="eye">
						<li><a href="${base}/orderBuyer/orderManager.htm" title="点击此处去查看所有订单">查看所有订单</a></li>
					</ul>
					<ul class="tabs">
						<li>未完成的交易(最近10笔)</li>
					</ul>
				</div>
				
				<div class="order-list">
					<#if orderAllList.size() &gt; 0>
							<#list orderAllList as orderAll> 
							<table id="tab">
							<tr class="otitle">
								<td class="number" colspan="3">订单编号：${orderAll.orderDO.orderId}</td>
								<td class="time" colspan="2">下单时间：${orderAll.orderDO.gmtCreate?string("yyyy-MM-dd HH:mm:ss")}</td>
							</tr>
								<#list orderAll.orderItemList as orderItemDO>
									<tr>
										<td class="img">
											<div class="imgwrap">
												<#if orderItemDO.snapId == 0 >
													<a target="_blank" href="${base}/detail/${orderItemDO.itemId}.htm" title="点击此处可以查看商品快照">
														<img width="86px" height="86px" src="${imageServer}${orderItemDO.itemPicUrl}" />
													</a>
												<#else>
													<a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}" title="点击此处可以查看商品快照">
														<img width="86px" height="86px" src="${imageServer}${orderItemDO.itemPicUrl}" />
													</a>
												</#if>
											</div>
										</td>
										<td class="desc" style="padding-top:13px">
											<#if orderItemDO.snapId == 0>
												<a target="_blank" href="${base}/detail/${orderItemDO.itemId}.htm" title="点击此处可以查看商品快照">${(orderItemDO.itemTitle!"")?html}</a><br>
											<#else>
												<a target="_blank" href="${base}/snapshot/itemSnapshot.htm?arg1=${orderItemDO.snapId}" title="点击此处可以查看商品快照">${(orderItemDO.itemTitle!"")?html}</a><br>
											</#if>
											
											<span>
												<#if orderItemDO.itemSkuAttributes != "0">
			                            			${(orderItemDO.itemSkuAttributes!"")?html}
			                            		</#if>
			                            	</span>
										</td>
										
										<#if orderItemDO_index+1==1>
										<td class="price" rowspan="${orderAll.orderItemList.size()}" style="padding-top:25px">
											${orderAll.orderDO.priceAmountByYuan}<br />
					                    	<#if orderAll.logisticsDO??>
						                    	<#if orderAll.logisticsDO.consignmentMode!="4">
						                    		 <span class="col1">(含运费:${orderAll.logisticsDO.postPriceByYuan} 元)</span>
							                    <#else>
							                    	 <span class="col1">卖家承担运费</span>
							                    </#if>
						                    </#if>
					                    	
										</td>
										<td rowspan="${orderAll.orderItemList.size()}" style="padding-top:25px;">
											<#assign propkey = orderAll.orderDO.countDown(orderItemDO.bussinessType,orderAll.logisticsDO.consignmentMode)>
					                  		<#if propkey == "交易关闭" || propkey == "交易成功">
					                  			 交易关闭<input type="hidden" value="${propkey}">
					                  		<#else>
					                  			 ${orderAll.orderDO.getOrderStateDisplay()}
					                  		</#if>
											<br />
											<a target="_blank" href="${base}/orderSeller/orderinfoBuy.htm?oid=${orderAll.orderDO.orderId}" title="点击此处可以查看商品详情">订单详情</a><br />
											<#if orderAll.orderDO.orderState == 3 || orderAll.orderDO.orderState == 5>
						 						<a  target="_blank" href="${base}/orderSeller/logisticsInfoBuyer.htm?cd=${(orderAll.logisticsDO.outLogisticsId!"")?html}&exp=${(orderAll.logisticsDO.logisticsType!"")?html}&orderId=${orderAll.orderDO.orderId}" title="点击此处可以查看查看物流信息">查看物流</a>
						          			</#if>
										</td>
										<td rowspan="${orderAll.orderItemList.size()}" style="padding-top:25px;width:80px">
											  <form action="${base}/orderPay/pay.htm" method="get" id="payFrom${orderAll.orderDO.orderId}" target="_blank">
											 	<input type="hidden" name="orderId" value="${orderAll.orderDO.orderId}">
												 <#if orderAll.orderDO.orderState == 1>
						                   		    <#if propkey == "等待买家付款">
						                   		    	<button class="btn-sorange" type="button" onclick="singlePay('${orderAll.orderDO.orderId}')" title="点击此处进行付款">付&nbsp;&nbsp;&nbsp;&nbsp;款</button>
								                  	</#if>
										      	 </#if>
											  </form>
										      <form action="${base}/orderPay/receiveItem.htm" method="get" id="mergepayFrom${orderAll.orderDO.orderId}" target="_blank">
										      	  <input type="hidden" name="orderId" value="${orderAll.orderDO.orderId}">
										      	  <#if orderAll.orderDO.orderState == 3>
										         	<#if propkey == "卖家已发货">
										         	<button class="btn-sorange" type="button" onclick="checkOrderItemState('${orderAll.orderDO.orderId}','${orderAll.orderDO.isRefund}')">确认收货</button>
											        </#if>
										          </#if>
									      	 </form>
										</td>
										</#if>
									</tr>
								 </#list>
								 </table>
							</#list>
						
					<#else>
						<div class="tmain">您还没有未完成的交易的订单，马上开始购物吧......</div>
					</#if>
				</div>
			</div>
			<div class="row">
				<div class="title">
					<ul class="eye">
						<li><a href="http://sns.pinju.com/dashboard/index?id=${userMemberId}" target='_blank' title="点击此处进入我的社区">进入我的社区</a></li>
					</ul>
					<ul class="tabs">
						<li>最新社区动态</li>
					</ul>
				</div>
				<div class="ifram-wrap">
					<iframe id="snsframe" frameborder="no" scrolling="yes" src="http://sns.pinju.com/apiFrame/index/id/${userMemberId}"></iframe>
				</div>
			</div>
		</div>
		
		<div class="sidebar">
			<div class="box shopcart">
				<div class="title">
					<h2>购物车</h2>
				</div>
				<div class="text">
					<p>我的购物车有 <strong id="cartsCount">0</strong> 件商品<br />
					<a href="${base}/cart/shoppingCartDetail.htm" title="点击此处去查看购物车" target="_blank"><strong>查看购物车 &raquo;</strong></a></p>
				</div>
			</div>
			<div class="box">
				<iframe src="http://news.pinju.com/cms/html/my_buy-activity/index.html" width="184" scrolling="no" frameborder="no" class="newslister">
					<p>您的浏览器不支持此模块。</p>
				</iframe>
			</div>
			<div class="box">
				<iframe src="http://news.pinju.com/cms/html/my_buy-question/index.html" width="184" scrolling="no" frameborder="no" class="newslister">
					<p>您的浏览器不支持此模块。</p>
				</iframe>
			</div>
		</div>
	<div id="carts"></div>

<div class="im-online" id="J_imOnline">
	<a href="http://service.pinju.com/cms/html/contactservice/#popfaqcat" target="_blank"></a>
</div>
<style type="text/css">
.im-online {
	position: fixed;
	right: 10px;
	top: 300px;
	_position: absolute;
}
.im-online a {
	float:left;
	background: url("http://static.pinju.com/img/im-online.png") no-repeat left top transparent;
	width: 33px;
	height: 174px;
}
.im-online a:hover { background-position:-33px top; }
</style>
	
<script type="text/javascript">
     //取得购物车中数量
	$(function(){
		$("#cartsCount").text(pc.getShoppingCartCount());
		
		var userMemberId = $("#userMemberId").val();
		//ajax跨域操作
		$.ajax({   
           async:false,   
           url: 'http://sns.pinju.com/api/getUserTipById?id='.concat(userMemberId),  // 跨域URL  
           type: 'GET',   
           dataType: 'jsonp',   
           jsonp: 'jsoncallback', //默认callback
           timeout: 5000,
           success: function (data) { //客户端jquery预先定义好的callback函数，成功获取跨域服务器上的json数据后，会动态执行这个callback函数
              var response = data.response;
              var mentioned_num = response.mentioned_num;
              var comment_num = response.comment_num;
              var private_msg_num = response.private_msg_num;
              var system_msg_num = response.system_msg_num;
              if(mentioned_num != 0){
              	var url = "http://sns.pinju.com/dashboard/mentioned?id="+userMemberId;
              	f_show("TDWD","<a href="+url+" onclick='f_hide(\"TDWD\",\"提到我的(0)\")' title='提到我的' target='_blank'>提到我的(<strong>"+mentioned_num+"</strong>)</a>");
              }
              if(comment_num != 0){
              	 var url = "http://sns.pinju.com/comment/index?id="+userMemberId;
              	f_show("XPL","<a href="+url+" onclick='f_hide(\"XPL\",\"新评论(0)\")' title='新评论' target='_blank'>新评论(<strong>"+comment_num+"</strong>)</a>");
              }
              if(private_msg_num != 0){
				var url = "http://sns.pinju.com/message/index/id/"+userMemberId;
              	f_show("XSX","<a href="+url+" onclick='f_hide(\"XSX\",\"新私信(0)\")' title='新私信' target='_blank'>新私信(<strong>"+private_msg_num+"</strong>)</a>");
              }
              if(system_msg_num != 0){
              	var url = "http://sns.pinju.com/privateMessage/systemMessage?id="+userMemberId;
              	f_show("XTXX","<a href="+url+" onclick='f_hide(\"XTXX\",\"系统消息(0)\")' title='系统消息' target='_blank'>系统消息(<strong>"+system_msg_num+"</strong>)</a>");
              }
           },   
           error: function(xhr){}
          });
        
        $("#tab tr").each(function(i,obj){
			var _val = $(obj).find("td:eq(3) input:eq(0)").val();
			if(_val == "交易关闭"){
				$(this).parent().parent().remove();
			}
			if($("#tab tr").size() ==0 ){
			   var creatediv = "<div class=\"tmain\">您还没有未完成的交易的订单，马上开始购物吧......</div>";
			   $(".order-list").append(creatediv);
			}
		}); 
		
		
         
        var fixWrap = $('#J_imOnline');
 		function display() {
 			fixWrap.show();
 		}
 		$(window).scroll(function() {
 			display();
 			if ($.browser.msie & $.browser.version < 7) {
 				fixWrap[0].className = fixWrap[0].className;
 			}
 		});
 		display();
 		
	});
	
	function f_show(id,_str){
		$("#"+id).html("");
      	$("#"+id).attr("class","hasnews");
      	$("#"+id).append(_str);
	}
	
	function f_hide(id,msgStr){
		$("#"+id).html("");
		$("#"+id).removeAttr("class")
      	$("#"+id).html(msgStr);
	}
	
	//付款
	function singlePay(obj){
		$("#payFrom"+obj).submit();
	}
	
	//确认收货
	function checkOrderItemState(orderId,isRefund){
		if(isRefund == 10002){
			alert("您有商品正在退款，请完成退款或关闭退款后再确认收货!");
			return false;
		}
		$("#mergepayFrom"+orderId).submit();
	}
	
</script>	
	