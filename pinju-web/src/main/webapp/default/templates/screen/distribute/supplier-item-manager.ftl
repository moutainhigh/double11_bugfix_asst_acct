<title>分销商品管理</title>
<link href="http://static.pinju.com/css/jin/sns-base.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/jin/management.css" rel="stylesheet" />
<div class="right_850px floatright"><!--右侧开始 -->
  	<div class="right_content02"><!--分销大框架开始 -->
        
      <div class="box_806 padding_10px fontgray_01 margin_bottom">您好，${userName}，欢迎进入云营销</div>
        
      <div class="box_806 padding_10px border_1px_gray bg_white margin_bottom"><span class="fontgray_03">我是卖家 &gt; 云营销 &gt;</span> <span class="fontred">爱分享分销商品管理</span></div>
        
       <div class="box_806 padding_10px border_1px_orange bg_yellow01 margin_bottom">
      	<div class="floatleft">所有商品<span class="fontred">（${allCount!0}件） </span></div>
        <div class="floatleft margin_left20">已分销商品 <span class="fontred">（${distributedItemCount!0}件） </span></div>
      </div>
        
		<div class="box_806 lable_bg_style padding_lr_10px ">
			<div <#if pageType==0>class="lable_present"<#elseif pageType==1>class="lable_after"</#if>> <a href="${base}/supplier/getAllItem.htm">全部商品</a> </div>
            <div <#if pageType==1>class="lable_present"<#elseif pageType==0>class="lable_after"</#if>> <a href="${base}/supplier/getDistributeItem.htm">已分销</a> </div>
        </div>
        
        <!-- <ul class="tab-main">
        	<li id="allItem" <#if pageType==0>class="count"</#if>><a href="${base}/supplier/getAllItem.htm">全部商品</a></li>
        	<li id="distributeItem" <#if pageType ==1>class="count"</#if>><a href="${base}/supplier/getDistributeItem.htm">已分销</a></li>
        </ul> -->
        <form id="pageForm" name="pageForm" method="post" action="${base}/supplier/saveItems.htm">
        <input type="hidden" name="pageType" value="${pageType}" />
        <input type="hidden" name="distributeSupplierDO.id" value="${distributeSupplierDO.id}" />
        <#if pageType==0>
	        <div class="box_806 lable_bg_style padding_10px ">
	        	批量设置返点比例：<input id="allReward" name="allReward" type="text" class="box_width_50px" maxLength="5">%
	            <input name="button" type="button" class="orange_button_005 margin_left20" value="批量设置分销" onclick="setAllReward()">
	        </div>
			<div class="box_826">
				<table width="826" border="0" class="bottom_1px_gray bg_gray01 fontbold">
					<tr>
						<td width="220"><input id="titleCheckbox" name="titleCheckbox" type="checkbox" value="">&nbsp; 商品名 </td>
			            <td width="100">零售价</td>
			            <td width="126">分成比例</td>
			            <td width="80">库存</td>
			            <td width="100">分销状态</td>
			            <td width="100">备注</td>
					</tr>
				</table>
				<!-- START <全部商品List> -->
				<table border="0" width="826">  
		          <tbody>
		          <#if pageList?exists && (pageList?size > 0)>
		            <#assign i=0 />
		          	<#list pageList as item>
			          <tr class="bottom_1px_gray_dashed fontgray_03">
			         	<input type="hidden" name="pageList[${i}].itemId" value="${item.itemId}" />
			         	<input type="hidden" name="pageList[${i}].supplierId" value="${item.supplierId}" />
			         	<input type="hidden" name="pageList[${i}].distributeNum" value="${item.distributeNum!"0"}" />
			         	<input type="hidden" id="check${i}" name="pageList[${i}].checked" value=""/>
			            <td width="220">
			            	<input id="${i}" <#if item.status?exists && item.status == 0>disabled="true"</#if> itemStatus="${item.status!-1}" name="itemCheckbox" value="" type="checkbox" onclick="itemCheck(this)">
			             	&nbsp;
			             	<a href="/detail/${(item.itemDO.id)!0}.htm" target="_blank">
			             		${item.itemDO.title?html}
			             	</a>
			            </td>
			            <td width="100">${item.itemDO.priceByYuan}</td>
			            <td width="126"><input id="reward${i}" name="pageList[${i}].reward" class=" box_width_50px" type="text" value="${(item.reward!'')?html}" maxLength=2 <#if item.status?exists && item.status == 0>READONLY</#if>>%</td>
			            <td width="80">${item.itemDO.curStock!""}</td>
			            <td width="100">
			              <label for="select"></label>
			              <select id="status${i}" name="pageList[${i}].status" class="box_width_90px" value="" <#if item.status?exists && item.status == 0>disabled="true"</#if>>
			             		<option value=""></option>
			              		<option value="0" <#if item.status?exists && item.status == 0>selected</#if>>分销</option>
			                    <!-- <option value="1" <#if item.status?exists && item.status == 1>selected</#if>>停止分销</option> -->
			              </select>
			             </td>
			            <td width="100"></td>
			          </tr>
			          <#assign i=i+1/>
		            </#list>
		          <#else>
		          <tr><td colspan="7" align='center'><label class="hong">您还没有分销商品！</label></td></tr>
		          </#if>
		        </tbody>
		       </table>
		       <div class="cbottom imgbtn">
		       	<#include "/default/templates/control/bottomPage.ftl">
		       </div>
				<!-- END -->
				<!-- START <button> -->
				<div class="box_806 padding_10px fontgray_01" align="center">
		        	<input name="button" class="orange_button_002" value="确定" type="button" onclick="save()">
		            <input name="button" class="gray_button_002 margin_left20" value="取消" type="button">	
		        </div>
				<!-- END -->
			</div>
			<#elseif pageType==1>
			<!--<div class="box_806 lable_bg_style padding_10px ">
		           <input name="button" class="gray_button_005 margin_left20" value="批量停止分销" type="button"> 
		      </div>
		      -->
		        	            
		      <div class="box_826">
			        <table class="bottom_1px_gray bg_gray01 fontbold" border="0" width="826">
			          <tbody><tr>
			            <td width="220">&nbsp; 商品名 </td>
			            <td width="100">零售价</td>
			            <td width="126">分成比例</td>
			            <td width="80">库存</td>
			            <td width="100">分销状态</td>
			            <td width="100">备注</td>
			          </tr>
			        </tbody></table>
			          
			        <table border="0" width="826">  
			          <tbody>
			          <#if pageList?exists && (pageList?size > 0)>
			            <#assign i=0 />
			          	<#list pageList as item>
				          <tr class="bottom_1px_gray_dashed fontgray_03">
				          	<input type="hidden" name="pageList[${i}].itemId" value="${item.itemId}" />
				         	<input type="hidden" name="pageList[${i}].supplierId" value="${item.supplierId}" />
				         	<input type="hidden" id="check${i}" name="pageList[${i}].checked"/>
				            <td width="220">&nbsp;
				            	<a href="/detail/${(item.itemDO.id)!0}.htm" target="_blank">
				            		${item.itemDO.title?html}
				            	</a>
				            </td>
				            <td width="100">${item.itemDO.priceByYuan}</td>
				            <td width="126">${item.reward!}%</td>
				            <td width="80">${item.itemDO.curStock!""}</td>
				            <td width="100"><#if item.status?exists && item.status == 0>已分销<#elseif item.status?exists && item.status == 1>停止分销</#if>
				              <!--<label for="select"></label>
				               <select id="status${i}" name="pageList[${i}].status" class="box_width_90px" value="${item.status!"0"}">
				                   <option value="0" <#if item.status?exists && item.status == 0>selected</#if>>分销</option>
			                       <option value="1" <#if item.status?exists && item.status == 1>selected</#if>>停止分销</option> 
				              </select>-->
				             </td>
				            <td width="100"></td>
				          </tr>
				           <#assign i=i+1/>
				        </#list>
				        <#else>
				          <tr><td colspan="7" align='center'><label class="hong">您还没有分销商品！</label></td></tr>
				        </#if>
			        </tbody></table>
			        <div class="cbottom imgbtn">
			        	<#include "/default/templates/control/bottomPage.ftl">
			        </div>
			        <!--
					<div class="box_806 padding_10px fontgray_01" align="center">
			        	<input name="button" class="orange_button_002" value="确定" type="button" onclick="">
			            <input name="button" class="gray_button_002 margin_left20" value="取消" type="button">	
			        </div>
			        -->
		        </div>
			</#if>
		</form>
  	</div><!--分销大框架结束 -->
</div>
<script type="text/javascript">
	$(function(){
		$('#itemManagerSupplier').addClass('count');
		
		$("#titleCheckbox").click(function() {
			if(this.checked){
				$("input[name='itemCheckbox']").each(function() {
					checkedCheckBox(this,true);
					changeValue(this);
				});
			}else{
				$("input[name='itemCheckbox']").each(function() {
					checkedCheckBox(this,false);
					changeValue(this);
				});
			}
		});
	})
		var count = ${(i!"0")?js_string};
		var patrn=/^[0-9]{1,2}$/;
		function isDigit(s){
			if (!patrn.exec(s)){ 
				return false;
			}else{
				return true;
			}
		}
		function setAllReward(){
			var allReward = $("#allReward").attr("value");
			if(0 == jQuery.trim(allReward).length){
				alert('请填写批量设置返点比例。');
				return false;
			}else if(!isDigit(allReward)){
				alert('返点比例只能输入两位整数。');
				return false;
			}else if(0 == allReward){
				alert('批量设置返点比例必须大于0，小于100。');
				return false;
			}
			if(count == 0){
        		return false;
        	}
        	for(var i = 0;i<count+1;i++){
        		var status = $("#"+i).attr('itemStatus');
        		if(status == -1 && $("#"+i).attr('checked')){
        			$("#reward" + i).attr("value",allReward);
        		}
        	}
		}
		
        function validate(){
        	var submitNum = 0;
        	if(count == 0){
        		return false;
        	}
        	var allReward = $("#allReward").attr("value");
        	for(var i = 0;i<count+1;i++){
        		if($("#" + i).attr("checked")) {
	        		// check 返点比率是否填写
	        		var temp = jQuery.trim($("#reward" + i).attr("value"));
        			if(0 == temp.length){
	        			alert('请填写第' + (i+1) + '项分销商品的返点比率');
	        			return false;
        			}else if(!isDigit(temp)){
						alert('第' + (i+1) + '项输入的不是数字。');
						return false;
					}else if(0 == temp){
        				alert('第' + (i+1) + '项返点比例必须大于0，小于100。');
						return false;
        			}
        			if(0 == jQuery.trim($("#status" + i).attr("value")).length){
	        			alert('请设置第' + (i+1) + '项分销商品的状态');
	        			return false;
        			}
        			submitNum ++;
        		}
        	}
        	if(0 == submitNum){
        		alert('请选择您要分销的商品');
        		return false;
        	}
        	return true;
        }
        
        function save(){
        	if(validate()){
				$("#pageForm").submit();
        	}
		}
		
		function itemCheck(o){
			changeValue(o);
		}
		
		function checkedCheckBox(element,checked){
			var status = $(element).attr('itemStatus');
			if(status == -1){
				$(element).attr("checked", checked);
			}
		}
		
		function changeValue(element){
			if(element.checked && !$(element).attr('itemStatus') == 0){
				$("#check"+element.id).val(1);
			}else{
				$("#check"+element.id).val(0);
			}
		}
    </script>
