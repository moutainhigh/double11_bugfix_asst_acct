<link href="http://static.pinju.com/css/rao/common.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="http://static.pinju.com/css/rao/drape.css" rel="stylesheet"
	type="text/css" media="screen" />
<script src="${base}/default/js/datePicker/WdatePicker.js">
</script>
<script src="http://static.pinju.com/js/jquery.pagination.js"></script>
<link href="http://static.pinju.com/css/page/pagination.css" rel="stylesheet" />  
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
<title>修改限时打折活动</title>
<div class="drape-right">
	<div class="buy-nav">
     	 您的位置：我是卖家 > 营销中心 > <span><a style="color:red;" href="${base}/active/promotionManager.htm">促销管理</a></span>
    </div>
	<ul class="act">
		<li class="here">
			<a href="${base}/active/promotionManager.htm">限时打折</a>
		</li>
	</ul>
	<ul class="top">
		<li>
			编辑限时折扣活动
		</li>
		<li>
			<span class="tip">本期剩余时间长${(surplus)!500}小时</span>
			<input type="hidden" id="surplus" value="${(surplus)!500}" />
		</li>
		<li style="float: right;">
			<span>
			<input type="button" value="取消编辑" onclick="cancelEdit();"
				class="pl-btn-juse" />
			</span>
		</li>
	</ul>
	<form action="${base}/active/doUpdateActivityDiscount.htm" id="pageForm" name="updateForm" method="post">
	<input type="hidden" id="imageServer" value="${imageServer}"/>
	<input type="hidden" name="pj0" value="${pj0!}"/>
	<input type="hidden" name="activityDiscount.id" id="activityId"
				value="${(activityDiscount.id)!}" />
	<input type="hidden" name="activityDiscount.status"
				value="${(activityDiscount.status)!}" />
	<!-- 原时长 -->
	<#if (activityDiscount.status)?exists && (activityDiscount.status == 0)>
		<input type="hidden" id="duration" value="${(duration)!240}"/>
	</#if>
	<div class="steps">
		<h3>
			<span class="h3-left">第一步 设置活动名称和促销时段</span><a id="first_modify"
				href="javascript:void(0);" onclick="hideAndShow('#first');"
				class="lan" style="display:none;">修改</a>
		</h3>

		<div id="first" class="drape-block">
			<ul class="set-promote">
				<li>
					<span class="left">设置促销名称：</span>*
					<input style="width:148px;" type="text" id="actName" name="activityDiscount.actName"  maxlength="40"
						value="${(activityDiscount.actName)!?html?js_string}" onfocus="hideFirstTip();"
					<#if (activityDiscount.status)?exists && activityDiscount.status !=0>disabled="disabled"</#if>/>
				</li>
				<li>
					<span class="left">促销开始时间：</span>*
					<input class="Wdate" type="text" id="startTime" name="activityDiscount.startTime"
						value="${((activityDiscount.startTime)?string("yyyy-MM-dd HH:mm:ss"))!}"
					<#if (activityDiscount.status)?exists && activityDiscount.status != 0>disabled="disabled"</#if>
						onfocus="hideFirstTip();WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00',minDate:'%y-%M-%d {%H+1}:00:00'})"/>
					<span id="timeTip" class="hui">促销活动时间必须长于1个小时，但不可超过240个小时</span>
				</li>
				<li>
					<span class="left">促销结束时间：</span>*
					<input class="Wdate" type="text" id="endTime" name="activityDiscount.endTime"
						value="${((activityDiscount.endTime)?string("yyyy-MM-dd HH:mm:ss"))!}"
					<#if (activityDiscount.status)?exists && activityDiscount.status != 0>disabled="disabled"</#if>
						onfocus="hideFirstTip();WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00',minDate:'%y-%M-%d {%H+2}:00:00'})"/>
				</li>
				<li>
					<#if (activityDiscount.status)?exists && activityDiscount.status !=	0>
						<input type="button" value="下一步" class="pl-btn-juse"
							onblur="hideFirstTip()"
							onclick="goToNext(${(activityDiscount.status)!0})" />
						<span class="tip">进行中的活动不允许修改活动名称和促销时段</span> 
					<#else>
						<input type="button" value="确定" class="pl-btn-juse"
							onblur="hideFirstTip()"
							onclick="goToNext(${(activityDiscount.status)!0})" />
						<span id="tip-1" class="tip-red" style="display: none;"></span>
					</#if>
				</li>
			</ul>
		</div>
	</div>
	<div class="steps">
		<h3>
			<span class="h3-left">第二步 选择商品</span><a href="javascript:void(0);" id="second_modify"
				onclick="hideAndShow('#second');" class="lan" style="display:none;">修改</a>
		</h3>

		<div style="display: none;" id="second" class="drape-block">
			<input type="hidden" id="itemList" name="activityDiscount.itemList"
				value="" />
			<input type="hidden" id="marker"
				value="${(activityDiscount.itemList)!}" />
			<ul class="steps-top">
				<li>
					<!--
					<select name="" style="width: 100px;">
						<option>
							未参加活动
						</option>
					</select>
					-->
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
					商品名称<input type="text" class="text" style="width: 145px;" id="itemName"  maxlength="200">
					<!-- onfocus="clearText($(this))" onblur="autoTip($(this), '商品名称')" -->
					<!--input type="text" class="text" style="width: 145px;" value="商家代码"-->
					<!-- onfocus="clearText($(this))" onblur="autoTip($(this), '商品代码')" -->
					<input type="button" value="查询" class="pl-btn-hui" id="searchItemButton" />
					<span id="query-tip" class="tip-red" style="display: none;"></span>
				</li>
				<li>
					<span class="tip">促销优惠只支持实物商品，每个活动最多可添加20件商品</span>
				</li>
			</ul>
			<table id="linkTable" cellspacing="0" cellpadding="0" class="table">
				<tr>
					<th class="t01">
						&nbsp;
					</th>
					<th class="t02">
						商品描述
					</th>
					<!--
					<th>
						商品编码
					</th>
					-->
					<th class="t03">
						一口价
					</th>
					<th class="t04">
						操作
					</th>
				</tr>
			</table>
			<table cellspacing="0" cellpadding="0" class="table tbfoot">
				<tr>
					<td class="tr-blue" colspan="2">
						<input type="checkbox" id="checkAll" onclick="checkAllItemBox(this)"
							name="checkAll">
						全选 &nbsp;
						<input type="button" value="批量参加" class="pl-btn-juse" id="batchAdd" />
					</td>
					<td class="tr-blue" colspan="3">
						<!--
			            <div class="sel-ye">
			              <ul>
			                <li class="sel-ye-selected">1</li>
			                <li class="t2">2</li>
			                <li class="t2">3</li>
			                <li class="t2">下一页&nbsp;<strong><span
			                    class="hong">></span> </strong></li>
			                <li><input class="pl-btn-hui" type="submit"
			                  value="确定" /></li>
			              </ul>
			            </div>
			            -->
			            <#include "/default/templates/screen/promotional/updatePagination.ftl">
					</td>
				</tr>
			</table>
			<div class="steps-bottom">
				<input type="button" id="secondButton" value="完成选择" onclick="goToLast();" class="pl-btn-juse">
				<span id="tip-2" style="display: none;" class="tip-red">至少添加一件商品</span>
			</div>
		</div>
	</div>
	<div class="steps">
		<h3>
			<span class="h3-left">第三步 设置限时打折(<span id="itemCountSpan">${(activityDiscount.itemCount)!0}</span>)</span>
			<input type="hidden" id="itemCount" name="itemCount" value="${(activityDiscount.itemCount)!0}"/>
			<input type="hidden" id="discountList" name="discountList" value="${(activityDiscount.discountList)!}"/>
			<input type="hidden" id="limitList" name="limitList" value="${(activityDiscount.limitList)!}"/>
			<input type="hidden" id="discountDefault" name="discountDefault" value="${(activityDiscount.discountDefault)!}"/>
			<a id="third_modify" href="javascript:void(0);" onclick="hideAndShow('#third');" class="lan" style="display:none;">修改</a>
		</h3>
		<div style="display: none;" id="third" class="drape-block">
			<ul class="steps-top">
				<li>
					批量设置：限时折扣
					<input type="text" class="text" id="batchRate" name="batchRate"  maxlength="4"
						onkeyup="clearNoNum(this);"
						<#--if (activityDiscount.discountDefault)?exists && 0 < activityDiscount.discountDefault>
							value="${((activityDiscount.discountDefault/100)?string("0.00"))!}" 
						</#if-->/>
					折&nbsp;&nbsp; 每人限购数
					<input type="text" class="text" id="batchLimit"  maxlength="8"
						onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'');}else{this.value=this.value.replace(/\D/g,'');}if(this.value.startWith('0')){this.value='';}" 
						onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'');}else{this.value=this.value.replace(/\D/g,'');}if(this.value.startWith('0')){this.value='';}" />
					<input type="button" value="确定" class="pl-btn-hui" id="setAll" />
					<span id="tip_3" class="tip">可设置的最高折扣为9.5折，且折后价不能小于1.00元</span>
					<span id="tip-3" style="display: none;" class="tip-red">请先输入折扣或者限购数再使用批量设置，且数值不能为0</span>
				</li>
			</ul>
			<table cellspacing="0" cellpadding="0" class="table" id="selectedTable">
				<tr>
					<th class="l3_t01">
						商品描述
					</th>
					<th class="l3_t02">
						一口价
					</th>
					<!--<th>当前最低折扣</th>-->
					<th class="l3_t03">
						限时折扣
					</th>
					<th class="l3_t04">
						折后价
					</th>
					<th class="l3_t05">
						每人限购数
					</th>
					<th class="l3_t06">
						操作
					</th>
				</tr>
				
				<#if selectedItemList?exists && (selectedItemList?size > 0) >
					<#list selectedItemList as selectedItem>
						<tr id="selected_${(selectedItem.id)!}">
							<!--<td><input type="checkbox" name="checkboxname"></td>-->
							<td class="table-long">
								<input type="hidden" value="${(selectedItem.id)!}" id="selectedItemId" name="selectedItemId"/>
								<img src="${imageServer}${(selectedItem.picUrl)!}_40x40.jpg" />
								<div class="title">
									<a href='${base}/detail/${(selectedItem.id)!}.htm ' target='_blank' class='lan'>${(selectedItem.title)?html?js_string}</a>
								</div>
							</td>
							<td id="price_${(selectedItem.id)!}" class="center">
								${((selectedItem.price/100)?string("0.00"))!}
							</td>
							<!--<td style="text-align: center;">-</td>-->
							<td class="center">
								<input type="text" class="text" id="rate_${(selectedItem.id)!}" name="discount_rate" maxlength="4"
									 onkeyup="clearNoNum(this);" onblur="autoDiscount(this);" onfocus="autoSelectRate(this);" />
								折
								<div class="absolute">
									<span id="tips_${(selectedItem.id)!}" style="display: none;" class="tip-red">折后价不能小于1元</span>
								</div>
							</td>
							<td id="discount_${(selectedItem.id)!}" class="center">
								0.00
							</td>
							<td class="center">
								<input type="text" class="text" id="limit_${(selectedItem.id)!}" name="discount_limit" maxlength="8"
									onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'');}else{this.value=this.value.replace(/\D/g,'');}if(this.value.startWith('0')){this.value='';}" 
									onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'');}else{this.value=this.value.replace(/\D/g,'');}if(this.value.startWith('0')){this.value='';}" />
							</td>
							<td class="center">
								<input type="button" onclick="deleteSelected(${selectedItem.id})" value="删除" class="pl-btn-juse">
							</td>
						</tr>
					</#list>
				</#if>
			</table>
			<div class="steps-bottom" style="width:100%">
				<input id="saveUpdate" type="button" value="保存修改" onclick="checkUpdateForm(document.updateForm, ${(activityDiscount.status)!0})" class="pl-btn-juse" />
			</div>
		</div>
	</div>
	</form>
</div>
<input type="hidden" value="promotion-manager" id="my-page" />
<script type="text/javascript" src="http://static.pinju.com/js/active/updateDiscount.js?t=20111207.js">
</script>
