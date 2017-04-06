<#setting number_format="#">
<#assign itemDO = query.itemDO>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="keywords" content="详情页" />
        <meta name="description" content="description-品聚" />
        <title>商城--御网</title>
        <LINK href="http://static.pinju.com/css/css.css" rel="stylesheet" />
        <link href="http://static.pinju.com/css/sell.css" rel="stylesheet" />
        <SCRIPT src="http://static.pinju.com/js/top.js"></SCRIPT>
        <SCRIPT src="http://static.pinju.com/js/hd.js"></SCRIPT>
    </head>
    <body>
       		 <link href="http://static.pinju.com/css/wuliu.css" rel="stylesheet" />
            <script src="http://static.pinju.com/js/item/itemdetail.js"></script>
            <SCRIPT src="http://static.pinju.com/js/jquery.validate.min.js"></SCRIPT>
            <SCRIPT src="http://static.pinju.com/js/jquery.lazyload.js"></SCRIPT>
            <script>
             $("img").lazyload();  
            </script>
       <!--内容页面开始-->
        <div class="sell-content">
        <!--left-->
 <#include "/default/templates/control/item/detailLeft.ftl">  
            <!--right-->
            <div class="sell-right">
                <div class="se-main">
                    <div class="main-left">
                       <!--  <img src="${imageServer}${query.getThumbJpg(itemDO.picUrl!)}" />-->
                        <img src="${imageServer}${itemDO.picUrl!}_310x310.jpg" />
                    </div>
                    <div class="main-right">
                        <h3>${itemDO.title!?html}</h3>
                        <ul class="mr-ul">
                            <li class="mr-price">
                                <span>价　　格：</span>
                                <strong id="SpecPrice">${query.getPriceCentToDollar(itemDO.price)!}</strong>元
                            </li>
                            <li >
                            	运　　费：至
                            	<span onmouseover="unDisplayLogistics()" onmouseout="displayLogistics()">
                            	<#include "/default/templates/control/item/detailLogistics.ftl"> 
                            	<a href="javascript:void(0);" id="toArea"><#if query.logisticsMap??&&query.logisticsMap.get('cityName')??>${query.logisticsMap.get('cityName')!}</#if></a>
                            	</span> 
                            	<#if query.logisticsMap??&&query.logisticsMap.get('1')??><div id="logistPy">平邮：${query.logisticsMap.get('1')!}元</div></#if>
                            	<#if query.logisticsMap??&&query.logisticsMap.get('2')??><div id="logistPd">快递：${query.logisticsMap.get('2')!}元</div></#if>
                            	<#if query.logisticsMap??&&query.logisticsMap.get('3')??> <div id="logistEms">EMS：${query.logisticsMap.get('3')!}元</div></#if>
                              
                            </li>
                            <li class="mr-pay">
                                支付方式：信用卡 <span>分期付款</span>
                                货到付款
                            </li>
                            <li>
                                共 售 出： <span class="cheng">${itemDO.oriStock-itemDO.curStock?default(0)}</span>件 　　　　 一个月内售出：<span class="cheng">_80</span>件
                            </li>
                            <li>商品口碑：</li>
                            <li>
                                <p>&nbsp;</p>
                            </li>
                        </ul>
                        <!--购物车-->
                        <div class="pj-buy">
                            <div class="pj-skin">
					           <#if query.skuListPV??>
										<#list query.skuListPV as skuListPV>
							             <dl>
		                                    <dt>${skuListPV.cateProlevel.name!}：</dt>
												<dd>
		                                        <ul class="sku-properties">
		                                        	<#list skuListPV.levelList as skuProValue>
		                                            <li id="${skuListPV.cateProlevel.id!}:${skuProValue.id}" class="pj-border2 ">
		                                                <a href="javascript:void(0);"  hidefocus="true">${skuProValue.value}</a>
		                                            </li>
		                                            </#list>
		                                        </ul>
		                                    </dd>
		                                </dl>		
										</#list>
								</#if>
                            
                                <dl>
                                    <dt>数&nbsp;&nbsp;&nbsp;量：</dt>
                                    <dd>
                                    <form  id="detailForm" name="detailForm" method="GET">
                                <input type="hidden"  value="${itemDO.id?c}" id="itemId" name="itemId">
                                <input type="hidden"  value="${query.encryptItemId}" id="encryptItemId" name="encryptItemId">
                                <input type="hidden" value="" id="SelectedSKU"  name="SelectedSKU" class="required" />
                                <input type="hidden" value="${query.getPriceCentToDollar(itemDO.price)!}" id="SelectedPrice"  name="SelectedPrice" class="required SelectedPrice" />
                                <input type="hidden" value="" id="SelectedSkuId"  name="SelectedSkuId" />
                                <input type="hidden"  id="bussinessType"  name="bussinessType" value="${bussinessType?default(100)}"/>
                                <#if channelId??>
                                	<input type="hidden" value="${channelId}" id="channelId"  name="channelId" />
                                </#if>
                                <input type="text" id="SelectedNum" name="SelectedNum" class="" value="1" maxlength="8" title="请输入购买量"/>
                                    件<em>(库存<strong id="SpecCapacity">${itemDO.curStock!}</strong>件)</em>
                                </form>
                                    
                                    </dd>
                                </dl>
                                
                                <div class="pj-btn-action">
                                    <div class="pj-btn-buy" style="width:96px;"  onclick="postForm('${base}/orderBuyer/lastTimeBuySell.htm')">
                                        <a href="#" title="点击此按钮，到下一步确认购买信息。">确认购买</a>
                                    </div>
                                    <!--加入购物车层-->
									 <#include "/default/templates/control/item/detailShopCart.ftl">  
                                    <div class="pj-btn-add" onclick="postShopCart('${base}/cart/addItem.htm');" style="cursor:pointer;">
                                        <a href="#this" title="加入购物车" >加入购物车</a>
                                    </div>
                                </div>
                                <div class="pj-btn-action">
						                                    商品类型：<#if itemDO.itemDegree??&&itemDO.itemDegree==1>全新
							                    	<#elseif itemDO.itemDegree??&&itemDO.itemDegree==2>
							                    		二手
							                    	<#elseif itemDO.itemDegree??&&itemDO.itemDegree==3>
							                    		个人闲置
							                    	</#if>
                                </div>
                            </div>
                        </div>
                    </div>
	                    	
                    <!--
                    <div class="main-bottom">
                        <img src="http://static.pinju.com/images/lihuod.jpg" align="left" />单笔订单满<span class="cheng">1</span>元，免运费 ; 单笔订单满<span class="cheng">200</span>元减<span class="cheng">30</span>元 ; 单笔订单满<span class="cheng">400</span>元减<span class="cheng">60</span>元 ; 单笔订单满<span class="cheng">600</span>元减<span class="cheng">90</span>元 ; 单笔订单满<span class="cheng">800</span>元减<span class="cheng">120</span>元 ;
                    </div>-->
                </div>
                <div class="se-bd">
                    <img src="http://static.pinju.com/images/lihd.jpg" />
                <a href="javascript:void(0);" onclick="InitTable(0)">点此处加载评价信息</a>
                    <ul class="sb-list">
							 <#if query.spuProList??>
							   	<#assign spuProList = query.spuProList>
								   <#list spuProList as spuMap>
								   <li>	${spuMap.cateProName}:&nbsp;${spuMap.cateProValue}</li>
									</#list>
							   </#if>
							   <#if query.cateProList??>
							   	<#assign cateProList = query.cateProList>
								   <#list cateProList as cateMap>
								   <li>	${cateMap.cateProName}:&nbsp;${cateMap.cateProValue}</li>
									</#list>
							 </#if>
                        <div class="cella"></div>
                    </ul>
                    <div class="sd-xxpic">
                       <!-- <img src="http://static.pinju.com/images/lixxpic.jpg" /> -->
                        ${itemDO.description!}
                    </div>
                    <!--<div class="sd-aq">
                        <h4>_商品答疑</h4>
                        <img src="http://static.pinju.com/images/liaq.jpg" />
                    </div>
                    <div class="sd-aq">
                        <h4>_商品口碑</h4>
                        <img src="http://static.pinju.com/images/likb.jpg" />
                    </div>
                    <div class="sd-aq">店铺推荐
                        <img src="http://static.pinju.com/images/litj.jpg" />
                    </div>
                    -->
                    <div class="sd-aq">
                        <h4>交易评价</h4>
                        <!--<img src="http://static.pinju.com/images/lsjy.jpg" />-->
                        <#include "/default/templates/control/item/detailRate.ftl">  
                       
                    </div>
                    
                </div>
            </div>
            <div class="cella"></div>
        </div>
<#if query.jsSkuInit??>
    <script type="text/javascript" src="http://static.pinju.com/js/item/detailsku_bak.js"></script>
    <script type="text/javascript">
        var sku = new SmartSKU(".sku-properties", {
            'PNC': 1,
            'upper-limit': '${query.upperPrice!}',
            'lower-limit': '${query.lowerPrice!}',
            <#if query.jsSkuInit??>
				<#assign jsSkuInit = query.jsSkuInit>
					<#list jsSkuInit as skuJsMap>
							'${skuJsMap.JsSkuPv}': ['${skuJsMap.JsSkuPrice}', ${skuJsMap.JsSkuCurrentStock},${skuJsMap.JsSkuId}],
					</#list>
			</#if>
        });
        sku.getSKU();
    </script>
</#if>
    </body>

</html>
