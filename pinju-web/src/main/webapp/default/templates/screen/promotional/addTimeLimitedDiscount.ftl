
	<title>创建限时打折</title>
	<link rel="icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
	<link href="http://static.pinju.com/css/rao/common.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="http://static.pinju.com/css/rao/drape.css" rel="stylesheet" type="text/css" media="screen" />
	<link type="text/css" href="http://static.pinju.com/css/page/pagination.css" rel="stylesheet" />
	<script src="${base}/default/js/datePicker/WdatePicker.js"></script>
	
	<style type="text/css">
	html {
	  margin: 0;
	}
	h3 {font-size: 12px;}
	.buy-nav {
	    background: none repeat scroll 0 0 #FFFFE0;
	    border: 1px solid #F9DFB2;
	    height: 28px;
	    line-height: 28px;
	    margin-bottom: 9px;
	    padding: 0;
	    text-indent: 12px;
	}
	</style>
  <div class="drape-right">
    <div class="buy-nav">
     	 您的位置：我是卖家 > 营销中心 > <span><a style="color:red;" href="${base}/active/promotionManager.htm">促销管理</a></span>
    </div>
    <ul class="act">
      <li class="here"><a href="${base}/active/promotionManager.htm">限时打折</a></li>
      <li class="here"><a href="/coupon/couponBatchAll.htm">店铺优惠券</a></li>
    </ul>
    <ul class="top">
      <li>创建限时折扣活动</li>
      <li>
      	<span class="tip">本期剩余时间长${(surplus)!}小时</span>
      	<input type="hidden" id="timeLeft" value="${(surplus)!}"/>
      </li>
      <li style="float: right;">
      	<input type="button" value="放弃创建" class="pl-btn-juse" onClick="give_up();"/>
      </li>
    </ul>
    
    <form action="${base}/active/addActivityDiscount.htm" method="post" id="form1">
    <div class="steps">
       <h3>
        <span class="h3-left">第一步 设置活动名称和促销时段</span><a href="#" class="lan" style="display:none;" id="firstUp">修改</a>
      </h3>
      
      <div style="display:display;" class="drape-block" id="firstStep">
      <input type="hidden" name="startTime" id="startTime" value=""/>
      <input type="hidden" name="endTime" id="endTime" value=""/>
      <ul class="set-promote">
        <li><span class="left">设置促销名称：</span>*<input style="width:148px;" type="text" name="activityDiscount.actName" id="actname" onfocus="hideFirstTip();" />
        </li>
        <li>
        	<span class="left">促销开始时间：</span>*<input class="Wdate" type="text" name="startT" id="startT" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00',minDate:'%y-%M-%d {%H+1}:00:00'});hideFirstTip();">
        	<span class="hui">促销活动时间必须长于1小时，但不可超过240个小时</span>
        </li>
        <li>
        	<span class="left">促销结束时间：</span>*<input class="Wdate" type="text" name="endT" id="endT" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00',minDate:'%y-%M-%d {%H+2}:00:00'});hideFirstTip();">
		</li>
        <li>
	        <input type="button" value="确定" class="pl-btn-juse" id="first" onblur="hideFirstTip()"/>
	        <span class="tip-red" id="errorMsg" style="display:none;"></span>
        </li>
      </ul>
      </div>
    </div>
    <div class="steps">
      <h3>
        <span class="h3-left">第二步  选择商品</span><a href="#" class="lan" style="display:none;" id="secondUp">修改</a>
      </h3>
      
      <div style="display:none;" class="drape-block" id="secondStep">
      <ul class="steps-top">
        <li>
	      	<select id="categoryId" name="categoryId" style="width: 150px;">
				<option value="">全部商品</option>
                <option value="0"<#if !(categoryId ??) || categoryId == "0"> selected</#if>>未分类商品</option>
                    <#list shopCategoryList.keySet() as key>
	                    <#if (shopCategoryList.get(key).subCategoryList?size > 0)>
	                		<optgroup label="${shopCategoryList.get(key).categoryName}">
	                    <#list shopCategoryList.get(key).subCategoryMap.keySet() as val>
	                		<option value="${val}"<#if categoryId ?? && categoryId == val> selected</#if>>└${shopCategoryList.get(key).subCategoryMap.get(val)}</option>
	                    </#list>
	                    	</optgroup>
	                    <#else>
	                		<option value="${shopCategoryList.get(key).id}"<#if categoryId ?? && categoryId == shopCategoryList.get(key).id> selected</#if>>${shopCategoryList.get(key).categoryName}</option>
	                    </#if>
                   </#list>
			</select>
			商品名称<input type="text" class="text" style="width: 145px;" id="itemName">
	        <input type="button" value="查询" class="pl-btn-hui" id="searche"/>
	        <span id="query-tip" class="tip-red" style="display: none;"></span>
        </li>
        <li><span class="tip">促销优惠只支持实物商品，每个活动最多可添加20个商品</span></li>
      </ul>
      
      <table cellspacing="0" cellpadding="0" class="table" id="linkTable">
        <tr>
          <th class="t01">&nbsp;</th>
          <th class="t02">商品描述</th>
          <th class="t03">一口价</th>
          <th class="t04">操作</th>
        </tr>
        </table>
        <table cellspacing="0" cellpadding="0" class="table tbfoot">
        <tr>
          <td class="tr-blue" colspan="2">　
	          <input type="checkbox" id="hobby_all">全选 &nbsp;
	          <input type="button" value="批量参加" class="pl-btn-juse" id="batch"/>
          </td>
          <td class="tr-blue" colspan="3">
           <#include "/default/templates/screen/promotional/updatePagination.ftl">
          </td>
        </tr>
      </table>
      <div class="steps-bottom">
        <input type="button" value="完成选择" class="pl-btn-juse" id="complete">
        <span class="tip-red" id="secondMs" style="display:none;"></span>
      </div>
      </div>
    </div>
    <div class="steps">
      <h3>
        <span class="h3-left" id="countDiscount">第三步  设置限时打折(0)</span><a href="#"class="lan" style="display:none;" id="thirdUp">修改</a>
      </h3>
      <div style="display:none;" class="drape-block" id="thirdStep">
      <ul class="steps-top">
        <li>
        	批量设置：限时折扣<input type="text" name="discountDefault" class="text" onkeyup="clearNoNum(this);" maxlength="4" id="discount"/>
        	折&nbsp;&nbsp;每人限购数<input type="text" class="text" maxlength="8" onkeyup="up(this);" onafterpaste="afterpaste(this);" id="disnumber"/>
        	<input type="button" value="确定" class="pl-btn-hui" id="setDisAll"/>
        	<span class="tip" id="thirdMsg" style="display:display;">可设置的最高折扣为9.5折，且折后价不能小于1.00元</span>
         </li>
      </ul>
      <table cellspacing="0" cellpadding="0" class="table" id="tThird">
        <tr>
          <th class="l3_t01">商品描述</th>
          <th class="l3_t02">一口价</th>
          <th class="l3_t03">限时折扣</th>
          <th class="l3_t04">折后价</th>
          <th class="l3_t05">每人限购数</th>
          <th class="l3_t06">操作</th>
        </tr>
      </table>
      <div class="steps-bottom">
        <input type="button" value="完成创建" class="pl-btn-juse" id="lastStep"/>
      </div>
    </div>
    </div>
    <input type="hidden" name="mbd" value="${mbd!}"/>
    </form>

  </div>

 <input type="hidden" value="promotion-manager" id="my-page" />
 <input type="hidden" value="${imageServer!}" id="iserver"/>
 <script type="text/javascript" src="http://static.pinju.com/js/active/discount.js?t=20111208.js"></script>
 
  